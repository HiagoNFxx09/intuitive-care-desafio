package com.intuitivecare.intuitive_care_api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intuitivecare.intuitive_care_api.model.Operadora;

public interface OperadoraRepository extends JpaRepository<Operadora, Long> {
    @Query("SELECT new map(o.uf as uf, SUM(o.valorDespesas) as total) FROM Operadora o GROUP BY o.uf")
    List<Map<String, Object>> buscarGastosPorUf();

}
