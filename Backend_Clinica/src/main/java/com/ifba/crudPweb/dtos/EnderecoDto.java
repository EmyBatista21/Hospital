package com.ifba.crudPweb.dtos;

import com.ifba.crudPweb.models.Endereco;
import com.ifba.crudPweb.models.Especialidade;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDto(@NotBlank String logradouro, @NotNull int numeroCasa,
						String complemento, @NotBlank String bairro, @NotBlank String cidade,
						@NotBlank String uf, @NotBlank String cep) {
}
