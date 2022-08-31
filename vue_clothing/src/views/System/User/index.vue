<template>
  <div id="user">
      <div id="search">
        <el-button type="primary" @click="userDialog(type='add')" :disabled="!authority">添加</el-button>
        <el-dialog :title="dialog.dialogTitle" @close="closeDialog('userForm')" :visible.sync="dialog.dialogFormVisible" width="30%" center destroy-on-close>
          <el-form  :model="userForm" ref="userForm" :rules="rules" label-position="left" label-width="20%">
            <el-form-item label="用户账号"  prop="userLogin" >
              <el-input v-model="userForm.userLogin" autocomplete="off" placeholder="邮箱" :disabled="dialog.dialogType=='edit'"></el-input>
            </el-form-item>
            <el-form-item label="用户名称" prop="userName" >
              <el-input v-model="userForm.userName" autocomplete="off" placeholder="汉字或字母"></el-input>
            </el-form-item>
            <el-form-item label="用户密码" prop="userPassword">
              <el-input v-model="userForm.userPassword" type="password" autocomplete="off" placeholder="6到40位字符"></el-input>
            </el-form-item>
            <el-form-item label="用户角色" prop="userRole">
              <el-select v-model="userForm.userRole" placeholder="请选择用户角色" style="" :disabled="dialog.dialogType=='edit' && !authority">
                <el-option label="用户" value="USER"></el-option>
                <el-option label="管理员" value="ADMIN"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialog.dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </div>
        </el-dialog>

        <el-form ref="searchForm" :inline="true" :model="searchInfo" class="demo-form-inline" style="float:right">
          <el-form-item label="用户登录号" prop="userLogin">
            <el-input v-model="searchInfo.userLogin" placeholder="用户登录号"  :disabled="!authority"></el-input>
          </el-form-item>
          <el-form-item label="用户姓名" prop="userName">
            <el-input v-model="searchInfo.userName" placeholder="用户姓名" :disabled="!authority"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchUser" :disabled="!authority">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div id="tag">
        <el-tag
          v-for="tag in dynamicTags"
          :key="tag"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)">
          {{tag}}
        </el-tag>
      </div>
      <div id="table">
        <el-table :data="userInfo"  stripe border  style="width: 100%;margin-top:10px" >
          
          <el-table-column
            width="100px"
            label="序号"
            type="index"
            :index="indexMethod"
            align="center">
          </el-table-column>

          <el-table-column
            prop="userLogin"
            label="用户账号"
            align="center">
          </el-table-column>

          <el-table-column
            prop="userName"
            label="用户姓名"
            align="center">
            <!-- <template slot-scope="scope">
              <el-tag
                type="success"
                disable-transitions>{{scope.row.userName}}</el-tag>
            </template> -->
          </el-table-column>

          <el-table-column
            prop="userRole"
            label="用户角色"
            :formatter="handleStatus"
            align="center">
          </el-table-column>
          

          
          <el-table-column label="操作" align="center" width="180px">
            <template slot-scope="scope" >
                <!--  :disabled="" 是否禁用 -->
                <el-button
                    slot="reference"
                    size="mini"
                    type="info" 
                    plain
                    v-if="authority"
                    @click="userDialog(scope.row,'edit')"
                    style="margin-right:10px">
                    编辑
                </el-button>

                 <el-button
                    slot="reference"
                    size="mini"
                    type="danger" 
                    v-if="authority"
                    @click="deleteUser(scope.row)"
                    style="margin-right:10px">
                    删除
                </el-button>
                <el-button
                    slot="reference"
                    size="mini"
                    type="info"
                    plain 
                    v-if="!authority"
                    @click="changeMyInfo(scope.row)"
                    style="margin-right:10px">
                    修改信息
                </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div id="pagination">
        <Pagination :pageInfo = "pageInfo"/>
      </div>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination/index.vue'
