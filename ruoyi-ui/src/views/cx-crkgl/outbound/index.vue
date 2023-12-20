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
      <el-form-item label="出库状态" prop="outStatus">
        <el-select v-model="queryParams.outStatus" placeholder="请选择出库状态" clearable>
          <el-option
            v-for="dict in dict.type.rk_status"
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
      <el-table-column label="出库单号" align="center" prop="outCode" />
      <el-table-column label="出库类型" align="center" prop="outType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.out_type" :value="scope.row.outType"/>
        </template>
      </el-table-column>
      <el-table-column label="出库状态" align="center" prop="outStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.rk_status" :value="scope.row.outStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="出库日期" align="center" prop="outDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="关联订单" align="center" prop="orderId" />
      <el-table-column label="客户" align="center" prop="cId" />
      <el-table-column label="联系人" align="center" prop="personId" />
      <el-table-column label="联系方式" align="center" prop="contactDetails" />
      <el-table-column label="出库负责人" align="center" prop="outManager" />
      <el-table-column label="出库仓库" align="center" prop="wId" />
      <el-table-column label="审核人" align="center" prop="reviewer" />
      <el-table-column label="审核时间" align="center" prop="reviewerTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewerTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
              <el-input :value="getSupplierName(form.cId)" placeholder="请输入" :disabled="true"></el-input>
            </el-form-item>

            <el-form-item label="客户" prop="cId" v-else>
              <el-select v-model="form.cId" placeholder="请选择客户" clearable @change="supplierChange">
                <el-option
                  v-for="dict in supplierList"
                  :key="dict.cId"
                  :label="dict.sName"
                  :value="dict.cId"
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
        <el-form-item label="联系方式" prop="contactDetails">
          <el-input v-model="form.contactDetails" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="出库负责人" prop="outManager">
          <el-input v-model="form.outManager" placeholder="请输入出库负责人" />
        </el-form-item>
        <el-divider content-position="center">出库明细
信息</el-divider>
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
          <el-table-column label="关联单号" prop="orderId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderId" placeholder="请输入关联单号" />
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unit" placeholder="请输入单位" />
            </template>
          </el-table-column>
          <el-table-column label="物品数量" prop="itemQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemQuantity" placeholder="请输入物品数量" />
            </template>
          </el-table-column>
          <el-table-column label="已发货数量" prop="quantityShipped" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantityShipped" placeholder="请输入已发货数量" />
            </template>
          </el-table-column>
          <el-table-column label="未交付数量" prop="undeliveredQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.undeliveredQuantity" placeholder="请输入未交付数量" />
            </template>
          </el-table-column>
          <el-table-column label="当前产出数量" prop="currOutQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.currOutQuantity" placeholder="请输入当前产出数量" />
            </template>
          </el-table-column>
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

<script>
import { listOutbound, getOutbound, delOutbound, addOutbound, updateOutbound } from "@/api/cx-crkgl/outbound";
import {listWarehouse} from "@/api/cx-crkgl/management";

export default {
  name: "Outbound",
  dicts: ['out_type', 'rk_status'],
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出库管理表格数据
      outboundList: [],
      // 出库明细表格数据
      crkOutDetailsList: [],
      //仓库信息
      warehouseList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        outCode: null,
        outType: null,
        outStatus: null,
        wId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        outCode: [
          { required: true, message: "出库单号不能为空", trigger: "blur" }
        ],
        outType: [
          { required: true, message: "出库类型不能为空", trigger: "change" }
        ],
        outStatus: [
          { required: true, message: "出库状态不能为空", trigger: "change" }
        ],
        outDate: [
          { required: true, message: "出库日期不能为空", trigger: "blur" }
        ],
        orderId: [
          { required: true, message: "关联订单 ID不能为空", trigger: "blur" }
        ],
        cId: [
          { required: true, message: "客户 ID不能为空", trigger: "blur" }
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
          { required: true, message: "出库仓库 Id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getWList();
  },
  methods: {
    /** 查询出库管理列表 */
    getList() {
      this.loading = true;
      listOutbound(this.queryParams).then(response => {
        this.outboundList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询仓库管理列表 */
    getWList() {
      listWarehouse().then(response => {
        this.warehouseList = response.rows;
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
        outId: null,
        outCode: null,
        outType: null,
        outStatus: null,
        outDate: null,
        orderId: null,
        cId: null,
        personId: null,
        contactDetails: null,
        remark: null,
        outManager: null,
        wId: null,
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
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.outId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加出库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const outId = row.outId || this.ids
      getOutbound(outId).then(response => {
        this.form = response.data;
        this.crkOutDetailsList = response.data.crkOutDetailsList;
        this.open = true;
        this.title = "修改出库管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.crkOutDetailsList = this.crkOutDetailsList;
          if (this.form.outId != null) {
            updateOutbound(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutbound(this.form).then(response => {
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
      const outIds = row.outId || this.ids;
      this.$modal.confirm('是否确认删除出库管理编号为"' + outIds + '"的数据项？').then(function() {
        return delOutbound(outIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 出库明细
序号 */
    rowCrkOutDetailsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 出库明细
添加按钮操作 */
    handleAddCrkOutDetails() {
      let obj = {};
      obj.orderId = "";
      obj.unit = "";
      obj.itemQuantity = "";
      obj.quantityShipped = "";
      obj.undeliveredQuantity = "";
      obj.currOutQuantity = "";
      obj.remark = "";
      this.crkOutDetailsList.push(obj);
    },
    /** 出库明细
删除按钮操作 */
    handleDeleteCrkOutDetails() {
      if (this.checkedCrkOutDetails.length == 0) {
        this.$modal.msgError("请先选择要删除的出库明细数据");
      } else {
        const crkOutDetailsList = this.crkOutDetailsList;
        const checkedCrkOutDetails = this.checkedCrkOutDetails;
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
    }
  }
};
</script>
