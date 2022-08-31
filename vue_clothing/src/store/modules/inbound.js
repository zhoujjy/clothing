import inboundApi from '@/api/inbound'

const state = {

}

const actions = {

    // 获取入库单列表
    async getAllInboundInfo({ commit }, data) {
        let result = await inboundApi.getAllInboundInfo(data)
        return Promise.resolve(result.data.data)
    },

    //查询明细
    async queryDetail({ commit }, data) {
        let result = await inboundApi.queryDetail(data)
        if(result.data.code === 200){
            return Promise.resolve(result.data.data)
        }
        return Promise.reject(result.data.msg)
    },

    //新增入库单
    async addInbound({ commit }, data) {
        let result = await inboundApi.addInbound(data)
        return Promise.resolve(result.data.data)
    },

    //删除入库单
    async deleteInboundInfo({ commit }, data) {
        let result = await inboundApi.deleteInboundInfo(data)
        if(result.data.code === 200){
            return Promise.resolve(result.data.msg)
        }
        return Promise.reject(result.data.msg)
    },

    //修改入库单
    async updateInbound({ commit }, data) {
        let result = await inboundApi.updateInbound(data)
        if(result.data.code === 200){
            return Promise.resolve(result.data.msg)
        }
        return Promise.reject(result.data.msg)
    },




    //新增入库单明细
    async addInboundDetail({ commit }, data) {
        let result = await inboundApi.addInboundDetail(data)
        if(result.data.code === 200){
            return Promise.resolve(result.data.msg)
        }
        return Promise.reject(result.data.msg)
    },

    //修改入库明细
    async updateInboundDetail({ commit }, data) {
        let result = await inboundApi.updateInboundDetail(data)
        if(result.data.code === 200){
            return Promise.resolve(result.data.msg)
        }
        return Promise.reject(result.data.msg)
    },

    //删除入库明细
    async deleteDetail({ commit }, data) {
        let result =await inboundApi.deleteDetail(data)
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

