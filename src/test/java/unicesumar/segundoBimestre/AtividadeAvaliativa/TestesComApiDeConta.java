package unicesumar.segundoBimestre.AtividadeAvaliativa;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import unicesumar.segundoBimestre.conta.Conta;
import unicesumar.segundoBimestre.conta.ContaController;
import unicesumar.segundoBimestre.conta.ContaService;
import unicesumar.segundoBimestre.conta.NotFoundException;

@WebMvcTest
@AutoConfigureMockMvc
class TestesComApiDeConta {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ContaController controller;
	
	@MockBean
	private ContaService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	

	@Test
	void testandoGetByIdComDadoInexistente() throws Exception {
		when(service.getById("2")).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/api/Contas/2")).andExpect(status().isNotFound());
	}
	
	@Test
	void testandoGetByIdComDadoExistente() throws Exception {
		Conta existente = new Conta("1", 999, 999d);		
		when(service.getById("1")).thenReturn(existente);
		
		mockMvc.perform(get("/api/Contas/1"))
		.andExpect(jsonPath("$.id").value("1"))
		.andExpect(jsonPath("$.numeroConta").value(999))
		.andExpect(jsonPath("$.saldo").value(999d))
		.andExpect(status().isOk());
	}
	
	@Test
	void testandoGetAll() throws Exception {
		Conta a = new Conta("1", 100, 100d);		
		Conta b = new Conta("2", 200, 200d);		
		Conta c = new Conta("3", 300, 300d);		
		when(service.getAll()).thenReturn(Arrays.asList(a,b,c));
		
		mockMvc.perform(get("/api/Contas"))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$.[0].id").value("1"))
		.andExpect(jsonPath("$.[1].id").value("2"))
		.andExpect(jsonPath("$.[2].id").value("3"))
		.andExpect(jsonPath("$.[0].numeroConta").value(100))
		.andExpect(jsonPath("$.[1].numeroConta").value(200))
		.andExpect(jsonPath("$.[2].numeroConta").value(300))
		.andExpect(jsonPath("$.[0].numeroDePaginas").value(100d))
		.andExpect(jsonPath("$.[1].numeroDePaginas").value(200d))
		.andExpect(jsonPath("$.[2].numeroDePaginas").value(300d))
		.andExpect(status().isOk());
	}
	@Test
	void testandoPost() throws Exception {
		when(service.save(ArgumentMatchers.any(Conta.class))).thenReturn("99");
		
		Map<String, String> Conta  = new HashMap<String, String>() {{
		    put("id", "99");
		    put("numeroConta", 999);
		    put("saldo", 999d);
		}

		private void put(String key, double d) {
			
			
		}};
		
		String ContaComoJson = objectMapper.writeValueAsString(Conta);
		
		mockMvc.perform(post("/api/Contas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ContaComoJson))
		.andExpect(status().isCreated())
		.andExpect(content().string("99"));
	}
	

}







