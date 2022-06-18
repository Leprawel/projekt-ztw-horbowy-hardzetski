
<template>
<div id="book-table">
  <table>
    <!-- ...thead... -->
    <thead>
      <tr>
      <th>Book copy id</th>
      <th>Book id</th>
      <th>Title</th>
      <th>Publish date</th>
      <th>Is borrowed</th>
      <th v-if="isManager">Delete</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="bookCopy in bookCopiesSource" :key="bookCopy.id">
        <td>{{ bookCopy.id }}</td>
        <td>{{ bookCopy.book_id }}</td>
        <td>{{ bookCopy.title }}</td>
        <td>{{ bookCopy.first_publication_date}}</td>
        <td>{{ bookCopy.is_borrowed != null? bookCopy.is_borrowed : false}}</td>
        <td v-if="isManager">
            <button class = "btn btn-danger" @click="deleteBookCopy(bookCopy.id)">
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
    name: 'book-copies-table',
    props: {
      bookCopiesSource: Array,
    },

    methods: {
      async deleteBookCopy(id) {
        try {
        console.log('Query to serv')
        await fetch('http://localhost:8080/book_copy/' + id, {method: 'DELETE'})
        this.$parent.changeSource()
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

<style scoped>

</style>
