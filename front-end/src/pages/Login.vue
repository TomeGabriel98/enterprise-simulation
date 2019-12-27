<template>
<div class="hello">
  <div class="formBox level-login">
    <div class="box loginBox">
      <div class="icon-head">
        <font-awesome-icon icon="globe" />
      </div>
      <h2>LOGIN</h2>
      <form class="form" @submit.prevent="validaLogin">
        <div class="f_row">
          <label for="text">Email {{url}}</label>
          <!-- <i class = "ti-user"> </i> -->
          <input type="text" id="email" class="input" v-model="username">
          <u></u>
        </div>
        <div class="f_row last">
          <label for="senha">Senha</label>
          <!-- <i class = "ti-lock"> </i> -->
          <input type="password" id="senha" class="input" v-model="password">
          <u></u>
        </div>
        <button class="btn" @click="validaLogin">Entrar</button>
        <div class="f_link">
          <a href="" class="resetTag">Esqueceu sua senha?</a>
        </div>
        <div class="f_link">
          <a @click="modalShow=true" class="cadastrarTag">Cadastre-se</a>
        </div>

      </form>
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
import { mapMutations } from 'vuex'
export default {
  name: 'Login',
  data() {
    return {
      url: process.env.VUE_APP_ROOT_URL,
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
      classe:{
        imgImg: ' d-none',
        span: ' d-block',
        erro: ' d-none'
      },
      spinner: false,
      dragClass: ''
    }
  },
  mounted(){
    // let headers = {"Access-Control-Allow-Origin": "http://127.0.0.1:8080/*"}
    // let app = this;
    // axios.get('file/downloadFile/all', {headers: headers}).then(function(res){
    //   console.log(res)
    //   app.img = res.data[0].data;
    //   //app.$refs.imageInput.src = res.data[0].data;
    // })
  },
  methods: {
    ...mapMutations([
      'setId',
      'setUsuario',
      'setAdmin'
    ]),
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
@import "../assets/sass/paper/_variables.scss";

.hello {
   color: #2c3e50;
   background-color: #212120;
   font-family: sans-serif, Arial;
   height: 100vh;
   width: 100%;
   display: flex;
   justify-content: center;


}
.icon-head{
  width: 100%;
  text-align: center;
  font-size: 5rem;
  color: #6F42C1;
  margin-bottom: -30px;
  margin-top: -45px;
}
h2 {
    color: $dark;
    padding: 0 45px;
    font-size: 32px;
    margin-bottom: 50px;
    text-align: center;
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
