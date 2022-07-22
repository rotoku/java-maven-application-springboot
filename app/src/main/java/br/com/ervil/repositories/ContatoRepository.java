package br.com.ervil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ervil.models.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {

}
