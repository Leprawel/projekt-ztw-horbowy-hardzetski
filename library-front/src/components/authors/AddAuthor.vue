
<template>
  <div id="add-book" >
    <h1 v-if="isManager">Add</h1>
    <h1 v-else>No permission</h1>
    <add-author-form @add:authorAdd="addAuthor" v-if="isManager"/>

  </div>
</template>

<script>
import AddAuthorForm from '@/components/authors/AddAuthorForm.vue'

export default {
  name: 'AddAuthor',
  components: {
    AddAuthorForm,
  },
    methods: {
    async addAuthor(authorAdd) {
      try {
      await fetch('http://localhost:8080/author/', {method: "POST", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(authorAdd)})
      } catch (error) {
      console.error(error)
    }
  },
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
