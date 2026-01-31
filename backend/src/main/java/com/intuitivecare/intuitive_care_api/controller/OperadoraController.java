
package com.intuitivecare.intuitive_care_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.intuitivecare.intuitive_care_api.model.Operadora;
import com.intuitivecare.intuitive_care_api.repository.OperadoraRepository;

import lombok.RequiredArgsConstructor;

/**
 * Controller responsável por expor os endpoints da API de Operadoras.
 * Atende aos requisitos de listagem, busca detalhada e análise estatística (Item 4 do teste).
 */
@RestController
@RequestMapping("/api/operadoras")
@RequiredArgsConstructor // Injeção de dependência via construtor (Boa prática: Imutabilidade)
@CrossOrigin(origins = "*") // Trade-off: Permite integração com o Frontend em portas diferentes (ex: Vue 5173)
public class OperadoraController {

    private final OperadoraRepository repository;

    /**
     * Endpoint para listagem paginada de operadoras.
     * Trade-off (Item 4.3.3): Paginação no Servidor.
     * Justificativa: Dado o alto volume de dados da ANS, a paginação evita sobrecarga 
     * no navegador do cliente e reduz o tráfego de rede.
     */
    @GetMapping
    public ResponseEntity<Page<Operadora>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        // Cria o objeto de paginação do Spring Data
        Pageable paging = PageRequest.of(page, size);
        
        // Retorna um objeto Page que contém os dados e metadados (total de páginas, etc)
        return ResponseEntity.ok(repository.findAll(paging));
    }

    /**
     * Endpoint para gerar dados do Dashboard.
     * Trade-off (Item 4.2.3): Agregação no Banco de Dados.
     * Justificativa: O cálculo de soma por UF é feito via SQL (SUM/GROUP BY).
     * É mais performático delegar isso ao MySQL do que processar milhares de linhas no Java.
     */
    @GetMapping("/estatisticas")
    public List<Map<String, Object>> obterEstatisticas() {
        // Retorna a lista de mapas diretamente para o JSON esperado pelo Chart.js no Frontend
        return repository.buscarGastosPorUf();
    }

    /**
     * Endpoint para busca individual por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Operadora> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok) // Se encontrar, retorna 200 OK
                .orElse(ResponseEntity.notFound().build()); // Se não encontrar, retorna 404 Not Found
    }
}