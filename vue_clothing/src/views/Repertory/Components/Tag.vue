<template>
  <div>
      <el-tag
        :key="tag"
        v-for="tag in dynamicTags"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)">
        {{tag}}
      </el-tag>
  </div>
</template>

<script>
export default {
    name: 'Tag',
    data(){
        return{
            dynamicTags:[]
        }
    },

    methods:{
        // 删除标签
        handleClose() {
            this.$parent.cancelSearch();
        },
    }
}
</script>

<style scoped>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
</style>