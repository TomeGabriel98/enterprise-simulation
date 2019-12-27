<template>
  <div class="wrapper">
    <side-bar-admin :sidebarLinks="sidebarLinks" >
      <template slot="links" >
        <sidebar-link to="/admin" name="Dashboard" icon="ti-panel"/>
        <sidebar-link to="/admin/turno" name="Turno" icon="ti-panel"/>
        <sidebar-link to="/admin/empresas" name="Empresas" icon="ti-panel"/>
      </template>
    </side-bar-admin>
    <div class="main-panel">
      <top-navbar></top-navbar>

      <dashboard-content @click.native="toggleSidebar">

      </dashboard-content>

      <content-footer></content-footer>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapMutations } from 'vuex'
export default {

  }
</script>

<style lang="scss">
</style>
<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "./MobileMenu";
import { mapMutations } from 'vuex'
  import axios from 'axios';
export default {
  components: {
    TopNavbar,
    ContentFooter,
    DashboardContent,
    MobileMenu
  },
  data(){
    return{
      nomeEmpresa: '',
      empresaID: null,
      emailEmpresa: '',
      caixa: '',
      sidebarLinks: ['dashboard', 'home'],
      show: {
        contratacao: false,
        equipes_infra: false,
        compras_vendas: false
      }
    }
  },
  methods: {
  ...mapMutations([
    'setCaixa'
  ]),
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }else{
        this.$sidebar.displaySidebar(true);
      }
    },
    setTrue(value){
      this.show[value] = !this.show[value]
    }
  },
  mounted(){
    this.nomeEmpresa = this.$session.set('empresa_nome');
    this.empresaID = this.$session.get('empresa_id');
    let app = this;
    let token = this.$session.get('authorization')
    let headers = {
      "Accept": 'application/json',
      "Authorization": "Bearer "+token,
      "Access-Control-Allow-Origin": "*"
    }
    axios.get('empresa/getById/'+this.empresaID, {headers:headers})
        .then(res => {
        app.caixa = parseFloat(res.data.caixa).toFixed(2)
        app.setCaixa(app.caixa)
        //this.usuarios = res.data
        console.log(res.data)
        })
        .catch(error => console.log(error))


    // app.$session.set('empresa_email', response.data['email'])
    // app.$session.set('empresa_nome', response.data['nome'])
  }
};
</script>

<style>
  .fade-enter-active, .fade-leave-active {
    transition: all .5s ease;
  }
  .fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }
.fade-enter, .fade-leave-to /* .fade-leave-active em versões anteriores a 2.1.8 */ {
  opacity: 0;
  transform: translateX(10px);
}
.nav-group{
  background: #303030
}
</style>
