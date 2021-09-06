const app = Vue.createApp({
    data() {
        return {
            cliente: [],
            cards: [],
            cuentas: [],
            selected: '',
            fromAccountNumber: '',
            toAccountNumber: '',
            amount: '',
            description: '',
        }
    },
    methods: {

        logout() {
            axios.post('/api/logout').then(response => window.location.href = "/web/index.html")
        },
        transfer() {
            axios.post('/api/transactions' + `?selected=${this.selected}&fromAccountNumber=${this.fromAccountNumber}&toAccountNumber=${this.toAccountNumber}&amount=${this.amount}&description=${this.description}`, {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(alert("Transferencia realizada correctamente"))
                .then(() => window.location.href = "/web/accounts.html")
        }
    },
    created() {
        axios.get("/api/clients/current")
            .then(data => {
                console.log(data)
                this.cliente = data.data
                this.cards = this.cliente.cards
                this.cuentas = this.cliente.accounts
                console.log(this.cards)

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