<template>
  <div id="add-rental-form">
    <form @submit.prevent="handleSubmit">
      <label>Book copy Id</label>
      <input type="text" v-model="rentalAdd.book_copy_id"
      :class="{ 'has-error': submitting && invalidBookCopyId }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Reader id</label>
      <input type="text" v-model="rentalAdd.reader_id"
      :class="{ 'has-error': submitting && invalidReaderId }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Start date</label>
      <input type="date" v-model="rentalAdd.start_date"
      :class="{ 'has-error': submitting && invalidStartDate }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Finish date</label>
      <input type="date" v-model="rentalAdd.end_date"
      :class="{ 'has-error': submitting && invalidFinishDate }"
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
  name: 'AddRentalForm',
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      rentalAdd: {
      book_copy_id: '',
      reader_id: '',
      start_date: '',
      end_date: '',
      },
    }
  },
  methods: {
    handleSubmit() {
    console.log('uruchomiono handleSubmit')

    this.submitting = true
    this.clearStatus()
    //check form fields
    if (this.invalidBookCopyId || this.invalidReaderId || this.invalidStartDate|| this.invalidFinishDate) {
    this.error = true
    return
    }

    this.$emit('add:rentalAdd', this.rentalAdd)
    //clear form fields
    this.rentalAdd = {
      book_copy_id: '',
      reader_id: '',
      start_date: '',
      end_date: '',
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
    invalidBookCopyId(){
      return this.rentalAdd.book_copy_id < 0
    },
    invalidReaderId(){
      return this.rentalAdd.reader_id < 0
    },
    invalidStartDate(){
      return this.rentalAdd.start_date === ''
    },
    invalidFinishDate(){
      return this.rentalAdd.end_date === ''
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
