import { defineStore } from 'pinia'
import { ref } from 'vue'

export const tagStore = defineStore('tag', () => {
  const tags = ref([])

  const addTag = (tag) => {
    tags.value.unshift(tag)
  }

  const removeTag = (tag) => {
    tags.value.filter(t => t.id !== tag.id)
  }

  const addList = (tagList) => {
    tags.value = [...tagList, ...tags.value]
  }

  return { tags, addTag, removeTag, addList }
})
