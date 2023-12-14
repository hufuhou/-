<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库ID" prop="warWId">
        <el-input
          v-model="queryParams.warWId"
          placeholder="请输入仓库ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点单号" prop="isCode">
        <el-input
          v-model="queryParams.isCode"
          placeholder="请输入盘点单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点结果" prop="isResult">
        <el-input
          v-model="queryParams.isResult"
          placeholder="请输入盘点结果"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库 ID" prop="wId">
        <el-input
          v-model="queryParams.wId"
          placeholder="请输入仓库 ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="isStartTime">
        <el-date-picker clearable
                        v-model="queryParams.isStartTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择盘点开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="isEndTime">
        <el-date-picker clearable
                        v-model="queryParams.isEndTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择盘点结束时间">
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
          v-hasPermi="['cx-kcinventory:kcinventory:add']"
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
          v-hasPermi="['cx-kcinventory:kcinventory:edit']"
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
          v-hasPermi="['cx-kcinventory:kcinventory:remove']"
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
          v-hasPermi="['cx-kcinventory:kcinventory:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kcinventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="盘点ID" align="center" prop="isId"/>
      <el-table-column label="仓库ID" align="center" prop="warWId"/>
      <el-table-column label="盘点单号" align="center" prop="isCode"/>
      <el-table-column label="盘点结果" align="center" prop="isResult"/>
      <el-table-column label="仓库 ID" align="center" prop="wId"/>
<!--      <el-table-column label="来自数据字典" align="center" prop="isType"/>-->
      <el-table-column label="盘点开始时间" align="center" prop="isStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.isStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点结束时间" align="center" prop="isEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.isEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库状态" align="center" prop="outStatus"/>
      <el-table-column label="入库状态" align="center" prop="inStatus"/>
      <el-table-column label="备注" align="center" prop="remark"/>
<!--      <el-table-column label="关联用户表" align="center" prop="isManager"/>-->
<!--      <el-table-column label="0：存在；1：已删除，不存在" align="center" prop="isDelte"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-kcinventory:kcinventory:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-kcinventory:kcinventory:remove']"
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

    <!-- 添加或修改库存盘点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库ID" prop="warWId">
          <el-input v-model="form.warWId" placeholder="请输入仓库ID"/>
        </el-form-item>
        <el-form-item label="盘点单号" prop="isCode">
          <el-input v-model="form.isCode" placeholder="请输入盘点单号"/>
        </el-form-item>
        <el-form-item label="盘点结果" prop="isResult">
          <el-input v-model="form.isResult" placeholder="请输入盘点结果"/>
        </el-form-item>
        <el-form-item label="仓库ID" prop="wId">
          <el-input v-model="form.wId" placeholder="请输入仓库 ID"/>
        </el-form-item>
        <el-form-item label="盘点开始时间" prop="isStartTime">
          <el-date-picker clearable
                          v-model="form.isStartTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择盘点开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="盘点结束时间" prop="isEndTime">
          <el-date-picker clearable
                          v-model="form.isEndTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择盘点结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
        <el-divider content-position="center">盘点明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddCrkIsDetails">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteCrkIsDetails">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="crkIsDetailsList" :row-class-name="rowCrkIsDetailsIndex"
                  @selection-change="handleCrkIsDetailsSelectionChange" ref="crkIsDetails">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="盘点单号" prop="isCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isCode" placeholder="请输入盘点单号"/>
            </template>
          </el-table-column>
          <el-table-column label="进货退货明细 ID 或销售订单 ID(相关订单 ID)" prop="orderId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderId" placeholder="请输入进货退货明细 ID 或销售订单 ID(相关订单 ID)"/>
            </template>
          </el-table-column>
          <el-table-column label="规格型号" prop="specCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specCode" placeholder="请输入规格型号"/>
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unit" placeholder="请输入单位"/>
            </template>
          </el-table-column>
          <el-table-column label="盘点数量" prop="countQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.countQuantity" placeholder="请输入盘点数量"/>
            </template>
          </el-table-column>
          <el-table-column label="盈亏数量" prop="profitLossQuantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.profitLossQuantity" placeholder="请输入盈亏数量"/>
            </template>
          </el-table-column>
          <el-table-column label="盘点状态 0:无盈亏 1:盘盈 2:盘亏" prop="isStatus" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isStatus" placeholder="请选择盘点状态 0:无盈亏 1:盘盈 2:盘亏">
                <el-option label="请选择字典生成" value=""/>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="盘点金额" prop="countAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.countAmount" placeholder="请输入盘点金额"/>
            </template>
          </el-table-column>
          <el-table-column label="入库单价" prop="iuPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.iuPrice" placeholder="请输入入库单价"/>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注"/>
            </template>
          </el-table-column>
          <el-table-column label="0：存在；1：已删除，不存在" prop="isDelte" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.isDelte" placeholder="0:存在 1:已删除"/>
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
import {
  listKcinventory,
  getKcinventory,
  addKcinventory,
  updateKcinventory,
  delKcinventory
} from "@/api/kcinventory/kcinventory";


