<template>
  <div class="content">
    <div class="container">
      <div>
         <h2> Bancos </h2>
         <div class="cards_inputs">
          <div class="input_form form-group">
            <label> Empréstimo </label>
            <b-form-input type = "number" placeholder="Valor:" class="form-control" v-model="emprestimo"></b-form-input>
            <button @click="fazerEmprestimo"> Confirmar </button>
          </div>
          <div class="input_form">
              <label> Pagamentos  </label>
              <b-form-input type="number" placeholder="Valor:" v-model="pagamento"></b-form-input>
              <button @click="fazerPagamento"> Confirmar </button>
          </div>
        </div>
      </div>
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
      emprestimo: '',
      pagamento: '',
      id: null,
    }
  },
  methods:{
    ...mapMutations([
      'setCaixa'
    ]),
    fazerEmprestimo(){
      let valor = {
        "valor": this.emprestimo
      }
      let app = this;
      let token = this.$session.get('authorization')
      let headers = {
        "Accept": 'application/json',
        "Authorization": "Bearer "+token,
        "Access-Control-Allow-Origin": "*"
      }
      axios.put('empresa/emprestimo/'+this.id+'/',valor, {headers:headers}).then(res => {
        console.log(res)
        app.setCaixa(res.data.caixa)
        app.emprestimo = ''
      })
    },
    fazerPagamento(){
      let valor = {
        "valor": this.pagamento
      }
      let app = this;
      let token = this.$session.get('authorization')
      let headers = {
        "Accept": 'application/json',
        "Authorization": "Bearer "+token,
        "Access-Control-Allow-Origin": "*"
      }
      axios.put('empresa/pagamento/'+this.id+'/',valor, {headers:headers}).then(res => {
        console.log(res)
        app.setCaixa(res.data.caixa)
        app.pagamento = ''
      })
    }
  },
  mounted(){
    this.id = this.$store.getters.getId
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
  width:50%;
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
