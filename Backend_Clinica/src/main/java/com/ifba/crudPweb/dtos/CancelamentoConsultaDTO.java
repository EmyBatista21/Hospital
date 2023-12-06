package com.ifba.crudPweb.dtos;

import com.ifba.crudPweb.models.MotivoCancelamento;

public class CancelamentoConsultaDTO {
    private MotivoCancelamento motivoCancelamento;

    public MotivoCancelamento getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(MotivoCancelamento motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }
}
