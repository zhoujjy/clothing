<template>
  <div class="charts" ref="charts">

  </div>
</template>

<script>
//引入echarts
import * as echarts from 'echarts';
export default {
    name:'',
    data(){
        return{
            chart:null,
        }
    },
    mounted(){
            
        this.draw() 

    },
    watch:{
        '$store.state.record.inboundChange':{
            deep:true,
            handler(val){
                this.draw()
            }
        },
    },
    methods:{
        draw(){         
            if (this.chart != null && this.chart != "" && this.chart != undefined){
　　            this.chart.dispose();
            }
            //初始化echarts实例
            this.chart = echarts.init(this.$refs.charts);
            //配置数据
            this.chart.setOption({
                xAxis:{
                    show:false,//隐藏x轴
                    type:'category',//折线
                },
                yAxis:{
                    show:false,//隐藏x轴
                },
                //系列
                series:[
                    {
                        type:'line', //折线图
                        // data:[2,1,5,3,5,8,6,8,4,8,9],
                        data:this.$store.state.record.inboundChange,
                        //拐点样式设置
                        itemStyle:{
                            opacity:0
                        },
                        //线条的样式
                        lineStyle:{
                            color:"purple"
                        },
                        //填充颜色设置
                        areaStyle:{
                            color:{
                                type:"linear",
                                x:0,
                                y:0,
                                x2:0,
                                y2:1,
                                colorStops:[
                                    {
                                        offset:0,
                                        color:"purple",
                                    },
                                    {
                                        offset:1,
                                        color:"#fff"
                                    }
                                ]
                            }
                        }
                    }
                ],
                //布局调试
                grid:{
                    left:0,
                    top:0,
                    right:0,
                    bottom:0
                }
            })
        }
    }
}
</script>

<style scoped>
.charts{
    width: 100%;
    height: 100%;
}
</style>