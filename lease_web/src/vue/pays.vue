<script lang="ts" setup>
import { ref, onMounted,nextTick } from "vue";
import api from '../api/index.js'
import { ElMessage, ElMessageBox,ElNotification  } from 'element-plus'
import { Delete, Plus, Edit,Minus, Search, Share, Upload } from '@element-plus/icons-vue'
// <el-icon><Minus /></el-icon>

const tableData = ref([]);

// 挂载页面时，查询数据
onMounted(async function () {


  checkData();
})


// 控制新增信息窗口是否显示
const addWinvisible = ref(false);

const addShow = () => {

  // 清空之前的数值
  addObject.value = {}

  addWinvisible.value = true;
}

const addClose = () => {
  addWinvisible.value = false;
}

// 声明一个行号，表示修改数据的下标，回显数据时，根据此下标修改表格
var editIndex = -1;

// 新增数据绑定对象
const addObject = ref({})

const editObject = ref({
  userId: '',
  vehicleId: '',
  status: '',
  startTime: '',
  endTime: '',
  updatedAt: '',
  actualRentalPrice: '',
  deposit: ''
})



// 检索数据的文本
const checkObj = ref({
  page: 1,
  size: 5,
  // 状态是已完成
  // status: ''
});
const total = ref(0);


const checkData = async (val) => {

  loading.value = true;

  if (typeof val == 'number') {
    checkObj.value.page = val;
  }

  if (typeof checkObj.value.size == 'number') {

  }

  console.log(checkObj.value.startTime)

  // 查询数据
  let result = await api.getParams("/api/payments", checkObj.value)

  // 更新表格数据
  tableData.value = result.data.data;
  total.value = result.data.total;

  loading.value = false;
}


// 批量删除
const deleteByIds = () => {
  if (listSelectId.length == 0) {
    ElMessage({
      type: 'warning',
      message: '请选择要删除的数据'
    })
    return;
  }

  ElMessageBox.confirm('确认删除吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    let result = await api.delByIds("/api/payments", listSelectId);
    if (result.code === 200) {


      console.log("批量删除==========")
        console.log(result.data)
  console.log("批量删除==========")

      checkData();

      ElMessage({
        type: 'success',
        message: '操作成功，共删除' + result.data + '条数据'
      })
    } else {
      ElMessage({
        type: 'warning',
        message: '操作失败，请重试'
      })
    }
  }).catch(() => {
    ElMessage({
      type: 'warning',
      message: '取消成功'
    })
  });
}



// 当行被勾选时，进入此方法。存有id号和行数据
var listSelect = [];
var listSelectId = [];
const selectL = (selection) => {


  listSelect = selection
  listSelectId = selection.map(item => item.id)
  

  console.log("勾选==========")

console.log(listSelect)// 行数据
console.log(listSelectId)// id号
console.log("勾选==========")

}

// 加载状态... ...
const loading = ref(true);



const handleClick = (row) => {
  // 获取表格实例
  console.log(row)

}

// 当页面大小发生改变时，触发
const handleSizeChange = (size) => {

  checkObj.value.size = size;
  checkData();
}

// 点击修改按钮时，触发此方法修改数据
const updateDate = () => {

  // 如果勾选数据为空，提示用户
  if (listSelect.length == 0) {
    ElMessage({
      type: 'warning',
      message: '请选择要修改的数据'
    })
    return;
  }
  // 如果勾选数据多于1条，提示用户
  if (listSelect.length > 1) {
    ElMessage({
      type: 'warning',
      message: '只能选择一行进行修改'
    })
    return;
  }

  // 弹出编辑窗口
  editWinvisible.value = true;

  console.log(listSelect[0])

  // 回显数据

  editObject.value = JSON.parse(JSON.stringify(listSelect[0]));


}

// 定义变量，控制编辑窗口是否显示
const editWinvisible = ref(false);

// 点击保存按钮时，触发此方法保存修改数据
const addEditSave = async () => {
    console.log("============保存==========")


    console.log(editObject.value)

    let result = await api.putJson("/api/payments", editObject.value)


    // 关闭编辑窗口
    editWinvisible.value = false;
    if(result.code === 200){
    // 刷新数据
    checkData();

    ElMessage({
      type:'success',
      message: '保存成功'
    })
    }else{
      ElMessage({
      type:'warning',
      message: '保存失败'
    })
    }
}

// 点击查询，弹出提示框
const pup = () => {
  ElNotification({
    title: 'Success',
    message: '查询成功',
    type: 'success',
  })
}


