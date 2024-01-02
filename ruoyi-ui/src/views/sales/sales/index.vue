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
      <el-form-item label="售单编码" prop="sId">
        <el-input
          v-model="queryParams.sId"
          placeholder="请输入销售订单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售单号" prop="sCode">
        <el-input
          v-model="queryParams.sCode"
          placeholder="请输入销售单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货品编号" prop="goodsNumber">
        <el-input
          v-model="queryParams.goodsNumber"
          placeholder="请输入货品编号"
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
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ orderSaleNumber }}</p>
            <p>销售单数量</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" style="text-align: center">
        <!-- 第二个板块 -->
        <el-card shadow="hover">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ "￥" + sumSale.toFixed(2) / 10000 + "W" }}</p>
            <p>销售货品金额</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" style="text-align: center">
        <!-- 第三个板块 -->
        <el-card shadow="hover">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ orderProductionNum }}</p>
            <p>销售货品数量</p>
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
          v-hasPermi="['sales:sales:add']"
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
          v-hasPermi="['sales:sales:edit']"
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
          v-hasPermi="['sales:sales:remove']"
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
          v-hasPermi="['sales:sales:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="销售明细id" align="center" prop="sdId"/>-->
      <!--      <el-table-column label="退货明细id" align="center" prop="srdId"/>-->
      <!--      <el-table-column label="销售订单id" align="center" prop="sId"/>-->
      <el-table-column label="销售单号" align="center" prop="sCode"/>
      <!--      <el-table-column label="货品编号" align="center" prop="goodsNumber"/>-->
      <!--      <el-table-column label="规格型号" align="center" prop="specCode"/>-->
      <!--      <el-table-column label="单位" align="center" prop="unit"/>-->
      <!--      <el-table-column label="货品类型" align="center" prop="goodsType"/>-->
      <!--      <el-table-column label="进货数量" align="center" prop="purchaseQuantity"/>-->
      <!--      <el-table-column label="已入库" align="center" prop="aiStock"/>-->
      <!--      <el-table-column label="销售单价" align="center" prop="suPrice"/>-->
      <el-table-column label="销售金额" align="center" prop="salesAmount"/>
      <el-table-column label="销售数量" align="center" prop="salesVolume"/>
      <el-table-column label="创建人" align="center" prop="create_user_name"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="update_user_name">
        <template slot-scope="scope">
          {{ scope.row.update_user_name === null ? '未更改' : scope.row.update_user_name }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.updateTime">{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          <span v-else>未更改</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          {{ scope.row.remark === null || scope.row.remark === " " ? '暂无备注' : scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sales:sales:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sales:sales:remove']"
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

    <!-- 添加或修改销售明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <h1>暂未开发!</h1>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" disabled="disabled">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addSales, delSales, getNumbers, getSales, getSumSale, listSalesWithUser, updateSales} from "@/api/sales/sales";
import {updateUser} from "@/api/system/user";
import {parseTime} from "@/utils/ruoyi";
import {selectDataParam} from "@/api/opdm/opdm";

export default {
  name: "Sales",
  data() {
    return {
      //今日 or 昨日
      TodayOrYesterday: "",
      //周,月开始日
      BeginDay: "",
      //周,月结束日
      EndDay: "",
      //售出产品数
      orderProductionNum: 0,
      //售单订单数
      orderSaleNumber: 0,
      //售单总金额
      sumSale: 0,
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
      // 销售明细表格数据
      salesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层++
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sId: null,
        sCode: null,
        goodsNumber: null,
        unit: null,
        goodsType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sId: [
          {required: true, message: "销售订单id不能为空", trigger: "blur"}
        ],
        sCode: [
          {required: true, message: "销售单号不能为空", trigger: "blur"}
        ],
        goodsNumber: [
          {required: true, message: "货品编号不能为空", trigger: "blur"}
        ],
        specCode: [
          {required: true, message: "规格型号不能为空", trigger: "blur"}
        ],
        unit: [
          {required: true, message: "单位不能为空", trigger: "blur"}
        ],
        goodsType: [
          {required: true, message: "货品类型不能为空", trigger: "change"}
        ],
        purchaseQuantity: [
          {required: true, message: "进货数量不能为空", trigger: "blur"}
        ],
        aiStock: [
          {required: true, message: "已入库不能为空", trigger: "blur"}
        ],
        suPrice: [
          {required: true, message: "销售单价不能为空", trigger: "blur"}
        ],
        salesAmount: [
          {required: true, message: "销售金额不能为空", trigger: "blur"}
        ],
        salesVolume: [
          {required: true, message: "销售数量不能为空", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "备注不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "关联至用户表/创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
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
  },
  mounted() {
    this.getSaleNumber();
    //this.getSumSale();
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
            this.$modal.msgSuccess("查询" + this.TodayOrYesterday + "数据");
          } else if (Array.isArray(response.data)) {
            // 如果是数组
            this.BeginDay = response.data[0];
            this.EndDay = response.data[1];
            this.$modal.msgSuccess("查询起始日: " + this.BeginDay + ",结束日: " + this.EndDay + "间数据!");
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

    parseTime,
    updateUser,
    /** 查询销售订单总数 **/
    getSaleNumber() {
      this.loading = true;
      getNumbers().then(respone => {
        this.orderSaleNumber = respone.data;
        //console.info(respone);
        //this.$modal.msgSuccess("查询成功!");
        this.loading = false;
      }).catch(reason => {
        this.$modal.msgError("发生错误!");
      })
    },
    /** 查询销售订单总额 **/
    getSumSale() {
      this.loading = true;
      getSumSale().then(respone => {
        this.sumSale = respone.data;
        //console.info(respone);
        //this.$modal.msgSuccess("查询成功!");
        this.loading = false;
      }).catch(reason => {
        this.$modal.msgError("发生错误!");
      })
    },
    /** 查询销售明细列表 */
    getList() {
      this.loading = true;
      listSalesWithUser(this.queryParams).then(response => {
        if (response && response.rows) {
          this.sumSale = response.rows.reduce((total, row) => total + row.salesAmount, 0);
          this.orderProductionNum = response.rows.reduce((total, row) => total + row.salesVolume, 0);
          console.info(this.sumSale);
          console.info(this.orderProductionNum);
          this.salesList = response.rows;
          this.total = response.total;
        }
        this.loading = false;
      });
    },
    /*getList() {
      this.loading = true;
      listSalesWithUser(this.queryParams).then(response => {
        //console.info(response);
        this.salesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },*/
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        sdId: null,
        srdId: null,
        sId: null,
        sCode: null,
        goodsNumber: null,
        specCode: null,
        unit: null,
        goodsType: null,
        purchaseQuantity: null,
        aiStock: null,
        suPrice: null,
        salesAmount: null,
        salesVolume: null,
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
      this.ids = selection.map(item => item.sdId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加销售明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sdId = row.sdId || this.ids
      getSales(sdId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改销售明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sdId != null) {
            updateSales(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSales(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getSaleNumber();
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const sdIds = row.sdId || this.ids;
      this.$modal.confirm('是否确认删除销售明细编号为"' + sdIds + '"的数据项？').then(function () {
        return delSales(sdIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sales/sales/export', {
        ...this.queryParams
      }, `sales_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
