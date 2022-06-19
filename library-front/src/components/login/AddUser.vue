
<template>
  <div id="add-user" >
    <h1 v-if="!isLogedIn">Add</h1>
    <h1 v-else>No permission</h1>

    <div style="display: none" class="alert" id="errorbox">
     <span></span>
     Error:
     <span v-text="this.errorMSG"></span>
    </div>


    <add-user-form class="form-container" @add:userAdd="addUser" v-if="!isLogedIn"/>

  </div>
</template>

<script>
import AddUserForm from '@/components/login/AddUserForm.vue'

export default {
  name: 'AddUser',
  components: {
    AddUserForm,
  },
  data() {
    return {
      errorMSG:'',
    }
  },
    methods: {
    async addUser(userAdd) {
      document.getElementById("errorbox").style.display = "none"
      try {
      const response = await fetch('http://localhost:8080/user/', {method: "POST", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(userAdd)})
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
