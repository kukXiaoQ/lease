package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.entries.Brand;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-10
 */
@Mapper
public interface BrandMapper {

    List<Brand> selectAllByName(String name);

    Brand getById(Integer brandId);

    List<Brand> getListByIds(@Param("ids") Set<Integer> ids);

    List<Brand> selectPage(@Param("brand") Brand brand,@Param("page") Page page);

    int getCount(@Param("brand") Brand brand);


    @Operation(value = OperationStatus.UPDATE)
    int update(@Param("brand") Brand brand);

    @Operation(value = OperationStatus.INSERT)
    int insert(@Param("brand") Brand brand);


    @Operation(value = OperationStatus.UPDATE)
    int logicalDeletion(@Param("ids") Long[] ids);
}
