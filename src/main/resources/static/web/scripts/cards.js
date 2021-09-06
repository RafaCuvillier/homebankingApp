const app = Vue.createApp({
    data() {
        return {
            cliente: [],
            cards: []
        }
    },
    methods: {
        formatDate: function(date) {
            return new Date(date).toLocaleDateString('en-gb');
        },

        ascendente() {
            return this.cuentas.sort((a, b) => { return a.id - b.id });
        },
        debito() {
            return this.cards.filter(card => card.cardType == "DEBIT")
        },
        credito() {
            return this.cards.filter(card => card.cardType == "CREDIT")
        },
        logout() {
            axios.post('/api/logout').then(response => window.location.href = "/web/index.html")
        }
    },
    created() {
        axios.get("/api/clients/current")
            .then(data => {
                console.log(data)
                this.cliente = data.data
                this.cards = this.cliente.cards
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