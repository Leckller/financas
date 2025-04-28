<template>
  <article>
    <section>
      <h2>{{ props.name }}</h2>
      <div class="buttons">
        <button class="edit-button" @click="handleEdit">Editar</button>
        <button class="delete-button" @click="handleDelete">Apagar</button>
      </div>
    </section>

    <p>R$ {{ props.amount }}</p>
    <small>{{ props.createdAt }}</small>
  </article>
</template>

<script setup>
import Swal from 'sweetalert2'
import { defineProps } from 'vue'
import deleteTransaction from '@/service/Transaction/deleteTransaction'
import { transactionStore } from '@/stores/transaction'

const transaction = transactionStore()

const props = defineProps({
  id: Number,
  name: String,
  amount: Number,
  createdAt: String,
  updatedAt: String
})

const handleDelete = async () => {
  const result = await Swal.fire({
    title: `Tem certeza que quer apagar a transação "${props.name}"?`,
    text: 'Essa ação não pode ser revertida!',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    cancelButtonText: 'Cancelar',
    confirmButtonText: 'Sim, exclua-o!'
  })

  if (result.isConfirmed) {
    try {
      await deleteTransaction(props.id)

      // Reverter o efeito da transação no saldo
      transaction.balance -= props.amount

      // Remover do estado local
      transaction.deleteTransaction(props.id)

      Swal.fire({
        title: 'Deletado!',
        text: 'A transação foi deletada com sucesso.',
        icon: 'success'
      })
    } catch (error) {
      console.log(error)
    }
  }
}

const handleEdit = async () => {
  // Em breve 😎
}
</script>

<style scoped>

article {
  background-color: var(--bg1);
  padding: 1rem;
  border-radius: 10px;
  width: 100%;
  max-width: 300px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  color: var(--c5);
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  transition: transform 0.2s, box-shadow 0.2s;
}

article:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

section {
  display: flex;
  align-items: center;
  gap: 16px;
  justify-content: space-between;
}

h2 {
  font-size: 1.6rem;
  font-weight: 600;
  color: var(--c5);
  margin: 0;
}

p {
  font-size: 1.4rem;
  font-weight: bold;
  color: var(--c5);
  margin: 0;
}

small {
  font-size: 0.9rem;
  color: var(--c5);
}

.buttons {
  display: flex;
  gap: 0.5rem;
}

button {
  background-color: var(--bg4);
  border: none;
  color: white;
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

button:hover {
  background-color: var(--bg3);
}

/* Estilo específico para os botões de apagar e editar */
.delete-button {
  background-color: #dc3545;
}

.delete-button:hover {
  background-color: #bd2130;
}

.edit-button {
  background-color: #28a745;
}

.edit-button:hover {
  background-color: #218838;
}

</style>
