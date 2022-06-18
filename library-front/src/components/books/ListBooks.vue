
<template>
<div id="book-table">
  <table>
    <!-- ...thead... -->
    <thead>
      <tr>
      <th>Book id</th>
      <th>Title</th>
      <th>Author ID</th>
      <th>Author</th>
      <th>Publish date</th>
      <th v-if="isManager">Edit</th>
      <th v-if="isManager">Delete</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="book in booksSource" :key="book.id">
        <td>{{ book.id }}</td>
        <td>{{ book.title }}</td>
        <td>{{ book.author_id }}</td>
        <td>{{ book.author.first_name + " " + book.author.last_name }}</td>
        <td>{{ book.first_publication_date}}</td>
        <td v-if="isManager">
              <router-link :to="
              {
                name: 'updateBook',
                params: {bookId: book.id},
              }">
              <button>Edit</button>
              </router-link>
        </td>
        <td v-if="isManager">
            <button class = "btn btn-danger" @click="deleteBook(book.id)">
              <i class="fas fa-trash-alt">Delete</i>
            </button>
        </td>
      </tr>
    </tbody>
  </table>
</div>
</template>

<script>  
  export default {
    name: 'book-table',
    props: {
      booksSource: Array,
    },

    methods: {
      async deleteBook(id) {
        try {
        console.log('Query to serv')
        await fetch('http://localhost:8080/book/' + id, {method: 'DELETE'} )
        this.$parent.getBooks()
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
      return localStorage.getItem('login') == "librarian"
    },
    isManager(){
      return localStorage.getItem('login') == "manager"
    },
  },
}
</script>

<style scoped>

</style>
