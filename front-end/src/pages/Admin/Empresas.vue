<template>
  <div class="content">
    <div class="container">
      <div>
         <h2> Empresas </h2>
         <b-button @click="abrirModal">Cadastrar</b-button>
         <b-table :items="empresas" :fields="tFields"></b-table>
      </div>
     </div>
 <b-modal v-model="modalShow" title="Cadastre sua empresa" @ok="salvaImagem" @hidden="fechaModal">
   <form class="">
     <div class="form-group">
       <label for="nome">Nome:</label>
       <input type="text" id="nome" v-model="nome">
     </div>
     <div class="form-group">
       <label for="email">E-mail:</label>
       <input type="email" id="email" v-model="email">
     </div>
     <div class="form-group">
       <label for="senha">Senha:</label>
       <input type="password" id="senha" v-model="senha">
     </div>
     <div class="form-group div-turma">
       <label>Turma:</label>
       <div class="div-forms-turma">
       <b-form-select :options="optTurmas" v-model="turma">
         <template v-slot:first>
          <option :value="null" disabled>-- Selecione --</option>
        </template>
       </b-form-select>
       <b-button @click="redirectToTurmas">+</b-button>
       </div>
     </div>

     <div class="form-group">
       <label>Logo:</label>
       <div
         :class="'div-file-drop' + dragClass" v-cloak
         @drop.prevent="addFile"
         @dragenter="dragClass=' on-drag'"
         @dragover.prevent
         @click="clickAddPhoto"
         @dragend="dragClass=''"
         @dragleave="dragClass=''">
         <span
           :class="classe.span"
           v-cloak
           @dragover.prevent
           @dragenter="dragClass=' on-drag'"
           @dragend="dragClass=''"
           @dragleave="dragClass=''">Clique aqui ou arraste o arquivo</span>
         <img v-bind:src="img" ref="imageInput" :class="'uploading-image imgLogo '+classe.imgImg"/>
         <span :class="'fonte-error '+classe.erro">Apenas arquivo do tipo PNG e tamanho máximo de 128Kb</span>
       </div>
        <input id="btnAddImg" type="file" ref="btnInputImg" accept="image/png" @change=uploadImage>
     </div>

   </form>
   <div class="back-spinner" v-show="spinner">

   </div>
   </b-modal>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Empresas',
  data(){
    return{
      username: '',
      password: '',
      img: null,
      previewImage: null,
      modalShow: false,
      nome: '',
      email: '',
      senha:'',
      file: null,
      localizacao: '',
      modalShow: false,
      empresa: null,
      empresas: [],
      tFields:[
      {
        key: 'nome',
        label: 'Nome'
      },
      {
        key: 'email',
        label: 'Email'
      },
      {
        key: 'dataDeCriacao',
        label: 'Data de Criação'
      },
      {
        key: 'numero_turno',
        label: 'Turno'
      }
      ],
      classe:{
        imgImg: ' d-none',
        span: ' d-block',
        erro: ' d-none'
      },
      spinner: false,
      dragClass: '',
      optTurmas: [],
      turma: null,
    }
  },
  methods:{
    redirectToTurmas(){
      this.$router.push('/admin/turno');
    },
    abrirModal(){
      this.modalShow = true
      let app = this
      axios.get('empresa/getAll').then(res => {
        for(let t in res.data){
          app.optTurmas.push({text: res.data[t].nome, value: res.data[t].id})
        }
      }).catch(err => {
        console.log(err)
      })
    },
    addFile(event){
      // tamanho maximo 131072
      this.dragClass = ''
      let files = event.dataTransfer.files;
      var file = files[0];
      this.file = files[0];
      let app = this;
      this.img = file;
      let valid = this.validaArquivo(file)
      if(valid){
        var reader = new FileReader();
        reader.onloadend = function() {
          app.img = reader.result
         }
         reader.readAsDataURL(file);
      }
    },
    validaArquivo(f){
      this.classe.span = ' d-none'
      if(f.size>131072 || f.type != "image/png"){
        this.classe.erro = ' d-block'
        this.classe.imgImg = ' d-none'
        return false
      }else{
        this.classe.imgImg = ' d-block'
        this.classe.erro = ' d-none'
        return true
      }
    },
    clickAddPhoto(){
      this.$refs.btnInputImg.click()
    },
    uploadImage(e){
      let files = e.target.files;
      var file = files[0];
      let app = this;
      this.img = file;
      this.file = this.$refs.btnInputImg.files[0]
      let valid = this.validaArquivo(file)
      if(valid){
        var reader = new FileReader();
        reader.onloadend = function() {
          app.img = reader.result
         }
         reader.readAsDataURL(file);
      }
    },
    fechaModal(){
      this.img = null
      this.previewImage = null
      this.nome = '',
      this.email = '',
      this.senha = '',
      this.classe = {
        imgImg: ' d-none',
        span: ' d-block',
        erro: ' d-none'
      }
    },
    async validaLogin() {
      let response = null

      let headers = {
        "Content-type": "application/json",
      };

      let data = {
        'username': this.username,
        'password': this.password,
      }
      let app = this;
      axios.post('logando/',
          data,
          headers)
        .then((result) => {
          response = result
          if (response.status == 200){
            app.$session.start()
            app.$session.set('authorization', response.headers['token'])
            app.$session.set('empresa_id', response.data['id'])
            app.setId(response.data['id'])
            app.setUsuario(response.data)

            app.$session.set('empresa_email', response.data['email'])
            app.$session.set('empresa_nome', response.data['nome'])
            app.$session.set('logo_id', response.data['logo'])

            if(response.data.autorizacoes[0].nome=="admin"){
              app.setAdmin(true)
              app.$router.push('/admin');
            }else{
              app.setAdmin(false)
              app.$router.push('/dashboard');
            }
          }

        }).catch((err) => {
          console.log(err);
          alert(err);
        });
    },
    criaEmpresa(id, empresa){
      empresa.logo = id
      let app = this;
      return axios.post('empresa/novoUsuario', empresa).then(function(res){
        app.modalShow = false;
        app.spinner = false;
        console.log(res)
      }).catch(function(err){
        console.log(err)
      })
    },
    async salvaImagem(){
      let app = this;
      let empresa = {};
      empresa.nome = this.nome;
      empresa.email = this.email;
      empresa.senha = this.senha;
      empresa.localizacao_id = this.localizacao;
      empresa.autorizacao = "2"
      this.spinner = true;
      var formData = new FormData();
      formData.append("file", this.file);
      return axios.post('file/uploadFile', formData).then(function(res){
        app.criaEmpresa(res.data.id, empresa)
      }).catch(function(err){
        console.log(err)
      })
    },
    encodeImageFileAsURL() {
      let app = this;
      let files = this.$refs.myFiles.files;
      var file = files[0];
      var reader = new FileReader();
      reader.onloadend = function() {
         //console.log('RESULT', reader.result)
         let b = reader.result.split(',')
         let a = b[0].split('/')
         app.img = a+','+b[1]
       }
       reader.readAsDataURL(file);
    },
  }
}
</script>
<style scoped lang="scss">
@import "../../assets/sass/paper/_variables.scss";

