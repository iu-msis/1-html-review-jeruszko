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

            
            },
            postBook(evt) {
              if (this.selectedBook === null) {
                  this.postNewBook(evt);
              } else {
                  this.postEditBook(evt);
              }
            },
            postEditBook(evt) {
              // this.offerForm.studentId = this.selectedStudent.id;
              this.bookForm.id = this.selectedBook.id;       
              
              console.log("Updating!", this.bookForm);
      
              fetch('api/books/update.php', {
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
                  this.books = json;
                  
                  this.resetBookForm();
                });
            },
            postDeleteBook(o) {
              if (!confirm("Are you sure you want to delete the offer from "+o.rname+"?")) {
                  return;
              }
              
              fetch('api/books/delete.php', {
                  method:'POST',
                  body: JSON.stringify(o),
                  headers: {
                    "Content-Type": "application/json; charset=utf-8"
                  }
                })
                .then( response => response.json() )
                .then( json => {
                  console.log("Returned from post:", json);
                  // TODO: test a result was returned!
                  this.books = json;
                  
                  this.resetBookForm();
                });
            },
            selectBook(o) {
              this.selectedBook = o;
              this.bookForm = Object.assign({}, this.selectedBook);
            },
            resetBookForm() {
              this.selectedBook = null;
              this.bookForm = {};
            }
        
    },
    created() {
            this.fetchBookData();
        }
  
  }
  
  Vue.createApp(BookApp).mount('#bookApp');