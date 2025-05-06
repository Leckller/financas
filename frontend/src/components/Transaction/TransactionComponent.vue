<template>
  <article class="transaction-article">
    <h2>{{ props.name }}</h2>

    <p>R$ {{ props.amount }}</p>
    <small>{{ props.createdAt }}</small>

    <section class="transaction-tags">
      <TagComponent v-for="t in tags" :key="t.id" :color="t.color" :name="t.name"/>
    </section>

    <section class="transaction-buttons">
      <button class="edit-button" @click="handleEditTransaction(props.id, props.amount, props.name)">Editar</button>
      <button class="delete-button" @click="handleDelete(props.id, props.amount, props.name, transaction)">Apagar</button>
    </section>
  </article>
</template>

<script setup>
import { defineProps } from 'vue'
import { transactionStore } from '@/stores/transaction'
import TagComponent from '../Tag/TagComponent.vue'
import { handleDelete } from './TransactionButtons/HandleDelete'
import { handleEditTransaction } from './TransactionButtons/HandleEditTransaction'

const transaction = transactionStore()

const props = defineProps({
  id: Number,
  name: String,
  amount: Number,
  createdAt: String,
  updatedAt: String,
  tags: Array
})

</script>

<style scoped>

.transaction-article {
  background-color: var(--bg1);
  word-break: break-all;
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

.transaction-article:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.transaction-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.transaction-buttons {
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
