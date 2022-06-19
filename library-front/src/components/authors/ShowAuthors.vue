
<template>

  <router-link style="" :to="{name: 'addAuthor'}" v-if="isManager"><button>Add new author</button></router-link>
  <div id="show-authors" >
    <author-table :authorsSource="authors" v-if="isLibrarian || isManager"/>
    <h1 v-else>No permission</h1>
  </div>
</template>

<script>
import AuthorTable from '@/components/authors/ListAuthors.vue'

export default {
  name: 'ShowAuthors',
  components: {
    AuthorTable,
  },
  data() {
    return {
      authors: []
      }
    },
    methods: {
      async getAuthors() {
        try {
        const response = await fetch('http://localhost:8080/authors', {method: 'GET'})
        const data = await response.json()
        console.log(data)
        this.authors = data
        } catch (error) {
        console.error(error)
      }
    },
  },
  mounted() {
    this.getAuthors()
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
