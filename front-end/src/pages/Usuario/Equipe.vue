<template>
    <div class="content">
      <div class="card col-md-7">
        <h2> Equipes contratadas </h2>
        <b-table class="table" :fields="fields" :items="equipes">
            <template  v-slot:cell(salarioMensal)="row">
               R$ {{row.item.salarioMensal}}
            </template>
            <template  v-slot:cell(actions)="row">
                <b-button class="btn-sm" @click="vender(row.item.id)">Vender</b-button>
            </template>
        </b-table>
       </div>
      <div class="card col-md-4 ml-2">
        <h2> Contratação de equipes </h2>
        <div class = "input_form">
          <label> Tipo da equipe </label>
          <b-form-select
            class="w-100"
            @change="mudaTipo"
            :options="optTipo"
            v-model="tipo">
         </b-form-select>
        </div>
        <div class = "input_form">
          <label> Região da equipe </label>
          <b-form-select
            class="w-100"
            :options="optRegiao"
            v-model="regiao">
         </b-form-select>
        </div>
        <div class = "input_form form-group">
            <label> Quantidade  </label>
            <b-form-input type="number" v-model="quantidade" class="form-control"></b-form-input>
        </div>
        <div class = "input_form" v-show="tipo">
          <p> A equipe {{selecionada.tipo}} custa R${{selecionada.valor}} valor de contratação com R${{selecionada.salario}} valor de salário, e o desligamento é R${{selecionada.desligamento}},
          com valor de contratação em K ela atende P lotes em R celulares/mes. </p>
        </div>
        <div class = "bt">
          <button class="button-cancel"> Cancelar </button>
          <button @click="confirmar"> Confirmar </button>
        </div>
       </div>
       <b-modal></b-modal>
    </div>
</template>

<script>
import axios from 'axios';
import { mapMutations } from 'vuex'
export default {
  name: 'Equipamentos',
  data(){
    return{
      dados: [],
      tipo: null,
      quantidade: 0,
      selecionada:{
        tipo: null,
        valor: null,
        salario: null,
        desligamento: null
      },
      /*
                              Salario Mensal  Desligamento  Treinamento
      Logistica       12000   58500           21000         7200
      Comercial       5800    28725           22330         7656
      Infraestrutura  14800   72150           20720         7104

      */
      optTipo:[
        {value: null, text: 'Selecione', disabled: true},
        {value: 'logistica', text: 'Logistica', contratacao: 12000,salario: 58500, desligamento: 21000, treinamento: 7200},
        {value: 'comercial', text: 'Comercial', contratacao: 5800,salario: 28725, desligamento: 22330, treinamento: 7656},
        {value: 'infraestrutura', text: 'Infraestrutura', contratacao: 14800,salario: 72150, desligamento: 20720, treinamento: 7104}
      ],
      optRegiao:[
        {value: 'Sudeste', text: 'Sudeste'},
        {value: 'Nordeste', text: 'Nordeste'},
        {value: 'Norte', text: 'Norte'}
      ],
      regiao: '',
      equipes: [],
      fields:[
      {
        key:'tipo',
        label: 'Tipo'
      },
      {
        key:'quantidade',
        label: 'Quantidade'
      },
      {
        key:'salarioMensal',
        label: 'Salário Mensal'
      },
      {
        key:'regiao',
        label: 'Região'
      },
      {
        key: 'actions',
        label: 'Ações'
      }
      ]
    }
  },
  methods:{
    ...mapMutations([
      'setUsuario',
      'setCaixa'
    ]),
    mudaTipo(tipo){
      let e = this.optTipo.filter(val => { return val.value==tipo})[0]
      this.selecionada = {
        tipo: e.text,
        valor: e.contratacao,
        salario: e.salario,
        desligamento: e.desligamento
      }
      console.log(e)
    },
    confirmar(){
      let id = this.$store.getters.getId
      let url = "equipe/contrataEquipe/"+id+"/"
      let json = {
        "tipo": this.tipo,
        "quantidade": this.quantidade,
        "regiao": this.regiao
      }
      let app = this
      axios.post(url, json).then(res => {
        app.setUsuario(res.data)
        app.setCaixa(res.data.caixa)
        this.equipes = this.$store.getters.getEquipes
        app.tipo = null
        app.quantidade = 0
      }).catch( err => {
        console.log(err)
      })
    }
  },
  mounted(){
    this.equipes = this.$store.getters.getEquipes
  }
}
</script>
<style scoped lang="scss">
@import "../../assets/sass/paper/_variables.scss";

.content{
    align-items: center;
    position: relative;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
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
h2{
  text-align:center;
  justify-content: center;
  display: flex;
}
label{
  justify-content: center;
  display: flex;
  font-size:20px;
}
.bt{
  justify-content: center;
  display: flex;
  margin-top: 5%;
}
form{
  width:40%;
}
button{
  width: 100%;
  &:hover {
    background-color: #946fd9;
  }
  background-color: $purple;
  border: none;
  border-radius: 8px;
  font-size: 18px;
  color:white;
  margin: 1%;
  justify-content: center;
  display: flex;
  font-weight: bold;
}
select{
  width:50%;
  border-radius: 5px;
}
.input_form{
  width:100%;
  text-align: center;
  margin-top: 5%;
}

.form-control{
  background-color: $gray-input-bg;
  border: 1px solid #DDDDDD;
  border-radius: $border-radius-base;
  color: $font-color;
  font-size: $font-size-base;
}
</style>
