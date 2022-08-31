import router from './index'
import tokenUtils from '@/utils/tokenUtils'
import store from '../store/index'
import { Message } from 'element-ui'
//路由守卫
router.beforeEach((to, from, next) => {
  if(to.fullPath=='/home'&&from.fullPath=='/'){
    if(tokenUtils.getToken()==null){
      next('/login')
    }
  }
  next()
})


