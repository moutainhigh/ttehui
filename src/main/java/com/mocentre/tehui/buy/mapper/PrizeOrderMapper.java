package com.mocentre.tehui.buy.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mocentre.tehui.backend.model.PrizeOrderInstance;
import com.mocentre.tehui.backend.param.PrizeOrderParam;
import com.mocentre.tehui.buy.model.PrizeOrder;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.frontend.model.PrizeOrderFTInstance;

/**
 * 实物礼品订单转换 Created by wangxueying on 2017/8/10.
 */
public class PrizeOrderMapper {

    public static List<PrizeOrderInstance> toPrizeOrderInstanceList(List<PrizeOrder> prizeOrderList) {
        ArrayList<PrizeOrderInstance> instanceList = new ArrayList<PrizeOrderInstance>();
        for (PrizeOrder po : prizeOrderList) {
            instanceList.add(toPrizeOrderInstance(po));
        }
        return instanceList;
    }

    public static PrizeOrderInstance toPrizeOrderInstance(PrizeOrder prizeOrder) {
        PrizeOrderInstance instance = new PrizeOrderInstance();
        BeanCopier copier = BeanCopier.create(PrizeOrder.class, PrizeOrderInstance.class, false);
        copier.copy(prizeOrder, instance, null);
        return instance;
    }

    public static PrizeOrder toPrizeOrder(PrizeOrderParam param) {
        PrizeOrder po = new PrizeOrder();
        BeanCopier copier = BeanCopier.create(PrizeOrderParam.class, PrizeOrder.class, false);
        copier.copy(param, po, null);
        return po;
    }

    public static List<PrizeOrderFTInstance> toPrizeOrderFTInstanceList(List<PrizeOrder> prizeOrderList) {
        if (prizeOrderList == null) {
            return null;
        }
        ArrayList<PrizeOrderFTInstance> instanceList = new ArrayList<PrizeOrderFTInstance>();
        for (PrizeOrder po : prizeOrderList) {
            instanceList.add(toPrizeOrderFTInstance(po));
        }
        return instanceList;
    }

    private static PrizeOrderFTInstance toPrizeOrderFTInstance(PrizeOrder prizeOrder) {
        PrizeOrderFTInstance instance = new PrizeOrderFTInstance();
        BeanCopier copier = BeanCopier.create(PrizeOrder.class, PrizeOrderFTInstance.class, false);
        copier.copy(prizeOrder, instance, null);
        Date endData = prizeOrder.getEndTime();
        Date nowData = DateUtils.parseDate(DateUtils.format(new Date()));
        boolean isEnd = false;
        if (endData != null) {
            if (endData.before(nowData)) {
                isEnd = true;
            }
        }
        instance.setEnded(isEnd);
        return instance;
    }

}
