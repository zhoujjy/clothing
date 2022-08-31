<template>
  <div class="pagination">
      <!--
          :current-page.sync:为默认页码 
          :page-size:为默认每页显示条数
           :total:为默认总条数
       -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="pageInfo.current"
      :page-size="pageInfo.size"
      layout="prev, pager, next, jumper"
      :total="pageInfo.total"
      background="">
    </el-pagination>
  </div>
</template>

<script>
export default {

    name:'pagination',

    data() {
      return{
        
      }
    },

    props:['pageInfo'],

    methods: {

      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },

      //切换页面时触发，currentPage为当前页码
      handleCurrentChange(currentPage) {
        this.$parent.pageChange(currentPage)
        //console.log(currentPage);
      }

    },

}
</script>

<style lang="less" scope>
.pagination{
    .el-pagination{
        text-align: center;
        margin-top: 10px;
    }
    .el-pager li{
        min-width: 35px !important;
    }
}

</style>