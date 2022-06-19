

<template>
  <div id="reader-form-mod">
    <form @submit.prevent="modSubmit">
      <label>Readers name</label>
      <input type="text" v-model="reader.first_name"
      :class="{ 'has-error': submitting && invalidName }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Readers surname</label>
      <input type="text" v-model="reader.last_name"
      :class="{ 'has-error': submitting && invalidSurname }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Readers document id</label>
      <input type="text" v-model="reader.document_id"
      :class="{ 'has-error': submitting && invalidDocumentId }"
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
  name: 'reader-form-mod',
  props: {
    readerOrId: {},
  },
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      reader: {},
    }
  },
  methods: {
    modSubmit() {
    console.log('uruchomiono handleSubmit')
    this.submitting = true
    this.clearStatus()
    //check form fields

    if (this.invalidId || this.invalidName || this.invalidSurname || this.invalidDocumentId) {
    this.error = true
    return
    }

    this.$emit('modify:reader', this.reader)
    this.error = false
    this.success = true
    this.submitting = false
    },

    clearStatus() {
    this.success = false
    this.error = false
    },

    async getReader(id) {
      try {
      const response = await fetch('http://localhost:8080/reader/' + id, {method: "GET"})
      const data = await response.json()
      console.log(data)
      this.reader = data
      } catch (error) {
      console.error(error)
    }
    },
  },
  computed: {
    invalidId(){
      if (this.reader.id < 0)
        return true

      return false
    },
    invalidName(){
      return this.reader.first_name === ''
    },
    invalidSurname(){
      return this.reader.last_name === ''
    },
    invalidDocumentId(){
      return this.reader.document_id === ''
    },
  },
  mounted() {
    this.getReader(this.readerOrId)
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
