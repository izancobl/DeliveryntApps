package PAE.deliverynt_backend.models.response

data class PropiertyResponse(val agent_index: Int,
                             val time: Int,
                             val start_time: Int,
                             val end_time: Int,
                             val distance: Int,
                             val legs: Any,
                             val mode: String,
                             val actions: ArrayList<ActionResponse>,
                             val waypoints: Any
)
