package com.corrida.service.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.corrida.service.expection.NegocioException;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> allowedContentTypes;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed()); 
	}
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
				
		if(multipartFile == null || multipartFile.isEmpty()) 
			return true;
		
		boolean result = this.allowedContentTypes.contains(multipartFile.getContentType());
		if(result)
			return true;
				
		throw new NegocioException("O tipo do arquivo não é suportado. Selecione um arquivo no formato de texto com a extensão .txt");
	}

}