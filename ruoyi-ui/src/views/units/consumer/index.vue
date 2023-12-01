<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户名称" prop="cName">
        <el-input
          v-model="queryParams.cName"
          placeholder="请输入客户名称/编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.status_list"
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
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="contactPerson">
        <el-input
          v-model="queryParams.contactPerson"
          placeholder="请输入联系人"
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
          v-hasPermi="['units:consumer:add']"
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
          v-hasPermi="['units:consumer:edit']"
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
          v-hasPermi="['units:consumer:remove']"
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
          v-hasPermi="['units:consumer:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="consumerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="客户ID" align="center" prop="cId"/>-->
      <el-table-column label="客户编码" align="center" prop="cCode" width="160"/>
      <el-table-column label="客户名称" align="center" prop="cName"/>
      <el-table-column label="所属行业" align="center" prop="industry">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.industry_list" :value="scope.row.industry"/>
        </template>
      </el-table-column>
      <el-table-column label="客户来源" align="center" prop="cSource">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.customer_source_list" :value="scope.row.cSource"/>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contactPerson"/>
      <el-table-column label="联系电话" align="center" prop="contactNumber" width="120"/>
      <el-table-column label="电子邮箱" align="center" prop="email" width="160"/>
      <el-table-column label="销售负责人" align="center" prop="nickName" width="100">
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
          <dict-tag :options="dict.type.status_list" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="客户地址" align="center" prop="cAddress" width="240"/>
      <el-table-column label="账户名称" align="center" prop="finance.accountName"/>
      <el-table-column label="纳税人识别号" align="center" prop="finance.tiNumber" width="120"/>
      <el-table-column label="银行账户" align="center" prop="finance.bankAccount" width="180"/>
      <el-table-column label="开户银行" align="center" prop="finance.bankDeposit" width="105"/>
      <el-table-column label="开户行地址" align="center" prop="finance.aobAddress" width="180"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="100">
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
            v-hasPermi="['units:consumer:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['units:consumer:remove']"
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

    <!-- 添加或修改客户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="95px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户编码" prop="cCode">
              <el-input v-model="form.cCode" placeholder="自动获取系统编码" :disabled="true" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="cName">
              <el-input v-model="form.cName" placeholder="请输入客户名称" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属行业" prop="industry">
              <el-select v-model="form.industry" placeholder="请选择所属行业">
                <el-option
                  v-for="dict in dict.type.industry_list"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户来源" prop="cSource">
              <el-select v-model="form.cSource" placeholder="请选择客户来源">
                <el-option
                  v-for="dict in dict.type.customer_source_list"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactPerson">
              <el-input v-model="form.contactPerson" placeholder="请输入联系人" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactNumber">
              <el-input v-model="form.contactNumber" placeholder="请输入联系电话" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电子邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入电子邮箱" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售负责人" prop="principalId">
              <el-select v-model="form.principalId" placeholder="请选择销售负责人">
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
        <el-form-item label="客户地址" prop="cAddress">
          <el-input v-model="form.cAddress" placeholder="请输入客户地址" style="width: 97.7%"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" style="width: 97.7%"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.status_list"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <el-divider content-position="center">财务信息</el-divider>
      <el-form ref="form2" :model="form2" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="账户名称" prop="accountName">
              <el-input v-model="form2.accountName" placeholder="请输入账户名称" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纳税人识别号" prop="tiNumber">
              <el-input v-model="form2.tiNumber" placeholder="请输入纳税人识别号" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="银行账户" prop="bankAccount">
              <el-input v-model="form2.bankAccount" placeholder="请输入银行账户" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开户银行" prop="bankDeposit">
              <el-input v-model="form2.bankDeposit" placeholder="请输入开户银行" style="width: 94.5%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="开户行地址" prop="aobAddress">
          <el-input v-model="form2.aobAddress" placeholder="请输入开户行地址" style="width: 97.7%"/>
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
import {listConsumer, getConsumer, delConsumer, addConsumer, updateConsumer, getCode} from "@/api/units/consumer";
import {listUser} from "@/api/system/user";

export default {
  name: "Consumer",
  dicts: ['status_list', 'industry_list', 'customer_source_list'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      fIds: [],
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
      // 客户表格数据
      consumerList: [],
      // 财务表格数据
      financeList: [],
      //进货人（用户）数据
      userList: [],
      //客户编码
      code: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cCode: null,
        cName: null,
        industry: null,
        contactPerson: null,
        status: null,
      },
      // 表单参数
      form: {},
      form2: {},
      // 表单校验
      rules: {
        fId: [
          {required: true, message: "财务id不能为空", trigger: "blur"}
        ],
        cName: [
          {required: true, message: "客户名称不能为空", trigger: "blur"}
        ],
        industry: [
          {required: true, message: "所属行业不能为空", trigger: "change"}
        ],
        cSource: [
          {required: true, message: "客户来源不能为空", trigger: "change"}
        ],
        contactPerson: [
          {required: true, message: "联系人不能为空", trigger: "blur"}
        ],
        contactNumber: [
          {required: true, message: "联系电话不能为空", trigger: "blur"}
        ],
        email: [
          {required: true, message: "电子邮箱不能为空", trigger: "blur"}
        ],
        principalId: [
          {required: true, message: "销售负责人不能为空", trigger: "blur"}
        ],
        cAddress: [
          {required: true, message: "客户地址不能为空", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "备注不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
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
          {required: true, message: "是否删除不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getBuyer();
  },
  methods: {
    /** 查询销售人 */
    getBuyer() {
      listUser().then(res => this.userList = res.rows);
    },

    /** 查询客户列表 */
    getList() {
      this.loading = true;
      listConsumer(this.queryParams).then(response => {
        this.consumerList = response.rows;
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
        cId: null,
        fId: null,
        cCode: null,
        cName: null,
        industry: null,
        cSource: null,
        contactPerson: null,
        contactNumber: null,
        email: null,
        principalId: null,
        cAddress: null,
        remark: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.cId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getConsumerCode();
      this.reset();
      this.open = true;
      this.title = "添加客户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const cId = row.cId || this.ids
      getConsumer(cId).then(response => {
        this.form = response.data;
        this.form2 = response.data.finance;
        this.open = true;
        this.title = "修改客户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.cId != null) {
            updateConsumer(this.form, this.form2).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.cCode = this.code
            addConsumer(this.form, this.form2).then(response => {
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
      const cIds = row.cId || this.ids;
      const fIds = row.fId || this.fIds;
      this.$modal.confirm('是否确认删除客户编号为"' + cIds + '"的数据项？').then(function () {
        return delConsumer(cIds, fIds);
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
    /** 财务添加按钮操作 */
    handleAddFinance() {
      let obj = {};
      obj.sCode = "";
      obj.cId = "";
      obj.accountName = "";
      obj.tiNumber = "";
      obj.bankAccount = "";
      obj.bankDeposit = "";
      obj.aobAddress = "";
      this.financeList.push(obj);
    },
    /** 财务删除按钮操作 */
    handleDeleteFinance() {
      if (this.checkedFinance.length == 0) {
        this.$modal.msgError("请先选择要删除的财务数据");
      } else {
        const financeList = this.financeList;
        const checkedFinance = this.checkedFinance;
        this.financeList = financeList.filter(function (item) {
          return checkedFinance.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleFinanceSelectionChange(selection) {
      this.checkedFinance = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('units/consumer/export', {
        ...this.queryParams
      }, `consumer_${new Date().getTime()}.xlsx`)
    },

    /**  获取编码 */
    getConsumerCode() {
      getCode().then(res => this.code = res.data.cCode)
    }
  }
};
</script>
