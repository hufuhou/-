<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="销售单号" prop="sCode">
        <el-input
          v-model="queryParams.sCode"
          placeholder="请输入销售单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择单据状态" clearable>
          <el-option
            v-for="dict in dict.type.sales_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="cId">
        <el-select v-model="queryParams.cId" placeholder="请选择客户" clearable>
          <el-option
            v-for="item in consumerList"
            :key="item.cId"
            :label="item.cName"
            :value="item.cId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售日期" prop="saleDate">
        <el-date-picker clearable
                        v-model="queryParams.saleDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择销售日期">
        </el-date-picker>
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
          v-hasPermi="['order:sales:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:sales:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:sales:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:sales:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table stripe v-loading="loading" :data="salesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <!--      <el-table-column label="销售订单ID" align="center" prop="sId"/>-->
      <el-table-column label="销售单号" align="center" prop="sCode" width="160" fixed="left"/>
      <!--      <el-table-column label="客户" align="center" prop="cId"/>-->
      <el-table-column label="单据状态" align="center" prop="status" fixed="left">
        <template slot-scope="scope">
          <el-tag :type="getStatusTagType(scope.row.status)">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="销售日期" align="center" prop="saleDate" width="100" fixed="left">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.saleDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="cName">
        <template slot-scope="scope">
        <span v-for="item in consumerList" :key="item.cId">
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
        <span v-for="item in consumerList" :key="item.cId">
          <template v-if="scope.row.cId===item.cId">
            {{ item.contactNumber }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="销售部门" align="center" prop="deptId">
        <template slot-scope="scope">
          <span v-for="item in deptList">
            <template v-if="scope.row.deptId===item.deptId">
              {{ item.deptName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="销售负责人" align="center" prop="nickName" width="90">
        <template slot-scope="scope">
        <span v-for="item in userList" :key="item.userId">
          <template v-if="scope.row.principalId===item.userId">
            {{ item.nickName }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="销售数量" align="center" prop="totalPurchaseQuantity"/>
      <el-table-column label="销售金额" align="center" prop="totalMoney"/>
      <el-table-column label="销售货品" align="center" :formatter="truncateText" width="180" prop="totalGoodsName"/>
      <el-table-column label="制单人" align="center" prop="createBy" width="100">
        <template slot-scope="scope">
        <span v-for="item in userList" :key="item.userId">
          <template v-if="scope.row.createBy==item.userId">
            {{ item.nickName }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="制单时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核人" align="center" prop="reviewer">
        <template slot-scope="scope">
        <span v-for="item in userList" :key="item.userId">
          <template v-if="scope.row.reviewer==item.userId">
            {{ item.nickName }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="审核时间" align="center" prop="reviewerDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewerDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="110" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:sales:edit']"
            :disabled="scope.row.status === 1 ||scope.row.status === 2 || scope.row.status === 3 || scope.row.status === 4"
          >修改
          </el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:sales:remove']"
            :disabled="scope.row.status === 1 ||scope.row.status === 2 ||scope.row.status === 3 || scope.row.status === 4"
          >删除
          </el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-message"
            @click="handleAudit(scope.row)"
            v-hasPermi="['order:sales:audit']"
            style="margin: 0"
            :disabled="scope.row.status === 1 ||scope.row.status === 2 || scope.row.status === 3 || scope.row.status === 4"
          >审核
          </el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleRevoke(scope.row)"
            v-hasPermi="['order:sales:revoke']"
            :disabled="scope.row.status === 0 || scope.row.status === 3 || scope.row.status === 4"
          >撤销
          </el-button>
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

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="销售单号" prop="sCode">
              <el-input v-model="form.sCode" placeholder="自动获取系统编码" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售日期" prop="saleDate">
              <el-date-picker clearable
                              v-model="form.saleDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              style="width: 400px"
                              placeholder="请选择进货日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="cId">
              <el-select v-model="form.cId" placeholder="请选择供应商" clearable @change="consumerChange"
                         style="width: 400px;">
                <el-option
                  v-for="item in consumerList"
                  :key="item.cId"
                  :label="item.cName"
                  :value="item.cId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactPerson">
              <el-input v-model="form.contactPerson" placeholder="请输入联系人" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="contactNumber">
              <el-input v-model="form.contactNumber" placeholder="请输入联系方式" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进货部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :normalizer="normalizer" :show-count="true"
                          placeholder="请选择进货部门"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="销售负责人" prop="principalId">
              <el-select v-model="form.principalId" placeholder="请选择进货人" style="width: 400px">
                <el-option
                  v-for="item in userList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <el-divider content-position="center">销售货品明细</el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addGoods"
            v-hasPermi="['order:purchase:add']"
          >添加
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleRemove"
            v-hasPermi="['order:purchase:remove']"
          >移除
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="goodsLoading" :data="salesDetails" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="45" align="center"/>
        <el-table-column label="货品名称" align="center" prop="gName"/>
        <el-table-column label="货品编号" width="75" align="center" prop="gCode"/>
        <el-table-column label="规格型号" align="center" prop="specCode"/>
        <el-table-column label="单位" align="center" prop="gUnit">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
          </template>
        </el-table-column>
        <el-table-column label="货品类型" align="center" prop="gtId">
          <template slot-scope="scope">
          <span v-for="item in goodsType">
            <template v-if="scope.row.gtId===item.gtId">
              {{ item.gtName }}
            </template>
          </span>
          </template>
        </el-table-column>
        <el-table-column label="货品数量" align="center" prop="salesVolume" width="120">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.salesVolume" size="mini"
                             @input="calculateTotal(scope.row)" :min="1" :max="100" label="描述文字"
                             style="width: 100px"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="销售单价" align="center" :formatter="formatPrice" prop="orPrice"/>
        <el-table-column label="金额" align="center" width="90">
          <template slot-scope="scope">
            {{
              (scope.row.salesVolume && scope.row.orPrice) ? (scope.row.salesVolume * scope.row.orPrice).toFixed(2) : 0
            }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="removeRow(scope.$index)"
            >移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--    添加货品对话框-->
    <el-dialog title="选择产品" :visible.sync="openGoodsList" append-to-body style="width: 1500px;margin: auto"
               @close="closeGoods">
      <el-form :model="goodsQueryParams" ref="queryGoodsForm" size="small" :inline="true" v-show="showSearch"
               label-width="68px">
        <el-form-item label="货品编号" prop="gCode">
          <el-input
            v-model="goodsQueryParams.gCode"
            placeholder="请输入货品编号"
            clearable
            @keyup.enter.native="handleGoodsQuery"
          />
        </el-form-item>
        <el-form-item label="货品名称" prop="gName">
          <el-input
            v-model="goodsQueryParams.gName"
            placeholder="请输入货品名称"
            clearable
            @keyup.enter.native="handleGoodsQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleGoodsQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetGoodsQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table ref="multipleTable" v-loading="goodsLoading" :data="goodsList"
                @selection-change="goodsSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="货品编号" align="center" prop="gCode" width="180"/>
        <el-table-column label="货品名称" align="center" prop="gName"/>
        <el-table-column label="规格型号" align="center" prop="specCode"/>
        <el-table-column label="单位" align="center" prop="gUnit">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
          </template>
        </el-table-column>
        <el-table-column label="出库单价" align="center" :formatter="formatPrice" prop="orPrice"/>
      </el-table>
      <pagination
        v-show="goodsTotal>0"
        :total="goodsTotal"
        :page.sync="goodsQueryParams.pageNum"
        :limit.sync="goodsQueryParams.pageSize"
        @pagination="getGoodsList"
      />
      <div slot="footer">
        <el-button @click="closeGoods">关 闭</el-button>
        <el-button type="primary" @click="goodsSubmit" v-hasPermi="['purchasesale:purchaseDetail:add']">确 定
        </el-button>
      </div>
    </el-dialog>

    <!--审核-->
    <el-drawer
      title="详情"
      :visible.sync="openAudit"
      :direction="direction"
      :before-close="handleClose"
      :size="1000">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="销售单号" prop="sCode">
              <el-input v-model="form.sCode" placeholder="自动获取系统编码" :disabled="true" style="width: 400px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售日期" prop="saleDate">
              <el-date-picker clearable
                              v-model="form.saleDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              style="width: 400px"
                              placeholder="请选择进货日期" :disabled="true">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="cId">
              <el-select v-model="form.cId" placeholder="请选择供应商" clearable @change="consumerChange"
                         style="width: 400px;" :disabled="true">
                <el-option
                  v-for="item in consumerList"
                  :key="item.cId"
                  :label="item.cName"
                  :value="item.cId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactPerson">
              <el-input v-model="form.contactPerson" placeholder="请输入联系人" :disabled="true" style="width: 400px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="contactNumber">
              <el-input v-model="form.contactNumber" placeholder="请输入联系方式" :disabled="true"
                        style="width: 400px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进货部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :normalizer="normalizer" :show-count="true"
                          placeholder="请选择进货部门" :disabled="true" style="width: 400px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="销售负责人" prop="principalId">
              <el-select v-model="form.principalId" placeholder="请选择进货人" style="width: 400px" :disabled="true">
                <el-option
                  v-for="item in userList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" :disabled="true"
                    style="width: 900px;"/>
        </el-form-item>
      </el-form>
      <el-divider content-position="center">销售货品明细</el-divider>
      <el-table v-loading="goodsLoading" :data="salesDetails" @selection-change="handleSelectionChange"
                style="margin-bottom: 22px">
        <el-table-column label="货品名称" align="center" prop="gName"/>
        <el-table-column label="货品编号" align="center" prop="gCode"/>
        <el-table-column label="规格型号" align="center" prop="specCode" width="120"/>
        <el-table-column label="单位" align="center" prop="gUnit" width="75">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
          </template>
        </el-table-column>
        <el-table-column label="货品类型" align="center" prop="gtId" width="75">
          <template slot-scope="scope">
          <span v-for="item in goodsType">
            <template v-if="scope.row.gtId===item.gtId">
              {{ item.gtName }}
            </template>
          </span>
          </template>
        </el-table-column>
        <el-table-column label="货品数量" align="center" prop="salesVolume" width="120">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.salesVolume" size="mini"
                             @input="calculateTotal(scope.row)" :min="1" :max="100" label="描述文字"
                             style="width: 100px" :disabled="true"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="销售单价" align="center" :formatter="formatPrice" prop="orPrice" width="80"/>
        <el-table-column label="金额" align="center" width="90">
          <template slot-scope="scope">
            {{
              (scope.row.salesVolume && scope.row.orPrice) ? (scope.row.salesVolume * scope.row.orPrice).toFixed(2) : 0
            }}
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top: 20px; display: flex; justify-content: flex-end; gap: 8px; margin-right: 22px;">
        <el-button type="warning" @click="handleAction('reject')">驳 回</el-button>
        <el-button type="primary" @click="handleAction('approve')">通 过</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {listSales, getSales, delSales, addSales, updateSales, updateAudit} from "@/api/order/sales";
import {listConsumer} from "@/api/units/consumer";
import {listDept} from "@/api/cx-ckgl/warehouse";
import {listUser} from "@/api/system/user";
import {listGoods, listType} from "@/api/cx-hpxx/goods";
import {getCode} from "@/api/order/sales";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Sales",
  dicts: ['sales_status', 'g_unit'],
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      loading: true,
      goodsLoading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      goodsTotal: 0,
      // 销售订单表格数据
      salesList: [],
      // 客户列表数据
      consumerList: [],
      // 部门数组
      deptList: [],
      //销售人（用户）数据
      userList: [],
      // 部门树选项
      deptOptions: undefined,
      //夏侯详细信息
      salesDetails: [],
      //货品类型
      goodsType: [],
      //货品信息数据
      goodsList: [],
      // 修改（删除）的数据
      updateDetails: [],
      // 销售编码
      code: null,
      // 订单id
      sId: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openGoodsList: false,
      openAudit: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sCode: null,
        cId: null,
        status: null,
        saleDate: null,
      },
      //货品列表查询参数
      goodsQueryParams: {
        pageNum: 1,
        pageSize: 10,
        gCode: null,
        gName: null,
        gUnit: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cId: [
          {required: true, message: "客户不能为空", trigger: "change"}
        ],
        status: [
          {required: true, message: "单据状态不能为空", trigger: "change"}
        ],
        saleDate: [
          {required: true, message: "销售日期不能为空", trigger: "blur"}
        ],
        contactPerson: [
          {required: true, message: "联系人不能为空", trigger: "blur"}
        ],
        contactDetails: [
          {required: true, message: "联系方式不能为空", trigger: "blur"}
        ],
        principalId: [
          {required: true, message: "销售负责人不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "关联至用户表/创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "操作时间不能为空", trigger: "blur"}
        ],
        updateBy: [
          {required: true, message: "关联至用户表/修改人不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        isDelte: [
          {required: true, message: "0：存在；1：已删除，不存在不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getConsumerList();
    this.getDeptTree();
    this.getPrincipal();
    this.getTypeList();
    this.getGoodsList();
  },
  // 监听器
  watch: {
    // 当这个dialogVisible为true的时候做验证
    openGoodsList(newVal) {
      if (newVal) {
        this.$nextTick(() => {
          console.log("666666666666")
          this.goodsList.forEach(product => {
            if (this.salesDetails.some(info => info.gCode === product.gCode)) {
              this.$refs.multipleTable.toggleRowSelection(product, true);
            }
          });
        });
      }
    }
  },
  methods: {
    /** 查询销售订单列表 */
    getList() {
      this.loading = true;
      listSales(this.queryParams).then(response => {
        this.salesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        sId: null,
        sdId: null,
        sCode: null,
        srId: null,
        cId: null,
        status: null,
        saleDate: null,
        contactPerson: null,
        contactDetails: null,
        principalId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null
      };
      this.resetForm("form");
      this.salesDetails = [];
      this.updateDetails = [];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleGoodsQuery() {
      this.goodsQueryParams.pageNum = 1;
      this.getGoodsList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    resetGoodsQuery() {
      this.resetForm("queryGoodsForm");
      this.handleGoodsQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.sId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 货品多选框选中数据 */
    goodsSelectionChange(selection) {
      this.goods = selection
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getsCode();
      this.reset();
      this.open = true;
      this.title = "添加销售订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sId = row.sId || this.ids
      getSales(sId).then(response => {
        this.form = response.data;
        this.salesDetails = response.data.detailsList.map(item => ({
          ...item.hpGoods,
          salesVolume: item.salesVolume,
          sdId: item.sdId,
          createBy: item.createBy,
          createTime: item.createTime
        }));
        this.updateDetails.push(...this.salesDetails);
        this.open = true;
        this.goodsLoading = false;
        this.title = "修改销售订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (this.salesDetails.length > 0) {
          this.salesDetails.forEach(details => {
            if (details.salesVolume == undefined) {
              // 提示用户输入数量
              this.$message.error('货品数量不能为空');
              // 阻止提交
              return;
            }
            return;
          })
        } else {
          this.$message.error('请选择销售货品');
          return;
        }

        const requestData = {
          orderSales: this.form,
          orderSalesDetails: this.salesDetails,
          updateDetails: this.updateDetails

        };

        if (valid) {
          if (this.form.sId != null) {
            updateSales(requestData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.sCode = this.code
            addSales(requestData).then(response => {
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
      const sIds = row.sId || this.ids;
      this.$modal.confirm('是否确认删除销售订单编号为"' + sIds + '"的数据项？').then(function () {
        return delSales(sIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('order/sales/export', {
        ...this.queryParams
      }, `sales_${new Date().getTime()}.xlsx`)
    },
    /** 查询客户列表 */
    getConsumerList() {
      listConsumer().then(res => this.consumerList = res.rows);
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      listDept().then(response => {
        const filteredDept = response.data.filter(dept => dept.deptId === 208 || dept.parentId === 208);
        this.deptOptions = this.handleTree(filteredDept, "deptId");
        this.deptList = response.data
        console.log(this.deptList)
      });
    },
    /** 查询负责人 */
    getPrincipal() {
      listUser().then(res => this.userList = res.rows);
    },
    /** 根据供应商id进行查询联系人和联系方式 */
    consumerChange() {
      const selectedContactPerson = this.consumerList.find(item => item.cId === this.form.cId);
      if (selectedContactPerson) {
        this.form.contactPerson = selectedContactPerson.contactPerson;
      }

      const selectedContactNumber = this.consumerList.find(item => item.cId === this.form.cId);
      if (selectedContactNumber) {
        this.form.contactNumber = selectedContactNumber.contactNumber;
      }
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
    },
    /** 新增货品信息操作 */
    addGoods() {
      // this.getGoodsList();
      this.openGoodsList = true;
    },
    /** 将总金额和进货编码塞入集合 */
    calculateTotal(row) {
      if (row.orPrice && row.salesVolume) {
        this.$set(row, 'salesAmount', (row.orPrice * row.salesVolume).toFixed(2));
      } else {
        this.$set(row, 'salesAmount', 0);
      }
      this.$set(row, 'sCode', this.code);
      console.log(this.salesDetails)
    },
    /** 移除 */
    removeRow(index) {
      // 通过 index 从 purchaseList 数组中移除对应行
      this.salesDetails.splice(index, 1);
    },
    handleRemove() {
      const sIds = this.ids;
      // 从 salesDetails 中移除选中的行数据
      sIds.forEach(row => {
        const index = this.salesDetails.findIndex(item => item.id === row.id);
        if (index !== -1) {
          this.salesDetails.splice(index, 1);
        }
      });

      // 清空选中状态
      this.$refs.salesTable.clearSelection();
    },
    /** 关闭产品选择列表 */
    closeGoods() {
      this.$refs.multipleTable.clearSelection()
      this.resetGoodsQuery();
      this.openGoodsList = false
    },
    /** 获取货品列表 */
    getGoodsList() {
      this.goodsLoading = true;
      listGoods(this.goodsQueryParams).then(response => {
        console.log(response.rows)
        this.goodsList = response.rows;
        this.goodsTotal = response.total;
        this.goodsLoading = false;
      });
    },
    /**  获取销售编码 */
    getsCode() {
      getCode().then(res => this.code = res.data.sCode)
    },
    /** 货品选择列表提交 */
    goodsSubmit() {
      this.goods = this.goods.filter(selectedProduct => {
        return !this.salesDetails.some(productInfo => productInfo.gCode === selectedProduct.gCode)
      });
      this.salesDetails.push(...this.goods);
      this.resetGoodsQuery();
      this.$refs.multipleTable.clearSelection()
      //关闭货品列表页面
      this.openGoodsList = false
    },
    /** 查询货品类型列表 */
    getTypeList() {
      listType(this.queryParams).then(response => {
        this.goodsType = response.data;
      });
    },
    /** 审核 */
    handleAudit(row) {
      const sId = row.sId || this.ids;
      this.sId = sId
      console.log(this.sId)
      this.openAudit = true;
      getSales(sId).then(response => {
        this.form = response.data;
        this.salesDetails = response.data.detailsList.map(item => ({
          ...item.hpGoods,
          salesVolume: item.salesVolume,
          sdId: item.sdId,
          createBy: item.createBy,
          createTime: item.createTime
        }));
        this.goodsLoading = false;
      });
    },
    /** 省略字符串 */
    truncateText(row, column, cellValue) {
      const maxLength = 12; // 设置最大长度
      if (cellValue && cellValue.length > maxLength) {
        return cellValue.substring(0, maxLength) + '等...';
      }
      return cellValue;
    },
    /** 显示两位小数 */
    formatPrice(row, column, cellValue) {
      // 使用 Number.prototype.toFixed 方法将数值格式化为两位小数
      return Number(cellValue).toFixed(2);
    },
    /** 单据标签 */
    getStatusTagType(status) {
      // 根据不同的status值返回不同的el-tag类型
      if (status === 0) {
        return 'info'; // 待审核状态
      } else if (status === 1) {
        return 'danger'; // 驳回状态
      } else if (status === 2) {
        return ''; // 待出库状态
      } else if (status === 3) {
        return 'warning'; // 部分出库状态
      } else if (status === 4) {
        return 'success'; // 已完成状态
      }
    },
    getStatusLabel(status) {
      // 根据不同的status值返回不同的标签显示文本
      if (status === 0) {
        return '待审核';
      } else if (status === 1) {
        return '驳回';
      } else if (status === 2) {
        return '待出库';
      } else if (status === 3) {
        return '部分出库';
      } else if (status === 4) {
        return '已完成';
      }
    },
    handleAction(actionType) {
      // actionType 可以是 'reject' 或 'approve'
      let newStatus = actionType === 'reject' ? 1 : actionType === 'approve' ? 2 : 0;
      console.log(this.sId)
      // 执行修改订单状态的操作，例如通过接口发送请求等
      updateAudit(newStatus, this.sId).then(res => {
        this.getList();
        this.$modal.msgSuccess("审核成功");
        this.openAudit = false;
      });
    },
    /** 撤销 */
    handleRevoke(row) {
      const sId = row.sId;
      this.$confirm('是否撤回所选单据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateAudit(0, sId).then(res => {
          this.getList();
          this.$message({
            type: 'success',
            message: '撤销成功!'
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消撤销'
        });
      });
    }
  }
};
</script>
