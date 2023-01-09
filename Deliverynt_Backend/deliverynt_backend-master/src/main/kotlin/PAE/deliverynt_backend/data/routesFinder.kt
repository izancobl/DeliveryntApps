package PAE.deliverynt_backend.data

import PAE.deliverynt_backend.api.getRoutePlannerReponse
import PAE.deliverynt_backend.models.*
import PAE.deliverynt_backend.models.response.Agent
import PAE.deliverynt_backend.models.response.RouteTemplate
import PAE.deliverynt_backend.models.response.Shipment
import PAE.deliverynt_backend.utils.*
import com.google.gson.Gson

fun getRoutes(vehicles: ArrayList<Vehicle>, orders: ArrayList<Comanda>): ArrayList<Route> {
    // Vehicles to Agent Format
    val agents: ArrayList<Agent> = parseVehicles(vehicles)

    // Orders to Shipment Format
    val shipments: ArrayList<Shipment> = parseOrders(orders)

    // Get RouteTemplate
    val routeTemplate: RouteTemplate = ROUTE_TEMPLATE

    // Fill Template
    routeTemplate.agents = agents
    routeTemplate.shipments = shipments

    // Template to Json
    val routeTemplateJson = Gson().toJson(routeTemplate)

    // API call response or mockResponse
    val response: String = getRoutePlannerReponse(routeTemplateJson)

    // Store Response to Temporal DB
    storeResponse(response)

    // Get routes from response
    val routes: ArrayList<Route> = getRoutesFromResponse(response, vehicles)

    return routes
}






