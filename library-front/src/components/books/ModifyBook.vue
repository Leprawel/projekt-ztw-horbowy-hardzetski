
<template>
  <div id="modify-book" >
    <h1 v-if="isManager">Modify</h1>
    <h1 v-else>No permission</h1>
    <book-form-mod :bookDetOrId = "bookDetOrId" @modify:bookDet="modifyBookDet" v-if="isManager"/>
  </div>
</template>

<script>
import BookFormMod from '@/components/books/BookFormMod.vue'

export default {
  name: 'ModifyBook',
  components: {
    BookFormMod,
  },
  data() {
    return {
      bookDetOr: {}
      }
    },
    methods: {
  async modifyBookDet(bookDet) {
    console.log('Query to serv')
    try {
    await fetch('http://localhost:8080/book/', {method: "PUT", headers: { 'Content-Type': 'application/json', }, body: JSON.stringify(bookDet)})
    } catch (error) {
    console.error(error)
  }
  },
  },

  mounted() {
    this.bookDetOrId = this.$route.params.bookId
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
