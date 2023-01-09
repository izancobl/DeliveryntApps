import PAE.deliverynt_backend.data.ComandaFinder
import PAE.deliverynt_backend.models.Comanda
import PAE.deliverynt_backend.models.Coordinates
import PAE.deliverynt_backend.models.Estate
import PAE.deliverynt_backend.models.Product
import org.junit.jupiter.api.Test

class ComandaTests {

    @Test
    fun contextLoads() {
        val comandaFinder = ComandaFinder()

        val products = arrayOf( Product(5, "Estrella Damm", 3, 0.9, 2, "drinks") ,Product(4, "Choco", 3, 0.9, 2, "drinks"))
        val comanda = Comanda("3", "Bar FIB", 4, products, Coordinates(1.23, 5.23), 14.9, "g@gmail.com", Estate.Created)

        comandaFinder.delete("3")
    }
}