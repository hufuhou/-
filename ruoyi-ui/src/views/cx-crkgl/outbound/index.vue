<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="出库单号" prop="outCode">
        <el-input
          v-model="queryParams.outCode"
          placeholder="请输入出库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出库类型" prop="outType">
        <el-select v-model="queryParams.outType" placeholder="请选择出库类型" clearable>
          <el-option
            v-for="dict in dict.type.out_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="出库状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择出库状态" clearable>
          <el-option
            v-for="dict in dict.type.status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="出库仓库" prop="wId">
        <el-select v-model="queryParams.wId" placeholder="请选择仓库" clearable>
          <el-option
            v-for="dict in warehouseList"
            :key="dict.wId"
            :label="dict.wName"
            :value="dict.wId"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['cx-crkgl:outbound:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['cx-crkgl:outbound:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-receiving"
          size="mini"
          :disabled="examine"
          @click="handleExamine"
          v-hasPermi="['cx-crkgl:outbound:examine']"
        >审核</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-connection"-->
<!--          size="mini"-->
<!--          :disabled="revoke"-->
<!--          @click="handleRevoke"-->
<!--          v-hasPermi="['cx-crkgl:outbound:revoke']"-->
<!--        >撤销</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['cx-crkgl:outbound:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cx-crkgl:outbound:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outboundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="出库单号" align="center" prop="outCode" width="185"/>
      <el-table-column label="出库类型" align="center" prop="outType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.out_type" :value="scope.row.outType"/>
        </template>
      </el-table-column>
      <el-table-column label="出库状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.status" :value="scope.row.status" :class="getStatusClass(scope.row.status)"/>
        </template>
      </el-table-column>
      <el-table-column label="出库日期" align="center" prop="outDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="关联订单" align="center" prop="orderId" width="160"/>
      <el-table-column label="客户" align="center" prop="cId" >
        <template slot-scope="scope">
          <span v-for="item in consumerList">
            <template v-if="parseInt(scope.row.cId)===item.cId">
              {{ item.cName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="出库货品" align="center" width="200px">
      <template slot-scope="scope">
    <span v-if="scope.row.childItems && scope.row.childItems.length">
      {{ scope.row.childItems.map(item => item.goodsName).join(',') }}
    </span>
        <span v-else>-</span> <!-- 无子项时显示占位符或空字符串 -->
      </template>
    </el-table-column>
      <el-table-column label="出库数量" align="center" prop="totalCksl">
        <template slot-scope="scope">
          {{ scope.row.totalCksl }}
        </template>
      </el-table-column>
      <el-table-column label="出库金额" align="center" prop="totalMoney">
        <template slot-scope="scope">
          {{ scope.row.totalMoney }}
        </template>
      </el-table-column>
      <el-table-column label="出库仓库" align="center" prop="wId">
        <template slot-scope="scope">
          <span v-for="item in warehouseList">
            <template v-if="scope.row.wId===item.wId">
              {{ item.wName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="personId" />
      <el-table-column label="联系方式" align="center" prop="contactDetails" width="120"/>
      <el-table-column label="经办人" align="center" prop="outManager" >
        <template slot-scope="scope">
          <span v-for="item in userList">
            <template v-if="parseInt(scope.row.outManager)===item.userId">
              {{ item.nickName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="部门" align="center" prop="outManager" width="100px">
        <template slot-scope="scope">
          <span v-for="item in deptList">
            <template v-if="scope.row.outManager===item.deptId">
              {{ item.deptName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="审核人" align="center" prop="reviewer" />
      <el-table-column label="审核时间" align="center" prop="reviewerTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewerTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-crkgl:outbound:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-crkgl:outbound:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加销售订单 -->
    <el-dialog :visible.sync="dialogMaterial1" title="销售订单" :modal="false">
      <el-table :data="saleList" v-loading="loading" @row-click="handleRowClickMaterial1">
        <el-table-column label="销售单号" align="center" prop="sCode" width="180ox"/>
        <el-table-column label="客户" align="center" prop="cId">
          <template slot-scope="scope">
        <span v-for="item in consumerList"  :key="item.cId">
          <template v-if="scope.row.cId===item.cId">
            {{ item.cName }}
          </template>
        </span>
          </template>
        </el-table-column>
        <el-table-column label="联系人" align="center" prop="contactPerson">
          <template slot-scope="scope">
        <span v-for="item in consumerList" :key="item.cId">
          <template v-if="scope.row.cId===item.cId">
            {{ item.contactPerson }}
          </template>
        </span>
          </template>
        </el-table-column>
        <el-table-column label="联系方式" align="center" prop="contactNumber" width="120">
          <template slot-scope="scope">
        <span v-for="item in consumerList" >
          <template v-if="scope.row.cId===item.cId">
            {{ item.contactNumber }}
          </template>
        </span>
          </template>
        </el-table-column>
        <el-table-column label="销售日期" align="center" prop="saleDate" />
        <el-table-column label="销售负责人" align="center" prop="principalId">
          <template slot-scope="scope">
        <span v-for="item in userList" :key="item.userId">
          <template v-if="scope.row.principalId===item.userId">
            {{ item.nickName }}
          </template>
        </span>
          </template>
        </el-table-column>
        <el-table-column label="销售部门" align="center" prop="deptId">
          <template slot-scope="scope">
        <span v-for="item in deptList" :key="item.deptId">
          <template v-if="scope.row.deptId===item.deptId">
            {{ item.deptName }}
          </template>
        </span>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
                  :limit.sync="caqueryParams.pageSize" @pagination="getXSList" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogMaterial1 = false">取消</el-button>
      </div>
    </el-dialog>

    <!-- 添加销售明细 -->
    <el-dialog :visible.sync="dialogMaterial" :title="'销售订单 - ' + form.orderId" :modal="false">
      <el-form :model="queryParams1" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="货品名称" prop="gName">
          <el-input
            v-model="queryParams1.gName"
            placeholder="请输入货品名称"
            clearable
            @keyup.enter.native="handleQuery1"
          />
        </el-form-item>
        <el-form-item label="单位" prop="gUnit">
          <el-select v-model="queryParams1.gUnit" placeholder="请选择单位" clearable>
            <el-option
              v-for="dict in dict.type.g_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery1">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table ref="multipleTable" :data="salesDetail" v-loading="loading" @selection-change="handleSelectionChange1">
        <el-table-column type="selection" align="center" width="50px">
        </el-table-column>
        <el-table-column label="货品编号" align="center" prop="gCode" width="200px"/>
        <el-table-column label="货品名称" align="center" prop="gName" width="140px"/>
        <el-table-column label="规格型号" align="center" prop="specCode" />
        <el-table-column label="单位" align="center" prop="gUnit" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
          </template>
        </el-table-column>
        <el-table-column label="销售单价" align="center" prop="dj">
          <template slot-scope="scope">
            {{ calculatedUnitPrice[scope.$index] }}
          </template>
        </el-table-column>
        <el-table-column label="销售数量" align="center" prop="salesVolume" />
        <el-table-column label="销售金额" align="center" prop="salesAmount" />
        <el-table-column label="已出库" align="center" prop="cksl" width="100px">
        </el-table-column>
        <el-table-column label="未出库" align="center" prop="wcksl" width="100px">
          <!-- 使用插槽来自定义单元格内容 -->
          <template slot-scope="{ row }">
            {{ row.wcksl }}
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="totals > 0" :total="totals" :page.sync="caqueryParammx.pageNum"
                  :limit.sync="caqueryParammx.pageSize" @pagination="getList1" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleRowClickMaterial">确定</el-button>
        <el-button @click="dialogMaterial = false">取消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改出库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="10">
          <el-col :span="12">
        <el-form-item label="出库单号" prop="outCode">
          <el-input v-model="form.outCode" placeholder="自动获取系统编码" :disabled="true" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="出库类型" prop="outType">
          <el-select v-model="form.outType" placeholder="请选择出库类型">
            <el-option
              v-for="dict in dict.type.out_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
        <el-form-item label="出库日期" prop="outDate">
          <el-date-picker clearable
            v-model="form.outDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出库日期">
          </el-date-picker>
        </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库" prop="wId">
              <el-select v-model="form.wId" placeholder="请选择入库仓库" @change="updateLocationList" clearable>
                <el-option
                  v-for="dict in warehouseList"
                  :key="dict.wId"
                  :label="dict.wName"
                  :value="dict.wId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="关联单号" prop="orderId">
          <div class="input-container">
            <el-input v-model="form.orderId" placeholder="请选择" v-if="form.iS" class="input-field" :disabled="true">
              <template #append v-if="form.iS">
                <span class="clickable-icon" @click="showMaterial1">...</span>
              </template>
            </el-input>
            <el-input v-model="form.orderId" placeholder="请输入单号" v-else class="input-field" />
          </div>
          <el-checkbox v-model="form.iS">是否关联销售订单</el-checkbox>
        </el-form-item>

        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="客户" prop="cId" v-if="form.iS">
              <el-input :value="getConsumerName(form.cId)" placeholder="请输入" :disabled="true"></el-input>
            </el-form-item>

            <el-form-item label="客户" prop="cId" v-else>
              <el-select v-model="form.cId" placeholder="请选择客户" clearable @change="consumerChange">
                <el-option
                  v-for="dict in consumerList"
                  :key="dict.cId"
                  :label="dict.cName"
                  :value="dict.cId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收货人" prop="personId">
              <el-input v-model="form.personId" placeholder="请输入收货人" :disabled="true" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
        <el-form-item label="联系方式" prop="contactDetails">
          <el-input v-model="form.contactDetails" placeholder="请输入联系方式"  :disabled="true" readonly />
        </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="outManager">
              <el-select v-model="form.outManager" placeholder="请选择出库负责人" clearable>
                <el-option
                  v-for="dict in userList"
                  :key="dict.userId"
                  :label="dict.nickName"
                  :value="dict.userId"
                />
              </el-select>
          </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>

        <el-divider content-position="center">出库明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddCrkOutDetails">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteCrkOutDetails">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="crkOutDetailsList" :row-class-name="rowCrkOutDetailsIndex" @selection-change="handleCrkOutDetailsSelectionChange" ref="crkOutDetails">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="货品名称" align="center" prop="gName"  width="140px">
            <template slot-scope="scope">
              {{ scope.row.gName }}
            </template>
          </el-table-column>
          <el-table-column label="货品编号" align="center" prop="gCode" width="220px">
            <template slot-scope="scope">
              {{ scope.row.gCode }}
            </template>
          </el-table-column>
          <el-table-column label="规格型号" align="center" prop="specCode" >
            <template slot-scope="scope">
              {{ scope.row.specCode }}
            </template>
          </el-table-column>
          <el-table-column label="单位" align="center" prop="gUnit" >
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
            </template>
          </el-table-column>
          <el-table-column label="当前库存" align="center" prop="kcsl">
            <template slot-scope="scope">
              {{ scope.row.kcsl }}
            </template>
          </el-table-column>
          <el-table-column label="销售单价" align="center" prop="dj">
            <template slot-scope="scope">
              {{ scope.row.dj }}
            </template>
          </el-table-column>
          <el-table-column label="销售数量" align="center" prop="salesVolume" >
            <template slot-scope="scope">
              {{ scope.row.salesVolume }}
            </template>
          </el-table-column>
          <el-table-column label="销售金额" align="center" prop="money" >
            <template slot-scope="scope">
              {{ scope.row.money }}
            </template>
          </el-table-column>
          <el-table-column label="已出库数量" prop="outBound" width="90px" align="center">
            <template slot-scope="scope">
              {{ scope.row.outBound }}
            </template>
          </el-table-column>
          <el-table-column label="未出库数量" prop="notShipped" width="90px" align="center">
            <template slot-scope="scope">
              {{ scope.row.notShipped }}
            </template>
          </el-table-column>
          <el-table-column label="出库仓位" prop="slId" width="150" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.slId" placeholder="请选择出库仓位" @change="updateCurrentStock(scope.row)">
                <el-option
                  v-for="dict in locationList"
                  :key="dict.slId"
                  :label="dict.slName"
                  :value="dict.slId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="本次出库数量" prop="itemQuantity" width="150" align="center">
            <template slot-scope="scope">
              <el-input
                v-model.number="scope.row.itemQuantity"
                placeholder="请输入本次出库数量"
                type="number"
                :min="0"
                :max="scope.row.salesVolume"
              />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" width="150" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--审核对话框-->
    <el-dialog title="审核" :visible.sync="openAudit" append-to-body style="width: 500px; margin: auto"
               @close="closeAudit">
      <el-form :model="auditForm" ref="queryGoodsForm" size="small" :inline="true" v-show="showSearch"
               label-width="68px">
        <el-form-item label="审核结果" prop="auditResult">
          <el-col :span="24">
            <el-radio-group v-model="auditForm.auditResult">
              <el-radio label="true">通过</el-radio>
              <el-radio label="false">驳回</el-radio>
            </el-radio-group>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="closeAudit">关 闭</el-button>
        <el-button type="primary" @click="auditSubmit">确 定
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  listOutbound,
  getOutbound,
  delOutbound,
  addOutbound,
  updateOutbound,
  getOutDetail, getKcsl, listOutDetails, AuditOutbound, RevokeOutbound
} from "@/api/cx-crkgl/outbound";
import {
  getInventoryDetail,
  getRksl, InventoryReview,
  listDetails, listGoods,
  listPurchase,
  listUser,
  listWarehouse
} from "@/api/cx-crkgl/management";
import {listConsumer} from "@/api/units/consumer";
import {listSales} from "@/api/order/sales";
import {listDept} from "@/api/cx-ckgl/warehouse";
import {listLocation} from "@/api/cx-ckgl/location";
import {updateAudit} from "@/api/order/purchase";

export default {
  computed: {
    getConsumerName() {
      return (consumerId) => {
        const consumer = this.consumerList.find((item) => item.cId === consumerId);
        return consumer ? consumer.cName : '';
      };
    },
    calculatedUnitPrice() {
      return this.salesDetail.map(item => (item.salesAmount / item.salesVolume).toFixed(2));
    },
  },
  // watch: {
  //   'form.iS'(newValue) {
  //     if (!newValue) {
  //       this.form.orderId = '';
  //       this.form.cId='';
  //       this.form.personId = '';
  //       this.form.contactDetails ='';
  //     }
  //   },
  // },
  name: "Outbound",
  dicts: ['out_type', 'status','g_unit'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedCrkOutDetails: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      examine: true,
      revoke: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      catotal:0,
      totals: 0,
      // 查询参数
      caqueryParams: {
        pageNum: 1,
        pageSize: 5,
      },
      // 查询参数明细
      caqueryParammx: {
        pageNum: 1,
        pageSize: 5,
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 20,
      },
      pdList:[],
      // 出库管理表格数据
      outboundList: [],
      // 出库明细表格数据
      crkOutDetailsList: [],
      //仓库信息
      warehouseList:[],
      //库位
      locationList:[],
      //客服
      consumerList:[],
      //用户
      userList:[],
      //部门
      deptList:[],
      //销售列表数据
      saleList:[],
      //销售明细
      salesDetail:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openAudit: false,
      dialogMaterial1:false,
      dialogMaterial:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        outCode: null,
        outType: null,
        status: null,
        wId: null,
      },
      // 查询参数
      queryParams1: {
        pageNum: 1,
        pageSize: 5,
        sCode:null,
        gName: null,
        gUnit: null,
        wId:null,
      },
      // 表单参数
      form: {},

      auditForm: {
        auditResult: true
      },
      // 表单校验
      rules: {
        outType: [
          { required: true, message: "出库类型不能为空", trigger: "change" }
        ],
        outDate: [
          { required: true, message: "出库日期不能为空", trigger: "blur" }
        ],
        orderId: [
          { required: true, message: "关联订单", trigger: "blur" }
        ],
        cId: [
          { required: true, message: "客户不能为空", trigger: "blur" }
        ],
        personId: [
          { required: true, message: "联系人不能为空", trigger: "blur" }
        ],
        contactDetails: [
          { required: true, message: "联系方式不能为空", trigger: "blur" }
        ],
        outManager: [
          { required: true, message: "出库负责人不能为空", trigger: "blur" }
        ],
        wId: [
          { required: true, message: "出库仓库不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getWList();
    this.getConsumer();
    this.getUserList();
    this.getBmList();
    this.getCWList();
  },
  methods: {
    /** 查询出库管理列表 */
    getList() {
      this.loading = true;
      listOutbound(this.queryParams).then(response => {
        this.outboundList = response.rows;
        this.outboundList = response.rows.map(item => ({
          ...item,
          totalCksl: 0,
          money:0,
        }));
        const promises = this.outboundList.map(item => {
          return listOutDetails(item.outId).then(response => {
            const cksls = response.rows.map(childItem => childItem.itemQuantity);
            const moneys = response.rows.map(childItem => childItem.money);
            // 查询货品表获取货品名称
            return listGoods(this.queryParams2).then(goodsResponse => {
              const goodsList = goodsResponse.rows;
              response.rows.forEach(childItem => {
                const goods = goodsList.find(good => good.gCode === childItem.gCode);
                if (goods) {
                  childItem.goodsName = goods.gName; // 将货品名称附加到子表对象上
                }
              });
              return { cksls,moneys, response }; // 返回入库数量、未入库数量和子表对象数组
            });
          });
        });
        Promise.all(promises).then(results => {
          results.forEach((result, index) => {
            const item = this.outboundList[index];
            const totalCksl = result.cksls.reduce((acc, val) => acc + val, 0);
            const totalMoney =result.moneys.reduce((acc, val) => acc + val, 0);
            item.totalCksl = totalCksl;
            item.totalMoney = totalMoney;

            item.childItems=result.response.rows;
          });
          this.total = response.total;
          this.loading = false;
        });
      });
    },
    /** 查询仓库管理列表 */
    getWList() {
      listWarehouse().then(response => {
        this.warehouseList = response.rows;
      });
    },
    /** 查询仓库详情列表 */
    getCWList() {
      listLocation().then(response => {
        this.locationList = response.rows;
      });
    },
    updateLocationList() {
      // 根据选中的仓库ID（form.wId）更新库位数据
      // 示例代码：
      listLocation().then(response => {
        this.locationList = response.rows;
        this.locationList=this.locationList.filter(item => item.wId === this.form.wId);
      });
    },
    /** 查询客户列表 */
    getConsumer() {
      listConsumer().then(response => {
        this.consumerList = response.rows;
      });
    },
    /** 查询用户列表列表 */
    getUserList() {
      listUser().then(response => {
        this.userList = response.rows;
      });
    },
    //部门
    getBmList() {
      listDept().then(response => {
        this.deptList = response.data;
      });
    },
    //获取当前库存
    updateCurrentStock(row) {
      // 根据选择的库位更新当前库存
      const selectedLocation = this.locationList.find(dict => dict.slId === row.slId);
      if (selectedLocation) {
        // 调用 getKcsl 函数获取当前库存
        getKcsl(row.slId, row.gId)
          .then(response => {
            row.kcsl = response.data;
          })
          .catch(error => {
            console.error(error);
          });
      }
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.pdList=[];
    },
    // 表单重置
    reset() {
      this.form = {
        outId: null,
        outCode: null,
        outType: null,
        status: null,
        outDate: null,
        orderId: null,
        cId: null,
        personId: null,
        contactDetails: null,
        remark: null,
        outManager: null,
        wId: null,
        iS: null,
        reviewer: null,
        reviewerTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null
      };
      this.crkOutDetailsList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 搜索按钮操作1 */
    handleQuery1() {
      this.queryParams.pageNum = 1;
      this.getList1();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.outId)
      this.outCode=selection.map(item=> item.outCode)
      this.single = selection.length!==1 || selection.some(item => item.status !== 1);
      this.multiple = !selection.length || selection.some(item => item.status !== 1);
      this.examine = selection.some(item => item.status !== 1);
      this.revoke = selection.some(item => item.status !==3);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加出库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const outId = row.outId || this.ids
      // 使用 filter 方法筛选出入库状态为待审核的项
      const outboundList = this.outboundList.filter(item => item.outId === outId);
      if (outboundList.some(item => item.status !== 1)) {
        this.$message.warning('待审核状态才能进行编辑！');
        return;
      }
      this.reset();
      getOutbound(outId).then(response => {
        this.crkOutDetailsList = response.data.crkOutDetailsList;
        this.form = response.data;
        this.updateLocationList();
        this.crkOutDetailsList.forEach(item => {
          item.dj=(item.money/item.itemQuantity).toFixed(2);
        })
        this.open = true;
        this.title = "修改出库管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      let pd=true;
      this.crkOutDetailsList.forEach(row => {
        if(row.itemQuantity===""||row.slId===""){
          this.$message.warning('基本信息不能为空！');
          pd=false;
          return;
        }
        if (row.itemQuantity===0){
          this.$message.warning('出库数量不能为0！');
          pd=false;
          return;
        }
        row.money=(row.dj*row.itemQuantity);
      })
      if (pd===false){
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.crkOutDetailsList = this.crkOutDetailsList;
          if (this.form.outId != null) {
            updateOutbound(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.pdList=[];
              this.getList();
            });
          } else {
            addOutbound(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.pdList=[];
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const outIds = row.outId || this.ids;
      const outboundList = this.outboundList.filter(item => item.outId === outIds);
      if (outboundList.some(item => item.status !== 1)) {
        this.$message.warning('该单号不能删除！');
        return;
      }
      this.$modal.confirm('是否确认删除出库管理编号为"' + outIds + '"的数据项？').then(function() {
        return delOutbound(outIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /**查询销售订单列表 */
    getList1() {
      this.loading = true;
      this.queryParams1.sCode = this.form.orderId;
      this.queryParams1.wId=this.form.wId;
      getOutDetail(this.queryParams1).then(response => {
        this.salesDetail = response.rows;
        const promises = this.salesDetail.map(item => {
            const wcksl = item.salesVolume - item.cksl;
            item.wcksl = wcksl;
        });
        Promise.all(promises).then(() => {
          this.totals = response.total;
          this.loading = false;
        });
      });
    },
	/** 出库明细
序号 */
    rowCrkOutDetailsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 出库明细
添加按钮操作 */
    handleAddCrkOutDetails() {
      if (!this.form.orderId) {
        this.$message.warning('请先选择关联的进货订单');
        return;
      }
      this.dialogMaterial = true;
      this.getList1();
    },
    handleSelectionChange1(rows) {
      this.selectedRows = rows;
    },
    handleRowClickMaterial() {
      if (this.selectedRows.length === 0) {
        this.$message.warning('请先选择货品');
        return;
      }
      this.selectedRows.forEach(row => {
        if (this.pdList.some(item => item.gCode === row.gCode)) {
          this.$message.warning('该货品已经添加过');
          return;
        }
        let obj = {
          // 赋值逻辑...
          orderId: row.sCode, // 赋值逻辑根据实际情况进行修改
          outBound: row.cksl,
          notShipped: row.wcksl,
          gId:row.gId,
          slId: "",
          itemQuantity: "",
          remark: "",
          gName: row.gName,
          gCode: row.gCode,
          specCode: row.specCode,
          gUnit: row.gUnit,
          dj: row.salesAmount / row.salesVolume.toFixed(2),
          salesVolume: row.salesVolume,
          kcsl:row.kcsl,
          money:row.salesAmount,
        };
        console.info(row.kcsl)
        this.pdList.push(obj);
        this.crkOutDetailsList.push(obj);
      });
      this.$refs.multipleTable.clearSelection();
      this.dialogMaterial = false;
    },
    /** 出库明细
删除按钮操作 */
    handleDeleteCrkOutDetails() {
      if (this.checkedCrkOutDetails.length == 0) {
        this.$modal.msgError("请先选择要删除的出库明细数据");
      } else {
        const crkOutDetailsList = this.crkOutDetailsList;
        const checkedCrkOutDetails = this.checkedCrkOutDetails;
        this.pdList=[];
        this.crkOutDetailsList = crkOutDetailsList.filter(function(item) {
          return checkedCrkOutDetails.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleCrkOutDetailsSelectionChange(selection) {
      this.checkedCrkOutDetails = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-crkgl/outbound/export', {
        ...this.queryParams
      }, `outbound_${new Date().getTime()}.xlsx`)
    },
    /** 审核 */
    handleExamine(row) {
      this.openAudit = true;
    },
    /** 审核页面关闭 */
    closeAudit() {
      this.openAudit = false;
    },
    /** 审核提交*/
    auditSubmit() {
      const outCodes = [];
      const outIds = this.ids;
      outCodes.push(...this.outCode);
      const isApproved=this.auditForm.auditResult;
      AuditOutbound(outCodes,outIds,isApproved).then(res => {
        this.getList();
        this.$modal.msgSuccess("审核成功");
        this.openAudit = false;
      });
    },
    /** 审核按钮操作 */
    // handleExamine() {
    //   const outCodes = [];
    //   const outIds = this.ids;
    //   outCodes.push(...this.outCode);
    //   let isCancelled = false;
    //   this.$confirm(`审核单号为"${outCodes}"的数据项？`, '审核数据项', {
    //     distinguishCancelAndClose: true,
    //     confirmButtonText: '审核通过',
    //     cancelButtonText: '审核不通过',
    //     type: 'warning',
    //   }).then(() => {
    //     if (!isCancelled) {
    //       // 执行审核通过操作
    //       AuditOutbound(outCodes, outIds, true)
    //         .then(() => {
    //           this.getList();
    //           this.$message.success('审核完成！');
    //         })
    //         .catch(() => {
    //           // 处理审核操作失败
    //         });
    //     }
    //   }).catch(action => {
    //     if (action === 'close') {
    //       isCancelled = true;
    //     } else {
    //       // 执行审核不通过操作
    //       AuditOutbound(outCodes, outIds, false)
    //         .then(() => {
    //           this.getList();
    //           this.$message.success('审核完成！');
    //         })
    //         .catch(() => {
    //           // 处理审核操作失败
    //         });
    //     }
    //   });
    // },
    /** 撤销按钮操作 */
    handleRevoke() {
      const outIds = this.ids;
      const outCode = this.outCode;
      this.$modal.confirm('是否撤销单号为"' + outCode + '"的数据项？')
        .then(() => {
          return RevokeOutbound(outIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("撤销成功");
        })
        .catch(() => {
          // 处理撤销操作失败
        });
      // });
    },
    /** 根据供应商id进行查询联系人和联系方式 */
    consumerChange() {
      const selectedPersonId = this.consumerList.find(item => item.cId === this.form.cId);
      if (selectedPersonId) {
        this.form.personId = selectedPersonId.contactPerson;
      }

      const selectedContactDetails = this.consumerList.find(item => item.cId === this.form.cId);
      if (selectedContactDetails) {
        this.form.contactDetails = selectedContactDetails.contactNumber;
      }
    },

    /**弹框 */
    showMaterial1() {
      this.dialogMaterial1 = true;
      this.getXSList();
    },
    /**获取销售订单列表 */
    getXSList() {
      this.loading = true;
      listSales().then(response => {
        this.saleList = response.rows;
        this.catotal = response.total;
        this.loading = false;
      });
    },
    handleRowClickMaterial1(row) {
      // 修改数据的属性值
      this.form.orderId = row.sCode;
      this.form.cId=row.cId;
      this.form.personId = row.contactPerson;
      this.form.contactDetails = row.contactNumber;
      this.dialogMaterial1 = false; // 关闭对话框
    },
    getStatusClass(status) {
      if (status === 1) {
        return 'bg-light-blue'; // 浅蓝色背景样式类名
      } else if (status === 2) {
        return 'bg-light-red'; // 浅红色背景样式类名
      } else if (status === 3) {
        return 'bg-light-green'; // 浅绿色背景样式类名
      } else {
        return ''; // 默认样式类名或空字符串
      }
    },
  }
};
</script>
<style>
.bg-light-blue {
  background-color: #ecf5ff;
  border-color: #d9ecff;
  color: #409eff;
 border-radius: 4px;
}
.bg-light-red {
background-color: #fef0f0;
border-color: #fde2e2;
color: #f56c6c;
  border-radius: 4px;
}

.bg-light-green {
background-color: #f0f9eb;
border-color: #e1f3d8;
color: #67c23a;
  border-radius: 4px;
}
</style>
