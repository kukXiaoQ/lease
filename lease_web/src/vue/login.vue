<template>

  <div class="login-container">
    <el-card style="width: 550px; padding: 10px;">
      <template #header>
        <div class="card-header">
          <span>{{ cardName }}</span>
        </div>
      </template>

      <el-tabs :tab-position="tabPosition" 
        style="height: 300px" 
        class="demo-tabs" 
        @tab-click="handleClick" 
        v-model="editableTabsValue" 
        @tab-change="handleTabChange"
        >
    <el-tab-pane label="用户登录" name="login">
      <el-form ref="ruleFormRef" style="width: 330px;" :model="loginForm" :rules="rules" label-width="auto"
        :size="formSize" status-icon>


        <!--  -->

        <el-form-item label="手机号：" prop="phone">
          <el-input v-model="loginForm.phone" />
        </el-form-item>
        <el-form-item label="密码：" prop="password" style="margin-top: 25px;">
          <el-input v-model="loginForm.password" type="password" />
        </el-form-item>
        <el-form-item label="验证码：" prop="code" label-width="80" style="margin-top: 25px;">
          <div style="display: flex;">
            <img :src="captchaImageUrl" @click="refreshCaptcha" alt="验证码" style="width: 100px; height: 30px;" />
            <el-input v-model="loginForm.code" style="margin-left: 10px;" placeholder="请输入验证码" />
          </div>
        </el-form-item>
        <div style="display: flex;margin-top: 70px;">
          <el-button type="primary" @click="submitForm(ruleFormRef)">
            登录
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        </div>
      </el-form>
    </el-tab-pane>

    <el-tab-pane label="忘记密码" name="forgot">
      <el-form ref="ruleFormRef1" style="width: 330px;" :model="forgotPassword" :rules="rules2" label-width="auto"
        :size="formSize1" status-icon>

        <el-form-item label="手机号：" prop="phone">
          <el-input  v-model="forgotPassword.phone" />
        </el-form-item>
        <el-form-item label="邮箱：" prop="email" style="margin-top: 25px;">
          <el-input v-model="forgotPassword.email"/>
        </el-form-item>
        

        <el-form-item label="验证码：" prop="code" label-width="80" style="margin-top: 25px;">
          <div style="display: flex;">
            <el-button type="primary" @click="sendCode()" style="width: 100px; height: 30px;">
            发送验证码
          </el-button>
            <el-input v-model="forgotPassword.code" style="margin-left: 10px;" placeholder="请输入验证码" />
          </div>
        </el-form-item>

        
      
        <div style="display: flex;margin-top: 70px;">
          <el-button type="primary" @click="submitForm1(ruleFormRef1)">
            确定
          </el-button>
          <el-button @click="resetForm1(ruleFormRef1)">重置</el-button>
        </div>

      </el-form>
    </el-tab-pane>
  </el-tabs>
    </el-card>
  </div>
</template>



<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import { ComponentSize, FormInstance, FormRules,ElMessage, ElMessageBox, ElNotification,TabsInstance } from 'element-plus'
import api from '../api/index.js'
import { useRouter } from 'vue-router'
import dentify from "./code.vue";
import axios from 'axios';
const tabPosition = ref<TabsInstance['tabPosition']>('left')
const identifyCodes = "1234567890abcdefjhijklinopqrsduvwxyz"; // 随机串内容, 从这里随机抽几个显示验证码
const identifyCode = ref(""); // 验证码图片内容


// 初始化验证码图像 URL
const captchaImageUrl = ref('');
// 刷新验证码
const refreshCaptcha = () => {
  getCaptcha();
};

const getCaptcha = async () => {
  try {
    const response = await axios.get('/api/captcha/get', { responseType: 'arraybuffer' });
    const imageUrl = URL.createObjectURL(new Blob([response.data], { type: 'image/jpeg' }));
    captchaImageUrl.value = imageUrl;
  } catch (error) {
    console.error('Error fetching captcha:', error);
  }
};
// 验证验证码有效性
const checkCaptcha = async (captcha) => {
  try {
    const response = await axios.get(`/api/captcha/check?captcha=${captcha}`);
    return response.data;
  } catch (error) {
    console.error('Error checking captcha:', error);
    return false;
  }
}


const handleTabChange = (tabName: string) => {
  // 切换当前标签页名称
  editableTabsValue.value = tabName
  console.log(tabName)
  cardName.value = tabName

  if (tabName === 'forgot') {
    // 忘记密码，刷新验证码
    cardName.value = "忘记密码"
    loginForm.value = {}

  }
  else{
    // 用户登录，刷新验证码
    
    forgotPassword.value = {}
    refreshCode();
    cardName.value = "用户登录"
  }

  

  console.log(loginForm.value)
  console.log(forgotPassword.value)
  console.log("=======================")
}

const cardName = ref('用户登录');
const editableTabsValue = ref('login')

const handleClick = () => {
  console.log(1231231)
}


onMounted(() => {
  // 初始化验证码
  getCaptcha();
  // 检查 sessionStorage 中是否存储了用户名和密码
  const storedUsername = sessionStorage.getItem("name");
  const storedPassword = sessionStorage.getItem("password");

  if (storedUsername && storedPassword) {
    loginForm.value.phone = storedUsername;
    loginForm.value.password = storedPassword;
    rememberPassword.value = true;
  }

});

const refreshCode = () => {
  identifyCode.value = "";
  makeCode(identifyCodes, 4);
};

const makeCode = (o, l) => {
  for (let i = 0; i < l; i++) {
    // 通过循环获取字符串内随机几位
    identifyCode.value += o[randomNum(0, o.length)];
  }
};

