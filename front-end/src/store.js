import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersist from 'vuex-persist'
import router from './router';


Vue.use(Vuex)

const vuexPersist = new VuexPersist({
  key: 'my-sec-app',
  storage: localStorage
})

export default new Vuex.Store({
  plugins: [
    vuexPersist.plugin
  ],
  state: {
    usuario: null,
    token: null,
    id: null,
    temToken: false,
    admin: false,
    valorCaixa: 0,
  },
  mutations: {
    setUsuario (state, usuario) {
      state.usuario = usuario
    },
    setId(state, id){
      state.id = id
    },
    setToken (state, token)  {
      state.token = token
      state.temToken = true
    },
    logout (state) {
      state.token = null
      state.usuario = null
      state.temToken = false
      router.push('/login');
    },
    setCaixa (state, valor){
      state.valorCaixa = valor;
    },
    setAdmin(state, admin){
      state.admin = admin;
    }
  },
  actions: {
    temAcesso:()=>{
      localStorage.getItem('temToken')
    },
    isAdmin:()=>{
      localStorage.getItem('admin')
    }
  },
  getters:{
    isAdmin: state =>{
      return state.admin
    },
    getId: state => {
      return state.id
    },
    getCaixa: state => {
      return state.valorCaixa
    },
    getUsuario: state => {
      return state.usuario
    },
    getEquipes: state => {
      return state.usuario.equipes
    }
  }
})
