package com.ifba.crudPweb.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ConsultaDTO {

    private Long pacienteId;
    private Long medicoId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;
    private Boolean cancelado; 
    
	public Long getPacienteId() {
		return pacienteId;
	}
	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}
	public Long getMedicoId() {
		return medicoId;
	}
	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}
	
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public Boolean getCancelado() {
		return cancelado;
	}
	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

}
