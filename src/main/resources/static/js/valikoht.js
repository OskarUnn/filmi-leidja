Vue.component('istekoht', {
    props: ['rida', 'iste', 'hõivatud', 'valitud'],
    template: `
            <button v-on:click="pressed()" :class="{'bg-red-500': hõivatud, 'bg-green-500': valitud}" class="bg-gray-300 rounded shadow-2xl w-10 h-10 m-1"></button>
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
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        valiUuedIstmed(rida, iste) {
            // for (let key in this.istekohad) {
            //     let istekoht = this.istekohad[key];
            //     istekoht.valitud = false;
            // }
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
