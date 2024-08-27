import http from '../util/http.js';

const API={
    get:(url)=>{return http({url:url,method:'get'})},
    getById:(url,id) => {
        if(id === undefined){
            return http({url:url,method:'get'})
        }
        url = url + '/' + id;
        return http({url:url,method:'get'})},
    del:(url,id) => {
        url = url + '/' + id;
        return http({url:url,method:'delete'})},
    delByIds:(url,id) => {return http({url:url,method:'delete',data:id})},
        
        // 发送表单数据,参数名是params，值为params：用于提交表单数据，会将数据拼接到url后面
    postForm: (url,params) => {return http({url:url,method:'post',params:params})},
    //   发送json数据,使用data参数，值为json数据，第二个是json数据
    postJson: (url,data) => {return http({url:url,method:'post',data:data})},

    putForm: (url,params) => {return http({url:url,method:'put',params:params})},
    //   发送json数据,使用data参数，值为json数据，第二个是json数据
    putJson: (url,data) => {return http({url:url,method:'put',data:data})},

    getParams:(url,params)=>{return http({url:url,method:'get',params:params})}

};
export default API;