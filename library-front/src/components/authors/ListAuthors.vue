
<template>
<div id="book-table">
  <table>
    <!-- ...thead... -->
    <thead>
      <tr>
      <th>Author id</th>
      <th>First name</th>
      <th>Last name</th>
      <th>Birth date</th>
      <th v-if="isManager">Edit</th>
      <th v-if="isManager">Delete</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="author in authorsSource" :key="author.id">
        <td>{{ author.id }}</td>
        <td>{{ author.first_name }}</td>
        <td>{{ author.last_name }}</td>
        <td>{{ author.birth_date }}</td>
        <td v-if="isManager">
              <router-link :to="
              {
                name: 'updateAuthor',
                params: {authorId: author.id},
              }">
              <button>Edit</button>
              </router-link>
        </td>
        <td v-if="isManager">
            <button class = "btn btn-danger" @click="deleteAuthor(author.id)">
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
    name: 'author-table',
    props: {
      authorsSource: Array,
    },

    methods: {
      async deleteAuthor(id) {
        try {
        console.log('Query to serv')
        await fetch('http://localhost:8080/author/' + id, {method: 'DELETE'} )
        this.$parent.getAuthors()
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
