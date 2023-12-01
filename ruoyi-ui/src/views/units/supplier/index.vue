<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
      <el-form-item label="供应商" prop="sName">
        <el-input
          v-model="queryParams.sName"
          placeholder="请输入供应商名称/编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择所属行业" clearable>
          <el-option
            v-for="dict in dict.type.supplier_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属行业" prop="industry">
        <el-select v-model="queryParams.industry" placeholder="请选择所属行业" clearable>
          <el-option
            v-for="dict in dict.type.industry_list"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="进货负责人" prop="principalId">
        <el-input
          v-model="queryParams.principalId"
          placeholder="请输入进货负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['units:supplier:add']"
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
          v-hasPermi="['units:supplier:edit']"
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
          v-hasPermi="['units:supplier:remove']"
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
          v-hasPermi="['units:supplier:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="id" align="center" prop="sId"/>-->
      <el-table-column label="供应商编码" align="center" prop="sCode" width="180"/>
      <el-table-column label="供应商" align="center" prop="sName"/>
      <el-table-column label="所属行业" align="center" prop="industry">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.industry_list" :value="scope.row.industry"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商分类" align="center" prop="supplierType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.supplier_classification_list" :value="scope.row.supplierType"/>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contactPerson"/>
      <el-table-column label="联系电话" align="center" prop="contactNumber" width="120"/>
      <el-table-column label="电子邮箱" align="center" prop="email" width="150"/>
      <el-table-column label="进货负责人" align="center" prop="nickName" width="100">
        <template slot-scope="scope">
        <span v-for="item in userList" :key="item.userId">
          <template v-if="scope.row.principalId===item.userId">
            {{ item.nickName }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.supplier_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商地址" align="center" prop="sAddress" width="250"/>
      <el-table-column label="账户名称" align="center" prop="finance.accountName"/>
      <el-table-column label="纳税人识别号" align="center" prop="finance.tiNumber" width="120"/>
      <el-table-column label="银行账户" align="center" prop="finance.bankAccount" width="180"/>
      <el-table-column label="开户银行" align="center" prop="finance.bankDeposit"/>
      <el-table-column label="开户行地址" align="center" prop="finance.aobAddress" width="180"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['units:supplier:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['units:supplier:remove']"
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

    <!-- 添加或修改供应商列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="供应商编码" prop="sCode">
          <el-input v-model="form.sCode" placeholder="自动获取系统编码" :disabled="true"/>
        </el-form-item>
        <el-form-item label="供应商名称" prop="sName">
          <el-input v-model="form.sName" placeholder="请输入供应商名称"/>
        </el-form-item>
        <el-form-item label="所属行业" prop="industry">
          <el-select v-model="form.industry" placeholder="请选择所属行业">
            <el-option
              v-for="dict in dict.type.industry_list"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商分类" prop="supplierType">
          <el-select v-model="form.supplierType" placeholder="请选择供应商分类">
            <el-option
              v-for="dict in dict.type.supplier_classification_list"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮箱"/>
        </el-form-item>
        <el-form-item label="进货负责人" prop="principalId">
          <el-select v-model="form.principalId" placeholder="请选择进货负责人">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商地址" prop="sAddress">
          <el-input v-model="form.sAddress" placeholder="请输入供应商地址"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <el-divider content-position="center">财务信息</el-divider>

      <!--      <el-row :gutter="10" class="mb8">-->
      <!--        <el-col :span="1.5">-->
      <!--          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddFinance">添加</el-button>-->
      <!--        </el-col>-->
      <!--        <el-col :span="1.5">-->
      <!--          <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteFinance">删除</el-button>-->
      <!--        </el-col>-->
      <!--      </el-row>-->
      <!--      <el-table :data="financeList" :row-class-name="rowFinanceIndex" @selection-change="handleFinanceSelectionChange"-->
      <!--                ref="finance">-->
      <!--        <el-table-column type="selection" width="50" align="center"/>-->
      <!--        <el-table-column label="帐户名称" prop="accountName" width="150">-->
      <!--          <template slot-scope="scope">-->
      <!--            <el-input v-model="scope.row.accountName" placeholder="请输入帐户名称"/>-->
      <!--          </template>-->
      <!--        </el-table-column>-->
      <!--        <el-table-column label="纳税人识别号" prop="tiNumber" width="150">-->
      <!--          <template slot-scope="scope">-->
      <!--            <el-input v-model="scope.row.tiNumber" placeholder="请输入纳税人识别号"/>-->
      <!--          </template>-->
      <!--        </el-table-column>-->
      <!--        <el-table-column label="银行账户" prop="bankAccount" width="150">-->
      <!--          <template slot-scope="scope">-->
      <!--            <el-input v-model="scope.row.bankAccount" placeholder="请输入银行账户"/>-->
      <!--          </template>-->
      <!--        </el-table-column>-->
      <!--        <el-table-column label="开户银行" prop="bankDeposit" width="150">-->
      <!--          <template slot-scope="scope">-->
      <!--            <el-input v-model="scope.row.bankDeposit" placeholder="请输入开户银行"/>-->
      <!--          </template>-->
      <!--        </el-table-column>-->
      <!--        <el-table-column label="开户行地址" prop="aobAddress" width="150">-->
      <!--          <template slot-scope="scope">-->
      <!--            <el-input v-model="scope.row.aobAddress" placeholder="请输入开户行地址"/>-->
      <!--          </template>-->
      <!--        </el-table-column>-->
      <!--      </el-table>-->

      <el-form ref="form2" :model="form2" :rules="rules" label-width="100px">
        <el-form-item label="账户名称" prop="accountName">
          <el-input v-model="form2.accountName" placeholder="请输入账户名称"/>
        </el-form-item>
        <el-form-item label="纳税人识别号" prop="tiNumber">
          <el-input v-model="form2.tiNumber" placeholder="请输入纳税人识别号"/>
        </el-form-item>
        <el-form-item label="银行账户" prop="bankAccount">
          <el-input v-model="form2.bankAccount" placeholder="请输入银行账户"/>
        </el-form-item>
        <el-form-item label="开户银行" prop="bankDeposit">
          <el-input v-model="form2.bankDeposit" placeholder="请输入开户银行"/>
        </el-form-item>
        <el-form-item label="开户行地址" prop="aobAddress">
          <el-input v-model="form2.aobAddress" placeholder="请输入开户行地址"/>
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
import {
  listSupplier,
  getSupplier,
  delSupplier,
  addSupplier,
  updateSupplier,
  getCode,
  addFinance,
  updateFinance,
} from "@/api/units/supplier";
import {listUser} from "@/api/system/user";

export default {
  name: "Supplier",
  dicts: ['industry_list', 'supplier_status', 'supplier_classification_list'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中编码数组
      codes: [],
      // 子表选中数据
      checkedFinance: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 供应商列表表格数据
      supplierList: [],
      // 财务表格数据
      financeList: [],
      //进货人（用户）数据
      userList: [],
      //供应商编码
      code: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sCode: null,
        sName: null,
        industry: null,
        principalId: null,
        status: null,
        userId: null,
      },
      // 表单参数
      form: {},
      // 财务表单参数
      form2: {},
      // 表单校验
      rules: {
        fId: [
          {required: true, message: "财务id不能为空", trigger: "change"}
        ],
        sCode: [
          {required: true, message: "供应商编码不能为空", trigger: "blur"}
        ],
        sName: [
          {required: true, message: "供应商不能为空", trigger: "blur"}
        ],
        industry: [
          {required: true, message: "所属行业不能为空", trigger: "change"}
        ],
        supplierType: [
          {required: true, message: "使用数据字典/供应商分类不能为空", trigger: "change"}
        ],
        contactPerson: [
          {required: true, message: "联系人不能为空", trigger: "blur"}
        ],
        contactNumber: [
          {required: true, message: "联系电话不能为空", trigger: "blur"}
        ],
        principalId: [
          {required: true, message: "进货负责人不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        sAddress: [
          {required: true, message: "供应商地址不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateBy: [
          {required: true, message: "修改人不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        isDelte: [
          {required: true, message: "是否存在不能为空", trigger: "change"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getBuyer();
    //this.getSupplierCode();
  },
  methods: {
    /** 查询进货人 */
    getBuyer() {
      listUser().then(res => this.userList = res.rows);
    },

    /** 查询供应商列表列表 */
    getList() {
      this.loading = true;
      console.log("参数" + this.queryParams.industry)
      listSupplier(this.queryParams).then(response => {
        this.supplierList = response.rows;
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
        fId: null,
        sCode: null,
        sName: null,
        industry: null,
        supplierType: null,
        contactPerson: null,
        contactNumber: null,
        email: null,
        principalId: null,
        status: null,
        sAddress: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null,
      };
      this.financeList = [];
      this.resetForm("form");
      this.resetForm("form2");
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
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.sId)
      this.codes = selection.map(item => item.sCode)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getSupplierCode();
      this.reset();
      this.open = true;
      this.title = "添加供应商列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sId = row.sId || this.ids
      getSupplier(sId).then(response => {
        this.form = response.data;
        this.form2 = response.data.finance;
        this.open = true;
        this.title = "修改供应商列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form2"].validate(valid => {
        if (valid) {
          if (this.form.sId != null) {
            updateSupplier(this.form).then(response => {
              this.form2.sCode = this.form.sCode
              updateFinance(this.form2).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              })
            });
          } else {
            this.form.sCode = this.code
            this.form2.sCode = this.code
            addFinance(this.form2).then(response => {
              addSupplier(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              })
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const sIds = row.sId || this.ids;
      const sCodes = row.sCode || this.codes;
      console.log(sIds)
      this.$modal.confirm('是否确认删除供应商列表编号为"' + sIds + '"的数据项？').then(function () {
        return delSupplier(sIds, sCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 财务序号 */
    rowFinanceIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('units/supplier/export', {
        ...this.queryParams
      }, `supplier_${new Date().getTime()}.xlsx`)
    },
    getSupplierCode() {
      getCode().then(res => this.code = res.data.sCode)
    }
  }
};
</script>
