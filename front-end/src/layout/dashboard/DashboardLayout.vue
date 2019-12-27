<template>
  <div class="wrapper">
    <side-bar :sidebarLinks="sidebarLinks" >
      <template slot="links" >
        <sidebar-link to="/dashboard" name="Dashboard" icon="ti-panel"/>
        <sidebar-link to="/dashboard/bancos" name="Bancos" icon="ti-shortcode"/>
        <sidebar-link to="/dashboard/atuacao" name="Atuação" icon="ti-receipt"/>

        <li class="nav-item" >
        <a @click="setTrue('contratacao')" class="nav-link dropdown-toggle"><i class="ti-files"></i> Contratação</a>
          <transition name="fade">
            <div v-if="show.contratacao == true" class="nav-group">
              <sidebar-link to="/dashboard/contratacao/equipes" name="Equipes" icon="ti-files"/>
              <sidebar-link to="/dashboard/contratacao/servicos-terceiros" name="Serviços terceiros" icon="ti-files"/>
            </div>
          </transition>
        </li>
        <li class="nav-item" >
        <a @click="setTrue('equipes_infra')" class="nav-link dropdown-toggle"><i class="ti-hand-open"></i> Equipes e infraestrutura</a>
          <transition name="fade">
            <div v-if="show.equipes_infra" class="nav-group">
              <sidebar-link to="/dashboard/equipes-infra/localidade-alocacao" name="Localidade e alocação" icon="ti-hand-open"/>
            </div>
          </transition>
        </li>
        <li class="nav-item" >
        <a @click="setTrue('compras_vendas')" class="nav-link dropdown-toggle"><i class="ti-shopping-cart-full"></i> Compras e Vendas</a>
          <transition name="fade">
            <div v-if="show.compras_vendas" class="nav-group">
              <sidebar-link to="/dashboard/compras-vendas/equipamento-celulares" name="Equipamentos Celulares" icon="shopping-cart-full"/>
              <sidebar-link to="/dashboard/compras-vendas/armazem" name="Compra de Armazém" icon="shopping-cart-full"/>
              <sidebar-link to="/dashboard/compras-vendas/venda-armazem" name="Venda de Armazém" icon="shopping-cart-full"/>
              <sidebar-link to="/dashboard/compras-vendas/erb" name="Compra de ERBs" icon="shopping-cart-full"/>
              <sidebar-link to="/dashboard/compras-vendas/venda-erbs" name="Venda de ERBs" icon="shopping-cart-full"/>
            </div>
          </transition>
        </li>

        <sidebar-link to="/notifications" name="Ajuda" icon="ti-help"/>
      </template>

      <mobile-menu>
        <li class="nav-item">
          <a href="#" class="nav-link">
            <i class="ti-panel"></i>
            <p>Caixa: R$ {{caixa}}</p>
          </a>
        </li>
        <drop-down class="nav-item"
                   title="5 Notifications"
                   title-classes="nav-link"
                   icon="ti-bell">
          <a class="dropdown-item">Notification 1</a>
          <a class="dropdown-item">Notification 2</a>
          <a class="dropdown-item">Notification 3</a>
          <a class="dropdown-item">Notification 4</a>
          <a class="dropdown-item">Another notification</a>
        </drop-down>
        <li class="nav-item">
          <a @click="$router.push('/dashboard/settings/empresa')" class="nav-link">
            <i class="ti-settings"></i>
            <p>Settings</p>
          </a>
        </li>
        <li class="divider"></li>
      </mobile-menu>
    </side-bar>
    <side-bar-admin :sidebarLinks="sidebarLinks" >
      <template slot="links" >
        <sidebar-link to="/dashboard" name="Dashboard" icon="ti-panel"/>
        <sidebar-link to="/dashboard/admin/turno" name="Turno" icon="ti-panel"/>
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
