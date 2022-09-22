package org.lz.workflow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lz.workflow.basic.FlowCommonEnum;
import org.lz.workflow.domain.FlowCommon;
import org.lz.workflow.mapper.FlowCommonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author lz
 */
@Service
public class FlowCommonService extends ServiceImpl<FlowCommonMapper, FlowCommon> {

    /**
     * Get new id by symbol.
     *
     * @param flowCommonEnum 流程公共参数枚举
     * @return new id
     */
    @Transactional
    public Long getIdAndIncr(FlowCommonEnum flowCommonEnum) {
        Objects.requireNonNull(flowCommonEnum);

        FlowCommon flowCommon = getById(flowCommonEnum.getSymbol());
        String value = flowCommon.getValue();
        if (value == null) {
            value = "0";
        }
        long id = Long.parseLong(value);
        flowCommon.setValue(String.valueOf(id + 1));
        updateById(flowCommon);
        return id;
    }
}
