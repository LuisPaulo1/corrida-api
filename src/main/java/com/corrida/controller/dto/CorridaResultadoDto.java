package com.corrida.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorridaResultadoDto {
	
	private Integer posicaoDeChegada;
	private String codigoDoSuperHeroi;
	private String nomeDoSuperHeroi;
	private Integer QuantidadeDeVoltasCompletadas;
	private String tempoTotalDaProva;
	private String melhorVoltaDoSuperHeroi;
	private String melhorVoltaDaCorrida;
	private String velocidadeMedia;

}
