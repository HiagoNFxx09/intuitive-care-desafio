<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/api' 
const registros = ref([]) 
const carregando = ref(true) 

onMounted(async () => {
  try {
  
    const res = await api.get('/operadoras')
    
  
    registros.value = res.data.content || []
    
    console.log("Dados carregados com sucesso:", registros.value)
  } catch (error) {
    console.error("Erro ao carregar lista:", error)
  } finally {
    carregando.value = false
  }
})
</script>