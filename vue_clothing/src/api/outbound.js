import request from '@/api/request'

export default{

    //获取出库单列表
    getAllOutboundInfo(data){
        return request({
            url:'/outbound/page',
            method:'post',
            data
        })
    },

    //查询明细
    queryDetail(data){
        return request({
            url:'/outboundDetail/'+data,
            method:'get',
        })
    },

    //新增出库单
    addOutbound(data){
        return request({
            url:'/outbound',
            method:'post',
            data
        })
    },

    //修改出库单
    updateOutbound(data){
        return request({
            url:'/outbound',
            method:'put',
            data
        })
    },

    //删除出库单
    deleteOutboundInfo(data){
        return request({
            url:'/outbound/'+data,
            method:'delete',
        })
    },


    //新增出库单明细
    addOutboundDetail(data){
        return request({
            url:'/outboundDetail',
            method:'post',
            data
        })
    },

    //修改出库单明细
    updateOutboundDetail(data){
        return request({
            url:'/outboundDetail',
            method:'put',
            data
        })
    },

    //删除出库单明细
    deleteDetail(data){
        return request({
            url:'/outboundDetail',
            method:'delete',
            data
        })
    }

    
}




