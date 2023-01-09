package PAE.deliverynt_backend.controllers

import PAE.deliverynt_backend.data.createVehicleDB
import PAE.deliverynt_backend.data.deleteVehicleDB
import PAE.deliverynt_backend.data.selectAllVehicles
import PAE.deliverynt_backend.models.Vehicles
import PAE.deliverynt_backend.models.Vehicle
import PAE.deliverynt_backend.utils.apiAuth
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/vehicles")
@RestController
class VehiclesController {
    val gson = Gson()

    @CrossOrigin(origins = ["*"])
    @GetMapping("")
    fun getAllVehicles(@RequestHeader(value="Authorization") authorization: String): ResponseEntity<String> {
        if(authorization != apiAuth){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization")
        }

        val vehiclesDB: Vehicles = selectAllVehicles()
        val body = gson.toJson(vehiclesDB.vehicles)
        return ResponseEntity.status(HttpStatus.OK).body(body)
    }
    @CrossOrigin(origins = ["*"])
    @PostMapping("")
    fun createVehicle(@RequestBody vehicle: Vehicle, @RequestHeader(value="Authorization") authorization: String): ResponseEntity<String> {
        if(authorization != apiAuth){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization")
        }
        createVehicleDB(vehicle)
        return ResponseEntity.status(HttpStatus.OK).body("Vehicle Created")
    }
    @CrossOrigin(origins = ["*"])
    @DeleteMapping("/{numberPlate}")
    fun deleteVehicle(@PathVariable numberPlate: String, @RequestHeader(value="Authorization") authorization: String): ResponseEntity<String> {
        if(authorization != apiAuth){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization")
        }
        deleteVehicleDB(numberPlate)
        return ResponseEntity.status(HttpStatus.OK).body("Vehicle Deleted")
    }
}