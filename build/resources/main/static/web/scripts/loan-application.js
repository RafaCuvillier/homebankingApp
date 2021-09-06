const app = Vue.createApp({
    data() {
        return {
            cliente: [],
            accounts: [],
            loans: [],
            numAccount: "",
            loanId: "",
            amount: 1000,
            payments: "",
        }
    },
    created() {
        axios.get('/api/loans')
            .then(resp => {
                this.loans = resp.data
                console.log(this.loans)
                console.log(this.loans[0].payments)
            })
        axios.get('/api/clients/current')
            .then(data => {
                this.cliente = data.data
                this.accounts = this.cliente.accounts
                console.log(this.accounts)
            })
    },
    methods: {
        aplication() {
            axios.post('/api/loans', {
                    loanId: this.loanId,
                    amount: parseInt(this.amount),
                    payments: this.payments,
                    numAccount: this.numAccount

                }, {
                    headers: { 'Content-Type': 'application/json' }
                })
                .catch(error => console.log(error))
                .then(() => window.location.href = "/web/accounts.html")
            this.resetValues()
        },
        maxAmount() {
            if (this.loanId != "") {
                return this.loans.filter(e => e.id == this.loanId)[0].maxAmount
            }

        },
        logout() {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
        },
        resetValues() {
            this.amount = 0
            this.amount = 1000
            this.payments = 0
        },
        paymentsLoan() {
            if (this.loanId != "") {
                return this.loans.filter(e => e.id == this.loanId)[0].payments
            }
        },
        reset() {
            this.resetValues()
            this.loanId = null
        },
        confirm() {
            if (this.numAccount == "" || this.loanId == "" || this.amount == 0 || this.payments == 0) {
                return true
            }
            return false
        }
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