<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          <h1>😎创鑫仓储管理系统😎</h1>
          <p>你说对，但原神，米自研，冒险游，提瓦特，神选中，授神眼，引元素。扮角色，邂同伴，击强敌，找亲人，掘真相。</p>
        </blockquote>
        <hr/>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <!--        -->
        <h2>😎创鑫仓储管理系统😎</h2>
        <pre class="cx_plant">
            ______  __  __        ____  __ ____
           / ___\ \/ /  \ \      / /  \/  / ___|
          | |    \  /____\ \ /\ / /| |\/| \___ \
          | |___ /  \_____\ V  V / | |  | |___) |
           \____/_/\_\     \_/\_/  |_|  |_|____/
        </pre>

        <p>
          基于若依框架二次开发的微服务架构仓储WMS
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>
<!--        <p>-->
<!--          <el-tag type="danger">感谢若依开源</el-tag>-->
<!--        </p>-->
<!--        <p>-->
<!--          <el-button type="primary" size="mini" icon="el-icon-cloudy" plain-->
<!--                     @click="goTarget('https://gitee.com/y_project/RuoYi-Cloud')">访问👉🏻若依码云-->
<!--          </el-button>-->
<!--          <el-button size="mini" icon="el-icon-s-home" plain @click="goTarget('http://ruoyi.vip')">访问若依👍🏻主页-->
<!--          </el-button>-->
<!--        </p>-->
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术框架</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端框架</h4>
            <ul>
              <li>SpringBoot</li>
              <li>SpringCloud</li>
              <li>Nacos</li>
              <li>Sentinel</li>
              <li>Seata</li>
              <li>Minio</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端框架</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Echarts</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider/>
    <el-row :gutter="30">
      <el-col :xs="30" :sm="25" :md="100" :lg="10">
        <el-card class="update-log">
          <div id="main0" style="width: 500px;height:400px;">
            main0
          </div>
        </el-card>
      </el-col>
      <el-col :xs="30" :sm="25" :md="100" :lg="10">
        <el-card class="update-log">
          <div id="main1" style="width: 500px;height:400px;">
            main1
          </div>
        </el-card>
      </el-col>
      <el-col :xs="30" :sm="25" :md="100" :lg="10">
        <el-card class="update-log">
          <div id="main2" style="width: 500px;height:400px;">
            main2
          </div>
        </el-card>
      </el-col>
      <el-col :xs="30" :sm="25" :md="100" :lg="10">
        <el-card class="update-log">
          <div id="main3" style="width: 500px;height:400px;">
            main3
          </div>
        </el-card>
      </el-col>
    </el-row>

<!--    <el-row :gutter="30">-->
<!--      <el-card style="width: 1315px; height: 400px; position: relative; left: 15px">-->
<!--        <el-card v-for="sv in WareHouseStockValue" :key="sv.warehouseId" shadow="hover">-->
<!--          <div>-->
<!--&lt;!&ndash;            库存价值 : {{ sv.allValue }}&ndash;&gt;-->
<!--&lt;!&ndash;            库存数量 : {{ sv.allCount }}&ndash;&gt;-->
<!--            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ warehouseName[sv.warehouseId - 1] }}</p>-->
<!--            <el-col :span="4">-->
<!--                <div>-->
<!--                  <p style="color: #00afff;font-size: 35px;line-height: 1px"> 库存价值 : {{ sv.allValue }}</p>-->
<!--                  <p style="color: #00afff;font-size: 35px;line-height: 1px"> 库存数量 : {{ sv.allCount }}</p>-->
<!--                </div>-->
<!--            </el-col>-->
<!--            </div>-->
<!--        </el-card>-->
<!--      </el-card>-->
<!--    </el-row>-->


    <el-row :gutter="30">
      <el-col :xs="30" :sm="25" :md="100" :lg="10">
        <el-card class="update-log">
          <span style="font-size : 18px;font-weight: bold;display: block">各库货品金额占比</span>
          <span style="font-size : 18px;font-weight: bold;display: block">库存货品总金额:{{ allValue }}</span>
          <div id="bin0" style="width: 500px;height:400px;">
            bin0
          </div>
        </el-card>
      </el-col>
      <el-col :xs="30" :sm="25" :md="100" :lg="10">
        <el-card class="update-log">
          <span style="font-size : 18px;font-weight: bold;display: block">各库货品数量占比</span>
          <span style="font-size : 18px;font-weight: bold;display: block">库存货品数量:{{ allCount }}</span>
          <div id="bin1" style="width: 500px;height:400px;">
            bin1
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listStockValue} from "@/api/stockvalue/stockvalue";
import * as echarts from 'echarts';
import {findCountValue} from "@/api/checkin/checkin";

