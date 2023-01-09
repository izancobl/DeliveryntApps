package PAE.deliverynt_backend.controllers

import PAE.deliverynt_backend.data.createUserDB
import PAE.deliverynt_backend.data.createVehicleDB
import PAE.deliverynt_backend.data.deleteVehicleDB
import PAE.deliverynt_backend.data.selectAllVehicles
import PAE.deliverynt_backend.models.User
import PAE.deliverynt_backend.models.Vehicles
import PAE.deliverynt_backend.models.Vehicle
import PAE.deliverynt_backend.utils.apiAuth
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController {
    val gson = Gson()
    @CrossOrigin(origins = ["/**"])
    @PostMapping("")
    fun createUser(@RequestBody user: User, @RequestHeader(value="Authorization") authorization: String): String {
        if(authorization != apiAuth){
            return "Invalid Authorization"
        }
        createUserDB(user)
        return "User Created"
    }
}