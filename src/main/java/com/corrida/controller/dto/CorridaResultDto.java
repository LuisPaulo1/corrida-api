package com.corrida.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorridaResultDto {
	
	private Integer posicaoChegada;
	private String codigoSuperHeroi;
	private String nomeSuperHeroi;
	private Integer QtsVoltasCompletadas;
	private String tempoTotalProva;	
	private String melhorVoltaDoSuperHeroi;
	private String melhorVoltaDaCorrida;
	private String velocidadeMedia;

}
