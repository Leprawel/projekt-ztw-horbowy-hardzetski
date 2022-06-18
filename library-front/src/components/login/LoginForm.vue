<template>
  <div id="add-book-form">
    <form @submit.prevent="loginAttempt" v-if="notLoggedIn">

      <label>Username</label>
      <input type="text" v-model="loginForm.username"
      :class="{ 'has-error': submitting && invalidUsername }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Password</label>
      <input type="password" v-model="loginForm.password"
      :class="{ 'has-error': submitting && invalidPassword }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <p v-if="error && submitting" class="error-message">
      Proszę wypełnić wskazane pola formularza
      </p>

      <p v-if="failedLogin" class="error-message">
      Wront credentials
      </p>
      <button id="submit">Authorise</button>
    </form>
    <h1 v-else>Already logged in</h1>
  </div>

  <button @click="loginSSO">Login with OKTA</button>
</template>

<script>
export default {
  name: 'LoginForm',
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      failedLogin: false,
      loginForm: {
      username: '',
      password: '',
      },
    }
  },
  methods: {
    loginAttempt() {
    console.log('uruchomiono loginAttempt')
    this.failedLogin = false

    this.submitting = true
    this.clearStatus()
    //check form fields
    if ( this.invalidLogin || this.invalidPassword) {
    this.error = true
    return
    }

    this.loginRequest(this.loginForm)
    //clear form fields
    this.loginForm = {
      username: '',
      password: '',
    },
    this.error = false
    this.success = true
    this.submitting = false
    },

    clearStatus() {
    this.success = false
    this.error = false
    },

    realiseLogin (loginData)   {
      if(loginData.username != null)
      {
        localStorage.setItem('login', loginData.username)
        localStorage.setItem('role', loginData.role)
        this.$router.go()
      }
    },

    async loginSSO ()   {
        await this.$auth.signInWithRedirect()
    },

    async loginRequest(loginForm) {
      try {
      console.log(JSON.stringify(loginForm))
      const response = await fetch('http://localhost:8080/user_password_check/', {method: 'POST', headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(loginForm)})
      console.log(await response)
      const data = await response.json()
      this.realiseLogin(data)
      console.log(data)
      } catch (error) {
      console.error(error)
      this.failedLogin = true
      }
    },



  },
  computed: {
    invalidUsername(){
      return this.loginForm.username === ''
    },
    invalidPassword(){
      return this.loginForm.password === ''
    },
    notLoggedIn(){
      return localStorage.getItem('login') == null
    },
  },
}
</script>

<style scoped>
form {
margin-bottom: 2rem;
}
[class*='-message'] {
font-weight: 500;
}
.error-message {
color: #d33c40;
}
.success-message {
color: #32a95d;
}
</style>
