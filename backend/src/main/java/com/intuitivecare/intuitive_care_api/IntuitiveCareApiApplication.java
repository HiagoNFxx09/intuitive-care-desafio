package com.intuitivecare.intuitive_care_api;

import com.intuitivecare.intuitive_care_api.service.OperadoraService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IntuitiveCareApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntuitiveCareApiApplication.class, args);
        System.out.println("--- RODANDO COM SUCESSO ---");
    }

    @Bean
    CommandLineRunner run(OperadoraService service) {
        return args -> {
            System.out.println("Iniciando importação dos arquivos ANS...");
            
            
            service.processarArquivosAns("./dados_ans");
            
            System.out.println("Processamento concluído! Verifique o banco de dados e o arquivo consolidado.");
        };
    }
}