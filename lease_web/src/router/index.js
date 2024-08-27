import {createRouter,createWebHashHistory} from  'vue-router'
import api from '../api/index.js'

const islogin =async (to,from) => {
    // 获取token
    let token = sessionStorage.getItem('token');

    let result =await api.get("/api/logins")

    console.log(result)
    if(!token || result.code != 200){
        return{path:'/login'}
    }
}

const router=createRouter(
    {
    history:createWebHashHistory(),
    routes:[
        {path: '/',redirect: 'login'},
        {path:'/login',component:()=>import("../vue/login.vue")},
        {path:'/code',component:()=>import("../vue/code.vue")},
        { path: '/homepage',component:()=>import("../vue/homepage.vue"),
            children:[
                // 所有的内容都在这里写
                {path:'/orders',component:() => import("../vue/orders.vue")},
                {path:'/brands',component:() => import("../vue/brands.vue")},
                {path:'/logs',component:() => import("../vue/sysLogs.vue")},
                {path:'/msg',component:() => import("../vue/msg.vue")},
                {path:'/vehicles',component:() => import("../vue/vehicles.vue")},
                {path:'/reviews',component:() => import("../vue/reviews.vue")},
                {path:'/users',component:() => import("../vue/users.vue")},
                {path:'/hisUsers',component:() => import("../vue/h_users.vue")},
                {path:'/reportform',component:() => import("../vue/reportform.vue")},
                {path:'/pays',component:() => import("../vue/pays.vue")},
                {path:'/rentals',component:() => import("../vue/rentals.vue")},
                {path:'/hisOrders',component:() => import("../vue/h_orders.vue")},
                {path:'/hisPays',component:() => import("../vue/h_pays.vue")},
                {path:'/hisRentals',component:() => import("../vue/h_rentals.vue")},
            ],
            // hisUsers
            beforeEnter:islogin
        }
    ]
    }
);

export default router;