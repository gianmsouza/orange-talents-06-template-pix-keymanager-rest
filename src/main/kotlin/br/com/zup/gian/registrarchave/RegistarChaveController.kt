package br.com.zup.gian.registrarchave

import br.com.zup.gian.KeyManagerRegistraChaveServiceGrpc
import br.com.zup.gian.RegistraChavePixRequest
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/pix/chaves")
class RegistarChaveController(
    @Inject val gRpcClient:
    KeyManagerRegistraChaveServiceGrpc.KeyManagerRegistraChaveServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Post
    fun registrarChave(@Body @Valid request: RegistrarChaveRequest): HttpResponse<Any> {

        logger.info("Novo registro de chave PIX em andamento")

        val requestGrpc: RegistraChavePixRequest = request.toRequestGrpc()

        try {
            val response = gRpcClient.registrar(requestGrpc)

            val uri = UriBuilder.of("/pix/chaves/{id}")
                .expand(mutableMapOf(Pair("id", response.id)))

            return HttpResponse.created(uri)

        } catch (e: StatusRuntimeException) {
            logger.info("Novo registro de chave PIX falhou: $e")
            val statusCode = e.status.code

            if (statusCode == Status.Code.INVALID_ARGUMENT) {
                throw HttpStatusException(HttpStatus.BAD_REQUEST, e.message)
            }

            if (statusCode == Status.Code.NOT_FOUND) {
                throw HttpStatusException(HttpStatus.NOT_FOUND, e.message)
            }

            if (statusCode == Status.Code.ALREADY_EXISTS) {
                throw HttpStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.message)
            }

            return HttpResponse.serverError(e.message)
        }
    }
}