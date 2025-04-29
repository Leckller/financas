<template>
    <button @click="handleCreateTransaction">Nova Transação</button>
</template>

<script setup>
import createTransactionWithTags from '@/service/Tag/createTransactionWithTags'
import createTransaction from '@/service/Transaction/createTransaction'
import { tagStore } from '@/stores/tag'
import { transactionStore } from '@/stores/transaction'
import Swal from 'sweetalert2'
import { reactive } from 'vue'

const transaction = transactionStore()
const tagBox = tagStore()

const form = reactive({
  name: '',
  amount: 0,
  tag: '',
  tags: []
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
      <div id="swal-form-add-tag">
        <button type="button" id="btn-add-tag" class="swal2-confirm btn swal2-styled">Adicionar Tag</button>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: 'Salvar',

    didOpen: () => {
      form.name = ''
      form.amount = 0
      form.tag = {}
      form.tags = []

      const incomeBtn = document.getElementById('btn-income')
      const expenseBtn = document.getElementById('btn-expense')
      const addTagBtn = document.getElementById('btn-add-tag')
      const addTagDiv = document.getElementById('swal-form-add-tag')

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

      // TAG
      const selectTags = document.createElement('select')
      selectTags.classList.add('tag-select')

      selectTags.addEventListener('change', ({ target: { value } }) => {
        const tag = tagBox.tags.filter(t => t.id === +value)[0]
        form.tag = tag
      })

      selectTags.value = tagBox.tags[0].id
      form.tag = tagBox.tags[0]

      tagBox.tags.forEach(t => {
        const tag = document.createElement('option')
        tag.value = t.id
        tag.textContent = t.name
        tag.classList.add('tag-option')

        selectTags.appendChild(tag)
      })

      addTagDiv.appendChild(selectTags)

      addTagBtn.addEventListener('click', () => {
        if (form.tags.includes(form.tag.id)) {
          return
        }
        form.tags.push(form.tag.id)
        console.log(form.tags)
      })

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

      return { name, amount, tags: form.tags }
    }
  })

  if (formValues) {
    try {
      if (formValues.tags.length > 0) {
        const addTransactionWithTags = await createTransactionWithTags({ name: formValues.name, amount: formValues.amount, tags: formValues.tags })
        transaction.addTransaction(addTransactionWithTags)
        transaction.setBalance(transaction.balance + formValues.amount)
        return
      }

      const addTransaction = await createTransaction({ name: formValues.name, amount: formValues.amount })
      transaction.addTransaction(addTransaction)
      transaction.setBalance(transaction.balance + formValues.amount)
    } catch (e) { console.log(e) }
  }
}

</script>

<style>

.tag-select {
  padding: .625em 1.1em;
  background-color: var(--c3);
  color: white;
  border-radius: .25em;
  font-size: 1em;
  border: 0;
}

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
  outline: none;
}

.swal-form-type button {
  width: 100%;
}

#btn-add-tag {
  background-color: var(--c3);
}

@media (max-width: 315px) {
  .swal-form-type {
    flex-wrap: wrap;
  }
}

</style>
