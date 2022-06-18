
<template>
  <div id="add-book" >
    <h1 v-if="isManager">Add</h1>
    <h1 v-else>No permission</h1>
    <add-book-copy-form @add:bookCopyAdd="addBookCopy" v-if="isManager"/>
  </div>
</template>

<script>
import AddBookCopyForm from '@/components/books/AddBookCopyForm.vue'

export default {
  name: 'AddBookCopy',
  components: {
    AddBookCopyForm,
  },
    methods: {
    async addBookCopy(bookCopyAdd) {
      try {
      await fetch('http://localhost:8080/book_copy/', {method: "POST", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(bookCopyAdd)})
      this.$parent.getBookCopies()
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
