<template>
<!-- 居中显示 -->
    <div  style="margin-top: 30px;margin-left: 80px;">
        <div style="display: flex;">
      <div ref="chartDom"style="width: 600px; height: 400px;"></div>
      <div ref="businessTrends" style="width: 700px; height: 400px;padding-left: 90px;"></div>
    </div>
  
    <div style="display: flex;">
      <div ref="salesRanking" style="width: 600px; height: 400px;"></div>
      <div ref="projectSigningOverview" style="width: 700px; height: 400px; padding-left: 30px;"></div>
    </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, onUnmounted, nextTick } from 'vue';
  import * as echarts from 'echarts';
  import api from '../api/index.js'
  
  // 创建一个响应式引用来保存DOM元素
  const chartDom = ref(null);
  const businessTrends = ref(null);
  const salesRanking = ref(null);
  const projectSigningOverview = ref(null);
  

  const listD = ref([])
  const listDay = ref([])

  // 绘制第一个图表：某类型的销量
  const drawSalesChart = () => {
    const chartElement = echarts.init(chartDom.value);
    const option = {
      title: {
        text: '近七天销售量(千)',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: listDay.value
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: listD.value,
        type: 'bar',
        barWidth: '45%',
      }]
    };
    chartElement.setOption(option);
  };
  
  const listC = ref()

  // 绘制第二个图表：品牌的每月营销额
  const drawMonthlySalesChart = () => {
    const chartElement = echarts.init(businessTrends.value);
    const option = {
      title: {
        text: '季度营销额(万)',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: ['春季', '夏季', '秋季',  '冬季']
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: listC.value,
        type: 'line',
        smooth: true
      }]
    };
    chartElement.setOption(option);
  };
  

  // 品牌列表
  const brandListB = ref([1,2,3])
  // 品牌销量列表
  const listB = ref([1,2,3])
  // 绘制第三个图表：营销前3类型
  const drawSalesRanking = () => {
    const chartElement = echarts.init(salesRanking.value);
    const option = {
      title: {
        text: '销量排行',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01],
        axisLine: { show: false },
        axisTick: { show: false },
        splitLine: { show: false }
      },
      yAxis: {
        type: 'category',
        data: brandListB.value,
        axisLine: { show: false },
        axisTick: { show: false },
        splitLine: { show: false }
      },
      series: [{
        name: '销量',
        type: 'bar',
        data: listB.value,
        barWidth: '25%',
        itemStyle: {
          borderRadius: [0, 5, 5, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: '#bdd1f6' },
            { offset: 1, color: '#4282ff' }
          ])
        },
        label: {
          show: true,
          position: 'right',
          formatter: '{c}万'
        }
      }]
    };
    chartElement.setOption(option);
  };

  // 总的汽车数量
  const listA = ref([])
  
  // 绘制第四个图表：目标量(完成)
  const drawProjectSigningOverview = () => {
    const chartElement = echarts.init(projectSigningOverview.value);
    const option = {
      title: {
        text: '车辆状态分布',
        left: 'center'
      },
      legend: {
        orient: 'vertical',
        right: 10,
        align: 'left',
        top: 'center',
        textStyle: {
          rich: {
            name: { color: '#929292' },
            value: { color: '#000', fontWeight: 'bold', fontSize: 16 }
          }
        },
        data: [
          { name: '可用 {value|' + listA.value[0] +'}{name| 个/'+ listA.value[3] +'辆}', textStyle: { rich: { value: {}, name: {} } } },
          { name: '已租 {value|' + listA.value[1] +'}{name| 个/'+ listA.value[3] +'辆}', textStyle: { rich: { value: {}, name: {} } } },
          { name: '维修中 {value|' + listA.value[2] +'}{name| 个/'+ listA.value[3] +'辆}', textStyle: { rich: { value: {}, name: {} } } }
        ],
        icon: 'circle'
      },
      series: [{
        name: 'Access From',
        type: 'pie',
        radius: ['50%', '70%'],
        center: ['22%', '50%'],
        avoidLabelOverlap: false,
        label: { show: false, position: 'center' },
        emphasis: { label: { show: false } },
        labelLine: { show: false },
        data: [
          // 
          { value: listA.value[0], name: '可用 {value|' + listA.value[0] +'}{name| 个/'+ listA.value[3] +'辆}' },
          { value: listA.value[1], name: '已租 {value|' + listA.value[1] +'}{name| 个/'+ listA.value[3] +'辆}' },
          { value: listA.value[2], name: '维修中 {value|' + listA.value[2] +'}{name| 个/'+ listA.value[3] +'辆}' },
        ],
        itemStyle: {
          borderRadius: 0,
          borderColor: '#fff',
          borderWidth: 0
        }
      }], 
    };
    chartElement.setOption(option);
  };


  // 发送请求，查询第四个图表
  const sendDrawProjectSigningOverview = async () => {
      let result =await api.get("/api/reportforms")

      listA.value = result.data;

      drawProjectSigningOverview();
  }
  // 发送请求，查询第三个图表
  const sendDrawSalesChart = async () => {

    let result =await api.get("/api/reportforms/salesChart")

    brandListB.value = result.data.brands;
    listB.value = result.data.counts;
    drawSalesRanking();
  }
  // 发送请求，查询第二个图表
  const sendDrawMonthlySalesChart = async () => {
    let result =await api.get("/api/reportforms/monthlySalesChart")
    listC.value = result.data;
    drawMonthlySalesChart();
  }
  // 发送请求，查询第一个图表
  const sendDrawOneSalesChart = async () => {
    let result =await api.get("/api/reportforms/oneSalesChart")

    console.log(result.data)

    console.log("=======xx=======")

    listDay.value = result.data.days;
    listD.value = result.data.count;
    drawSalesChart();
  }

  // 初始化ECharts实例并设置配置项
  onMounted(async() => {
    
    await nextTick(); // 确保DOM已经渲染完成

    // 请求一
    sendDrawProjectSigningOverview();
    // 请求二
    sendDrawSalesChart();
    // 请求三
    sendDrawMonthlySalesChart();
    // 请求四
    sendDrawOneSalesChart();
  });
  
  // 销毁ECharts实例
  // onUnmounted(() => {
  //   if (chartInstance != null && chartInstance.dispose) {
  //     chartInstance.dispose();
  //   }
  // });
  </script>
  
  <style scoped>
  /* 添加一些CSS样式来美化图表容器（可选） */
  div[ref] {
    border: 1px solid #ccc;
    border-radius: 8px;
    margin: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  </style>
  