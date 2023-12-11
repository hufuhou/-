<template>
  <div class="app-container">
    <el-form>
      <el-form-item>
        <el-row>
          <el-button plain @click="getInfoByDate('today')">今 日</el-button>
          <el-button type="primary" plain @click="getInfoByDate('yesterday')">昨 日</el-button>
          <el-button type="success" plain @click="getInfoByDate('thisWeek')">本 周</el-button>
          <el-button type="info" plain @click="getInfoByDate('lastWeek')">上 周</el-button>
          <el-button type="warning" plain @click="getInfoByDate('thisMonth')">本 月</el-button>
          <el-button type="danger" plain @click="getInfoByDate('lastMonth')">上 月</el-button>
        </el-row>
      </el-form-item>
    </el-form>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="出库编号" prop="outId">
        <el-input
          v-model="queryParams.outId"
          placeholder="请输入出库编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="明细ID" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物品数" prop="itemQuantity">
        <el-input
          v-model="queryParams.itemQuantity"
          placeholder="请输入物品数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已发数" prop="quantityShipped">
        <el-input
          v-model="queryParams.quantityShipped"
          placeholder="请输入已发货数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="未交数" prop="undeliveredQuantity">
        <el-input
          v-model="queryParams.undeliveredQuantity"
          placeholder="请输入未交付数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产出数" prop="currOutQuantity">
        <el-input
          v-model="queryParams.currOutQuantity"
          placeholder="请输入当前产出数"
          clearable
          @keyup.enter.native="handleQuery"
        />
        <!--   批号录入     -->
      </el-form-item>
      <el-form-item label="批号" prop="batchNumber">
        <el-input
          v-model="queryParams.batchNumber"
          placeholder="请输入批号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
                        v-model="queryParams.updateTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="20" class="mb8">
      <!-- 搜索和重置按钮... -->
      <el-col :span="1.5"></el-col> <!-- 空白列 -->
      <el-col :span="4">
        <!-- 第一个板块 -->
        <el-card shadow="hover" style="text-align: center">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ outDetailNumber }}</p>
            <p>出库单数量</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" style="text-align: center">
        <!-- 第二个板块 -->
        <el-card shadow="hover">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ "$" + (outDetailNumber).toFixed(2) }}</p>
            <p>出库货品金额</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" style="text-align: center">
        <!-- 第三个板块 -->
        <el-card shadow="hover">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ outDetailNumber }}</p>
            <p>出库货品总数</p>
          </div>
        </el-card>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['out_details:details:add']"
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
          v-hasPermi="['out_details:details:edit']"
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
          v-hasPermi="['out_details:details:remove']"
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
          v-hasPermi="['out_details:details:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="订单ID" align="center" prop="omId"/>
      <el-table-column label="出库编号" align="center" prop="outId"/>
      <el-table-column label="明细 ID" align="center" prop="orderId"/>
      <el-table-column label="单位" align="center" prop="unit"/>
      <el-table-column label="物品数" align="center" prop="itemQuantity"/>
      <el-table-column label="已发数" align="center" prop="quantityShipped"/>
      <el-table-column label="未交数" align="center" prop="undeliveredQuantity"/>
      <el-table-column label="产出数" align="center" prop="currOutQuantity"/>
      <el-table-column label="批号" align="center" prop="batchNumber"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="条形码" align="center" prop="barcode"/>
      <el-table-column label="创建人" align="center" prop="create_user_name"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="update_user_name"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['out_details:details:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['out_details:details:remove']"
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

    <!-- 添加或修改出库明细
对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
<!--      <el-form ref="form" :model="form" :rules="rules" label-width="80px">-->
<!--        <el-form-item label="出库编号" prop="outId">-->
<!--          <el-input v-model="form.outId" placeholder="请输入出库编号"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="明细ID" prop="orderId">-->
<!--          <el-input v-model="form.orderId" placeholder="请输入明细 ID"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="单位" prop="unit">-->
<!--          <el-input v-model="form.unit" placeholder="请输入单位"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="物品数" prop="itemQuantity">-->
<!--          <el-input v-model="form.itemQuantity" placeholder="请输入物品数"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="已发量" prop="quantityShipped">-->
<!--          <el-input v-model="form.quantityShipped" placeholder="请输入已发数量"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="未交数" prop="undeliveredQuantity">-->
<!--          <el-input v-model="form.undeliveredQuantity" placeholder="请输入未交付数量"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="产出数" prop="currOutQuantity">-->
<!--          <el-input v-model="form.currOutQuantity" placeholder="请输入当前产出数量"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="批号" prop="batchNumber">-->
<!--          <el-input v-model="form.batchNumber" placeholder="请输入批号"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="备注" prop="remark">-->
<!--          <el-input v-model="form.remark" placeholder="请输入备注"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="条形码" prop="barcode">-->
<!--          <el-input v-model="form.barcode" placeholder="请输入条形码"/>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
      <h1>绝赞开发中！</h1>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" disabled="disabled">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addDetails,
  delDetails,
  getDetails,
  getNumber,
  listDetailsWithUser,
  updateDetails
} from "@/api/out_details/details";
import {selectDataParam} from "@/api/opdm/opdm";

