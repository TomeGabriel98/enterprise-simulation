<template>
  <div class="content">
    <div class="container">
      <form>
         <h2> Localidade e Alocação </h2>
          <div class="select_form">
            <label> Tipo </label>
            <b-form-select class="" name="tipo" :options="tipos" v-model="tipo">
            </b-form-select>
          </div>
          <div class="input_form">
              <label> Quantidade a ser alocada</label>
              <b-form-input type="text"></b-form-input>
          </div>
          <div class="areas">
            <div class="area-atuacao">
              <span>Área de atuação</span>
              <div class="atuacao-group">
                <div class="area-um">
                  <b-form-checkbox
                    id="id_area_um"
                    v-model="area_um"
                  >
                    Área um
                  </b-form-checkbox>
                </div>
                <div class="area-dois">
                  <b-form-checkbox
                    id="id_area_dois"
                    v-model="area_dois"
                  >
                    Área dois
                  </b-form-checkbox>
                </div>
                <div class="area-tres">
                  <b-form-checkbox
                    id="id_area_tres"
                    v-model="area_tres"
                  >
                    Área três
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="area-equipe">
              <span>Quantidade por Equipe</span>
              <table>
                <tr>
                  <td>
                    <span for="qtd_logistica">Quantidade Total de Logistica</span>
                  </td>
                  <td>
                    <input v-if="area_um==false" type="text" name="" id="qtd_logistica" disabled="disabled" value="">
                    <input v-else type="text" name="" id="qtd_logistica">
                  </td>
                </tr>
                <tr>
                  <td>
                    <span for="qtd_comercial">Quantidade Total de Comercial</span>
                  </td>
                  <td>
                    <input v-if="area_dois==false" type="text" name="" id="qtd_comercial" disabled="disabled" value="">
                    <input v-else type="text" name="" id="qtd_comercial">
                  </td>
                </tr>
                <tr>
                  <td>
                    <span for="qtd_infraestrutura">Quantidade Total de Infraestrutura</span>
                  </td>
                  <td>
                    <input v-if="area_tres==false" type="text" name="" id="qtd_infraestrutura" disabled="disabled" value="">
                    <input v-else type="text" name="" id="qtd_infraestrutura">
                  </td>
                </tr>
              </table>
            </div>
          </div>
          <div class="bt">
            <button type="submit" class="button-cancel"> Cancelar </button>
            <button type="submit"> Confirmar </button>
          </div>
      </form>
     </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data(){
    return{
      dados: [],
      tipo: 'logistica',
      tipos:[
        {
          value: "logistica",
          text: "Logistica",
        },
        {
          value: "comercial",
          text: "Comercial",
        },{
          value: "infraestrutura",
          text: "Infraestrutura",
        },
      ],
      area_um: false,
      area_dois: false,
      area_tres: false,
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
    height: 75vh;
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
.input_form, .select_form{
  width:100%;
  text-align: center;
  margin-top: 5%;
}
.select_form select{
  width: 100%;
}
.input_form input{
  border: 1px solid #ced4da;
}
.areas{
  display: flex;
  margin-top: 30px;
  width: 250%;
  margin-left: -50%;
}
.atuacao-group{
  display: grid;
  margin-right: 30px;
}
.atuacao-group div{
  display: flex;
}
.area-equipe{
  width: 70%;
  margin-left: 5%;
}
.area-equipe table tr td:last-child{
  width: 30%;
}
.area-equipe td label{
  text-align: left;
}
.area-equipe input{
  width: 20%;
}
.atuacao-group label, .area-equipe label{
  font-size: .9rem;
  text-align: left;
}
</style>
