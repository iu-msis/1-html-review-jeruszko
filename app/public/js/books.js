const BookApp = {
    data() {
      return {
        books: [],
        selectedBook: null, //maybe
        bookForm: {}
      }
    },
    computed: {},
    methods: {
        prettyDollar(n) {
            const d = new Intl.NumberFormat("en-US").format(n);
            return "$ " + d;
        },
        fetchBookData() {
            fetch('/api/books/')
            .then( response => response.json() )
            .then( (responseJson) => {
                console.log(responseJson);
                this.books = responseJson;
            })
            .catch( (err) => {
                console.error(err);
            })
        },
        postNewBook(evt) {
            //this.bookForm.id = this.selectedBook.id; //not sure about this
            console.log("Posted!", this.bookForm); //test to make sure form works  //use cmd+shift+r to make sure
            alert("Post");

            fetch('api/books/create.php', {
                method:'POST',
                body: JSON.stringify(this.bookForm),
                headers: {
                  "Content-Type": "application/json; charset=utf-8"
                }
              })
              .then( response => response.json() )
              .then( json => {
                console.log("Returned from post:", json);
                // TODO: test a result was returned!
                this.books = json; //idk
                
                // reset the form
                this.bookForm = {};
              });

            
            }
        
    },
    created() {
            this.fetchBookData();
        }
  
  }
  
  Vue.createApp(BookApp).mount('#bookApp');