package com.mocentre.gift.buy.mapper;

import com.mocentre.gift.backend.model.GiftOrderBillInstance;
import com.mocentre.gift.backend.param.GiftBillParam;
import com.mocentre.gift.buy.model.GiftOrderBill;
import com.mocentre.gift.frontend.model.GiftBillFTInstance;
import org.springframework.cglib.beans.BeanCopier;

/**
 * GiftOrderBillMapper
 * Created by 王雪莹 on 2017/4/13.
 */
public class GiftOrderBillMapper {
    public static GiftOrderBillInstance toGiftOrderBillInstance(GiftOrderBill bill) {
        GiftOrderBillInstance instance = new GiftOrderBillInstance();
        BeanCopier copier = BeanCopier.create(GiftOrderBill.class, GiftOrderBillInstance.class,false);
        copier.copy(bill, instance, null);
        return instance;
    }
    public static GiftOrderBill toGiftOrderBill(GiftBillParam param) {
        GiftOrderBill bill = new GiftOrderBill();
        BeanCopier copier = BeanCopier.create(GiftBillParam.class, GiftOrderBill.class,false);
        copier.copy(param,bill,  null);
        return bill;
    }

    public static GiftBillFTInstance toGiftBillFTInstance(GiftOrderBill bill) {
        GiftBillFTInstance instance = new GiftBillFTInstance();
        BeanCopier copier = BeanCopier.create(GiftOrderBill.class, GiftBillFTInstance.class,false);
        copier.copy(bill, instance, null);
        return instance;
    }
}
