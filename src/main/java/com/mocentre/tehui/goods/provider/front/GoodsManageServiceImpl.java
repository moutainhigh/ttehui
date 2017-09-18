package com.mocentre.tehui.goods.provider.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.act.mapper.GrouponMapper;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.act.service.IGrouponService;
import com.mocentre.tehui.backend.param.CategoryGoodsQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.*;
import com.mocentre.tehui.frontend.service.GoodsManageService;
import com.mocentre.tehui.goods.mapper.CategoryGoodsMapper;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.mapper.GoodsMapper;
import com.mocentre.tehui.goods.model.*;
import com.mocentre.tehui.goods.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商品模块privider Created by 王雪莹 on 2016/11/24.
 */
public class GoodsManageServiceImpl implements GoodsManageService {

    @Autowired
    private IGoodsService           goodsService;
    @Autowired
    private IGoodsStorageService    goodsStorageService;
    @Autowired
    private IActivityGoodsService   activityGoodsService;
    @Autowired
    private IGrouponService         grouponService;
    @Autowired
    private ICategoryService        categoryService;
    @Autowired
    private IStoreService           storeService;
    @Autowired
    private IGoodsShareService      goodsShareService;
    @Autowired
    private ICategoryGoodsService   categoryGoodsService;

    /**
     * 根据商品id获取详情页信息
     *
     * @param id
     * @return
     */
    @Override
    public PlainResult<GoodsDetailFTInstance> goodsDetailPage(Long id, String requestId) {
        PlainResult<GoodsDetailFTInstance> resultData = new PlainResult<>();
        resultData.setRequestId(requestId);
        try {
            Goods goods = goodsService.getGoodsById(id);
            GoodsDetailFTInstance goodsDetailFTInstance = GoodsMapper.toGoodsDetailFTInstance(goods);

            // TODO: 2016/12/8 参与的活动及其参数
            // TODO: 2016/12/9 来过的人
            resultData.setData(goodsDetailFTInstance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("goodsDetailPage", e);
            resultData.setErrorMessage("999", "系统异常");
        }
        return resultData;
    }

    /**
     * 根据商品库存及价格信息
     *
     * @param id
     * @return
     */
    @Override
    public PlainResult<GoodsStorageAndPriceFTInstance> goodsStorageAndPrice(Long id, String standardCode,
                                                                            String requestId) {
        PlainResult<GoodsStorageAndPriceFTInstance> resultData = new PlainResult<>();
        resultData.setRequestId(requestId);
        try {
            GoodsStorage goodsStorage = goodsStorageService.queryGoodsStorageByStandard(id, standardCode);
            GoodsStorageAndPriceFTInstance instance = GoodsMapper.toGoodsStorageAndPriceFTInstance(goodsStorage);
            resultData.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("goodsStorageAndPrice", e);
            resultData.setErrorMessage("999", "系统异常");
        }
        return resultData;
    }

    /**
     * 获取活动信息
     *
     * @param goodsId
     * @param actGoodsId
     * @param requestId
     * @return
     */
    @Override
    public PlainResult<ActGoodsDetailFTInstance> actGoodsDetailPage(Long goodsId, Long actGoodsId, Long grouponId,
                                                                    String requestId) {
        PlainResult<ActGoodsDetailFTInstance> result = new PlainResult<ActGoodsDetailFTInstance>();
        result.setRequestId(requestId);
        try {
            ActivityGoods actGoods = activityGoodsService.getActivityGoodsById(actGoodsId);
            Goods goods = goodsService.getGoodsById(goodsId);
            // 解析商品详情图片
            if (!StringUtils.isBlank(goods.getDetails())) {
                List<String> detailList = new ArrayList<String>();
                Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(goods.getDetails());
                while (m.find()) {
                    detailList.add(m.group().replace("src=", ""));
                }
                goods.setDetails(detailList.toString());
            }
            Long totalStocknum = goodsStorageService.queryTotalStocknum(goodsId, actGoodsId);
            ActGoodsDetailFTInstance instance = GoodsMapper.toActGoodsDetailFTInstance(actGoods, goods);
            if (null != grouponId) {// 参团
                Groupon groupon = grouponService.getGrouponById(grouponId);
                Date endTime = groupon.getEndTime();
                Date nowTime = new Date();
                Integer takeNum = groupon.getTakeNum();
                Integer groupNum = groupon.getGrouponNum();
                if (takeNum != null && takeNum.intValue() >= groupNum.intValue()) {// 团购人数已达上限
                    instance.setFinishedGroupon(true);
                } else {
                    instance.setFinishedGroupon(false);
                }
                if (!nowTime.after(endTime)) {// 团购时间未结束
                    instance.setFinishedTime(false);
                } else {
                    instance.setFinishedTime(true);
                }
                GrouponFTInstance grouponFTInstance = GrouponMapper.toGrouponFTInstance(groupon);
                instance.setGroupon(grouponFTInstance);
            }
            String actType = actGoods.getActivityType();
            instance.setActType(actType);
            instance.setStoreTotal(totalStocknum);
            result.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("actGoodsDetailPage", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<GoodsStorageAndPriceFTInstance> actGoodsStorageAndPrice(Long goodsId, Long actGoodsId,
                                                                               String standardCode, String requestId) {
        PlainResult<GoodsStorageAndPriceFTInstance> resultData = new PlainResult<>();
        resultData.setRequestId(requestId);
        try {
            //            GoodsStorage goodsStorage = goodsStorageService.queryGoodsStorageByStandard(goodsId, standardCode,
            //                    actGoodsId);
            GoodsStorage goodsStorage = goodsStorageService.queryGoodsStorageFromCache(goodsId, standardCode,
                    actGoodsId);
            if (null == goodsStorage) {
                goodsStorage = new GoodsStorage();
                goodsStorage.setGoodsId(goodsId);
                goodsStorage.setStockNum(0L);
                goodsStorage.setStandardCode(standardCode);
            }
            GoodsStorageAndPriceFTInstance instance = GoodsMapper.toGoodsStorageAndPriceFTInstance(goodsStorage);
            resultData.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("actGoodsStorageAndPrice", e);
            resultData.setErrorMessage("999", "系统异常");
        }
        return resultData;
    }

    @Override
    public PlainResult<GoodsDetailWithStorageAndPriceFTInstance> getGoodsWithStorageAndPrice(Long goodsId,
                                                                                             Long customerId,
                                                                                             Long actGoodsId,
                                                                                             String comefrom,
                                                                                             String requestId) {
        PlainResult<GoodsDetailWithStorageAndPriceFTInstance> result = new PlainResult<GoodsDetailWithStorageAndPriceFTInstance>();
        result.setRequestId(requestId);
        try {
            if (goodsId == null && actGoodsId == null) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            if (actGoodsId == null) {
                actGoodsId = 0L;
            }
            //查询活动商品
            ActivityGoods actGoods = null;
            if (actGoodsId != 0) {
                actGoods = activityGoodsService.getActivityGoodsFromCache(actGoodsId);
                if (actGoods == null) {
                    result.setErrorMessage("1001", "活动商品不存在");
                    return result;
                }
            }
            if (goodsId == null) {
                goodsId = actGoods.getGoodsId();
            }
            Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
            if (goods == null) {
                result.setErrorMessage("1002", "商品不存在");
                return result;
            }
            //缓存中查询库存
            List<GoodsStorage> goodsStorageList = new ArrayList<GoodsStorage>();
            String holdStandard = "";
            //判断是否活动商品，库存取值不一样
            if (actGoodsId != 0) {
                holdStandard = actGoods.getHoldStandard();
            } else {
                holdStandard = goods.getHoldStandard();
            }
            if (StringUtils.isNotBlank(holdStandard)) {
                JSONArray hsdArr = JSON.parseArray(holdStandard);
                for (int i = 0; i < hsdArr.size(); i++) {
                    JSONObject hsdObj = hsdArr.getJSONObject(i);
                    String stcode = hsdObj.getString("code");
                    GoodsStorage storage = goodsStorageService.queryGoodsStorageFromCache(goodsId, stcode, actGoodsId);
                    if (storage != null) {
                        goodsStorageList.add(storage);
                    }
                }
            }
            int count = 0;
            if (actGoodsId == 0 && customerId != null && "wx".equals(comefrom)) {
                count = storeService.countStoreByGoods(goodsId, customerId);
            }
            //分享信息(针对微信)
            GoodsShare goodsShare = null;
            if ("wx".equals(comefrom)) {
                goodsShare = goodsShareService.getGoodsShareByGoodsid(goodsId);
            }
            GoodsDetailWithStorageAndPriceFTInstance instance = GoodsMapper.toGoodsDetailWithStorageAndPriceFTInstance(
                    goods, goodsStorageList, goodsShare, actGoods);
            if (count > 0) {
                instance.setStored(true);
            } else {
                instance.setStored(false);
            }
            result.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGoodsWithStorageAndPrice", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    /**
     * 获取全部分类，及该分类下的全部商品
     *
     * @param cgParam
     * @return
     */
    @Override
    public ListResult<CategoryWithGoodsFTInstance> getAllCategoryWithGoods(CategoryGoodsQueryParam cgParam) {
        ListResult<CategoryWithGoodsFTInstance> result = new ListResult<CategoryWithGoodsFTInstance>();
        result.setRequestId(cgParam.getRequestId());
        try {
            Integer start = cgParam.getStart();
            Integer length = cgParam.getLength();
            List<Category> categoryList = categoryService.getCategoryPage(start, length);
            List<CategoryWithGoodsFTInstance> instanceList = CategoryMapper.toCategoryWithGoodsFTInstanceList(categoryList);
            for (CategoryWithGoodsFTInstance ins : instanceList) {
                List<CategoryGoods> cgList = categoryGoodsService.getCategoryGoodsListByCategoryId(ins.getId());
                ins.setGoodsList(CategoryGoodsMapper.toCategoryGoodsFTInsList(cgList));
            }
            result.setData(instanceList);
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.tehuiLog.error("getAllCategoryWithGoods", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public ListResult<GoodsListPageInfoFTInstance> getGuessLike(int num, String requestId) {
        ListResult<GoodsListPageInfoFTInstance> result = new ListResult<GoodsListPageInfoFTInstance>();
        try {
            List<Goods> goodsList = goodsService.getSellTopN(null, num);
            List<GoodsListPageInfoFTInstance> info = new ArrayList<GoodsListPageInfoFTInstance>();
            for (Goods goods : goodsList) {
                info.add(GoodsMapper.toGoodsListPageInfoFTInstance(goods));
            }
            result.setData(info);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGuessLike", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }
}
