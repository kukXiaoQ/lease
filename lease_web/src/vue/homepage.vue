<template>


<!-- unique-opened="true" -->
  <el-container class="layout-container-demo" style="height: 900px">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu 
        class="el-menu-demo" 
        router="true" 
        :default-openeds="['0']"
        :default-active="activeIndex"
        >
          <el-sub-menu index="0">
            <template #title>
              <el-icon><icon-menu /></el-icon>租赁车管Root
            </template>

            <!-- 一号菜单 -->
            <el-sub-menu index="1">
              <template #title>

                <el-icon>
                  <UserFilled />
                </el-icon>用户管理
              </template>
              <el-menu-item index="1-1" route="reviews" @click="addtags1_1()">信誉审核</el-menu-item>
              <el-menu-item index="1-2" route="users" @click="addtags1_2()">用户信息</el-menu-item>
            </el-sub-menu>
            <!-- 二号菜单 -->
            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <ChromeFilled />
                </el-icon>车辆管理
              </template>
              <el-menu-item index="2-1" route="brands" @click="addtags2_1()">车辆品牌</el-menu-item>
              <el-menu-item index="2-2" route="vehicles" @click="addtags2_2()">车辆信息</el-menu-item>
            </el-sub-menu>
            <!-- 三号菜单 -->
            <el-sub-menu index="3">
              <template #title>
                <el-icon>
                  <Promotion />
                </el-icon>记录管理
              </template>
              <el-menu-item index="3-1" route="pays" @click="addtags3_1()">支付记录</el-menu-item>
              <el-menu-item index="3-2" route="orders" @click="addtags3_2()">订单记录</el-menu-item>
              <el-menu-item index="3-3" route="rentals" @click="addtags3_3()">租赁记录</el-menu-item>
            </el-sub-menu>
            <!-- 四号菜单 -->
            <!-- <el-sub-menu index="4">
              <template #title>
                <el-icon>
                  <Comment />
                </el-icon>信誉管理（暂定）
              </template>
              <el-menu-item index="4-1" @click="addtags4_1()">信誉审核</el-menu-item>
              <el-menu-item index="4-2" @click="addtags4_2()">信誉信息</el-menu-item>
            </el-sub-menu> -->
            <!-- 五号菜单 -->
            <el-sub-menu index="5">
              <template #title>
                <el-icon>
                  <Histogram />
                </el-icon>报表管理
              </template>
              <el-menu-item index="5-1" route="reportform" @click="addtags5_1()">报表统计</el-menu-item>
            </el-sub-menu>
            <!-- 六号菜单 -->
            <el-sub-menu index="6">
              <template #title>
                <el-icon>
                  <HelpFilled />
                </el-icon>通知管理
              </template>
              <el-menu-item index="6-1" route="msg" @click="addtags6_1()">通知记录</el-menu-item>
              <el-menu-item index="6-2" route="logs" @click="addtags6_2()">日志记录</el-menu-item>
            </el-sub-menu>
            <!-- 七号菜单 -->
            <el-sub-menu index="7">
              <template #title>
                <el-icon>
                  <DeleteFilled />
                </el-icon>历史数据
              </template>
              <el-menu-item index="7-1" route="hisUsers" @click="addtags7_1()">历史用户</el-menu-item>
              <el-menu-item index="7-2" route="hisOrders" @click="addtags7_2()">历史订单</el-menu-item>
              <el-menu-item index="7-3" route="hisPays" @click="addtags7_3()">历史支付</el-menu-item>
              <el-menu-item index="7-4" route="hisRentals" @click="addtags7_4()">历史租赁</el-menu-item>
            </el-sub-menu>  
          </el-sub-menu>

          <template #title>
            <el-icon><icon-menu /></el-icon>Navigator Two
          </template>





        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>

      <el-header style="display: flex; align-items: center; justify-content: space-between; font-size: 15px">
  <!-- 左侧的标签页部分 -->
  <div>
    <el-tag 
    v-for="tag in dynamicTags" 
    :key="tag" 
    size="large" 
    closable 
    :disable-transitions="false" 
    @close="handleClose(tag)"
    @click="handleClick(tag)"
    class="tag-item"
    >
      {{ tag }}
    </el-tag>
  </div>

  <!-- 右侧的工具栏部分 -->
  <div class="toolbar">
    <el-dropdown>
      <el-icon style="margin-right: 8px; margin-top: 1px">
        <setting />
      </el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="handleLogout()">退出登录</el-dropdown-item>
          <el-dropdown-item @click="handleModifyPassword()">修改密码</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <!-- 对span里面的文字，进行高亮显示 -->
    <span> 欢迎： </span>
    <span style="color:green; margin-right: 10px; font-weight: bold; font-size: 20px"> {{ item.name }}</span>
  </div>
