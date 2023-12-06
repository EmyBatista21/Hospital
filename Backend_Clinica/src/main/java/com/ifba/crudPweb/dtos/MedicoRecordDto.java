package com.ifba.crudPweb.dtos;

import com.ifba.crudPweb.models.Endereco;
import com.ifba.crudPweb.models.Especialidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoRecordDto(@NotBlank String nome, @NotBlank String email, 
							String numero, @NotBlank String crm, 
							@NotNull Especialidade especialidade, 
							@NotNull Endereco endereco, Boolean ativo) {

	public String getNome() {
		return this.nome;
	}

	public String getNumero() {
		return this.numero;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

}