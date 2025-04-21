<template>
  <div>
    <h2>{{ props.name }}</h2>
    <p>R$ {{ props.amount }}</p>
    <small>{{ props.createdAt }}</small>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({
  name: String,
  amount: Number,
  createdAt: String,
  updatedAt: String
})
</script>
