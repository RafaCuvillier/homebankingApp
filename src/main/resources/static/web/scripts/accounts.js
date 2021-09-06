const app = Vue.createApp({
    data() {
        return {
            cliente: [],
            clientesAMostrar: [],
            cuentas: [],
            firstName: '',
            lastName: '',
            email: '',
            prestamos: [],
            mostrar: true,
        }
    },
    methods: {
        formatDate: function(date) {
            return new Date(date).toLocaleDateString('en-gb');
        },

        ascendente() {
            return this.cuentas.sort((a, b) => { return a.id - b.id });
        },
        logout() {
            axios.post('/api/logout').then(response => window.location.href = "/web/index.html")
        },
        create() {
            axios.post('/api/clients/current/accounts').then(response => window.location.href = "/web/accounts.html")
        }


    },
    created() {
        axios.get("/api/clients/current")
            .then(data => {
                console.log(data)

                this.cliente = data.data
                this.firstName = this.cliente.firstName
                this.lastName = this.cliente.lastName
                this.email = this.cliente.email
                this.cuentas = this.cliente.accounts
                this.prestamos = this.cliente.loans
                console.log(this.cliente)
                console.log(this.cuentas)
                console.log(this.prestamos)
            })

    }
})
app.mount("#app")

var menu = document.querySelector('.hamburger');

// method
// 
function toggleMenu(event) {
    this.classList.toggle('is-active');
    document.querySelector(".menuppal").classList.toggle("is_active");
    event.preventDefault();
}

// event
menu.addEventListener('click', toggleMenu, false);