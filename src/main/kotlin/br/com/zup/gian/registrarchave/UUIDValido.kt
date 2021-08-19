package br.com.zup.gian.registrarchave

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint

@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UUIDValidator::class])
annotation class UUIDValido (
    val message: String = "ID não está em formato UUID"
)

@Singleton
class UUIDValidator: ConstraintValidator<UUIDValido, String> {
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<UUIDValido>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (!value!!.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$".toRegex())) {
            return false
        }
        return true
    }
}
