
<template>

  <h1 v-if="isLogedIn">Welcome, {{this.login}}! <button @click="logout()">Log out </button></h1>
  <h1 v-else>Not logged in <router-link to="/login"><button>Log in</button></router-link></h1>
  <nav>
  <ul>
    <li><router-link to="/bookCopies">BooksCopies</router-link></li>
    <li v-if="isLogedIn"><router-link to="/books">Books</router-link></li>
    <li v-if="isLogedIn"><router-link to="/readers">Readers</router-link></li>
    <li v-if="isLogedIn"><router-link to="/rentals">Rentals</router-link></li>
  </ul>
  </nav>
  <router-view> </router-view>

</template>

<script>

export default {
  name: 'App',
  data() {
    return {
      login:'',
      role:''
    }
  },
  computed: {
    isLogedIn(){
      return localStorage.getItem('login') != null
    },
    isLibrarian(){
      return localStorage.getItem('role') == "librarian"
    },
    isManager(){
      return localStorage.getItem('role') == "manager"
    },
  },
  methods: {
    async logout() {
      localStorage.removeItem('login')
      localStorage.removeItem('role')
      await this.$auth.signOut()
      this.$router.go()
    },
    async refreshUserSSO()
    {
      console.log((await this.$auth.session.get()).status)
        console.log(await this.$auth.getUser())
      if((await this.$auth.session.get()).status === 'ACTIVE')
        localStorage.setItem('login', (await this.$auth.getUser()).name)
    }
  },
  mounted()
  {
    this.login = localStorage.getItem('login')
    this.role = localStorage.getItem('role')
    this.refreshUserSSO()
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
button {
background: #009435;
border: 1px solid #009435;
}
.small-container {
max-width: 680px;
}

</style>
