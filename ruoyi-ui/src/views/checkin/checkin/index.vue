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
      <el-form-item label="入库单号" prop="inCode">
        <el-input
          v-model="queryParams.inCode"
          placeholder="请输入入库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产日期" prop="productionDate">
        <el-date-picker clearable
          v-model="queryParams.productionDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择生产日期">
        </el-date-picker>
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
      <el-form-item label="修改人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入修改人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="修改时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择修改时间">
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
      <el-col :span="4"> <!-- 第一个板块 -->
        <el-card shadow="hover" style="text-align: center">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ orderNum }}</p>
            <p>入库单数量</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" style="text-align: center"> <!-- 第二个板块 -->
        <el-card shadow="hover">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ "￥" + orderNum * 100 }}</p>
            <p>入库货品金额</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" style="text-align: center"> <!-- 第三个板块 -->
        <el-card shadow="hover">
          <div>
            <p style="color: #00afff;font-size: 35px;line-height: 1px">{{ checkinProductionNum }}</p>
            <p>入库货品数量</p>
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
          v-hasPermi="['checkin:checkin:add']"
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
          v-hasPermi="['checkin:checkin:edit']"
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
          v-hasPermi="['checkin:checkin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['checkin:checkin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkinList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="入库明细单ID" align="center" prop="imdId" />-->
<!--      <el-table-column label="入库ID" align="center" prop="inId" />-->
<!--      <el-table-column label="入库单号" align="center" prop="inCode" />-->
<!--      <el-table-column label="明细ID" align="center" prop="odId" />-->
<!--      <el-table-column label="单位" align="center" prop="unit" />-->
      <el-table-column label="入库仓库" align="center" prop="warehouseName" />
      <el-table-column label="入库仓位" align="center" prop="location" />
      <el-table-column label="本次入库数" align="center" prop="thisQuantity" />
      <el-table-column label="已入库数" align="center" prop="quantityInStock" />
      <el-table-column label="未入库数" align="center" prop="unstockedQuantity" />
      <el-table-column label="货品" align="center" prop="goods_name"/>
      <el-table-column label="生产日期" align="center" prop="productionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="条形码" align="center" prop="barcode" />-->
      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          {{ scope.row.remark === null ||  scope.row.remark === " " ? '暂无备注' : scope.row.remark }}
        </template>
      </el-table-column>
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
            v-hasPermi="['checkin:checkin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['checkin:checkin:remove']"
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

    <!-- 添加或修改入库明细对话框 -->
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
  listCheckin,
  getCheckin,
  delCheckin,
  addCheckin,
  updateCheckin,
  findCheckNmu,
  listCheckinWithUser, findInfoByDateCheckin
} from "@/api/checkin/checkin";
import {parseTime} from "@/utils/ruoyi";
import {selectDataParam} from "@/api/opdm/opdm";


export default {
  name: "Checkin",
  data() {
    return {
      //今日 or 昨日
      TodayOrYesterday: "",
      //周,月开始日
      BeginDay: "",
      //周,月结束日
      EndDay: "",
      //进货产品数量
      checkinProductionNum : 0,
      //订单总数
      orderNum : 0,
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
      // 入库明细表格数据
      checkinList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inId: null,
        inCode: null,
        odId: null,
        unit: null,
        productionDate: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        inId: [
          { required: true, message: "入库ID不能为空", trigger: "blur" }
        ],
        inCode: [
          { required: true, message: "入库单号不能为空", trigger: "blur" }
        ],
        odId: [
          { required: true, message: "明细ID不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        quantityInStock: [
          { required: true, message: "已入库数不能为空", trigger: "blur" }
        ],
        unstockedQuantity: [
          { required: true, message: "未入库数不能为空", trigger: "blur" }
        ],
        slId: [
          { required: true, message: "入库仓位不能为空", trigger: "blur" }
        ],
        thisQuantity: [
          { required: true, message: "本次入库数不能为空", trigger: "blur" }
        ],
        productionDate: [
          { required: true, message: "生产日期不能为空", trigger: "blur" }
        ],
        barcode: [
          { required: true, message: "条形码不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateBy: [
          { required: true, message: "修改人不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "修改时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    //this.getCheckinNumber();
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
            this.$modal.msgSuccess(this.TodayOrYesterday);
          } else if (Array.isArray(response.data)) {
            // 如果是数组
            this.BeginDay = response.data[0];
            this.EndDay = response.data[1];
            this.$modal.msgSuccess("BeginDay: " + this.BeginDay + ",EndDay : " + this.EndDay);
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
        findInfoByDateCheckin(DateParams)
          .then(response => {
            // 处理响应
            console.info(response)
            this.$set(this, 'checkinList', response.data);
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
    /** 查询入库明细列表 */
    getList() {
      this.loading = true;
      listCheckinWithUser(this.queryParams).then(response => {
        this.checkinList = response.rows;
        this.total = response.total;
        //总进货金额
        this.orderNum = this.total;
        this.checkinProductionNum = response.rows.reduce((total, row) => total + row.thisQuantity, 0);
        this.loading = false;
      });
    },
    //查询入库单总数
    getCheckinNumber(){
      this.loading = true;
      findCheckNmu().then(response =>{
        this.orderNum = response.data;
        this.loading = false;
        this.$modal.msgSuccess("查询成功");
      }).catch(error=>{
        this.$modal.msgError(error);
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
        imdId: null,
        inId: null,
        inCode: null,
        odId: null,
        unit: null,
        quantityInStock: null,
        unstockedQuantity: null,
        slId: null,
        thisQuantity: null,
        productionDate: null,
        barcode: null,
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
      this.ids = selection.map(item => item.imdId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入库明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const imdId = row.imdId || this.ids
      getCheckin(imdId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改入库明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.imdId != null) {
            updateCheckin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckin(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getCheckinNumber();
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const imdIds = row.imdId || this.ids;
      this.$modal.confirm('是否确认删除入库明细编号为"' + imdIds + '"的数据项？').then(function() {
        return delCheckin(imdIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('checkin/checkin/export', {
        ...this.queryParams
      }, `checkin_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
