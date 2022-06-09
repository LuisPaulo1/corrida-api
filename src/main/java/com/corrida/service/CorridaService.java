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
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.corrida.controller.dto.CorridaInputDto;
import com.corrida.controller.dto.CorridaResultDto;
import com.corrida.model.Corrida;
import com.corrida.service.expection.NegocioException;

@Service
public class CorridaService {
	
	private DateTimeFormatter  formatarTempoDaVolta = new DateTimeFormatterBuilder()
			.appendPattern("m:ss.SSS")
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)				
			.toFormatter();
	
	public List<CorridaResultDto> lerArquivo(CorridaInputDto corridaInputDto) {
				
		try {
			InputStream inputStream = corridaInputDto.getArquivo().getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");			
			
			List<Corrida> listaInput = new ArrayList<>();
			String linha = br.readLine();
			while(linha != null) {				
				String[] sf = linha.split(";");				
				LocalTime hora = LocalTime.from(formatterHora.parse(sf[0]));				
				String nomeSuperHeroi = sf[1];
				Integer numeroVolta = Integer.parseInt(sf[2]);				
				LocalTime tempoDeVolta = LocalTime.parse(sf[3], formatarTempoDaVolta);	
				Double velocidadeMedia = Double.parseDouble(sf[4].replace(",", "."));
				
				listaInput.add(new Corrida(hora, nomeSuperHeroi, numeroVolta, tempoDeVolta, velocidadeMedia));
				linha = br.readLine();
			}
			
			List<CorridaResultDto> listaResult = calcularEstatisticasCorrida(listaInput);
		
		return listaResult;
		
		} catch (Exception e) {
			throw new NegocioException("Erro durante a leitura do arquivo"); 
		}		
	}
	
	private List<CorridaResultDto> calcularEstatisticasCorrida(List<Corrida> listaInput) {
				
		List<Corrida> listaClassificacao = listaInput.stream()
				 .filter(x -> x.getNumeroVolta() == 4 || (x.getNumeroVolta() == 3 && x.getNomeSuperHeroi().startsWith("011")))
				 .sorted((x, y) -> x.getHora().compareTo(y.getHora()))
				 .collect(Collectors.toList());
		
		List<CorridaResultDto> listaResultadoFinal = new ArrayList<>();
				
		listaClassificacao.forEach(x -> {
			int posicaoChegada = listaClassificacao.indexOf(x) + 1;
			String codigoSuperHeroi = x.getNomeSuperHeroi().substring(0, 3);
			String nomeSuperHeroi = x.getNomeSuperHeroi().substring(4);
			Integer QtsVoltasCompletadas = x.getNumeroVolta();
			String tempoTotalProva = formatarTempoDaVolta.format(buscarTempoTotalDeProva(x.getNomeSuperHeroi().substring(0, 3), listaInput));	
			String melhorVoltaDoSuperHeroi = melhorVoltaDoSuperHeroi(x.getNomeSuperHeroi().substring(0, 3), listaInput);
			String melhorVoltaDaCorrida = melhorVoltaDaCorrida(listaInput).getNomeSuperHeroi().subSequence(0, 3).equals(codigoSuperHeroi) ? 
					"Tempo: "+ formatarTempoDaVolta.format(melhorVoltaDaCorrida(listaInput).getTempoDeVolta())+" - "+"Número da volta: "+melhorVoltaDaCorrida(listaInput).getNumeroVolta() : null;  
			String velocidadeMedia = velocidadeMediaDoSuperHeroi(x.getNomeSuperHeroi().substring(0, 3), listaInput);
			listaResultadoFinal.add(new CorridaResultDto(posicaoChegada, codigoSuperHeroi, nomeSuperHeroi, QtsVoltasCompletadas, tempoTotalProva, melhorVoltaDoSuperHeroi, melhorVoltaDaCorrida, velocidadeMedia));			
		});
		 
		return listaResultadoFinal;
	}
	
	private LocalTime buscarTempoTotalDeProva(String codigo, List<Corrida> listaInput) {
		LocalTime tempo = listaInput.stream()
				.filter(x -> x.getNomeSuperHeroi().startsWith(codigo))
				.map(x -> x.getTempoDeVolta())				
				.reduce(LocalTime.of(0, 0, 0, 0), (x , y) -> x.plusHours(y.getHour()).plusMinutes(y.getMinute()).plusSeconds(y.getSecond()).plusNanos(y.getNano()));		
		return tempo;
	}
	
	private String melhorVoltaDoSuperHeroi(String codigo, List<Corrida> listaInput) {
		Corrida corrida = listaInput.stream()
				.filter(x -> x.getNomeSuperHeroi().startsWith(codigo))				
				.min((x, y) -> x.getTempoDeVolta().compareTo(y.getTempoDeVolta()))
				.get();		
		String result = "Tempo: "+formatarTempoDaVolta.format(corrida.getTempoDeVolta())+" - "+"Número da volta: "+ corrida.getNumeroVolta();		
		return result;
	}
	
	private Corrida melhorVoltaDaCorrida(List<Corrida> listaInput) {				
		Corrida corrida = listaInput.stream()				
				.min((x, y) -> x.getTempoDeVolta().compareTo(y.getTempoDeVolta()))
				.get();		
		return corrida;
	}
	
	private String velocidadeMediaDoSuperHeroi(String codigo, List<Corrida> listaInput) {		
		Double velocidadeMedia = listaInput.stream()
				.filter(x -> x.getNomeSuperHeroi().startsWith(codigo))
				.mapToDouble(x -> x.getVelocidadeMedia())				
				.average().orElse(0.0);		
		return new DecimalFormat("0.###").format(velocidadeMedia);
	}

}
