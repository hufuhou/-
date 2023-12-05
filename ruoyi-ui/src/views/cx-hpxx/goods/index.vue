<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="货品类型" prop="gtId" >
          <treeselect v-model="queryParams.gtId" :options="typeOptions" :normalizer="normalizer" placeholder="请选择类型"  style="width: 205px"/>
      </el-form-item>
      <el-form-item label="货品编码" prop="gCode" >
        <el-input
          v-model="queryParams.gCode"
          placeholder="请输入货品编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['cx-hpxx:goods:add']"
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
          v-hasPermi="['cx-hpxx:goods:edit']"
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
          v-hasPermi="['cx-hpxx:goods:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cx-hpxx:goods:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sortedDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="货品类型" align="center" prop="gtId" >
        <template slot-scope="scope">
          <span v-for="item in goodsType">
            <template v-if="scope.row.gtId===item.gtId">
              {{item.gtName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="货品编码" align="center" prop="gCode" width="250" />
      <el-table-column label="货品名称" align="center" prop="gName" />
      <el-table-column label="单位" align="center" prop="gUnit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.g_unit" :value="scope.row.gUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.goods_states" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" align="center" prop="specCode" />
      <el-table-column label="供应商" align="center" prop="sId" >
        <template slot-scope="scope">
          <span v-for="item in supplierList">
            <template v-if="scope.row.sId===item.sId">
              {{item.sName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="出库参考价/元" align="center" prop="orPrice" width="105"/>
      <el-table-column label="入库参考价/元" align="center" prop="wrPrice" width="105"/>
      <el-table-column label="保质期管理" align="center" prop="hasShelfLife" width="85">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.has_shelf_life" :value="scope.row.hasShelfLife"/>
        </template>
      </el-table-column>
      <el-table-column label="预警天数" align="center" prop="warningDays" />
<!--      <el-table-column label="货品上限" align="center" prop="itemLimit" />-->
<!--      <el-table-column label="货品下限" align="center" prop="lowerLimit" />-->
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="保质期" align="center" prop="qualityG" />
      <el-table-column label="操作" align="center" width="155" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-hpxx:goods:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-hpxx:goods:remove']"
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

    <!-- 添加或修改货品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="99px">
        <el-form-item label="货品编码" prop="gCode" >
          <el-input v-model="form.gCode" placeholder="自动获取系统编码" :disabled="true" />
        </el-form-item>
        <el-form-item label="货品类型" prop="gtId" >
          <treeselect v-model="form.gtId" :options="typeOptions" :normalizer="normalizer" placeholder="请选择类型"  style="width: 205px"/>
        </el-form-item>
        <el-form-item label="货品名称" prop="gName">
          <el-input v-model="form.gName" placeholder="请输入货品名称" />
        </el-form-item>
        <el-form-item label="单位" prop="gUnit">
          <el-select v-model="form.gUnit" placeholder="请选择单位">
            <el-option
              v-for="dict in dict.type.g_unit"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.goods_states"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="规格型号" prop="specCode">
          <el-input v-model="form.specCode" placeholder="请输入规格型号" />
        </el-form-item>
        <el-form-item label="供应商" prop="sId">
          <el-select v-model="form.sId" placeholder="请选择供应商" >
            <el-option
              v-for="dict in supplierList"
              :key="dict.sId"
              :label="dict.sName"
              :value="dict.sId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="出库参考价" prop="orPrice">
          <el-input v-model="form.orPrice" placeholder="请输入出库参考价" />
        </el-form-item>
        <el-form-item label="入库参考价" prop="wrPrice">
          <el-input v-model="form.wrPrice" placeholder="请输入入库参考价" />
        </el-form-item>
        <el-form-item label="保质期管理" prop="hasShelfLife">
          <el-select v-model="form.hasShelfLife" placeholder="请选择保质期管理" @change="handleShelfLifeChange">
            <el-option
              v-for="dict in dict.type.has_shelf_life"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="保质期" prop="qualityG" v-if="showQualityG">
          <el-input v-model="form.qualityG" placeholder="请输入保质期" />
        </el-form-item>
        <el-form-item label="预警天数" prop="warningDays">
          <el-input v-model="form.warningDays" placeholder="请输入预警天数" />
        </el-form-item>
        <el-form-item label="货品上限" prop="itemLimit">
          <el-input v-model="form.itemLimit" placeholder="请输入货品上限" />
        </el-form-item>
        <el-form-item label="货品下限" prop="lowerLimit">
          <el-input v-model="form.lowerLimit" placeholder="请输入货品下限" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listGoods, getGoods, delGoods, addGoods, updateGoods ,listType,listSupplier} from "@/api/cx-hpxx/goods";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Goods",
  dicts: ['has_shelf_life', 'g_unit', 'goods_states'],
  computed: {
    sortedDataList() {
      return this.goodsList.slice().sort((a, b) => a.sort - b.sort);
    }
  },
  components: { Treeselect },
  data() {
    return {
      // 默认显示 "保质期" 文本框
      showQualityG: true,
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
      // 货品信息表格数据
      goodsList: [],
      goodsType: [],
      typeOptions:[],
      //供应商
      supplierList:[],
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
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        gtId: [
          { required: true, message: "货品类型不能为空", trigger: "blur" }
        ],
        gName: [
          { required: true, message: "货品名称不能为空", trigger: "blur" }
        ],
        sId: [
          { required: true, message: "供应商不能为空", trigger: "blur" }
        ],
        gUnit: [
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        sort: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        specCode: [
          { required: true, message: "规格型号不能为空", trigger: "blur" }
        ],
        hasShelfLife: [
          { required: true, message: "保质期管理不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getType();
    this.getSupplier();
  },
  methods: {
    /** 查询货品信息列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then(response => {
        this.goodsList = response.rows;
        this.loading = false;
      });
    },
    /** 查询货品类型列表 */
    getType() {
      listType().then(response => {
        this.goodsType = this.handleTree(response.data);
        this.typeOptions = [];
        const data = { gtId: 0, gtName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "gtId", "parentId");
        this.typeOptions.push(data);
      });
    },
    /** 转换类型数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.gtId,
        label: node.gtName,
        children: node.children
      };
    },
    /** 查询供应商列表列表 */
    getSupplier() {
      listSupplier().then(response => {
        this.supplierList = response.rows;
      });
    },
    /** 保质期 */
    handleShelfLifeChange() {
      if (this.form.hasShelfLife === 1) {
        this.showQualityG = false; // 当选择停用时，隐藏 "保质期" 文本框
      } else {
        this.showQualityG = true; // 其他情况下显示 "保质期" 文本框
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
        specCode: null,
        sId: null,
        remark: null,
        orPrice: null,
        wrPrice: null,
        hasShelfLife: null,
        warningDays: null,
        itemLimit: null,
        lowerLimit: null,
        createBy: null,
        createTime: null,
        isDelte: null,
        qualityG: null
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
      this.ids = selection.map(item => item.gId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加货品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const gId = row.gId || this.ids
      getGoods(gId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改货品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.gId != null) {
            updateGoods(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGoods(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除货品"' + row.gName + '"的数据项？').then(function() {
        return delGoods(gIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-hpxx/goods/export', {
        ...this.queryParams
      }, `goods_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
