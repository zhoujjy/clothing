<template>
  <div>
    <Head id="head" :isInbound="false" ref="head"/>
    <Tag id="tag" ref="tag"/>
    <Table id="table" :isInbound="false" :pageInfo="pageInfo" />
    <Pagination :pageInfo="pageInfo" />
  </div>
</template>

<script>
import Head from "../Components/Head.vue";
import Table from "../Components/Table.vue";
import Tag from "../Components/Tag.vue";
import Pagination from "@/components/Pagination/index.vue";
export default {
  name: "Outbound",
  components: {
    Head,
    Table,
    Tag,
    Pagination
  },
  data() {
    return {
      pageInfo: {},
      outboundDataList: [
        // {id:'2',commId:'2',storage:'2',date:'2',operator:'张三',recipient:'2',remark:'2',detail:[{id:'1',commName:'棉袄',commColor:'红色',commSize:'S',commNumber:'100'}]},
        // {id:'2',commId:'2',storage:'2',date:'2',operator:'张三',recipient:'2',recipientPhone:'324234242342',remark:'2'},
        // {id:'2',commId:'2',storage:'2',date:'2',operator:'张三',recipient:'2',recipientPhone:'324234242342',remark:'2'},
        // {id:'2',commId:'2',storage:'2',date:'2',operator:'张三',recipient:'2',recipientPhone:'324234242342',remark:'2'},
        // {id:'2',commId:'2',storage:'2',date:'2',operator:'张三',recipient:'2',recipientPhone:'324234242342',remark:'2'},
        // {id:'2',commId:'2',storage:'2',date:'2',operator:'张三',recipient:'2',recipientPhone:'324234242342',remark:'2'},
      ],
      searchInfo: { id: "", recipient: "", dateRange: [] },
    };
  },

  created() {
    this.init();
  },

  methods: {
    //页面初始化
    async init({currentPage = 1,id = "",recipient = "",dateRange = [],} = {}) {
      let data = {
        currentPage,
        pageSize: 2,
        id,
        recipient,
        dateRange,
      };
      this.$store.dispatch("outbound/getAllOutboundInfo", data).then((res) => {
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
      if (searchInfo.recipient != "") {
        tagInfo = tagInfo + `接收人：${searchInfo.recipient} `;
      }
      if (searchInfo.dateRange.length != 0) {
        tagInfo =tagInfo +`日期范围：${searchInfo.dateRange[0]} ~ ${searchInfo.dateRange[1]} `;
      }
      if (tagInfo != "") {
        this.$refs.tag.dynamicTags = [tagInfo];
      }

      this.searchInfo = JSON.parse(JSON.stringify(searchInfo));
      let data = {
        id: this.searchInfo.id,
        recipient: this.searchInfo.recipient,
        dateRange: this.searchInfo.dateRange,
      };
      this.init(data);
    },

    //取消查询
    cancelSearch() {
      this.$refs.tag.dynamicTags = [];
      this.$refs.head.searchInfo = { id: "", recipient: "", dateRange: [] };
      this.init();
      this.searchInfo = { id: "", recipient: "", dateRange: [] };
    },

    //页码变化
    pageChange(currentPage) {
      let data = {
        currentPage,
        id: this.searchInfo.id,
        storage: this.searchInfo.recipient,
        dateRange: this.searchInfo.dateRange,
      };
      this.init(data);
    },

    // 新增出库
    addData() {
      this.$router.push({
        name: "outboundDetail",
        params: {
          data: undefined,
          title: "新建出库单",
          isAdd: true,
        },
      });
    },

    //编辑数据
    editData(data) {
      this.$router.push({
        name: "outboundDetail",
        params: {
          data,
          title: "编辑出库单",
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
            .dispatch("outbound/deleteOutboundInfo", data.id)
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

<style>
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