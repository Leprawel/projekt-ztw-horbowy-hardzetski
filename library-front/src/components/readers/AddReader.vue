
<template>
  <div id="add-reader" >
    <h1 v-if="isLibrarian">Add</h1>
    <h1 v-else>No permission</h1>
    <add-reader-form @add:readerAdd="addReader" v-if="isLibrarian"/>
  </div>
</template>

<script>
import AddReaderForm from '@/components/readers/AddReaderForm.vue'

export default {
  name: 'AddReader',
  components: {
    AddReaderForm,
  },
    methods: {
    async addReader(readerAdd) {
      try {
      await fetch('http://localhost:8080/reader/', {method: "POST", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(readerAdd)})
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
