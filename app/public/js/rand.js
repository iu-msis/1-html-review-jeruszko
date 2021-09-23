//const { Rand } = Vue.createAPP;
const app = ({
    data(){
        return{
            "results": {}

        }
    },
    computed: {
        bday(){
            return dayjs(this.results.dob.date).format('DD/MM/YYYY')
         }
    },

    methods: {
        fetchUserData(){
            console.log("a");
            fetch('https://randomuser.me/api/')
            .then( response => response.json())
            .then((parsedJson) => {
            this.results = parsedJson.results[0];
        })
        .catch ( err => {
            console.error(err);
        })
        }

    },
    created() {
        this.fetchUserData();
    }
})
Vue.createApp(app).mount('#app')
