<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="盘点单号" prop="isCode">
        <el-input
          v-model="queryParams.isCode"
          placeholder="请输入盘点单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点结果" prop="isResult">
        <el-select v-model="queryParams.isResult" placeholder="请选择盘点结果" clearable>
          <el-option
            v-for="dict in dict.type.is_result"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库 ID" prop="wId">
        <el-input
          v-model="queryParams.wId"
          placeholder="请输入仓库 ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点类型" prop="isType">
        <el-select v-model="queryParams.isType" placeholder="请选择来自数据字典" clearable>
          <el-option
            v-for="dict in dict.type.is_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="daterangeIsStartTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="daterangeIsEndTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['kcinventory:kcinventory:add']"
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
          v-hasPermi="['kcinventory:kcinventory:edit']"
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
          v-hasPermi="['kcinventory:kcinventory:remove']"
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
          v-hasPermi="['kcinventory:kcinventory:export']"
        >导出
        </el-button>
        <el-button
          type="info"
          plain
          icon="el-icon-s-release"
          size="mini"
          :disabled="single"
          @click="handleExam"
          v-hasPermi="['kcinventory:kcinventory:edit']"
        >审核
        </el-button>
        <el-button
          type="info"
          plain
          icon="el-icon-finished"
          size="mini"
          :disabled="single"
          @click="handleFinish"
          v-hasPermi="['kcinventory:kcinventory:edit']"
        >完成
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kcinventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="盘点ID" align="center" prop="isId" />-->
      <el-table-column label="盘点单号" align="center" prop="isCode"/>
      <el-table-column label="盘点结果" align="center" prop="isResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_result" :value="scope.row.isResult"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="warehouseName"/>
      <el-table-column label="盘点类型" align="center" prop="isType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_type" :value="scope.row.isType"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="sheetStatus" width="100">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.sheetStatus === 0">待审核</el-tag>
          <el-tag type="success" size="mini" v-else-if="scope.row.sheetStatus === 1">审核通过</el-tag>
          <el-tag type="warning" size="mini" v-else-if="scope.row.sheetStatus === 2">驳回</el-tag>
          <el-tag type="info" size="mini" v-else-if="scope.row.sheetStatus === 3">已完成</el-tag>
          <el-tag type="danger" size="mini" v-else>未知状态</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="盘点开始时间" align="center" prop="isStartTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.isStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点结束时间" align="center" prop="isEndTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.isEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库状态" align="center" prop="outStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.out_status" :value="scope.row.outStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="入库状态" align="center" prop="inStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.in_status" :value="scope.row.inStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="经办人" align="center" prop="manager"/>
      <!--      <el-table-column label="0：存在；1：已删除，不存在" align="center" prop="isDelte" />-->
      <!--      <el-table-column label="货品code" align="center" prop="gCode"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['kcinventory:kcinventory:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['kcinventory:kcinventory:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="盘点ID" prop="isId">
          <el-input v-model="form.isId" placeholder="盘点id" disabled="disabled"/>
        </el-form-item>
        <el-form-item label="盘点单号" prop="isCode">
          <el-input v-model="form.isCode" placeholder="请输入盘点单号" disabled="disabled"/>
        </el-form-item>
        <el-form-item label="盘点结果" prop="isResult">
          <el-select v-model="form.isResult" placeholder="请选择盘点结果"
                     :disabled="this.title === '添加库存盘点'">
            <el-option
              v-for="dict in dict.type.is_result"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库" prop="wId">
          <el-select v-model="form.wId" placeholder="请选择仓库" filterable>
            <el-option
              v-for="warehouse in WareHouse"
              :key="warehouse.w_id"
              :label="warehouse.w_name"
              :value="warehouse.w_id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="盘点类型" prop="isType">
          <el-select v-model="form.isType" placeholder="请选择盘点类型">
            <el-option
              v-for="dict in dict.type.is_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
        <el-form-item label="经办人" prop="isManager">
          <el-select v-model="form.isManager" placeholder="请选择经办人">
            <el-option
              v-for="user in allUser"
              :key="user.user_id"
              :label="user.nick_name"
              :value="user.user_id"
            />
          </el-select>

        </el-form-item>
        <el-form-item label="出库状态" prop="outStatus">
          <el-select v-model="form.outStatus" placeholder="请选择出库状态">
            <el-option
              v-for="dict in dict.type.out_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入库状态" prop="inStatus">
          <el-select v-model="form.inStatus" placeholder="请选择入库状态">
            <el-option
              v-for="dict in dict.type.in_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">盘点明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="openDetailDialog">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="">删除
            </el-button>
          </el-col>
        </el-row>
        <el-dialog title="添加盘点明细信息" :visible.sync="openDetail" width="800px" append-to-body>
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
            <el-table-column label="单号ID" align="center" prop="isId" width="100"/>
            <el-table-column label="盘点单号" prop="isCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.isCode" placeholder="请输入盘点单号"/>
              </template>
            </el-table-column>
            <el-table-column label="相关订单ID" prop="orderId" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.orderId" placeholder="请输入相关订单ID"/>
              </template>
            </el-table-column>
            <el-table-column label="盘点数量" prop="countQuantity" width="210">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.countQuantity" placeholder="请输入盘点数量"/>
              </template>
            </el-table-column>
            <el-table-column label="盈亏数量" prop="profitLossQuantity" width="210">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.profitLossQuantity" placeholder="请输入盈亏数量"/>
              </template>
            </el-table-column>
            <el-table-column label="盘点状态" prop="isStatus" width="210">
              <template slot-scope="scope">
                <el-select v-model="scope.row.isStatus" placeholder="请选择盘点状态">
                  <el-option
                    v-for="dict in dict.type.inventory_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="盘点金额" prop="countAmount" width="210">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.countAmount" placeholder="请输入盘点金额"/>
              </template>
            </el-table-column>
            <el-table-column label="入库单价" prop="iuPrice" width="210">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.iuPrice" placeholder="请输入入库单价"/>
              </template>
            </el-table-column>
            <el-table-column label="备注" prop="remark" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.remark" placeholder="请输入备注"/>
              </template>
            </el-table-column>
            <el-table-column label="货品" prop="gCode" width="210">
              <template slot-scope="scope">
                <el-select v-model="scope.row.gCode" placeholder="请选择货品">
                  <el-option
                    v-for="good in allHpGoods"
                    :key="good.g_code"
                    :label="good.g_name"
                    :value="good.g_code"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
          </el-table>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="Detailcancel">取 消</el-button>
          </div>
        </el-dialog>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--  抽屉区  -->
    <el-drawer
      title="库存盘点审核"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="盘点ID" prop="isId">
          <el-input v-model="form.isId" placeholder="盘点id" disabled="disabled"/>
        </el-form-item>
        <el-form-item label="盘点单号" prop="isCode">
          <el-input v-model="form.isCode" placeholder="请输入盘点单号"/>
        </el-form-item>
        <el-form-item label="仓库" prop="wId">
          <el-select v-model="form.wId" placeholder="请选择仓库" filterable>
            <el-option
              v-for="warehouse in WareHouse"
              :key="warehouse.w_id"
              :label="warehouse.w_name"
              :value="warehouse.w_id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="盘点类型" prop="isType">
          <el-select v-model="form.isType" placeholder="请选择盘点类型">
            <el-option
              v-for="dict in dict.type.is_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
        <el-form-item label="经办人" prop="isManager">
          <el-select v-model="form.isManager" placeholder="请选择经办人">
            <el-option
              v-for="user in allUser"
              :key="user.user_id"
              :label="user.nick_name"
              :value="user.user_id"
            />
          </el-select>
        </el-form-item>
        <el-divider content-position="center">盘点审批信息</el-divider>
        <center>
          <div style="margin: 30px">
            <el-button type="primary" @click="approved(form.isId)" style="position: relative; left: -30px">通 过
            </el-button>
            <el-button type="danger" @click="dismiss(form.isId)" style="position: relative; left: 30px">驳 回
            </el-button>
          </div>
        </center>
      </el-form>
    </el-drawer>
  </div>
</template>

<script>
import {
  addKcinventory,
  delKcinventory,
  findAllHpGoods,
  findAllUser,
  findIsId,
  findSheetStatus,
  findWareHouse,
  genIsCode,
  getKcinventory,
  listKcinventory,
  updateKcinventory,
  updateSheetStatus
} from "@/api/kcinventory/kcinventory";
import {parseTime} from "@/utils/ruoyi";

export default {
  name: "Kcinventory",
  dicts: ['is_result', 'is_type', 'out_status', 'in_status', 'inventory_status'],
  data() {
    return {
      //添加明细弹出层状态
      openDetail: false,
      //全局isId判断
      isId: 0,
      //抽屉状态
      drawer: false,
      //右到左
      direction: 'rtl',
      //货品
      allHpGoods: [],
      //用户
      allUser: [],
      //仓库
      WareHouse: [],
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
      // 货品code时间范围
      daterangeIsStartTime: [],
      // 货品code时间范围
      daterangeIsEndTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        isCode: null,
        isResult: null,
        wId: null,
        isType: null,
        isStartTime: null,
        isEndTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        isCode: [
          {required: true, message: "盘点单号不能为空", trigger: "blur"}
        ],
        isResult: [
          {required: true, message: "盘点结果不能为空", trigger: "change"}
        ],
        isStartTime: [
          {required: true, message: "盘点开始时间不能为空", trigger: "blur"}
        ],
        isEndTime: [
          {required: true, message: "盘点结束时间不能为空", trigger: "blur"}
        ],
        isManager: [
          {required: true, message: "经办人不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.findWareHouse();
    this.findAllUser();
    this.findAllHpGoods();
  },
  methods: {
    parseTime,
    /** 查询库存盘点列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeIsStartTime && '' != this.daterangeIsStartTime) {
        this.queryParams.params["beginIsStartTime"] = this.daterangeIsStartTime[0];
        this.queryParams.params["endIsStartTime"] = this.daterangeIsStartTime[1];
      }
      if (null != this.daterangeIsEndTime && '' != this.daterangeIsEndTime) {
        this.queryParams.params["beginIsEndTime"] = this.daterangeIsEndTime[0];
        this.queryParams.params["endIsEndTime"] = this.daterangeIsEndTime[1];
      }
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

    //驳回盘点单
    dismiss(isId) {
      //this.$modal.msg(isId);
      const stateCode = 2;
      updateSheetStatus(isId, stateCode).then(response => {
        //console.info(response);
        const res = response.code;
        if (res === 200) {
          this.$modal.msgSuccess("操作成功!");
          this.drawer = false;
          this.reset();
          this.resetQuery();
        }
        if (res === 500) {
          this.$modal.msgError("操作失败!");
          this.drawer = false;
          this.reset();
        }
      }).catch(error => {
        this.$modal.msgError(error);
      })
    },

    //盘点单审批通过
    approved(isId) {
      //this.$modal.msg(isId);
      const stateCode = 1;
      updateSheetStatus(isId, stateCode).then(response => {
        //console.info(response);
        const res = response.code;
        if (res === 200) {
          this.$modal.msgSuccess("操作成功!");
          this.drawer = false;
          this.reset();
          this.resetQuery();
        }
        if (res === 500) {
          this.$modal.msgError("操作失败!");
          this.drawer = false;
          this.reset();
        }
      }).catch(error => {
        this.$modal.msgError(error);
      })
    },

    // 表单重置
    reset() {
      this.form = {
        isId: null,
        isCode: null,
        isResult: 3,
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
        isDelte: null,
        gCode: null
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
      this.daterangeIsStartTime = [];
      this.daterangeIsEndTime = [];
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
      genIsCode().then(response => {
        //console.info(response);
        this.form.isCode = response.data;
        this.form.isResult = 3;
      });
      findIsId().then(response => {
        //console.info(response);
        // 将字符串转换为整数
        let isId = parseInt(response.data, 10);
        // 如果转换成功，则加1，否则默认为1
        isId = isNaN(isId) ? 1 : isId + 1;
        this.isId = isId;
        this.form.isId = isId;
      });

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const isId = row.isId || this.ids;
      findSheetStatus(isId).then(response => {
        const status = response.data;
        if (status === 3) {
          this.$modal.msgWarning("此订单已完成，不允许修改");
        } else {
          this.reset();
          getKcinventory(isId).then(response => {
            this.form = response.data;
            this.crkIsDetailsList = response.data.crkIsDetailsList;
            this.open = true;
            this.title = "修改库存盘点";
          });
        }
      });
    },

    /** 审核按钮操作 */
    /* 0:待审核 1:审核通过 2:驳回 3:已完成  */
    handleExam(row) {
      this.reset();
      const isId = row.isId || this.ids;
      findSheetStatus(isId).then(response => {
        const status = response.data;
        let message = "";
        /* 如果订单已完成或者已经审核 */
        if (status === 1 || status === 3) {
          message = status === 1 ? "此单已经审核" : "此单已经完成";
          this.$modal.msgSuccess(message);
        } else if (status === 0 || status === 2) {
          message = status === 0 ? "初单审核" : "驳回单重新审核";
          this.$modal.msg(message);
          getKcinventory(isId).then(response => {
            const {data} = response;
            this.form = data;
            this.crkIsDetailsList = data.crkIsDetailsList;
            this.drawer = true;
          });
        } else {
          this.$modal.msgError("未知状态!");
        }
      });
    },

    /*完成盘点单*/
    handleFinish(row) {
      const isId = row.isId || this.ids;
      findSheetStatus(isId).then(response => {
        const status = response.data;
        let message = "";
        /* 如果订单已完成 */
        if (status === 3) {
          message = "已完成单不可操作";
          this.$modal.msgSuccess(message);
        } else {
          //修改主表状态为已完成
          const stateCode = 3;
          updateSheetStatus(isId, stateCode).then(response => {
            if (response.code === 200) {
              message = "盘点单已完成";
              this.$modal.msgSuccess(message);
              this.getList();
            } else {
              message = "发生错误";
              this.$modal.msgError(message);
            }
          })
        }
      });
    },

    //关闭抽屉
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
    },


    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.crkIsDetailsList = this.crkIsDetailsList;
          if (this.title == "修改库存盘点") {
            this.$modal.msg("updateKcinventory方法!");
            updateKcinventory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.openDetail = false;
              this.getList();
            });
          } else {
            this.$modal.msg("addKcinventory方法!");
            addKcinventory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.openDetail = false;
              this.getList();
            });
          }
        }
      });
    },

    Detailcancel() {
      this.open = false;
      this.openDetail = false;
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

    openDetailDialog() {
      const isId = this.form.isId;
      findSheetStatus(isId).then(response => {
        const status = response.data;
        if (status === 0 || status === 2) {
          this.$modal.msgWarning(status === 0 ? "此单待审核!" : "此单已驳回!");
        } else if (status === 3) {
          this.$modal.msgWarning("此单已完成!");
        } else if (status === 1) {
          getKcinventory(isId).then(response => {
            const {data} = response;
            this.form = data;
            this.crkIsDetailsList = data.crkIsDetailsList;
            this.openDetail = true;
          });
        } else if (status == null) {
          this.$modal.msgError("此表还未提交!");
        } else {
          this.$modal.msgError("未知状态!");
        }
      });
    },


    /** 盘点明细添加按钮操作 */
    handleAddCrkIsDetails() {
      this.openDetail = true;
      let obj = {};
      obj.isCode = this.form.isCode;
      obj.isId = this.form.isId;
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
      obj.gCode = "HPXX202311241034001";
      obj.isDelte = 0;
      obj.specCode = null;
      obj.unit = null;
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
      this.download('kcinventory/kcinventory/export', {
        ...this.queryParams
      }, `kcinventory_${new Date().getTime()}.xlsx`)
    },
    // 获取仓库信息
    findWareHouse() {
      findWareHouse().then(response => {
        //console.info(response);
        this.WareHouse = response.data;
      })
    },
    // 获取用户信息
    findAllUser() {
      findAllUser().then(response => {
        //console.info(response);
        this.allUser = response.data;
      })
    },

    // 获取货品信息
    findAllHpGoods() {
      findAllHpGoods().then(response => {
        //console.info(response);
        this.allHpGoods = response.data;
      })
    },


  }
};
</script>
