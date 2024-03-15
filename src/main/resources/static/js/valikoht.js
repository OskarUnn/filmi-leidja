Vue.component('istekoht', {
    props: ['rida', 'iste', 'hõivatud', 'valitud'],
    template: `
            <button v-on:click="pressed()" :class="{'bg-red-500': hõivatud, 'bg-green-500': valitud}" class="bg-gray-300 rounded shadow-2xl w-8 h-8 m-1"></button>
        `,
    created() {
        this.valitud = false;
        app.istekohad[this.rida + "-" + this.iste] = this;
    },
    methods: {
        pressed() {
            if (!this.hõivatud) {
                app.valiUuedIstmed(this.rida, this.iste);
            }
        }
    }
});
let app = new Vue({
    el: '#app',
    data: {
        seanss: null,
        istekohad: {},
        valitudKohad: [],
    },
    mounted() {
        this.küsiAndmed();
    },
    methods: {
        küsiAndmed() {
            axios.get('/api/v1/seanss/' + seanssID)
                .then(response => {
                    this.seanss = response.data;
                    this.küsiParimadKohad();
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        küsiParimadKohad() {
            axios.get('/api/v1/parimadKohad/' + seanssID + "?kohti=" + kohtadeArv)
                .then(response => {
                    console.log(response.data);
                    if (response.data !== null) {
                        this.valiKohad(response.data);
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        kinnitaKohad() {
            if (this.valitudKohad.length === kohtadeArv) {
                var kohad = []

                for (let valitudKohadKey in this.valitudKohad) {
                    let iste = this.valitudKohad[valitudKohadKey];
                    let koht = iste.rida + "-" + iste.iste;
                    kohad.push(koht);
                }
                const data = {kohad: kohad};
                console.log(data);
                axios.post('/api/v1/valikohad/' + seanssID, kohad)
                    .then(response => {
                        window.location.href = '/' + response.data;
                    })
                    .catch(error => {
                        console.error('Error fetching data:', error);
                    });
            }
        },
        valiKohad(kohad) {
            for (let i = 0; i < kohad.length; i++) {
                let istekoht = this.istekohad[kohad[i]];
                this.valitudKohad.push(istekoht);
                istekoht.valitud = true;
            }
        },
        valiUuedIstmed(rida, iste) {
            let istekoht = this.istekohad[rida+"-"+iste];
            if (istekoht.valitud) {
                this.valitudKohad = this.valitudKohad.filter(item => item !== istekoht);
                istekoht.valitud = false;
            } else if (this.valitudKohad.length < kohtadeArv) {
                this.valitudKohad.push(istekoht);
                istekoht.valitud = true;
            }
        }
    }
});

function kuupäev(dateTimeString) { // ChatGPT abil loodud funktsioon
    const date = new Date(dateTimeString);
    const options = { weekday: 'long', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', timeZone: 'Europe/Tallinn' };
    return date.toLocaleDateString('et-EE', options);
}
