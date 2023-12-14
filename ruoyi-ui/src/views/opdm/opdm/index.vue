<template>
  <div class="app-container"
       style='font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;'>
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
      <el-form-item label="进货单id" prop="poId">
        <el-input
          v-model="queryParams.poId"
          placeholder="请输入进货订单id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进货单号" prop="pdCode">
        <el-input
          v-model="queryParams.pdCode"
          placeholder="请输入进货单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货品id" prop="goodsId">
        <el-input
          v-model="queryParams.goodsId"
          placeholder="请输入货品id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格型号" prop="specCode">
        <el-input
          v-model="queryParams.specCode"
          placeholder="请输入规格型号"
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
      <el-row :gutter="20" class="mb8">
        <!-- 搜索和重置按钮... -->
        <el-col :span="1.5"></el-col> <!-- 空白列 -->
        <el-col :span="4"> <!-- 第一个板块 -->
          <el-card shadow="hover" style="text-align: center">
            <div>
              <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ orderNum }}</p>
              <p>进货单数量</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="4" style="text-align: center"> <!-- 第二个板块 -->
          <el-card shadow="hover">
            <div>
              <p style="color: #00afff;font-size: 35px;line-height: 1px">
                {{ "￥" + (OrderPurchaseMoney / 10000).toFixed(2) + "W" }}</p>
              <p>进货货品金额</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="4" style="text-align: center"> <!-- 第三个板块+ -->
          <el-card shadow="hover">
            <div>
              <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ orderProductionNum }}</p>
              <p>进货货品数量</p>
            </div>
          </el-card>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['opdm:opdm:add']"
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
          v-hasPermi="['opdm:opdm:edit']"
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
          v-hasPermi="['opdm:opdm:remove']"
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
          v-hasPermi="['opdm:opdm:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="opdmList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="进货明细单id" align="center" prop="pdId"/>
      <el-table-column label="进货单号" align="center" prop="poCode"/>
      <el-table-column label="货品" align="center" prop="goods_name"/>
      <el-table-column label="进货数量" align="center" prop="purchaseQuantity"/>
      <el-table-column label="总金额" align="center" prop="money"/>
      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          {{ scope.row.remark === null || scope.row.remark === " " ? '暂无备注' : scope.row.remark }}
        </template>
      </el-table-column>
<!--      <el-table-column label="是否删除" align="center" prop="isDelete">-->
<!--        <template slot-scope="scope">-->
<!--          {{ scope.row.isDelete === 1 ? '删除' : '未删除' }}-->
<!--        </template>-->
<!--      </el-table-column>-->
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:details:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:details:remove']"
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

    <!-- 添加或修改进货明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <h1>绝赞开发中!</h1>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" disabled="disabled">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addOpdm,
  delOpdm,
  findInfoByDate,
  getOpdm,
  selectDataParam,
  selectListUG,
  selectNum,
  updateOpdm
} from "@/api/opdm/opdm";
import {parseTime} from "../../../utils/ruoyi";

export default {
  name: "Opdm",
  data() {
    return {
      //今日 or 昨日
      TodayOrYesterday: "",
      //周,月开始日
      BeginDay: "",
      //周,月结束日
      EndDay: "",
      //进货货品数量
      orderProductionNum: 0,
      //总售金额
      OrderPurchaseMoney: 0,
      //订单数
      orderNum: 0,
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
      // 进货明细表格数据+
      opdmList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        poId: null,
        pdCode: null,
        goodsId: null,
        specCode: null,
        unit: null,
        goodsType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        poId: [
          {required: true, message: "进货订单id不能为空", trigger: "blur"}
        ],
        pdCode: [
          {required: true, message: "进货单号不能为空", trigger: "blur"}
        ],
        goodsId: [
          {required: true, message: "货品id不能为空", trigger: "blur"}
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
        niStock: [
          {required: true, message: "未入库不能为空", trigger: "blur"}
        ],
        returnQuantity: [
          {required: true, message: "本次退货数量不能为空", trigger: "blur"}
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
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.getOrderNum();
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
        /*console.info(DateParams);*/
        console.info(DateParams)
        findInfoByDate(DateParams)
          .then(response => {
            // 处理响应
            console.info(response)
            this.$set(this, 'opdmList', response.data);
          })
          .catch(error => {
            //处理错误
            console.info(error)
          });
      } catch (error) {
        // 处理错误
        console.error('按时间段查询进货信息失败:', error);
      }
    },
    parseTime,
    /** 查询进货明细列表 */
    getList() {
      this.loading = true;
      selectListUG(this.queryParams).then(response => {
        if (response && response.rows) {
          //总进货金额
          this.OrderPurchaseMoney = response.rows.reduce((total, row) => total + row.money, 0);
          //总进货订单数
          this.orderProductionNum = response.rows.reduce((total, row) => total + row.purchaseQuantity, 0);
          console.info(this.OrderPurchaseMoney);
          this.opdmList = response.rows;
          this.total = response.total;
        }
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    calculateRandomAmount() {
      // 生成一个随机数，这里使用 Math.random() * 100 作为示例
      return Math.random() * 100;
    },
    // 表单重置
    reset() {
      this.form = {
        pdId: null,
        poId: null,
        pdCode: null,
        goodsId: null,
        specCode: null,
        unit: null,
        goodsType: null,
        purchaseQuantity: null,
        aiStock: null,
        niStock: null,
        returnQuantity: null,
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
      this.ids = selection.map(item => item.pdId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加进货明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const pdId = row.pdId || this.ids
      getOpdm(pdId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改进货明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pdId != null) {
            updateOpdm(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOpdm(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getOrderNum();
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const pdIds = row.pdId || this.ids;
      this.$modal.confirm('是否确认删除进货明细编号为"' + pdIds + '"的数据项？').then(function () {
        return delOpdm(pdIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('opdm/opdm/export', {
        ...this.queryParams
      }, `opdm_${new Date().getTime()}.xlsx`)
    },
    /** 查询总进货单数量 **/
    getOrderNum() {
      selectNum().then(response => {
        if (response != null) {
          this.$modal.msgSuccess("查询成功");
          this.orderNum = response.data;
        }
      }).catch(() => {
        this.$modal.msgError("查询失败");
      })
    }
  }
};
</script>
