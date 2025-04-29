<template>
  <HeaderComponent :balance="transaction.balance" />

  <main>

    <section id="home-buttons">
      <CreateComponent />
      <CreateTagComponent />
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
import CreateComponent from '@/components/Transaction/CreateComponent.vue'
import TransactionComponent from '@/components/TransactionComponent.vue'
import listTransaction from '@/service/Transaction/listTransactions'
import { transactionStore } from '@/stores/transaction'
import HeaderComponent from '../components/HeaderComponent.vue'
import { onMounted } from 'vue'
import CreateTagComponent from '@/components/Tag/CreateTagComponent.vue'
import listTags from '@/service/Tag/listTags'
import { tagStore } from '@/stores/tag'

const transaction = transactionStore()
const tagBox = tagStore()

onMounted(async () => {
  try {
    const transactionsFetch = await listTransaction()
    const tagsFetch = await listTags()
    tagBox.addList(tagsFetch)
    transaction.addTransactionList(transactionsFetch.transactions)
    transaction.setBalance(transactionsFetch.balance)
  } catch (e) {
    console.log(e)
  }
})

</script>

<style>

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
  gap: 16px;

}

#home-buttons > button {
  display: block;
  padding: 1rem;
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

</style>