import md5 from 'js-md5';
export default {
    name:'User',
    components:{
        Pagination
    },
    data(){
      // 表单验证规则定义
      var checkUserLogin = (rule, value, callback) => {
          //邮箱验证
          const isbnReg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/;
          if (value === '') {
          return callback(new Error('请输入用户账号'));
          } 
          if (!isbnReg.test(value)) {
              callback(new Error('账号格式错误'));
          } else {
              callback();
          }
      };
      return{
        // authority:this.$store.state.user.userRole == 'ADMIN', // 权限

        userInfo:[
          // {userLogin:'1',userName:'张三',userPassword:'2',userRole:'USER'},
          // {userLogin:'2',userName:'张三',userPassword:'2',userRole:'ADMIN'},
          // {userLogin:'4',userName:'张三',userPassword:'2',userRole:'USER'},
          // {userLogin:'4',userName:'张三',userPassword:'2',userRole:'USER'},
          // {userLogin:'4',userName:'张三',userPassword:'2',userRole:'USER'},
          // {userLogin:'4',userName:'张三',userPassword:'2',userRole:'USER'},
        ],
        
        userForm:{userLogin:'',userPassword:'',userName:'',userRole:''},

        searchInfo:{
          userLogin:'',
          userName:''
        },
        searchInfoTemp:{ // 查询状态临时保存查询条件
          userLogin:'',
          userName:''
        },

        pageInfo:{},

        dynamicTags: [],

        
        dialog:{
          dialogFormVisible: false,
          dialogTitle:'',
          dialogType:'',
        },

        rules: {
                userLogin: [
                     { required: true,validator: checkUserLogin,trigger: 'blur' },
                ],
                userName: [
                    { required: true, message: '请设置名称', trigger: 'blur' },
                ],
                userPassword: [
                    { required: true, message: '请设置密码', trigger: 'blur' },
                    { min: 6, max: 40, message: '长度在 6 到 40 个字符', trigger: 'blur' }
                ],
                userRole: [
                    { required: true, message: '请选择用户角色', trigger: 'blur' },
                ],
        },

        loading:false,
      }
    },

    created(){
      this.$store.dispatch('user/getUserInfo')
      this.getAllUserInfo()
    },

    computed:{
      authority(){
        return this.$store.state.user.userRole == 'ADMIN'
      }
    },


    methods:{
      // 查询所有用户
      getAllUserInfo({currentPage = 1,userLogin='',userName=''}={}){//解构赋值设置默认参数
        let data = {
          currentPage,
          pageSize:6,
          userLogin,
          userName,
        }
        this.$store.dispatch('user/getAllUserInfo',data).then(res=>{
          this.pageInfo = JSON.parse(JSON.stringify(res))
          this.userInfo = JSON.parse(JSON.stringify(res.records))
        })
      },

      //页码变化
      pageChange(currentPage){
        let data={
          currentPage,
          userLogin:this.searchInfoTemp.userLogin,
          userName:this.searchInfoTemp.userName,
        }
        this.getAllUserInfo(data)
      },

      //编辑用户或新增用户dialog切换
      userDialog(row,type){
        if(type == 'edit'){
          this.dialog.dialogTitle = '编辑用户'
          this.dialog.dialogType = 'edit'
          this.$nextTick(()=>{
            this.userForm = JSON.parse(JSON.stringify(row))
          })
          
          
        }else{
          this.userForm = {userLogin:'',userPassword:'',userName:'',userRole:'USER'}
          this.dialog.dialogTitle = '新增用户'
          this.dialog.dialogType = 'add'
        }
        this.dialog.dialogFormVisible = true;
      },

      //删除用户
      deleteUser(row){
        this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$store.dispatch('user/deleteUser',row).then(msg=>{
            this.$message.success(msg)
            this.getAllUserInfo()
          }).catch(err=>{
            this.$message.error(err)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
        
      },

      //查询用户
      searchUser(){
        if(this.searchInfo.userLogin != ''){
          let tagInfo =`账号：${this.searchInfo.userLogin}`
          this.dynamicTags = [tagInfo]
        }
        if(this.searchInfo.userName != ''){
          let tagInfo =`姓名：${this.searchInfo.userName}`
          this.dynamicTags = [tagInfo]
        }
        if(this.searchInfo.userLogin != '' && this.searchInfo.userName != ''){
          let tagInfo = `账号：${this.searchInfo.userLogin}，姓名：${this.searchInfo.userName}`
          this.dynamicTags = [tagInfo]
        }
        let queryData = {
          userLogin:this.searchInfo.userLogin,
          userName:this.searchInfo.userName,
        }
        this.searchInfoTemp = JSON.parse(JSON.stringify(queryData))
        this.getAllUserInfo(queryData)    
      },

      //删除标签
      handleClose(tag) {
        this.dynamicTags=[]
        this.$refs['searchForm'].resetFields()
        this.getAllUserInfo()
        this.searchInfoTemp={userLogin:'',userName:''}  
      },

      //提交表单
      submitForm(){
        this.$refs['userForm'].validate(valid => {
          //如果符合规则
          if (valid) {
            if(this.dialog.dialogType == "add"){
              //新增用户
              this.$store.dispatch('user/addUser',this.userForm).then(res=>{
                this.$message.success(res)    
                this.getAllUserInfo()
                this.dialog.dialogFormVisible = false;
              }).catch(err=>{
                this.$message.error(err)
              })

            }else{
              //编辑用户
              this.$store.dispatch('user/editUser',this.userForm).then(res=>{
                this.$message.success(res)    
                this.getAllUserInfo()
                this.dialog.dialogFormVisible = false;
              }).catch(err=>{
                this.$message.error(err)
              })
            }      
            return
          } 
          return false
        })
      },

      //普通用户修改信息
      changeMyInfo(row){
        this.$prompt('请输入密码', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {
          if(row.userPassword == md5(value)){
            this.userDialog(row,'edit')
            return;
          }
          this.$message({
            type: 'error',
            message: '密码错误'
          }); 
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });       
        });
      },

      //dialog关闭
      closeDialog(formName){
          this.$refs[formName].resetFields()     
      },

      //格式化用户角色
      handleStatus(value){
        if(value.userRole == 'USER'){
          return '用户'
        }else if(value.userRole == 'ADMIN'){
          return '管理员'
        }
      },
      
      //表格序号计算
      indexMethod(index){
        return (index + 1) + (this.pageInfo.current - 1) * this.pageInfo.size
      },

    }
}
</script>

<style scoped>
  #search{
    margin-top:40px ;
    width: 100%;
  }
  #tag{
    margin-top:20px ;
    height: 25px;
  }
  #table{
    margin-top:10px ;
    width: 100%;
    height:450px;
  }
  .el-tag + .el-tag {
    margin-left: 10px;
  }
</style>
