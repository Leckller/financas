<template>

  <main>

    <h1>
      Login
    </h1>

    <form @submit.prevent="handleLogin">

      <section>

        <label>
          username
          <input type="text" v-model="username" placeholder="Username" class="w-full p-2 border rounded c5" required />
        </label>

        <label>
          email
          <input type="text" v-model="email" placeholder="Email" class="w-full p-2 border rounded c5" required />
        </label>

        <label>
          password
          <input type="password" v-model="password" placeholder="Password" class="w-full p-2 border rounded c5" required />
        </label>

      </section>

    </form>

  </main>

</template>

<script>

export default {
  name: 'LoginView',
  data () {
    return {
      username: '',
      password: '',
      email: ''
    }
  },
  methods: {
    handleLogin () {
      console.log('Username:', this.username)
      console.log('Password:', this.password)
    }
  }
}

</script>
