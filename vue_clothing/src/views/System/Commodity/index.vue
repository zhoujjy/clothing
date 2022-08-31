<template>
  <div id="user">
      <div id="search">
        <el-button type="primary" @click="userDialog(type='add')" :disabled="!authority">添加</el-button>
        <el-dialog :title="dialog.dialogTitle" @close="closeDialog('commodityForm')" :visible.sync="dialog.dialogFormVisible" width="30%" center destroy-on-close>
          <el-form  :model="commodityForm" ref="commodityForm" :rules="rules" label-position="left" label-width="20%">
            <el-form-item label="货号"  prop="id" >
              <el-input v-model="commodityForm.id" autocomplete="off" placeholder="8位数字" :disabled="dialog.dialogType == 'edit'"></el-input>
            </el-form-item>
            <el-form-item label="品名" prop="commName" >
              <el-input v-model="commodityForm.commName" autocomplete="off" placeholder="字符"></el-input>
            </el-form-item>
            <el-form-item label="色号" prop="commColor">
              <el-input v-model="commodityForm.commColor" autocomplete="off" placeholder="字符"></el-input>
            </el-form-item>
            <el-form-item label="尺码" prop="commSize">
              <el-input v-model="commodityForm.commSize" autocomplete="off" placeholder="字符"></el-input>
            </el-form-item>
            <el-form-item label="数量" prop="commNumber">
              <el-input v-model="commodityForm.commNumber" autocomplete="off" placeholder="数字"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialog.dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </div>
        </el-dialog>

        <el-form ref="searchForm" :inline="true" :model="searchInfo" class="demo-form-inline" style="float:right">
          <el-form-item label="货号" prop="id">
            <el-input v-model="searchInfo.id" placeholder="货号" ></el-input>
          </el-form-item>
          <el-form-item label="色号" prop="commColor">
            <el-input v-model="searchInfo.commColor" placeholder="色号" ></el-input>
          </el-form-item>
          <el-form-item label="尺码" prop="commSize">
            <el-input v-model="searchInfo.commSize" placeholder="尺码" ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchCommodity" >查询</el-button>
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
        <el-table :data="commodityInfo"  stripe border  style="width: 100%;margin-top:10px" >
          
          <el-table-column
            width="100px"
            label="序号"
            type="index"
            :index="indexMethod"
            align="center">
          </el-table-column>

          <el-table-column
            prop="id"
            label="货号"
            align="center">
          </el-table-column>

           <el-table-column
            prop="commName"
            label="品名"
            align="center">
          </el-table-column>

           <el-table-column
            prop="commColor"
            label="色号"
            align="center">
          </el-table-column>

          <el-table-column
            prop="commSize"
            label="尺码"
            align="center">
          </el-table-column>

          <el-table-column
            prop="commNumber"
            label="数量"
            align="center">
          </el-table-column>
          

          
          <el-table-column label="操作" align="center" width="180px" v-if="authority">
            <template slot-scope="scope" >
                <!--  :disabled="" 是否禁用 -->
                <el-button
                    slot="reference"
                    size="mini"
                    type="info" 
                    plain
                    @click="userDialog(scope.row,'edit')"
                    style="margin-right:10px">
                    编辑
                </el-button>

                 <el-button
                    slot="reference"
                    size="mini"
                    type="danger" 
                    @click="deleteCommodity(scope.row)"
                    style="margin-right:10px">
                    删除
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
export default {
    name:'Commodity',
    components:{
        Pagination
    },
    data(){
      // 表单验证规则定义

      //验证货号
        var checkId = (rule, value, callback) => {
            //8位数字
            const isbnReg = /^\d{8}$/;
            if (value === '') {
            return callback(new Error('请输入货号'));
            } 
            if (!isbnReg.test(value)) {
                callback(new Error('货号格式错误'));
            } else {
                callback();
            }
        };
        //验证数量
        var checkNumber = (rule, value, callback) => {
            //数字
            const isbnReg = /^\d{1,}$/;
            if (value === '') {
            return callback(new Error('请输入数量'));
            } 
            if (!isbnReg.test(value)) {
                callback(new Error('格式错误'));
            } else {
                callback();
            }
        };

      return{
        // authority:this.$store.state.user.userRole == 'ADMIN', // 权限

        commodityInfo:[
            // {id:'1',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'},
            // {id:'2',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'},
            // {id:'3',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'},
        ],
        
        commodityForm:{id:'',commName:'',commColor:'',commSize:'',commNumber:''},

        searchInfo:{
          id:'',
          commColor:'',
          commSize:''
        },
        searchInfoTemp:{ //用与查询状态下的查询条件保存
          id:'',
          commColor:'',
          commSize:''
        },

        pageInfo:{},

        dynamicTags: [],

        
        dialog:{
          dialogFormVisible: false,
          dialogTitle:'',
          dialogType:'',
        },

        rules: {
                id:[
                    { required: true,validator: checkId,trigger: 'blur' },
                ],
                commName:[
                    { required: true, message: '请设置品名', trigger: 'blur' },
                ],
                commColor:[
                    { required: true, message: '请设置色号', trigger: 'blur' },
                ],
                commSize:[
                    { required: true, message: '请设置尺码', trigger: 'blur' },
                ],
                commNumber:[
                    { required: true,validator: checkNumber,trigger: 'blur' },
                ],
        },

      }
    },

    created(){
      //this.$store.dispatch('user/getUserInfo')
      this.init()
    },

    computed:{
      authority(){
        return this.$store.state.user.userRole == 'ADMIN'
      }
    },


    methods:{
      // 查询所有记录
      init({currentPage = 1,id='',commColor='',commSize=''}={}){ //解构赋值设置默认参数
        let data = {
          currentPage,
          pageSize:6,
          id,
          commColor,
          commSize
        }
        this.$store.dispatch('commodity/getAllCommodityInfo',data).then(res=>{
          this.pageInfo = JSON.parse(JSON.stringify(res))
          this.commodityInfo = JSON.parse(JSON.stringify(res.records))
        })
      },

      //页码变化
      pageChange(currentPage){
        let data={
          currentPage,
          id:this.searchInfoTemp.id,
          commColor:this.searchInfoTemp.commColor,
          commSize:this.searchInfoTemp.commSize
        }
        this.init(data)
      },

      //编辑货品或新增货品dialog切换
      userDialog(row,type){
        if(type == 'edit'){
          this.dialog.dialogTitle = '编辑货品'
          this.dialog.dialogType = 'edit'
          this.$nextTick(()=>{
            this.commodityForm = JSON.parse(JSON.stringify(row))
          })
          
          
        }else{
          this.commodityForm = {id:'',commName:'',commColor:'',commSize:'',commNumber:''}
          this.dialog.dialogTitle = '新增货品'
          this.dialog.dialogType = 'add'
        }
        this.dialog.dialogFormVisible = true;
      },

      //删除货品
      deleteCommodity(row){
        this.$confirm('此操作将永久删除该货品, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

          this.$store.dispatch('commodity/deleteCommodity',row).then(res=>{
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.init()
          })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
        
      },

      //查询货品
      searchCommodity(){
        let tagInfo = ''
        if(this.searchInfo.id != ''){
          tagInfo = tagInfo+`货号：${this.searchInfo.id} `
        }
        if(this.searchInfo.commColor != ''){
          tagInfo =tagInfo+`色号：${this.searchInfo.commColor} `
        }
        if(this.searchInfo.commSize != ''){
          tagInfo =tagInfo+ `尺码：${this.searchInfo.commSize} `
        }
        if(tagInfo != ''){
          this.dynamicTags = [tagInfo]
        }

        let data={
          id:this.searchInfo.id,
          commColor:this.searchInfo.commColor,
          commSize:this.searchInfo.commSize
        }
        this.searchInfoTemp = JSON.parse(JSON.stringify(data))
        this.init(data)   
      },

      //删除标签
      handleClose(tag) {
        this.dynamicTags=[]
        this.$refs['searchForm'].resetFields()
        this.init()
        this.searchInfoTemp = {id:'',commColor:'',commSize:''}  
      },

      //提交表单
      submitForm(){
        this.$refs['commodityForm'].validate(valid => {
          //如果符合规则
          if (valid) {
            if(this.dialog.dialogType == "add"){
              //添加货品
              this.$store.dispatch('commodity/addCommodity',this.commodityForm).then(msg=>{
                this.$message.success(msg)
                this.init()
                this.dialog.dialogFormVisible = false
              }).catch(err=>{
                this.$message.error(err)
              })


            }else{
              //编辑货品
              this.$store.dispatch('commodity/editCommodity',this.commodityForm).then(msg=>{
                this.$message.success(msg)
                this.init()
                this.dialog.dialogFormVisible = false
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
          if(row.userPassword == value){
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
