<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!--      <el-form-item label="货品编码" prop="gCode">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.gCode"-->
      <!--          placeholder="请输入货品编码"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="货品名称" prop="gName">
        <el-input
          v-model="queryParams.gName"
          placeholder="请输入货品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="gUnit">
        <el-select v-model="queryParams.gUnit" placeholder="请选择单位" clearable>
          <el-option
            v-for="dict in dict.type.g_unit"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="排序" prop="sort">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.sort"-->
      <!--          placeholder="请输入排序"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.goods_states"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="供应商id" prop="sId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.sId"-->
      <!--          placeholder="请输入供应商id"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="规格型号" prop="specCode">
        <el-input
          v-model="queryParams.specCode"
          placeholder="请输入规格型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="出库参考价" prop="orPrice">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.orPrice"-->
      <!--          placeholder="请输入出库参考价"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="入库参考价" prop="wrPrice">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.wrPrice"-->
      <!--          placeholder="请输入入库参考价"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="保质期管理" prop="hasShelfLife">-->
      <!--        <el-select v-model="queryParams.hasShelfLife" placeholder="请选择保质期管理" clearable>-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.has_shelf_life"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item label="预警天数" prop="warningDays">
        <el-input
          v-model="queryParams.warningDays"
          placeholder="请输入预警天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="货品上限" prop="itemLimit">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.itemLimit"-->
      <!--          placeholder="请输入货品上限"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="货品下限" prop="lowerLimit">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.lowerLimit"-->
      <!--          placeholder="请输入货品下限"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="是否删除" prop="isDelte">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.isDelte"-->
      <!--          placeholder="请输入是否删除"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="保质期" prop="qualityG">
        <el-input
          v-model="queryParams.qualityG"
          placeholder="请输入保质期"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cx-ledger:shelflife:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shelflifeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="货品编码" align="center" prop="gCode" width="180"/>
      <el-table-column label="货品名称" align="center" prop="gName"/>
      <el-table-column label="货品类型" align="center" prop="gtId">
        <template slot-scope="scope">
          <span v-for="item in goodsType">
            <template v-if="scope.row.gtId===item.gtId">
              {{ item.gtName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" align="center" prop="specCode"/>
      <el-table-column label="单位" align="center" prop="gUnit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort"/>
      <el-table-column label="仓库名称" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.gId">
            {{getWname(scope.row.gId)}}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="仓位名称" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.gId">
            {{getSlname(scope.row.gId)}}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.goods_states" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="保质期管理" align="center" prop="hasShelfLife">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.has_shelf_life" :value="scope.row.hasShelfLife"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" width="110">
        <template slot-scope="scope">
          <template v-if="scope.row.qualityG.includes('天')">
                <span>{{
                    parseTime(new Date(scope.row.createTime).setDate(new Date(scope.row.createTime).getDate() + parseInt(scope.row.qualityG)), '{y}-{m}-{d}')
                  }}</span>
          </template>
          <template v-else-if="scope.row.qualityG.includes('月')">
            <span>{{
                parseTime(new Date(scope.row.createTime).setMonth(new Date(scope.row.createTime).getMonth() + parseInt(scope.row.qualityG)), '{y}-{m}-{d}')
              }}</span>
          </template>
          <template v-else-if="scope.row.qualityG.includes('年')">
            <span>{{
                parseTime(new Date(scope.row.createTime).setFullYear(new Date(scope.row.createTime).getFullYear() + parseInt(scope.row.qualityG)), '{y}-{m}-{d}')
              }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="保质期" align="center" prop="qualityG"/>
      <el-table-column label="剩余天数" align="center">
        <template slot-scope="scope">
          <template v-if="scope.row.qualityG.includes('天')">
                <span>{{
                    Math.floor((new Date(scope.row.createTime).setDate(new Date(scope.row.createTime).getDate() + parseInt(scope.row.qualityG)) - new Date()) / (1000 * 60 * 60 * 24))
                  }}</span>
          </template>
          <template v-else-if="scope.row.qualityG.includes('月')">
              <span>{{
                  Math.floor((new Date(scope.row.createTime).setMonth(new Date(scope.row.createTime).getMonth() + parseInt(scope.row.qualityG)) - new Date()) / (1000 * 60 * 60 * 24))
                }}</span>
          </template>
          <template v-else-if="scope.row.qualityG.includes('年')">
               <span>{{
                   Math.floor((new Date(scope.row.createTime).setFullYear(new Date(scope.row.createTime).getFullYear() + parseInt(scope.row.qualityG)) - new Date()) / (1000 * 60 * 60 * 24))
                 }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="预警天数" align="center" prop="warningDays"/>

      <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
      <!--        <template slot-scope="scope">-->
      <!--&lt;!&ndash;          <el-button&ndash;&gt;-->
      <!--&lt;!&ndash;            size="mini"&ndash;&gt;-->
      <!--&lt;!&ndash;            type="text"&ndash;&gt;-->
      <!--&lt;!&ndash;            icon="el-icon-edit"&ndash;&gt;-->
      <!--&lt;!&ndash;            @click="handleUpdate(scope.row)"&ndash;&gt;-->
      <!--&lt;!&ndash;            v-hasPermi="['cx-ledger:shelflife:edit']"&ndash;&gt;-->
      <!--&lt;!&ndash;          >修改&ndash;&gt;-->
      <!--&lt;!&ndash;          </el-button>&ndash;&gt;-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-delete"-->
      <!--            @click="handleDelete(scope.row)"-->
      <!--            v-hasPermi="['cx-ledger:shelflife:remove']"-->
      <!--          >删除-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {listShelflife, getShelflife, delShelflife, addShelflife, updateShelflife} from "@/api/cx-ledger/shelflife";
import {listType} from "@/api/cx-hpxx/type";
import {listWarehouse} from "@/api/cx-ckgl/warehouse";
import {listLocation} from "@/api/cx-ckgl/location";
import {listUser} from "@/api/system/user";
import {listStock} from "@/api/cx-ledger/stock";

export default {
  name: "Shelflife",
  dicts: ['has_shelf_life', 'g_unit', 'goods_states'],
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
      //货品类型数据
      goodsType: [],
      //用户数据
      userList: [],
      //仓库信息
      warehouseList: [],
      //库存信息
      stockList: [],
      //仓位信息
      locationList: [],
      // 总条数
      total: 0,
      // 保质期查询表格数据
      shelflifeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gtId: null,
        gCode: null,
        gName: null,
        gUnit: null,
        sort: null,
        status: null,
        sId: null,
        specCode: null,
        orPrice: null,
        wrPrice: null,
        hasShelfLife: null,
        warningDays: null,
        itemLimit: null,
        lowerLimit: null,
        isDelte: null,
        qualityG: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        gtId: [
          {required: true, message: "货品类型不能为空", trigger: "change"}
        ],
        gCode: [
          {required: true, message: "货品编码不能为空", trigger: "blur"}
        ],
        gName: [
          {required: true, message: "货品名称不能为空", trigger: "blur"}
        ],
        gUnit: [
          {required: true, message: "单位不能为空", trigger: "change"}
        ],
        sort: [
          {required: true, message: "排序不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        sId: [
          {required: true, message: "供应商id不能为空", trigger: "blur"}
        ],
        specCode: [
          {required: true, message: "规格型号不能为空", trigger: "blur"}
        ],
        hasShelfLife: [
          {required: true, message: "保质期管理不能为空", trigger: "change"}
        ],
        createBy: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        qualityG: [
          {required: true, message: "保质期不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getGtype();
    this.getWarehouse();
    this.getLocation();
    this.getBuyer();
    this.getStock();
  },
  methods: {
    /** 查询保质期查询列表 */
    getList() {
      this.loading = true;
      listShelflife(this.queryParams).then(response => {
        this.shelflifeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    getWname(gid){
      let wname="暂无";
      for(let item of this.stockList){
        for(let i of this.warehouseList){
          if(item.gId===gid && item.wId===i.wId){
           wname= i.wName;
          }
        }
      }
      return wname;
    },
    getSlname(gid){
      let slname="暂无";
      for(let item of this.stockList){
        for(let i of this.locationList){
          if(item.gId===gid && item.slId===i.slId){
              slname=i.slName;
          }
        }
      }
      return slname;
    },
    // 表单重置
    reset() {
      this.form = {
        gId: null,
        gtId: null,
        gCode: null,
        gName: null,
        gUnit: null,
        sort: null,
        status: null,
        sId: null,
        specCode: null,
        orPrice: null,
        wrPrice: null,
        hasShelfLife: null,
        warningDays: null,
        itemLimit: null,
        lowerLimit: null,
        createBy: null,
        createTime: null,
        isDelte: null,
        qualityG: null,
        remark: null
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
    //库存信息
    getStock() {
      listStock().then(res => this.stockList = res.rows);
    },
    //仓库信息
    getWarehouse() {
      listWarehouse().then(res => this.warehouseList = res.rows);
    },
    //用户数据
    getBuyer() {
      listUser().then(res => this.userList = res.rows);
    },
    //仓位信息
    getLocation() {
      listLocation().then(res => this.locationList = res.rows);
    },
    //货品类型数据
    getGtype() {
      listType().then(response => {
        this.goodsType = this.handleTree(response.data);
        console.log(this.goodsType)
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.gId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加保质期查询";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const gId = row.gId || this.ids
      getShelflife(gId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改保质期查询";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.gId != null) {
            updateShelflife(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShelflife(this.form).then(response => {
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
      const gIds = row.gId || this.ids;
      this.$modal.confirm('是否确认删除保质期查询编号为"' + gIds + '"的数据项？').then(function () {
        return delShelflife(gIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-ledger/shelflife/export', {
        ...this.queryParams
      }, `shelflife_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
