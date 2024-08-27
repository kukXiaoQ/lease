<script lang="ts" setup>
import { ref, onMounted, nextTick } from "vue";
import api from '../api/index.js'
import { ElMessage, ElMessageBox, ElNotification,UploadProps, UploadUserFile  } from 'element-plus'
import { Delete, Plus, Edit, Minus, Search, Tools, Upload } from '@element-plus/icons-vue'
import { count } from "echarts/types/src/component/dataZoom/history.js";

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
  // 清空头像
  imageUrl.value = "";
  // 清空品牌

  valueRemote.value = [];

  addWinvisible.value = true;
}

const addClose = () => {
  addWinvisible.value = false;
}

// 声明一个行号，表示修改数据的下标，回显数据时，根据此下标修改表格
var editIndex = -1;

// 新增数据绑定对象
const addObject = ref({})

const editObject = ref({})



// 检索数据的文本
const checkObj = ref({
  page: 1,
  size: 5,
  // 状态是已完成
  status: ''
});
const total = ref(0);


const checkData = async (val) => {

  loading.value = true;

  if (typeof val == 'number') {
    checkObj.value.page = val;
  }

  if (typeof checkObj.value.size == 'number') {

  }

  checkObj.value.brandId = valueRemote1.value;
  console.log(checkObj.value.startTime)

  // 查询数据
  let result = await api.getParams("/api/vehicles", checkObj.value)


  // 更新表格数据
  tableData.value = result.data.data;
  // 
  total.value = result.data.total;

  loading.value = false;


  console.log(tableData.value)
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
    let result = await api.delByIds("/api/vehicles", listSelectId);
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
  listSelectId = selection.map(item => item.vehicles.id)

  console.log("勾选==========")

console.log("行数据=========")
  console.log(listSelect)
  console.log("行数据=========")

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

  // 清除之前数据
  editObject.value = {}
  imageUrl.value = "";
  fileList.value = [];
  // 清空搜索框
  valueRemote.value = [];


  console.log(listSelect[0])

  // 回显数据
  // editObject.value = listSelect[0];

  editObject.value = JSON.parse(JSON.stringify(listSelect[0]));
  valueRemote.value = editObject.value.brandName

  if(!editObject.value.vehicles.url || editObject.value.vehicles.url == ""){
    // 图片地址为空，不发送请求
    console.log("图片地址为空，不发送请求")
    return;
  }
  console.log("图片地址不为空")

  fileList.value[0] = [];

  console.log(editObject.value.vehicles.url)
  console.log("============回显==========")
  imageUrl.value = "api" + editObject.value.vehicles.url;

  console.log(imageUrl.value)
  fileList.value[0].url = imageUrl.value;

}

// 定义变量，控制编辑窗口是否显示
const editWinvisible = ref(false);

