
<template>


  <add-book-copy v-if="isManager"/>
  <h3>Show only avaliable</h3>
  <Toggle v-model="showAvaliable" @change="changeSource"/>
  <div id="show-books" >
    <book-copies-table :bookCopiesSource="bookCopies"/>
  </div>
</template>

<script>
import BookCopiesTable from '@/components/books/ListBooksCopies.vue'
import AddBookCopy from '@/components/books/AddBookCopy.vue'
import Toggle from '@vueform/toggle'

export default {
  name: 'ShowBookCopies',
  components: {
    BookCopiesTable,
    AddBookCopy,
    Toggle,
  },
  data() {
    return {
      bookCopies: [],
      showAvaliable: false
      }
    },
    methods: {
      changeSource()
      {
        if(this.showAvaliable)
          this.getAvaliableBookCopies()
        else
          this.getBookCopies()
      },
      async getBookCopies() {
        try {
        console.log("getting copies")
        var today = new Date();
        const response = await fetch('http://localhost:8080/all_book_copies/'+today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate())
        const data = await response.json()
        this.bookCopies = data
        } catch (error) {
        console.error(error)
      }
    },

    async getAvaliableBookCopies() {
      try {
      console.log("getting copies")
      var today = new Date();
      const response = await fetch('http://localhost:8080/not_borrowed_copies/'+today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate())
      const data = await response.json()
      this.bookCopies = data
      } catch (error) {
      console.error(error)
    }
  },
  },
  mounted() {
    this.getBookCopies()
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
<style src="@vueform/toggle/themes/default.css"></style>
