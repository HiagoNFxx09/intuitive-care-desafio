<script setup>
import { ref, watch } from 'vue'
import api from '../api/api'

const operadoras = ref([])
const pagina = ref(0)
const totalPaginas = ref(0)
const filtro = ref('')

const carregar = async () => {
  try {
    const res = await api.get('/operadoras', {
      params: { 
        page: pagina.value, 
        size: 10, 
        filtro: filtro.value 
      }
    })
    operadoras.value = res.data.content || []
    totalPaginas.value = res.data.totalPages || 0
  } catch (error) {
    console.error(error)
  }
}

watch(filtro, () => {
  pagina.value = 0
})

watch([pagina, filtro], carregar, { immediate: true })
</script>

<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Operadoras de Saúde</h2>
      <router-link to="/estatisticas" class="btn btn-info">Ver Estatísticas</router-link>
    </div>

    <input
      v-model="filtro"
      class="form-control mb-3"
      placeholder="Buscar por CNPJ ou Razão Social"
    />

    <table class="table table-striped table-bordered shadow-sm">
      <thead class="table-dark">
        <tr>
          <th>CNPJ</th>
          <th>Razão Social</th>
          <th>UF</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="op in operadoras" :key="op.id">
          <td>{{ op.cnpj }}</td>
          <td>{{ op.razaoSocial }}</td>
          <td>{{ op.uf }}</td>
        </tr>
        <tr v-if="operadoras.length === 0">
          <td colspan="3" class="text-center">Nenhum registro encontrado.</td>
        </tr>
      </tbody>
    </table>

    <div class="d-flex justify-content-between align-items-center mt-3" v-if="totalPaginas > 0">
      <button class="btn btn-secondary" @click="pagina--" :disabled="pagina === 0">Anterior</button>
      <span>Página {{ pagina + 1 }} de {{ totalPaginas }}</span>
      <button class="btn btn-secondary" @click="pagina++" :disabled="pagina >= totalPaginas - 1">Próxima</button>
    </div>
  </div>
</template>