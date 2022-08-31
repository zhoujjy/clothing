<template>
  <div>
      <div id="h2">
        <el-button @click="backPage" class="iconfont icon-return1x" size="small" type="primary" title="返回上一页" plain></el-button>
        <el-button @click="saveInfo" v-if="isAdd" class="iconfont icon-icon_baocun" size="small" type="primary" title="提交页面信息" plain></el-button>
      </div>
        <div class="detailTitle">
          <span>入库单信息</span>
          <el-button @click="updateInbound" v-if="!isAdd" class="iconfont icon-icon_baocun" size="small" type="primary" plain title="入库单信息保存" style="font-size:15px !important"></el-button>
      </div>
      <div id="info">
        <el-form ref="form" :model="dataInfo" label-width="80px" id="form">
            <el-form-item label="单据号" >
                <el-input v-model="dataInfo.id" disabled placeholder="自动编号" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="来源">
                <el-input v-model="dataInfo.source" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="入库日期">
                <el-date-picker
                v-model="dataInfo.date"
                type="date"
                placeholder="选择日期"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="所入仓库">
                <el-select v-model="dataInfo.storage" placeholder="所入仓库" style="width:220px">
                    <el-option label="一号仓库" value="一号仓库"></el-option>
                    <el-option label="二号仓库" value="二号仓库"></el-option>
                    <el-option label="三号仓库" value="三号仓库"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="备注" >
                <el-input type="textarea" v-model="dataInfo.remark" style="width:700px;resize:none"></el-input>
            </el-form-item>
        </el-form>
      </div>
      <div class="detailTitle">
          <span>入库单明细</span>
            <el-button icon="el-icon-circle-plus-outline" size="small" type="primary" plain @click="addDetail">新增</el-button>
      </div>
      <div id="detail">
          <el-table :data="dataInfo.detail" height="190" size="small" stripe border  style="width: 100%;margin-top:10px;padding:10px;" >
          
          <el-table-column
            width="100px"
            label="序号"
            type="index"
            align="center">
          </el-table-column>

          <el-table-column
            prop="commId"
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
          

          
          <el-table-column label="操作" align="center" width="180px">
            <template slot-scope="scope" >
                <el-button
                slot="reference" 
                type="primary" 
                icon="el-icon-edit"
                size="mini"
                @click="editDetail(scope.row)" 
                circle>
                </el-button>
                <!--  :disabled="" 是否禁用 -->
                 <el-button
                    slot="reference"
                    size="mini"
                    type="danger" 
                    icon="el-icon-delete" 
                    circle
                    @click="deleteDeail(scope.$index,scope.row)"
                    style="margin-right:10px">
                </el-button>
                
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-dialog :title="isAddDetail?'新增明细':'修改明细'" @close="closeDialog('commodityForm')" :visible.sync="dialogFormVisible" width="30%" center destroy-on-close>
          <el-form  :model="commodityForm" ref="commodityForm" :rules="rules" label-position="left" label-width="20%">
            <el-form-item label="货号"  prop="commId" >
              <el-input v-model="commodityForm.commId" @blur="getCommdityById" :disabled="!isAddDetail" autocomplete="off" placeholder="8位数字" ></el-input>
            </el-form-item>
            <el-form-item label="品名" prop="commName" >
              <el-input v-model="commodityForm.commName" autocomplete="off" placeholder="字符" :disabled="autoGetInfo"></el-input>
            </el-form-item>
            <el-form-item label="色号" prop="commColor">
              <el-input v-model="commodityForm.commColor" autocomplete="off" placeholder="字符" :disabled="autoGetInfo"></el-input>
            </el-form-item>
            <el-form-item label="尺码" prop="commSize">
              <el-input v-model="commodityForm.commSize" autocomplete="off" placeholder="字符" :disabled="autoGetInfo"></el-input>
            </el-form-item>
            <el-form-item label="数量" prop="commNumber">
              <el-input v-model="commodityForm.commNumber" autocomplete="off" placeholder="数字"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </div>
        </el-dialog>
  </div>
</template>

