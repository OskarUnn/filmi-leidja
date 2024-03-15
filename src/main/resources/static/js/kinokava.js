Vue.component('seanssi-filter', {
    props: ['kategooria', 'valikud'],
    data() {
        return {
            valitud: []
        }
    },
    template: `
        <div class="flex flex-col items-start mr-8">
            <h3 class="text-xl font-semibold mb-2">{{ kategooria }}</h3>
            <label v-for="valik in valikud" :key="valik" class="flex items-center">
                <input type="checkbox" :value="valik" class="mr-2 leading-tight" @change="filterMuutus($event, valik)">
                <span class="text-sm">{{ valik }}</span>
            </label>
        </div>
    `,
    methods: {
        filterMuutus(event, valik) {
            if (this.valitud.includes(valik)) {
                this.valitud = this.valitud.filter(item => item !== valik);
            } else {
                this.valitud.push(valik);
            }

            app.rakendaFilter(this.kategooria, this.valitud);
        }
    }
});

Vue.component('seanssi-kaart', {
    props: ['seanss'],
    data() {
        return {
            valitudKohti: 0
        };
    },
    template: `
        <div class="flex bg-gray-200 shadow-outline rounded min-w-min">
            <div class="relative flex-shrink-0 h-full w-1/3">
                <img :src="seanss.film.poster" alt="Image" class="h-full w-full object-cover rounded-l">
                <div class="absolute bottom-0 right-0 p-2 bg-black bg-opacity-50 text-white rounded-bl">
                    <p class="text-xs">{{ seanss.film.vanusepiirang }}</p>
                </div>
            </div>
            <div class="flex flex-row justify-between p-4 w-2/3">
                <div class="flex flex-col">
                    <h2 class="font-bold w-40 text-2xl">{{ seanss.film.pealkiri }}</h2>
                    <p class="text-left text-lg font-semibold">{{ seanss.film.žanrid.join(", ") }}</p>
                    <p class="text-left text-lg font-semibold">Keel: {{ seanss.keel }}</p>
                </div>
                <div class="flex flex-col">
                    <p class="text-left text-xl font-semibold">{{ kuupäev(seanss.algus) }}</p>
                    <div class="flex flex-row items-center">
                        <p class="text-xl mr-2">Kohtade arv:</p>
                        <input type="number" min="0" v-model="valitudKohti" class="mt-2 border border-gray-400 rounded px-2 py-1 w-16">
                    </div>
                </div>
                <button v-on:click="valiSeanss(seanss.id, valitudKohti)"
                        class="self-center bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                    Vali seanss
                </button>
            </div>
        </div>
    `,
    methods: {
        valiSeanss(id, kohtadeArv) {
            if (kohtadeArv <= 0) {
                return;
            }
            window.location.href = '/valikoht/' + id + "?kohti=" + kohtadeArv;
        }
    }
});

let app = new Vue({
    el: '#app',
    data: {
        seanssid: null,
        žanrid: null,
        keeled: null,
        vanusepiirangud: null,
        algusAeg: 9,
        otsinguKitsendus: {
            žanrid: [],
            algus: this.algusAeg,
            keeled: [],
            vanusepiirangud: [],
        },
    },
    mounted() {
        this.küsiAndmed();
        this.küsiŽanrid();
        this.küsiKeeled();
        this.küsiVanusepiirangud();
    },
    computed: {
        filtreeritudSeanssid() {
            if (this.seanssid === null) {
                return null;
            }
            return this.seanssid;
        },
        formatTime() { // ChatGPT abil koostatud funktsioon
            const hours = this.algusAeg;
            const minutes = 0;
            return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;
        },
    },
    methods: {
        küsiAndmed() {
            axios.post('/api/v1/kinokava', this.otsinguKitsendus)
                .then(response => {
                    this.seanssid = response.data;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        küsiŽanrid() {
            axios.get('/api/v1/žanrid')
                .then(response => {
                    this.žanrid = response.data;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        küsiKeeled() {
            axios.get('/api/v1/keeled')
                .then(response => {
                    this.keeled = response.data;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        küsiVanusepiirangud() {
            axios.get('/api/v1/vanusepiirangud')
                .then(response => {
                    this.vanusepiirangud = response.data;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        rakendaFilter(kategooria, väärtus) {
            if (kategooria === 'Žanrid') {
                this.otsinguKitsendus.žanrid = väärtus;
            } else if (kategooria === 'Keel') {
                this.otsinguKitsendus.keeled = väärtus;
            } else if (kategooria === 'Vanusepiirang') {
                this.otsinguKitsendus.vanusepiirangud = väärtus;
            }
            this.küsiAndmed();
        },
        filtreeriAlgusAeg(event) {
            this.otsinguKitsendus.algus = this.algusAeg;
            this.küsiAndmed();
        },
    }
});

function kuupäev(dateTimeString) { // ChatGPT abil loodud funktsioon
    const date = new Date(dateTimeString);
    const options = { weekday: 'short', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', timeZone: 'Europe/Tallinn' };
    return date.toLocaleDateString('et-EE', options);
}