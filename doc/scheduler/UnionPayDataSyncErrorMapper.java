package com.idata3d.scheduler;

import com.idata3d.domain.meal.BusinessDistrict;
import com.idata3d.domain.meal.MealUnionPayData;
import com.idata3d.domain.meal.UnionPayDataSyncError;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpeng
 */
@Mapper
public interface UnionPayDataSyncErrorMapper
{
    @Select("SELECT id, user_id, business_district_id, create_time, `error` FROM dc_meal_unionpay_data_sync_error WHERE create_time >=#{time} AND `error`=#{error} AND fixed=FALSE")
    List<UnionPayDataSyncError> findByError(@Param("time") LocalDateTime time,
        @Param("error") UnionPayDataSyncError.Error error);
    
    @Update("UPDATE dc_meal_unionpay_data_sync_error SET fixed = TRUE WHERE id=#{id}")
    void updateFixedStatus(@Param("id") String id);
    
    @Select("SELECT id,union_pay_code FROM dc_meal_business_district" +
            " WHERE open = TRUE AND union_pay_code IS NOT NULL")
    @MapKey("id")
    Map<String, BusinessDistrict> findIdToCode();
    
    void batchSave(@Param("unionPayDataList") List<MealUnionPayData> unionPayDataList);
    
    @Delete("DELETE FROM dc_meal_unionpay_data WHERE business_district_id = #{businessDistrictId}")
    void deleteOld(@Param("businessDistrictId") String businessDistrictId);
}
