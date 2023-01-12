package com.corrida.controller.dto;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.corrida.validation.FileContentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorridaInputDto {
		
	@FileContentType(allowed = MediaType.TEXT_PLAIN_VALUE)	
	private MultipartFile arquivo;
	
}
