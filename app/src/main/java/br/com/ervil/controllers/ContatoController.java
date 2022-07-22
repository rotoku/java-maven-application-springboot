package br.com.ervil.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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
import br.com.ervil.repositories.ContatoRepository;

@RestController
@RequestMapping("/api")
public class ContatoController {
	@Autowired
	private ContatoRepository contatoRepository;
	
	@PostMapping(path="/v1/contatos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody @Valid ContatoDTO contatoDTO, UriComponentsBuilder uriComponentsBuilder) {
		Contato contato = dto2Entity(contatoDTO);
		contatoRepository.saveAndFlush(contato);
		URI location = uriComponentsBuilder.path("/api/v1/contatos/{codigo}").buildAndExpand(contato.getCodigo()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path="/v1/contatos/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContatoDTO> retrieve(@PathVariable("codigo") Integer codigo) {
		Optional<Contato> optional = contatoRepository.findById(codigo);
		if(optional.isPresent()) {
			ContatoDTO contatoDTO = entity2DTO(optional.get());
			return ResponseEntity.ok(contatoDTO);	
		}else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	@PutMapping(path="/v1/contatos/{codigo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@PathVariable("codigo") Integer codigo, @RequestBody @Valid ContatoDTO contatoDTO) {
		Contato contato = null;
		Optional<Contato> optional = contatoRepository.findById(codigo);
		if(optional.isPresent()) {
			contato = optional.get();
		}else {
			return ResponseEntity.notFound().build();
		}
		
		contato.setNome(contatoDTO.getNome());
		contato.setTelefone(contatoDTO.getTelefone());
		contato.setDataNascimento(contatoDTO.getDataNascimento());
		
		contatoRepository.saveAndFlush(contato);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(path="/v1/contatos/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable("codigo") Integer codigo) {
		Contato contato = null;
		Optional<Contato> optional = contatoRepository.findById(codigo);		
		if(optional.isPresent()) {
			contato = optional.get();
		}else {
			return ResponseEntity.notFound().build();
		}		
		contatoRepository.delete(contato);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(path="/v1/contatos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContatoDTO>> list(){
		List<ContatoDTO> contatos = contatoRepository.findAll().stream().map(c -> entity2DTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok(contatos);
	}
	
	private ContatoDTO entity2DTO(Contato contato){
		return new ContatoDTO(contato.getCodigo(), contato.getNome(), contato.getTelefone(), contato.getDataNascimento());
	}
	
	private Contato dto2Entity(ContatoDTO contatoDTO){
		return new Contato(contatoDTO.getCodigo(), contatoDTO.getNome(), contatoDTO.getTelefone(), contatoDTO.getDataNascimento());
	}
	
}
