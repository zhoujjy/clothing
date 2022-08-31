<template>
  <div >
    <el-row :gutter="60" style="margin-top:10px">
      <el-col :span="8">
        <el-card id="userCard"  style="height:200px;">
          <div class="card-header">
            <span>用户</span>
            <svg t="1647945172957" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2199" width="16" height="16"><path d="M536 480v192a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16V480a16 16 0 0 1 16-16h16a16 16 0 0 1 16 16z m-32-128h16a16 16 0 0 1 16 16v32a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16v-32a16 16 0 0 1 16-16z m8 448c159.056 0 288-128.944 288-288s-128.944-288-288-288-288 128.944-288 288 128.944 288 288 288z m0 48c-185.568 0-336-150.432-336-336s150.432-336 336-336 336 150.432 336 336-150.432 336-336 336z" p-id="2200" fill="#8a8a8a"></path></svg>
          </div>
          <div class="content">
            <img src="../../assets/images/admin.png"  alt="" width="120" style="margin-top:10px">
            <div class="text">
              <div>角色：{{$store.state.user.userRole=='ADMIN'?'管理员':'用户'}}</div>
              <div>姓名：{{$store.state.user.userName}}</div>
              <div>账号：{{$store.state.user.userLogin}}</div>
            </div>
          </div>
          
          
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card style="height:200px">
          <div class="card-header">
            <span>出库量</span>
            <svg t="1647945172957" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2199" width="16" height="16"><path d="M536 480v192a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16V480a16 16 0 0 1 16-16h16a16 16 0 0 1 16 16z m-32-128h16a16 16 0 0 1 16 16v32a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16v-32a16 16 0 0 1 16-16z m8 448c159.056 0 288-128.944 288-288s-128.944-288-288-288-288 128.944-288 288 128.944 288 288 288z m0 48c-185.568 0-336-150.432-336-336s150.432-336 336-336 336 150.432 336 336-150.432 336-336 336z" p-id="2200" fill="#8a8a8a"></path></svg>
          </div>
          <div class="echars">
            <span style="font-size: 40px;position: absolute;top: 60px;">
              <svg t="1654932872600" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4655" width="32" height="32"><path d="M959.52024 543.984008c0 264.571714-215.444278 480.015992-480.015992 480.015992S0 808.043978 0 543.984008s215.444278-480.015992 480.015992-480.015992v480.015992h479.504248z" fill="#9FC2F8" p-id="4656"></path><path d="M1023.488256 480.015992h-480.015992V0C808.043978 0 1023.488256 215.444278 1023.488256 480.015992z" fill="#B3DEFC" p-id="4657"></path></svg>
              {{statisticsData.allOutboundNum}}
            </span>
          </div>
          <div class="card-footer">
            <span>
              当天数量：&nbsp;{{statisticsData.todayOutboundNum||0}}
            </span>
            <svg t="1654932198850" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2204" width="16" height="16"><path d="M698.7 480.9v429.5c0 54.8-44.5 99.3-99.3 99.3H434c-54.8 0-99.3-44.5-99.3-99.3V480.9H70l446.9-463 446.9 463H698.7z m0 0" fill="#1afa29" p-id="2205"></path></svg>
          </div>  
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card style="height:200px">
          <div class="card-header">
            <span>入库变化</span>
            <svg t="1647945172957" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2199" width="16" height="16"><path d="M536 480v192a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16V480a16 16 0 0 1 16-16h16a16 16 0 0 1 16 16z m-32-128h16a16 16 0 0 1 16 16v32a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16v-32a16 16 0 0 1 16-16z m8 448c159.056 0 288-128.944 288-288s-128.944-288-288-288-288 128.944-288 288 128.944 288 288 288z m0 48c-185.568 0-336-150.432-336-336s150.432-336 336-336 336 150.432 336 336-150.432 336-336 336z" p-id="2200" fill="#8a8a8a"></path></svg>
          </div>
          <div class="echars">
            <LineCharts></LineCharts>
          </div>
          <div class="card-footer">
            库存余量：{{statisticsData.stockNum}}
          </div>     
        </el-card>
      </el-col>
    </el-row>
    <div class="center">
      <div class="table">
      <h4>在线人员</h4>
      <template>
        <el-table :data="onlineUser" style="width: 100%;margin-top:20px;box-shadow: 0 0 10px rgba(0,0,0,0.1);">
          <el-table-column
            prop="userLogin"
            label="账号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="userName"
            label="姓名"
            width="180">
          </el-table-column>
          <el-table-column label="操作" align="center" width="80px">
            <template slot-scope="scope" >
                <!--  :disabled="" 是否禁用 -->
              <el-link @click.prevent="quitLogin(scope.row)" type="danger">强制退出</el-link>
            </template>
          </el-table-column>
        </el-table>
    </template>

      </div>
      <div class="barchart">
        <BarChart/>
      </div>
    </div>
    
  </div>
</template>

<script>
import LineCharts from './lineChart'
import BarChart from './BarChart'
export default {
    name: 'Home',
    components:{LineCharts,BarChart},
    data(){
      return{
        onlineUser:[],
        statisticsData:{}
      }
        
    },
    computed:{
      isAdmin(){
        return this.$store.state.user.userRole == 'ADMIN'
      }
    },
    created(){
      this.getOnlineUser();
      this.getStatistics();
    },
    methods:{
      //获得所有在线用户
      getOnlineUser(){
        this.$store.dispatch('user/getOnlineUser').then(res=>{
          this.onlineUser = res
        })
      },
      // 退出登录
      quitLogin(row){
        if(!this.isAdmin){
          this.$message({
            type: 'info',
            message: '您没有权限'
          })
          return
        }
        this.$confirm('确定强制退出此人?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$store.dispatch('user/kickOut',row.id).then(res=>{
            this.getOnlineUser()
            this.$message.success(res)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '操作已取消'
          });
        });
      },
      //获得统计数据
      getStatistics(){
        this.$store.dispatch('record/getStatistics').then(res=>{
          this.statisticsData = JSON.parse(JSON.stringify(res))
        })
      }

    }
}
</script>

<style scoped>
  .card-header{
        display: flex;
        justify-content: space-between;
        color: #d9d9d9;
    }
  .card-footer{
    border-top: 1px solid #eee;
    padding: 5px 0;
  }
  .text{
    display: flex;
    flex-wrap: wrap;
    flex-direction: column;
    justify-content: space-around;
    margin-top:10px;
    width:230px;
    height:120px;
  }
    .content{
   display: flex;
   justify-content: space-around;
 }
   .echars{
    width:100%;
    height: 120px;
  }
 .center{
  display: flex;
  justify-content: space-between;
  height: 450px;
 }

 .table{
   margin-top: 20px;
   background-color: #ffffff;
 }
 .barchart{
   display:inline-block;
    margin-top: 20px;
    width: 45%;
    height: 450px;
    background-color: #ffffff;
 }
</style>