const randomNum = (min, max) => {
  return Math.floor(Math.random() * (max - min) + min);
};


const router = useRouter()
const ruleFormRef = ref<FormInstance>()
const ruleFormRef1 = ref<FormInstance>()
const loginForm = ref({})

// 定义表单规则
interface RuleForm {
  phone: string
  password: string
  code:string
}

const formSize = ref<ComponentSize>('default')
const formSize1 = ref<ComponentSize>('default')


//    正则表达式
//    表示字符位数量  {N} {N,M}  {N,}   ?有或有一个   +最少有一个   *任意个数
//    中括号表示一个字符位  [ab]  这个字符位可以是a也可以是b
//[]   [a-z]   代表a到z   [0-8]     [abc]{8,11}
//     .  代表任意字符    .{20}

//   元字符    \d   0-9     \w  0-9_A-z   \D  排除掉所有数字    \W
//[0-9A-z]{6,16}
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
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'change',
    },
    {
      min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'
    }
  ],
  code: [
    {
      required: true,
      message: '请输入验证码',
      trigger: 'change',
    },
  ],
  
})

const rules2 = reactive<FormRules<RuleForm>>({
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
  code: [
    {
      required: true,
      message: '请输入验证码',
      trigger: 'change'
    },
  ],
});


// 点击发送验证码按钮，发送验证码
const sendCode = async () => {
  console.log("============OK1=============")
  
  // 发送验证码
  // 电话、邮箱

  if(!forgotPassword.value.phone || !forgotPassword.value.email){
      console.log("手机号或邮箱不能为空");
      ElMessage({
        type: 'warning',
        message: '手机号或邮箱不能为空'
      })
      return;
  }

  // 发送请求
  let result = await api.postJson("/api/logins/msg",forgotPassword.value);
  if (result.code == 200) {
    ElMessage({
          type: 'success',
          message: result.message
        })
  }else{
    ElMessage({
          type: 'error',
          message: result.message
    })
  }
}


// 点击忘记密码按钮，保存
const submitForm1 = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  console.log("============OK1=============")

  await formEl.validate(async (valid, fields) => {
    if (valid) {

      // 校验成功
      // 发送请求
      let result = await api.putJson("/api/users/forgot",forgotPassword.value);

      console.log("获取相应信息")
      console.log(result)

      if(result.code == 200){
        // 修改成功
        ElMessage({
          type: 'success',
          message: '密码重置成功！'
    })
    forgotPassword.value = {}
    loginForm.value = {}
      }else{
        // 修改失败
        ElMessage({
          type: 'warning',
          message: result.message
    })
      }
    }else{
      ElMessage({
          type: 'warning',
          message: '请输入正确的数据！'
    })
    }
  })


}

// 点击登录按钮，验证表单
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  await formEl.validate(async (valid, fields) => {


    if (valid) {

      // 验证码验证
      console.log("验证码验证开始");
      console.log(loginForm.value.code);
      const captchaValid = await checkCaptcha(loginForm.value.code);

      console.log("验证码验证结果：", captchaValid);

      if (!captchaValid) {
        ElMessage({
          type: 'warning',
          message: '验证码错误，请重新输入！'
        })
        refreshCaptcha();
        resetForm(formEl)
        return
      }

      // 表单验证成功！
      let result = await api.postJson('/api/logins', loginForm.value);

      if (result.code === 200 && result.data) {
        // 登录成功，跳转到首页
        ElMessage({
      type: 'success',
      message: '登录成功！'
    })
        // 存放token
        sessionStorage.setItem('token', result.data.token);


        setTimeout(() => {

          // 将username传递
          sessionStorage.setItem('username', result.data.username);


          // 记住密码开始
          console.log("是否记住密码：", rememberPassword.value);
        if (rememberPassword.value) {
          sessionStorage.setItem("phone", loginForm.value.phone);
          sessionStorage.setItem("password", loginForm.value.password);
        } else {
          sessionStorage.removeItem("name");
          sessionStorage.removeItem("password");
        }
        // 记住密码结束
          router.push({ path: 'reportform'});

        }, 1000);
      } else {
        ElMessage({
      type: 'warning',
      message: '用户名或密码错误，请重新输入！'
    })
    refreshCaptcha();
        // 登录失败，清空表单
        resetForm(formEl)
      }
    } else {
      ElMessage({
      type: 'warning',
      message: '请输入正确的数据！'
    })
    refreshCaptcha();
    }
  })
}


const forgotPassword = ref({})

const rememberPassword = ref(false)

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return

  console.log("============OK=")
  formEl.resetFields()
}

const resetForm1 = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  console.log("============OK1=")
  formEl.resetFields()

}

</script>


<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.el-tabs--right .el-tabs__content,
.el-tabs--left .el-tabs__content {
  height: 100%;
}
</style>

<style scoped>

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('../assets/login.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.login-box {
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 5px #8e1a1a4d;
}

/* 修改输入框周围的样式 */
.el-form-item {
  margin-bottom: 15px; /* 调整底部间距 */
}

/* 修改输入框的样式 */
.el-input {
  width: 100%; /* 设置宽度为100% */
}



button {
  width: 100%; /* 让按钮宽度与容器相同 */
  padding: 10px;
  background-color: #508dce;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}
button:hover {
  background-color: #174e89;
}
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.card-header {
  text-align: center;
  font-size: 1.4rem;
  font-weight: bold;
}

.button-container {
  /* display: flex; */
  /* align-items: center; */
  /* justify-content: center; */
  /* margin-top: 20px; */
  /* justify-content: center; */
  /* gap: 90px; */
  /* 按钮之间的间距 */
}

.demo-tabs>.el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
