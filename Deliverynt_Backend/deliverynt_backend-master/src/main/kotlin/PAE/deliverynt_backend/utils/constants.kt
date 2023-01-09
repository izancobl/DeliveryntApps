package PAE.deliverynt_backend.utils

import PAE.deliverynt_backend.models.response.Location
import PAE.deliverynt_backend.models.response.RouteTemplate

const val AVERAGE_SPEED: Double = 25.0

val STORAGE_COORDINATES: ArrayList<Double> = arrayListOf(2.029982, 41.328411)

const val ORDER_DURATION: Int = 120

val ROUTE_TEMPLATE: RouteTemplate = RouteTemplate("drive",
                                                    arrayListOf(),
                                                    arrayListOf(),
                                                    arrayListOf(Location("storage", STORAGE_COORDINATES)))


val mailBody: String = "Goog Morning!\n\n" +
        "We are happy to inform you that your purchase realized the DATE has been processed!\n\n" +
        "The order total was PRICE" +
        "\n\n" +
        "We will inform you in next steps" +
        "\n\nThank you for your trust in us!" +
        "\n\nDeliveryn\'t support team"