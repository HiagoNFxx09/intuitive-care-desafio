package com.intuitivecare.intuitive_care_api.util;

import java.math.BigDecimal;
import com.intuitivecare.intuitive_care_api.model.Operadora;

public class ValidadorDados { 

    
    public static boolean validar(Operadora op){
        return isCnpjValido(op.getCnpj()) && 
               isRazaoSocialValida(op.getRazaoSocial()) && 
               isValorPositivo(op.getValorDespesas());
    }

    private static boolean isRazaoSocialValida(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    private static boolean isValorPositivo(BigDecimal valor) {
        return valor != null && valor.compareTo(BigDecimal.ZERO) >= 0;
    }

    private static boolean isCnpjValido(String cnpj){
        if (cnpj == null) return false;
    
        String cleanCnpj = cnpj.replaceAll("\\D", ""); 
        return cleanCnpj.length() == 14;
    }
}