.div-forms-turma{
  display: flex;
}
.div-forms-turma select{
  flex: 1
}
.div-forms-turma select{
  width:60%
}
.div-forms-turma button{
  width:10%;
  margin-left:5%;
  padding:0;
}


/* .div-turma select[data-v-62edb68e] {
    width: 70% !important;
    float: left;
    margin-right: 20px;
}

.div-turma h1{
    margin-top: -10px;
    margin-left: 20px;
    /* border:2px solid #ccc; *
} */

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

form {
    display: block;
    line-height: 1 !important;
    padding: 0 50px;
    position: relative;
}
.formBox {
    width: 460px;
    height: 552px;
    margin-top: 45px;
    margin-bottom: 60px;
}
.box {
    text-align: left;
    background-color: #fff;
    border-radius: 8px;
    padding: 60px 0 40px;
    width: 100%;
    box-shadow: 0 2px 11px -2px rgba(0, 0, 0, 0.5);
    transition: all 0.2s cubic-bezier(.35,.33,.75,.9);
}
.f_row {
    margin-bottom: 45px;
    height: 52px;

    label{
      font-size: 20px;
      text-align: left;
      cursor: pointer;
    }

    &.shake {
        animation: shake 0.4s linear;
    }
    &.last {
        margin-bottom: 53px;
    }
    &:focus-within {
        label {
          font-size: 22px;
        }
        u {
            &:before {
                width: 100%;
            }
        }
        input{
          font-size: 18px;
          border-bottom: 2px solid #6F42C1;
        }
    }
}
input {
    background-color: white;
    border: none;
    border-bottom: 1px solid black;
    font-size: 18px;
    margin-top: -10px;
    text-align: left;
    width: 100%;
    cursor: pointer;
    padding-left: 10px;
}
span {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    color: #000;
}
.btn {
    transition: all 0.6s ease;
    background-color: $purple;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    display: block;
    text-align: center;
    width: 100%;
    padding: 8px;
    font-size: 23px;
    font-weight: bold;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 0.5rem;
    &:hover {
        background-color: #946fd9;
    }
}
.resetTag {
    color: $purple;
    text-align: center;
}
.cadastrarTag{
  color: $purple;
  text-align: center;
  cursor: pointer;
  width: 100%;
}
.imgLogo{
  width: 90%;
}
.div-file-drop{
  min-height: 90px;
  border: 2px dashed #66615b;
  border-radius: 5px;
  padding: 5px;
  display: flex;
  cursor: pointer;
}
.div-file-drop span{
  position: relative;
  margin: 40px 0;
  flex: 1;
  text-align: center;
  color: #66615b;
}
.div-file-drop img{
  flex: 1;
}
.on-drag {
  box-shadow: inset 0px 0px 20px 10px rgba(0,0,0,0.3);
}
#btnAddImg{
  display: none
}
.fonte-error{
  color: #dc3545 !important;
}
.back-spinner{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  position: fixed;
  background-color: rgba(0,0,0,.3);
}
</style>
