<script lang="ts" setup>
import { ref, onMounted, nextTick,reactive } from "vue";
import api from '../api/index.js'
import { ElMessage, ElMessageBox, ElNotification,UploadProps, UploadUserFile  } from 'element-plus'
import { Delete, Plus, Edit, Minus, Search, Share, Upload } from '@element-plus/icons-vue'
import { fi } from "element-plus/es/locale/index.mjs";

const tableData = ref([{
  "url":''
}]);

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

  console.log(checkObj.value.startTime)

  // 查询数据
  let result = await api.getParams("/api/users", checkObj.value)

  // 更新表格数据
  tableData.value = result.data.data;
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
    let result = await api.delByIds("/api/users", listSelectId);
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


  console.log(listSelect[0])

  // 回显数据
  // editObject.value = listSelect[0];

  editObject.value = JSON.parse(JSON.stringify(listSelect[0]));
  console.log("编辑窗口中==============")
  console.log(editObject.value)
  console.log("编辑窗口中==============")



  console.log()
  if(!editObject.value.url || editObject.value.url == '/undefined'){
    return;
  }
  fileList.value[0] = [];

  console.log("================..===.=================")
  console.log(editObject.value.url)

  imageUrl.value = "api" + editObject.value.url;

  fileList.value[0].url = imageUrl.value;

}

// 定义变量，控制编辑窗口是否显示
const editWinvisible = ref(false);

