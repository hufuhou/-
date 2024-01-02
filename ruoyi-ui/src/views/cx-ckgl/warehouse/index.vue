<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库编号" prop="wCode">
        <el-input
          v-model="queryParams.wCode"
          placeholder="请输入仓库编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="wName">
        <el-input
          v-model="queryParams.wName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="wStatus">
        <el-select v-model="queryParams.wStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.w_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库主管" prop="wSupervisor">
        <el-select v-model="queryParams.wSupervisor" placeholder="请选择主管" clearable>
          <el-option
            v-for="dict in userList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓管部门" prop="warehouseMD">
        <treeselect v-model="queryParams.warehouseMD" :options="deptOptions" :normalizer="normalizer"
                    placeholder="选择部门" style="width: 205px"/>
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
          v-hasPermi="['cx-ckgl:warehouse:add']"
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
          v-hasPermi="['cx-ckgl:warehouse:edit']"
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
          v-hasPermi="['cx-ckgl:warehouse:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-open"
          size="mini"
          :disabled="enable"
          @click="handleEnable"
          v-hasPermi="['cx-ckgl:warehouse:enable']"
        >启用
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-turn-off"
          size="mini"
          :disabled="deactivate"
          @click="handleDeactivate"
          v-hasPermi="['cx-ckgl:warehouse:deactivate']"
        >停用
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-lock"
          size="mini"
          :disabled="lock"
          @click="handleLock"
          v-hasPermi="['cx-ckgl:warehouse:lock']"
        >锁定
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['cx-ckgl:warehouse:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="仓库编号" align="center" prop="wCode" width="250px"/>
      <el-table-column label="仓库名称" align="center" prop="wName"/>
      <el-table-column label="仓库容量/m³" align="center" prop="wCapacity"/>
      <el-table-column label="状态" align="center" prop="wStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.w_status" :value="scope.row.wStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库地址" align="center" prop="wAddress" width="300px"/>
      <el-table-column label="仓管部门" align="center" prop="warehouseMD">
        <template slot-scope="scope">
          <span v-for="item in deptList">
            <template v-if="scope.row.warehouseMD===item.deptId">
              {{ item.deptName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="仓库主管" align="center" prop="wSupervisor">
        <template slot-scope="scope">
          <span v-for="item in userList1">
            <template v-if="scope.row.wSupervisor===item.userId">
              {{ item.nickName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="库位数量" align="center" prop="wId">
        <template slot-scope="scope">
    <span>
      {{ getWarehouseLocationCount(scope.row.wId) }}
    </span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="createBy">
        <template slot-scope="scope">
          <span v-for="item in userList1">
            <template v-if="parseInt(scope.row.createBy)===item.userId">
              {{ item.userName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-ckgl:warehouse:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-ckgl:warehouse:remove']"
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

    <!-- 添加或修改仓库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库编号" prop="wCode">
          <el-input v-model="form.wCode" placeholder="自动获取系统编码" :disabled="true"/>
        </el-form-item>
        <el-form-item label="仓库名称" prop="wName">
          <el-input v-model="form.wName" placeholder="请输入仓库名称"/>
        </el-form-item>
        <el-form-item label="仓库容量/m³" prop="wCapacity" label-width="100px">
          <el-input v-model="form.wCapacity" placeholder="请输入仓库容量"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序"/>
        </el-form-item>
<!--        <el-form-item label="状态" prop="wStatus">-->
<!--          <el-radio-group v-model="form.wStatus">-->
<!--            <el-radio-->
<!--              v-for="dict in dict.type.w_status"-->
<!--              :key="dict.value"-->
<!--              :label="parseInt(dict.value)"-->
<!--            >{{ dict.label }}-->
<!--            </el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
        <el-form-item label="仓库地址" prop="wAddress">
          <el-input v-model="form.wAddress" placeholder="请输入仓库地址"/>
        </el-form-item>
        <el-form-item label="仓管部门" prop="warehouseMD">
          <treeselect v-model="form.warehouseMD" :options="deptOptions" :normalizer="normalizer"
                      placeholder="选择部门"/>
        </el-form-item>
        <el-form-item label="仓库主管" prop="wSupervisor">
          <el-select v-model="form.wSupervisor" placeholder="请选择主管">
            <el-option
              v-for="dict in userList"
              :key="dict.userId"
              :label="dict.nickName"
              :value="dict.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
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
import {
  listWarehouse,
  getWarehouse,
  delWarehouse,
  addWarehouse,
  updateWarehouse,
  listDept,
  listUser,
  listLocation,
  enableWarehouse,
  deactivateWarehouse,
  lockWarehouse
} from "@/api/cx-ckgl/warehouse";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";



// 检查仓库是否存在关联的库位信息
function checkKwExist(wId) {
  return new Promise((resolve, reject) => {
    // 根据货品仓库ID查询相关的库位信息
    listLocation(wId)
      .then(response => {
        const kWList = response.rows.filter(Location => Location.wId === wId);
        const exists = kWList.length > 0;
        resolve({ exists, kWList });
      })
      .catch(error => {
        console.error(error);
        reject(error);
      });
  });
}

// 检查仓库是否存在关联的库位信息（数组中只要有一个库位信息，仓库就不能删除）
function checkKwExistForDeletion(wIdList) {
  return Promise.all(
    wIdList.map(wId => checkKwExist(wId))
  ).then(results => {
    const exists = results.some(result => result.exists);
    const kWList = results.flatMap(result => result.kWList);
    return { exists, kWList };
  });
}

export default {
  name: "Warehouse",
  dicts: ['w_status'],
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      enable: true,
      deactivate: true,
      lock: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 仓库管理表格数据
      warehouseList: [],
      //部门表格数据
      deptList: [],
      // 部门树选项
      deptOptions: [],
      //用户表格数据
      userList: [],
      userList1: [],
      //库位
      locationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wCode: null,
        wName: null,
        wCapacity: null,
        wStatus: null,
        wAddress: null,
        wSupervisor: null,
        warehouseMD: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wName: [
          {required: true, message: "仓库名称不能为空", trigger: "blur"}
        ],
        wCapacity: [
          {required: true, message: "仓库容量不能为空", trigger: "blur"}
        ],
        wStatus: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        wSupervisor: [
          {required: true, message: "仓库主管不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "操作人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "操作时间不能为空", trigger: "blur"}
        ],
        warehouseMD: [
          {required: true, message: "仓管部门不能为空", trigger: "blur"}
        ]
      },
    };
  },
  created() {
    this.getList();
    this.getBmList();
    this.getUserList();
    this.getKwList();
  },
  methods: {
    /** 查询仓库管理列表 */
    getList() {
      this.loading = true;
      listWarehouse(this.queryParams).then(response => {
        this.warehouseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门列表 */
    // getBmList() {
    //   listDept().then(response => {
    //     this.deptOptions = this.handleTree(response.data, "deptId");
    //   });
    // },
    getBmList() {
      listDept().then(response => {
        const filteredDepts = response.data.filter(dept => dept.deptId === 201 || dept.parentId === 201);
        this.deptOptions = this.handleTree(filteredDepts, "deptId");
        this.deptList = response.data;
      });
    },
    getUserList() {
      listUser().then(response => {
        this.userList = response.rows.filter(user => user.deptId === 201);
        this.userList1 = response.rows;
      });
    },
    /** 查询仓库详情列表 */
    getKwList() {
      listLocation().then(response => {
        this.locationList = response.rows;
      });
    },
    /** 获取库位数量 */
    getWarehouseLocationCount(wId) {
      // 使用filter方法筛选出属于当前仓库的库位
      const warehouseLocations = this.locationList.filter(item => item.wId === wId);
      // 返回当前仓库的库位数量
      return warehouseLocations.length;
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        wId: null,
        tId: null,
        inId: null,
        wCode: null,
        wName: null,
        wCapacity: null,
        wStatus: null,
        wAddress: null,
        isDefault: null,
        wSupervisor: null,
        remark: null,
        sort: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null,
        warehouseMD: null
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
      this.ids = selection.map(item => item.wId)
      this.names=selection.map(item=> item.wName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.enable = selection.some(item => item.wStatus ===0);
      this.deactivate = selection.some(item => item.wStatus === 1);
      this.lock = selection.some(item => item.wStatus === 2);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加仓库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const wId = row.wId || this.ids
      getWarehouse(wId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改仓库管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.wId != null) {
            updateWarehouse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWarehouse(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    // handleDelete(row) {
    //   const wIds = row.wId || this.ids;
    //   this.$modal.confirm('是否确认删除仓库管理编号为"' + wIds + '"的数据项？').then(function () {
    //     return delWarehouse(wIds);
    //   }).then(() => {
    //     this.getList();
    //     this.$modal.msgSuccess("删除成功");
    //   }).catch(() => {
    //   });
    // },
    handleDelete(row) {
      const wIds = row.wId || this.ids;
      const wName = row.wName || this.names;

      const performDelete = () => {
        this.$modal.confirm('是否确认删除仓库"' + wName + '"的数据项？')
          .then(() => {
            return delWarehouse(wIds);
          })
          .then(() => {
            this.getList();
            this.$modal.msgSuccess('删除成功');
          })
          .catch(() => {
          });
      };

      const checkExistAndDelete = (checkMethod) => {
        checkMethod(wIds)
          .then(response => {
            if (response.exists) {
              this.$modal.alert('该仓库下存在库位信息，请先删除相关库位信息。');
            } else {
              performDelete();
            }
          })
          .catch(error => {
            console.error(error);
          });
      };

      if (wIds === row.wId) {
        checkExistAndDelete(checkKwExist);
      } else {
        checkExistAndDelete(checkKwExistForDeletion);
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-ckgl/warehouse/export', {
        ...this.queryParams
      }, `warehouse_${new Date().getTime()}.xlsx`)
    },
    /** 启动按钮操作 */
    handleEnable() {
      const wIds = this.ids;
      const wName = this.names;
      this.$modal.confirm('是否启用仓库为"' + wName + '"的数据项？').then(function () {
        return enableWarehouse(wIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("启用成功");
      }).catch(() => {
      });
    },
    /** 停用按钮操作 */
    handleDeactivate() {
      const wIds =  this.ids;
      const wName =  this.names;
      this.$modal.confirm('是否停用仓库为"' + wName + '"的数据项？').then(function () {
        return deactivateWarehouse(wIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("停用成功");
      }).catch(() => {
      });
    },
    /** 锁定按钮操作 */
    handleLock() {
      const wIds = this.ids;
      const wName = this.names;
      this.$modal.confirm('是否锁定仓库为"' + wName + '"的数据项？').then(function () {
        return lockWarehouse(wIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("锁定成功");
      }).catch(() => {
      });
    },
  }
};
</script>
