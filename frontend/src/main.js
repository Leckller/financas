import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import 'vue-select/dist/vue-select.css'
import VueSelect from 'vue-select'

const app = createApp(App)

app.component('v-select', VueSelect)
app.use(createPinia())
app.use(router)

app.mount('#app')
