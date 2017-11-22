package com.idata3d.scheduler;

import com.alibaba.fastjson.JSON;
import com.idata3d.core.util.DbTool;
import com.idata3d.domain.meal.BusinessDistrict;
import com.idata3d.domain.meal.MealUnionPayData;
import com.idata3d.domain.meal.UnionPayDataSyncError;
import com.idata3d.unionpay.UPAData;
import com.idata3d.unionpay.UPAService;
import jodd.bean.BeanCopy;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangpeng
 */
@Component
@Transactional
public class UnionPayErrorAutoFixScheduler
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UnionPayDataSyncErrorMapper mapper;
    private final UPAService                  upaService;
    
    @Autowired
    public UnionPayErrorAutoFixScheduler(
        @NotNull final UnionPayDataSyncErrorMapper mapper,
        @NotNull final UPAService upaService)
    {
        this.mapper = mapper;
        this.upaService = upaService;
    }
    
    @Scheduled(fixedRateString = "${unionpay.auto-fix.fixed-rate-string:1800000}",
        initialDelayString = "${unionpay.auto-fix.initial-delay-string:300000}")
    @Transactional
    public void errorAutoFix()
    {
        this.logger.info("UnionPayErrorAutoFixScheduler start, time:{}", LocalDateTime.now());
        final List<UnionPayDataSyncError> unionPayDataSyncErrors = this.mapper.findByError(LocalDateTime.now().minusDays(1), UnionPayDataSyncError.Error.SOCKET_ERROR);
        if (null == unionPayDataSyncErrors || unionPayDataSyncErrors.isEmpty())
        {
            this.logger.info("UnionPayErrorAutoFixScheduler end, time:{}, unionPayDataSyncErrors:Empty", LocalDateTime.now());
            return;
        }
        final Map<String, BusinessDistrict> idToCode = this.mapper.findIdToCode();
        for (final UnionPayDataSyncError syncError : unionPayDataSyncErrors)
        {
            final String unionPayCode = idToCode.get(syncError.getBusinessDistrictId()).getUnionPayCode();
            this.logger.info("errorAutoFix, UnionPayDataSyncError:{}", JSON.toJSONString(syncError));
            if (unionPayCode != null)
            {
                this.mapper.deleteOld(syncError.getBusinessDistrictId());
                final List<UPAData> upaData = this.upaService.requestData(unionPayCode, syncError.getUserId());
                this.mapper.batchSave(upaData.stream().map(it -> toMealUnionPayData(it, syncError.getBusinessDistrictId())).collect(Collectors.toList()));
                this.mapper.updateFixedStatus(syncError.getId());
            }
        }
        this.logger.info("UnionPayErrorAutoFixScheduler end, time:{}", LocalDateTime.now());
    }
    
    @NotNull
    public MealUnionPayData toMealUnionPayData(@NotNull final UPAData upaData, @NotNull final String businessDistrictId)
    {
        //noinspection Duplicates
        final MealUnionPayData mealUnionPayData = new MealUnionPayData();
        BeanCopy.fromBean(upaData).toBean(mealUnionPayData).copy();
        mealUnionPayData.setBusinessDistrictId(businessDistrictId);
        mealUnionPayData.setId(DbTool.uuid());
        mealUnionPayData.setCreateTime(new Date());
        return mealUnionPayData;
    }
}
