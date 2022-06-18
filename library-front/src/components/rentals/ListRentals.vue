
<template>
<div id="rental-table">
  <table>
    <!-- ...thead... -->
    <thead>
      <tr>
      <th>Rental Id</th>
      <th>Book copy Id</th>
      <th>Reader ID</th>
      <th>Start date</th>
      <th>Finish date</th>
      <th>Status</th>
      <th v-if="isLibrarian">Edit</th>
      <th v-if="isLibrarian">Delete</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="rental in rentalsSource" :key="rental.id">
        <td>{{ rental.id }}</td>
        <td>{{ rental.bookCopyId }}</td>
        <td>{{ rental.readerId }}</td>
        <td>{{ rental.startDate}}</td>
        <td>{{ rental.finishDate}}</td>
        <td>{{ rental.status}}</td>
        <td v-if="isLibrarian">
              <router-link :to="
              {
                name: 'updateRental',
                params: {rentalId: rental.id},
              }">
              <button>Edit</button>
              </router-link>
        </td>
        <td v-if="isLibrarian">
            <button class = "btn btn-danger" @click="deleteRental(rental.id)">
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
    name: 'rental-table',
    props: {
      rentalsSource: Array,
    },

    methods: {
      async deleteRental(id) {
        try {
        console.log('Query to serv')
        await fetch('http://localhost:8080/rental/' + id, {method: "DELETE"} )
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
