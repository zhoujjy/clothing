<template>
  <div>
    <div id="search">
      <el-form
        ref="searchForm"
        :inline="true"
        :model="searchInfo"
        class="demo-form-inline"
        style="float: right"
      >
        <el-form-item label="货品编号" prop="commId">
          <el-input
            v-model="searchInfo.commId"
            placeholder="货品编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="修改日期" prop="dateRange">
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
    <div id="tag">
      <el-tag
        v-for="(tag, index) in dynamicTags"
        :key="index"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)"
      >
        {{ tag }}
      </el-tag>
    </div>
    <div id="table">
      <el-table
        :data="recordInfo"
        stripe
        border
        style="width: 100%; margin-top: 10px"
      >
        <!-- <el-table-column
            width="100px"
            label="序号"
            type="index"
            :index="indexMethod"
            align="center">
          </el-table-column> -->

        <el-table-column prop="commId" label="货号" align="center">
        </el-table-column>

        <el-table-column prop="date" label="修改日期" sortable align="center">
        </el-table-column>

        <el-table-column
          prop="modelType"
          label="模块名"
          :formatter="handleStatusByModelType"
          align="center"
        >
        </el-table-column>

        <el-table-column prop="operator" label="操作人" align="center">
        </el-table-column>

        <el-table-column
          prop="operationType"
          label="操作类型"
          :formatter="handleStatusByOperationType"
          align="center"
        >
        </el-table-column>

        <el-table-column prop="number" label="更改数量" align="center">
        </el-table-column>
      </el-table>
    </div>
    <Pagination :pageInfo="pageInfo" />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination/index.vue";
export default {
  name: "Record",
  components: { Pagination },
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

      searchInfo: {
        commId: "",
        dateRange: [],
      },
      searchInfoTemp: {
        commId: "",
        dateRange: [],
      },
      pageInfo: {},
      dynamicTags: [],
      recordInfo: [
        //{commId:'1',modelType:'1',date:'1',operator:'1',operationType:'1',number:'1'}
      ],
    };
  },

  created() {
    this.init();
  },

  methods: {
    //初始化
    init({ currentPage = 1, commId = "", dateRange = [] } = {}) {
      let data = {
        currentPage,
        pageSize: 6,
        commId,
        dateRange,
      };
      this.$store.dispatch("record/getRecordList", data).then((res) => {
        let pageInfo = JSON.parse(JSON.stringify(res));
        pageInfo.records.forEach((item) =>
          this.$store
            .dispatch("user/getUserName", item.operator)
            .then((res) => (item.operator = res.userName))
        );
        this.pageInfo = pageInfo;
        this.recordInfo = pageInfo.records;
      });
    },

    //查询
    onSubmit() {
      let tagInfo = "";
      if (this.searchInfo.commId != "") {
        tagInfo = tagInfo + `货品编号：${this.searchInfo.commId} `;
      }
      if (this.searchInfo.dateRange.length != 0) {
        tagInfo =
          tagInfo +
          `日期范围：${this.searchInfo.dateRange[0]} ~ ${this.searchInfo.dateRange[1]} `;
      }
      if (tagInfo != "") {
        this.dynamicTags = [tagInfo];
      }

      this.searchInfoTemp = JSON.parse(JSON.stringify(this.searchInfo));
      let data = {
        commId: this.searchInfo.commId,
        dateRange: this.searchInfo.dateRange,
      };
      this.init(data);
    },

    //页码变化
    pageChange(currentPage) {
      let data = {
        currentPage,
        commId: this.searchInfoTemp.commId,
        dateRange: this.searchInfoTemp.dateRange,
      };
      this.init(data);
    },

    //删除标签
    handleClose() {
      this.dynamicTags = [];
      this.$refs["searchForm"].resetFields();
      this.init();
      this.searchInfoTemp = { commId: "", dateRange: [] };
    },

    //dialog关闭
    closeDialog(formName) {
      this.$refs[formName].resetFields();
    },

    //表格序号计算
    indexMethod(index) {
      return index + 1 + (this.pageInfo.current - 1) * this.pageInfo.size;
    },

    handleStatusByModelType(row) {
      switch (row.modelType) {
        case 1:
          return "货品模块";
        case 2:
          return "出入库模块";
      }
    },

    handleStatusByOperationType(row) {
      switch (row.operationType) {
        case 1:
          return "增加";
        case 2:
          return "减少";
        case 3:
          return "删除";
        case 4:
          return "新增";
      }
    },
  },
};
</script>

<style scoped>
#search {
  position: relative;
  margin-top: 40px;
  height: 50px;
  width: 100%;
}
#tag {
  margin-top: 20px;
  height: 25px;
}
#table {
  margin-top: 15px;
  width: 100%;
  height: 450px;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>