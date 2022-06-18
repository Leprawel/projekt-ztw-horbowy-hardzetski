<template>

  <router-link style="" :to="{name: 'addRental'}" v-if="isLibrarian"><button>Add new rental</button></router-link>
  <div id="show-rentals" >
    <rental-table :rentalsSource="rentals" v-if="isLibrarian || isManager"/>
    <h1 v-else>No permission</h1>
  </div>
</template>

<script>
import RentalTable from '@/components/rentals/ListRentals.vue'

export default {
  name: 'ShowRentals',
  components: {
    RentalTable,
  },
  data() {
    return {
      rentals: []
      }
    },
    methods: {
      async getRentals() {
        try {
        const response = await fetch('http://localhost:8080/rentals')
        const data = await response.json()
        console.log(data)
        this.rentals = data
        } catch (error) {
        console.error(error)
      }
    },
  },
  mounted() {
    this.getRentals()
    this.rentals = [{id: 4, bookCopyId: 2, readerId:2, startDate: '2012-05-06', finishDate: '2021-05-06', status: 0},
                  {id: 5, bookCopyId: 42, readerId:22, startDate: '2015-05-02', finishDate: '2018-05-03', status: 0}]
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
