<template>
  <div class="content">
      <div class="container">
        <div class="">

    <b-card title="Cadastrar" class="novo-turno">
      <b-form-input type="text" v-model="nomeTurno"></b-form-input>
      <b-button type="button" name="button" @click="addTurno">Cadastrar turno</b-button>
    </b-card>
    <b-table responsive :items="turnos" :fields="tFields">
      <template v-slot:cell(acoes)="data">
        <span @click="excluir(data.item.id)"><button class="btn btn-danger"><i class="fas fa-times"></i></button></span>
        <span v-if="!data.item.turno_bloqueado" @click="travar(data.item.id)"><button class="btn btn-warning"><i class="fas fa-ban"></i></button></span>
        <span v-else @click="mudar(data.item.id)"><button class="btn btn-warning"><i class="far fa-arrow-alt-circle-right fa-2x"></i></button></span>
        <b-form-checkbox v-model="data.detailsShowing" @change="data.toggleDetails">
          Details via check
        </b-form-checkbox>
      </template>
      <template v-slot:row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Empresas:</b></b-col>
            {{row.item.empresas}}
          </b-row>
        </b-card>
      </template>
    </b-table>
    </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapMutations } from 'vuex'
export default{
  name: 'Turno',
  data(){
    return{
      nomeTurno: '',
      admin: null,
      turnos: [],
      tFields:[
        {
          key: 'nome',
          label: 'Nome'
        },
        {
          key: 'indice',
          label: 'Indice'
        },
        {
          key: 'data_inicio',
          label: 'Data de inicio'
        },
        {
          key: 'turno_bloqueado',
          label: 'Bloqueado'
        },
        {
          key: 'acoes',
          label: 'Ações'
        }
      ]
    }
  },
  methods:{
    ...mapMutations([
      'setUsuario'
    ]),
    addTurno(){
      let app = this
      let body = {}
      body.nome = this.nomeTurno
      axios.post('turno/novoTurno/'+this.admin.id, body).then( res => {
        console.log(res)
        app.setUsuario = (res.data)
        app.turnos = res.data.adminTurno
        app.nomeTurno = ''
      })
    },
    excluir(id){
      let app = this
      axios.delete('turno/apagaTurno/'+id).then(res => {
        app.setUsuario = res.data
        app.turnos = res.data.adminTurno
      }).catch(err => {
        console.log(err)
      })
    },
    travar(id){
      let app = this
      axios.post('turno/travarTurno/'+id).then(res => {
        app.turnos = []
        app.setUsuario = res.data
        app.turnos = res.data.adminTurno
      }).catch(err => {
        console.log(err)
      })
    },
    mudar(id){
      let app = this
      axios.post('turno/mudarTurno/'+id).then(res => {
        app.turnos = []
        app.setUsuario = res.data
        app.turnos = res.data.adminTurno
      }).catch(err => {
        console.log(err)
      })
    }
  },
  mounted(){
    this.admin = this.$store.getters.getUsuario;
    this.turnos = this.admin.adminTurno
  }
}
</script>

<style lang="scss">
@import "../../assets/sass/paper/_variables.scss";
.content{
    align-items: center;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}
.content, .footer{
  display: contents
}
.button-cancel {
  background-color: $cyan;
}
.container {
    width: 90%;
    height: 500px;
    background: white;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    // align-items: center;
    border-radius: 8px;
    box-shadow: 0 2px 2px rgba(204, 197, 185, 0.5);
}
.novo-turno input{
  border: 1px solid $gray;
}
.footer{
  position: absolute !important;
}
</style>
