<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="预警天数" prop="alertDays">
        <el-input
          v-model="queryParams.alertDays"
          placeholder="请输入预警天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警负责人" prop="alertNotifier" label-width="180">
        <el-input
          v-model="queryParams.alertNotifier"
          placeholder="请输入预警负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否预警" prop="warning">
        <el-input
          v-model="queryParams.warning"
          placeholder="请输入是否预警"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警信息" prop="warningInformation">
        <el-input
          v-model="queryParams.warningInformation"
          placeholder="请输入预警信息"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['cx-ledger:information:add']"
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
          v-hasPermi="['cx-ledger:information:edit']"
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
          v-hasPermi="['cx-ledger:information:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cx-ledger:information:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="informationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预警id" align="center" prop="alertId" />
      <el-table-column label="预警天数" align="center" prop="alertDays" />
      <el-table-column label="预警负责人" align="center" prop="alertNotifier" >
        <template slot-scope="scope">
          <span v-for="item in userList" :key="item.userId">
            <template v-if="scope.row.alertNotifier===item.userId">
              {{item.nickName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="是否预警" align="center" prop="warning" >
        <template slot-scope="scope">
          <template v-if="scope.row.warning===0">
            否
          </template>
          <template v-else>
            是
          </template>
        </template>
      </el-table-column>
      <el-table-column label="预警信息" align="center" prop="warningInformation" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-ledger:information:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-ledger:information:remove']"
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

    <!-- 添加或修改库存预警对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="预警天数" prop="alertDays">
          <el-input v-model="form.alertDays" placeholder="请输入预警天数" />
        </el-form-item>
        <el-form-item label="负责人" prop="alertNotifier">
          <el-select v-model="form.alertNotifier" placeholder="请输入预警负责人" >
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否预警" prop="warning">
          <el-input v-model="form.warning" placeholder="请输入是否预警" />
        </el-form-item>
        <el-form-item label="预警信息" prop="warningInformation">
          <el-input v-model="form.warningInformation" placeholder="请输入预警信息" />
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
import { listInformation, getInformation, delInformation, addInformation, updateInformation } from "@/api/cx-ledger/information";
import {listUser} from "@/api/system/user";
export default {
  name: "Information",
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
      // 库存预警表格数据
      informationList: [],
      //用户数据
      userList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        alertDays: null,
        alertNotifier: null,
        warning: null,
        warningInformation: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getBuyer();
  },
  methods: {
    /** 查询库存预警列表 */
    getList() {
      this.loading = true;
      listInformation(this.queryParams).then(response => {
        this.informationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    //查询负责人
    getBuyer(){
      listUser().then(res=>this.userList=res.rows);
    },
    // 表单重置
    reset() {
      this.form = {
        alertId: null,
        alertDays: null,
        alertNotifier: null,
        warning: null,
        warningInformation: null
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
      this.ids = selection.map(item => item.alertId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库存预警";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const alertId = row.alertId || this.ids
      getInformation(alertId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库存预警";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.alertId != null) {
            updateInformation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInformation(this.form).then(response => {
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
      const alertIds = row.alertId || this.ids;
      this.$modal.confirm('是否确认删除库存预警编号为"' + alertIds + '"的数据项？').then(function() {
        return delInformation(alertIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-ledger/information/export', {
        ...this.queryParams
      }, `information_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
