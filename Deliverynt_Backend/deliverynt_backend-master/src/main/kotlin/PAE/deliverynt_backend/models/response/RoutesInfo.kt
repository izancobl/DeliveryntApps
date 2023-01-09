package PAE.deliverynt_backend.models.response

import PAE.deliverynt_backend.models.Comanda
import PAE.deliverynt_backend.models.Vehicle

class RoutesInfo (
    var vehicles: ArrayList<Vehicle>,
    var orders: ArrayList<Comanda>
)