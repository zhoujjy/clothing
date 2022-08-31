import userApi from '@/api/user'
import tokenUtils from '@/utils/tokenUtils'

const state = {
  userRole: '',
  userName:'',
  userLogin:''
}

const actions = {

  // 登录
  async login({ commit }, userInfo) {
    const { username, password } = userInfo;
    let result = await userApi.login({ userLogin: username, userPassword: password });

    if(result.data.code==200){
      tokenUtils.setToken(result.data.data.token)
      return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //注销
  async logout({ commit }) {
    let result = await userApi.logout()
    tokenUtils.removeToken()
    return Promise.resolve(result.data.msg)
  },

  // 获取用户信息
  async getUserInfo({ commit }) {
    let result = await userApi.getUserInfo()
    commit('SETUSERROLE', result.data.data)
  },

  // 获取所有用户信息
  async getAllUserInfo({ commit },data) {
    let result = await userApi.getAllUserInfo(data)
    return Promise.resolve(result.data.data)
  },

  //添加用户
  async addUser({ commit }, data) {
    let result = await userApi.addUser(data)
    if(result.data.code==200){
      return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //删除用户
  async deleteUser({ commit }, data) {
    let result = await userApi.deleteUser(data)
    if(result.data.code==200){
      return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //编辑用户
  async editUser({ commit }, data) {
    let result = await userApi.editUser(data)
    if(result.data.code==200){
      return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //获取用户名字
  async getUserName({ commit }, data) {
    let result = await userApi.getUserName(data)
    return Promise.resolve(result.data.data)
  },
  //获得在线用户
  async getOnlineUser({ commit }) {
    let result = await userApi.getOnlineUser()
    return Promise.resolve(result.data.data)
  },

  //强制退出
  async kickOut({ commit },id) {
    let result = await userApi.kickOut(id)
    return Promise.resolve(result.data.msg)
  }



}

const mutations = {
  SETUSERROLE(state, data) {
    state.userRole = data.userRole
    state.userName = data.userName
    state.userLogin = data.userLogin
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

