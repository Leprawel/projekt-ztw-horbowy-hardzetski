

<template>
  <div id="author-form-mod">
    <form @submit.prevent="modSubmit">
      <label>First name</label>
      <input type="text" v-model="author.first_name"
      :class="{ 'has-error': submitting && invalidFirstName }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Last name</label>
      <input type="text" v-model="author.last_name"
      :class="{ 'has-error': submitting && invalidLastName }"
      @focus="clearStatus"
      @keypress="clearStatus"/>


      <label>Birth date</label>
      <input type="date" v-model="author.birth_date"
      :class="{ 'has-error': submitting && invalidBirthDate }"
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
  name: 'author-form-mod',
  props: {
  authorOrId: {},
  },
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      author: {},
    }
  },
  methods: {
    modSubmit() {
    console.log('uruchomiono handleSubmit')
    this.submitting = true
    this.clearStatus()
    //check form fields

    if (this.invalidId || this.invalidFirstName || this.invalidLastName || this.invalidBirthDate) {
    this.error = true
    return
    }

    this.$emit('modify:author', this.author)
    this.error = false
    this.success = true
    this.submitting = false
    },

    clearStatus() {
    this.success = false
    this.error = false
    },

    async getAuthor(id) {
      try {
      const response = await fetch('http://localhost:8080/author/' + id, {method: "GET"})
      const data = await response.json()
      this.author = data
      } catch (error) {
      console.error(error)
    }
    },
  },
  computed: {
    invalidId(){
      if (this.author.id < 0)
        return true

      return false
    },
    invalidFirstName(){
      return this.author.first_name === ''
    },
    invalidLastName(){
      return this.author.last_name === ''
    },
    invalidBirthDate(){
      return this.author.birth_date == ''
    },
  },
  mounted() {
    console.log(this.authorOrId)
    this.getAuthor(this.authorOrId)
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
