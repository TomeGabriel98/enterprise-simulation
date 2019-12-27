<template>
  <div :class="'sidebar '+getSidebar()"
       :data-background-color="backgroundColor"
       :data-active-color="activeColor" v-show="$store.getters.isAdmin">

    <div class="sidebar-wrapper" id="style-3">
      <div class="logo">
        <a href="#" class="simple-text">
            <div class="logo-img">
                <!-- <img src="@/assets/img/vue-logo.png" alt=""> -->
                <img :src="logo" alt="">
            </div>
          {{nomeEmpresa}}
        </a>
      </div>
      <slot>

      </slot>
      <ul class="nav">
        <slot name="links">
          <sidebar-link v-for="(link,index) in sidebarLinks"
                        :key="index"
                        :to="link.path"
                        :name="link.name"
                        :icon="link.icon">
          </sidebar-link>
        </slot>
      </ul>
      <moving-arrow :move-y="arrowMovePx">

      </moving-arrow>
    </div>
  </div>
</template>
<script>
import MovingArrow from "./MovingArrow.vue";
import SidebarLink from "./SidebarLink";
import axios from 'axios';

export default {
  props: {
    title: {
      type: String,
      default: "Paper Dashboard"
    },
    backgroundColor: {
      type: String,
      default: "black",
      validator: value => {
        let acceptedValues = ["white", "black", "darkblue"];
        return acceptedValues.indexOf(value) !== -1;
      }
    },
    activeColor: {
      type: String,
      default: "success",
      validator: value => {
        let acceptedValues = [
          "primary",
          "info",
          "success",
          "warning",
          "danger"
        ];
        return acceptedValues.indexOf(value) !== -1;
      }
    },
    sidebarLinks: {
      type: Array,
      default: () => []
    },
    autoClose: {
      type: Boolean,
      default: true
    }
  },
  provide() {
    return {
      autoClose: this.autoClose,
      addLink: this.addLink,
      removeLink: this.removeLink
    };
  },
  components: {
    MovingArrow,
    SidebarLink
  },
  computed: {
    /**
     * Styles to animate the arrow near the current active sidebar link
     * @returns {{transform: string}}
     */
    arrowMovePx() {
      return this.linkHeight - 75//* this.activeLinkIndex;
    }
  },
  data() {
    return {
      linkHeight: 65,
      activeLinkIndex: 0,
      windowWidth: 0,
      isWindows: false,
      hasAutoHeight: false,
      links: [],
      logo: '',
      nomeEmpresa: ''
    };
  },
  methods: {
    findActiveLink() {
      this.links.forEach((link, index) => {
        if (link.isActive()) {
          this.linkHeight = link.$vnode.elm.getBoundingClientRect().y;
          this.activeLinkIndex = index;
        }
      });
    },
    addLink(link) {
      const index = this.$slots.links.indexOf(link.$vnode);
      this.links.splice(index, 0, link);
    },
    removeLink(link) {
      const index = this.links.indexOf(link);
      if (index > -1) {
        this.links.splice(index, 1);
      }
    },
    getSidebar(){
      return (this.$sidebar.showSidebar)?' ':'sidebar-d-none'
    }
  },
  mounted() {
    this.$watch("$route", this.findActiveLink, {
      immediate: true
    });
    let app = this;
    this.empresaLogo = this.$session.get('logo_id')
    let empresaID = this.$session.get('empresa_id')
    if(this.empresaLogo){
      axios.get('file/downloadFile/'+this.empresaLogo).then(function(res){
        app.logo = 'data:'+res.headers['content-type']+';base64,'+res.data;
      })
    }
    axios.get('empresa/getById/'+empresaID).then(res => {
      app.nomeEmpresa = res.data.nome;
    })
  }
};
</script>
<style>
  .logo-img img{
    width: 40px;
    max-width: 40px !important;
    height: auto;
    max-height: 40px !important;
  }
</style>
