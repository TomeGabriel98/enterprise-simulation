<template>
  <div class="content">
    <div class="container">
      <form>
          <h2> Demissão de equipes </h2>
          <div class = "input_form">
            <label> Tipo da equipe </label>
            <select>
              <option> Infraestrutura </option>
               <option> Logística </option>
               <option> Comercial </option>
           </select>
          </div>
          <div class = "input_form">
              <label> Quantidade  </label>
              <input type = "number">
          </div>
          <div class = "input_form">
            <p> A equipe A custa X valor de contratação com Y valor de salário, e o desligamento é Z,
            com valor de contratação em K ela atende P lotes em R celulares/mes. </p>
          </div>
          <div class = "bt">
            <button type="submit" class="button-cancel"> Cancelar </button>
            <button type = "submit"> Confirmar </button>
          </div>
      </form>
     </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Equipamentos',
  data(){
    return{
      dados: []
    }
  },
  methods:{
  },
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
