import Sidebar from "./SideBar.vue";
import SidebarAdmin from "./SideBarAdmin.vue";
import SidebarLink from "./SidebarLink";

const SidebarStore = {
  showSidebar: false,
  sidebarLinks: [''],
  displaySidebar(value) {
    this.showSidebar = value;
  }
};

const SidebarPlugin = {
  install(Vue) {
    let app = new Vue({
      data: {
        sidebarStore: SidebarStore
      }
    });

    Vue.prototype.$sidebar = app.sidebarStore;
    Vue.component("side-bar", Sidebar);
    Vue.component("side-bar-admin", SidebarAdmin);
    Vue.component("sidebar-link", SidebarLink);
  }
};

export default SidebarPlugin;
