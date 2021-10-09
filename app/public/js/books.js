const BookApp = {
    data() {
      return {
        books: []
      }
    },
    computed: {},
    methods: {
        prettyDollar(n) {
            const d = new Intl.NumberFormat("en-US").format(n);
            return "$ " + d;
        }
    }
  
  }
  
  Vue.createApp(BookApp).mount('#bookApp');