<script>
export default {
    name: 'RepertoryDetail',
    //路由守卫
    beforeRouteEnter(to, from, next) {
        to.meta.title = to.params.title;
        next();
    },
    data() {
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

        return {
            isAdd: false, //是否新增
            isAddDetail:true, //是否新增明细
            autoGetInfo: false,//是否自动获取信息
            dataInfo: {id:'',storage:'',date:'',operator:'',source:'',remark:'',detail:[
                // {id:'1',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'},
                // {id:'2',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'},
                // {id:'3',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'},
                //  {id:'3',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'},
            ]},
            commodityForm:{commId:'',commName:'',commColor:'',commSize:'',commNumber:''},
            dialogFormVisible:false,
             rules: {
                commId:[
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
        this.isAdd = this.$route.params.isAdd;
        if(this.$route.params.title==undefined){
            //this.$router.push({name:'borrowBook'});
            this.$router.back()
            return
        }
        if(this.$route.params.data){
            let dataInfo=JSON.parse(JSON.stringify(this.$route.params.data))
            Object.keys(dataInfo).forEach(key=>{
                this.dataInfo[key]=dataInfo[key]
            })
        }

        this.queryDetail()
    },

    methods:{
        //保存页面信息
        saveInfo(){
            
            if(!this.checkInfo().isTrue){
                this.$message.error(this.checkInfo().msg)
                return
            }
             this.$confirm('是否保存信息?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                    this.$store.dispatch('inbound/addInbound',this.dataInfo).then(data=>{
                         this.$alert('您的单据号为：'+data.inboundId, '提示', {
                            confirmButtonText: '确定',
                            callback: action => {
                                this.$router.back()
                            }
                        });         
                    }).catch(err=>{
                        this.$message.error(err)
                    })
                }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '操作已取消'
                });          
            });
        },

        //更新入库单信息
        updateInbound(){
             if(!this.checkInfo().isTrue){
                this.$message.error(this.checkInfo().msg)
                return
            }
            this.$confirm('你的操作将更新入库单信息，是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                    let params={
                        id:this.dataInfo.id,
                        storage:this.dataInfo.storage,
                        date:this.dataInfo.date,
                        source:this.dataInfo.source,
                        remark:this.dataInfo.remark,
                    }
                    this.$store.dispatch('inbound/updateInbound',params).then(data=>{
                         this.$message.success(data)
                         this.$router.back()     
                    }).catch(err=>{
                        this.$message.error(err)
                    })

                }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '操作已取消'
                });          
            });
        },

        //提交表单
        submitForm(){        
            this.$refs['commodityForm'].validate(valid => {
                if(valid){

                    //详情增加模块(数组添加)
                    if(this.isAdd){
                        //添加详情
                        if(this.isAddDetail){
                            let isExist=false;
                            this.dataInfo.detail.forEach(item=>{
                                if(item.commId==this.commodityForm.commId){
                                    this.$message.error('该货号已存在')
                                    isExist=true;
                                    return
                                }
                            })
                            if(isExist) return
                            let data = JSON.parse(JSON.stringify(this.commodityForm));
                            this.dataInfo.detail.push(data);
                            this.dialogFormVisible=false
                            return
                        }
                        //修改详情
                        this.dataInfo.detail.forEach(item=>{
                            if(item.commId==this.commodityForm.commId){
                                item.commName=this.commodityForm.commName
                                item.commColor=this.commodityForm.commColor
                                item.commSize=this.commodityForm.commSize
                                item.commNumber=this.commodityForm.commNumber
                                this.dialogFormVisible=false
                                return
                            }
                        })
                        
                    }
                    //详情修改模块(请求添加或者修改)
                    if(this.isAddDetail){
                        //添加详情
                        let data = JSON.parse(JSON.stringify(this.commodityForm));
                        data["id"]=this.dataInfo.id
                        this.$store.dispatch('inbound/addInboundDetail',data).then(data=>{
                            this.$message.success(data)
                            this.dialogFormVisible=false
                            this.queryDetail()
                        }).catch(err=>{
                            this.$message.error(err)
                        })

                    }else{
                        //修改详情
                        let data = JSON.parse(JSON.stringify(this.commodityForm));
                        data["id"]=this.dataInfo.id
                        this.$store.dispatch('inbound/updateInboundDetail',data).then(data=>{
                            this.$message.success(data)
                            this.dialogFormVisible=false
                            this.queryDetail()
                        }).catch(err=>{
                            this.$message.error(err)
                        })
                    }
                    

                }
                return false;
            })

            
            
        },

        //新增明细打开
        addDetail(){
            this.isAddDetail=true
            this.dialogFormVisible=true
        },

        //修改明细回显
        editDetail(data){
            this.isAddDetail=false
            this.$nextTick(()=>{
                Object.keys(data).forEach(key=>{
                    if(key != 'id' && key != 'isDeleted'){
                        this.commodityForm[key]=data[key]
                    }
                    
                })
                if(this.isAdd){
                    //if(this.commodityForm.commId=='') return
                    this.$store.dispatch('commodity/getCommodityById',this.commodityForm.commId).then(res=>{
                        if(res==null) {
                            this.autoGetInfo=false
                            return
                        }
                        this.autoGetInfo=true
                        this.commodityForm.commName=res.commName
                        this.commodityForm.commColor=res.commColor
                        this.commodityForm.commSize=res.commSize
                    })
                }else{
                    this.getCommdityById()
                }
                
                // this.commodityForm = data
                
            })
            this.dialogFormVisible=true
            
        },

        //页面信息非空检查
        checkInfo(){
            if(this.dataInfo.storage=='') return {isTrue:false,msg:'所入仓库不能为空'}
            if(this.dataInfo.date == '') return {isTrue:false,msg:'日期不能为空'}
            if(this.dataInfo.source == '') return {isTrue:false,msg:'来源不能为空'}
            if(this.dataInfo.detail.length==0) return {isTrue:false,msg:'明细不能为空'}
            return {isTrue:true}
        },

        //查询明细
        queryDetail(){
            if(this.dataInfo.id=='') return
            this.$store.dispatch('inbound/queryDetail',this.dataInfo.id).then(res=>{
                this.dataInfo.detail=[]
                res.forEach(item=>{
                    this.dataInfo.detail.push(item)
                })
                
               
            })
        },

        //删除明细
        deleteDeail(data,row){
            
            this.$confirm('该操作将删除数据，是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {

                    if(this.isAdd){
                        //数组方式删除
                        this.dataInfo.detail.splice(data,1)
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    }else{
                        //请求方式删除明细
                        this.$store.dispatch('inbound/deleteDetail',row).then(res=>{
                            this.$message({
                                type: 'success',
                                message: res
                            });
                            this.queryDetail()
                        }).catch(err=>{
                            this.$message.error(err)
                        })
                    }
                    
                    
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '操作已取消'
                    });          
            });
        
            
        },

        //根据id获取货品信息
        getCommdityById(){
            if(this.commodityForm.commId=='') return
            this.$store.dispatch('commodity/getCommodityById',this.commodityForm.commId).then(res=>{
                if(res==null) {
                    this.autoGetInfo=false
                    this.commodityForm.commName=""
                    this.commodityForm.commColor=''
                    this.commodityForm.commSize=''
                    return
                }
                this.autoGetInfo=true
                this.commodityForm.commName=res.commName
                this.commodityForm.commColor=res.commColor
                this.commodityForm.commSize=res.commSize
            })
        },



        //页面回退
        backPage(){
            this.$router.back()
        },
        //dialog关闭
        closeDialog(formName){
            this.autoGetInfo=false
            this.$refs[formName].resetFields()     
        },
    }

}
</script>

<style scoped>
    #h2{
        margin-top: 10px;
        display: flex;
        justify-content: space-between;
        height: 30px;

    }
    #h2 button{
        padding: 5px;
        font-size: 20px !important;
    }

    #info{
        margin-top: 10px;
        background-color: #fafafa;
    }
    #detail{
        margin-top: 10px;
    }
    #form{
        width: 600px;
        padding-top: 20px;
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
    }
    .detailTitle{
        position: relative;
        border-radius: 30px;
        margin-top: 20px;
        height: 50px;
        background-color: #d9ecff;
    }
    .detailTitle span{
        font-size: 20px;
        line-height: 50px;
        margin-left: 20px;
    }
    .detailTitle button{
        position: absolute;
        right: 30px;
        top: 8px;
        font-size: 10px !important; 
    }
 
</style>
<style>
   .el-textarea__inner{
        resize: none;
        height: 100px;
    }
    .el-table__header-wrapper{
        background-color: #d9ecff !important;
    }
</style>