<template>
  <div class="content">
    <div class="container">
      <form>
          <h2> Venda de ERBs </h2>
          <div class = "input_form">
            <label> Quantidade a ser vendida </label>
            <input type = "number">
          </div>
          <div class = "input_form">
              <label> Área de atuação </label>
              <input
                type="radio"
                id="area_um"
                name="area"
                value="area_um"
              >
              <span> Sul /Sudeste </span>
              <br>

              <input
                type="radio"
                id="area_dois"
                name="area"
                value="area_dois"
              >
              <span> Nordeste </span> <br>

              <input
                type="radio"
                id="area_tres"
                name="area"
                value="area_tres"
              >
              <span> Centro-Oeste / Norte </span>

          </div>
          <div class = "bt">
            <button type = "submit" class="button-cancel"> Cancelar </button>
            <button type = "submit"> Confirmar </button>
          </div>
      </form>

     </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'CompraERBs',
  data(){
    return{
      dados: []
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
        this.errored = true
      })

    }
  },
   created: function(){
      this.teste()
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
