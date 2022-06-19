
<template>
  <div id="modify-author" >
    <h1 v-if="isManager">Modify</h1>
    <h1 v-else>No permission</h1>

    <div style="display: none" class="alert" id="errorbox">
     <span></span>
     Error:
     <span v-text="this.errorMSG"></span>
    </div>

    <author-form-mod class="form-container"  :authorOrId = "authorOrId" @modify:author="modifyAuthor" v-if="isManager"/>
  </div>
</template>

<script>
import AuthorFormMod from '@/components/authors/AuthorFormMod.vue'

export default {
  name: 'ModifyAuthor',
  components: {
    AuthorFormMod,
  },
  data() {
    return {
      authorOrId: {},
        errorMSG:'',
      }
    },
    methods: {
  async modifyAuthor(author) {
    document.getElementById("errorbox").style.display = "none"
    console.log('Query to serv')
    try {
    const response = await fetch('http://localhost:8080/author/', {method: "PUT", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(author)})
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
    this.authorOrId = this.$route.params.authorId
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
