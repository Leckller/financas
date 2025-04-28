<template>
    <button @click="handleCreateTransaction">Nova Transação</button>
</template>

<script setup>
import createTransaction from '@/service/Transaction/createTransaction'
import { transactionStore } from '@/stores/transaction'
import Swal from 'sweetalert2'
import { reactive } from 'vue'

const transaction = transactionStore()

const form = reactive({
  name: '',
  amount: 0
})

const handleCreateTransaction = async () => {
  let transactionType = 'income' // income = receita, expense = despesa

  const { value: formValues } = await Swal.fire({
    title: 'Nova Transação',
    html: `
      <div class="swal-form-fields">
        <input id="swal-input-name" class="swal2-input" placeholder="Nome da transação">
        <input id="swal-input-amount" min="1" type="number" class="swal2-input" placeholder="Valor">
      </div>
      <div class="swal-form-type">
        <button type="button" id="btn-income" class="swal2-confirm btn swal2-styled" style="background-color: #28a745;">Receita</button>
        <button type="button" id="btn-expense" class="swal2-confirm btn swal2-styled" style="background-color: #dc3545;">Despesa</button>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: 'Salvar',

    didOpen: () => {
      const incomeBtn = document.getElementById('btn-income')
      const expenseBtn = document.getElementById('btn-expense')

      incomeBtn.addEventListener('click', () => {
        transactionType = 'income'
        incomeBtn.style.opacity = '1'
        expenseBtn.style.opacity = '0.5'
      })

      expenseBtn.addEventListener('click', () => {
        transactionType = 'expense'
        expenseBtn.style.opacity = '1'
        incomeBtn.style.opacity = '0.5'
      })

      // Deixa o botão receita como selecionado inicialmente
      document.getElementById('btn-income').style.opacity = '1'
      document.getElementById('btn-expense').style.opacity = '0.5'
    },

    preConfirm: () => {
      const name = document.getElementById('swal-input-name').value
      let amount = parseFloat(document.getElementById('swal-input-amount').value)

      if (isNaN(amount) || amount < 1) {
        Swal.showValidationMessage('O valor deve ser um número positivo.')
        return
      }

      if (!name) {
        Swal.showValidationMessage('O nome da transação é obrigatório.')
        return
      }

      // Se for despesa, transforma o valor em negativo
      if (transactionType === 'expense') {
        amount = -Math.abs(amount)
      } else {
        amount = Math.abs(amount)
      }

      return { name, amount }
    }
  })

  if (formValues) {
    form.name = formValues.name
    form.amount = formValues.amount
    const addTransaction = await createTransaction({ name: formValues.name, amount: formValues.amount })
    transaction.addTransaction(addTransaction)
    transaction.setBalance(transaction.balance + form.amount)
  }
}

</script>

<style>

.swal-form-fields {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    overflow-x: hidden;
    gap: 8px;
    width: 100%;
  }

.swal-form-type {
  display: flex;
  flex-direction: row;
  overflow-x: hidden;
  width: 100%;
}

.swal-form-fields input {
  width: 100%;
  margin: 0;
}

.swal-form-type button {
  width: 100%;
}

@media (max-width: 315px) {
  .swal-form-type {
    flex-wrap: wrap;
  }
}

</style>
