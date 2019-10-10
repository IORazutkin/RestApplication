import Vue from 'vue'
import VueRouter from 'vue-router'
import AuthPage from 'pages/AuthPage.vue'
import MainPage from 'pages/MainPage.vue'
import ProfilePage from 'pages/ProfilePage.vue'
import AdminPage from 'pages/AdminPage.vue'

Vue.use(VueRouter)

const routes = [
     {path: '/login', component: AuthPage},
     {path: '/profile', component: ProfilePage},
     {path: '/admin', component: AdminPage},
     {path: '/**', component: MainPage}
]

export default new VueRouter({
    mode: 'history',
    routes
})