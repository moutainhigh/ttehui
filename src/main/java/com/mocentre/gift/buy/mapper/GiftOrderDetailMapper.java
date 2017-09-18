package com.mocentre.gift.buy.mapper;

import com.mocentre.gift.backend.model.GiftOrderGoodsDetailInstance;
import com.mocentre.gift.backend.param.GiftOrderDetailParam;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.gift.frontend.model.GiftGoodsPageFTInstance;
import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * GiftOrderDetailMapper
 * Created by 王雪莹 on 2017/4/13.
 */
public class GiftOrderDetailMapper {
    public static GiftOrderGoodsDetailInstance toGiftOrderGoodsDetailInstance(GiftOrderDetail detail) {
        GiftOrderGoodsDetailInstance instance = new GiftOrderGoodsDetailInstance();
        instance.setId(detail.getId());
        instance.setGoodsName(detail.getGoodsName());
        instance.setGoodsPrice(String.valueOf(detail.getGoodsPrice()/100f));
        instance.setGoodsAmount(detail.getGoodsAmount());
        return instance;
    }
    public static List<GiftOrderGoodsDetailInstance> toGiftOrderGoodsDetailInstanceList(List<GiftOrderDetail> detailList) {
        List<GiftOrderGoodsDetailInstance> instanceList = new ArrayList<GiftOrderGoodsDetailInstance>();
         for(GiftOrderDetail instance:detailList){
             instanceList.add(toGiftOrderGoodsDetailInstance(instance));
        }
        return instanceList;
    }

    public static GiftOrderDetail toGiftOrderDetail(GiftOrderDetailParam param) {
        GiftOrderDetail detail = new GiftOrderDetail();
        BeanCopier copier = BeanCopier.create(GiftOrderDetailParam.class, GiftOrderDetail.class,false);
        copier.copy(param,detail,  null);
        detail.setGoodsPrice(param.getGoodsPrice().multiply(new BigDecimal(100)).longValue());
        return detail;
    }
    public static GiftOrderDetail toGiftOrderDetail(GiftOrderGoodsDetailInstance instance) {
        GiftOrderDetail detail = new GiftOrderDetail();
        BeanCopier copier = BeanCopier.create(GiftOrderGoodsDetailInstance.class, GiftOrderDetail.class,false);
        copier.copy(instance,detail,  null);
        return detail;
    }
    public static GiftGoodsPageFTInstance toGiftGoodsPageFTInstance(GiftOrderDetail detail) {
        GiftGoodsPageFTInstance instance = new GiftGoodsPageFTInstance();
        BeanCopier copier = BeanCopier.create(GiftOrderDetail.class, GiftGoodsPageFTInstance.class,false);
        copier.copy(detail,instance,  null);
        DecimalFormat nf = new DecimalFormat("#0.00");
        instance.setPrice(String.valueOf(nf.format(detail.getGoodsPrice()/100f)));
        instance.setTotalPrice(String.valueOf(nf.format(detail.getGoodsPrice()*detail.getGoodsAmount()/100f)));
        instance.setBuyNum(detail.getGoodsAmount());
        return instance;
    }
    public static List<GiftGoodsPageFTInstance> toGiftGoodsPageFTInstanceList(List<GiftOrderDetail> detailList) {
        List<GiftGoodsPageFTInstance> instanceList = new ArrayList<>();
        for(GiftOrderDetail instance:detailList){
            instanceList.add(toGiftGoodsPageFTInstance(instance));
        }
        return instanceList;
    }
}
