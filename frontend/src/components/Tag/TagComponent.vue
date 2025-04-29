<template>
  <article class="tag" :style="{backgroundColor: props.color}">
    <p>{{ props.color }}</p>
  </article>
</template>

<script setup>

// eslint-disable-next-line no-undef
const props = defineProps({
  name: String,
  color: String
})

</script>
