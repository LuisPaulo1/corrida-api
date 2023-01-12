package com.corrida.model;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CorridaTests {
	
	@Test
	public void  corridaDeveriaTerUmaEstruturaCorreta() {
		
		Corrida corrida = new Corrida();
		corrida.setHora(LocalTime.of(23, 49, 8, 277));
		corrida.setNomeSuperHeroi("038–Superman");
		corrida.setNumeroVolta(3);
		corrida.setTempoDeVolta(LocalTime.of(0, 1, 02, 852));
		corrida.setVelocidadeMedia(44.275);
	
		Assertions.assertNotNull(corrida.getHora());
		Assertions.assertNotNull(corrida.getNomeSuperHeroi());
		Assertions.assertNotNull(corrida.getNumeroVolta());
		Assertions.assertNotNull(corrida.getTempoDeVolta());
		Assertions.assertNotNull(corrida.getVelocidadeMedia());
		
	}

}
