import { defineStore } from 'pinia'
import { ref } from 'vue'

export const transactionStore = defineStore('transaction', () => {
  const transactions = ref([])

  const addTransaction = (transaction) => {
    transactions.value.push(transaction)
  }

  const addTransactionList = (transactionList) => {
    const existingIds = new Set(transactions.value.map(t => t.id))
    const filteredList = transactionList.filter(t => !existingIds.has(t.id))
    transactions.value = [...transactions.value, ...filteredList]
  }

  const deleteTransaction = (id) => {
    transactions.value = transactions.value.filter(t => t.id !== id)
  }

  return { transactions, addTransaction, deleteTransaction, addTransactionList }
})
