package PAE.deliverynt_backend.models.response

data class ActionResponse(val index: Int,
                          val type: String,
                          val start_time: Int,
                          val duration: Int,
                          val waypoint_index: Int,
                          val shipment_index: String?,
                          val shipment_id: String?
)
