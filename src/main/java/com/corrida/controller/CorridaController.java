package com.corrida.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corrida.controller.dto.CorridaInputDto;
import com.corrida.controller.dto.CorridaResultDto;
import com.corrida.service.CorridaService;

@RestController
@RequestMapping(path = "/corridas")
public class CorridaController {
	
	@Autowired
	private CorridaService service;
	
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<List<CorridaResultDto>> lerArquivoTexto(@Valid CorridaInputDto corridaInputDto){
		return ResponseEntity.ok(service.lerArquivo(corridaInputDto));
	}

}
