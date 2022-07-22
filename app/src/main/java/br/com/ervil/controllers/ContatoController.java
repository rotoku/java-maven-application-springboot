package br.com.ervil.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ervil.dtos.ContatoDTO;
import br.com.ervil.models.Contato;
import br.com.ervil.services.ContatoService;

@RestController
@RequestMapping("/api")
public class ContatoController {
	@Autowired
	private ContatoService service;
	
	@PostMapping(path="/v1/contatos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody @Valid ContatoDTO contatoDTO, UriComponentsBuilder uriComponentsBuilder) {
		Contato contato = contatoDTO.converter();
		service.create(contato);
		URI location = uriComponentsBuilder.path("/api/v1/contatos/{id}").buildAndExpand(contatoDTO.getCodigo()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path="/v1/contatos/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContatoDTO> retrieve(@PathVariable("codigo") Long codigo) {
		ContatoDTO contatoDTO = new ContatoDTO(service.retrieve(codigo));
		return ResponseEntity.ok(contatoDTO);
	}
	
	@PutMapping(path="/v1/contatos/{codigo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@PathVariable("codigo") Long codigo, @RequestBody @Valid ContatoDTO contatoDTO) {
		Contato contato = contatoDTO.converter();
		service.update(codigo, contato);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(path="/v1/contatos/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable("codigo") Long codigo) {
		service.delete(codigo);
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping(path="/v1/contatos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContatoDTO>> list(){
		List<ContatoDTO> contatos = service.list().stream().map(ContatoDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok(contatos);
	}
	
}
