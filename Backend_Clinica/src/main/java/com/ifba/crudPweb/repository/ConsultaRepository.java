package com.ifba.crudPweb.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ifba.crudPweb.models.Consulta;
import com.ifba.crudPweb.models.MedicoModel;
import com.ifba.crudPweb.models.PacienteModel;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consulta c " +
	           "WHERE c.paciente = :paciente AND DATE(c.dataHora) = :dataConsulta")
	    boolean existsByPacienteAndDataConsulta(@Param("paciente") PacienteModel paciente,
	                                            @Param("dataConsulta") LocalDate dataConsulta);
	
	

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consulta c " +
	           "WHERE c.medico = :medico AND DATE(c.dataHora) = :dataConsulta")
	boolean existsByMedicoAndDataHoraConsulta(@Param("medico") MedicoModel medico,
            @Param("dataConsulta") LocalDateTime dataHoraConsulta);
	
	@Query(value = "SELECT * FROM Consultas c WHERE c.cancelado = false", nativeQuery = true)
    List<Consulta> findByCanceladoFalse();

}
