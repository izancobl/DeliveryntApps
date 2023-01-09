package PAE.deliverynt_backend.api

import PAE.deliverynt_backend.controllers.ComandaController
import PAE.deliverynt_backend.data.ComandaFinder
import PAE.deliverynt_backend.models.Comanda
import PAE.deliverynt_backend.utils.apiAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ApiController {
    val comandaController = ComandaController()

    @CrossOrigin(origins = ["*"])
    @GetMapping("/comandes")
    fun getCurrentComandes(@RequestHeader(value="Authorization") authorization: String): Array<Comanda>? {
        if(authorization != apiAuth){
            return null
        }
        val comandes = comandaController.readComandesDB()
        return comandes
    }

    @CrossOrigin(origins = ["/**"])
    @PostMapping("/comandes")
    fun newComanda(@RequestBody comanda : Comanda, @RequestHeader(value="Authorization") authorization: String): String {
        if(authorization != apiAuth){
            return "Invalid Authorization"
        }
        comandaController.writeComandesDB(comanda)
        return "Comanada created"
    }

    @CrossOrigin(origins = ["*"])
    @DeleteMapping("/comandes")
    fun deleteComanda(@RequestParam id : String, @RequestHeader(value="Authorization") authorization: String) : ResponseEntity<String> {
        if(authorization != apiAuth){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization")
        }
        comandaController.deleteComandaDB(id)
        return ResponseEntity.status(HttpStatus.OK).body("Comanda deleted")
    }

    @CrossOrigin(origins = ["/**"])
    @GetMapping("/comandes/{email}")
    fun getCurrentComandes(@PathVariable email: String, @RequestHeader(value="Authorization") authorization: String): List<Comanda>? {
        if(authorization != apiAuth){
            return null
        }
        val comandes = ComandaFinder().getComandaByEmail(email)
        return comandes
    }
}