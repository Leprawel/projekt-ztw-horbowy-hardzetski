<template>
  <div id="add-book-form">
    <form @submit.prevent="handleSubmit">
      <label>Username</label>
      <input type="text" v-model="userAdd.username"
      :class="{ 'has-error': submitting && invalidUsername }"
      @focus="clearStatus"
      @keypress="clearStatus"/>

      <label>Password</label>
      <input type="text" v-model="userAdd.password"
      :class="{ 'has-error': submitting && invalidPassword }"
      @focus="clearStatus"
      @keypress="clearStatus"/>


      <label>Role</label>
      <select name="roles" id="role"
      v-model="userAdd.role"
      :class="{ 'has-error': submitting && invalidRole }"
      @focus="clearStatus"
      @keypress="clearStatus">
        <option value="manager">manager</option>
        <option value="librarian">librarian</option>
      </select>

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
      userAdd: {
      username: '',
      password: '',
      role: '',
      },
    }
  },
  methods: {
    handleSubmit() {
    console.log('uruchomiono handleSubmit')

    this.submitting = true
    this.clearStatus()
    //check form fields
    if (this.invalidUsername || this.invalidPassword || this.invalidRole) {
    this.error = true
    return
    }

    console.log(this.userAdd)

    this.$emit('add:userAdd', this.userAdd)
    //clear form fields
    this.userAdd = {
    username: '',
    password: '',
    role: '',
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
    invalidUsername(){
      return this.userAdd.username === ''
    },
    invalidPassword(){
      return this.userAdd.password === ''
    },
    invalidRole(){
      return this.userAdd.role === ''
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
