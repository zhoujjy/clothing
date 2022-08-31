import request from '@/api/request'

export default{
    // 登录
    login(userInfo) {
        return request({
            url: '/user/login',
            method: 'post',
            data: userInfo
        })
    },

    //注销
    logout() {
        return request({
            url: '/user/logout',
            method: 'post'
        })
    },

    // 获取用户信息
    getUserInfo() {
        return request({
            url: '/user',
            method: 'get'
        })
    },

    // 获取所有用户信息
    getAllUserInfo(data) {
        return request({
            url: '/user/userPage',
            method: 'post',
            data
        })
    },

    //添加用户
    addUser(data) {
        return request({
            url: '/user/addUser',
            method: 'post',
            data
        })
    },

    //删除用户
    deleteUser(data) {
        return request({
            url: '/user',
            method: 'delete',
            data
        })
    },

    //编辑用户
    editUser(data) {
        return request({
            url: '/user',
            method: 'put',
            data
        })
    },

    //获取用户姓名
    getUserName(data) {
        return request({
            url: '/user/getUserName/'+data,
            method: 'get',
            data
        })
    },

    //获得在线用户
    getOnlineUser() {
        return request({
            url: '/user/onlineUser',
            method: 'get'
        })
    },

    //强制退出
    kickOut(id) {
        return request({
            url: '/user/kickOut/'+id,
            method: 'delete'
        })
    }



    
}




