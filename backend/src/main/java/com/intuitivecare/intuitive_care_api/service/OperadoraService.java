package com.intuitivecare.intuitive_care_api.service;

import com.intuitivecare.intuitive_care_api.model.Operadora;
import com.intuitivecare.intuitive_care_api.repository.OperadoraRepository;
import com.intuitivecare.intuitive_care_api.util.ValidadorDados;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor 
public class OperadoraService {

    private final OperadoraRepository repository;

   
    public Page<Operadora> listarTodas(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Operadora> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void salvarSeValido(Operadora operadora) {
        if (ValidadorDados.validar(operadora)) {
            repository.save(operadora);
        } else {
            
            System.out.println("Registro inválido descartado: " + operadora.getCnpj());
        }
    }
}