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
            <!-- Content container -->
            <div class="flex flex-row justify-between p-4 w-2/3">
              <h2 class="font-bold w-40 text-2xl">{{ seanss.film.pealkiri }}</h2>

              <div class="flex flex-col">
                <p class="text-left">{{ seanss.algus }}</p>
                <p class="text-left">{{ seanss.film.žanrid }}</p>
                <div class="flex flex-row items-center">
                    <p class="font-bold mr-2">Kohtade arv:</p>
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
        seanssid: null
    },
    mounted() {
        this.küsiAndmed();
    },
    methods: {
        küsiAndmed() {
            axios.get('/api/v1/kinokava')
                .then(response => {
                    this.seanssid = response.data;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        }
    }
});