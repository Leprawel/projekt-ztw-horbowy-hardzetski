

<template>
  <div id="book-form-mod">
    <form @submit.prevent="modSubmit">
      <label>Book title</label>
      <input type="text" v-model="bookDet.title"
      :class="{ 'has-error': submitting && invalidTitle }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Author id</label>
      <input type="text" v-model="bookDet.author_id"
      :class="{ 'has-error': submitting && invalidTitle }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Publish date</label>
      <input type="date" v-model="bookDet.first_publication_date"
      :class="{ 'has-error': submitting && invalidPdate }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <p v-if="error && submitting" class="error-message">
      Please enter correct data in fields
      </p>
      <p v-if="success" class="success-message">
      Data was sent to the database
      </p>

      <button id="submit">Modify</button>
    </form>
  </div>
</template>

<script>


export default {
  name: 'book-form-mod',
  props: {
    bookDetOrId: {},
  },
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      bookDet: {},
    }
  },
  methods: {
    modSubmit() {
    console.log('uruchomiono handleSubmit')
    this.submitting = true
    this.clearStatus()
    //check form fields

    if (this.invalidId || this.invalidTitle || this.invalidAuthorId || this.invalidPdate) {
    this.error = true
    return
    }

    this.$emit('modify:bookDet', this.bookDet)
    this.error = false
    this.success = true
    this.submitting = false
    },

    clearStatus() {
    this.success = false
    this.error = false
    },

    async getBook(id) {
      try {
      const response = await fetch('http://localhost:8080/book/' + id, {method: "GET"})
      const data = await response.json()
      console.log('async get')
      console.log(data)
      this.bookDet = data
      } catch (error) {
      console.error(error)
    }
    },
  },
  computed: {
    invalidId(){
      if (this.bookDet.id < 0)
        return true

      return false
    },
    invalidTitle(){
      return this.bookDet.title === ''
    },
    invalidAuthorId(){
      return this.bookDet.author_id <= 0
    },
    invalidPdate(){
      return this.bookDet.first_publication_date === ''
    },
  },
  mounted() {
    this.getBook(this.bookDetOrId)
  },
}


</script>

<style scoped>
  form {
  margin-bottom: 2rem;
  }
  [class*='-message'] {
  font-weight: 500;
  }
  .error-message {
  color: #d33c40;
  }
  .success-message {
  color: #32a95d;
  }

</style>
