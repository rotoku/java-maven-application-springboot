package br.com.ervil.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CONTATO")
public class Contato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	private String nome;
	private String telefone;
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	public Contato() {
	}

	public Contato(String nome, String telefone, LocalDate dataNascimento) {
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public LocalDate getDataNascimento() {
		return this.dataNascimento;
	}

}
