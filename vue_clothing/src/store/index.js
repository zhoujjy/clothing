import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import commodity from './modules/commodity'
import inbound from './modules/inbound'
import record from './modules/record'
import outbound from './modules/outbound'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    commodity,
    inbound,
    record,
    outbound
  },
  getters
})

export default store
