package PAE.deliverynt_backend.controllers


import PAE.deliverynt_backend.api.getGeolocation
import PAE.deliverynt_backend.data.getRoutes
import PAE.deliverynt_backend.models.Coordinates
import PAE.deliverynt_backend.models.response.RoutesInfo
import PAE.deliverynt_backend.utils.apiAuth
import PAE.deliverynt_backend.utils.getCoordFromStringResponse
import PAE.deliverynt_backend.utils.getMockedMapResponse
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/routes")
@RestController
class RoutesController {

    @CrossOrigin(origins = ["*"])
    @PostMapping("")
    fun getRoute(@RequestBody routeInfo: RoutesInfo, @RequestHeader(value="Authorization") authorization: String): ResponseEntity<String> {
        if(authorization != apiAuth){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization")
        }

        val body: String= Gson().toJson(getRoutes(routeInfo.vehicles, routeInfo.orders))
        return ResponseEntity.status(HttpStatus.OK).body(body)
    }

    @CrossOrigin(origins = ["*"])
    @GetMapping("/map")
    fun getLatestRoute(@RequestHeader(value="Authorization") authorization: String): ResponseEntity<String> {
        if(authorization != apiAuth){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization")
        }

        val body: String= getMockedMapResponse()
        return ResponseEntity.status(HttpStatus.OK).body(body)
    }

    @CrossOrigin(origins = ["/**"])
    @PostMapping("/coord")
    fun getCoord(@RequestBody direction: String, @RequestHeader(value="Authorization") authorization: String): Coordinates? {
        if(authorization != apiAuth){
            return null
        }
        val apiResult: String= Gson().toJson(getGeolocation(direction))
        val body: Coordinates = getCoordFromStringResponse(apiResult)
        return body
    }
}