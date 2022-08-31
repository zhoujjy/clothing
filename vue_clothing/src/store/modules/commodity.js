import commodityApi from '@/api/commodity'

const state = {

}

const actions = {

    // 获取所有货品
    async getAllCommodityInfo({ commit }, data) {
        let result = await commodityApi.getAllCommodityInfo(data)
        return Promise.resolve(result.data.data)
    },

    // 新增货品
    async addCommodity({ commit }, data) {
        let result = await commodityApi.addCommodity(data)
        if (result.data.code == 200) {
            return Promise.resolve(result.data.msg)
        }
        return Promise.reject(result.data.msg)
    },

    //删除货品
    async deleteCommodity({ commit }, data) {
        let result = await commodityApi.deleteCommodity(data)
        if (result.data.code == 200) {
            return Promise.resolve(result.data.msg)
        }
        return Promise.reject(result.data.msg)
    },

    //编辑货品
    async editCommodity({ commit }, data) {
        let result = await commodityApi.editCommodity(data)
        if (result.data.code == 200) {
            return Promise.resolve(result.data.msg)
        }
        return Promise.reject(result.data.msg)
    },

    //根据id获取货品详情
    async getCommodityById({ commit }, data) {
        let result = await commodityApi.getCommodityById(data)
        return Promise.resolve(result.data.data)
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

