package com.situ.ws.aop;

import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.entries.Notifications;
import com.situ.ws.bean.entries.SystemLogs;
import com.situ.ws.bean.entries.Users;
import com.situ.ws.bean.enums.OperationStatus;
import com.situ.ws.service.INotificationsService;
import com.situ.ws.service.ISystemLogsService;
import com.situ.ws.util.LeaseThreadLocal;
import com.situ.ws.util.RegexUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MsgAop {

    private final INotificationsService notificationsService;
    private final StringRedisTemplate redisTemplate;
    private final ISystemLogsService systemLogsService;

    @Pointcut("execution(public void com.situ.ws.service.impl.MsgServiceImpl.sendMsg(..))")
    public void point(){}

    @Pointcut("@annotation(com.situ.ws.bean.document.Operation)")
    public void setLog(){}


    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("执行忘记密码的环绕通知=============================");

        Users users = (Users) joinPoint.getArgs()[0];
        System.out.println(users);

        Object proceed = joinPoint.proceed();

        // 将数据插入，信息表中
        Notifications notifications = new Notifications();
        String code = redisTemplate.opsForValue().get("lease:msg:phone:" + users.getPhone());
        notifications.setMessage("忘记密码-->发送邮箱--->" + users.getEmail() + "--->验证码为" + code);
        notifications.setSentAt(LocalDate.now());
        notifications.setUserId(users.getId());
        int count = notificationsService.insert(notifications);

        if (count <= 0)
            throw new RuntimeException("消息日志记录失败！");
        return proceed;
    }

    @Around("setLog()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("...........logAround............");
        Object[] args = joinPoint.getArgs();

        Object proceed = joinPoint.proceed(args);

        System.out.println(Arrays.toString(args));
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Operation annotation = method.getAnnotation(Operation.class);

        String classAndMethodName = signature.getDeclaringTypeName() + "." + method.getName();

        if (annotation != null) {
            OperationStatus status = annotation.value();
            // 创建对象
            SystemLogs logs = new SystemLogs();
            logs.setCreatedAt(LocalDateTime.now());
            String action = null;

            if (status == OperationStatus.UPDATE){
                action = "修改操作";
            } else if (status == OperationStatus.INSERT) {
                action = "新增操作";
            }else if (status == OperationStatus.DELETE) {
                action = "删除操作";
            }else if (status == OperationStatus.RESTORE) {
                action = "恢复操作";
            }else if (status == OperationStatus.LOGICAL) {
                action = "逻辑删除操作";
            }else {
                throw new RuntimeException("Operation注解异常");
            }

            // 获取当前操作人的id
            Integer userId = LeaseThreadLocal.get();

            logs.setUserId(userId);
            logs.setAction(action);
            logs.setDetails("操作方法 ===> "+classAndMethodName +  "()");

            // 插入
            systemLogsService.insert(logs);
        }
        return proceed;
    }
}
