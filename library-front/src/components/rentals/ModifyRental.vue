
<template>
  <div id="modify-rental" >
    <h1 v-if="isLibrarian">Modify</h1>
    <h1 v-else>No permission</h1>

    <div style="display: none" class="alert" id="errorbox">
     <span></span>
     Error:
     <span v-text="this.errorMSG"></span>
    </div>

    <rental-form-mod class="form-container"  :rentalOrId = "rentalOrId" @modify:rental="modifyRental" v-if="isLibrarian"/>
  </div>
</template>

<script>
import RentalFormMod from '@/components/rentals/RentalFormMod.vue'

export default {
  name: 'ModifyRental',
  components: {
    RentalFormMod,
  },
  data() {
    return {
      rentalOrId: {},
        errorMSG:'',
      }
    },
    methods: {
  async modifyRental(rental) {
    document.getElementById("errorbox").style.display = "none"
    console.log('Query to serv')
    try {
    const response = await fetch('http://localhost:8080/rental/', {method: "PUT", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(rental)})
    if(!response.ok)
    {
      this.errorMSG = await response.text()
      document.getElementById("errorbox").style.display = "block"
    }
    } catch (error) {
    console.error(error)
  }
  },
  },

  created() {
    this.rentalOrId = this.$route.params.rentalId
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
