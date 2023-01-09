package PAE.deliverynt_backend.controllers

import PAE.deliverynt_backend.api.ApiMailJet
import PAE.deliverynt_backend.api.ApiSendGrid
import PAE.deliverynt_backend.data.ComandaFinder
import PAE.deliverynt_backend.models.Comanda
import PAE.deliverynt_backend.utils.ENABLED_MAILS_API
import PAE.deliverynt_backend.utils.RECEIVER_MAIL
import PAE.deliverynt_backend.utils.mailBody
import com.google.gson.Gson
import java.time.LocalDate

class ComandaController {

    fun readComandesDB() : Array<Comanda>? {
        return ComandaFinder().read()
    }

    fun writeComandesDB(comanda: Comanda) {
        ComandaFinder().write(comanda)
        if (ENABLED_MAILS_API) {
            val body: String = mailBody.replace("DATE", LocalDate.now().toString()).replace("PRICE", comanda.price.toString())
            ApiMailJet.sendMail(RECEIVER_MAIL, body)
        }
    }

    fun deleteComandaDB(id: String) {
        ComandaFinder().delete(id)
    }
}