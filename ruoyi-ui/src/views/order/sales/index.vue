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
            v-for="dict in dict.type.order_status"
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

    <el-table v-loading="loading" :data="salesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="销售订单ID" align="center" prop="sId"/>-->
      <el-table-column label="销售单号" align="center" prop="sCode"/>
      <!--      <el-table-column label="客户" align="center" prop="cId"/>-->
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="销售日期" align="center" prop="saleDate" width="180">
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
      <el-table-column label="销售负责人" align="center" prop="nickName">
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
      <el-table-column label="销售货品" align="center" prop="totalGoodsName" width="180"/>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:sales:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:sales:remove']"
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

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="销售明细ID" prop="sdId">
          <el-input v-model="form.sdId" placeholder="请输入销售明细ID"/>
        </el-form-item>
        <el-form-item label="销售单号" prop="sCode">
          <el-input v-model="form.sCode" placeholder="请输入销售单号"/>
        </el-form-item>
        <el-form-item label="单据状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择单据状态">
            <el-option
              v-for="dict in dict.type.order_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售日期" prop="saleDate">
          <el-date-picker clearable
                          v-model="form.saleDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择销售日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系方式" prop="contactDetails">
          <el-input v-model="form.contactDetails" placeholder="请输入联系方式"/>
        </el-form-item>
        <el-form-item label="销售负责人" prop="principalId">
          <el-input v-model="form.principalId" placeholder="请输入销售负责人"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listSales, getSales, delSales, addSales, updateSales} from "@/api/order/sales";
import {listConsumer} from "@/api/units/consumer";
import {listDept} from "@/api/cx-ckgl/warehouse";
import {listUser} from "@/api/system/user";

export default {
  name: "Sales",
  dicts: ['order_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 销售订单表格数据
      salesList: [],
      // 客户列表数据
      consumerList: [],
      // 部门数组
      deptList: [],
      //进货人（用户）数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sCode: null,
        cId: null,
        status: null,
        saleDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sdId: [
          {required: true, message: "销售明细ID不能为空", trigger: "blur"}
        ],
        sCode: [
          {required: true, message: "销售单号不能为空", trigger: "blur"}
        ],
        srId: [
          {required: true, message: "销售退货id不能为空", trigger: "blur"}
        ],
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
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.sId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
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
        this.open = true;
        this.title = "修改销售订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sId != null) {
            updateSales(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSales(this.form).then(response => {
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
        const filteredDept = response.data.filter(dept => dept.deptId === 200 || dept.parentId === 200);
        this.deptOptions = this.handleTree(filteredDept, "deptId");
        this.deptList = response.data
        console.log(this.deptList)
      });
    },
    /** 查询负责人 */
    getPrincipal() {
      listUser().then(res => this.userList = res.rows);
    },
  }
};
</script>
