<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="入库单号" prop="inCode">
        <el-input
          v-model="queryParams.inCode"
          placeholder="请输入入库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库类型" prop="inType">
        <el-select v-model="queryParams.inType" placeholder="请选择入库类型" clearable>
          <el-option
            v-for="dict in dict.type.in_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单据状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择单据状态" clearable>
          <el-option
            v-for="dict in dict.type.rk_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
        <el-form-item label="仓库" prop="wId">
          <el-select v-model="queryParams.wId" placeholder="请选择仓库" clearable>
            <el-option
              v-for="dict in warehouseList"
              :key="dict.wId"
              :label="dict.wName"
              :value="dict.wId"
            />
          </el-select>
        </el-form-item>
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
          v-hasPermi="['cx-crkgl:management:add']"
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
          v-hasPermi="['cx-crkgl:management:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['cx-crkgl:management:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-receiving"
          size="mini"
          :disabled="examine"
          @click="handleExamine"
          v-hasPermi="['cx-crkgl:management:examine']"
        >审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-connection"
          size="mini"
          :disabled="revoke"
          @click="handleRevoke"
          v-hasPermi="['cx-crkgl:management:revoke']"
        >撤销</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cx-crkgl:management:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单号" align="center" prop="inCode"  width="165"/>
      <el-table-column label="入库类型" align="center" prop="inType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.in_type" :value="scope.row.inType"/>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.rk_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="入库日期" align="center" prop="storageDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.storageDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="center" prop="wId">
        <template slot-scope="scope">
          <span v-for="item in warehouseList">
            <template v-if="scope.row.wId===item.wId">
              {{ item.wName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="入库货品" align="center" width="200px">
        <template slot-scope="scope">
    <span v-for="item in scope.row.childItems">
      {{ item.goodsName }}
    </span>
        </template>
      </el-table-column>
<!--      <el-table-column label="供应商 ID" align="center" prop="sId" />-->
      <el-table-column label="关联单号" align="center" prop="orderId" width="165"/>
      <el-table-column label="经办人" align="center" prop="wManager" >
        <template slot-scope="scope">
          <span v-for="item in userList">
            <template v-if="parseInt(scope.row.wManager)===item.userId">
              {{ item.nickName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="入库数量" align="center" prop="totalRksl">
        <template slot-scope="scope">
          {{ scope.row.totalRksl }}
        </template>
      </el-table-column>
      <el-table-column label="入库金额" align="center" prop="totalMoney">
        <template slot-scope="scope">
          {{ scope.row.totalMoney }}
        </template>
      </el-table-column>
<!--      <el-table-column label="联系人" align="center" prop="personId" />-->
<!--      <el-table-column label="联系方式" align="center" prop="contactDetails" />-->
<!--      <el-table-column label="是否关联" align="center" prop="iS" />-->

<!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="审核人" align="center" prop="reviewer" />
      <el-table-column label="审核时间" align="center" prop="reviewerTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewerTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180px">
        <template slot-scope="scope" >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-crkgl:management:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-crkgl:management:remove']"
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

    <!-- 添加进货明细 -->
    <el-dialog :visible.sync="dialogMaterial" :title="'进货订单 - ' + form.orderId" :modal="false">
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
      <el-table ref="multipleTable" :data="inventoryDetail" v-loading="loading" @selection-change="handleSelectionChange1">
        <el-table-column type="selection" align="center" width="50px">
        </el-table-column>
        <el-table-column label="货品编号" align="center" prop="gCode" width="200px"/>
        <el-table-column label="货品名称" align="center" prop="gName" />
        <el-table-column label="规格型号" align="center" prop="specCode" />
        <el-table-column label="单位" align="center" prop="gUnit" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
          </template>
        </el-table-column>
        <el-table-column label="进货单价" align="center" prop="dj">
          <template slot-scope="scope">
            {{ calculatedUnitPrice[scope.$index] }}
          </template>
        </el-table-column>
        <el-table-column label="进货数量" align="center" prop="purchaseQuantity" />
        <el-table-column label="进货金额" align="center" prop="money" />
        <el-table-column label="已入库数量" align="center" prop="rksl" width="100px">
          <!-- 使用插槽来自定义单元格内容 -->
          <template slot-scope="{ row }">
            {{ row.rksl }}
          </template>
        </el-table-column>
        <el-table-column label="未入库数量" align="center" prop="wrksl" width="100px">
          <!-- 使用插槽来自定义单元格内容 -->
          <template slot-scope="{ row }">
            {{ row.wrksl }}
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

    <!-- 添加进货订单 -->
    <el-dialog :visible.sync="dialogMaterial1" title="进货订单" :modal="false">
      <el-table :data="purchaseList" v-loading="loading" @row-click="handleRowClickMaterial1">
        <el-table-column label="进货单号" align="center" prop="poCode" />
        <el-table-column label="供应商" align="center" prop="sId">
          <template slot-scope="scope">
        <span v-for="item in supplierList"  :key="item.sId">
          <template v-if="scope.row.sId===item.sId">
            {{ item.sName }}
          </template>
        </span>
          </template>
        </el-table-column>
        <el-table-column label="联系人" align="center" prop="contactPerson">
          <template slot-scope="scope">
        <span v-for="item in supplierList" :key="item.sId">
          <template v-if="scope.row.sId===item.sId">
            {{ item.contactPerson }}
          </template>
        </span>
          </template>
        </el-table-column>
        <el-table-column label="联系方式" align="center" prop="contactDetails" width="120">
          <template slot-scope="scope">
        <span v-for="item in supplierList" >
          <template v-if="scope.row.sId===item.sId">
            {{ item.contactNumber }}
          </template>
        </span>
          </template>
        </el-table-column>
        <el-table-column label="进货人" align="center" prop="nickName">
          <template slot-scope="scope">
        <span v-for="item in userList" :key="item.userId">
          <template v-if="scope.row.purchaserId===item.userId">
            {{ item.nickName }}
          </template>
        </span>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
                  :limit.sync="caqueryParams.pageSize" @pagination="getJHList" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogMaterial1 = false">取消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改入库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- 表单项 -->
        <el-row :gutter="10">
          <el-col :span="12">
        <el-form-item label="入库单号" prop="inCode">
          <el-input v-model="form.inCode" placeholder="自动获取系统编码" :disabled="true" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="入库类型" prop="inType">
          <el-select v-model="form.inType" placeholder="请选择入库类型">
            <el-option
              v-for="dict in dict.type.in_type"
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
          <el-col :span="12">
        <el-form-item label="入库日期" prop="storageDate">
          <el-date-picker clearable
                          v-model="form.storageDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择入库日期">
          </el-date-picker>
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
          <el-checkbox v-model="form.iS">是否关联进货订单</el-checkbox>
        </el-form-item>
        <el-row :gutter="10">
          <el-col :span="12">
        <el-form-item label="供应商" prop="sId" v-if="form.iS">
          <el-input :value="getSupplierName(form.sId)" placeholder="请输入" :disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="供应商" prop="sId" v-else>
          <el-select v-model="form.sId" placeholder="请选择供应商" clearable @change="supplierChange">
            <el-option
              v-for="dict in supplierList"
              :key="dict.sId"
              :label="dict.sName"
              :value="dict.sId"
            />
          </el-select>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="联系人" prop="personId">
          <el-input v-model="form.personId" placeholder="请输入联系人" :disabled="true" readonly/>
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
        <el-form-item label="联系方式" prop="contactDetails">
          <el-input v-model="form.contactDetails" placeholder="请输入联系方式" :disabled="true" readonly/>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="经办人" prop="wManager">
          <el-select v-model="form.wManager" placeholder="请选择入库经办人" clearable>
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
          <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-divider content-position="center">入库明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddCrkImDetails">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteCrkImDetails">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="crkImDetailsList" :row-class-name="rowCrkImDetailsIndex" @selection-change="handleCrkImDetailsSelectionChange" ref="crkImDetails">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
<!--          <el-table-column label="入库单号" prop="inCode" width="150">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.inCode" placeholder="请输入入库单号" />-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="关联单号" prop="odId" width="150">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.odId" placeholder="关联单号" />-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column label="货品名称" align="center" prop="gName" >
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
          <el-table-column label="进货单价" align="center" prop="dj">
            <template slot-scope="scope">
              {{ scope.row.dj }}
            </template>
          </el-table-column>
          <el-table-column label="进货数量" align="center" prop="purchaseQuantity" >
            <template slot-scope="scope">
              {{ scope.row.purchaseQuantity }}
            </template>
          </el-table-column>
          <el-table-column label="进货金额" align="center" prop="money" >
            <template slot-scope="scope">
              {{ scope.row.money }}
            </template>
          </el-table-column>
          <el-table-column label="已入库数量" prop="quantityInStock" width="90px">
            <template slot-scope="scope">
              {{ scope.row.quantityInStock }}
            </template>
          </el-table-column>
          <el-table-column label="未入库数量" prop="unstockedQuantity" width="90px">
            <template slot-scope="scope">
              {{ scope.row.unstockedQuantity }}
            </template>
          </el-table-column>
          <el-table-column label="入库仓位" prop="slId" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.slId" placeholder="请选择入库仓位">
                <el-option
                  v-for="dict in locationList"
                  :key="dict.slId"
                  :label="dict.slName"
                  :value="dict.slId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="本次入库数量" prop="thisQuantity" width="150">
            <template slot-scope="scope">
              <el-input
                v-model.number="scope.row.thisQuantity"
                placeholder="请输入本次入库数量"
                type="number"
                :min="0"
              />
            </template>
          </el-table-column>
          <el-table-column label="生产日期" prop="productionDate" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.productionDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择生产日期" />
            </template>
          </el-table-column>
<!--          <el-table-column label="条形码" prop="barcode" width="150">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.barcode" placeholder="请输入条形码" />-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column label="备注" prop="remark" width="150">
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
  </div>
</template>

<Management @examine="performExamine"></Management>
<script>
import { listManagement, getManagement, delManagement, addManagement, updateManagement,listWarehouse,InventoryReview,WithdrawalStorage,listSupplier,listUser,listPurchase,getInventoryDetail,getRksl,listManagement1,listDetails,listGoods } from "@/api/cx-crkgl/management";
import {listLocation} from "@/api/cx-ckgl/location"
import item from "@/layout/components/Sidebar/Item";


export default {
  computed: {
    getSupplierName() {
      return (supplierId) => {
        const supplier = this.supplierList.find((item) => item.sId === supplierId);
        return supplier ? supplier.sName : '';
      };
    },
    calculatedUnitPrice() {
      return this.inventoryDetail.map(item => (item.money / item.purchaseQuantity).toFixed(2));
    },
  },
  watch: {
    'form.iS'(newValue) {
      if (!newValue) {
        this.form.orderId = '';
        this.form.sId='';
        this.form.personId = '';
        this.form.contactDetails ='';
      }
    },
  },
  name: "Management",
  dicts: ['in_type', 'rk_status','g_unit'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      in_code: [],
      gIds:[],
      // 子表选中数据
      checkedCrkImDetails: [],
      // 存储选中的行数据
      selectedRows: [],
      locationList:[],
      pdList:[],
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
      totals: 0,
      catotal:0,
      // 查询参数
      caqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数明细
      caqueryParammx: {
        pageNum: 1,
        pageSize: 5,
      },
      inventoryDetail:[],
      // 入库管理表格数据
      managementList: [],
      // 入库明细表格数据
      crkImDetailsList: [],
      crkImDetailsList1: [],
      managementList1: [],
      tzStockList:[],
      //仓库信息
      warehouseList:[],
      //供应商
      supplierList:[],
      //用户表格数据
      userList: [],
      //进货列表数据
      purchaseList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dialogMaterial1:false,
      dialogMaterial:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inCode: null,
        inType: null,
        wId: null,
        status: null,
      },
      // 查询参数
      queryParams1: {
        pageNum: 1,
        pageSize: 5,
        poCode:null,
        gName: null,
        gUnit: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        inType: [
          { required: true, message: "入库类型不能为空", trigger: "change" }
        ],
        wId: [
          { required: true, message: "仓库不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ],
        sId: [
          { required: true, message: "供应商不能为空", trigger: "blur" }
        ],
        wManager: [
          { required: true, message: "入库经办人不能为空", trigger: "blur" }
        ],
        storageDate: [
          { required: true, message: "入库日期不能为空", trigger: "blur" }
        ],
        orderId: [
          { required: true, message: "关联单号", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getList1();
    this.getWList();
    this.getCWList();
    this.getJHList();
    this.getUserList();
    this.getSupplier();
  },
  methods: {
    /** 查询入库管理列表 */
    getList() {
      this.loading = true;
      listManagement(this.queryParams).then(response => {
        this.managementList = response.rows;
        this.managementList = response.rows.map(item => ({
          ...item,
          totalRksl: 0,
          money:0,
        }));
        const promises = this.managementList.map(item => {
          return listDetails(item.inId).then(response => {
            const rksls = response.rows.map(childItem => childItem.thisQuantity);
            const moneys = response.rows.map(childItem => childItem.money);
            // 查询货品表获取货品名称
            return listGoods().then(goodsResponse => {
              const goodsList = goodsResponse.rows;
              response.rows.forEach(childItem => {
                const goods = goodsList.find(good => good.gCode === childItem.gCode);
                if (goods) {
                  childItem.goodsName = goods.gName; // 将货品名称附加到子表对象上
                }
              });
              return { rksls,moneys, response }; // 返回入库数量、未入库数量和子表对象数组
            });
          });
        });
        Promise.all(promises).then(results => {
          results.forEach((result, index) => {
            const item = this.managementList[index];
            const totalRksl = result.rksls.reduce((acc, val) => acc + val, 0);
            const totalMoney =result.moneys.reduce((acc, val) => acc + val, 0);
            item.totalRksl = totalRksl;
            item.totalMoney = totalMoney;

            item.childItems=result.response.rows;
          });
          this.totals = response.total;
          this.loading = false;
        });
      });
    },
    /**查询进货订单列表 */
    getList1() {
      this.loading = true;
      this.queryParams1.poCode = this.form.orderId;
      getInventoryDetail(this.queryParams1).then(response => {
        this.inventoryDetail = response.rows;
        const promises = this.inventoryDetail.map(item => {
          return getRksl(item.gCode).then(response => {
            const rksl = response.data;
            const wrksl = item.purchaseQuantity - rksl;
            item.rksl = rksl;
            item.wrksl = wrksl;
          });
        });
        Promise.all(promises).then(() => {
          this.totals = response.total;
          this.loading = false;
        });
      });
    },
    /** 查询仓库详情列表 */
    getCWList() {
      this.loading = true;
      listLocation().then(response => {
        this.locationList = response.rows;
        this.total = response.total;
        this.loading = false;
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
    /** 查询仓库管理列表 */
    getWList() {
      listWarehouse().then(response => {
        this.warehouseList = response.rows;
      });
    },
    /** 查询供应商列表列表 */
    getSupplier() {
      listSupplier().then(response => {
        this.supplierList = response.rows;
      });
    },
    /** 查询用户列表列表 */
    getUserList() {
      listUser().then(response => {
        this.userList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.dialogMaterial1 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        inId: null,
        inCode: null,
        inType: null,
        wId: null,
        status: null,
        sId: null,
        personId: null,
        contactDetails: null,
        iS: null,
        wManager: null,
        remark: null,
        reviewer: null,
        reviewerTime: null,
        storageDate: null,
        orderId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null
      };
      this.crkImDetailsList = [];
      this.tzStockList=[];
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
      this.ids = selection.map(item => item.inId)
      this.in_code=selection.map(item=> item.inCode)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.examine = selection.some(item => item.status !== 1);
      this.revoke = selection.some(item => item.status !==3);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const inId = row.inId || this.ids;
// 使用 filter 方法筛选出入库状态为待审核的项
      const managementList = this.managementList.filter(item => item.inId === inId);
      if (managementList.some(item => item.status !== 1)) {
        this.$message.warning('待审核状态才能进行编辑！');
        return;
      }
      this.reset();
      getManagement(inId).then(response => {
        this.crkImDetailsList = response.data.crkImDetailsList;
        this.form = response.data;
        console.info(this.crkImDetailsList)
          this.crkImDetailsList.forEach(item => {
            item.dj=(item.money/item.purchaseQuantity).toFixed(2);
          })
        this.open = true;
        this.title = "修改入库管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      let pd=true;
      this.crkImDetailsList.forEach(row => {
        if(row.thisQuantity===""||row.slId===""||row.productionDate===""){
          this.$message.warning('基本信息不能为空！');
          pd=false;
          return;
        }
        let obj={
          wId:this.form.wId,
          slId:row.slId,
          itemQuantity:0,
          numberPlans:row.thisQuantity,
          gId:row.gId,
          remark:row.remark,
        };
        row.money=(row.dj*row.thisQuantity);
        this.tzStockList.push(obj);
      })
      if (pd===false){
        return;
      }
      this.$refs["form"].validate(valid => {
        this.form.crkImDetailsList = this.crkImDetailsList;
        this.form.tzStockList=this.tzStockList;
        if (valid) {
          if (this.form.inId != null) {
            updateManagement(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addManagement(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const inIds = row.inId || this.ids;
      this.$modal.confirm('是否确认删除入库管理编号为"' + inIds + '"的数据项？').then(function() {
        return delManagement(inIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 审核按钮操作 */
    handleExamine() {
      const inCodes = [];
      const inIds = this.ids;
      inCodes.push(...this.in_code);
      // this.gIds = [];
      // listDetails(inId)
      //   .then(response => {
      //     response.rows.forEach(row => {
      //       this.gIds.push(row.gId + '');
      //     });
          let isCancelled = false;
          this.$confirm(`审核单号为"${inCodes}"的数据项？`, '审核数据项', {
            distinguishCancelAndClose: true,
            confirmButtonText: '审核通过',
            cancelButtonText: '审核不通过',
            type: 'warning',
          }).then(() => {
            if (!isCancelled) {
              // 执行审核通过操作
              InventoryReview(inCodes, inIds, true)
                .then(() => {
                  this.getList();
                  this.$message.success('审核完成！');
                })
                .catch(() => {
                  // 处理审核操作失败
                });
            }
          }).catch(action => {
            if (action === 'close') {
              isCancelled = true;
            } else {
              // 执行审核不通过操作
              InventoryReview(inCodes, inIds, false)
                .then(() => {
                  this.getList();
                  this.$message.success('审核完成！');
                })
                .catch(() => {
                  // 处理审核操作失败
                });
            }
          });
        // });
    },

    /** 撤销按钮操作 */
    handleRevoke() {
      const inIds = this.ids;
      const inCode = this.in_code;
      // let gIds = []; // 存储获取的 gIds

      // listDetails(inIds).then(response => {
      //   // 在回调函数内部进行遍历操作
      //   response.rows.forEach(row => {
      //     gIds.push(row.gId + '');
      //   });

        this.$modal.confirm('是否撤销单号为"' + inCode + '"的数据项？')
          .then(() => {
            return WithdrawalStorage(inIds);
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
	/** 入库明细序号 */
    rowCrkImDetailsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },

    /** 入库明细添加按钮操作 */
    handleAddCrkImDetails() {
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
          odId: row.poCode, // 赋值逻辑根据实际情况进行修改
          quantityInStock: row.rksl,
          unstockedQuantity: row.wrksl,
          gId:row.gId,
          slId: "",
          thisQuantity: "",
          productionDate: "",
          barcode: "",
          remark: "",
          gName: row.gName,
          gCode: row.gCode,
          specCode: row.specCode,
          gUnit: row.gUnit,
          dj: row.money / row.purchaseQuantity.toFixed(2),
          purchaseQuantity: row.purchaseQuantity,
        };
        this.pdList.push(obj);
        this.crkImDetailsList.push(obj);
      });
      this.$refs.multipleTable.clearSelection();
      this.dialogMaterial = false;
    },
    /** 入库明细删除按钮操作 */
    handleDeleteCrkImDetails() {
      if (this.checkedCrkImDetails.length === 0) {
        this.$modal.msgError("请先选择要删除的入库明细数据");
      } else {
        const crkImDetailsList = this.crkImDetailsList;
        const checkedCrkImDetails = this.checkedCrkImDetails;
        this.crkImDetailsList = crkImDetailsList.filter((item) => {
          // 删除选中的入库明细
          if (checkedCrkImDetails.indexOf(item.index) !== -1) {
            // 将相应的数据从 pdList 数组中删除
            const index = this.pdList.findIndex((pd) => pd.gCode === item.gCode);
            if (index !== -1) {
              this.pdList.splice(index, 1);
            }
            return false; // 不保留被删除的入库明细
          }
          return true; // 保留未被删除的入库明细
        });
      }
    },
    /** 复选框选中数据 */
    handleCrkImDetailsSelectionChange(selection) {
      this.checkedCrkImDetails = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-crkgl/management/export', {
        ...this.queryParams
      }, `management_${new Date().getTime()}.xlsx`)
    },
    /** 根据供应商id进行查询联系人和联系方式 */
    supplierChange() {
      const selectedPersonId = this.supplierList.find(item => item.sId === this.form.sId);
      if (selectedPersonId) {
        this.form.personId = selectedPersonId.contactPerson;
      }

      const selectedContactDetails = this.supplierList.find(item => item.sId === this.form.sId);
      if (selectedContactDetails) {
        this.form.contactDetails = selectedContactDetails.contactNumber;
      }
    },
    /**弹框 */
    showMaterial1() {
      this.dialogMaterial1 = true;
      this.getJHList();
    },
    /**获取订单列表 */
    getJHList() {
      this.loading = true;
      listPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows;
        this.catotal = response.total;
        this.loading = false;
      });
    },
    handleRowClickMaterial1(row) {
      // 修改数据的属性值
      this.form.orderId = row.poCode;
      this.form.sId=row.sId;
      this.form.personId = row.contactPerson;
      this.form.contactDetails = row.contactNumber;
      this.dialogMaterial1 = false; // 关闭对话框
    },
  }
};
</script>

