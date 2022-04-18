package com.corrida.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class CorridaControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	private String nomeArquivoKey;
	private String nomeArquivoEmDisco;
	private String mediaType;
	private String mediaTypeInvalido;
	private String conteudoDoArquivo;
	private String conteudoDoArquivoInvalido;
	
	
	@BeforeEach
	void setUp() throws Exception {	
		nomeArquivoKey = "arquivo";
		nomeArquivoEmDisco = "corrida.txt";
		mediaType = MediaType.TEXT_PLAIN_VALUE;
		mediaTypeInvalido = MediaType.IMAGE_JPEG_VALUE;
		conteudoDoArquivo = "23:49:08.277;038–Superman;1;1:02.852;44,275";
		conteudoDoArquivoInvalido = "23:49:08.277;038–Superman;1;1:02.852;44,2--75";
	}
	
	@Test
	public void lerArquivoTextoDeveriaRetornarStatusOkQuandoArquivoForValido() throws Exception {
		
		MockMultipartFile arquivoTexto = new MockMultipartFile(nomeArquivoKey, nomeArquivoEmDisco, mediaType, conteudoDoArquivo.getBytes());
		
		ResultActions result =
				mockMvc.perform(multipart("/corridas")						
						.file(arquivoTexto));
				
				result.andExpect(status().isOk());	
	}
	
	@Test
	public void lerArquivoTextoDeveriaRetornarStatusBadRequestQuandoTipoDeArquivoForInvalido() throws Exception {
		
		MockMultipartFile arquivoTexto = new MockMultipartFile(nomeArquivoKey, nomeArquivoEmDisco, mediaTypeInvalido, conteudoDoArquivo.getBytes());
		
		ResultActions result =
				mockMvc.perform(multipart("/corridas")						
						.file(arquivoTexto));
				
				result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void lerArquivoTextoDeveriaRetornarStatusBadRequestQuandoConteudoDoArquivoForInvalido() throws Exception {
		
		MockMultipartFile arquivoTexto = new MockMultipartFile(nomeArquivoKey, nomeArquivoEmDisco, mediaType, conteudoDoArquivoInvalido.getBytes());
		
		ResultActions result =
				mockMvc.perform(multipart("/corridas")						
						.file(arquivoTexto));
				
				result.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void lerArquivoTextoDeveriaRetornarStatusUnsupportedMediaTypeQuandoContentTypeNaoForMultipartForm() throws Exception {
		
		MockMultipartFile arquivoTexto = new MockMultipartFile(nomeArquivoKey, nomeArquivoEmDisco, mediaType, conteudoDoArquivo.getBytes());
		
		ResultActions result =
				mockMvc.perform(multipart("/corridas")
						.file(arquivoTexto)
						.contentType(MediaType.APPLICATION_JSON_VALUE));
				
				result.andExpect(status().isUnsupportedMediaType());	
	}
	
	
}
