package br.com.ervil.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContatoDTO {
	private Integer codigo;
	private String nome;
	private String telefone;
	@JsonProperty("data_nascimento")
	private LocalDate dataNascimento;
	
	public ContatoDTO() {}

	public ContatoDTO(Integer codigo, String nome, String telefone, LocalDate dataNascimento) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

}
