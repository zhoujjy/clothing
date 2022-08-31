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
            stockNumByTypeName:[],
            stockNumByTypeData:[],
            stockNumByTypeDataMax:0,
            chart:null,
        }
    },
    mounted(){
        this.draw() 
             
    },
    watch:{
        '$store.state.record.stockNumByTypeData':{
            deep:true,
            handler(val){
                console.log(val);
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
            this.chart.setOption({
            title: [
                {
                text: '货品及数量'
                }
            ],
            polar: {
                radius: [30, '90%']
            },
            radiusAxis: {
                max: this.$store.state.record.stockNumByTypeDataMax+10,
            },
            angleAxis: {
                type: 'category',
                // data: ['a', 'b', 'c', 'd'],
                data: this.$store.state.record.stockNumByTypeName,
                startAngle: 75
            },
            tooltip: {},
            series: {
                type: 'bar',
                // data: [2, 1.2, 2.4, 3.6],
                data:this.$store.state.record.stockNumByTypeData,
                coordinateSystem: 'polar',
                label: {
                show: true,
                position: 'middle',
                // formatter: '{b}: {c}'
                }
            },
            backgroundColor: '#fff',
            animation: false
            });
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