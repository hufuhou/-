<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="调拨单号" prop="tdCode">
        <el-input
          v-model="queryParams.tdCode"
          placeholder="请输入调拨单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="调拨日期" prop="tDate">
        <el-date-picker clearable
                        v-model="queryParams.tDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择调拨申请日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="调出仓库" prop="outWId">
        <el-select
          v-model="queryParams.outWId"
          placeholder="请输入调出仓库"
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
      <el-form-item label="调入仓库" prop="inWId">
        <el-select
          v-model="queryParams.inWId"
          placeholder="请输入调入仓库"
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
      <el-form-item label="经办人" prop="tManager">
        <el-select
          v-model="queryParams.tManager"
          placeholder="请输入经办人"
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
          v-hasPermi="['cx-crkgl:transfer:add']"
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
          v-hasPermi="['cx-crkgl:transfer:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-check"
          size="mini"
          :disabled="single"
          @click="handleExam"
          v-hasPermi="['cx-crkgl:transfer:edit']"
        >审核
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-close"
          size="mini"
          :disabled="single"
          @click="revokeExam"
          v-hasPermi="['cx-crkgl:transfer:edit']"
        >撤销
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
          v-hasPermi="['cx-crkgl:transfer:remove']"
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
          v-hasPermi="['cx-crkgl:transfer:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="调拨ID" align="center" prop="tId" />-->
      <el-table-column label="调拨单号" align="center" prop="tdCode" fixed="left"/>
      <el-table-column label="单据状态" align="center" prop="docStatus" fixed="left">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.doc_status" :value="scope.row.docStatus"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="调拨申请日期" align="center" prop="tDate" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.tDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="调出仓库" align="center" prop="outWId">
        <template slot-scope="scope">
          <span v-for="item in warehouseList">
            <template v-if="scope.row.outWId==item.wId">
              {{ item.wName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="调入仓库" align="center" prop="inWId">
        <template slot-scope="scope">
          <span v-for="item in warehouseList">
            <template v-if="scope.row.inWId == item.wId">
              {{ item.wName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="出库状态" align="center" prop="outStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.out_status" :value="scope.row.outStatus"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="入库状态" align="center" prop="inStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.in_status" :value="scope.row.inStatus"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="调拨类型" align="center" prop="tType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_type" :value="scope.row.tType"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="经办人" align="center" prop="tManager">
        <template slot-scope="scope">
          <span v-for="item in userList">
            <template v-if="scope.row.tManager==item.userId">
              {{ item.nickName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="制单人" align="center" prop="createBy">
        <template slot-scope="scope">
          <span v-for="item in userList">
            <template v-if="scope.row.createBy==item.userId">
              {{ item.nickName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="制单时间" align="center" prop="createTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核人" align="center" prop="updateBy">
        <template slot-scope="scope">
          <span v-for="item in userList">
            <template v-if="scope.row.updateBy==item.userId">
              {{ item.nickName }}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="审核时间" align="center" prop="updateTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cx-crkgl:transfer:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cx-crkgl:transfer:remove']"
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

    <!-- 添加或修改库存调拨对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="调拨单号" prop="tdCode">
          <el-input v-model="form.tdCode" placeholder="自动获取系统编码" :disabled="true"/>
        </el-form-item>
        <el-form-item label="单据状态" prop="docStatus">
          <el-select v-model="form.docStatus" placeholder="请选择单据状态" :disabled="isDe1">
            <el-option
              v-for="dict in dict.type.doc_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调拨类型" prop="tType">
          <el-select v-model="form.tType" placeholder="请选择调拨类型" :disabled="isDe2">
            <el-option
              v-for="dict in dict.type.t_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调拨日期" prop="tDate">
          <el-date-picker clearable
                          v-model="form.tDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择调拨申请日期" :disabled="isDe2">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="调出仓库" prop="outWId">
          <el-select v-model="form.outWId" placeholder="请输入调出仓库" :disabled="isDe2" clearable
                     @change="trOutbound">
            <el-option
              v-for="item in warehouseList"
              :key="item.wId"
              :label="item.wName"
              :value="item.wId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调入仓库" prop="inWId">
          <el-select v-model="form.inWId" placeholder="请输入调入仓库" :disabled="isDe2" clearable
                     @change="trWarehousing">
            <el-option
              v-for="item in warehouseList"
              :key="item.wId"
              :label="item.wName"
              :value="item.wId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入库状态" prop="inStatus">
          <el-select v-model="form.inStatus" placeholder="请选择调拨类型" :disabled="isDe2">
            <el-option
              v-for="dict in dict.type.in_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出库状态" prop="outStatus">
          <el-select v-model="form.outStatus" placeholder="请选择调拨类型" :disabled="isDe2">
            <el-option
              v-for="dict in dict.type.out_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="经办人" prop="tManager">
          <el-select v-model="form.tManager" placeholder="请输入经办人" :disabled="isDe2">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="制单人" prop="createBy">
          <el-select v-model="form.createBy" placeholder="请输入制单人" :disabled="isDe2">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">调拨明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="addGoods" :disabled="isDe2">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteCrkTransferDetails" :disabled="isDe2">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table v-loading="goodsLoading" :data="crkTransferDetailsList" :row-class-name="rowCrkTransferDetailsIndex"
                  @selection-change="handleCrkTransferDetailsSelectionChange" ref="crkTransferDetails">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="货品名称" align="center">
            <template slot-scope="scope">
              <span v-for="item in goodsList">
                <template v-if="scope.row.goodsId===item.gId">
                  {{ item.gName }}
                </template>
              </span>
            </template>
          </el-table-column>
          <el-table-column label="货品编号" width="75" align="center">
            <template slot-scope="scope">
              <span v-for="item in goodsList">
                <template v-if="scope.row.goodsId===item.gId">
                  {{ item.gCode }}
                </template>
              </span>
            </template>
          </el-table-column>
          <el-table-column label="规格型号" align="center" prop="specCode"/>
          <el-table-column label="单位" align="center" prop="unit">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit" :value="scope.row.unit"/>
            </template>
          </el-table-column>
          <el-table-column label="货品类型" align="center" prop="gtId">
            <template slot-scope="scope">
              <span v-for="i in goodsList">
                  <span v-for="item in goodsType">
                   <template v-if="scope.row.goodsId===i.gId && i.gtId===item.gtId">
                        {{ item.gtName }}
                   </template>
                  </span>
              </span>
            </template>
          </el-table-column>
          <el-table-column label="货品数量" align="center" prop="tQuantity" width="120">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.tQuantity" size="mini"
                               @input="calculateTotal(scope.row)" :min="1" :max="1000" label="描述文字"
                               style="width: 100px"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="进货单价" align="center" prop="iuPrice"/>

          <el-table-column label="金额" align="center" width="90">
            <template slot-scope="scope">
              {{
                (scope.row.tQuantity && scope.row.iuPrice) ? (scope.row.tQuantity * scope.row.iuPrice).toFixed(2) : 0
              }}
            </template>
          </el-table-column>
          <el-table-column label="入库库位id" prop="inSlid" width="130">
            <template slot-scope="scope">
              <el-select v-model="scope.row.inSlid" placeholder="请输入库位">
                <el-option
                  v-for="item in locationName"
                  :key="item.slId"
                  :label="item.slName"
                  :value="item.slId"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="removeRow(scope.$index)"
              >移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="选择产品" :visible.sync="openGoodsList"
               append-to-body style="width: 1500px;margin: auto"
               @close="closeGoods">
      <el-form :model="goodsQueryParams" ref="queryGoodsForm"
               size="small" :inline="true" v-show="showSearch"
               label-width="68px">
        <el-form-item label="货品编号" prop="gCode">
          <el-input
            v-model="goodsQueryParams.gCode"
            placeholder="请输入货品编号"
            clearable
            @keyup.enter.native="handleGoodsQuery"
          />
        </el-form-item>
        <el-form-item label="货品名称" prop="gName">
          <el-input
            v-model="goodsQueryParams.gName"
            placeholder="请输入货品名称"
            clearable
            @keyup.enter.native="handleGoodsQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleGoodsQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetGoodsQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table ref="multipleTable" v-loading="goodsLoading" :data="goodList"
                @selection-change="goodsSelectionChange">
        <el-table-column type="selection" :reserve-selection="true" width="55" align="center"/>
        <el-table-column label="货品编号" align="center" prop="hpGoods.gCode" width="180"/>
        <el-table-column label="货品名称" align="center" prop="hpGoods.gName"/>
        <el-table-column label="规格型号" align="center" prop="hpGoods.specCode"/>
        <el-table-column label="单位" align="center" prop="hpGoods.gUnit">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit" :value="scope.row.hpGoods.gUnit"/>
          </template>
        </el-table-column>
        <el-table-column label="入库单价" align="center" prop="hpGoods.wrPrice"/>
      </el-table>
      <pagination
        v-show="goodsTotal>0"
        :total="goodsTotal"
        :page.sync="goodsQueryParams.pageNum"
        :limit.sync="goodsQueryParams.pageSize"
        @pagination="trOutboundLocation"
      />
      <div slot="footer">
        <el-button @click="closeGoods">关 闭</el-button>
        <el-button type="primary" @click="goodsSubmit">确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listTransfer, getTransfer, delTransfer, addTransfer, updateTransfer, getTdCode} from "@/api/cx-crkgl/transfer";
import {listUser} from "@/api/system/user";
import {listWarehouse} from "@/api/cx-ckgl/warehouse";
import {listLocation, getLocas} from "@/api/cx-ckgl/location";
import {listGoods, listType} from "@/api/cx-hpxx/goods";
import {listOutGoods,listStock} from "@/api/cx-ledger/stock";
import Register from "@/views/register";

export default {
  name: "Transfer",
  components: {Register},
  dicts: ['doc_status', 't_type', 'in_status', 'out_status', 'g_unit'],
  data() {
    return {
      // 遮罩层
      loading: true,
      goodsLoading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedCrkTransferDetails: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      goodsTotal: 0,
      // 库存调拨表格数据
      transferList: [],
      //货品列表查询参数
      goodsQueryParams: {
        pageNum: 1,
        pageSize: 10,
        gCode: null,
        gName: null,
        wId: null
      },
      // 选中货品数组
      goods: [],
      //用户数据
      userList: [],
      //货品数据
      goodsList: [],
      //货品类型
      goodsType: [],
      //查询调出仓库里面的货品信息
      goodList: [],
      //库存信息
      stockList:[],
      //仓库信息
      warehouseList: [],
      //仓位信息
      locationList: [],
      //仓位名称
      locationName: [],
      // 调拨明细表格数据
      crkTransferDetailsList: [],
      //调拨编码
      code: null,
      // 弹出层标题
      title: "",
      isDe1: true,
      isDe2: false,
      // 是否显示弹出层
      open: false,
      openGoodsList: false,
      openAudit: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tdCode: null,
        docStatus: null,
        tDate: null,
        outWId: null,
        inWId: null,
        outStatus: null,
        tType: null,
        inStatus: null,
        tManager: null,
        isDelte: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // tdCode: [
        //   { required: true, message: "调拨单号不能为空", trigger: "blur" }
        // ],
        // docStatus: [
        //   {required: true, message: "单据状态不能为空", trigger: "change"}
        // ],
        tDate: [
          {required: true, message: "调拨申请日期不能为空", trigger: "blur"}
        ],
        outWId: [
          {required: true, message: "调出仓库 ID不能为空", trigger: "blur"}
        ],
        inWId: [
          {required: true, message: "调入仓库不能为空", trigger: "blur"}
        ],
        outStatus: [
          {required: true, message: "出库状态不能为空", trigger: "change"}
        ],
        tType: [
          {required: true, message: "数据字典不能为空", trigger: "change"}
        ],
        inStatus: [
          {required: true, message: "入库状态不能为空", trigger: "change"}
        ],
        tManager: [
          {required: true, message: "外键，关联用户表不能为空", trigger: "blur"}
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
    this.getBuyer();
    this.getWarehouse();
    this.getLocation();
    this.getGoodsList();
    this.getTypeList();
    this.getStock();
    this.trOutboundLocation();
  },
  methods: {
    /** 查询库存调拨列表 */
    getList() {
      this.loading = true;
      listTransfer(this.queryParams).then(response => {
        this.transferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    trOutbound() {
      this.crkTransferDetailsList = []
    },
    trOutboundLocation() {
      this.goodsLoading = true;
      this.goodsQueryParams.wId = this.form.outWId
      listOutGoods(this.goodsQueryParams).then(resopnse => {
        this.goodList = resopnse.rows;
        this.goodsTotal = resopnse.total;
        this.goodsLoading = false
      })
    },
    trWarehousing() {
      if (this.form.crkTransferDetailsList != null) {
        this.form.crkTransferDetailsList.forEach(item => {
          item.inSlId = null
        })
      }
      this.trWarehousingLocation(this.form.inWId)
    },
    trWarehousingLocation(inWid) {
      getLocas(inWid).then(response => {
        this.locationName = response.data
      })
    },

    calculateTotal(row) {
      const sum =this.getStocks(row.goodsId);
      if(row.tQuantity>sum){
        this.$message.warning("调拨数量不能超过库存,库存:"+sum);
        this.$nextTick(()=>{
          row.tQuantity=sum
        })
      }
      if (row.iuPrice && row.tQuantity) {
        row.tAmount = (row.iuPrice * row.tQuantity);
      } else {
        row.tAmount = 0;
      }
    },
    getStocks(goodId){
      const sl = this.stockList.find(item=>item.gId===goodId)
      console.log(sl)
      return sl?sl.itemQuantity:0
    },
    getTypeList() {
      listType(this.queryParams).then(response => {
        this.goodsType = response.data;
      });
    },
    /**库存信息*/
    getStock(){
      listStock().then(res=>this.stockList=res.rows)
    },
    /** 获取货品列表 */
    getGoodsList() {
      listGoods().then(res => this.goodsList = res.rows)
    },
    /** 新增货品信息操作 */
    addGoods() {
      if (this.form.outWId === null || this.form.inWId === null) {
        this.$message.warning("调出调入仓库不能为空");
        return;
      }else if(this.form.outWId==this.form.inWId){
        this.$message.warning("两个仓库不能一致")
        return;
      }
      this.openGoodsList = true;
      this.trOutboundLocation();
    },
    goodsSelectionChange(selection) {
      this.goods = selection
    },
    /** 关闭产品选择列表 */
    closeGoods() {
      this.$refs.multipleTable.clearSelection()
      this.resetGoodsQuery();
      this.openGoodsList = false
    },
    /** 移除 */
    removeRow(index) {
      // 通过 index 从 purchaseList 数组中移除对应行
      this.crkTransferDetailsList.splice(index, 1);
    },
    /** 货品选择列表提交 */
    goodsSubmit() {
      this.trWarehousingLocation(this.form.inWId)
      this.goods.forEach(item => {
        this.crkTransferDetailsList.push({
          specCode: item.hpGoods.specCode,
          unit: item.hpGoods.gUnit,
          iuPrice: item.hpGoods.wrPrice,
          tQuantity: 1,
          goodsId: item.hpGoods.gId
        })
      })
      // this.resetGoodsQuery();
      // this.$refs.multipleTable.clearSelection()
      //关闭货品列表页面
      this.openGoodsList = false
    },

    resetGoodsQuery() {
      this.resetForm("queryGoodsForm");
      this.handleGoodsQuery();
    },
    handleGoodsQuery() {
      this.goodsQueryParams.pageNum = 1;
      this.trOutboundLocation();
    },
    //用户数据
    getBuyer() {
      listUser().then(res => this.userList = res.rows);
    },
    //仓库信息
    getWarehouse() {
      listWarehouse().then(res => this.warehouseList = res.rows);
    },
    //仓位信息
    getLocation() {
      listLocation().then(res => this.locationList = res.rows);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tId: null,
        tdCode: null,
        docStatus: null,
        tDate: null,
        outWId: null,
        inWId: null,
        outStatus: null,
        tType: null,
        remark: null,
        inStatus: null,
        tManager: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelte: null
      };
      this.isDe2 = false;
      this.isDe1 = true;
      this.crkTransferDetailsList = [];
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
      this.ids = selection.map(item => item.tId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getTranCode();
      this.reset();
      this.open = true;
      this.title = "添加库存调拨";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tId = row.tId || this.ids
      getTransfer(tId).then(response => {
        if (response.data.docStatus === 3) {
          this.$message.warning("不能对审核完成的订单进行操作");
          return;
        } else {
          this.form = response.data;
          this.crkTransferDetailsList = response.data.crkTransferDetailsList;
          this.open = true;
          this.trWarehousingLocation(response.data.inWId);
          this.title = "修改库存调拨";
        }
      });
    },

    /** 审核按钮操作 */
    handleExam(row) {
      this.reset();
      this.isDe1 = false;
      this.isDe2 = true;
      const tId = row.tId || this.ids
      getTransfer(tId).then(response => {
        if (response.data.docStatus === 3) {
          this.$message.warning("不能对审核完成的订单进行操作");
          return;
        } else {
          this.form = response.data;
          this.form.docStatus = 3;
          this.crkTransferDetailsList = response.data.crkTransferDetailsList;
          this.open = true;
          this.trWarehousingLocation(response.data.inWId);
        }
      });
    },
    /**撤销审核的按钮操作*/
    revokeExam(row) {
      this.reset();
      this.isDe1 = false;
      this.isDe2 = true;
      const tId = row.tId || this.ids
      getTransfer(tId).then(response => {
        if (response.data.docStatus===1){
          this.$message.warning("目前状态无需撤销");
          return;
        }
        this.form = response.data;
        this.form.docStatus = 1;
        updateTransfer(this.form).then(item => {
          this.$modal.msgSuccess("撤销成功");
          this.getList();
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.crkTransferDetailsList = this.crkTransferDetailsList;
          console.log(this.form.crkTransferDetailsList)
          if (this.form.tId != null) {
            updateTransfer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.tdCode = this.code
            addTransfer(this.form).then(response => {
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
      const tIds = row.tId || this.ids;
      getTransfer(tIds).then(response => {
        if (response.data.docStatus === 3) {
          this.$message.warning("不能对审核完成的订单进行操作");
          return;
        } else {
          this.$modal.confirm('是否确认删除库存调拨编号为"' + tIds + '"的数据项？').then(function () {
            return delTransfer(tIds);
          }).then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {
          });
        }
      });
    },

    /** 调拨明细序号 */
    rowCrkTransferDetailsIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 调拨明细添加按钮操作 */
    // handleAddCrkTransferDetails() {
    //   let obj = {};
    //   if (this.form.tId != null) {
    //     obj.tdCode = this.form.tdCode
    //   } else {
    //     obj.tdCode = this.code
    //   }
    //   obj.specCode = "";
    //   obj.unit = "";
    //   obj.tQuantity = "";
    //   obj.iuPrice = "";
    //   obj.tAmount = "";
    //   obj.goodsId = "";
    //   obj.remark = "";
    //   obj.isDelte = "";
    //   obj.inSlid = "";
    //   // this.crkTransferDetailsList.push(obj);
    // },
    /** 调拨明细删除按钮操作 */
    handleDeleteCrkTransferDetails() {
      if (this.checkedCrkTransferDetails.length === 0) {
        this.$modal.msgError("请先选择要删除的调拨明细数据");
      } else {
        const crkTransferDetailsList = this.crkTransferDetailsList;
        const checkedCrkTransferDetails = this.checkedCrkTransferDetails;
        this.crkTransferDetailsList = crkTransferDetailsList.filter(function (item) {
          return checkedCrkTransferDetails.indexOf(item.index) === -1
        });
      }
    },
    /** 复选框选中数据 */
    handleCrkTransferDetailsSelectionChange(selection) {
      this.checkedCrkTransferDetails = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cx-crkgl/transfer/export', {
        ...this.queryParams
      }, `transfer_${new Date().getTime()}.xlsx`)
    },
    getTranCode() {
      getTdCode().then(res => this.code = res.data.tdCode)
    }
  }
};
</script>
