package com.situ.ws.exception;

import com.situ.ws.bean.common.CommonResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class SpringException {


    @ExceptionHandler(DataIntegrityViolationException.class)
    public CommonResult sqlIntegrityConstraintViolation(DataIntegrityViolationException e){
        System.out.println("拦截异常------DataIntegrityViolationException---------------");
        System.out.println(e.getMessage());

        boolean cannotBeNull = Objects.requireNonNull(e.getMessage()).contains("cannot be null");
        boolean c1 = Objects.requireNonNull(e.getMessage()).contains("Duplicate entry");
        String msg;
        if (cannotBeNull)
            msg = "字段列不能为空";
        else if (c1) {
            msg = "手机号重复";
        } else
            msg = "DataIntegrityViolationException--未知错误";

        return CommonResult.fail(300,msg,null);
    }


    @ExceptionHandler(RuntimeException.class)
    public CommonResult runTimeE(RuntimeException e){
        String message = e.getMessage();
        String msg= null;

        System.out.println(message.contains("Rating must be between 0 and 100"));

        if (message.contains("请选择车辆")){
            msg = "请选择车辆！";
        } else if (message.contains("用户状态异常")) {
            msg = "当前用户状态异常！";
        } else if (message.contains("预租价格状态！")){
            msg = "预租价格状态！";
        }else if (message.contains("请输入正确的预租价格！")) {
            msg = "请输入正确的预租价格！";
        }else if (message.contains("请输入实租价格！")){
            msg = "请输入实租价格！";
        }else if (message.contains("请输入正确的实租价格！")) {
            msg = "请输入正确的实租价格！";
        }else if (message.contains("请输入押金！")){
            msg = "请输入押金！";
        }else if (message.contains("请输入正确的押金价格！")) {
            msg = "请输入正确的押金价格！";
        }else if (message.contains("请输入支付金额！")) {
            msg = "请输入支付金额！";
        }else if (message.contains("请输入正确的支付金额价格！")) {
            msg = "请输入正确的支付金额价格！";
        }else if (message.contains("支付金额过小！")){
            msg = "支付金额过小！";
        }else if (message.contains("请选择支付方式！")){
            msg = "请选择支付方式！";
        }else if (message.contains("请输入还车日期！")){
            msg = "请输入还车日期！";
        }else if (message.contains("请选择正确的未来时间！")) {
            msg = "请选择正确的未来时间！";
        }else if (message.contains("Cannot deserialize value of type")) {
            msg = "请输入正确类型！";
        }else if (message.contains("Rating must be between 0 and 100")) {
            msg = "信誉积分范围在0~100之间！";
        } else if (message.contains("用户信誉过低！")) {
            msg = "用户信誉过低！";
        } else if (message.contains("用户信誉异常！")) {
            msg = "用户信誉异常！";
        } else if (message.contains("当前用户不存在！")) {
            msg = "当前用户不存在！";
        } else if (message.contains("邮箱地址不存在！")) {
            msg = "邮箱地址不存在！";
        } else if (message.contains("SMTPAddressFailedException: 501 Bad address syntax.")) {
            msg = "收件人邮箱格式异常！";
        } else if (message.contains("消息日志记录失败！")) {
            msg = "消息日志记录失败！";
        } else if (message.contains("验证码不存在！")) {
            msg = "验证码不存在！";
        } else if (message.contains("填写信息不能为空！")) {
            msg = "填写信息不能为空！";
        } else if (message.contains("该品牌下，还存在车辆！")) {
            msg = "该品牌下，还存在车辆！";
        } else {
            msg = "RuntimeException--未知异常！";
        }

        System.out.println(e.getMessage());
        return CommonResult.fail(300,msg,null);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult all(Exception e){
        System.out.println(".......");

        String message = e.getMessage();
        System.out.println(message);


        String msg = null;
        if (message.contains("请选择车辆")){
            msg = "请选择车辆！";
        } else if (message.contains("用户状态异常")) {
            msg = "当前用户状态异常！";
        } else if (message.contains("预租价格状态！")){
            msg = "预租价格状态！";
        }else if (message.contains("请输入正确的预租价格！")) {
            msg = "请输入正确的预租价格！";
        }else if (message.contains("请输入实租价格！")){
            msg = "请输入实租价格！";
        }else if (message.contains("请输入正确的实租价格！")) {
            msg = "请输入正确的实租价格！";
        }else if (message.contains("请输入押金！")){
            msg = "请输入押金！";
        }else if (message.contains("请输入正确的押金价格！")) {
            msg = "请输入正确的押金价格！";
        }else if (message.contains("请输入支付金额！")) {
            msg = "请输入支付金额！";
        }else if (message.contains("请输入正确的支付金额价格！")) {
            msg = "请输入正确的支付金额价格！";
        }else if (message.contains("支付金额过小！")){
            msg = "支付金额过小！";
        }else if (message.contains("请选择支付方式！")){
            msg = "请选择支付方式！";
        }else if (message.contains("请输入还车日期！")){
            msg = "请输入还车日期！";
        }else if (message.contains("请选择正确的未来时间！")) {
            msg = "请选择正确的未来时间！";
        }else if (message.contains("Cannot deserialize value of type")) {
            msg = "请输入正确类型！";
        }else if (message.contains("Rating must be between 0 and 100")) {
            msg = "信誉积分范围在0~100之间！";
        } else if (message.contains("用户信誉过低！")) {
            msg = "用户信誉过低！";
        } else if (message.contains("用户信誉异常！")) {
            msg = "用户信誉异常！";
        } else {
            msg = "Exception--未知异常！";
        }

        System.out.println("================");
        return CommonResult.fail(300,msg,null);
    }
}
