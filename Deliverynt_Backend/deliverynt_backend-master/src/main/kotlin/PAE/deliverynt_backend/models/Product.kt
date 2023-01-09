package PAE.deliverynt_backend.models

class Product (
    var id : Int,
    var name : String,
    var size : Int,
    var price: Double,
    var quantity: Int,
    var photo: String,
        ) {
        override fun toString(): String {
                return "Product(Id=$id, Name='$name', Size=$size)"
        }
}