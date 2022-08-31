<template>
  <div>
    <el-button type="primary" @click="addData" :disabled="!authority">新增</el-button>
    <div id="search">
      <el-form :inline="true" :model="searchInfo" class="demo-form-inline">
        <el-form-item label="单据编号">
          <el-input v-model="searchInfo.id" placeholder="单据编号"></el-input>
        </el-form-item>
        <el-form-item label="所入仓库" v-if="isInbound">
          <el-select v-model="searchInfo.storage" placeholder="所入仓库">
            <el-option label="一号仓库" value="一号仓库"></el-option>
            <el-option label="二号仓库" value="二号仓库"></el-option>
            <el-option label="三号仓库" value="三号仓库"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收人" v-if="!isInbound">
          <el-input v-model="searchInfo.recipient" placeholder="接收人"></el-input>
        </el-form-item>
        <el-form-item :label="isInbound ? '入库日期' : '出库日期'">
          <el-date-picker
            id="dataPicker"
            v-model="searchInfo.dateRange"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Head",
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },

      searchInfo: { id: "", storage: "", recipient: "", dateRange: [] },
    };
  },

  computed: {
    authority() {
      return this.$store.state.user.userRole == "ADMIN";
    },
  },

  props: ["isInbound"],

  methods: {
    // 提交表单
    onSubmit() {
      this.$parent.searchData(this.searchInfo);
    },

    // 新增
    addData() {
      this.$parent.addData();
    },
  },
};
</script>

<style scoped>
#search {
  float: right;
}
</style>