package com.corrida.model;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CorridaTests {
	
	@Test
	public void  corridaDeveriaTerUmaEstruturaCorreta() {
		
		Corrida corrida = new Corrida();
		corrida.setHoraQueCompletaAVolta(LocalTime.of(23, 49, 8, 277));
		corrida.setNomeDoSuperHeroi("038–Superman");
		corrida.setNumeroDeVolta(3);
		corrida.setTempoDaVolta(LocalTime.of(0, 1, 02, 852));
		corrida.setVelocidadeMedia(44.275);
	
		Assertions.assertNotNull(corrida.getHoraQueCompletaAVolta());
		Assertions.assertNotNull(corrida.getNomeDoSuperHeroi());
		Assertions.assertNotNull(corrida.getNumeroDeVolta());
		Assertions.assertNotNull(corrida.getTempoDaVolta());
		Assertions.assertNotNull(corrida.getVelocidadeMedia());
		
	}

}
