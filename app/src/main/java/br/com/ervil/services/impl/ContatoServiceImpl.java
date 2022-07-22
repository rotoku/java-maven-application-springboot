package br.com.ervil.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ervil.exceptions.ContatoNotFoundException;
import br.com.ervil.models.Contato;
import br.com.ervil.repositories.ContatoRepository;
import br.com.ervil.services.ContatoService;

@Service("toDoService")
public class ContatoServiceImpl implements ContatoService {
	@Autowired
	private ContatoRepository repository;

	@Override
	public void create(Contato contato) {
		repository.saveAndFlush(contato);
	}

	@Override
	public Contato retrieve(Long codigo) {
		Optional<Contato> optional = repository.findById(codigo);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			String message = String.format("Contato \"%d\" not found!", codigo);
			throw new ContatoNotFoundException(message);
		}
	}

	@Override
	public void update(Long codigo, Contato contato) {
		retrieve(codigo);
		create(contato);		
	}

	@Override
	public void delete(Long codigo) {
		Contato contato = retrieve(codigo);
		repository.delete(contato);			
	}

	@Override
	public List<Contato> list() {
		return repository.findAll();
	}

}
