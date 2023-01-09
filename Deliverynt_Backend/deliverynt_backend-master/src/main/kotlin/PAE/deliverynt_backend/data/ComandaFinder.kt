package PAE.deliverynt_backend.data

import PAE.deliverynt_backend.models.Comanda
import PAE.deliverynt_backend.models.Estate
import com.google.gson.Gson
import java.io.File

class ComandaFinder {

    private val gson = Gson()

    fun read(): Array<Comanda> {
        // reads file and transforms is into an array
        val readFile = File("src/main/kotlin/PAE/deliverynt_backend/db/ComandesDB.json").readText()
        val array = gson.fromJson(readFile, Array<Comanda>::class.java)
        return array
    }

    fun write(comanda : Comanda) {
        // read file
        val comandes : Array<Comanda>? = read()

        val toWrite : ArrayList<Comanda> = ArrayList()

        // check if it already exists
        if (comandes != null) {
            for (c in comandes) {
                if (c.id == comanda.id) {
                    throw Exception("Comanda already exists")
                }
                toWrite.add(c)
            }
        }

        toWrite.add(comanda)

        val jsonString = gson.toJson(toWrite)
        File("src/main/kotlin/PAE/deliverynt_backend/db/ComandesDB.json").writeText(jsonString)
    }

    fun delete(Id : String) {
        val comandes : Array<Comanda> = read()

        // find if comanda exists
        val found = comandes.find { it.id == Id }
        found ?: throw Exception("Comanda doesn't exists")

        // remove all comandes
        val toWrite : ArrayList<Comanda> = ArrayList()
        comandes.filterTo(toWrite) {it.id != Id}

        val jsonString = gson.toJson(toWrite)
        File("src/main/kotlin/PAE/deliverynt_backend/db/ComandesDB.json").writeText(jsonString)

    }

    fun getComanda(id: String): Comanda {
        val comandes : Array<Comanda> = read()

        // find if comanda exists
        val comanda: Comanda? = comandes.find { it.id == id }
        comanda ?: throw Exception("Comanda doesn't exists")

       return comanda
    }

    fun getComandaByEmail(email: String): List<Comanda>? {
        val comandes : Array<Comanda> = read()

        // find if comanda exists
        val comandesFiltered: List<Comanda>? = comandes.filter { it.userMail == email }

        return comandesFiltered
    }
    fun updateComanda(id: String) {
        // Get comanda
        val comanda: Comanda = this.getComanda(id)

        // Delete from DB
        this.delete(id)

        // Put Updated
        comanda.estate = Estate.Delivering
        this.write(comanda)
    }
}
