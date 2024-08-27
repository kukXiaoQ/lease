package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.dto.CarRentalDto;
import com.situ.ws.bean.dto.CodeDto;
import com.situ.ws.bean.dto.UpdDto;
import com.situ.ws.bean.entries.Brand;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Users;
import com.situ.ws.service.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final IUsersService usersService;


    /**
     * 忘记密码
     */
    @PutMapping("/forgot")
    public CommonResult forgotPassword(@RequestBody CodeDto dto){
        log.info("修改的用户为===================>{}",dto);
        boolean flag = usersService.forgotPassword(dto);

        String msg = null;
        int code = 0;
        if (flag){
            msg = "恭喜您，密码重置成功！";
            code = 200;
        }else {
            msg = "密码重置失败，请重试... ...";
            code = 301;
        }
        return CommonResult.success(code,msg);
    }

    /**
     * 修改密码
     */
    @PutMapping("/updPas")
    public CommonResult updPassword(@RequestBody UpdDto dto){
        log.info("=====================>{}",dto);

        boolean flag = usersService.updPassword(dto);

        String msg = null;
        int code = 0;
        if (flag){
            msg = "密码修改成功！";
            code = 200;
        }else {
            msg = "密码修改失败，请重试... ...";
            code = 301;
        }

        return CommonResult.fail(code,msg);
    }

    /**
     * 还车
     */
    @PutMapping("/return")
    public CommonResult returnRental(@RequestBody @Valid Orders orders){
        System.out.println(orders + "================");

        boolean flag = usersService.returnRental(orders);
        String msg = null;
        int code = 0;
        if (flag){
            msg = "恭喜您，还车成功！";
            code = 200;
        }else {
            msg = "还车失败，请重试... ...";
            code = 301;
        }

        return CommonResult.success(code,msg,flag);
    }

    @GetMapping("/{id}")
    public CommonResult getBrandForUSerId(@PathVariable int id) {
        log.info("=================>{}",id);

        Map<String, Object> map = usersService.getBrandForUSerId(id);

        return CommonResult.success(map);
    }

    /**
     * 租车
     */
    @PostMapping("/carRental")
    public CommonResult userRental(@RequestBody @Valid CarRentalDto dto){

        System.out.println(dto);
        boolean flag = usersService.createRentalRecords(dto);
        String msg = null;
        int code = 0;
        if (flag){
            msg = "订单正在生成中... ...";
            code = 200;
        }else {
            msg = "订单生成失败，请重试... ...";
            code = 301;
        }
        return CommonResult.fail(code,msg);
    }

    @GetMapping("/one")
    public CommonResult getSelectOne(Users users){
        System.out.println(users);
        System.out.println("================================");


        Users u = usersService.getSelectOne(users);

        return CommonResult.success(u);
    }

    /**
     * 分页+条件查询
     * @param users 订单条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping
    public CommonResult PageQuery(Users users, Page page){
        log.error("order=====>{}",users);
        users.setIsDel(0);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Orders> list = usersService.PageQuery(users,page);
        int count = usersService.getCount(users);

        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 历史分页
     * @param users 订单条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping("/is")
    public CommonResult PageQueryIsDel(Users users, Page page){
        log.error("order=====>{}",users);
        users.setIsDel(1);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Orders> list = usersService.PageQuery(users,page);
        int count = usersService.getCount(users);

        return CommonResult.success(new PageResult<>(list, count));
    }


    @PutMapping
    public CommonResult updateById(@RequestBody Users users){

        int count = usersService.updateById(users);
        return CommonResult.success(count);
    }

    @PostMapping
    public CommonResult addOrder(@RequestBody Users users){

        int count = usersService.addOrder(users);
        return CommonResult.success(count);
    }


    /**
     * 真实删除
     * @param ids id数组
     * @return 修改行数
     */
    @DeleteMapping("/is")
    public CommonResult removeByIds(@RequestBody Long[] ids){

        int count = usersService.removeByIds(ids);
        return CommonResult.success(count);
    }

    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){
        int count = usersService.logicalDeletion(ids);
        return CommonResult.success(count);
    }

    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    @DeleteMapping("/n_is")
    public CommonResult restoreData(@RequestBody Long[] ids){

        log.info("要恢复的数据===》{}", Arrays.toString(ids));

        int count = usersService.restoreData(ids);
        return CommonResult.success(count);
    }

}
