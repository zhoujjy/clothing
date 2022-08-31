import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'



export const constantRoutes = [
  {
    path: '/login',
    name:'login',
    component: () => import('@/views/Login/index'),//路由懒加载
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/login',
  // },

  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children:[
      {
        path: 'home',
        component: () => import('@/views/Home/index'),
        meta: { title: '首页', icon: 'el-icon-house' },
      }
    ]
    
  },


  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    meta: { title: '系统设置', icon: 'el-icon-menu' },
    children:[
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/System/User'),
        meta: { title: '用户管理', icon: 'user' },
      },
      {
        path: 'commodity',
        name: 'commodity',
        component: () => import('@/views/System/Commodity'),
        meta: { title: '货号管理', icon: 'el-icon-goods' }
      }
    ]
  },

  {
    path: '/repertory',
    component: Layout,
    redirect: '/repertory/inbound',
    meta: { title: '仓库管理', icon: 'el-icon-menu' },
    children:[
      {
        path: 'inbound',
        name: 'inbound',
        component: () => import('@/views/Repertory/Inbound'),
        meta: { title: '入库管理', icon: 'el-icon-goods' }
      },
      {
        path: 'outbound',
        name: 'outbound',
        component: () => import('@/views/Repertory/Outbound'),
        meta: { title: '出库管理', icon: 'el-icon-goods' }
      },
      {
        path:'inboundDetail',
        name:'inboundDetail',
        hidden: true,
        component: () => import('@/views/Repertory/Inbound/Detail'),
        meta: { title: '入库详情', icon: 'el-icon-goods' }
      },
      {
        path:'outboundDetail',
        name:'outboundDetail',
        hidden: true,
        component: () => import('@/views/Repertory/Outbound/Detail'),
        meta: { title: '出库详情', icon: 'el-icon-goods' }
      }
    ]
  },

  {
    path:'/other',
    component:Layout,
    redirect:'/other/records',
    //meta:{title:'辅助管理',icon:'el-icon-menu'},
    children:[
      {
        path: 'records',
        name: 'records',
        component: () => import('@/views/Records/index'),
        meta: { title: '操作记录', icon: 'el-icon-document' }
      }
    ]
  },
  // el-icon-truck



  
  //breadcrumb : false 不显示面包屑
  //hidden: true, 隐藏


  { 
    path: '*', 
    redirect: '/404',
    hidden: true 
  }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher 
}

export default router
