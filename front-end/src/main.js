/*!

 =========================================================
 * Vue Paper Dashboard - v2.0.0
 =========================================================

 * Product Page: http://www.creative-tim.com/product/paper-dashboard
 * Copyright 2019 Creative Tim (http://www.creative-tim.com)
 * Licensed under MIT (https://github.com/creativetimofficial/paper-dashboard/blob/master/LICENSE.md)

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 */
import Vue from "vue";
import App from "./App";
import router from "./router";
import store from './store'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import axios from 'axios'

import PaperDashboard from "./plugins/paperDashboard";
import "vue-notifyjs/themes/default.css";
import vueSession from 'vue-session'
import BootstrapVue from 'bootstrap-vue'

import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { faGlobe } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faUserSecret)
library.add(faGlobe)

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue)

Vue.use( vueSession )
Vue.use( PaperDashboard );


//axios.defaults.baseURL = 'https://projetofatecapi.herokuapp.com/springRest/'

axios.defaults.baseURL =  process.env.VUE_APP_ROOT_URL
//
axios.interceptors.request.use(config => {
  if(store.state.token) {
    axios.defaults.Authorization = store.state.token
  }
  return config
})
// axios.interceptors.response.use(res => {
//     return res
//   }, error => {
//     if(error.response.status === 403) {
//       alert('Não autorizado!')
//       store.commit('logout')
//       router.push('/login')
//     }
//     else if (error.response.status === 401) {
//       store.commit('logout')
//       router.push('/login')
//     }
//     throw error
// })
// Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
