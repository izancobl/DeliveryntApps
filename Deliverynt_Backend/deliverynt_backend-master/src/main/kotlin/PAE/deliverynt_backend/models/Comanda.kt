package PAE.deliverynt_backend.models


class Comanda (
    var id : String,
    var restaurant : String,
    var numPackages : Int,
    var productes : Array<Product>,
    var location : Coordinates,
    var price: Double,
    val userMail: String,
    var estate: Estate
    )
    {
        override fun toString(): String {
            return "Comanda(id=$id, restaurant='$restaurant', numPackages=$numPackages, productes=${productes.contentToString()}, location=$location)"
        }
    }