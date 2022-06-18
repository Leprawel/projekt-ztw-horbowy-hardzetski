<template>

  <router-link style="" :to="{name: 'addReader'}" v-if="isLibrarian"><button>Add new reader</button></router-link>
  <div id="show-books" >
    <reader-table :readersSource="readers" v-if="isLibrarian || isManager"/>
    <h1 v-else>No permission</h1>
  </div>
</template>

<script>
import ReaderTable from '@/components/readers/ListReaders.vue'

export default {
  name: 'ShowBooks',
  components: {
    ReaderTable,
  },
  data() {
    return {
      readers: []
      }
    },
    methods: {
      async getReaders() {
        try {
        const response = await fetch('http://localhost:8080/readers')
        const data = await response.json()
        console.log(data)
        this.readers = data
        } catch (error) {
        console.error(error)
      }
    },
  },
  mounted() {
    this.getReaders()
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
