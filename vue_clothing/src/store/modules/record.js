import recordApi from '@/api/record'

const state = {
  inboundChange:[],
  stockNumByTypeName : [],
  stockNumByTypeData : [],
  stockNumByTypeDataMax:0,
}

const actions = {

    // 获取入库记录列表
    async getRecordList({commit},data){
        let result =await recordApi.getRecordList(data)
        return Promise.resolve(result.data.data)
    },
    //获得统计数据
    async getStatistics({commit}){
        let result =await recordApi.getStatistics()
        commit('SETSTATISTICS',result.data.data)
        return Promise.resolve(result.data.data)
    }



}

const mutations = {
  SETSTATISTICS(state,data){
    state.inboundChange = data.inboundChange
    state.stockNumByTypeName = []
    state.stockNumByTypeData = []
    data.stockNumByType.forEach(item=>{
      if(item.comm_number>state.stockNumByTypeDataMax){
        state.stockNumByTypeDataMax = item.comm_number
      } 
      
      state.stockNumByTypeName.push(item.comm_name)
      state.stockNumByTypeData.push(item.comm_number)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

