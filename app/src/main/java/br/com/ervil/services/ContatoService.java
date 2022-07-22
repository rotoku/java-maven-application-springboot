package br.com.ervil.services;

import java.util.List;

import br.com.ervil.models.Contato;

public interface ContatoService {
	public void create(Contato contato);

	public Contato retrieve(Long codigo);

	public void update(Long codigo, Contato contato);

	public void delete(Long codigo);

	public List<Contato> list();
}
