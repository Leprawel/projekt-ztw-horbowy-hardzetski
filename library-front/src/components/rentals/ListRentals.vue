
<template>
<div id="rental-table">
  <table>
    <!-- ...thead... -->
    <thead>
      <tr>
      <th>Rental Id</th>
      <th>Book copy Id</th>
      <th>Book Id</th>
      <th>Book title</th>
      <th>Reader ID</th>
      <th>Reader name</th>
      <th>Start date</th>
      <th>Finish date</th>
      <th v-if="isLibrarian">Edit</th>
      <th v-if="isLibrarian">Delete</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="rental in rentalsSource" :key="rental.rental_id">
        <td>{{ rental.rental_id }}</td>
        <td>{{ rental.book_copy_id }}</td>
        <td>{{ rental.book_id }}</td>
        <td>{{ rental.title}}</td>
        <td>{{ rental.reader_id}}</td>
        <td>{{ rental.first_name + " " + rental.last_name}}</td>
        <td>{{ rental.start_date}}</td>
        <td>{{ rental.end_date}}</td>
        <td v-if="isLibrarian">
              <router-link :to="
              {
                name: 'updateRental',
                params: {rentalId: rental.rental_id},
              }">
              <button>Edit</button>
              </router-link>
        </td>
        <td v-if="isLibrarian">
            <button class = "btn btn-danger" @click="deleteRental(rental.rental_id)">
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
        this.$parent.getRentals()
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
