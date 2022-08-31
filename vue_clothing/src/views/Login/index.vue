<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" v-show="!isRegister" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">服装库存管理系统</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="请输入用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="请输入密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-link @click="cutRegister" type="success" style="float:right">没有账号？注册</el-link>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;margin-top:10px" @click.native.prevent="handleLogin">登录</el-button>

    </el-form>

    <el-form v-show="isRegister"  :model="registerForm" ref="RegisterForm" :rules="registerRules" class="login-form" label-position="left" >
      <div class="title-container">
        <h3 class="title">账号注册</h3>
      </div>
      <el-form-item prop="userLogin" >
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="registerForm.userLogin"  autocomplete="off" placeholder="邮箱" ></el-input>
      </el-form-item>
      <el-form-item prop="userName" >
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="registerForm.userName" autocomplete="off" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item prop="userPassword">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input type="password" v-model="registerForm.userPassword" autocomplete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input type="password" v-model="registerForm.confirmPassword" autocomplete="off" placeholder="确认密码"></el-input>
      </el-form-item>

      <el-link @click="cutRegister" type="success" style="float:right">已有账号？去登录</el-link>
      <el-button type="primary" style="width:100%;margin-bottom:30px;margin-top:10px" @click.native.prevent="register">注册</el-button>
    </el-form>
    <Vcode :show="isShow" @success="handleSuccess" />
  </div>
</template>

<script>
import Vcode from "vue-puzzle-vcode"
export default {
  name: 'Login',
  components: {
    Vcode,
  },
  data() {
    //判断输入的账户和密码是否符合规则
    const validateUsername = (rule, value, callback) => {
      const reg =  /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/
      if (!reg.test(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码长度不能小于六'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '1378894054@qq.com',
        password: '123456'
      },
      registerForm:{
        userLogin:'',
        userName:'',
        userPassword:'',
        confirmPassword:''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      
      registerRules: {
              userLogin: [
                    { required: true,validator: validateUsername,trigger: 'blur' },
              ],
              userName: [
                  { required: true, message: '请设置名称', trigger: 'blur' },
              ],
              userPassword: [
                  { required: true, message: '请设置密码', trigger: 'blur' },
                  { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
              ],
              confirmPassword: [
                  { required: true, message: '请设置密码', trigger: 'blur' },
                  { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
              ],
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      isShow:false,
      isRegister:false,
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleSuccess(){
      this.isShow = false
      //按钮会有一个loading效果
      this.loading = true
      // this.$router.push({ path: this.redirect || '/system' })
      //派发一个带着用户名和密码的载荷
      this.$store.dispatch('user/login', this.loginForm).then((res) => {
          this.$message.success(res)
        //登录成功，进行路由的跳转
        this.$router.push({ path: this.redirect || '/' })
        //loading效果结束
        this.loading = false
      }).catch((res) => {
        this.$message.error(res)
        this.loading = false
      })
    },

    //登录业务
    handleLogin() {
      //验证表单元素是否符合规则
      this.$refs.loginForm.validate(valid => {
        //如果符合规则
        if (valid) {
          //验证码验证
          this.isShow = true
        } else {
          return false
        }
      })

    },

    //注册业务
    register(){
       this.$refs.RegisterForm.validate(valid => {
        //如果符合规则
        if (valid) {
          if(this.registerForm.userPassword != this.registerForm.confirmPassword){
            this.$message.error('两次密码输入不一致')
            return
          }
          //新增用户
          let data={
            userName:this.registerForm.userName,
            userLogin:this.registerForm.userLogin,
            userPassword:this.registerForm.userPassword
          }
          this.$store.dispatch('user/addUser',data).then(res=>{
            this.$message.success('注册成功') 
            this.cutRegister()   
          }).catch(err=>{
            this.$message.error('注册失败')
          })
        } else {
          return false
        }
      })
    },

    //切换注册
    cutRegister(){
      this.$refs.loginForm.resetFields()
      this.$refs.RegisterForm.resetFields()
      this.isRegister=!this.isRegister
    }

  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
