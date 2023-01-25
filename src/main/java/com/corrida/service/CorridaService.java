package com.corrida.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.corrida.controller.dto.CorridaInputDto;
import com.corrida.controller.dto.CorridaResultadoDto;
import com.corrida.model.Corrida;
import com.corrida.service.expection.ReadFileException;

@Service
public class CorridaService {
	
	private final DateTimeFormatter  formatarTempoDaVolta = new DateTimeFormatterBuilder()
			.appendPattern("m:ss.SSS")
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)				
			.toFormatter();
	
	public List<CorridaResultadoDto> lerArquivo(CorridaInputDto corridaInputDto) {
				
		try {
			InputStream inputStream = corridaInputDto.getArquivo().getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");			
			
			List<Corrida> listaDeParticipantesDaCorrida = new ArrayList<>();
			String linha = br.readLine();
			while(linha != null) {
				String[] sf = linha.split(";");				
				LocalTime horaQueCompletaAVolta = LocalTime.from(formatterHora.parse(sf[0]));
				String nomeDoSuperHeroi = sf[1];
				Integer numeroDeVolta = Integer.parseInt(sf[2]);
				LocalTime tempoDaVolta = LocalTime.parse(sf[3], formatarTempoDaVolta);
				Double velocidadeMedia = Double.parseDouble(sf[4].replace(",", "."));
				
				listaDeParticipantesDaCorrida.add(new Corrida(horaQueCompletaAVolta, nomeDoSuperHeroi, numeroDeVolta, tempoDaVolta, velocidadeMedia));
				linha = br.readLine();
			}

			return calcularEstatisticaDaCorrida(listaDeParticipantesDaCorrida);
		
		} catch (Exception e) {
			throw new ReadFileException("Erro durante a leitura do arquivo");
		}		
	}
	
	private List<CorridaResultadoDto> calcularEstatisticaDaCorrida(List<Corrida> listaDeParticipantesDaCorrida) {
				
		List<Corrida> listaDeClassificados = listaDeParticipantesDaCorrida.stream()
				 .filter(x -> x.getNumeroDeVolta() == 4 || (x.getNumeroDeVolta() == 3 && x.getNomeDoSuperHeroi().startsWith("011")))
				 .sorted(Comparator.comparing(Corrida::getHoraQueCompletaAVolta))
				 .collect(Collectors.toList());
		
		List<CorridaResultadoDto> listaResultadoFinal = new ArrayList<>();
				
		listaDeClassificados.forEach(corrida -> {
			int posicaoDeChegada = listaDeClassificados.indexOf(corrida) + 1;
			String codigoDoSuperHeroi = corrida.getNomeDoSuperHeroi().substring(0, 3);
			String nomeDoSuperHeroi = corrida.getNomeDoSuperHeroi().substring(4);
			Integer QuantidadeDeVoltasCompletadas = corrida.getNumeroDeVolta();
			String tempoTotalDaProva = buscarTempoTotalDaProva(codigoDoSuperHeroi, listaDeParticipantesDaCorrida);
			String melhorVoltaDoSuperHeroi = melhorVoltaDoSuperHeroi(codigoDoSuperHeroi, listaDeParticipantesDaCorrida);
			String melhorVoltaDaCorrida = melhorVoltaDaCorrida(codigoDoSuperHeroi, listaDeParticipantesDaCorrida);
			String velocidadeMedia = velocidadeMediaDoSuperHeroi(codigoDoSuperHeroi, listaDeParticipantesDaCorrida);
			listaResultadoFinal.add(new CorridaResultadoDto(posicaoDeChegada, codigoDoSuperHeroi, nomeDoSuperHeroi, QuantidadeDeVoltasCompletadas, tempoTotalDaProva, melhorVoltaDoSuperHeroi, melhorVoltaDaCorrida, velocidadeMedia));
		});
		 
		return listaResultadoFinal;
	}
	
	private String buscarTempoTotalDaProva(String codigoSuperHeroi, List<Corrida> listaDeParticipantesDaCorrida) {
		LocalTime tempoTotalDaProva = listaDeParticipantesDaCorrida.stream()
				.filter(x -> x.getNomeDoSuperHeroi().startsWith(codigoSuperHeroi))
				.map(Corrida::getTempoDaVolta)
				.reduce(LocalTime.of(0, 0, 0, 0), (x , y) -> x.plusHours(y.getHour()).plusMinutes(y.getMinute()).plusSeconds(y.getSecond()).plusNanos(y.getNano()));		
		return formatarTempoDaVolta.format(tempoTotalDaProva);
	}
	
	private String melhorVoltaDoSuperHeroi(String codigoSuperHeroi, List<Corrida> listaDeParticipantesDaCorrida) {
		Optional<Corrida> corrida = listaDeParticipantesDaCorrida.stream()
				.filter(x -> x.getNomeDoSuperHeroi().startsWith(codigoSuperHeroi))
				.min(Comparator.comparing(Corrida::getTempoDaVolta));

		return corrida.map(value -> "Tempo: " + formatarTempoDaVolta.format(value.getTempoDaVolta()) + " - " + "Número da volta: " + value.getNumeroDeVolta()).orElse(null);
	}
	
	private String melhorVoltaDaCorrida(String codigoSuperHeroi, List<Corrida> listaDeParticipantesDaCorrida) {
		Optional<Corrida> superHeroiComMelhorVoltaNaCorrida = listaDeParticipantesDaCorrida.stream()
				.min(Comparator.comparing(Corrida::getTempoDaVolta));

		if(superHeroiComMelhorVoltaNaCorrida.isPresent()) {
			var superHeroi = superHeroiComMelhorVoltaNaCorrida.get();
			if(superHeroi.getNomeDoSuperHeroi().substring(0, 3).equals(codigoSuperHeroi)){
				return "Tempo: "+ formatarTempoDaVolta.format(superHeroi.getTempoDaVolta())+" - "+"Número da volta: "+superHeroi.getNumeroDeVolta();
			}
		}
		return null;
	}
	
	private String velocidadeMediaDoSuperHeroi(String codigoSuperHeroi, List<Corrida> listaDeParticipantesDaCorrida) {
		Double velocidadeMedia = listaDeParticipantesDaCorrida.stream()
				.filter(x -> x.getNomeDoSuperHeroi().startsWith(codigoSuperHeroi))
				.mapToDouble(Corrida::getVelocidadeMedia)
				.average().orElse(0.0);		
		return new DecimalFormat("0.###").format(velocidadeMedia);
	}

}