const insertDate = () => {
  
  // 显示窗口
  addShow();
}

// 点击保存按钮
const addOrderSave = async () => {
  console.log("============新增==========")
  console.log(addObject)

  console.log("============新增==========")

  // 开始保存
  let result = await api.postJson("/api/payments",addObject.value)

  console.log("============新增jg==========")
  console.log(result)
  console.log("============新增jg==========")
  if(result.code === 200){
      // 刷新数据
      checkData();
    // 关闭新增窗口
    addWinvisible.value = false;

    ElMessage({
      type:'success',
      message: '新增成功：修改行数为' + result.data
    })
  }else{
    ElMessage({
      type:'warning',
      message: '新增失败：' + result.message
    })
  }
}
</script>


<template>

  <!-- 让里面的div并排显示,并且居中 -->
  <div style="margin-top: 15px;display: flex; column-gap: 40px;margin-bottom: 50px;">

<!-- 使每个输入框间隔20px -->
<div style="margin-right: 30px;margin-left: 140px;">
  <el-input v-model="checkObj.orderId" style="width: 160px" size="large" placeholder="订单编号" />
</div>
<div style="margin-right: 30px;margin-left: 10px;">
  <!-- <el-input v-model="checkObj.vehicleId" style="width: 160px" size="large" placeholder="支付方式" /> -->
  <el-select v-model="checkObj.paymentMethod" placeholder="支付方式" style="width: 160px" size="large">
  <el-option label="信用卡" value="creditcard" />
  <el-option label="支付宝" value="paypal"/>
  <el-option label="现金" value="cash" />

</el-select>



</div>
<div  style="margin-right: 30px;margin-left: 10px;">
<!-- 待确认、已确认、已取消、已完成 -->
<el-select v-model="checkObj.status" placeholder="支付状态" style="width: 160px" size="large">
  <el-option label="待处理" value="pending" />
  <el-option label="已完成" value="completed"/>
  <el-option label="已退款" value="refunded" />
</el-select>
</div>
<div style="margin-right: 30px;">
  <el-date-picker v-model="checkObj.createdAt" type="date" size="large" placeholder="创建时间" :size="size"
    value-format="YYYY-MM-DD" style="width: 200px" />
</div>
<div style="margin-right: 30px;">
  <el-date-picker v-model="checkObj.updatedAt" type="date" size="large" placeholder="更新时间" :size="size"
    value-format="YYYY-MM-DD" style="width: 200px" ref="table" />
</div>

