Vue.component('istekoht', {
    props: ['rida', 'iste', 'hõivatud', 'valitud'],
    template: `
            <button v-on:click="pressed()" :class="{'bg-red-500': hõivatud, 'bg-green-500': valitud}" class="bg-gray-300 rounded shadow-2xl w-10 h-10 m-1"></button>
        `,
    created() {
        this.valitud = false;
        app.istekohad[this.rida + "-" + this.iste] = this;
        console.log(app.istekohad);
    },
    methods: {
        pressed() {
            if (!this.valitud && !this.hõivatud) {
                // app.istekohad[(this.rida) + "-" + (this.iste+1)].valitud = true;
                app.valiUuedIstmed(this.rida, this.iste);
            }
        }
    }
});
let app = new Vue({
    el: '#app',
    data: {
        seanss: null,
        istekohad: {}
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
        },
        valiUuedIstmed(rida, iste) {
            for (let key in this.istekohad) {
                let istekoht = this.istekohad[key];
                istekoht.valitud = false;
            }
            this.istekohad[rida + "-" + iste].valitud = true;
        }
    }
});