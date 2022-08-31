import outboundApi from '@/api/outbound'

const state = {

}

const actions = {

  // 获取出库单列表
  async getAllOutboundInfo ({ commit }, data) {
    let result =await outboundApi.getAllOutboundInfo(data);
    return Promise.resolve(result.data.data);
  },

  //查询明细
  async queryDetail({ commit }, data) {
    let result = await outboundApi.queryDetail(data)
    if(result.data.code === 200){
        return Promise.resolve(result.data.data)
    }
    return Promise.reject(result.data.msg)
  },

  //新增出库单
  async addOutbound({ commit }, data) {
    let result = await outboundApi.addOutbound(data)
    return Promise.resolve(result.data.data)
  },

  //修改出库单
  async updateOutbound({ commit }, data) {
    let result = await outboundApi.updateOutbound(data)
    if(result.data.code === 200){
        return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //删除入库单
  async deleteOutboundInfo({ commit }, data) {
    let result = await outboundApi.deleteOutboundInfo(data)
    if(result.data.code === 200){
        return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },



  //新增入库单明细
  async addOutboundDetail({ commit }, data) {
    let result = await outboundApi.addOutboundDetail(data)
    if(result.data.code === 200){
        return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //修改入库明细
  async updateOutboundDetail({ commit }, data) {
    let result = await outboundApi.updateOutboundDetail(data)
    if(result.data.code === 200){
        return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //删除入库明细
  async deleteDetail({ commit }, data) {
    let result =await outboundApi.deleteDetail(data)
    if(result.data.code === 200){
        return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  }

    



}

const mutations = {

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

