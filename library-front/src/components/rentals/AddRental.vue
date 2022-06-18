
<template>
  <div id="add-rental" >
    <h1 v-if="isLibrarian">Add</h1>
    <h1 v-else>No permission</h1>
    <add-rental-form @add:rentalAdd="addRental" v-if="isLibrarian"/>
  </div>
</template>

<script>
import AddRentalForm from '@/components/rentals/AddRentalForm.vue'

export default {
  name: 'AddRental',
  components: {
    AddRentalForm,
  },
    methods: {
    async addRental(rentalAdd) {
      try {
        await fetch('http://localhost:8080/rental/', {method: "POST", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(rentalAdd)})
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
