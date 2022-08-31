<template>
  <div id="table">
      <el-table :data="pageInfo.records"  stripe border  style="width: 100%;margin-top:10px" >
          
          <el-table-column
            width="100px"
            label="序号"
            type="index"
            :index="indexMethod"
            align="center">
          </el-table-column>

          <el-table-column
            prop="id"
            label="单据编号"
            align="center">
          </el-table-column>

          <el-table-column
            prop="storage"
            v-if="isInbound"
            label="所入仓库"
            align="center">
          </el-table-column>

           <el-table-column
            prop="date"
            :label="isInbound?'入库日期':'出库日期'"
            sortable
            align="center">
          </el-table-column>


          <el-table-column
            prop="operatorId"
            label="经办人"
            align="center"
            >
          </el-table-column>

          <el-table-column
            v-if="isInbound"
            prop="source"
            label="来源"
            align="center">
          </el-table-column>
          <el-table-column
            v-else
            prop="recipient"
            label="接收人"
            align="center"
            >
          </el-table-column>

          <el-table-column
            prop="recipientPhone"
            v-if="!isInbound"
            label="联系电话"
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
                    icon="el-icon-view"
                    @click="editData(scope.row)"
                    style="margin-right:10px">
                </el-button>

                 <el-button
                    slot="reference"
                    size="mini"
                    type="danger" 
                    icon="el-icon-delete"
                    @click="deleteData(scope.row)"
                    style="margin-right:10px">
                </el-button>
            </template>
          </el-table-column>
        </el-table>
  </div>
</template>

<script>
export default {
    name: 'Table',
    data(){
        return{

        }
    },

    props:['isInbound','pageInfo'],

    computed:{
      authority(){
        return this.$store.state.user.userRole == 'ADMIN'
      }
    },



    methods:{
        //编辑数据
        editData(data){
          this.$parent.editData(data)
        },

        //删除数据
        deleteData(row){
          this.$parent.deleteData(row)
        },
        //表格序号计算
        indexMethod(index){
            return (index + 1) + (this.pageInfo.current - 1) * this.pageInfo.size
        },

        

    },
}
</script>

<style>
#table{
  height: 450px;
}
</style>