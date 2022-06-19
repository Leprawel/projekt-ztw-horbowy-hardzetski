
<template>
  <div id="rental-form-mod">
    <form @submit.prevent="modSubmit">
      <label>Book copy Id</label>
      <input type="text" v-model="rental.book_copy_id"
      :class="{ 'has-error': submitting && invalidBookCopyId }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Reader id</label>
      <input type="text" v-model="rental.reader_id"
      :class="{ 'has-error': submitting && invalidReaderId }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Start date</label>
      <input type="date" v-model="rental.start_date"
      :class="{ 'has-error': submitting && invalidStartDate }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Finish date</label>
      <input type="date" v-model="rental.end_date"
      :class="{ 'has-error': submitting && invalidFinishDate }"
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
  name: 'rental-form-mod',
  props: {
    rentalOrId: {},
  },
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      rental: {},
    }
  },
  methods: {
    modSubmit() {
    console.log('uruchomiono handleSubmit')
    this.submitting = true
    this.clearStatus()
    //check form fields

    if (this.invalidId || this.invalidBookCopyId || this.invalidReaderId || this.invalidStartDate|| this.invalidFinishDate) {
    this.error = true
    return
    }

    this.$emit('modify:rental', this.rental)
    this.error = false
    this.success = true
    this.submitting = false
    },

    clearStatus() {
    this.success = false
    this.error = false
    },

    async getRental(id) {
      try {
      const response = await fetch('http://localhost:8080/rental/' + id, {method: "GET"})
      const data = await response.json()
      console.log(data)
      this.rental = data
      } catch (error) {
      console.error(error)
    }
    },
  },
  computed: {
    invalidId(){
      if (this.rental.id < 0)
        return true

      return false
    },
    invalidBookCopyId(){
      return this.rental.book_copy_id <0
    },
    invalidReaderId(){
      return this.rental.reader_id <0
    },
    invalidStartDate(){
      return this.rental.start_date === ''
    },
    invalidFinishDate(){
      return this.rental.end_date === ''
    },
  },
  mounted() {
    this.getRental(this.rentalOrId)
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
