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
	
	private LocalTime horaQueCompletaAVolta;
	private String nomeDoSuperHeroi;
	private Integer numeroDeVolta;
	private LocalTime tempoDaVolta;
	private Double velocidadeMedia;	

}
