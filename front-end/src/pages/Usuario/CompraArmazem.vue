<template>
  <div>
    <div class=" content container">
      
      <form>
          <h2> Compra de Armazém </h2>
          <div class = "input_form">
            <label> Quantidade a ser comprada </label>
            <input type = "number">
          </div>
          <div class = "input_form">
            <label> Área de atuação </label>
            
            <input type="radio" id="area_um" name="area" value="area_um" >
            <span> Sul /Sudeste </span>
            
            <br>

            <input type="radio" id="area_dois" name="area" value="area_dois" >
            <span> Nordeste </span> <br>

            <input type="radio" id="area_tres" name="area" value="area_tres">
            <span> Centro-Oste / Norte </span>
          </div>

          <div class = "bt">
            <button type = "submit" class="button-cancel"> Cancelar </button>
            <button type = "submit"> Confirmar </button>
          </div>
      </form>
    </div>

    <div class="container margin-table">
      <h2> Registro de Armazém</h2>
      
      <table class="table table-hover table-position">
        <thead>
          <tr>
            <th scope="col">Empresa</th>
            <th scope="col">Quantidade</th>
            <th scope="col">Area de Atuação</th>
            <th scope="col">Data</th>
          </tr>
        </thead>
        <tbody>
           <tr v-for="(armazem, index) in armazens" :key="index">
            <th scope="row">{{ armazem.armazem.quantity }}</th>
            <td> {{ armazem.armazem.area_de_atuacao }}</td>
            <td>{{ armazem.armazem.created_at }}</td>
          </tr> 
        </tbody>
      </table>
    </div> 
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'CompraArmazem',
  data(){
    return{
      dados: [],
      armazens: []

    }
  },
  methods:{
    async teste(){
      let response = null
      axios
      .get('https://api.coindesk.com/v1/bpi/currentprice.json')
      .then(response => {
        this.dados = response.data.bpi
        console.log(this.dados);
      })
      .catch(error => {
        console.log(error)
        
      })
    },

    async getArmazem(){
      let response = null
      axios
      .get('https://private-ede436-projetofatecfront.apiary-mock.com/empresa/getAll')
      .then(response => {
        this.armazens = response.data
      })
      .catch(error => {
      })
    }

  },
   created: function(){
      this.teste()
      this.getArmazem()
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

.margin-table {
  position: relative;
  top: 530px;
  left: -40px;
  width: 970px;
}

.table-position {
  position: relative;
  top: -100px;
}
</style>
