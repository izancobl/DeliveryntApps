package PAE.deliverynt_backend.api

import PAE.deliverynt_backend.utils.MAILJET_API_KEY
import PAE.deliverynt_backend.utils.MAILJET_SECRET_KEY
import PAE.deliverynt_backend.utils.SENDER_MAIL
import com.mailjet.client.ClientOptions
import com.mailjet.client.MailjetClient
import com.mailjet.client.MailjetRequest
import com.mailjet.client.MailjetResponse
import com.mailjet.client.errors.MailjetException
import com.mailjet.client.resource.Emailv31
import org.json.JSONArray
import org.json.JSONObject

object ApiMailJet {
    @Throws(MailjetException::class)
    @JvmStatic
    fun sendMail(RECEIVER: String, body: String) {
        val response: MailjetResponse
        val client: MailjetClient = MailjetClient(
            MAILJET_API_KEY,
            MAILJET_SECRET_KEY
        )
        val request: MailjetRequest = MailjetRequest(Emailv31.resource)
            .property(
                Emailv31.MESSAGES, JSONArray()
                    .put(
                        JSONObject()
                            .put(
                                Emailv31.Message.FROM, JSONObject()
                                    .put("Email", SENDER_MAIL)
                                    .put("Name", "Deliveryn't Team")
                            )
                            .put(
                                Emailv31.Message.TO, JSONArray()
                                    .put(
                                        JSONObject()
                                            .put("Email", RECEIVER)
                                            .put("Name", "User")
                                    )
                            )
                            .put(Emailv31.Message.SUBJECT, "Thanks for your purchase")
                            .put(Emailv31.Message.TEXTPART, "Order Confirmation")
                            .put(
                                Emailv31.Message.TEXTPART, body
                            )
                            .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")
                    )
            )
        response = client.post(request)
        println(response.status)
        println(response.data)
    }
}