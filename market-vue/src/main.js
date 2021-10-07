import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import VueApexCharts from 'vue-apexcharts'
import Vuelidate from 'vuelidate'
import VueMask from 'v-mask'
import VueRouter from 'vue-router'
import vco from "v-click-outside"
import router from './router/index'

import vueMoment from 'vue-moment'
import "@/design/index.scss";
import VueSweetalert2 from 'vue-sweetalert2';
import store from '@/state/store'
import App from './App.vue'

Vue.use(VueRouter)
Vue.use(vco)
Vue.config.productionTip = false

Vue.use(vueMoment)
Vue.use(BootstrapVue)
Vue.use(Vuelidate)
Vue.use(VueMask)
Vue.use(require('vue-chartist'))
Vue.use(VueSweetalert2)
Vue.component('apexchart', VueApexCharts)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
