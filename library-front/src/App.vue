<template>
  <div>
    <Nav :navLinks="navLinks" :navConfig="navConfig" :btnConfig="btnConfig">
      <div class = "login-wrapper">
        <h3 v-if="isLogedIn" style="color: white;">Welcome, {{this.login}}! <button @click="logout()">Log out </button></h3>
        <h3 style="color: white;" v-else>Not logged in <router-link to="/login"><button>Log in</button></router-link> <router-link to="/register"><button>Register</button></router-link></h3>
      </div>
      <img class="img" src="logo" alt="" srcset="" /> <!-- A slot For your project Logo -->
    </Nav>
  </div>
  <div class = "contents-container">
  <h1></h1>

    <router-view> </router-view>
  </div>

</template>

<script>
import Nav from "vue-nav-ui"

export default {
  name: 'App',
  components: {
    Nav
  },
  data() {
    return {
      login:'',
      role:'',
      /* FOR YOUR NAVIGATION LINKING NAMES AND PATHS */
            navLinks: [],
            navConfig: {
                whitespace: false, /* GIVES PADDING TO YOUR NAV, IF SET TO FALSE, REMOVES PADDING */
                navBg: "#074d20", /* BACKGROUND COLOR OF YOUR NAV  */
                navBorderRadius: "30px", /* BORDER RADIUS OF YOUR NAV */
                linkFont: "poppins", /* FONT FAMILY OF YOUR NAV */
                linkColor: "white", /* FONT COLOR OF YOUR NAV */
                responsivePosition: "top or bottom", /* FOR CHANGING THE POSITION OF YOUR NAV WHEN RESPONSIVE. BOTTOM or TOP | The only two options */
            },
            btnConfig: {
                btnLink: false, /* FOR INITIALIZING NAV BUTTON USAGE, IF SET TO FALSE, REMOVES THE NAV BUTTON  */
                btnUrl: "https://dhaniel.disha.page", /* LINK URL OF YOUR NAV BUTTON */
                btnText: "Download app", /* NAV BUTTON TEXT */
                btnBg: "#40269E", /* BACKGROUND COLOR OF YOUR NAV BUTTON  */
                btnTextColor: "white", /* FONT COLOR OF YOUR NAV BUTTON*/
                btnBorderWidth: "0", /* BORDER WIDTH OF YOUR NAV BUTTON */
                btnBorderColor: "black", /* BORDER COLOR OF YOUR NAV BUTTON */
                btnBorderRadius: "20px", /* BORDER RADIUS OF YOUR NAV BUTTON */
            }
    }
  },
  computed: {
    isLogedIn(){
      return localStorage.getItem('login') != null
    },
    isLibrarian(){
      return localStorage.getItem('role') == "librarian"
    },
    isManager(){
      return localStorage.getItem('role') == "manager"
    },
  },
  methods: {
    async logout() {
      localStorage.removeItem('login')
      localStorage.removeItem('role')
      await this.$auth.signOut()
      this.$router.push('/');
    },
    async refreshUserSSO()
    {
      console.log((await this.$auth.session.get()).status)
        console.log(await this.$auth.getUser())
      if((await this.$auth.session.get()).status === 'ACTIVE')
        localStorage.setItem('login', (await this.$auth.getUser()).name)
    }
  },
  mounted()
  {
    this.login = localStorage.getItem('login')
    this.role = localStorage.getItem('role')
    this.refreshUserSSO()

    if(this.isLogedIn)
    {
      this.navLinks = [
          {
              name: "Home",
              path: "/",
          },
          {
              name: "Book Copies",
              path: "/bookCopies",
          },
          {
              name: "Authors",
              path: "/authors",
          },
          {
              name: "Books",
              path: "/books",
          },
          {
              name: "Readers",
              path: "/readers",
          },
          {
              name: "Rentals",
              path: "/rentals",
          },
      ]
    }
    else
    {
      this.navLinks = [
          {
              name: "Home",
              path: "/",
          },
          {
              name: "Book Copies",
              path: "/bookCopies",
          },
      ]
    }
  },

}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
button {
background: #009435;
border: 1px solid #009435;
}
.contents-container {
max-width: 980px;
margin: auto;
}

.form-container {
max-width: 680px;
margin: auto;
}

.login-wrapper {
  display: flex;
  /*We want 1 row and we dont want items to wrap into other rows*/
  flex-flow: row nowrap;
  /*Positions elements to the start, end and whatever is between while keeping some space between them */
  justify-content: space-between;
  /*You can add this if you also want to horizontally align items*/
  align-items: center;
}
/* The alert message box */
.alert {
 padding: 20px;
 background-color: #f44336; /* Red */
 color: white;
 margin-bottom: 15px;
}

/* The close button */
.closebtn {
 margin-left: 15px;
 color: white;
 font-weight: bold;
 float: right;
 font-size: 22px;
 line-height: 20px;
 cursor: pointer;
 transition: 0.3s;
}

/* When moving the mouse over the close button */
.closebtn:hover {
 color: black;
}
</style>
