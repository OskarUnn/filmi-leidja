Vue.component('filmi-kaart', {
    props: ['film'],
    template: `
        <div class="flex bg-gray-200 shadow-outline rounded w-full">
            <div class="flex-shrink-0 w-full w-1/2">
              <img :src="film.poster" alt="Image" class="h-full w-full object-cover rounded-l">
            </div>
            <div class="flex flex-row justify-between p-4 w-1/2">
                <div class="flex flex-col space-y-4">
                  <h2 class="font-bold w-40 text-3xl">{{ film.pealkiri }}</h2>
                  <p class="text-left text-2xl font-semibold">{{ film.žanrid.join(", ") }}</p>
                </div>
            </div>
          </div>
        `,
});
let app = new Vue({
    el: '#app',
    data: {
        filmid: null
    },
    mounted() {
        this.küsiAndmed();
    },
    methods: {
        küsiAndmed() {
            axios.get('/api/v1/filmid')
                .then(response => {
                    this.filmid = response.data;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        }
    }
});