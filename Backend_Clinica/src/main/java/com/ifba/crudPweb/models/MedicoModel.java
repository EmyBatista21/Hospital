package com.ifba.crudPweb.models;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import com.ifba.crudPweb.dtos.EnderecoDto;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.ifba.crudPweb.models.Especialidade;

@Entity
@Table(name = "Medicos")
public class MedicoModel extends RepresentationModel<MedicoModel>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String email; 
	
	private String numero; 
	
    private String crm; 
    
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade; 
    
    @Embedded
    private Endereco endereco;

    private Boolean ativo;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	} 
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
 
	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
		//BeanUtils.copyProperties(enderecoDto, endereco);
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
