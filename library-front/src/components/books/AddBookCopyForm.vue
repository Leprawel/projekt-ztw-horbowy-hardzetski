<template>
  <div id="add-book-form">
    <form @submit.prevent="handleSubmit">
      <label>Book id</label>
      <input type="text" v-model="bookCopyAdd.book_id"
      :class="{ 'has-error': submitting && invalidBookId }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <p v-if="error && submitting" class="error-message">
      Proszę wypełnić wskazane pola formularza
      </p>
      <p v-if="success" class="success-message">
      Dane poprawnie zapisano
      </p>

      <button id="submit">Add</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AddBookForm',
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      bookCopyAdd: {
      bookId: '',
      copyId: 0,
      },
    }
  },
  methods: {
    handleSubmit() {
    console.log('uruchomiono handleSubmit')

    this.submitting = true
    this.clearStatus()
    //check form fields
    if ( this.invalidBookId) {
    this.error = true
    return
    }

    this.$emit('add:bookCopyAdd', this.bookCopyAdd)
    //clear form fields
    this.bookCopyAdd = {
      bookId: '',
      copyId: 0,
    },
    this.error = false
    this.success = true
    this.submitting = false
    },

    clearStatus() {
    this.success = false
    this.error = false
    },
  },
  computed: {
    invalidBookId(){
      return this.bookCopyAdd.book_id <= 0
    },
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