</div>



  <!-- 让此div中的按钮，每个间隔30px-->
  <div style="margin-bottom: 30px;display: flex; justify-content: center; align-items: center; column-gap: 50px;">

  <el-tooltip placement="top" content="添加支付记录" effect="customized">
    <el-button type="primary" @click="insertDate()" round :icon="Plus" style="width: 80px;" />
  </el-tooltip>
  <el-tooltip placement="top" content="删除支付记录" effect="customized" >
    <el-button type="danger" @click="deleteByIds()" round :icon="Minus" style="width: 80px;"/>
  </el-tooltip>
  <el-tooltip placement="top" content="修改支付记录"  effect="customized">
    <el-button type="warning" @click="updateDate()" round :icon="Edit" style="width: 80px;"/>
  </el-tooltip>
  <el-tooltip placement="top" content="查询支付记录" effect="customized" >
    <el-button type="info" @click="checkData(),pup()" round :icon="Search" style="width: 80px;"/>
  </el-tooltip>

    
    
    
    

  </div>

  <!-- 定义表格数据 -->
  <el-table :data="tableData" style="width: 100%" border highlight-current-row="true" @selection-change="selectL"
    v-loading="loading" stripe="true">
    <el-table-column type="selection" width="70" align="center" />
    <!-- <el-table-column prop="id" label="ID" align="center" width="120" /> -->
    <el-table-column prop="orderId" label="订单编号" align="center"  />
    <el-table-column prop="amount" label="支付金额" align="center"  />
    <el-table-column prop="paymentMethod" label="支付方式" align="center"  />
    <el-table-column prop="status" label="支付状态" align="center"  />
    <el-table-column prop="createdAt" label="创建时间" align="center"  />
    <el-table-column prop="updatedAt" label="更新时间" align="center"  />
  </el-table>


  <!-- 分页 -->
  <div class="pagination-container" style="margin-top: 30px;margin-right: 50px;">
    <el-pagination v-model:current-page="checkObj.page" v-model:page-size="checkObj.size" :page-sizes="[5, 10, 15, 30]"
      :size="size" :disabled="disabled" background="true" layout="prev, pager, next, sizes" :total="total"
      @size-change="handleSizeChange" @current-change="checkData" />
  </div>


  <!-- 编辑 -->
  <el-dialog 
    v-model="editWinvisible" 
    title="订单编辑" 
    width="500"
    align-center 
    center
    draggable
    :show-close="false"
    :destroy-on-close="true"
    >
  <el-form
    style="max-width: 600px"
    :model="editObject"
    :size="formSize"
    status-icon
  >

  <el-form-item label="订单编号：" :label-width="120">
    <el-input v-model="editObject.orderId" disabled autocomplete="off" />
  </el-form-item>
  <el-form-item label="支付金额" :label-width="120">
    <el-input v-model="editObject.amount"  autocomplete="off" />
  </el-form-item>

    <el-form-item label="支付方式：" :label-width="120">
    <el-select v-model="editObject.paymentMethod" placeholder="请选择支付方式">
      <el-option label="信用卡" value="0" />
      <el-option label="支付宝" value="1"/>
      <el-option label="现金" value="2" />
      </el-select>
  </el-form-item>

  <el-form-item label="支付状态：" :label-width="120">
    <el-select v-model="editObject.status" placeholder="请选择支付状态">
        <el-option label="待处理" value="0" />
        <el-option label="已完成" value="1" />
        <el-option label="已退款" value="2" />
      </el-select>
  </el-form-item>
  
  <el-form-item label="创建时间：" :label-width="120">
    <el-date-picker v-model="editObject.createdAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
  </el-form-item>
  <el-form-item label="更新时间：" :label-width="120">
    <el-date-picker v-model="editObject.updatedAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
  </el-form-item>

  </el-form>

  <template #footer>
    <div class="dialog-footer">
      <el-button @click="editWinvisible = false">取消</el-button>
      <el-button type="primary" @click="addEditSave()">
        保存
      </el-button>
    </div>
  </template>
</el-dialog>


<!-- 添加订单 -->
<el-dialog 
    v-model="addWinvisible" 
    title="新增支付记录" 
    width="500"
    align-center 
    center
    draggable
    :show-close="false"
    :destroy-on-close="true"
    >
  <el-form
    style="max-width: 600px"
    :model="addObject"
    :size="formSize"
    status-icon
  >

  <el-form-item label="订单编号：" :label-width="120">
    <el-input v-model="addObject.orderId"  autocomplete="off" />
  </el-form-item>
  <el-form-item label="支付金额" :label-width="120">
    <el-input v-model="addObject.amount"  autocomplete="off" />
  </el-form-item>

    <el-form-item label="支付方式：" :label-width="120">
    <el-select v-model="addObject.paymentMethod" placeholder="请选择支付方式">
      <el-option label="信用卡" value="0" />
      <el-option label="支付宝" value="1"/>
      <el-option label="现金" value="2" />
      </el-select>
  </el-form-item>

    <el-form-item label="支付状态：" :label-width="120">
    <el-select v-model="addObject.status" placeholder="请选择支付状态">
        <el-option label="待处理" value="0" />
        <el-option label="已完成" value="1" />
        <el-option label="已退款" value="2" />
      </el-select>
  </el-form-item>
  
  <el-form-item label="创建时间：" :label-width="120">
    <el-date-picker v-model="addObject.createdAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
  </el-form-item>
  <el-form-item label="更新时间：" :label-width="120">
    <el-date-picker v-model="addObject.updatedAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
  </el-form-item>

  </el-form>

  <template #footer>
    <div class="dialog-footer">
      <el-button @click="addWinvisible = false">取消</el-button>
      <el-button type="primary" @click="addOrderSave()">
        保存
      </el-button>
    </div>
  </template>
</el-dialog>



</template>

<style>
  .el-popper.is-customized {
  /* Set padding to ensure the height is 32px */
  padding: 6px 12px;
  background: linear-gradient(90deg, rgb(159, 229, 151), rgb(204, 229, 129));
}

  .el-popper.is-customized .el-popper__arrow::before {
    background: linear-gradient(45deg, #b2e68d, #bce689);
    right: 0;
  }
</style>

<style scoped>

/* 每个div，距离右侧20px */
.first-div {
  margin-right: 20px;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}
</style>