<script setup>
import { ref, onMounted } from 'vue'
import Chart from 'chart.js/auto' 
import api from '../api/api'

const dados = ref([])
const carregando = ref(true)
const chartCanvas = ref(null) 

const carregarEstatisticas = async () => {
  try {
    const res = await api.get('/operadoras/estatisticas')
    dados.value = res.data
    
    // Após carregar os dados, inicializamos o gráfico
    renderizarGrafico(res.data)
  } catch (error) {
    console.error("Erro ao buscar dados:", error)
  } finally {
    carregando.value = false
  }
}

const renderizarGrafico = (data) => {

  setTimeout(() => {
    if (!chartCanvas.value) return;
    
    new Chart(chartCanvas.value, {
      type: 'bar',
      data: {
        labels: data.map(item => item.uf),
        datasets: [{
          label: 'Total de Gastos (R$)',
          data: data.map(item => item.total),
          backgroundColor: '#0d6efd',
          borderRadius: 5
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: false }
        }
      }
    })
  }, 100)
}

onMounted(carregarEstatisticas)
</script>

<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>📊 Dashboard de Gastos por UF</h2>
      <router-link to="/" class="btn btn-secondary">Voltar para Lista</router-link>
    </div>

    <div v-if="carregando" class="text-center py-5">
      <div class="spinner-border text-primary" role="status"></div>
      <p>Processando dados da ANS...</p>
    </div>

    <div v-else class="row">
      <div class="col-md-7 mb-4">
        <div class="card p-3 shadow-sm">
          <canvas ref="chartCanvas"></canvas>
        </div>
      </div>

      <div class="col-md-5">
        <div class="table-responsive">
          <table class="table table-hover border">
            <thead class="table-primary">
              <tr>
                <th>Estado</th>
                <th class="text-end">Total</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in dados" :key="item.uf">
                <td><strong>{{ item.uf }}</strong></td>
                <td class="text-end">R$ {{ Number(item.total).toLocaleString('pt-BR', { minimumFractionDigits: 2 }) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>