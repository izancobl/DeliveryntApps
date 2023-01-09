package PAE.deliverynt_backend.api

import PAE.deliverynt_backend.utils.SENDGRID_API_KEY
import com.sendgrid.*


class ApiSendGrid {
    @Throws(Exception::class)
    fun sendEmail(emailValue: String, reciever: String) {
        val from: Email = Email("gecasgue2001@gmail.com")
        val subject = "Your order has been processed"
        val to = Email(reciever)
        val content = Content("text/plain", emailValue)
        val mail = Mail(from, subject, to, content)
        val sg = SendGrid(SENDGRID_API_KEY)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
            println(response.statusCode)
            println(response.body)
            println(response.headers)
        } catch (ex: Exception) {
            throw ex
        }
    }
}