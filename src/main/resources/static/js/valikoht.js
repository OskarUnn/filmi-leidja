Vue.component('istekoht', {
    props: ['rida', 'iste', 'hõivatud', 'valitud'],
    template: `
            <button v-on:click="valiIste()" :class="{'bg-red-500': hõivatud, 'bg-green-500': valitud}" class="bg-gray-300 rounded shadow-2xl w-10 h-10 m-1"></button>
        `,
    created() {
        this.valitud = false;
    },
    methods: {
        valiIste() {
            if (!this.valitud && !this.hõivatud) {
                this.valitud = !this.valitud;
            }
        }
    }
});
let app = new Vue({
    el: '#app',
    data: {
        seanss: null
    },
    mounted() {
        this.küsiAndmed();
    },
    methods: {
        küsiAndmed() {
            axios.get('/api/v1/seanss/'+ seanssID)
                .then(response => {
                    this.seanss = response.data;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        }
    }
});