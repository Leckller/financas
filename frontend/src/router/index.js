import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    meta: { public: true }, // Rota pública
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    meta: { public: true }, // Rota pública
    component: () => import('../views/RegisterView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// Simulação de função que verifica se o usuário está autenticado
function isAuthenticated () {
  return !!localStorage.getItem('authToken') // ou outro método de verificação
}

router.beforeEach((to, from, next) => {
  // Se a rota não for pública e o usuário não estiver autenticado
  if (!to.meta.public && !isAuthenticated()) {
    return next('/login') // redireciona para login
  }

  // Senão, segue normalmente
  next()
})

export default router
