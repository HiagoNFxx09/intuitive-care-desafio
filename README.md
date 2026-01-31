🏥 Intuitive Care - Processamento de Dados ANS
 A aplicação automatiza a ingestão, limpeza, consolidação e visualização de dados contábeis de operadoras de saúde suplementar (ANS).

🛠️ Tecnologias e Decisões Técnicas (Trade-offs)
Conforme solicitado nas diretrizes do desafio, abaixo estão as fundamentações para as escolhas de implementação:

1. Ingestão de Dados e Performance (Item 2.1)
Trade-off: Uso de BufferedReader e InputStreamReader (Streaming).

Justificativa: Dado que os arquivos da ANS podem ser volumosos, carregar todo o conteúdo em memória de uma vez causaria um OutOfMemoryError. O processamento linha a linha garante que o sistema utilize memória constante, independentemente do tamanho do arquivo (Escalabilidade).

2. Transformação e Consolidação (Item 2.3)
Trade-off: Consolidação "On-the-fly" durante a leitura.

Justificativa: Em vez de realizar duas passagens nos dados (uma para salvar e outra para gerar o CSV), o sistema processa, limpa e já escreve no arquivo consolidado_despesas.csv simultaneamente. Isso reduz o tempo de I/O em 50%.

3. Banco de Dados e Tipagem (Item 3.3)
Trade-off: Uso de BigDecimal para valores monetários.

Justificativa: Evitei o uso de Float ou Double devido aos problemas conhecidos de arredondamento em cálculos financeiros. O BigDecimal garante a precisão decimal necessária para auditorias contábeis.

4. Estatísticas e Dashboard (Item 4.2.3 e 4.3.3)
Trade-off: Agregação via Query SQL (SUM e GROUP BY) no Banco de Dados.

Justificativa: É significativamente mais performático processar agregados no MySQL do que trazer milhares de registros para o Java ou JavaScript para somar manualmente. Os dados já chegam "mastigados" para o componente de gráfico no Vue.js.

5. Interface e UX (Item 4.3.4)
Análise Crítica: Implementação de estados de Loading e tratamento de erros de rede.

Justificativa: Uma boa UX exige que o usuário saiba o que está acontecendo. O uso de Spinners durante o processamento e mensagens claras para "Nenhum resultado encontrado" evita a percepção de erro ou travamento da aplicação.

📂 Estrutura do Projeto
Plaintext
intuitive-care-api/
├── backend/                # API REST Spring Boot (Java 17+)
│   ├── src/main/java/.../  # Controller, Service, Model e Repository
│   └── dados_ans/          # Pasta para depósito dos arquivos CSV originais
├── frontend/               # SPA em Vue.js 3 (Composition API)
│   ├── src/views/          # Telas de Listagem e Dashboard
│   └── src/api/            # Configuração Axios (BaseURL: 8080)
├── postman/                # Coleção JSON para teste das rotas (Item 4.4)
└── consolidado_despesas.csv # Gerado automaticamente após o processamento

🚀 Como Executar o Projeto
1. Configuração do Backend
Banco de Dados: Crie o schema no MySQL: CREATE DATABASE intuitive_care;.

Arquivos: Insira os CSVs da ANS na pasta backend/dados_ans/.

Config: Ajuste o application.properties com seu usuário e senha do MySQL.

Run: Execute mvn spring-boot:run. O sistema fará a importação e gerará o CSV consolidado na raiz.

2. Configuração do Frontend
Acesse a pasta: cd frontend.

Instale as dependências: npm install.

Inicie a aplicação: npm run dev.

📬 API Documentation (Postman)
Para validar os endpoints conforme o item 4.4 do desafio.

GET /api/operadoras: Listagem paginada.

GET /api/operadoras/estatisticas: Dados para o gráfico de barras.