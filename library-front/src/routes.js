import ShowBooks from '@/components/books/ShowBooks.vue'
import ModifyBook from '@/components/books/ModifyBook.vue'
import AddBook from '@/components/books/AddBook.vue'
import ShowBookCopies from '@/components/books/ShowBookCopies.vue'

import ShowReaders from '@/components/readers/ShowReaders.vue'
import ModifyReader from '@/components/readers/ModifyReader.vue'
import AddReader from '@/components/readers/AddReader.vue'

import ShowRentals from '@/components/rentals/ShowRentals.vue'
import ModifyRental from '@/components/rentals/ModifyRental.vue'
import AddRental from '@/components/rentals/AddRental.vue'

import ShowAuthors from '@/components/authors/ShowAuthors.vue'
import ModifyAuthor from '@/components/authors/ModifyAuthor.vue'
import AddAuthor from '@/components/authors/AddAuthor.vue'


import LoginForm from '@/components/login/LoginForm.vue'
import AddUser from '@/components/login/AddUser.vue'

import { LoginCallback } from '@okta/okta-vue/'



export default
[
    {path:'/', name: 'home', component: ShowBookCopies},
    {path:'/books', name: 'book', component: ShowBooks},
    {path:'/bookCopies', name: 'bookCopies', component: ShowBookCopies},
    {path:'/updateBook/:bookId', name:"updateBook", component: ModifyBook},
    {path:'/addBook', name:"addBook", component: AddBook},

    {path:'/authors', component: ShowAuthors},
    {path:'/updateAuthor/:authorId', name:"updateAuthor", component: ModifyAuthor},
    {path:'/addAuthor', name:"addAuthor", component: AddAuthor},

    {path:'/readers', component: ShowReaders},
    {path:'/updateReader/:readerId', name:"updateReader", component: ModifyReader},
    {path:'/addReader', name:"addReader", component: AddReader},

    {path:'/rentals', component: ShowRentals},
    {path:'/updateRental/:rentalId', name:"updateRental", component: ModifyRental},
    {path:'/addRental', name:"addRental", component: AddRental},

    {path:'/login', name:"login", component: LoginForm},
    {path:'/login/callback', name: 'callback', component: LoginCallback},
    {path:'/register', name:"register", component: AddUser},
]
