package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Users;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */

@Mapper
public interface UsersMapper {

    /**
     * 根据用户名或者手机号查询用户
     * @param users
     * @return
     */
    Users getUserByPhone(@Param("users") Users users);

    List<Orders> selectPage(@Param("users") Users users, @Param("page") Page page);

    int getCount(@Param("users")Users users);


    @Operation(value = OperationStatus.UPDATE)
    int update(@Param("users")Users users);


    @Operation(value = OperationStatus.INSERT)
    int insert(@Param("users")Users users);

    @Operation(value = OperationStatus.DELETE)
    int deleteByIds(@Param("ids") Long[] ids);

    @Operation(value = OperationStatus.RESTORE)
    int restoreData(@Param("ids")Long[] ids);

    @Operation(value = OperationStatus.LOGICAL)
    int logicalDeletion(@Param("ids")Long[] ids);

    Users selectOne(@Param("users") Users users);

    @Operation(value = OperationStatus.UPDATE)
    int updatePass(@Param("user") Users user);
}
