const app = Vue.createApp({
    data() {
        return {
            cliente: [],
            cards: [],
            name: '',
            pwd: '',
            email: '',
            firstName: '',
            lastName: '',

        }
    },
    methods: {
        formatDate: function(date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        login: function() {
            axios.post('/api/login', `name=${this.name}&pwd=${this.pwd}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }).then(response => window.location.href = "/web/accounts.html")
        },
        register() {
            axios.post('/api/clients' + `?firstName=${this.firstName}&lastName=${this.lastName}&pwd=${this.pwd}&name=${this.name}`, {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(response => this.login())



        },
        logout() {
            axios.post('/api/logout').then(response => window.location.href = "/web/index.html")
        },
        created() {
            axios.get('/api/clients')
                .then(data => {
                    this.cliente = data.data
                    console.log(this.cliente)
                })

        },
    }
})



app.mount("#app")