package SpringMicroservicesInAction.ZipkinService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import zipkin.server.EnableZipkinServer

@SpringBootApplication
@EnableZipkinServer
class ZipkinServiceApplication

fun main(args: Array<String>) {
	runApplication<ZipkinServiceApplication>(*args)
}
