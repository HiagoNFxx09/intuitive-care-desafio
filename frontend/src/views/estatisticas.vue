<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/api'

const dados = ref([])
const carregando = ref(true)

const carregarEstatisticas = async () => {
  try {
    const res = await api.get('/operadoras/estatisticas')
    dados.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    carregando.value = false
  }
}

onMounted(carregarEstatisticas)
</script>

<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Estatísticas por UF</h2>
      <router-link to="/" class="btn btn-secondary">Voltar para Lista</router-link>
    </div>

    <div v-if="carregando" class="text-center">Carregando...</div>

    <div v-else class="row">
      <div class="col-md-6 mx-auto">
        <table class="table table-hover border">
          <thead class="table-primary">
            <tr>
              <th>Estado (UF)</th>
              <th class="text-end">Total de Gastos</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in dados" :key="item.uf">
              <td>{{ item.uf }}</td>
              <td class="text-end">R$ {{ Number(item.total).toLocaleString('pt-BR', { minimumFractionDigits: 2 }) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>