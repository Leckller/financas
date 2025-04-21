<template>
  <article>
    <section style="display: flex; align-items: center; gap: 8px;">
      <h2>{{ props.name }}</h2>
      <button @click="handleDelete">Apagar</button>
      <button @click="handleEdit">Editar</button>
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