export default {
  name: "Details",
  data() {
    return {
      //今日 or 昨日
      TodayOrYesterday: "",
      //周,月开始日
      BeginDay: "",
      //周,月结束日
      EndDay: "",
      //出库单量
      outDetailNumber: 0,
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
      // 出库明细表格数据
      detailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        outOutId: null,
        outId: null,
        orderId: null,
        unit: null,
        itemQuantity: null,
        quantityShipped: null,
        undeliveredQuantity: null,
        currOutQuantity: null,
        batchNumber: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        outOutId: [
          {required: true, message: "?不能为空", trigger: "blur"}
        ],
        outId: [
          {required: true, message: "出库编号不能为空", trigger: "blur"}
        ],
        orderId: [
          {required: true, message: "进货退货明细 ID 或销售订单 ID不能为空", trigger: "blur"}
        ],
        unit: [
          {required: true, message: "单位不能为空", trigger: "blur"}
        ],
        itemQuantity: [
          {required: true, message: "物品数量不能为空", trigger: "blur"}
        ],
        quantityShipped: [
          {required: true, message: "已发货数量不能为空", trigger: "blur"}
        ],
        undeliveredQuantity: [
          {required: true, message: "未交付数量不能为空", trigger: "blur"}
        ],
        currOutQuantity: [
          {required: true, message: "当前产出数量不能为空", trigger: "blur"}
        ],
        batchNumber: [
          {required: true, message: "批号不能为空", trigger: "blur"}
        ],
        barcode: [
          {required: true, message: "条形码不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "关联至用户表不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateBy: [
          {required: true, message: "关联至用户表不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ],
        isDelte: [
          {required: true, message: "0：存在；1：已删除，不存在不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.getOutNumber();
  },
  methods: {

    /**
     * 获取时间参数
     * @param param
     */
    async getDateParam(param) {
      return new Promise((resolve, reject) => {
        selectDataParam(param).then(response => {
          if (typeof response.data === 'string') {
            // 如果是字符串
            this.TodayOrYesterday = response.data;
            resolve(true);
          } else if (Array.isArray(response.data)) {
            // 如果是数组
            this.BeginDay = response.data[0];
            this.EndDay = response.data[1];
            resolve(true);
          } else {
            // 处理其他类型的数据，或者抛出错误提示
            console.error('非法数据类型:', typeof response.data);
            reject(false);
          }
        }).catch(error => {
          console.error('获取参数失败:', error);
          reject(false);
        });
      });
    },

    /**
     * 按时间段获取信息
     * @param param
     */
    async getInfoByDate(param) {
      let DateParams;
      this.TodayOrYesterday = null;
      this.BeginDay = null;
      this.EndDay = null;
      DateParams = null;
      try {
        // 等待 getDateParam 方法执行完毕
        await this.getDateParam(param);
        // 在这里可以获取到正确的 DateParams
        DateParams = [this.TodayOrYesterday, this.BeginDay, this.EndDay];
        console.info(DateParams)
        //TODO : 从这下面添加业务代码
      } catch (error) {
        // 处理错误
        console.error('按时间段查询进货信息失败:', error);
      }
    },

    /** 查询出库明细列表 */
    getList() {
      this.loading = true;
      listDetailsWithUser(this.queryParams).then(response => {
        this.detailsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getOutNumber() {
      this.loading = true;
      getNumber().then(response => {
        this.outDetailNumber = response.data;
        this.loading = false;
        this.$modal.msgSuccess("查询成功");
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        omId: null,
        outOutId: null,
        outId: null,
        orderId: null,
        unit: null,
        itemQuantity: null,
        quantityShipped: null,
        undeliveredQuantity: null,
        currOutQuantity: null,
        batchNumber: null,
        remark: null,
        barcode: null,
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
      this.ids = selection.map(item => item.omId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加出库明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const omId = row.omId || this.ids
      getDetails(omId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改出库明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.omId != null) {
            updateDetails(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDetails(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getOutNumber();
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const omIds = row.omId || this.ids;
      this.$modal.confirm('是否确认删除出库明细编号为"' + omIds + '"的数据项？').then(function () {
        return delDetails(omIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('out_details/details/export', {
        ...this.queryParams
      }, `details_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
