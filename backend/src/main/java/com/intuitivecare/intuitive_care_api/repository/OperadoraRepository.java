package com.intuitivecare.intuitive_care_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intuitivecare.intuitive_care_api.model.Operadora;

public interface OperadoraRepository extends JpaRepository<Operadora, Long> {
    @Query("SELECT o.uf, SUM(o.valorDespesas) FROM Operadora o GROUP BY o.uf")
    List<Object[]> buscarGastosPorUf();

}
