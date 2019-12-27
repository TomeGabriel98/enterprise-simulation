<template>

  <div class="content">
    <div class="container">
      <div>
         <h2> Atuação </h2>
         <div class="cards_inputs">
          <div class="input_form form-group">
            <label> Zona de atuação (localidade) </label>
            <b-form-select v-model="localizacao" :options="optLocalizacoes"></b-form-select>
            <button @click="defineLocal"> Confirmar </button>
          </div>
          <div class="input_form">
            <label> Nível de mercado (tipo de mercado) </label>
            <b-form-select>
              <option> Premium </option>
              <option> Intermediário </option>
              <option> Entrada </option>
            </b-form-select>
              <button> Confirmar </button>
          </div>
        </div>
      </div>
     </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Teste',
  data(){
    return{
      empresa: null,
      optLocalizacoes: [],
      localizacao: null
    }
  },
  methods:{
    defineLocal(){
      let local = {}
      local.id = this.localizacao
      let id = this.empresa.id
      axios.post('/localizacao/defineLocalizacao/'+this.empresa.id+'/', local).then(res => {
        console.log(res)
      }).catch( err => {
        console.log(err)
      })
    }
  },
  mounted(){
    this.empresa = this.$store.getters.getUsuario;
    console.log(this.empresa)
    for(let loc in this.empresa.localizacao){
      this.optLocalizacoes.push({value: this.empresa.localizacao[loc].id, text: this.empresa.localizacao[loc].nome})
    }
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
  margin-top: 10%;
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
  width:100%;
  border-radius: 5px;
}
form{
  width: 100%;
}
.input_form{
  text-align: center;
  margin-top: 5%;
  flex: 1;
  display: grid;
  padding: 0 9rem 2rem 9rem;
}
.input_form:first-child{
  border-right: 3px solid #f4f3ef;
}
.input_form button{
    margin-top: 1.2rem
}
.cards_inputs{
  display: flex;
}
.form-control{
  background-color: $gray-input-bg;
  border: 1px solid #DDDDDD;
  border-radius: $border-radius-base;
  color: $font-color;
  font-size: $font-size-base;
}

@media (max-width: 991px){
  .cards_inputs{
    display: table;
  }
  .input_form:first-child{
    border-right: 0px solid #f4f3ef;
    border-bottom: 3px solid #f4f3ef;
  }
}
</style>
