<template>
  <button @click="handleCreateTag">Nova Tag</button>
</template>

<script setup>
import createTag from '@/service/Tag/createTag'
import { tagStore } from '@/stores/tag'
import Swal from 'sweetalert2'
import { reactive } from 'vue'

const tagBox = tagStore()

const form = reactive({
  name: '',
  color: ''
})

const handleCreateTag = async () => {
  const { value: formValues } = await Swal.fire({
    title: 'Nova Tag',
    html: `
    <div class="swal-form-fields">
      <input id="swal-input-name" type="text" class="swal2-input" placeholder="Nome da tag">
      <label>
        Selecione uma cor para a tag:
        <input id="swal-input-color" type="color" class="swal2-input" placeholder="Cor da tag">
        <span id="color_front"></span>
      </label
    </div>
  `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: 'Salvar',

    didOpen: () => {
      const inputColor = document.querySelector('#swal-input-color')
      const spanColor = document.querySelector('#color_front')

      inputColor.addEventListener('input', (e) => {
        spanColor.style.backgroundColor = e.target.value
      })
    },

    preConfirm: () => {
      const name = document.getElementById('swal-input-name').value
      const color = document.getElementById('swal-input-color').value

      if (!name) {
        Swal.showValidationMessage('O nome da tag é obrigatório.')
        return
      }

      return { name, color }
    }
  })

  if (formValues) {
    form.name = formValues.name
    form.color = formValues.amount
    try {
      const tag = await createTag({ name: formValues.name, color: formValues.color })
      tagBox.addTag(tag)
    } catch (e) {
      console.log(e)
    }
  }
}

</script>

<style>

  .swal-form-fields label {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    width: 100%;
  }

  .swal-form-fields label input {
    display: none;
  }

  .swal-form-fields label span {
    border-radius: 99999px;
    width: 50px;
    height: 50px;
    background-color: black;
  }

</style>
