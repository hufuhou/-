<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库id" prop="wId">
        <el-select
          v-model="queryParams.wId"
          placeholder="请输入仓库id"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="item in warehouseList"
            :key="item.wId"
            :label="item.wName"
            :value="item.wId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="库位id" prop="slId">
        <el-select
          v-model="queryParams.slId"
          placeholder="请输入库位id"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="item in locationList"
            :key="item.slId"
            :label="item.slName"
            :value="item.slId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="货品数量" prop="itemQuantity">
        <el-input
          v-model="queryParams.itemQuantity"
          placeholder="请输入货品数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划数量" prop="numberPlans">
        <el-input
          v-model="queryParams.numberPlans"
          placeholder="请输入计划数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货品id" prop="gId">
        <el-select
          v-model="queryParams.gId"
          placeholder="请输入货品id"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="item in goodsList"
            :key="item.gId"
            :label="item.gName"
            :value="item.gId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createBys">
        <el-select
          v-model="queryParams.createBys"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="状态" prop="isDelte">-->
<!--        <el-input-->
<!--          v-model="queryParams.isDelte"-->
<!--          placeholder="请输入0：启用；1：停用"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['cx-ledger:stock:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['cx-ledger:stock:edit']"-->
      <!--        >修改-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['cx-ledger:stock:remove']"
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
          v-hasPermi="['cx-ledger:stock:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="stockList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="货品编码" align="center" width="175" prop="gId">
        <template slot-scope="scope">
        <span v-for="item in goodsList">
          <template v-if="scope.row.gId===item.gId">
             {{ item.gCode }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="货品名称" align="center">
        <template slot-scope="scope">
        <span v-for="item in goodsList">
          <template v-if="scope.row.gId===item.gId">
             {{ item.gName }}
          </template>
        </span>
        </template>
      </el-table-column>
      <el-table-column label="货品类型" align="center">
        <template slot-scope="scope">
          <span v-for="item in goodsType">
            <span v-for="i in goodsList">
              <template v-if="scope.row.gId===i.gId && item.gtId===i.gtId">
               {{ item.gtName }}
             </template>
            </span>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="center">
        <template slot-scope="scope">
          <span v-for="item in warehouseList">
            <template v-if="scope.row.wId===item.wId">
              {{ item.wName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" align="center">
        <template slot-scope="scope">
            <span v-for="i in goodsList">
              <template v-if="scope.row.gId===i.gId ">
               {{ i.specCode }}
             </template>
            </span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center">
        <template slot-scope="scope">
          <template v-for="i in goodsList">
            <template v-if="scope.row.gId===i.gId">
              <dict-tag :options="dict.type.g_unit" :value="i.gUnit"/>
            </template>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="仓位名称" align="center">
        <template slot-scope="scope">
          <span v-for="item in locationList">
            <template v-if="scope.row.slId===item.slId">
              {{ item.slName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="货品数量" align="center" prop="itemQuantity"/>
      <el-table-column label="计划数量" align="center" prop="numberPlans"/>
      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          <div :class="['truncate-text', isOverLength(scope.row.remark)]">
            {{ scope.row.remark }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBys">
        <template slot-scope="scope">
          <span v-for="item in userList" :key="item.userId">
            <template v-if="scope.row.createBys===item.userId">
              {{ item.nickName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-edit"-->
          <!--            @click="handleUpdate(scope.row)"-->
          <!--            v-hasPermi="['cx-ledger:stock:edit']"-->
          <!--          >修改-->
          <!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-ledger:stock:remove']"
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

    <!-- 添加或修改库存查询对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库id" prop="wId">
          <el-select v-model="form.wId" placeholder="请输入仓库id">
            <el-option
              v-for="item in warehouseList"
              :key="item.wId"
              :label="item.wName"
              :value="item.wId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库位id" prop="slId">
          <el-select v-model="form.slId" placeholder="请输入库位id">
            <el-option
              v-for="item in locationList"
              :key="item.slId"
              :label="item.slName"
              :value="item.slId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货品数量" prop="itemQuantity">
          <el-input v-model="form.itemQuantity" placeholder="请输入货品数量"/>
        </el-form-item>
        <el-form-item label="计划数量" prop="numberPlans">
          <el-input v-model="form.numberPlans" placeholder="请输入计划数量"/>
        </el-form-item>
        <el-form-item label="货品id" prop="gId">
          <el-select v-model="form.gId" placeholder="请输入货品id">
            <el-option
              v-for="item in goodsList"
              :key="item.gId"
              :label="item.gName"
              :value="item.gId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
        <el-form-item label="创建人" prop="createBys">
          <el-select v-model="form.createBys" placeholder="请输入创建人">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="isDelte">
          <el-input v-model="form.isDelte" placeholder="请输入0：存在；1：已删除，不存在"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style>
.truncate-text {
  width: 60px; /* 自定义宽度 */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
<script>
import {listStock, getStock, delStock, addStock, updateStock,} from "@/api/cx-ledger/stock";
import {listUser} from "@/api/system/user";
import {listGoods} from "@/api/cx-hpxx/goods";
import {listType} from "@/api/cx-hpxx/type";
import {listWarehouse} from "@/api/cx-ckgl/warehouse";
import {listLocation} from "@/api/cx-ckgl/location";
import Config from "@/views/system/config";

export default {
  name: "Stock",
  dicts: ['g_unit'],
  components: {Config},
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
      //用户数据
      userList: [],
      //货品数据
      goodsList: [],
      //货品类型数据
      goodsType: [],
      //仓库信息
      warehouseList: [],
      //仓位信息
      locationList: [],
      // 库存查询表格数据
      stockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wId: null,
        slId: null,
        itemQuantity: null,
        numberPlans: null,
        gId: null,
        remark: null,
        createBys: null,
        createTime: null,
        isDelte: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wId: [
          {required: true, message: "仓库id不能为空", trigger: "blur"}
        ],
        slId: [
          {required: true, message: "库位id不能为空", trigger: "blur"}
        ],
        itemQuantity: [
          {required: true, message: "货品数量不能为空", trigger: "blur"}
        ],
        numberPlans: [
          {required: true, message: "计划数量不能为空", trigger: "blur"}
        ],
        gId: [
          {required: true, message: "货品id不能为空", trigger: "blur"}
        ],
        createBys: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        isDelte: [
          {required: true, message: "状态不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getBuyer();
    this.getGoods();
    this.getGtype();
    this.getWarehouse();
    this.getLocation();
  },
  methods: {
    /** 查询库存查询列表 */
    getList() {
      this.loading = true;
      listStock(this.queryParams).then(response => {
        this.stockList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    isOverLength(text) {
      const maxLength = 10; // 自定义最大长度
      return text.length > maxLength;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    //仓库信息
    getWarehouse() {
      listWarehouse().then(res => this.warehouseList = res.rows);
    },
    //仓位信息
    getLocation() {
      listLocation().then(res => this.locationList = res.rows);
    },
    //货品数据
    getGoods() {
      listGoods().then(res => this.goodsList = res.rows);
    },
    //用户数据
    getBuyer() {
      listUser().then(res => this.userList = res.rows);
    },
    //货品类型数据
    getGtype() {
      listType().then(response => {
        this.goodsType = this.handleTree(response.data);
        console.log(this.goodsType)
      });
    },
    // 表单重置
    reset() {
      this.form = {
        sId: null,
        wId: null,
        slId: null,
        itemQuantity: null,
        numberPlans: null,
        gId: null,
        remark: null,
        createBys: null,
        createTime: null,
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
      this.ids = selection.map(item => item.sId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库存查询";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sId = row.sId || this.ids
      getStock(sId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库存查询";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sId != null) {
            updateStock(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStock(this.form).then(response => {
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
      const sIds = row.sId || this.ids;
      this.$modal.confirm('是否确认删除库存查询编号为"' + sIds + '"的数据项？').then(function () {
        return delStock(sIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-ledger/stock/export', {
        ...this.queryParams
      }, `stock_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
