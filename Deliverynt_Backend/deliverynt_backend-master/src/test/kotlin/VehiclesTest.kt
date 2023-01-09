
import PAE.deliverynt_backend.data.createVehicleDB
import PAE.deliverynt_backend.models.Vehicle
import org.junit.jupiter.api.Test

class VehiclesTest {
    @Test
    fun fileRead() {
        val vehicle = Vehicle("3333ZZZ", 100.0, 10)
        createVehicleDB(vehicle)
        //deleteVehicleDB(vehicle)
    }


}





