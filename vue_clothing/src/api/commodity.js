import request from '@/api/request'

export default{
    // 获取所有货品
    getAllCommodityInfo(data){
        return request({
            url: '/commodity/commodityPage',
            method: 'post',
            data
        })
    },

    // 新增货品
    addCommodity(data){
        return request({
            url: '/commodity',
            method: 'post',
            data
        })
    },

    //删除货品
    deleteCommodity(data){
        return request({
            url: '/commodity/'+data.id,
            method: 'delete',
        })
    },

    //编辑货品
    editCommodity(data){
        return request({
            url: '/commodity',
            method: 'put',
            data
        })
    },

    //根据id获取货品详情
    getCommodityById(data){
        return request({
            url: '/commodity/'+data,
            method: 'get',
        })
    }



    
}




