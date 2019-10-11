import Vue from 'vue'
import VueResource from 'vue-resource'
import router from 'router/router.js'
import App from 'pages/App.vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify)
Vue.use(VueResource)

new Vue({
    el: '#app',
    router,
    vuetify: new Vuetify(),
    render: a => a(App)
})