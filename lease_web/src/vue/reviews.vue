<script lang="ts" setup>
import { ref, onMounted, nextTick } from "vue";
import api from '../api/index.js'
import { ElMessage, ElMessageBox, ElNotification } from 'element-plus'
import { Delete, Plus, Edit, Minus, Search, Share, Upload } from '@element-plus/icons-vue'

const tableData = ref([]);

// 挂载页面时，查询数据
onMounted(async function () {


  checkData();
})


// 控制新增信息窗口是否显示
const addWinvisible = ref(false);


const editObject = ref({})



// 检索数据的文本
const checkObj = ref({
  page: 1,
  size: 5,

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
  let result = await api.getParams("/api/reviews", checkObj.value)

  // 更新表格数据
  tableData.value = result.data.data;
  total.value = result.data.total;

  loading.value = false;
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
  // editObject.value = listSelect[0];

  editObject.value = JSON.parse(JSON.stringify(listSelect[0]));


}

// 定义变量，控制编辑窗口是否显示
const editWinvisible = ref(false);

// 点击保存按钮时，触发此方法保存修改数据
const addEditSave = async () => {
  console.log("============保存==========")

  let result = await api.putJson("/api/reviews", editObject.value)

  if (result.code === 200) {

    ElMessage({
      type: 'success',
      message: '保存成功'
    })
      // 关闭编辑窗口
  editWinvisible.value = false;
      // 刷新数据
      checkData();
  } else {
    ElMessage({
      type: 'warning',
      message: '保存失败：' + result.message
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


</script>


<template>

  <!-- 让里面的div并排显示,并且居中 -->
  <div style="margin-top: 15px;display: flex; column-gap: 40px;margin-bottom: 50px;">

    <!-- 使每个输入框间隔20px -->
    <div style="margin-right: 30px;margin-left: 140px;">
      <el-input v-model="checkObj.username" style="width: 160px" clearable size="large" placeholder="用户名" />
    </div>
    <div style="margin-right: 30px;margin-left: 10px;">
      <el-input v-model="checkObj.nickname" style="width: 160px" clearable size="large" placeholder="用户昵称" />
    </div>
    <div style="margin-right: 30px;margin-left: 10px;">
      <el-input v-model="checkObj.phone" style="width: 160px" clearable size="large" placeholder="手机号" />
    </div>



    <div  style="margin-right: 30px;margin-left: 10px;">
<el-select v-model="checkObj.status" placeholder="信誉状态" clearable style="width: 160px" size="large">
      <el-option label="一般" value="commonly" />
      <el-option label="良好" value="merit"/>
      <el-option label="优秀" value="excellent" />
      <el-option label="差" value="bad" />
    </el-select>
    </div>

    
    <div style="margin-right: 30px;margin-left: 10px;">
        <el-date-picker v-model="checkObj.updateAt" clearable style="width: 160px" size="large" type="date" placeholder="更新日期" value-format="YYYY-MM-DD" />
    </div>
  </div>

















  <!-- 让此div中的按钮，每个间隔30px-->
  <div style="margin-bottom: 30px;display: flex; justify-content: center; align-items: center; column-gap: 50px;">
    <el-tooltip placement="top" content="修改用户信誉" effect="customized">
      <el-button type="warning" @click="updateDate()" round :icon="Edit" style="width: 80px;" />
    </el-tooltip>
    <el-tooltip placement="top" content="查询用户信誉" effect="customized">
      <el-button type="info" @click="checkData(), pup()" round :icon="Search" style="width: 80px;" />
    </el-tooltip>






  </div>

  <!-- 定义表格数据 -->
  <el-table :data="tableData" style="width: 100%" border highlight-current-row="true" @selection-change="selectL"
    v-loading="loading" stripe="true">
    <el-table-column type="selection" width="70" align="center" />
    <el-table-column prop="username" label="用户名" align="center" />
    <el-table-column prop="nickname" label="用户昵称" align="center" />
    <el-table-column prop="phone" label="手机号" align="center" />

    <el-table-column prop="rating" label="信誉积分" align="center" />
    <el-table-column prop="status" label="信誉状态" align="center" />
    <el-table-column prop="updateAt" label="更新时间" align="center" />

  </el-table>


  <!-- 分页 -->
  <div class="pagination-container" style="margin-top: 30px;margin-right: 50px;">
    <el-pagination v-model:current-page="checkObj.page" v-model:page-size="checkObj.size" :page-sizes="[5, 10, 15, 30]"
      :size="size" :disabled="disabled" background="true" layout="prev, pager, next, sizes" :total="total"
      @size-change="handleSizeChange" @current-change="checkData" />
  </div>


  <el-dialog v-model="editWinvisible" title="信誉编辑" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form style="max-width: 600px" :model="editObject" :size="formSize" status-icon>

      <el-form-item label="用户名" :label-width="120">
        <el-input v-model="editObject.username" disabled autocomplete="off" />
      </el-form-item>
      <el-form-item label="用户昵称" :label-width="120">
        <el-input v-model="editObject.nickname" disabled autocomplete="off" />
      </el-form-item>

      <el-form-item label="手机号" :label-width="120">
        <el-input v-model="editObject.phone" disabled autocomplete="off" />
      </el-form-item>
      
      <el-form-item label="信誉积分" :label-width="120">
        <el-input v-model="editObject.rating" autocomplete="off" />
      </el-form-item>
      <el-form-item label="信誉状态" :label-width="120">
        <el-input v-model="editObject.status" disabled autocomplete="off" />
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