</el-header>





      <el-main>
        <!-- 设置子路由 -->
        <router-view></router-view>
      </el-main>


    </el-container>
  </el-container>




  
  <el-dialog v-model="updPasswordDig" title="修改密码" width="500" align-center center draggable :show-close="false"
    :destroy-on-close="true">
    <el-form style="max-width: 600px" :model="updPassword" :size="formSize" status-icon>

      <el-form-item label="旧密码：" :label-width="120">
        <el-input v-model="updPassword.oldPassword" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item label="新密码：" :label-width="120">
        <el-input v-model="updPassword.newPassword" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item label="确认密码" :label-width="120">
        <el-input v-model="updPassword.confirmPassword" type="password" autocomplete="off" />
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="updPasswordDig = false">取消</el-button>
        <el-button type="primary" @click="updatePassword()">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>



</template>

<script lang="ts" setup>
import { ref,onMounted, nextTick } from 'vue'
import { ChatLineRound, ChromeFilled, Comment, DeleteFilled, GoodsFilled, HelpFilled, Histogram, Menu as IconMenu, Message, Promotion, Setting, ShoppingBag, Tools, UploadFilled, User, UserFilled } from '@element-plus/icons-vue'
// import router from '../router';
import api from '../api/index.js'
import { ElMessage, ElMessageBox,ElInput } from 'element-plus'

import { useRouter,useRoute } from 'vue-router'
import { el, ta } from 'element-plus/es/locale/index.mjs';
const router = useRouter()
const route = useRoute()

onMounted( () => {

    console.log('========初始化=========')

    console.log(sessionStorage.getItem('username'))
    // 对赋值
    item.value.name = sessionStorage.getItem('username')

    console.log('========初始化=========')
})


const activeIndex = ref('0')

// 设置标签页

const addtags1_1 = () => {
  activeIndex.value = '1-1'
  // 向标签数据添加
  if (!dynamicTags.value.includes('信誉审核')){
    dynamicTags.value.push('信誉审核')
  }
}
const addtags1_2 = () => {
  activeIndex.value = '1-2';
  if (!dynamicTags.value.includes('用户信息')){
    dynamicTags.value.push('用户信息')
  }
}

const addtags2_1 = () => {
  activeIndex.value = '2-1'
  if (!dynamicTags.value.includes('车辆品牌')){
    dynamicTags.value.push('车辆品牌')
  }
}

const addtags2_2 = () => {
  activeIndex.value = '2-2'
  if (!dynamicTags.value.includes('车辆信息')){
    dynamicTags.value.push('车辆信息')
  }
}


const addtags3_1 = () => {
  activeIndex.value = '3-1'
  if (!dynamicTags.value.includes('支付记录')){
    dynamicTags.value.push('支付记录')
  }
}
// 添加订单信息标签
const addtags3_2 = () => {
  // 当被点击后，设置高亮
  activeIndex.value = '3-2'
  if (!dynamicTags.value.includes('订单记录')){
    dynamicTags.value.push('订单记录')
  }
}

const addtags3_3 = () => {
  activeIndex.value = '3-3'
  if (!dynamicTags.value.includes('租赁记录')){
    dynamicTags.value.push('租赁记录')
  }
}
const addtags6_1 = () => {
  activeIndex.value = '6-1'
  if (!dynamicTags.value.includes('通知记录')){
    dynamicTags.value.push('通知记录')
  }
}
const addtags6_2 = () => {
  activeIndex.value = '6-2'
  if (!dynamicTags.value.includes('日志记录')){
    dynamicTags.value.push('日志记录')
  }
}



const addtags7_1 = () => {
  activeIndex.value = '7-1'
  if (!dynamicTags.value.includes('历史用户')){
    dynamicTags.value.push('历史用户')
  }
}

const addtags7_2 = () => {
  activeIndex.value = '7-2'
  if (!dynamicTags.value.includes('历史订单')){
    dynamicTags.value.push('历史订单')
  }
}

const addtags7_3 = () => {
  activeIndex.value = '7-3'
  if (!dynamicTags.value.includes('历史支付')){
    dynamicTags.value.push('历史支付')
  }
}

const addtags7_4 = () => {
  activeIndex.value = '7-4'
  if (!dynamicTags.value.includes('历史租赁')){
    dynamicTags.value.push('历史租赁')
  }
}


const addtags5_1 = () => {
  // 当被点击后，设置高亮
  activeIndex.value = '5-1'

  if (!dynamicTags.value.includes('报表统计')){
    dynamicTags.value.push('报表统计')
  }
}

// 标签数据
const dynamicTags = ref([])

