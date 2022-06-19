<template>
  <div id="add-author-form">
    <form @submit.prevent="handleSubmit">
      <label>First name</label>
      <input type="text" v-model="authorAdd.first_name"
      :class="{ 'has-error': submitting && invalidFirstName }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Last name</label>
      <input type="text" v-model="authorAdd.last_name"
      :class="{ 'has-error': submitting && invalidLastName }"
      @focus="clearStatus"
      @keypress="clearStatus"/>


      <label>Birth date</label>
      <input type="date" v-model="authorAdd.birth_date"
      :class="{ 'has-error': submitting && invalidBirthDate }"
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
  name: 'AddAuthorForm',
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      authorAdd: {
      first_name: '',
      last_name: '',
      birth_date: '',
      },
    }
  },
  methods: {
    handleSubmit() {
    console.log('uruchomiono handleSubmit')

    this.submitting = true
    this.clearStatus()
    //check form fields
    if (this.invalidFirstName || this.invalidLastName || this.invalidBirthDate) {
    this.error = true
    return
    }

    this.$emit('add:authorAdd', this.authorAdd)
    
    //clear form fields
    this.authorAdd = {
      first_name: '',
      last_name: '',
      birth_date: '',
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
    invalidFirstName(){
      return this.authorAdd.first_name === ''
    },
    invalidLastName(){
      return this.authorAdd.last_name === ''
    },
    invalidBirthDate(){
      return this.authorAdd.birth_date == ''
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
