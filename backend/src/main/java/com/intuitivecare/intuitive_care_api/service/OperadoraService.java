package com.intuitivecare.intuitive_care_api.service;

import com.intuitivecare.intuitive_care_api.model.Operadora;
import com.intuitivecare.intuitive_care_api.repository.OperadoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service responsável pela lógica de negócio pesada: Ingestão, Limpeza e Consolidação.
 * Atende aos requisitos de Transformação de Dados (Item 2) e Banco de Dados (Item 3).
 */
@Service
@RequiredArgsConstructor
public class OperadoraService {

    private final OperadoraRepository repository;

    public List<Operadora> listarTodas() {
        return repository.findAll();
    }

    /**
     * Processa os arquivos CSV da ANS, realiza o parse, salva no DB e gera o consolidado.
     * Trade-off Técnico (Item 2.1): Uso de BufferedReader (Streaming de arquivos).
     * Justificativa: Evita o carregamento de arquivos gigantes em memória (OutOfMemoryError).
     */
    public void processarArquivosAns(String caminhoPasta) {
        File pasta = new File(caminhoPasta);
        // Filtra apenas arquivos .csv (Robustez)
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".csv"));
        if (arquivos == null) return;

        // Try-with-resources para garantir o fechamento do arquivo consolidado ao final
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./consolidado_despesas.csv"))) {
            // Escreve o cabeçalho conforme exigido no item 2.3
            writer.write("CNPJ;RazaoSocial;UF;ValorDespesas");
            writer.newLine();

            for (File arquivo : arquivos) {
                // ISO-8859-1 é o padrão comum nos arquivos da ANS (Evita erros de acentuação)
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "ISO-8859-1"))) {
                    String linha;
                    boolean primeiraLinha = true;
                    
                    while ((linha = br.readLine()) != null) {
                        // Ignora o cabeçalho do CSV original e linhas vazias
                        if (primeiraLinha || linha.trim().isEmpty()) { 
                            primeiraLinha = false; 
                            continue; 
                        }
                        
                        String[] dados = linha.split(";");
                    
                        // Validação de integridade da linha
                        if (dados.length >= 9) { 
                            Operadora op = new Operadora();
                            
                            // Limpeza de dados: Removendo aspas extras que vêm no CSV da ANS
                            op.setCnpj(dados[2].replace("\"", ""));
                            op.setRazaoSocial(dados[3].replace("\"", ""));
                            
                            // Captura da UF (Enriquecimento de dados solicitado no item 2.2)
                            String uf = dados[0].replace("\"", "").trim(); 
                            op.setUf(uf);

                            // Tratamento Numérico (Item 3.3):
                            // Converte o formato monetário brasileiro (1.500,50) para o padrão BigDecimal (1500.50)
                            String valorRaw = dados[8].replace("\"", "").replace(".", "").replace(",", ".");
                            BigDecimal valor = new BigDecimal(valorRaw);
                            op.setValorDespesas(valor);

                            // Persistência no Banco de Dados (Item 3.2)
                            repository.save(op);

                            // Escrita no arquivo de saída (Item 2.3 - Consolidação)
                            writer.write(op.getCnpj() + ";" + op.getRazaoSocial() + ";" + op.getUf() + ";" + op.getValorDespesas());
                            writer.newLine();
                        }
                    }
                }
            }
        } catch (Exception e) { 
            // Tratamento de erros de IO conforme exigido na análise de robustez
            e.printStackTrace(); 
        }
    }
}