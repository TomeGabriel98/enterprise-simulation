<template>
  <div class="content">
    <div class="container">
         <h2> Serviços Terceiros </h2>
          <div class = "input_form">
            <label> Tipo de serviço terceiro </label>
            <select>
               <option> Pontos de Atendimento </option>
           </select>
          </div>
          <div class = "input_form">
              <label> Quantidade  </label>
              <input type = "number" v-model="quantidade">
          </div>
          <div class = "input_form">
            <p> A equipe A custa X valor de contratação com Y valor de salário, e o desligamento é Z,
            com valor de treinamento em K ela atende P infraestrutura em R Postos. </p>
          </div>
          <div class = "bt">
            <button type="submit" class="button-cancel"> Cancelar </button>
            <button @click="contratar"> Confirmar </button>
          </div>
     </div>
     <div class="card">
       <b-table :items="servs"></b-table>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapMutations } from 'vuex'
export default {
  data(){
    return{
      dados: [],
      usuario: null,
      quantidade: '',
      servs: []
    }
  },
  methods:{
    ...mapMutations([
      'setUsuario',
      'setCaixa'
    ]),
    contratar(){
        let url = 'servico/contrataServicoTerceiro/'
        let app = this
        let object = {}
        object.quantidade = this.quantidade
        axios.post(url+app.usuario.id+'/', object).then(res => {
          console.log(res)
          app.setUsuario = res.data
          app.setCaixa = res.data.caixa
          app.usuario = res.data
          app.servs = res.data.servicoTerceiro
          app.quantidade = ''
        }).catch(err => {
          console.log(err)
        })
    }

  },
  mounted(){
    this.usuario = this.$store.getters.getUsuario;
    this.servs = this.usuario.servicoTerceiro
    console.log('servs', this.servs)
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

</style>
