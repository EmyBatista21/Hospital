package com.ifba.crudPweb.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import com.ifba.crudPweb.models.PacienteModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Consultas")

public class Consulta extends RepresentationModel<Consulta> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "paciente_id", nullable = false)
    private PacienteModel paciente;
	
	@ManyToOne
	@JoinColumn(name = "medico_id", nullable = false)
    private MedicoModel medico;
	
    private LocalDateTime dataHora;
    
    private Boolean cancelado; 
    
    public Consulta() {
    	this.cancelado = false; 
    }
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
	public PacienteModel getPaciente() {
		return paciente;
	}
	public void setPaciente(PacienteModel paciente) {
		this.paciente = paciente;
	}
	
	public MedicoModel getMedico() {
		return medico;
	}
	public void setMedico(MedicoModel medico) {
		this.medico = medico;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public void setCancelado(boolean status) {
		this.cancelado = status; 
	}
	public Boolean getCancelado() {
		return cancelado;
	}
	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}
	
	
}
