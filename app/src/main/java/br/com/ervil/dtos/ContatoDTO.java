package br.com.ervil.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ervil.models.Contato;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContatoDTO {
	private Long codigo;
	private String nome;
	private String telefone;
	@JsonProperty(value = "data_nascimento")
	private LocalDate dataNascimento;

	public ContatoDTO() {
	}

	public ContatoDTO(Contato contato) {
		this.codigo = contato.getCodigo();
		this.nome = contato.getNome();
		this.telefone = contato.getTelefone();
		this.dataNascimento = contato.getDataNascimento();
	}

	public Contato converter() {
		return new Contato(nome, telefone, dataNascimento);
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
