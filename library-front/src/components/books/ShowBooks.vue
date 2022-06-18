
<template>

  <router-link style="" :to="{name: 'addBook'}" v-if="isManager"><button>Add new book</button></router-link>
  <div id="show-books" >
    <book-table :booksSource="books" v-if="isLibrarian || isManager"/>
    <h1 v-else>No permission</h1>
  </div>
</template>

<script>
import BookTable from '@/components/books/ListBooks.vue'

export default {
  name: 'ShowBooks',
  components: {
    BookTable,
  },
  data() {
    return {
      books: []
      }
    },
    methods: {
      async getBooks() {
        try {
        const response = await fetch('http://localhost:8080/books_with_authors', {method: 'GET'})
        const data = await response.json()
        console.log(data)
        this.books = data
        } catch (error) {
        console.error(error)
      }
    },
  },
  mounted() {
    this.getBooks()
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
