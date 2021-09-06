const app = Vue.createApp({
    data() {
        return {
            cliente: [],
            cards: [],
            cardType: '',
            cardColor: '',
        }
    },
    methods: {
        crearCard() {
            axios.post('/api/clients/current/cards' + `?cardColor=${this.cardColor}&cardType=${this.cardType}`, {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(alert("Tarjeta creada correctamente"))
                .then(() => window.location.href = "/web/cards.html")

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