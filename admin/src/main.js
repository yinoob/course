import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios"

import $ from 'jquery';
window.jQuery = $;
window.$ = $;

import Axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, Axios)
Vue.config.productionTip = false

import qs from 'qs'
Vue.prototype.$qs = qs

Vue.config.productionTip=false
Vue.prototype.$ajax=axios

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
