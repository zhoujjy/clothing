import request from '@/api/request'

export default{

    getAllInboundInfo(data){
        return request({
            url: '/inbound/inboundPage',
            method: 'post',
            data
        })
    },

    //查询明细
    queryDetail(data){
        return request({
            url: '/inboundDetail/'+data,
            method: 'get',
        })
    },

    //新增入库单
    addInbound(data){
        return request({
            url: '/inbound',
            method: 'post',
            data
        })
    },

    //删除入库单
    deleteInboundInfo(data){
        return request({
            url: '/inbound/'+data,
            method: 'delete',
        })
    },

    //修改入库单
    updateInbound(data){
        return request({
            url: '/inbound',
            method: 'put',
            data
        })
    },





    //新增入库单明细
    addInboundDetail(data){
        return request({
            url: '/inboundDetail',
            method: 'post',
            data
        })
    },

    //修改入库明细
    updateInboundDetail(data){
        return request({
            url: '/inboundDetail',
            method: 'put',
            data
        })
    },

    //删除入库明细
    deleteDetail(data){
        return request({
            url: '/inboundDetail',
            method: 'delete',
            data
        })
    }
        



    
}




