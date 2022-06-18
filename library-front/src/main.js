import { createApp } from 'vue'
import App from './App.vue'
import {createRouter, createWebHistory} from 'vue-router'
import Routes from './routes.js'
import { OktaAuth } from '@okta/okta-auth-js'
import OktaVue from '@okta/okta-vue'


const oktaAuth = new OktaAuth({
  issuer: 'https://dev-82426781.okta.com/oauth2/default',
  clientId: '0oa5c7cn3ylJeghIh5d7',
  redirectUri: 'http://localhost:8081/login/callback',
  scopes: ['openid', 'profile', 'email']
})

const router = createRouter({
  routes: Routes,
  mode: 'history',
  history: createWebHistory(process.env.BASE_URL)
});



const app = createApp(App)
app.use(OktaVue, { oktaAuth })
app.use(router)
app.mount('#app')
