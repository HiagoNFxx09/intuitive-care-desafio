package com.intuitivecare.intuitive_care_api.util;

import java.math.BigDecimal;
import com.intuitivecare.intuitive_care_api.model.Operadora;

public class ValidadorDados { // Nome da classe com inicial Maiúscula

    // 1. Método principal: agora chamando os nomes corretos dos métodos abaixo
    public static boolean validar(Operadora op){
        return isCnpjValido(op.getCnpj()) && 
               isRazaoSocialValida(op.getRazaoSocial()) && // Nome corrigido
               isValorPositivo(op.getValorDespesas());
    }

    // 2. Método com nome corrigido para bater com a chamada acima
    private static boolean isRazaoSocialValida(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    private static boolean isValorPositivo(BigDecimal valor) {
        return valor != null && valor.compareTo(BigDecimal.ZERO) >= 0;
    }

    private static boolean isCnpjValido(String cnpj){
        if (cnpj == null) return false;
        // CORREÇÃO: replaceAll é necessário para usar a Regex \\D (tudo que não é dígito)
        String cleanCnpj = cnpj.replaceAll("\\D", ""); 
        return cleanCnpj.length() == 14;
    }
}