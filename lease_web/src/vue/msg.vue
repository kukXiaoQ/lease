<script lang="ts" setup>
import { ref, onMounted,nextTick } from "vue";
import api from '../api/index.js'
import { ElMessage, ElMessageBox,ElNotification  } from 'element-plus'
import { Delete, Plus, Edit,Minus, Search, Share, Upload,Download } from '@element-plus/icons-vue'

const tableData = ref([]);

// 挂载页面时，查询数据
onMounted(async function () {
  checkData();
})

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
  let result = await api.getParams("/api/notifications", checkObj.value)

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
    let result = await api.delByIds("/api/notifications", listSelectId);
    if (result.code === 200) {


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
  
}

// 加载状态... ...
const loading = ref(true);



// 当页面大小发生改变时，触发
const handleSizeChange = (size) => {

  checkObj.value.size = size;
  checkData();
}


// 点击查询，弹出提示框
const pup = () => {
  ElNotification({
    title: 'Success',
    message: '查询成功',
    type: 'success',
  })
}

const exportData =async () => {

// 导出数据
let result=await api.get("/api/loading/up_msgs");

if(result.code==200){
  ElMessage({
    type:'success',
    message: result.message
  })
}else{
  ElMessage({
    type:'warning',
    message: '导出失败'
  })
}

}

</script>


<template>

  <!-- 让里面的div并排显示,并且居中 -->
  <div style="margin-top: 15px;display: flex; column-gap: 40px;margin-bottom: 50px;">

<!-- 使每个输入框间隔20px -->
<div style="margin-right: 30px;margin-left: 100px;">
  <el-input v-model="checkObj.username" style="width: 160px" clearable size="large" placeholder="用户名" />
</div>
<div style="margin-right: 30px;margin-left: 140px;">
  <el-input v-model="checkObj.nickname" style="width: 160px" clearable size="large" placeholder="用户昵称" />
</div>
<div style="margin-right: 30px;margin-left: 140px;">
  <el-input v-model="checkObj.email" style="width: 160px" clearable size="large" placeholder="电子邮箱" />
</div>
<div style="margin-right: 30px;margin-left: 140px;">
  <el-date-picker v-model="checkObj.sentAt" type="date" clearable size="large" placeholder="发送时间" 
    value-format="YYYY-MM-DD" style="width: 160px" ref="table" />
</div>
</div>



  <!-- 让此div中的按钮，每个间隔30px-->
  <div style="margin-bottom: 30px;display: flex; justify-content: center; align-items: center; column-gap: 50px;">

  <el-tooltip placement="top" content="删除通知记录" effect="customized" >
    <el-button type="danger" @click="deleteByIds()" round :icon="Minus" style="width: 80px;"/>
  </el-tooltip>
  <el-tooltip placement="top" content="查询通知记录" effect="customized" >
    <el-button type="info" @click="checkData(),pup()" round :icon="Search" style="width: 80px;"/>
  </el-tooltip>

  <el-tooltip placement="top" content="导出数据" effect="customized" >
    <el-button type="primary"  @click="exportData()" round :icon="Download"  style="width: 80px;"/>
  </el-tooltip>
  </div>

  <!-- 定义表格数据 -->
  <el-table :data="tableData" style="width: 100%" border highlight-current-row="true" @selection-change="selectL"
    v-loading="loading" stripe="true">
    <el-table-column type="selection" width="70" align="center" />
    <el-table-column prop="username" label="用户名" align="center"  />
    <el-table-column prop="nickname" label="用户昵称" align="center"  />
    <el-table-column prop="email" label="电子邮箱" align="center"  />
    <el-table-column prop="message" label="发送信息" align="center"  />
    <el-table-column prop="sentAt" label="发送时间" align="center"  />
  </el-table>


  <!-- 分页 -->
  <div class="pagination-container" style="margin-top: 30px;margin-right: 50px;">
    <el-pagination v-model:current-page="checkObj.page" v-model:page-size="checkObj.size" :page-sizes="[5, 10, 15, 30]"
      :size="size" :disabled="disabled" background="true" layout="prev, pager, next, sizes" :total="total"
      @size-change="handleSizeChange" @current-change="checkData" />
  </div>

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