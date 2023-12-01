<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库名称" prop="wId">
        <el-select v-model="queryParams.wId" placeholder="请选择仓库" clearable>
          <el-option
            v-for="dict in warehouseList"
            :key="dict.wId"
            :label="dict.wName"
            :value="dict.wId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库位编码" prop="slCode">
        <el-input
          v-model="queryParams.slCode"
          placeholder="请输入库位编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库位名称" prop="slName">
        <el-input
          v-model="queryParams.slName"
          placeholder="请输入库位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓位主管" prop="positionManager">
        <el-select v-model="queryParams.positionManager" placeholder="请选择主管" clearable>
          <el-option
            v-for="dict in userList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.kw_status"
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
          v-hasPermi="['cx-ckgl:location:add']"
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
          v-hasPermi="['cx-ckgl:location:edit']"
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
          v-hasPermi="['cx-ckgl:location:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cx-ckgl:location:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sortedDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库位编码" align="center" prop="slCode" width="250px"/>
      <el-table-column label="库位名称" align="center" prop="slName" />
      <el-table-column label="库位容量/m³" align="center" prop="locationCapacity" />
      <el-table-column label="仓位主管" align="center" prop="positionManager" >
        <template slot-scope="scope">
          <span v-for="item in userList1">
            <template v-if="scope.row.positionManager===item.userId">
              {{item.nickName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.kw_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-ckgl:location:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-ckgl:location:remove']"
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

    <template>
    <div class="table-container" style=" margin-top: 100px;">
    <el-table :data="[selectedWarehouse]" >
      <el-table-column label="仓库名称" align="center" prop="wName" />
      <el-table-column label="仓库编号" align="center" prop="wCode" width="230px">
        <template slot-scope="scope">
          <div class="copy-container">
            <span>{{ scope.row.wCode }}</span>
            <i class="el-icon-document-copy copy-icon" @click="copyText(scope.row.wCode)"></i>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="仓库容量/m³" align="center" prop="wCapacity" />
      <el-table-column label="状态" align="center" prop="wStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.w_status" :value="scope.row.wStatus" />
        </template>
      </el-table-column>
      <el-table-column label="库位数量" align="center" >
        <template slot-scope="scope">
    <span>
      {{ getWarehouseLocationCount(scope.row.wId) }}
    </span>
        </template>
      </el-table-column>
      <el-table-column label="仓库地址" align="center" prop="wAddress" width="300px"/>
      <el-table-column label="仓管部门" align="center" prop="warehouseMD" >
        <template slot-scope="scope">
          <span v-for="item in deptList">
            <template v-if="scope.row.warehouseMD===item.deptId">
              {{item.deptName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="仓库主管" align="center" prop="wSupervisor" >
        <template slot-scope="scope">
          <span v-for="item in userList1">
            <template v-if="scope.row.wSupervisor===item.userId">
              {{item.nickName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" >
        <template slot-scope="scope">
          <span v-for="item in userList1">
            <template v-if="parseInt(scope.row.createBy)===item.userId">
              {{item.userName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="updateBy" >
        <template slot-scope="scope">
          <span v-for="item in userList1">
            <template v-if="parseInt(scope.row.updateBy)===item.userId">
              {{item.userName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
    </el-table>
    </div>
    </template>



    <!-- 添加或修改仓库详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="库位编码" prop="slCode">
          <el-input v-model="form.slCode" placeholder="自动获取系统编码" :disabled="true"/>
        </el-form-item>
        <el-form-item label="仓库名称" prop="wId">
          <el-select v-model="form.wId" placeholder="请选择仓库" >
            <el-option
              v-for="dict in warehouseList"
              :key="dict.wId"
              :label="dict.wName"
              :value="dict.wId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="库位名称" prop="slName">
          <el-input v-model="form.slName" placeholder="请输入库位名称" />
        </el-form-item>
        <el-form-item label="库位容量/m³" prop="locationCapacity" label-width="100px">
          <el-input v-model="form.locationCapacity" placeholder="请输入库位容量/立方" />
        </el-form-item>
        <el-form-item label="仓位主管" prop="positionManager">
          <el-select v-model="form.positionManager" placeholder="请选择仓位主管" >
            <el-option
              v-for="dict in userList"
              :key="dict.userId"
              :label="dict.nickName"
              :value="dict.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.kw_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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
<style>
.copy-container {
  display: flex;
  align-items: center;
}

.copy-icon {
  margin-left: 5px;
  cursor: pointer;
}
</style>
<script>

import { listLocation, getLocation, delLocation, addLocation, updateLocation,listUser,listWarehouse,listDept } from "@/api/cx-ckgl/location";

export default {
  name: "Location",
  watch: {
    'queryParams.wId'(newVal) {
      this.selectedWarehouse = this.warehouseList.find(warehouse => warehouse.wId === newVal);
    }
  },
  computed: {
    sortedDataList() {
      return this.locationList.slice().sort((a, b) => a.sort - b.sort);
    }
  },
  dicts: ['kw_status','w_status'],

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
      // 仓库详情表格数据
      locationList: [],
      //用户表格数据
      userList: [],
      userList1: [],
      //部门
      deptList:[],
      // 仓库管理表格数据
      warehouseList: [],
      // 选择的仓库信息
      selectedWarehouse: null ,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wId: null,
        slCode: null,
        slName: null,
        positionManager: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wId: [
          {required: true, message: "仓库名称不能为空", trigger: "blur"}
        ],
        slName: [
          {required: true, message: "库位名称不能为空", trigger: "blur"}
        ],
        locationCapacity: [
          {required: true, message: "库位容量不能为空", trigger: "blur"}
        ],
        positionManager: [
          {required: true, message: "仓位主管不能为空", trigger: "blur"}
        ],
        sort: [
          {required: true, message: "排序不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getUserList();
    this.getWList();
    this.getBmList();
  },
  methods: {
    /** 查询仓库详情列表 */
    getList() {
      this.loading = true;
      listLocation(this.queryParams).then(response => {
        this.locationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询用户列表 */
    getUserList() {
      listUser().then(response => {
        this.userList = response.rows.filter(user => user.deptId === 201);
        this.userList1= response.rows;
      });
  },
    /** 查询仓库管理列表 */
    getWList() {
      listWarehouse().then(response => {
        this.warehouseList = response.rows;
        if (this.warehouseList.length > 0) {
          this.selectedWarehouse = this.warehouseList[0];
        }
      });
    },
    //部门
    getBmList() {
      listDept().then(response => {
        this.deptList = response.data;
      });
    },
    /** 获取库位数量 */
    getWarehouseLocationCount(wId) {
      // 使用filter方法筛选出属于当前仓库的库位
      const warehouseLocations = this.locationList.filter(item => item.wId === wId);
      // 返回当前仓库的库位数量
      return warehouseLocations.length;
    },
    copyText(text) {
      const el = document.createElement('textarea');
      el.value = text;
      document.body.appendChild(el);
      el.select();
      document.execCommand('copy');
      document.body.removeChild(el);
      // 可以添加一些提示或反馈，表示复制成功
      console.log('信息已复制:', text);
    },

  // 取消按钮
  cancel() {
    this.open = false;
    this.reset();
  },
  // 表单重置
  reset() {
    this.form = {
      slId: null,
      wId: null,
      slCode: null,
      slName: null,
      locationCapacity: null,
      positionManager: null,
      sort: null,
      status: null,
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
    this.ids = selection.map(item => item.slId)
    this.single = selection.length !== 1
    this.multiple = !selection.length
  },
  /** 新增按钮操作 */
  handleAdd() {
    this.reset();
    this.open = true;
    this.title = "添加仓库详情";
  },
  /** 修改按钮操作 */
  handleUpdate(row) {
    this.reset();
    const slId = row.slId || this.ids
    getLocation(slId).then(response => {
      this.form = response.data;
      this.open = true;
      this.title = "修改仓库详情";
    });
  },
  /** 提交按钮 */
  submitForm() {
    this.$refs["form"].validate(valid => {
      if (valid) {
        if (this.form.slId != null) {
          updateLocation(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        } else {
          addLocation(this.form).then(response => {
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
    const slIds = row.slId || this.ids;
    this.$modal.confirm('是否确认删除库位"' + row.slName + '"的数据项？').then(function () {
      return delLocation(slIds);
    }).then(() => {
      this.getList();
      this.$modal.msgSuccess("删除成功");
    }).catch(() => {
    });
  },
  /** 导出按钮操作 */
  handleExport() {
    this.download('cx-ckgl/location/export', {
      ...this.queryParams
    }, `location_${new Date().getTime()}.xlsx`)
  }
}
};
</script>
