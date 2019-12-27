import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/pages/Login.vue";
import DashboardLayout from "@/layout/dashboard/DashboardLayout.vue";
import DashboardLayoutAdmin from "@/layout/dashboardAdmin/DashboardLayout.vue";
// GeneralViews
import NotFound from "@/pages/NotFoundPage.vue";

// Usuario pages
import Dashboard from "@/pages/Usuario/Dashboard.vue";
import UserProfile from "@/pages/UserProfile.vue";
import Notifications from "@/pages/Notifications.vue";
import Icons from "@/pages/Icons.vue";
import Maps from "@/pages/Maps.vue";
import Typography from "@/pages/Typography.vue";
import TableList from "@/pages/TableList.vue";
import Atuacao from "@/pages/Usuario/Atuacao.vue";
import EquipamentosCelulares from "@/pages/Usuario/EquipamentosCelulares.vue";
import CompraERBs from "@/pages/Usuario/CompraERBs.vue";
import CompraArmazem from "@/pages/Usuario/CompraArmazem.vue";
import Equipe from "@/pages/Usuario/Equipe.vue";
import ServicoTerceiro from "@/pages/Usuario/ServicoTerceiro.vue";
import Bancos from "@/pages/Usuario/Bancos.vue";
import LocalidadeAlocacao from "@/pages/Usuario/LocalidadeAlocacao.vue";
import VendaArmazem from "@/pages/Usuario/VendaArmazem.vue";
import VendaERBs from "@/pages/Usuario/VendaERBs.vue";
import DemitirEquipe from "@/pages/Usuario/DemitirEquipe.vue";
import Empresa from "@/pages/Usuario/Empresa.vue";

// Admin pages
import DashboardAdmin from "@/pages/Admin/Dashboard.vue";
import Turno from "@/pages/Admin/Turno.vue";
import Empresas from "@/pages/Admin/Empresas.vue";

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: "/login",
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    { path: "*", component: NotFound },
    {
      path: "/admin",
      name: "Admin",
      component: DashboardLayoutAdmin,
      children:[
        {
          path: "/admin",
          name: "dashboard",
          component: DashboardAdmin,
        },
        {
          path: "/admin/turno",
          name: "turno",
          component: Turno
        },
        {
          path: "/admin/empresas",
          name: "empresas",
          component: Empresas
        }
      ]
    },
    {
    path: "/dash",
    redirect: "/dashboard",
    component: DashboardLayout,
    children: [
      {
        path: "/dashboard",
        name: "dashboard",
        component: Dashboard,
      },
      {
        path: "/dashboard/stats",
        name: "stats",
        component: UserProfile
      },
      {
        path: "/dashboard/contratacao/servicos-terceiros",
        name: "servicos-terceiros",
        component: ServicoTerceiro
      },
      {
        path: "/dashboard/contratacao/equipes",
        name: "Tipo da Equipe",
        component: Equipe
      },
      {
        path: "/dashboard/settings/empresa",
        name: "Empresa",
        component: Empresa
      },
      {
        path: "/dashboard/bancos",
        name: "Bancos",
        component: Bancos
      },
      {
        path: "/dashboard/notifications",
        name: "notifications",
        component: Notifications
      },
      {
        path: "/dashboard/icons",
        name: "icons",
        component: Icons
      },
      {
        path: "/dashboard/maps",
        name: "maps",
        component: Maps
      },
      {
        path: "/dashboard/atuacao",
        name: "atuacao",
        component: Atuacao
      },
      {
        path: "/dashboard/compras-vendas/equipamento-celulares",
        name: "Equipamentos Celulares",
        component: EquipamentosCelulares
      },
      {
        path: "/dashboard/compras-vendas/armazem",
        name: "Compra Armazem",
        component: CompraArmazem
      },
      {
        path: "/dashboard/compras-vendas/venda-armazem",
        name: "Venda Armazem",
        component: VendaArmazem
      },
      {
        path: "/dashboard/compras-vendas/erb",
        name: "Compra ERBs",
        component: CompraERBs
      },
      {
        path: "/dashboard/compras-vendas/venda-erbs",
        name: "Venda ERBs",
        component: VendaERBs
      },
      {
        path: "/dashboard/equipes-infra/localidade-alocacao",
        name: "Localidade e Alocacao",
        component: LocalidadeAlocacao
      },
      {
        path: "/dashboard/typography",
        name: "typography",
        component: Typography
      },
      {
        path: "/dashboard/table-list",
        name: "table-list",
        component: TableList
      },
      {
      path: "/dashboard/admin/turno",
      name: 'turno',
      component: Turno
      }
    ]
  },

  ]

})


export default router;
