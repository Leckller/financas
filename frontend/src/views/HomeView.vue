<template>
  <header>
    <h1>DOT FINANCE</h1>
    <p>Balance: {{ transaction.balance }}</p>
  </header>

  <main>
    <button @click="handleCreateTransaction">Nova Transação</button>

    <section>
      <TransactionComponent
        v-for="t in transaction.transactions" :key="t.id"
        :name="t.name" :amount="t.amount" :created-at="t.createdAt" :updated-at="t.updatedAt"
      />
    </section>
  </main>
</template>

<script setup>
import TransactionComponent from '@/components/TransactionComponent.vue'
import createTransaction from '@/service/Transaction/createTransaction'
import listTransaction from '@/service/Transaction/listTransactions'
import { transactionStore } from '@/stores/transaction'
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
  const { value: formValues } = await Swal.fire({
    title: 'Nova Transação',
    html:
      '<input id="swal-input-name" class="swal2-input" placeholder="Nome da transação">' +
      '<input id="swal-input-amount" type="number" class="swal2-input" placeholder="Valor">',

    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: 'Salvar',

    preConfirm: () => {
      const name = document.getElementById('swal-input-name').value
      const amount = parseFloat(document.getElementById('swal-input-amount').value)

      if (!name || isNaN(amount)) {
        Swal.showValidationMessage('Preencha todos os campos corretamente.')
        return
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
