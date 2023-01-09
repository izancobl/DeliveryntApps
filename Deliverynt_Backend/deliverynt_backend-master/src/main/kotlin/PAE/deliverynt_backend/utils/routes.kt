package PAE.deliverynt_backend.utils

import PAE.deliverynt_backend.data.ComandaFinder
import PAE.deliverynt_backend.data.selectAllVehicles
import PAE.deliverynt_backend.models.*
import PAE.deliverynt_backend.models.response.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.IOException


/**
 * This function parses vehicles info into a new arrayList of Agents
 *
 * @param vehicles array of the diferent vehicles to convert to agent
 * @return array of the vehicles transformed to agents
 * */
fun parseVehicles(vehicles: ArrayList<Vehicle>): ArrayList<Agent> {
    val agents: ArrayList<Agent> = ArrayList()
    for(v in vehicles) {
        val workTime: Int = (20 / AVERAGE_SPEED * 3600).toInt()
        agents.add(Agent(STORAGE_COORDINATES ,arrayListOf(arrayListOf(0,workTime))))
    }
    return agents
}

/**
 * This function parses Comanda info into a new arrayList of Shipment
 *
 * @param orders array of the diferent Comanda to convert to agent
 @return array of the Comanda transformed to Shipments
 * */
fun parseOrders(orders: ArrayList<Comanda>): ArrayList<Shipment> {
    val shipments: ArrayList<Shipment> = ArrayList()
    for(i in orders.indices) {
        val id = orders[i].id
        val pickUp = PickUp(0, ORDER_DURATION)
        val delivery = Delivery(arrayListOf(orders[i].location.lon, orders[i].location.lat), ORDER_DURATION)
        shipments.add(Shipment(id,pickUp,delivery))
    }
    return shipments
}

/**
 * This function cleans response stringify Json
 *
 * @param response routePlanner Api response
 * @return stringify response without useless parts
 * */
fun sanitizeRouteResponse(response: String): String {
    val index: Int =  response.indexOf("features")
    val responseShort: String = response.removeRange(0,index + 10)
    val featuresString: String = responseShort.dropLast(1)

    return featuresString
}

/**
 * This function take the routes that are RouteResponse and return routes with Route format
 *
 * @param featureArray ArrayList with the routes in RouteResponse Format
 * @param vehicles ArrayList of vehicles
 * @return ArrayList with Route format
 * */
fun getRouteFromResponse(featureArray: ArrayList<RouteResponse>, vehicles: ArrayList<Vehicle>): ArrayList<Route> {
    val routes: ArrayList<Route> = arrayListOf()
    for(agent in featureArray) {
        // Agent Features/Properties to Object
        val agentProperty: PropiertyResponse = agent.properties

        // Get Vehicle
        val vehicleId: Int = agentProperty.agent_index
        val vehicle: Vehicle = vehicles[vehicleId]

        // Get Comandes
        val comandes: ArrayList<Comanda> = arrayListOf()
        for (action in agentProperty.actions) {
            val id: String? = action.shipment_id
            if(id != null && action.type == "delivery") {
                val orderId: String = id
                comandes.add(ComandaFinder().getComanda(orderId))
                ComandaFinder().updateComanda(id)
            }
        }
        routes.add(Route(vehicle,comandes))
    }
    return routes
}

/**
 * This function parses vehicles info into a new arrayList of Agents
 *
 * @param vehicles array of the diferent vehicles to convert to agent
 * @return array of the vehicles transformed to agents
 * */
fun getRoutesFromResponse(response: String, vehicles: ArrayList<Vehicle>): ArrayList<Route> {
    // Clean response String
    val featuresString: String = sanitizeRouteResponse(response)

    // Response to Object
    val type = object : TypeToken<ArrayList<RouteResponse>>() {}.type
    val featureArray: ArrayList<RouteResponse> = Gson().fromJson(featuresString, type)

    // Get routes in Route format
    val routes: ArrayList<Route> = getRouteFromResponse(featureArray, vehicles)

    return routes
}

fun getMockedMapResponse(): String {
    val mockFile = File("src/main/kotlin/PAE/deliverynt_backend/db/mockResponse.json")
    val bufferedReader: BufferedReader = mockFile.bufferedReader()
    var inputString = bufferedReader.use { it.readText() }
    inputString = inputString.replace("\\", "")
    inputString = inputString.substring(1, inputString.length - 1)
    println(inputString)

    return inputString
}

fun getCoordFromStringResponse(apiResult: String) : Coordinates {
    val sanitizeApiResult: String = sanitizeApiResult(apiResult)
    val startIndex: Int = sanitizeApiResult.indexOf("lon:")
    val endIndex: Int = sanitizeApiResult.indexOf("formatted")
    val finalResult: String = sanitizeApiResult.substring(startIndex,endIndex)
    val lon: Double = finalResult.substring(finalResult.indexOf(":") + 1, finalResult.indexOf(",")).toDouble()
    val lat: Double = finalResult.substring(finalResult.indexOf("lat:") + 4, finalResult.indexOf(",", finalResult.indexOf(",") + 1)).toDouble()
    return Coordinates(lon,lat)
}

fun sanitizeApiResult(apiResult: String): String {
    val sanitizeApiResult: String = apiResult.replace("\\", "")
    val finalResult: String = sanitizeApiResult.replace("\"", "")
    return finalResult
}
fun storeResponse(response: String) {
    // Variables
    val mockFile = File("src/main/kotlin/PAE/deliverynt_backend/db/mockResponse.json")

    // Vehicles to String and Store on File
    val outputString = Gson().toJson(response)
    mockFile.writeText(outputString)
}

