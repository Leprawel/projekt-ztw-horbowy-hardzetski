
<template>
<div id="reader-table">
  <table>
    <!-- ...thead... -->
    <thead>
      <tr>
      <th>Reader Id</th>
      <th>Name</th>
      <th>Surname</th>
      <th>Document Id</th>
      <th v-if="isLibrarian">Edit</th>
      <th v-if="isLibrarian">Delete</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="reader in readersSource" :key="reader.id">
        <td>{{ reader.id }}</td>
        <td>{{ reader.first_name }}</td>
        <td>{{ reader.last_name }}</td>
        <td>{{ reader.document_id}}</td>
        <td v-if="isLibrarian">
              <router-link :to="
              {
                name: 'updateReader',
                params: {readerId: reader.id},
              }">
              <button>Edit</button>
              </router-link>
        </td>
        <td v-if="isLibrarian">
            <button class = "btn btn-danger" @click="deleteReader(reader.id)">
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
    name: 'reader-table',
    props: {
      readersSource: Array,
    },

    methods: {
      async deleteReader(id) {
        try {
        console.log('Query to serv')
        await fetch('http://localhost:8080/reader/' + id, {method: "DELETE"} )
        this.$parent.getReaders()
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
