<template>

  <router-link style="" :to="{name: 'addRental'}" v-if="isLibrarian"><button>Add new rental</button></router-link>
  <h3 v-if="isLibrarian || isManager">Show active rentals</h3>
  <Toggle v-model="showActive" @change="changeSource" v-if="isLibrarian || isManager"/>
  <div id="show-rentals" >
    <rental-table :rentalsSource="rentals" v-if="isLibrarian || isManager"/>
    <h1 v-else>No permission</h1>
  </div>
</template>

<script>
import RentalTable from '@/components/rentals/ListRentals.vue'
import Toggle from '@vueform/toggle'

export default {
  name: 'ShowRentals',
  components: {
    RentalTable,
    Toggle,
  },
  data() {
    return {
      rentals: [],
      showActive: false
      }
    },
    methods: {
      changeSource()
      {
        if(this.showActive)
          this.getActiveRentals()
        else
          this.getRentals()
      },
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

    async getActiveRentals() {
      try {
        var today = new Date();
        const response = await fetch('http://localhost:8080/active_rentals/'+today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate())
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
