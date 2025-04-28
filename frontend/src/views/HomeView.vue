<template>
  <HeaderComponent :balance="transaction.balance" />

  <main>
    <section id="home-buttons">
      <button @click="handleCreateTransaction">Nova Transação</button>

    </section>

    <section id="home-transactions">
      <TransactionComponent
        v-for="t in transaction.transactions" :key="t.id"
        :name="t.name" :amount="t.amount" :created-at="t.createdAt" :updated-at="t.updatedAt" :id="t.id"
      />
    </section>
  </main>
</template>

<script setup>
import TransactionComponent from '@/components/TransactionComponent.vue'
import createTransaction from '@/service/Transaction/createTransaction'
import listTransaction from '@/service/Transaction/listTransactions'
import { transactionStore } from '@/stores/transaction'
import HeaderComponent from '../components/HeaderComponent.vue'
import Swal from 'sweetalert2'
import { onMounted, reactive } from 'vue'

const transaction = transactionStore()

const form = reactive({
  name: '',
  amount: 0
})

onMounted(async () => {
  try {
    const transactionsFetch = await listTransaction()
    transaction.addTransactionList(transactionsFetch.transactions)
    transaction.setBalance(transactionsFetch.balance)
  } catch (e) {
    console.log(e)
  }
})

const handleCreateTransaction = async () => {
  let transactionType = 'income' // income = receita, expense = despesa

  const { value: formValues } = await Swal.fire({
    title: 'Nova Transação',
    html: `
      <div class="transaction-fields">
        <input id="swal-input-name" class="swal2-input" placeholder="Nome da transação">
        <input id="swal-input-amount" min="1" type="number" class="swal2-input" placeholder="Valor">
      </div>
      <div class="transaction-type">
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

<style scoped>

main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 32px;
}

#home-buttons {

  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  padding: 16px;

}

#home-buttons > button {
  display: block;
  padding: 0.75rem 2rem;
  font-size: 1rem;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

main > button:hover {
  background-color: #2980b9;
}

#home-transactions {

  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-wrap: wrap;
  width: 100%;
  align-items: center;
  gap: 16px;
  padding: 16px;

}

.transaction-fields {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    overflow-x: hidden;
    width: 100%;
  }

  .transaction-type {
    display: flex;
    flex-direction: row;
    overflow-x: hidden;
    width: 100%;
  }

  .transaction-fields input {
    width: 100%;
    margin: .3125em;
  }

  .transaction-type button {
    width: 100%;
  }

</style>
