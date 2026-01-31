import { createRouter, createWebHistory } from 'vue-router'
import Operadoras from '../views/operadoras.vue'
import Estatisticas from '../views/estatisticas.vue'

const routes = [
  { path: '/', component: Operadoras },
  { path: '/estatisticas', component: Estatisticas }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router