<template>
  <div>
    <Head id="head" :isInbound="true" ref="head" />
    <Tag id="tag" ref="tag" />
    <Table id="table" :isInbound="true" :pageInfo="pageInfo" />
    <Pagination :pageInfo="pageInfo" />
  </div>
</template>

<script>
import Head from "../Components/Head.vue";
import Table from "../Components/Table.vue";
import Tag from "../Components/Tag.vue";
import Pagination from "@/components/Pagination/index.vue";
export default {
  name: "Inbound",
  components: { Head, Table, Tag, Pagination },
  data() {
    return {
      pageInfo: {},
      inboundDataList: [
        // {id:'1',storage:'1',date:'2022-01-02',operator:'1',source:'1',remark:'1',detail:[{id:'1',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'}]},
        // {id:'2',storage:'1',date:'2022-01-03',operator:'1',source:'1',remark:'1'},
        // {id:'3',storage:'1',date:'2022-01-04',operator:'1',source:'1',remark:'1'},
        // {id:'4',storage:'1',date:'2022-01-05',operator:'1',source:'1',remark:'1'},
        // {id:'5',storage:'1',date:'2022-01-06',operator:'1',source:'1',remark:'1'},
        // {id:'6',storage:'1',date:'2022-01-07',operator:'1',source:'1',remark:'1'},
      ],
      searchInfo: { id: "", storage: "", dateRange: [] },
    };
  },

  created() {
    this.init();
  },

  methods: {
    //页面初始化
    async init({currentPage = 1,id = "",storage = "",dateRange = []} = {}) {
      let data = {
        currentPage,
        pageSize: 2,
        id,
        storage,
        dateRange,
      };
      this.$store.dispatch("inbound/getAllInboundInfo", data).then((res) => {
        let pageInfo = JSON.parse(JSON.stringify(res));
        pageInfo.records.forEach((item) =>this.$store.dispatch("user/getUserName", item.operatorId).then((res) => (item.operatorId = res.userName)));
        this.pageInfo = pageInfo;
      });
    },

    // 查询
    searchData(searchInfo) {
      let tagInfo = "";
      if (searchInfo.id != "") {
        tagInfo = tagInfo + `单据编号：${searchInfo.id} `;
      }
      if (searchInfo.storage != "") {
        tagInfo = tagInfo + `所入仓库：${searchInfo.storage} `;
      }
      if (searchInfo.dateRange.length != 0) {
        tagInfo =
          tagInfo +
          `日期范围：${searchInfo.dateRange[0]} ~ ${searchInfo.dateRange[1]} `;
      }
      if (tagInfo != "") {
        this.$refs.tag.dynamicTags = [tagInfo];
      }

      this.searchInfo = JSON.parse(JSON.stringify(searchInfo));
      let data = {
        id: this.searchInfo.id,
        storage: this.searchInfo.storage,
        dateRange: this.searchInfo.dateRange,
      };
      this.init(data);
    },

    //页码变化
    pageChange(currentPage) {
      let data = {
        currentPage,
        id: this.searchInfo.id,
        storage: this.searchInfo.storage,
        dateRange: this.searchInfo.dateRange,
      };
      this.init(data);
    },

    //取消查询
    cancelSearch() {
      this.$refs.tag.dynamicTags = [];
      this.$refs.head.searchInfo = { id: "", storage: "", dateRange: [] };
      this.init();
      this.searchInfo = { id: "", storage: "", dateRange: [] };
    },

    // 新增入库
    addData() {
      this.$router.push({
        name: "inboundDetail",
        params: {
          data: undefined,
          title: "新建入库单",
          isAdd: true,
        },
      });
    },

    //编辑数据
    editData(data) {
      this.$router.push({
        name: "inboundDetail",
        params: {
          data,
          title: "编辑入库单",
          isAdd: false,
        },
      });
    },

    //删除数据
    deleteData(data) {
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$store
            .dispatch("inbound/deleteInboundInfo", data.id)
            .then((res) => {
              this.$message({
                type: "success",
                message: res,
              });
              this.init();
            })
            .catch((err) => {
              this.$message({
                type: "error",
                message: err,
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>

<style scoped>
#head {
  margin-top: 40px;
}
#tag {
  margin-top: 20px;
  height: 30px;
}
#table {
  margin-top: 20px;
}
</style>