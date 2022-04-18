package com.corrida.service.validation;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileContentTypeValidator.class })
public @interface FileContentType {

	String message() default "O tipo de arquivo não é suportado";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String[] allowed();
	
}