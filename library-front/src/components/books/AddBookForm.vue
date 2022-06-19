<template>
  <div id="add-book-form">
    <form @submit.prevent="handleSubmit">
      <label>Book title</label>
      <input type="text" v-model="bookAdd.title"
      :class="{ 'has-error': submitting && invalidTitle }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Author id</label>
      <input type="text" v-model="bookAdd.author_id"
      :class="{ 'has-error': submitting && invalidAuthorId }"
      @focus="clearStatus"
      @keypress="clearStatus"/>


      <label>Publish date</label>
      <input type="date" v-model="bookAdd.first_publication_date"
      :class="{ 'has-error': submitting && invalidPdate }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <p v-if="error && submitting" class="error-message">
      Please enter correct data in fields
      </p>
      <p v-if="success" class="success-message">
      Data was sent to the database
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
      bookAdd: {
      title: '',
      authorId: 0,
      first_publication_date: '',
      },
    }
  },
  methods: {
    handleSubmit() {
    console.log('uruchomiono handleSubmit')

    this.submitting = true
    this.clearStatus()
    //check form fields
    if (this.invalidTitle || this.invalidAuthorId || this.invalidPdate) {
    this.error = true
    return
    }

    this.$emit('add:bookAdd', this.bookAdd)
    //clear form fields
    this.bookAdd = {
      title: '',
      authorId: 0,
      first_publication_date: '',
    },
    this.error = false
    this.success = true
    this.submitting = false
    },

    clearStatus() {
    this.success = false
    this.error = false
    },

    setSelected(value)
    {
        console.log('select changed')
            console.log(value)
    },
  },
  computed: {
    invalidTitle(){
      return this.bookAdd.title === ''
    },
    invalidAuthorId(){
      return this.bookAdd.author_id <= 0
    },
    invalidPdate(){
      return this.bookAdd.first_publication_date == ''
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
