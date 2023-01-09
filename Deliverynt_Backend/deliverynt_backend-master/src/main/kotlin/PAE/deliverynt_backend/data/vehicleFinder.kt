package PAE.deliverynt_backend.data

import PAE.deliverynt_backend.models.Vehicle
import PAE.deliverynt_backend.models.Vehicles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.util.*

fun selectAllVehicles(): Vehicles {
    // Variables
    val gson = Gson()
    val vehiclesFile = File("src/main/kotlin/PAE/deliverynt_backend/db/vehiclesDB.json")

    // File to String
    val bufferedReader: BufferedReader = vehiclesFile.bufferedReader()
    val inputString = bufferedReader.use { it.readText() }

    // String to type
    val arrayTutorialType = object : TypeToken<Vehicles>() {}.type
    val vehiclesDB: Vehicles = gson.fromJson(inputString, arrayTutorialType)

    return vehiclesDB
}

@Throws(Exception::class)
fun createVehicleDB(vehicle: Vehicle) {
    // Variables
    val vehiclesFile = File("src/main/kotlin/PAE/deliverynt_backend/db/vehiclesDB.json")
    val gson = Gson()

    // Function that gets Vehicles from DB
    val vehiclesDB: Vehicles = selectAllVehicles()

    // Add new vehicle if not exists
    for (v in vehiclesDB.vehicles) {
        if(v.numberPlate == vehicle.numberPlate) {
            throw Exception("Vehicle Already Exists")
        }
    }
    vehiclesDB.vehicles.add(vehicle)

    // Vehicles to String and Store on File
    val outputString = gson.toJson(vehiclesDB)
    vehiclesFile.writeText(outputString)
}

@Throws(Exception::class)
fun deleteVehicleDB (numberPlate: String) {
    // Variables
    val vehiclesFile = File("src/main/kotlin/PAE/deliverynt_backend/db/vehiclesDB.json")
    val gson = Gson()

    // Function that gets Vehicles from DB
    val vehiclesDB: Vehicles = selectAllVehicles()

    // Vehicles already doesn't exist
    val vehicle: Vehicle? = vehiclesDB.vehicles.find { it.numberPlate == numberPlate}
    vehicle ?: throw IOException("Vehicle doesn't exist")


    // Delete vehicle if exists
    val vehiclesToFilter = arrayListOf<Vehicle>()
    vehiclesDB.vehicles.filterTo(vehiclesToFilter) { it.numberPlate != numberPlate }

    // Vehicles to String and Store on File
    vehiclesDB.vehicles = vehiclesToFilter
    val outputString = gson.toJson(vehiclesDB)
    vehiclesFile.writeText(outputString)
}
