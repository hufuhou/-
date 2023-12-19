<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="进货单号" prop="poCode">
        <el-input
          v-model="queryParams.poCode"
          placeholder="请输入进货单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择单据状态" clearable>
          <el-option
            v-for="dict in dict.type.order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="sId">
        <el-select v-model="queryParams.sId" placeholder="请选择供应商" clearable>
          <el-option
            v-for="item in supplierList"
            :key="item.sId"
            :label="item.sName"
            :value="item.sId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="进货日期" prop="purchaseDate">
        <el-date-picker clearable
                        v-model="queryParams.purchaseDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择进货日期">
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
          v-hasPermi="['order:purchase:add']"
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
          v-hasPermi="['order:purchase:edit']"
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
          v-hasPermi="['order:purchase:remove']"
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
          v-hasPermi="['order:purchase:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="handleAudit"
          v-hasPermi="['order:purchase:audit']"
        >审核
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="进货单号" align="center" prop="poCode" width="200"/>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="进货日期" align="center" prop="purchaseDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="供应商" align="center" prop="sName">
        <template slot-scope="scope">
        <span v-for="item in supplierList" :key="item.sId">
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
      <el-table-column label="联系方式" align="center" prop="contactNumber" width="120">
        <template slot-scope="scope">
        <span v-for="item in supplierList" :key="item.sId">
          <template v-if="scope.row.sId===item.sId">
            {{ item.contactNumber }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="进货部门" align="center" prop="deptId">
        <template slot-scope="scope">
          <span v-for="item in deptList">
            <template v-if="scope.row.deptId===item.deptId">
              {{ item.deptName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="进货人" align="center" prop="purchaserId"/>-->
      <el-table-column label="进货人" align="center" prop="nickName">
        <template slot-scope="scope">
        <span v-for="item in userList" :key="item.userId">
          <template v-if="scope.row.purchaserId===item.userId">
            {{ item.nickName }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="货品数量" align="center" prop="totalPurchaseQuantity"/>
      <el-table-column label="货品金额" align="center" prop="totalMoney"/>
      <el-table-column label="进货货品" align="center" prop="totalGoodsName" width="180"/>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:purchase:edit']"
            :disabled="scope.row.status === 2"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:purchase:remove']"
          >删除
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

    <!-- 添加或修改进货订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="进货单号" prop="poCode">
              <el-input v-model="form.poCode" placeholder="自动获取系统编码" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进货日期" prop="purchaseDate">
              <el-date-picker clearable
                              v-model="form.purchaseDate"
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
            <el-form-item label="供应商" prop="sId">
              <el-select v-model="form.sId" placeholder="请选择供应商" clearable @change="supplierChange"
                         style="width: 400px;">
                <el-option
                  v-for="item in supplierList"
                  :key="item.sId"
                  :label="item.sName"
                  :value="item.sId"
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
            <el-form-item label="进货人" prop="purchaserId">
              <el-select v-model="form.purchaserId" placeholder="请选择进货人" style="width: 400px">
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
      <el-divider content-position="center">进货明细</el-divider>
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
            @click="handleDelete"
            v-hasPermi="['order:purchase:remove']"
          >移除
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="goodsLoading" :data="purchaseDetails" @selection-change="handleSelectionChange">
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
        <el-table-column label="货品数量" align="center" prop="purchaseQuantity" width="120">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.purchaseQuantity" size="mini"
                             @input="calculateTotal(scope.row)" :min="1" :max="100" label="描述文字"
                             style="width: 100px"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="进货单价" align="center" prop="wrPrice"/>
        <el-table-column label="金额" align="center" width="90">
          <template slot-scope="scope">
            {{
              (scope.row.purchaseQuantity && scope.row.wrPrice) ? (scope.row.purchaseQuantity * scope.row.wrPrice).toFixed(2) : 0
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
        <el-table-column label="入库单价" align="center" prop="wrPrice"/>
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

    <!--审核对话框-->
    <el-dialog title="审核" :visible.sync="openAudit" append-to-body style="width: 500px; margin: auto"
               @close="closeAudit">
      <el-form :model="auditForm" ref="queryGoodsForm" size="small" :inline="true" v-show="showSearch"
               label-width="68px">
        <el-form-item label="审核结果" prop="auditResult">
          <el-col :span="24">
            <el-radio-group v-model="auditForm.auditResult">
              <el-radio label="2">通过</el-radio>
              <el-radio label="1">驳回</el-radio>
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
  listPurchase,
  getPurchase,
  delPurchase,
  addPurchase,
  updatePurchase,
  preProduct,
  updateAudit
} from "@/api/order/purchase";
import {deptTreeSelect, listUser} from "@/api/system/user";
import {listSupplier} from "@/api/units/supplier";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listGoods, listType} from "@/api/cx-hpxx/goods";
import {getCode} from "@/api/order/purchase"
import {addDetails, detailAndProduct} from "@/api/order/details";
import item from "@/layout/components/Sidebar/Item";
import {listDept} from "@/api/cx-ckgl/warehouse";

export default {
  name: "Purchase",
  dicts: ['order_status', 'g_unit'],
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      loading: true,
      goodsLoading: true,
      // 选中数组
      ids: [],
      // 选中货品数组
      goods: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      goodsTotal: 0,
      // 进货订单表格数据
      purchaseList: [],
      // 供应商列表数据
      supplierList: [],
      // 部门数组
      deptList: [],
      //根据供应商id查询的供应商数据
      supplierForId: [],
      //进货人（用户）数据
      userList: [],
      //货品信息数据
      goodsList: [],
      //货品类型
      goodsType: [],
      //进货详细信息
      purchaseDetails: [],
      // 修改（删除）的数据
      updateDetails: [],
      // 审核id
      poIds: [],
      // 部门树选项
      deptOptions: undefined,
      // 进货编码
      code: null,
      oldCode: null,
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
        poCode: null,
        status: null,
        purchaseDate: null,
        sId: null,
        sId2: null,
        userId: null,
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
      auditForm: {
        auditResult: '2'
      },
      // 表单校验
      rules: {
        status: [
          {required: true, message: "单据状态不能为空", trigger: "change"}
        ],
        purchaseDate: [
          {required: true, message: "进货日期不能为空", trigger: "blur"}
        ],
        sId: [
          {required: true, message: "供应商不能为空", trigger: "change"}
        ],
        purchaserId: [
          {required: true, message: "进货人不能为空", trigger: "blur"}
        ],
        purchaseQuantity: [
          {required: true, message: "货品数量不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getSupplierList();
    this.getBuyer();
    this.getDeptTree();
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
            if (this.purchaseDetails.some(info => info.gCode === product.gCode)) {
              this.$refs.multipleTable.toggleRowSelection(product, true);
            }
          });
        });
      }
    }
  },
  methods: {
    /** 根据供应商id进行查询联系人和联系方式 */
    supplierChange() {
      const selectedContactPerson = this.supplierList.find(item => item.sId === this.form.sId);
      if (selectedContactPerson) {
        this.form.contactPerson = selectedContactPerson.contactPerson;
      }

      const selectedContactNumber = this.supplierList.find(item => item.sId === this.form.sId);
      if (selectedContactNumber) {
        this.form.contactNumber = selectedContactNumber.contactNumber;
      }
    },

    /** 查询进货人 */
    getBuyer() {
      listUser().then(res => this.userList = res.rows);
    },

    /** 查询供应商 */
    getSupplierList() {
      listSupplier().then(res => this.supplierList = res.rows);
    },

    /** 查询进货订单列表 */
    getList() {
      this.loading = true;
      listPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows;
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
        poId: null,
        poCode: null,
        status: null,
        purchaseDate: null,
        sId: null,
        contactPerson: null,
        contactNumber: null,
        purchaserId: null,
        deptId: null,
        remark: null,
        reviewer: null,
        reviewerDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null
      };
      this.resetForm("form");
      this.purchaseDetails = [];
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
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.poId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 货品多选框选中数据 */
    goodsSelectionChange(selection) {
      this.goods = selection
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getPoCode();
      this.reset();
      this.open = true;
      this.title = "添加进货订单";
    },
    /** 新增货品信息操作 */
    addGoods() {
      // this.getGoodsList();
      this.openGoodsList = true;
    },
    /** 关闭产品选择列表 */
    closeGoods() {
      this.$refs.multipleTable.clearSelection()
      this.resetGoodsQuery();
      this.openGoodsList = false
    },
    /** 修改按钮操作 */
    handleUpdate: function (row) {
      this.reset();
      const poId = row.poId || this.ids
      getPurchase(poId).then(response => {
        console.log(response.data)
        this.form = response.data;
        this.purchaseDetails = response.data.detailsList.map(item => ({
          ...item.hpGoods,
          purchaseQuantity: item.purchaseQuantity,
          pdId: item.pdId,
          createBy: item.createBy,
          createTime: item.createTime
        }));
        this.updateDetails.push(...this.purchaseDetails);
        console.info(this.purchaseDetails)
        this.open = true;
        this.goodsLoading = false;
        this.title = "修改进货订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (this.purchaseDetails.length > 0) {
          this.purchaseDetails.forEach(details => {
            if (details.purchaseQuantity == undefined) {
              // 提示用户输入数量
              this.$message.error('货品数量不能为空');
              // 阻止提交
              return;
            }
            return;
          })
        } else {
          this.$message.error('请选择进货货品');
          return;
        }
        const dataToSubmit = this.purchaseDetails.map(row => ({
          ...row
        }))
        console.log('提交的数据:', dataToSubmit);
        console.log(this.updateDetails)
        const requestData = {
          orderPurchase: this.form,
          orderPurchaseDetails: this.purchaseDetails,
          updateDetails: this.updateDetails

        };
        if (valid) {
          if (this.form.poId != null) {
            updatePurchase(requestData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.poCode = this.code
            addPurchase(requestData).then(response => {
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
      const poIds = row.poId || this.ids;
      this.$modal.confirm('是否确认删除进货订单编号为"' + poIds + '"的数据项？').then(function () {
        return delPurchase(poIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('order/purchase/export', {
        ...this.queryParams
      }, `purchase_${new Date().getTime()}.xlsx`)
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      listDept().then(response => {
        const filteredDept = response.data.filter(dept => dept.deptId === 200 || dept.parentId === 200);
        this.deptOptions = this.handleTree(filteredDept, "deptId");
        this.deptList = response.data
        console.log(this.deptList)
      });
    },
    /** 获取货品列表 */
    getGoodsList() {
      this.goodsLoading = true;
      listGoods(this.goodsQueryParams).then(response => {
        this.goodsList = response.rows;
        this.goodsTotal = response.total;
        this.goodsLoading = false;
      });
    },
    /**  获取进货编码 */
    getPoCode() {
      getCode().then(res => this.code = res.data.poCode)
    },
    /** 货品选择列表提交 */
    goodsSubmit() {
      let preList = [];
      // let data = {
      //   poCode: this.code,
      //   goodsCode: []
      // }
      // //选中数组遍历
      // this.goods.forEach(item => {
      //   data.goodsCode.push(item.gCode)
      // })

      // preProduct(data).then(res => {
      //   preList = res.data;
      //   this.goodsLoading = true;
      //   return addDetails(preList);
      // }).then(res => {
      //   return detailAndProduct(this.code, this.oldCode)
      // }).then(res => {
      //   for (let i = 0; i < res.data.length; i++) {
      //     if (res.data[i].purchaseQuantity === null) {
      //       res.data[i].purchaseQuantity = 1;
      //     }
      //     if (res.data[i].poCode === this.code) {
      //
      //     }
      //   }
      //this.purchaseDetails = res.data;
      //console.log(this.purchaseDetails)
      // this.goodsLoading = false;
      // })
      this.goods = this.goods.filter(selectedProduct => {
        return !this.purchaseDetails.some(productInfo => productInfo.gCode === selectedProduct.gCode)
      });
      this.purchaseDetails.push(...this.goods);
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
    /** 移除 */
    removeRow(index) {
      // 通过 index 从 purchaseList 数组中移除对应行
      this.purchaseDetails.splice(index, 1);
    },
    /** 将总金额和进货编码塞入集合 */
    calculateTotal(row) {
      if (row.wrPrice && row.purchaseQuantity) {
        this.$set(row, 'money', (row.wrPrice * row.purchaseQuantity).toFixed(2));
      } else {
        this.$set(row, 'money', 0);
      }
      this.$set(row, 'poCode', this.code);
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
    /** 审核 */
    handleAudit(row) {
      this.poIds = row.poId || this.ids;
      console.log(this.poIds)
      this.openAudit = true;
      // this.$modal.confirm('是否确认审核进货订单编号为"' + poIds + '"的数据项？').then(function () {
      //   return delPurchase(poIds);
      // }).then(() => {
      //   this.getList();
      //   this.$modal.msgSuccess("审核成功");
      // }).catch(() => {
      // });
    },
    /** 审核页面关闭 */
    closeAudit() {
      this.openAudit = false;
    },
    /** 审核提交*/
    auditSubmit() {
      updateAudit(this.auditForm.auditResult, this.poIds).then(res => {
        this.getList();
        this.$modal.msgSuccess("审核成功");
        this.openAudit = false;
      });
    }
  }
};
</script>
