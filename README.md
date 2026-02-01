# 🏥 Intuitive Care — Processamento de Dados ANS

Aplicação responsável por **automatizar a ingestão, limpeza, consolidação e visualização de dados contábeis** de operadoras de saúde suplementar disponibilizados pela **ANS**.

O projeto foi desenvolvido seguindo as diretrizes do desafio técnico, com foco em **performance, escalabilidade, precisão financeira e boa experiência do usuário**.

---

## 🛠️ Tecnologias Utilizadas

### Backend

* **Java 17+ (usei o java 21)**
* **Spring Boot**
* **MySQL**
* **Maven**

### Frontend

* **Vue.js 3**
* **Composition API**
* **Axios**
* **Vite**

---

## ⚖️ Decisões Técnicas e Trade-offs

### 1️⃣ Ingestão de Dados e Performance (Item 2.1)

**Decisão:**
Uso de `BufferedReader` + `InputStreamReader` (processamento em streaming).

**Justificativa:**
Os arquivos da ANS podem ser muito grandes. Carregar tudo em memória aumentaria o risco de `OutOfMemoryError`.
O processamento linha a linha garante:

* Uso constante de memória
* Melhor escalabilidade
* Maior estabilidade da aplicação

---

### 2️⃣ Transformação e Consolidação dos Dados (Item 2.3)

**Decisão:**
Consolidação **on-the-fly** durante a leitura do arquivo.

**Justificativa:**
Ao invés de:

1. Ler e salvar tudo
2. Ler novamente para consolidar

O sistema:

* Lê
* Limpa
* Processa
* Escreve diretamente no `consolidado_despesas.csv`

📉 **Resultado:** redução de ~50% no tempo de I/O.

---

### 3️⃣ Banco de Dados e Tipagem Numérica (Item 3.3)

**Decisão:**
Uso de `BigDecimal` para valores monetários.

**Justificativa:**
`float` e `double` apresentam problemas de arredondamento em cálculos financeiros.
O `BigDecimal` garante:

* Precisão decimal
* Confiabilidade contábil
* Segurança para auditorias

---

### 4️⃣ Estatísticas e Dashboard (Itens 4.2.3 e 4.3.3)

**Decisão:**
Agregações feitas diretamente no banco via **SQL (`SUM`, `GROUP BY`)**.

**Justificativa:**
É muito mais performático:

* Processar agregações no MySQL
* Do que trazer milhares de registros para Java ou JavaScript

➡️ Os dados já chegam **prontos** para o Vue.js renderizar os gráficos.

---

### 5️⃣ Interface e Experiência do Usuário (Item 4.3.4)

**Análise Crítica:**
Implementação de:

* Estados de **loading**
* Tratamento de erros de rede
* Mensagens claras para ausência de dados

**Justificativa:**
Boa UX evita sensação de travamento ou erro silencioso.
Spinners e feedback visual tornam o sistema mais confiável para o usuário final.

---

## 📂 Estrutura do Projeto

```plaintext
intuitive-care-api/
├── backend/                      # API REST Spring Boot
│   ├── src/main/java/.../         # Controller, Service, Model e Repository
│   └── dados_ans/                # CSVs originais da ANS
│
├── frontend/                     # SPA Vue.js 3
│   ├── src/views/                # Telas de listagem e dashboard
│   └── src/api/                  # Configuração do Axios (BaseURL: 8080)
│
└── consolidado_despesas.csv      # Gerado automaticamente após o processamento
```

---

## 🚀 Como Executar o Projeto

### 🔹 Backend

1. **Banco de Dados**

```sql
CREATE DATABASE intuitive_care;
```

2. **Arquivos CSV**
   Coloque os arquivos da ANS em:

```bash
backend/dados_ans/
```

3. **Configuração**

* Ajuste `application.properties` com usuário e senha do MySQL

4. **Execução**

```bash
mvn spring-boot:run
```

📌 O sistema irá:

* Importar os dados
* Processar os arquivos
* Gerar o `consolidado_despesas.csv` na raiz do projeto

---

### 🔹 Frontend

```bash
cd frontend
npm install
npm run dev
```

A aplicação estará disponível em ambiente de desenvolvimento.

---

## 📬 Documentação da API (Postman)

Coleção disponível na pasta `/postman`, conforme solicitado no item **4.4** do desafio.

### Endpoints Principais

* **GET** `/api/operadoras`
  → Listagem paginada das operadoras

* **GET** `/api/operadoras/estatisticas`
  → Dados consolidados para o gráfico de barras

---

## 📌 Projeto

**Repositório:** `intuitive-care-desafio`

--