// 点击保存按钮时，触发此方法保存修改数据
const addEditSave = async () => {
  console.log("============保存==========")


  // editObject.value.url = imageUrl.value;
    // 将新图片赋值
    editObject.value.vehicles.url = imageUrl.value;
    let linshi = editObject.value.vehicles;

  let result = await api.putJson("/api/vehicles", linshi)


  console.log(editObject)
  // 关闭编辑窗口
  editWinvisible.value = false;
  if (result.code === 200) {
    // 刷新数据
    checkData();

    ElMessage({
      type: 'success',
      message: '保存成功'
    })
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

// 点击保存按钮
const addOrderSave = async () => {
  console.log("============新增==========")

  addObject.value.url = imageUrl.value;

//   选中的品牌id
  console.log(valueRemote.value)
  addObject.value.brandId = valueRemote.value
  console.log("============新增==========")

  // 开始保存
  let result = await api.postJson("/api/vehicles", addObject.value)


  if (result.code === 200) {
    // 刷新数据
    checkData();
    // 关闭新增窗口
    addWinvisible.value = false;

    ElMessage({
      type: 'success',
      message: '新增成功：新增行数为' + result.data
    })
  } else {
    ElMessage({
      type: 'warning',
      message: '新增失败：' + result.message
    })
  }
}

const uploadImg = ref({});
const addUploadeImg = async () => {

  // 将文件地址、数据id发送给后台

  console.log(listSelectId)

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

  console.log(listSelectId);
  console.log(imageUrl.value)
  editObject.value.url = imageUrl.value;
  editObject.value.id = listSelectId[0];
  let result = await api.putJson("/api/vehicles/img",editObject.value);
  console.log(result)
}

// 上传头像，绑定的数据
const imageUrl = ref();

// 上传头像成功
const handleAvatarSuccess = (result,uploadFile)  => {
  
  console.log("============上传成功==========")
  imageUrl.value = "api/" + result.data;


  console.log(fileList)
}
// 上传前的钩子
const beforeAvatarUpload = (rawFile) =>{
  console.log("============上传前==========")
  console.log(rawFile)
  console.log("============上传前==========")

  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('请传入正确的图片格式!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能超过 2MB!')
    return false
  }
  return true
}


const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {

  console.log("============删除==========")

  console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {

  console.log("============预览==========")
  console.log(uploadFile)
  console.log("============预览==========")
  dialogImageUrl.value = uploadFile.url!
  dialogVisible.value = true
}
const fileList = ref<UploadUserFile[]>([{
  name: '',
  url: '',
}])




const options = ref<ListItem[]>([])
const valueRemote = ref<string[]>([])
const loading1 = ref(false)
const valueRemote1 = ref<string[]>([])
    const options1 = ref<ListItem[]>([])

const remoteMethod1 = async (query: string) => {

console.log(query)
name.value.name = query;
let result = await api.getParams("/api/brands",name.value);

// 发送数据
if (query) {

loading1.value = true
options1.value = result.data;
} else {
options1.value = []
}
}
const name = ref({
    "name":''
})

const remoteMethod = async (query: string) => {

    console.log(query)
    name.value.name = query;
    let result = await api.getParams("/api/brands",name.value);

    // 发送数据
  if (query) {

    loading1.value = true
    options.value = result.data;
  } else {
    options.value = []
  }
}

</script>

<template>


<!-- 搜素框 -->
  <!-- 让里面的div并排显示,并且居中 -->
  <div style="margin-top: 15px;display: flex; column-gap: 20px;margin-bottom: 50px;">

    <!-- 使每个输入框间隔20px -->
    <div style="margin-right: 30px;margin-left: 140px;">
      <el-input clearable="true" v-model="checkObj.plateNumber" style="width: 160px" size="large" placeholder="车牌号码" />
    </div>

    <div style="margin-right: 30px;margin-left: 140px;">
      <el-select
      clearable="true"
        v-model="valueRemote1"
        filterable
        remote
        placeholder="请选择品牌"
        style="width: 160px"
        :remote-method="remoteMethod1"
        multiple-limit="1"
        size="large"
  >
    <el-option
      v-for="item in options1"
      :key="item.id"
      :label="item.name"
      :value="item.id"
    />
  </el-select>
    </div>
    <div  style="margin-right: 30px;margin-left: 140px;">
      <el-input clearable="true" v-model="checkObj.preLeasePrice" style="width: 160px" size="large" placeholder="预租价格" />
    </div>

    <div style="margin-right: 30px;margin-left: 140px;">
        <el-select v-model="checkObj.status" placeholder="车辆状态" clearable="true" style="width: 160px" size="large">
      <el-option label="可用" value="available" />
      <el-option label="已租" value="rented"/>
      <el-option label="维修中" value="maintenance" />
    </el-select>
    </div>
  </div>
<!-- 搜素框 -->


<!-- 按钮 -->
  <!-- 让此div中的按钮，每个间隔30px-->
  <div style="margin-bottom: 30px;display: flex; justify-content: center; align-items: center; column-gap: 50px;">

    <el-tooltip placement="top" content="添加车辆" effect="customized">
      <el-button type="primary" @click="insertDate()" round :icon="Plus" style="width: 80px;" />
    </el-tooltip>
    <el-tooltip placement="top" content="删除车辆" effect="customized">
      <el-button type="danger" @click="deleteByIds()" round :icon="Minus" style="width: 80px;" />
    </el-tooltip>
    <el-tooltip placement="top" content="修改车辆" effect="customized">
      <el-button type="warning" @click="updateDate()" round :icon="Edit" style="width: 80px;" />
    </el-tooltip>
    <el-tooltip placement="top" content="查询车辆" effect="customized">
      <el-button type="info" @click="checkData(), pup()" round :icon="Search" style="width: 80px;" />
    </el-tooltip>

  </div>
<!-- 按钮 -->

  <!-- 定义表格数据 -->
  <el-table :data="tableData" style="width: 100%" border highlight-current-row="true" @selection-change="selectL"
    v-loading="loading" stripe="true">
    <el-table-column type="selection" width="70" align="center" />
    <el-table-column label="头像" align="center">

        <template #default="scope">
          <el-image
        style="width: 100px; height: 100px"
        :src="'http://localhost:5173/api/' + scope.row.vehicles.url"
        :zoom-rate="1.2"
        :max-scale="7"
        :min-scale="0.2"
        fit="cover"
        />
      </template>

</el-table-column>
    <el-table-column prop="vehicles.plateNumber" label="车牌号码" align="center" />
    <el-table-column prop="brandName" label="品牌" align="center" />
    <el-table-column prop="vehicles.status" label="车辆状态" align="center" />
    <el-table-column prop="vehicles.preLeasePrice" label="预租价格" align="center" />
    <el-table-column prop="vehicles.updatedAt" label="更新时间" align="center" />

  </el-table>

  <!-- 分页 -->
  <div class="pagination-container" style="margin-top: 30px;margin-right: 50px;">
    <el-pagination v-model:current-page="checkObj.page" v-model:page-size="checkObj.size" :page-sizes="[5, 10, 15, 30]"
      :size="size" :disabled="disabled" background="true" layout="prev, pager, next, sizes" :total="total"
      @size-change="handleSizeChange" @current-change="checkData" />
  </div>


  <el-dialog v-model="editWinvisible" title="车辆编辑" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form style="max-width: 600px" :model="editObject" :size="formSize" status-icon>
      <el-form-item label="车辆图片：" :label-width="120" >
        <el-upload
            class="avatar-uploader"
            action="/api/loading"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            list-type="picture-card"
            :limit="1"
            v-model:file-list="fileList"
          >
            <img v-if="imageUrl" style="opacity: 0;" :src="imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            
          </el-upload>
          <el-dialog v-model="dialogVisible" style="width: 300px;height: 300px;">
            <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 300px;height: 300px;margin-top: -32px;margin-left: -16px;" />
          </el-dialog>
          
      </el-form-item>



      <el-form-item label="车牌号" :label-width="120">
        <el-input v-model="editObject.vehicles.plateNumber" autocomplete="off" />
      </el-form-item>

      <el-form-item label="品牌：" :label-width="120" >
    <el-select
        v-model="valueRemote"
        filterable
        remote
        placeholder="请选择品牌"
        style="width: 240px"
        :remote-method="remoteMethod"
        multiple-limit="1"
        disabled
  >
    <el-option
      v-for="item in options"
      :key="item.id"
      :label="item.name"
      :value="item.id"
    />
  </el-select>

      </el-form-item>

      <el-form-item label="车辆状态：" :label-width="120">
        <el-select v-model="editObject.vehicles.status" placeholder="请选择车辆状态">
          <el-option label="可用" value="0"/>
          <el-option label="已租" value="1" />
          <el-option label="维修中" value="2" />
        </el-select>

      </el-form-item>

      <el-form-item label="预租价格：" :label-width="120">
        <el-input v-model="editObject.vehicles.preLeasePrice" autocomplete="off" />
      </el-form-item>

      
      <el-form-item label="创建时间：" :label-width="120">
        <el-date-picker v-model="editObject.vehicles.createdAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      
      <el-form-item label="更新时间：" :label-width="120">
        <el-date-picker v-model="editObject.vehicles.updatedAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
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


  <el-dialog v-model="addWinvisible" title="新增车辆" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form style="max-width: 600px" :model="addObject" :size="formSize" status-icon>

<!-- #ffffff -->
      <el-form-item label="车辆图片：" :label-width="120" >
        <el-upload
            class="avatar-uploader"
            action="/api/loading"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            list-type="picture-card"
            :limit="1"
          >
            <img v-if="imageUrl" style="opacity: 0;" :src="imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            
          </el-upload>
          <el-dialog v-model="dialogVisible" style="width: 300px;height: 300px;">
            <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 300px;height: 300px;margin-top: -32px;margin-left: -16px;" />
          </el-dialog>
          
      </el-form-item>

      <el-form-item label="车牌号" :label-width="120">
        <el-input v-model="addObject.plateNumber" autocomplete="off" />
      </el-form-item>

      <el-form-item label="品牌：" :label-width="120">
    <el-select
        v-model="valueRemote"
        filterable
        remote
        placeholder="请选择品牌"
        style="width: 240px"
        :remote-method="remoteMethod"
        multiple-limit="1"
  >
    <el-option
      v-for="item in options"
      :key="item.id"
      :label="item.name"
      :value="item.id"
    />
  </el-select>

      </el-form-item>

      <el-form-item label="车辆状态：" :label-width="120">
        <el-select v-model="addObject.status" placeholder="请选择车辆状态">
          <el-option label="可用" value="0"/>
          <el-option label="已租" value="1" />
          <el-option label="维修中" value="2" />
        </el-select>

      </el-form-item>

      <el-form-item label="预租价格：" :label-width="120">
        <el-input v-model="addObject.preLeasePrice" autocomplete="off" />
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
.easybox:hover .easy-dicover{
    display: block;
}
.easy-dicover{
    display: none;
    position: absolute;
    height:100%;
    width:100%;
    top:0;
    left:0;
    background:rgba(0,0,0,0.5);
}
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
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}




.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>