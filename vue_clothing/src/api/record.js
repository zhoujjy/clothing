import request from '@/api/request'

export default{
    // 获取入库记录列表
    getRecordList(data){
        return request({
            url:'/record',
            method:'post',
            data
        })
    },

    //获得统计数据
    getStatistics(){
        return request({
            url:'/record/statistics',
            method:'get'
        })
    }




    
}




