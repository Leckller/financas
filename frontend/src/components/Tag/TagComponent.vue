<template>
  <article class="tag-article">
    <p>{{ props.name }}</p>
    <span :style="{backgroundColor: props.color}"></span>
  </article>
</template>

<script setup>

// eslint-disable-next-line no-undef
const props = defineProps({
  name: String,
  color: String
})

</script>

<style scoped>

.tag-article {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-grow: 1;
  padding: 0.3rem;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.221);
  border-radius: 16px;
}

.tag-article span {
  width: 20px;
  height: 20px;
  border-radius: 999999px;
}

.tag-article p {
  padding: 2px 4px;
  border-radius: 8px;
}

</style>
