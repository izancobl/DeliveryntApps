package PAE.deliverynt_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class DeliveryntBackendApplicationKt {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<DeliveryntBackendApplicationKt>(*args)
		}
	}
}