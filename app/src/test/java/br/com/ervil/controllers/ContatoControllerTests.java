package br.com.ervil.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ervil.models.Contato;
import br.com.ervil.repositories.ContatoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ContatoControllerTests {
	@MockBean
	private ContatoRepository contatoRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private ObjectMapper mapper;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void create() throws Exception {
		Contato beltrano = getContatoBeltrano();

		Mockito.when(contatoRepository.saveAndFlush(beltrano)).thenReturn(beltrano);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/v1/contatos")
				.content(asJsonString(beltrano))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());
	}
	
	@Test
	public void retrieve() throws Exception {
		Integer codigo = 1;
		Mockito.when(contatoRepository.findById(codigo)).thenReturn(Optional.of(getContatoBeltrano()));
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/contatos/{codigo}", codigo).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(codigo));
	}

	@Test
	public void update() throws Exception {
		Integer codigo = 1;
		Contato beltrano = getContatoBeltrano();
		Mockito.when(contatoRepository.findById(codigo)).thenReturn(Optional.of(beltrano));
		Mockito.when(contatoRepository.saveAndFlush(beltrano)).thenReturn(beltrano);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/contatos/{codigo}", codigo)
				.content(asJsonString(beltrano)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	public void delete() throws Exception {
		Integer codigo = 2;
		Contato beltrano = getContatoBeltrano();
		Mockito.when(contatoRepository.findById(codigo)).thenReturn(Optional.of(beltrano));
		Mockito.doNothing().when(contatoRepository).delete(beltrano);
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/v1/contatos/{codigo}", codigo).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNoContent());
	}
	
	@Test
	public void list() throws Exception {
		Mockito.when(contatoRepository.findAll()).thenReturn(getContatos());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contatos").accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	private List<Contato> getContatos() {
		List<Contato> contatos = List.of(
				new Contato(1, "Beltrano", "(37) 99999-9999", LocalDate.of(1985, 6, 3)),
				new Contato(2, "Fulano", "(11) 99999-9999", LocalDate.of(1994, 1, 29)),
				new Contato(3, "Sicrano", "(21) 99999-9999", LocalDate.of(1995, 8, 15)));
		return contatos;
	}
	
	private Contato getContatoBeltrano() {
		return new Contato(1, "Beltrano", "(37) 99999-9999", LocalDate.of(1985, 6, 3));
	}
	
	private String asJsonString(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception error) {
			throw new RuntimeException(error);
		}
	}
	
}