export default {
  name: "Kcinventory",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedCrkIsDetails: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库存盘点表格数据
      kcinventoryList: [],
      // 盘点明细表格数据
      crkIsDetailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warWId: null,
        isCode: null,
        isResult: null,
        wId: null,
        isType: null,
        isStartTime: null,
        isEndTime: null,
        outStatus: null,
        inStatus: null,
        isManager: null,
        isDelte: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        warWId: [
          {required: true, message: "仓库表_仓库ID不能为空", trigger: "blur"}
        ],
        isCode: [
          {required: true, message: "盘点单号不能为空", trigger: "blur"}
        ],
        isResult: [
          {required: true, message: "盘点结果", trigger: "blur"}
        ],
        wId: [
          {required: true, message: "仓库 ID不能为空", trigger: "blur"}
        ],
        isType: [
          {required: true, message: "数据字典不能为空", trigger: "change"}
        ],
        isStartTime: [
          {required: true, message: "盘点开始时间不能为空", trigger: "blur"}
        ],
        isEndTime: [
          {required: true, message: "盘点结束时间不能为空", trigger: "blur"}
        ],
        outStatus: [
          {required: true, message: "出库状态", trigger: "change"}
        ],
        inStatus: [
          {required: true, message: "入库状态", trigger: "change"}
        ],
        isManager: [
          {required: true, message: "关联用户表不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询库存盘点列表 */
    getList() {
      this.loading = true;
      listKcinventory(this.queryParams).then(response => {
        this.kcinventoryList = response.rows;
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
        isId: null,
        warWId: null,
        isCode: null,
        isResult: null,
        wId: null,
        isType: null,
        isStartTime: null,
        isEndTime: null,
        outStatus: null,
        inStatus: null,
        remark: null,
        isManager: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null
      };
      this.crkIsDetailsList = [];
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
      this.ids = selection.map(item => item.isId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库存盘点";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const isId = row.isId || this.ids
      getKcinventory(isId).then(response => {
        this.form = response.data;
        this.crkIsDetailsList = response.data.crkIsDetailsList;
        this.open = true;
        this.title = "修改库存盘点";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.crkIsDetailsList = this.crkIsDetailsList;
          if (this.form.isId != null) {
            updateKcinventory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addKcinventory(this.form).then(response => {
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
      const isIds = row.isId || this.ids;
      this.$modal.confirm('是否确认删除库存盘点编号为"' + isIds + '"的数据项？').then(function () {
        return delKcinventory(isIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 盘点明细序号 */
    rowCrkIsDetailsIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 盘点明细添加按钮操作 */
    handleAddCrkIsDetails() {
      let obj = {};
      obj.isCode = "";
      obj.orderId = "";
      obj.specCode = "";
      obj.unit = "";
      obj.countQuantity = "";
      obj.profitLossQuantity = "";
      obj.isStatus = "";
      obj.countAmount = "";
      obj.iuPrice = "";
      obj.remark = "";
      obj.isDelte = "";
      this.crkIsDetailsList.push(obj);
    },
    /** 盘点明细删除按钮操作 */
    handleDeleteCrkIsDetails() {
      if (this.checkedCrkIsDetails.length == 0) {
        this.$modal.msgError("请先选择要删除的盘点明细数据");
      } else {
        const crkIsDetailsList = this.crkIsDetailsList;
        const checkedCrkIsDetails = this.checkedCrkIsDetails;
        this.crkIsDetailsList = crkIsDetailsList.filter(function (item) {
          return checkedCrkIsDetails.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleCrkIsDetailsSelectionChange(selection) {
      this.checkedCrkIsDetails = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-kcinventory/kcinventory/export', {
        ...this.queryParams
      }, `kcinventory_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
