<template>

  <header>
    <h1>DOT FINANCE</h1>
    <p>Balance: {{ props.balance }}</p>
  </header>

</template>

<script setup>

// eslint-disable-next-line no-undef
const props = defineProps({
  balance: Number
})

</script>

<style>

/* Cabeçalho */
header {
  background-color: var(--bg4);
  color: var(--c1);
  padding: 2rem 1rem;
  text-align: center;
  border-radius: 0 0 16px 16px;
}

header h1 {
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
  color: var(--c1);
}

header p {
  font-size: 1.25rem;
  color: var(--c2); /* c2 */
}

</style>