export default {
  name: "Index",
  data() {
    return {
      version: "1.1.4.514 下北泽特別版", //1.1.4.514 獣の先祖特別版
      KcDetail: [],
      groupedData: [],
      chartIds: ['main0', 'main1', 'main2', 'main3'],
      WareHouseStockValue: [],
      allValue: 0,
      allCount: 0,
      wId: [],
      warehouseName: [],

    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    listStockValue() {
      listStockValue().then(response => {
        this.KcDetail = response.data;
        // 提取 svw_id 到 wId 数组
        this.wId = [...new Set(response.data.map(item => item.svw_id))];
        this.groupData(this.KcDetail);
        this.$nextTick(() => {
          this.getchart();
        });
      });
    },
    groupData(data) {
      const groupedByWarehouse = this.groupBy(data, 'svw_id');
      this.groupedData = Object.values(groupedByWarehouse).map((warehouseData) => {
        //const locations = this.groupBy(warehouseData, 'svsl_id');
        return {warehouseName: warehouseData[0].svw_name, warehouseData};
      });
    },
    groupBy(data, key) {
      return data.reduce((acc, item) => {
        const groupKey = item[key];
        acc[groupKey] = acc[groupKey] || [];
        acc[groupKey].push(item);
        return acc;
      }, {});
    },
    getChartOption(warehouseName, xAxisData, seriesData) {
      return {
        title: {
          text: warehouseName + '库存情况'
        },
        tooltip: {},
        legend: {
          data: ['库存量']
        },
        xAxis: {
          data: xAxisData
        },
        yAxis: {},
        series: [
          {
            name: '库存量',
            type: 'bar',
            data: seriesData
          }
        ]
      };
    },
    initChart(id, option) {
      const chart = echarts.init(document.getElementById(id));
      chart.setOption(option);
    },
    getchart() {
      for (let i = 0; i < this.groupedData.length; i++) {
        let warehouseData = this.groupedData[i].warehouseData;
        // 使用 map 提取每个仓库库位的 svg_name
        let xAxisData = warehouseData.map(item => item.svg_name);
        // 查询 每个仓库库位库存数量
        let seriesData = warehouseData.map(item => item.svitem_quantity);
        //console.info(xAxisData);
        this.warehouseName.push(this.groupedData[i].warehouseName);
        let option = this.getChartOption(this.groupedData[i].warehouseName, xAxisData, seriesData);
        this.initChart(this.chartIds[i], option);
      }
    },
    createBin() {
      const option = {
        graphic: [
          {
            type: 'text',
            left: 'center',
            top: 'center',
            style: {
              text: '各仓库货品总金额占比',
              font: '14px Arial',
              fill: '#333' // 文本颜色
            }
          }
        ],
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.warehouseName
        },
        series: [
          {
            type: 'pie',
            radius: ['50%', '65%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            labelLine: {
              show: false
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '30',
                fontWeight: 'bold'
              }
            },
            data: [
              {value: this.WareHouseStockValue[0].allValue, name: this.warehouseName[0]},
              {value: this.WareHouseStockValue[1].allValue, name: this.warehouseName[1]},
              {value: this.WareHouseStockValue[2].allValue, name: this.warehouseName[2]},
              {value: this.WareHouseStockValue[3].allValue, name: this.warehouseName[3]}
            ]
          }
        ]
      };
      const option2 = {
        graphic: [
          {
            type: 'text',
            left: 'center',
            top: 'center',
            style: {
              text: '各仓库货品数量占比',
              font: '14px Arial',
              fill: '#333' // 文本颜色
            }
          }
        ],
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.warehouseName
        },
        series: [
          {
            type: 'pie',
            radius: ['50%', '65%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            labelLine: {
              show: false
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '30',
                fontWeight: 'bold'
              }
            },
            data: [
              {value: this.WareHouseStockValue[0].allCount, name: this.warehouseName[0]},
              {value: this.WareHouseStockValue[1].allCount, name: this.warehouseName[1]},
              {value: this.WareHouseStockValue[2].allCount, name: this.warehouseName[2]},
              {value: this.WareHouseStockValue[3].allCount, name: this.warehouseName[3]}
            ]
          }
        ]
      };
      this.initChart("bin0", option)
      this.initChart("bin1", option2)
    },

    async getCountValue() {
      try {
        // 先调用一次无参数的 findCountValue 方法
        const initialResponse = await findCountValue(null);
        this.allCount = initialResponse.data.allCount;
        this.allValue = initialResponse.data.allValue;
        // 循环 wId 数组，以数组中的每个元素作为参数调用 findCountValue 方法
        for (let i = 0; i < this.wId.length; i++) {
          const currentWId = this.wId[i];
          const response = await findCountValue(currentWId);
          this.WareHouseStockValue.push({
            warehouseId: currentWId,
            allValue: response.data.allValue,
            allCount: response.data.allCount
          });
        }
        // 在这里执行渲染图表的逻辑
        this.renderChartLogic();
      } catch (error) {
        console.error('Error:', error);
      }
    },

    renderChartLogic() {
      if (this.WareHouseStockValue[0].allValue != null) {
        this.createBin();
      }
    }

  },

  mounted() {
    this.listStockValue();
    this.getchart();
    this.getCountValue();
  }
};
</script>


<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans",
  "Helvetica Neue",
  Helvetica,
  Arial,
  sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }

  .cx_plant {
    color: #00afff;
    font-size: 18px;
    font-weight: bold;
    font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, Courier, monospace
  }
}
</style>
<!-- Json  -->
