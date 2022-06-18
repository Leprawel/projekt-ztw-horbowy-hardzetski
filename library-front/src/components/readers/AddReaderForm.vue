<template>
  <div id="add-book-form">
    <form @submit.prevent="handleSubmit">
      <label>Readers name</label>
      <input type="text" v-model="readerAdd.first_name"
      :class="{ 'has-error': submitting && invalidName }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Readers surname</label>
      <input type="text" v-model="readerAdd.last_name"
      :class="{ 'has-error': submitting && invalidSurname }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Readers document id</label>
      <input type="text" v-model="readerAdd.document_id"
      :class="{ 'has-error': submitting && invalidDocumentId }"
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
  name: 'AddReaderForm',
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      readerAdd: {
      first_name: '',
      last_name: '',
      document_id: '',
      },
    }
  },
  methods: {
    handleSubmit() {
    console.log('uruchomiono handleSubmit')

    this.submitting = true
    this.clearStatus()
    //check form fields
    if (this.invalidName || this.invalidSurname || this.invalidDocumentId) {
    this.error = true
    return
    }

    this.$emit('add:readerAdd', this.readerAdd)
    //clear form fields
    this.readerAdd = {
      name: '',
      surname: '',
      documentId: '',
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
    invalidName(){
      return this.readerAdd.first_name === ''
    },
    invalidSurname(){
      return this.readerAdd.last_name === ''
    },
    invalidDocumentId(){
      return this.readerAdd.document_id === ''
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
