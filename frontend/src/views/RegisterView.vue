<template>
  <main class="">
    <h1>Login</h1>

    <form @submit.prevent="handleLogin">
      <section>
        <label>
          Username
          <input
            type="text"
            v-model="form.username"
            placeholder="Username"
            class="w-full p-2 border rounded c5"
            required
          />
        </label>

        <label>
          Name
          <input
            type="text"
            v-model="form.name"
            placeholder="Name"
            class="w-full p-2 border rounded c5"
            required
          />
        </label>

        <label>
          Email
          <input
            type="email"
            v-model="form.email"
            placeholder="Email"
            class="w-full p-2 border rounded c5"
            required
          />
        </label>

        <label>
          Budget
          <input
            type="text"
            v-model="form.budget"
            placeholder="Budget"
            class="w-full p-2 border rounded c5"
            required
          />
        </label>

        <label>
          Password
          <input
            type="password"
            v-model="form.password"
            placeholder="Password"
            class="w-full p-2 border rounded c5"
            required
          />
        </label>

        <button @click="handleSubmit" type="submit" class="mt-4 p-2 bg-blue-500 text-white rounded">Entrar</button>
      </section>
    </form>
  </main>
</template>

<script setup>

import createUser from '@/service/User/CreateUser'
import Swal from 'sweetalert2'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  name: '',
  email: '',
  budget: 0
})

const handleLogin = async () => {
  try {
    if (form.username && form.password && form.budget && form.email && form.name) {
      const { token } = await createUser(form)
      localStorage.setItem('authToken', token)
      router.push('/')
    } else {
      Swal.fire({
        title: 'Preencha todos os campos corretamente!'
      })
    }
  } catch {
    localStorage.removeItem('authToken')
  }
}
</script>
