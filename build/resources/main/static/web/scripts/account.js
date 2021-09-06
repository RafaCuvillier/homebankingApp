const app = Vue.createApp({
    data() {
        return {
            cuentas: [],
            transacciones: []

        }
    },
    methods: {
        formatDate: function(date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        descendente() {
            return this.transacciones.sort((a, b) => { return b.id - a.id });
        }

    },
    created() {
        const urlParams = new URLSearchParams(window.location.search)
        const myParam = urlParams.get('id')
        axios.get('/api/accounts/' + myParam)
            .then(data => {
                this.cuentas = data.data
                this.transacciones = this.cuentas.transactions
                console.log(this.cuentas)
                console.log(this.transacciones)


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
menu.addEventListener('click', this.toggleMenu, false);