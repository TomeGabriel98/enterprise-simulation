<template>
  <div class="content">
        <div class="card col-md-6">
          <h2> Registro de ERB's </h2>
          <b-table class="table" :fields="fields" :items="erbs">
          </b-table>
        </div>
        <div class="card col-md-5 ml-2">
          <h2> Compra de ERBs </h2>
          <div class = "input_form">
            <label> Quantidade a ser comprada </label>
            <input type = "number" v-model="quantidade">
          </div>
          <div class = "input_form">
              <label> Área de atuação </label>
              <input
                type="radio"
                id="area_um"
                name="area"
                value="Sudeste"
                v-model="regiao"
              >
              <span class="pointer" for="area_um"> Sudeste </span>
              <br>

              <input
                type="radio"
                id="area_dois"
                name="area"
                value="Nordeste"
                v-model="regiao"
              >
              <span class="pointer" for="area_dois"> Nordeste </span> <br>

              <input
                type="radio"
                id="area_tres"
                name="area"
                value="Norte"
                v-model="regiao"
              >
              <span class="pointer" for="area_tres"> Norte </span>

          </div>
          <div class = "bt">
            <button type = "button" v-on:click="cadastrar_erb()"> Confirmar </button>
          </div>
    </div>
  </div>

</template>

<script>
import axios from 'axios';
import { mapMutations } from 'vuex'
export default {
  name: 'CompraERBs',
  data(){
    return{
      dados: [],
      empresaId: null,
      erb: [],
      erbs: [],
      quantidade: null,
      regiao: null,
      fields: [

      ]
    }
  },
  mounted(){
    this.empresaId = this.$store.getters.getUsuario
    this.erbs = this.empresaId.erb
    //this.pegar_erbs()
  },
  methods:{
    ...mapMutations([
      'setUsuario'
    ]),
    pegar_erbs(){
      axios.post("/erb/listaERB/"+this.empresaId+'/')
      .then(res=>{
        console.log(res)
        console.log(res.data)
      })
      .catch(error => {
        console.log(error)
      })
    },

    cadastrar_erb(){ //quantidade, regiao
      let json = {
        "regiao": this.regiao,
        "quantidade": this.quantidade,
      }
      let app = this
      axios.post("/erb/compraERB/"+this.empresaId+'/', json)
      .then(res=>{
        app.setUsuario(res.data)
        app.erbs = res.data.erb
      })
    }
  },
   created: function(){

  }
}
</script>
<style scoped lang="scss">
@import "../../assets/sass/paper/_variables.scss";

.content{
    align-items: center;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
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
.button-cancel {
  background-color: $cyan;
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
  margin-top: 3%;
}

</style>
