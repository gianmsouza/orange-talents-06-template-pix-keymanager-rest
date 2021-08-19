package br.com.zup.gian.registrarchave

import br.com.zup.gian.RegistraChavePixRequest
import br.com.zup.gian.TipoChave
import br.com.zup.gian.TipoConta
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class RegistrarChaveRequest(
    @field:NotBlank @field:UUIDValido val id: String,
    @field:NotBlank @field:NotNull val tipoChave: TipoChave,
    @field:Max(value = 77) val valorChave: String,
    @field:NotBlank @field:NotNull val tipoConta: TipoConta
) {
    fun toRequestGrpc(): RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setId(this.id)
            .setTipoChave(this.tipoChave)
            .setValorChave(this.valorChave)
            .setTipoConta(this.tipoConta)
            .build()
    }
}