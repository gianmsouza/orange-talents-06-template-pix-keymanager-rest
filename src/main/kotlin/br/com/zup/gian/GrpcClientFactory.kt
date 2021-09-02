package br.com.zup.gian

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {

    @Singleton
    fun pixKeyManagerClientStub(@GrpcChannel("pixKeyManager") channel: ManagedChannel)
            : KeyManagerRegistraChaveServiceGrpc.KeyManagerRegistraChaveServiceBlockingStub {

        return KeyManagerRegistraChaveServiceGrpc.newBlockingStub(channel)
    }
}