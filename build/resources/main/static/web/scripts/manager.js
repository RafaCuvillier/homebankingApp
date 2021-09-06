const app = Vue.createApp({
    data() {
        return {
            clientes: [],
            clientesAMostrar: [],
            firstName: '',
            lastName: '',
            email: '',
        }
    },
    created() {
        axios.get("/clients")
            .then(data => {
                console.log(data)
                this.clientes = data.data["_embedded"].clients
                console.log(this.clientes)
            })
    },
    methods: {
        agregarCliente() {
            axios.post(), this.clientes.push({
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
            })
            this.firstName = ""
            this.lastName = ""
            this.email = ""

        },
    },
    computed: {

    },

})
app.mount("#app")