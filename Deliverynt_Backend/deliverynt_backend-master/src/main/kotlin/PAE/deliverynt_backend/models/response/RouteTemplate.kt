package PAE.deliverynt_backend.models.response

data class RouteTemplate(val mode: String,
                         var agents: ArrayList<Agent>?,
                         var shipments: ArrayList<Shipment>?,
                         val locations: ArrayList<Location>)