// 点击保存按钮时，触发此方法保存修改数据
const addEditSave = async () => {
  console.log("============保存==========")


  console.log(imageUrl.value)
  editObject.value.url = imageUrl.value;

  let result = await api.putJson("/api/users", editObject.value)


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
const addOrderSave = async (formEl: FormInstance | undefined) => {
  console.log("============新增==========")
  console.log(addObject)

  if (!formEl) return

  await formEl.validate(async (valid, fields) => {


    if (valid) {
  console.log("图片================")
  console.log(imageUrl.value)
  console.log("图片================")
  addObject.value.url = imageUrl.value;

  console.log("============新增==========")

  // 开始保存
  let result = await api.postJson("/api/users", addObject.value)

  if (result.code === 200) {
    // 刷新数据
    checkData();
    // 关闭新增窗口
    addWinvisible.value = false;

    ElMessage({
      type: 'success',
      message: '新增成功：修改行数为' + result.data
    })
  } else {
    ElMessage({
      type: 'warning',
      message: '新增失败：' + result.message
    })
  }
}})
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
  let result = await api.putJson("/api/users/img",editObject.value);
  console.log(result)
}

// 上传头像，绑定的数据
const imageUrl = ref();

// 上传头像成功
const handleAvatarSuccess = (result,uploadFile)  => {
  
  console.log(result.value)
  console.log(uploadFile)
  console.log("============上传成功==========")
  imageUrl.value = "api/" + result.data;

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



const comCarObject = ref({
  "brandId":'',
  "number":'',
  "actualRentalPrice":'请先选择车辆',
  "comCarObject":'请先选择车辆'
});

const returnCarObject = ref({})




// 点击租车按钮
const comeCar = () => {
  console.log("============租车==========")



  // 如果勾选数据为空，提示用户
  if (listSelect.length == 0) {
    ElMessage({
      type: 'warning',
      message: '请选择要租车的用户'
    })
    return;
  }
  // 如果勾选数据多于1条，提示用户
  if (listSelect.length > 1) {
    ElMessage({
      type: 'warning',
      message: '只能选择一个用户'
    })
    return;
  }

  // 在弹出表单时，清空表单数据
  comCarObject.value = {}

  valueRemote.value = [];
  valueRemote1.value = [];


  options.value = [];
  options1.value =  [];



  // 设置预租价格
  comCarObject.value.preLeasePrice = '请先选择品牌'
  // 设置实租价格
  comCarObject.value.actualRentalPrice = '请先选择品牌'
  comCarObject.value.deposit = '请先选择品牌'
  comCarObject.value.amount = '请先选择品牌'
  // 弹出表单
  comeCarWinvisible.value = true;
}
const returnCar = () => {

  console.log("============还车==========")

  if (listSelect.length == 0) {
    ElMessage({
      type: 'warning',
      message: '请选择要租车的用户'
    })
    return;
  }
  // 如果勾选数据多于1条，提示用户
  if (listSelect.length > 1) {
    ElMessage({
      type: 'warning',
      message: '只能选择一个用户'
    })
    return;
  }

  // 清空品牌，车牌号
  valueRemote2.value = [];
  options2.value = [];
  valueRemote3.value = [];
  options3.value = [];

  //出现弹窗
  returnCarWinvisible.value = true;
}


const comeCarWinvisible = ref(false);


const addComeSave = async () => {
  console.log("保存租车信息。。。。。。。。。。。。")

  // 将品牌id，车辆id存入comCarObject

  // comCarObject.value.brandId = valueRemote.value;
  comCarObject.value.userId = listSelectId[0];
  comCarObject.value.vehicleId = valueRemote1.value;
  console.log(comCarObject.value)

  // 发送请求
  let result = await api.postJson("/api/users/carRental",comCarObject.value);

  console.log("==========")
  console.log(result)
  console.log("==========")



  if (result.code === 200) {
      // 关闭窗口
  comeCarWinvisible.value = false;
    ElMessage({
      type: 'success',
      message: result.message
    })
  }else if(result.code === 301){
    comeCarWinvisible.value = false;
    ElMessage({
      type: 'warning',
      message: result.message
    })
  }
  else{
    ElMessage({
      type: 'warning',
      message: result.message
    })
  }
}

// 远程查询品牌
const options = ref<ListItem[]>([])
const valueRemote = ref<string[]>([])
const loading1 = ref(false)
// 远程查询车牌号
const options1 = ref<ListItem[]>([])
const valueRemote1 = ref<string[]>([])
const loading2 = ref(false)

const options2 = ref<ListItem[]>([])
const valueRemote2 = ref<string[]>([])
const loading3 = ref(false)

const options3 = ref<ListItem[]>([])
const valueRemote3 = ref<string[]>([])
const loading4 = ref(false)

const name = ref({
    "name":''
})


const remoteMethod2 = async (query: string) => {


  console.log("============远程查询品牌remoteMethod2==========")

  let result = await api.getById("/api/users",listSelectId[0])

  options2.value = result.data.brands;
  options3.value = result.data.vehicles;

}


const remoteMethod = async (query: string) => {

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
const remoteMethod1 = async () => {
  console.log("============远程查询车牌号==========")

  // 获取，品牌id
  let brandId = valueRemote.value;

  if (!brandId) {
    // 品牌已清空
    options1.value = []
    return;
  }
  // 发送请求
  let result = await api.getById("/api/vehicles/number",brandId);

  // 将车牌号赋值
  options1.value = result.data;
}


const handleSelectChange1 = () => {
  console.log("当品牌变化时，触发。。。。。。。。")


  console.log(valueRemote1.value)
  console.log(options1.value)

  // 如果品牌已清空
  if(!valueRemote.value){
    options1.value = []
    valueRemote1.value = ''

    comCarObject.value.preLeasePrice = '请先选择品牌'
    comCarObject.value.actualRentalPrice = '请先选择品牌'
    comCarObject.value.deposit = '请先选择品牌'
    comCarObject.value.amount = '请先选择品牌'
  }else {
    comCarObject.value.preLeasePrice = '请先选择车辆'
    comCarObject.value.actualRentalPrice = '请先选择车辆'
    comCarObject.value.deposit = '请先选择车辆'
    comCarObject.value.amount = '请先选择车辆'
  }

}

const handleSelectChange = ()=> {
  if (valueRemote1.value){
    for(let i=0;i<options1.value.length;i++){
      if(options1.value[i].id == valueRemote1.value){
        comCarObject.value.preLeasePrice = options1.value[i].preLeasePrice
      }
    }
  // 设置实租价格
  comCarObject.value.actualRentalPrice = ''
  comCarObject.value.deposit = ''
  comCarObject.value.amount = ''
  // comCarObject.value.preLeasePrice=''
  }else{
    comCarObject.value.preLeasePrice = '请先选择车辆'
    comCarObject.value.actualRentalPrice = '请先选择车辆'
    comCarObject.value.deposit = '请先选择车辆'
    comCarObject.value.amount = '请先选择车辆'
  }
}

const handleSelectChange2 = ()=>{
  console.log("还车品牌变化时，触发。。。。。。。。")


  // 如果品牌数量不存在时，清空车牌号
  if(!valueRemote2.value){
    options3.value = []
    options2.value = []
    valueRemote3.value = ''
  }
}


const returnCarWinvisible = ref(false);



const returnCarSave = async () => {
  console.log("保存还车信息。。。。。。。。。。")

  console.log("品牌id")
  console.log(valueRemote2.value)
  console.log("车牌号id")
  console.log(valueRemote3.value)



  returnCarObject.value.userId = listSelectId[0];
  returnCarObject.value.vehicleId = valueRemote3.value;
  console.log("发送数据")
  console.log(returnCarObject.value)

  let result = await api.putJson("/api/users/return",returnCarObject.value)

  if (result.code === 200) {
      // 关闭窗口
  returnCarWinvisible.value = false;
    ElMessage({
      type: 'success',
      message: result.message
    })
  }else if(result.code === 301){
    returnCarWinvisible.value = false;
    ElMessage({
      type: 'warning',
      message: result.message
    })
  }
  else{
    ElMessage({
      type: 'warning',
      message: result.message
    })
  }


  console.log(result);
}


const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules<RuleForm>>({
  phone: [
    {
      required: true,
      message: '请输入手机号',
      trigger: 'change'
    },
    { min: 3, max: 11, message: '长度在 3 到 11 个字符', trigger: 'blur' },
    { pattern: /^[\d]{3,11}$/, message: '输入格式不正确', trigger: 'change' }
  ],
  email: [
  {
      required: true,
      message: '请输入邮箱',
      trigger: 'change'
    },
    { type: 'email', message: '邮箱格式不正确', trigger: 'change' }
  ],
  username: [
    {
      required: true,
      message: '请输入用户名',
      trigger: 'change'
    },
  ],
  nickname: [
    {
      required: true,
      message: '请输入用户昵称',
      trigger: 'change'
    },
  ],
  createdAt: [
    {
      required: true,
      message: '请选择创建日期',
      trigger: 'change'
    },
  ],
  updatedAt: [
    {
      required: true,
      message: '请选择更新日期',
      trigger: 'change'
    },
  ],
});
</script>

<template>

  <!-- 让里面的div并排显示,并且居中 -->
  <div style="margin-top: 15px;display: flex; column-gap: 40px;margin-bottom: 50px;">

    <!-- 使每个输入框间隔20px -->
    <div style="margin-right: 30px;margin-left: 140px;">
      <el-input v-model="checkObj.username" style="width: 160px" size="large" placeholder="用户名" />
    </div>
    <div style="margin-right: 30px;margin-left: 10px;">
      <el-input v-model="checkObj.email" style="width: 160px" size="large" placeholder="电子邮件" />
    </div>
    <div style="margin-right: 30px;margin-left: 10px;">
      <el-input v-model="checkObj.phone" style="width: 160px" size="large" placeholder="手机号" />
    </div>
    <div style="margin-right: 30px;margin-left: 10px;">
        <el-date-picker v-model="checkObj.createdAt" style="width: 160px" size="large" type="date" placeholder="创建日期" value-format="YYYY-MM-DD" />
    </div>
    <div style="margin-right: 30px;margin-left: 10px;">
        <el-date-picker v-model="checkObj.updatedAt" style="width: 160px" size="large" type="date" placeholder="更新日期" value-format="YYYY-MM-DD" />
    </div>
  </div>


  <!-- 让此div中的按钮，每个间隔30px-->

  <div style="margin-bottom: 30px;display: flex; column-gap: 100px;margin-left: 580px;">
    <div style="margin-right: 200px;">
    <el-tooltip placement="top" content="租车" effect="customized">
      <el-button type="primary" @click="comeCar"  style="width: 80px;" round>租车</el-button>
    
    </el-tooltip>
    <el-tooltip placement="top" content="还车" effect="customized">
      <el-button type="success" @click="returnCar" style="width: 80px;" round>还车</el-button>
    </el-tooltip>
  </div>

  <!-- 靠右侧 -->
  <div style="margin-left: 50px;">
    <el-tooltip placement="top" content="添加用户" effect="customized">
      <el-button type="primary" @click="insertDate()" round :icon="Plus" style="width: 80px;" />
    </el-tooltip>
    <el-tooltip placement="top" content="删除用户" effect="customized">
      <el-button type="danger" @click="deleteByIds()" round :icon="Minus" style="width: 80px;" />
    </el-tooltip>
    <el-tooltip placement="top" content="修改用户" effect="customized">
      <el-button type="warning" @click="updateDate()" round :icon="Edit" style="width: 80px;" />
    </el-tooltip>
    <el-tooltip placement="top" content="查询用户" effect="customized">
      <el-button type="info" @click="checkData(), pup()" round :icon="Search" style="width: 80px;" />
    </el-tooltip>
  </div>
  </div>


  <!-- 定义表格数据 -->
  <el-table :data="tableData" style="width: 100%" border highlight-current-row="true" @selection-change="selectL"
    v-loading="loading" stripe="true">
    <el-table-column type="selection" width="70" align="center" />
    <el-table-column label="头像" align="center">
      <template #default="scope">
        <el-image
      style="width: 100px; height: 100px"
      :src="'http://localhost:5173/api/' + scope.row.url"
      fit="cover"
    />
    </template>

    </el-table-column>

    <el-table-column prop="username" label="用户名" align="center" />
    <el-table-column prop="nickname" label="用户昵称" align="center" />
    <el-table-column prop="email" label="电子邮件" align="center" />
    <el-table-column prop="phone" label="手机号" align="center" />


    <el-table-column prop="createdAt" label="创建时间" align="center" />
    <el-table-column prop="updatedAt" label="更新时间" align="center" />



  </el-table>


  <!-- 分页 -->
  <div class="pagination-container" style="margin-top: 30px;margin-right: 50px;">
    <el-pagination v-model:current-page="checkObj.page" v-model:page-size="checkObj.size" :page-sizes="[5, 10, 15, 30]"
      :size="size" :disabled="disabled" background="true" layout="prev, pager, next, sizes" :total="total"
      @size-change="handleSizeChange" @current-change="checkData" />
  </div>


  <el-dialog v-model="editWinvisible" title="用户编辑" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form style="max-width: 600px" :model="editObject" :size="formSize" status-icon>

      <el-form-item label="用户头像：" :label-width="120" >
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
        <el-form-item label="用户ID" :label-width="120">
        <el-input v-model="editObject.id" disabled autocomplete="off" />
      </el-form-item>
      <el-form-item label="用户名" :label-width="120">
        <el-input v-model="editObject.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="用户昵称：" :label-width="120">
        <el-input v-model="editObject.nickname" autocomplete="off" />
      </el-form-item>
      <el-form-item label="电子邮件" :label-width="120">
        <el-input v-model="editObject.email" autocomplete="off" />
      </el-form-item>

      <el-form-item label="手机号" :label-width="120">
        <el-input v-model="editObject.phone" autocomplete="off" />
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


  <el-dialog v-model="addWinvisible" title="新增用户" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form ref="ruleFormRef" style="max-width: 600px" :model="addObject"  :rules="rules" :size="formSize" status-icon>

<!-- #ffffff -->
      <el-form-item label="用户头像：" :label-width="120" >
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

      <el-form-item label="用户名：" prop="username"  :label-width="120">
        <el-input v-model="addObject.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="用户昵称：" prop="nickname" :label-width="120">
        <el-input v-model="addObject.nickname" autocomplete="off" />
      </el-form-item>
      <el-form-item label="电子邮件：" prop="email" :label-width="120">
        <el-input v-model="addObject.email" autocomplete="off" />
      </el-form-item>

      <el-form-item label="手机号：" prop="phone" :label-width="120">
        <el-input v-model="addObject.phone" autocomplete="off" />
      </el-form-item>


      <el-form-item label="创建时间：" prop="createdAt" :label-width="120">
        <el-date-picker v-model="addObject.createdAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      
      <el-form-item label="更新时间：" prop="updatedAt" :label-width="120">
        <el-date-picker v-model="addObject.updatedAt" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
      </el-form-item>

    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addWinvisible = false">取消</el-button>
        <el-button type="primary" @click="addOrderSave(ruleFormRef)">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="comeCarWinvisible" title="用户租车" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form style="max-width: 600px" :model="comCarObject" :size="formSize" status-icon>

<!-- #ffffff -->

<!-- 1.用户先选择感兴趣的品牌 -->
 <!-- 2.根据用户所选择的品牌，来查询所有符合条件的车辆，用户自己勾选 -->
      <el-form-item label="品牌：" :label-width="120">
        <el-select
        v-model="valueRemote"
        filterable
        remote
        placeholder="请选择品牌"
        style="width: 240px"
        :remote-method="remoteMethod"
        multiple-limit="1"
        clearable="true"
        @change="handleSelectChange1"
  >
    <el-option
      v-for="item in options"
      :key="item.id"
      :label="item.name"
      :value="item.id"
    />
  </el-select>
      </el-form-item>
      <el-form-item label="车牌号码：" :label-width="120">
        <el-select
        v-model="valueRemote1"
        remote
        placeholder="请选择车牌号"
        style="width: 240px"
        multiple-limit="1"
        @click="remoteMethod1"
        clearable="true"
        @change="handleSelectChange"
  >
    <el-option
      v-for="item in options1"
      :key="item.id"
      :label="item.plateNumber"
      :value="item.id"
    />
  </el-select>
      </el-form-item>
      <el-form-item label="预租价格：" :label-width="120">
        <el-input v-model="comCarObject.preLeasePrice" disabled autocomplete="off" />
      </el-form-item>
      <el-form-item label="实租价格：" :label-width="120">
        <el-input v-if="valueRemote1 != null && valueRemote1.length!=0" v-model="comCarObject.actualRentalPrice" autocomplete="off" />
          <el-input v-else v-model="comCarObject.actualRentalPrice" disabled autocomplete="off" />
      </el-form-item>
      <el-form-item label="押金：" :label-width="120">
        <el-input v-if="valueRemote1 != null && valueRemote1.length!=0" v-model="comCarObject.deposit" autocomplete="off" />
          <el-input v-else v-model="comCarObject.deposit" disabled autocomplete="off" />
      </el-form-item>
      <el-form-item label="支付金额：" :label-width="120">
        <el-input v-if="valueRemote1 != null && valueRemote1.length!=0" v-model="comCarObject.amount" autocomplete="off" />
          <el-input v-else v-model="comCarObject.amount" disabled autocomplete="off" />
      </el-form-item>

      <el-form-item label="支付方式：" :label-width="120">
        <el-select v-model="comCarObject.paymentMethod" placeholder="支付方式" style="width: 200px" size="large">
          <el-option label="信用卡" value="0" />
          <el-option label="支付宝" value="1"/>
          <el-option label="现金" value="2" />
        </el-select>
      </el-form-item>

      <el-form-item label="还车时间：" :label-width="120">
        <el-date-picker v-model="comCarObject.preReturnTime" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      <!-- 押金 -->
       <!-- 支付方式 -->
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="comeCarWinvisible = false">取消</el-button>
        <el-button type="primary" @click="addComeSave()">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="returnCarWinvisible" title="用户还车" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form style="max-width: 600px" :model="returnCarObject" :size="formSize" status-icon>

<!-- #ffffff -->

<!-- 1.用户先选择感兴趣的品牌 -->
 <!-- 2.根据用户所选择的品牌，来查询所有符合条件的车辆，用户自己勾选 -->
      <el-form-item label="品牌：" :label-width="120">
        <el-select
        v-model="valueRemote2"
        remote
        placeholder="请选择品牌"
        style="width: 240px"
        multiple-limit="1"
        clearable="true"
        @click="remoteMethod2"
        @change="handleSelectChange2"
  >
    <el-option
      v-for="item in options2"
      :key="item.id"
      :label="item.name"
      :value="item.id"
    />
  </el-select>
      </el-form-item>



      
      <el-form-item label="车牌号码：" :label-width="120">
        <el-select
        v-model="valueRemote3"
        remote
        placeholder="请选择车牌号"
        style="width: 240px"
        multiple-limit="1"
        clearable="true"
  >
    <el-option
      v-for="item in options3"
      :key="item.id"
      :label="item.plateNumber"
      :value="item.id"
    />
        </el-select>
      </el-form-item>

      <!-- 押金 -->
       <!-- 支付方式 -->
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="returnCarWinvisible = false">取消</el-button>
        <el-button type="primary" @click="returnCarSave()">
          还车
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
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
</style>