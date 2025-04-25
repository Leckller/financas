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
          Password
          <input
            type="password"
            v-model="form.password"
            placeholder="Password"
            class="w-full p-2 border rounded c5"
            required
          />
        </label>

      </section>
      <button @click="handleSubmit" type="submit" class="mt-4 p-2 bg-blue-500 text-white rounded">Entrar</button>
      <router-link to="/register">
        Ainda não possui uma conta? Registre-se agora!
      </router-link>
    </form>
  </main>
</template>

<script setup>

import loginUser from '@/service/Auth/LoginUser'
import Swal from 'sweetalert2'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  try {
    if (form.username && form.password) {
      const { token } = await loginUser({ username: form.username, password: form.password })
      localStorage.setItem('authToken', token)
      router.push('/')
    } else {
      Swal.fire({
        title: 'Preencha todos os campos corretamente!'
      })
    }
  } catch {
    console.log('catched')
    localStorage.removeItem('authToken')
  }
}
</script>
