package com.intuitivecare.intuitive_care_api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CORREÇÃO DOS IMPORTS: Use sempre os do org.springframework.data.domain
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; // Adicionado
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.intuitivecare.intuitive_care_api.model.Operadora;
import com.intuitivecare.intuitive_care_api.repository.OperadoraRepository;

import lombok.RequiredArgsConstructor; // Dica: use isso com o final

@RestController
@RequestMapping("/api/operadoras")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OperadoraController {

    private final OperadoraRepository repository;

    @GetMapping
    public ResponseEntity<Page<Operadora>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Operadora> resultado = repository.findAll(paging);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operadora> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estatisticas")
    public List<Map<String, Object>> obterEstatisticas() {
        List<Object[]> resultados = repository.buscarGastosPorUf();
        List<Map<String, Object>> listaFinal = new ArrayList<>();

        for (Object[] linha : resultados) {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("uf", linha[0]);
            mapa.put("total", linha[1]);
            listaFinal.add(mapa);
        }

        return listaFinal;
    }
}