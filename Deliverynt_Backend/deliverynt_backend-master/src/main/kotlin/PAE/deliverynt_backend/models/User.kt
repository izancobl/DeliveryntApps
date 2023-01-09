package PAE.deliverynt_backend.models

data class User(val email: String, val provider: String, val local: String, val location: Coordinates)
