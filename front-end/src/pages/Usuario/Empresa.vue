<template>
  <div class="content">
    <h2>{{empresa.nome}}</h2><br>
    <h2>{{empresa.email}}</h2><br>
    <img :src="img" alt="">
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Empresa',
  data(){
    return{
      dados: [],
      empresaId: null,
      empresa: null,
      img: null
    }
  },
  methods:{
    async teste(){
      let response = null
      axios
      .get('https://api.coindesk.com/v1/bpi/currentprice.json')
      .then(response => {
        this.dados = response.data.bpi
      })
      .catch(error => {
        console.log(error)
        this.errored = true
      })

    },
    getLogo(id){
      let app = this;
      axios.get('/file/downloadFile/'+id).then(res => {
        app.img = 'data:'+res.headers['content-type']+';base64,'+res.data;
      })
    }
  },
   created: function(){
      this.teste()
  },
  mounted(){
    this.empresaId = this.$session.get('empresa_id');
    console.log(this.empresaId)
    let app = this;
    axios.get('/empresa/getById/'+app.empresaId).then(res => {
      console.log(res)
      app.empresa = res.data;
      if(res.data.logo!=null){
          app.getLogo(res.data.logo)
      }
    })
  }
}
</script>
<style scoped lang="scss">
@import "../../assets/sass/paper/_variables.scss";

.content{
    align-items: center;
    display: table;
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
