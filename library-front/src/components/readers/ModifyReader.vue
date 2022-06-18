
<template>
  <div id="modify-reader" >
    <h1 v-if="isLibrarian">Modify</h1>
    <h1 v-else>No permission</h1>
    <reader-form-mod :readerOrId = "readerOrId" @modify:reader="modifyReader" v-if="isLibrarian"/>
  </div>
</template>

<script>
import ReaderFormMod from '@/components/readers/ReaderFormMod.vue'

export default {
  name: 'ModifyReader',
  components: {
    ReaderFormMod,
  },
  data() {
    return {
      readerOrId: {}
      }
    },
    methods: {
  async modifyReader(reader) {
    console.log('Query to serv')
    try {
    await fetch('http://localhost:8080/reader/', {method: "PUT", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(reader)})
    } catch (error) {
    console.error(error)
  }
  },
  },

  created() {
    this.readerOrId = this.$route.params.readerId
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
