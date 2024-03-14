Vue.component('seanssi-filter', {
    props: ['kategooria', 'valikud'],
    template: `
        <div class="flex flex-col items-start mr-8">
            <h3 class="text-xl font-semibold mb-2">{{ kategooria }}</h3>
            <label v-for="valik in valikud" :key="valik" class="flex items-center">
                <input type="checkbox" :value="valik" class="mr-2 leading-tight" @change="rakendaFilter">
                <span class="text-sm">{{ valik }}</span>
            </label>
        </div>
    `,
    methods: {
        rakendaFilter() {
            console.log("AAA");
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
            <div class="flex-shrink-0 h-full w-1/3">
              <img :src="seanss.film.poster" alt="Image" class="h-full w-full object-cover rounded-l">
            </div>
            <div class="flex flex-row justify-between p-4 w-2/3">
                <div class="flex flex-col">
                  <h2 class="font-bold w-40 text-2xl">{{ seanss.film.pealkiri }}</h2>
                  <p class="text-left text-xl font-semibold">{{ seanss.film.žanrid.join(", ") }}</p>
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
        otsinguKitsendus: {
            žanrid: [],
            algus: ""
        }
    },
    mounted() {
        this.küsiAndmed();
        this.küsiŽanrid();
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
        }
    }
});

function kuupäev(dateTimeString) { // ChatGPT abil loodud funktsioon
    const date = new Date(dateTimeString);
    const options = { weekday: 'short', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', timeZone: 'Europe/Tallinn' };
    return date.toLocaleDateString('et-EE', options);
}