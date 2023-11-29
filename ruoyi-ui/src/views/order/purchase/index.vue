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
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
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
      <el-table-column label="审核人" align="center" prop="reviewer"/>
      <el-table-column label="审核时间" align="center" prop="reviewerDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewerDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="制单人" align="center" prop="createBy"/>
      <el-table-column label="制单时间" align="center" prop="createTime" width="180">
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
            v-hasPermi="['order:purchase:edit']"
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="进货单号" prop="poCode">
          <el-input v-model="form.poCode" placeholder="请输入进货单号"/>
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
        <el-form-item label="进货日期" prop="purchaseDate">
          <el-date-picker clearable
                          v-model="form.purchaseDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择进货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="供应商" prop="sId">
          <el-select v-model="queryParams.sId2" placeholder="请选择供应商" clearable @change="supplierChange">
            <el-option
              v-for="item in supplierList"
              :key="item.sId"
              :label="item.sName"
              :value="item.sId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人" readonly/>
        </el-form-item>
        <el-form-item label="联系方式" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入联系方式" readonly/>
        </el-form-item>
        <el-form-item label="进货人" prop="purchaserId">
          <el-input v-model="form.purchaserId" placeholder="请输入进货人"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
        <el-form-item label="审核人" prop="reviewer">
          <el-input v-model="form.reviewer" placeholder="请输入审核人"/>
        </el-form-item>
        <el-form-item label="审核时间" prop="reviewerDate">
          <el-date-picker clearable
                          v-model="form.reviewerDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择审核时间">
          </el-date-picker>
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
import {listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase} from "@/api/order/purchase";
import {listUser} from "@/api/system/user";
import {listSupplier} from "@/api/units/supplier";

export default {
  name: "Purchase",
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
      // 进货订单表格数据
      purchaseList: [],
      // 供应商列表数据
      supplierList: [],
      //根据供应商id查询的供应商数据
      supplierForId: [],
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
        poCode: null,
        status: null,
        purchaseDate: null,
        sId: null,
        sId2: null,
        userId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        poCode: [
          {required: true, message: "进货单号不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "单据状态不能为空", trigger: "change"}
        ],
        purchaseDate: [
          {required: true, message: "进货日期不能为空", trigger: "blur"}
        ],
        sId2: [
          {required: true, message: "供应商不能为空", trigger: "change"}
        ],
        contactPerson: [
          {required: true, message: "联系人不能为空", trigger: "blur"}
        ],
        purchaserId: [
          {required: true, message: "进货人不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "制单人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "制单时间不能为空", trigger: "blur"}
        ],
        updateBy: [
          {required: true, message: "关联至用户表/修改人不能为空", trigger: "blur"}
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
    this.getSupplierList();
    this.getBuyer();
  },
  methods: {
    /** 根据供应商id进行查询联系人和联系方式 */
    supplierChange() {
      const selectedContactPerson = this.supplierList.find(item => item.sId === this.queryParams.sId2);
      if (selectedContactPerson) {
        this.form.contactPerson = selectedContactPerson.contactPerson;
      }

      const selectedContactNumber = this.supplierList.find(item => item.sId === this.queryParams.sId2);
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
        contactDetails: null,
        purchaserId: null,
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
      this.ids = selection.map(item => item.poId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加进货订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const poId = row.poId || this.ids
      getPurchase(poId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改进货订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.poId != null) {
            updatePurchase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchase(this.form).then(response => {
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
    }
  }
};
</script>
