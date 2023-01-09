package PAE.deliverynt_backend.models.response

data class Agent(
    val start_location: ArrayList<Double>,
    val time_windows: ArrayList<ArrayList<Int>>)