// 关闭标签页
const handleClose = (tag: string) => {
  dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1)
  console.log(dynamicTags.value)

  // 获取最后一个标签页
  let lastTag = ''
  if (dynamicTags.value.length > 0){
    lastTag = dynamicTags.value[dynamicTags.value.length - 1]

  if (lastTag === '订单记录'){
    activeIndex.value = '3-2'
    router.push('orders')
  }else if (lastTag === '报表统计'){
    activeIndex.value = '5-1'
    router.push({ path: '/'+ 'reportform'})
  }else if (lastTag === '支付记录'){
    activeIndex.value = '3-1'
    router.push('pays')
  }else if (lastTag === '租赁记录'){
    activeIndex.value = '3-3'
    router.push('rentals')
  }else if (lastTag === '历史订单'){
    activeIndex.value = '7-2'
    router.push({ path: '/'+ 'hisOrders'})
  }else if (lastTag === '历史支付'){
    activeIndex.value = '7-3'
    router.push({ path: '/'+ 'hisPays'})
  }else if (lastTag === '历史租赁'){
    activeIndex.value = '7-4'
    router.push({ path: '/'+ 'hisRentals'})
  }else if(lastTag === '用户信息'){
    activeIndex.value = '1-2'
    router.push('users')
  }else if(lastTag === '历史用户'){
    activeIndex.value = '7-1'
    router.push({ path: '/'+ 'hisUsers'})
  // ... ...
  }else if(lastTag === '信誉审核'){
    activeIndex.value = '1-1'
    router.push('reviews')
  }else if(lastTag === '车辆信息'){
    activeIndex.value = '2-2'
    router.push('vehicles')
  }else if(lastTag === '通知记录'){
    activeIndex.value = '6-1'
    router.push('msg')
  }else if(lastTag === '日志记录'){
    activeIndex.value = '6-2'
    router.push('logs')
  }else if(lastTag === '车辆品牌'){
    activeIndex.value = '2-1'
    router.push('brands')
  }
  
  
  else{
    // 如果标签页为空，则设置默认高亮
    activeIndex.value = '0'
    // 进行路由到首页
    // reportform
  }

}
}

// 点击标签页
const handleClick = (tag: string) => {
  activeIndex.value = tag
  // 进行路由跳转
  if (tag === '订单记录'){
    activeIndex.value = '3-2'
    // router.push({ path: '/'+ 'orders'})
    router.push('orders')
  }else if (tag === '报表统计'){
    activeIndex.value = '5-1'
    router.push({ path: '/'+ 'reportform'})
  }else if (tag === '支付记录'){
    activeIndex.value = '3-1'
    // router.push({ path: '/'+ 'pays'})
    router.push('pays')
  }else if (tag === '租赁记录'){
    activeIndex.value = '3-3'
    // router.push({ path: '/'+ 'rentals'})
    router.push('rentals')
  }else if (tag === '历史订单'){
    activeIndex.value = '7-2'
    router.push({ path: '/'+ 'hisOrders'})
  }else if (tag === '历史支付'){
    activeIndex.value = '7-3'
    router.push({ path: '/'+ 'hisPays'})
  }else if (tag === '历史租赁'){
    activeIndex.value = '7-4'
    router.push({ path: '/'+ 'hisRentals'})
  }else if(tag === '用户信息'){
    activeIndex.value = '1-2'
    router.push('users')
  }else if(tag === '历史用户'){
    activeIndex.value = '7-1'
    router.push({ path: '/'+ 'hisUsers'})
  }else if(tag === '信誉审核'){
    activeIndex.value = '1-1'
    router.push('reviews')
  }else if(tag === '车辆信息'){
    activeIndex.value = '2-2'
    router.push('vehicles')
  }else if(tag === '通知记录'){
    activeIndex.value = '6-1'
    router.push('msg')
  }else if(tag === '日志记录'){ 
    activeIndex.value = '6-2'
    router.push('logs')
  }else if(tag === '车辆品牌'){
    activeIndex.value = '2-1'
    router.push('brands')
  }
}

// 设置token为空
const handleLogout = () => {
  console.log('logout')

  sessionStorage.removeItem('token')
  sessionStorage.removeItem('username')
  router.push({ path: '/login' })
}


const updPasswordDig = ref(false);
const updPassword = ref({});

// 修改密码
const handleModifyPassword = () => {
  // 显示对话框
  console.log('修改密码')
  updPasswordDig.value = true;


}
const updatePassword =async () => {
  console.log('提交修改密码-------')

  // 比较两次密码是否一致
  if (updPassword.value.newPassword !== updPassword.value.confirmPassword){
    ElMessage({
      type: 'warning',
      message: '两次密码不一致'
    })
    return
  }

  // 一致，发送请求

  let result = await api.putJson('/api/users/updPas', updPassword.value);

  console.log(result)
  if (result.code === 200) {
    ElMessage({
      type: 'success',
      message: result.message
    })
    // 关闭对话框
    updPasswordDig.value = false;
    // 清除token
    sessionStorage.removeItem('token')
    sessionStorage.removeItem('username')
    router.push({ path: '/login' })
  }else if (result.code === 301) {
    ElMessage({
      type: 'warning',
      message: result.message
    })
  }
}


const item = ref({
  name: sessionStorage.getItem('username')
})
</script>

<style scoped>

.el-menu-demo {
  --el-menu-active-color: #0D98BA; /* 设置活动菜单项的文本颜色 */
}

.el-menu-item.is-active {
  font-weight: bold; /* 设置活动菜单项的字体加粗 */
}

.tag-item {
  margin-right: 15px; /* 设置每个标签页之间的间隔为10px */
}

.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0px;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>