package com.mocentre.gift.buy.mapper;

import com.mocentre.gift.buy.model.GiftGiftSheet;
import com.mocentre.gift.frontend.model.GiftGiftSheetFTInstance;
import com.mocentre.gift.frontend.param.GiftGiftSheetFTParam;
import org.springframework.cglib.beans.BeanCopier;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * GiftGiftSheetMapper
 * Created by 王雪莹 on 2017/4/15.
 */
public class GiftGiftSheetMapper {
    public static GiftGiftSheet toGiftGiftSheet(GiftGiftSheetFTParam param) {
        GiftGiftSheet giftSheet = new GiftGiftSheet();
        BeanCopier copier = BeanCopier.create(GiftGiftSheetFTParam.class, GiftGiftSheet.class,false);
        copier.copy(param,giftSheet,null);
        return giftSheet;
    }
    public static List<GiftGiftSheet> toGiftGiftSheetList(List<GiftGiftSheetFTParam> paramList) {
        ArrayList<GiftGiftSheet> giftSheetList = new ArrayList<>();
        for(GiftGiftSheetFTParam param:paramList){
            giftSheetList.add(toGiftGiftSheet(param));
        }
        return giftSheetList;
    }
    public static GiftGiftSheetFTInstance toGIftGiftSheetFTInstance(GiftGiftSheet giftGiftSheet) {
        GiftGiftSheetFTInstance instance = new GiftGiftSheetFTInstance();
        BeanCopier copier = BeanCopier.create(GiftGiftSheet.class, GiftGiftSheetFTInstance.class,false);
        copier.copy(giftGiftSheet,instance,null);
        instance.setNum(giftGiftSheet.getNum());
        instance.setLimitNum(giftGiftSheet.getLimitNum());
        DecimalFormat df = new DecimalFormat("#.00");
        instance.setPrice(String.valueOf(df.format(giftGiftSheet.getPrice() / 100)));
        return instance;
    }

    public static List<GiftGiftSheetFTInstance> toGIftGiftSheetFTInstanceList(List<GiftGiftSheet> giftGiftSheetList) {
        List<GiftGiftSheetFTInstance> instanceList = new ArrayList<GiftGiftSheetFTInstance>();
        for(GiftGiftSheet sheet:giftGiftSheetList){
            instanceList.add(toGIftGiftSheetFTInstance(sheet));
        }
        return instanceList;
    }
}
