package PAE.deliverynt_backend.data

import PAE.deliverynt_backend.models.User
import kotlin.collections.ArrayList
import PAE.deliverynt_backend.models.Vehicle
import PAE.deliverynt_backend.models.Vehicles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.util.*

fun selectAllUsers(): ArrayList<User> {
    // Variables
    val gson = Gson()
    val vehiclesFile = File("src/main/kotlin/PAE/deliverynt_backend/db/userDB.json")

    // File to String
    val bufferedReader: BufferedReader = vehiclesFile.bufferedReader()
    val inputString = bufferedReader.use { it.readText() }

    // String to type
    val arrayTutorialType = object : TypeToken<ArrayList<User>>() {}.type
    val vehiclesDB: ArrayList<User> = gson.fromJson(inputString, arrayTutorialType)

    return vehiclesDB
}

@Throws(Exception::class)
fun createUserDB(user: User) {
    // Variables
    val userFile = File("src/main/kotlin/PAE/deliverynt_backend/db/userDB.json")
    val gson = Gson()

    // Function that gets Vehicles from DB
    val userDB: ArrayList<User> = selectAllUsers()

    // Add new vehicle if not exists
    for (v in userDB) {
        if(v.email == user.email) {
            throw Exception("Vehicle Already Exists")
        }
    }
    userDB.add(user)

    // Vehicles to String and Store on File
    val outputString = gson.toJson(userDB)
    userFile.writeText(outputString)
}

@Throws(Exception::class)
fun deleteUserDB (email: String) {
    // Variables
    val userFile = File("src/main/kotlin/PAE/deliverynt_backend/db/userDB.json")
    val gson = Gson()

    // Function that gets Vehicles from DB
    var userDB: ArrayList<User> = selectAllUsers()

    // Vehicles already doesn't exist
    val user: User? = userDB.find { it.email == email}
    user ?: throw IOException("Vehicle doesn't exist")


    // Delete vehicle if exists
    val vehiclesToFilter = arrayListOf<User>()
    userDB.filterTo(vehiclesToFilter) { it.email != email }

    // Vehicles to String and Store on File
    userDB = vehiclesToFilter
    val outputString = gson.toJson(userDB)
    userFile.writeText(outputString)
}