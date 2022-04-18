package com.corrida.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Corrida {
	
	private LocalTime hora;
	private String nomeSuperHeroi;
	private Integer numeroVolta;
	private LocalTime tempoDeVolta;
	private Double velocidadeMedia;	

}
