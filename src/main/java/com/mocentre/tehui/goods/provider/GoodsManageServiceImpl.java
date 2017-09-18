package com.mocentre.tehui.goods.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocentre.common.BaseResult;
import com.mocentre.common.CommonResultCode;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.GoodsManageService;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.backend.model.AttributeInstance;
import com.mocentre.tehui.backend.model.CategoryInstance;
import com.mocentre.tehui.backend.model.GoodsAdvancedInstance;
import com.mocentre.tehui.backend.model.GoodsBaseInstance;
import com.mocentre.tehui.backend.model.GoodsInstance;
import com.mocentre.tehui.backend.model.GoodsLaunchInstance;
import com.mocentre.tehui.backend.model.GoodsParamInstance;
import com.mocentre.tehui.backend.model.GoodsShareInstance;
import com.mocentre.tehui.backend.model.GoodsStorageAttributeInstance;
import com.mocentre.tehui.backend.model.GoodsStorageInstance;
import com.mocentre.tehui.backend.param.GoodsBaseInfoParam;
import com.mocentre.tehui.backend.param.GoodsQueryParam;
import com.mocentre.tehui.backend.param.GoodsStorageParam;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.bak.service.IMallHomeService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.enums.GoodsCheckedType;
import com.mocentre.tehui.goods.enums.GoodsShowType;
import com.mocentre.tehui.goods.mapper.AttributeMapper;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.mapper.GoodsLaunchMapper;
import com.mocentre.tehui.goods.mapper.GoodsMapper;
import com.mocentre.tehui.goods.mapper.GoodsShareMapper;
import com.mocentre.tehui.goods.mapper.GoodsStorageMapper;
import com.mocentre.tehui.goods.model.Attribute;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsLaunch;
import com.mocentre.tehui.goods.model.GoodsParam;
import com.mocentre.tehui.goods.model.GoodsShare;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.goods.service.IAttributeService;
import com.mocentre.tehui.goods.service.ICategoryService;
import com.mocentre.tehui.goods.service.IGoodsLaunchService;
import com.mocentre.tehui.goods.service.IGoodsParamService;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.goods.service.IGoodsShareService;
import com.mocentre.tehui.goods.service.IGoodsStorageService;
import com.mocentre.tehui.sub.model.SubjectGoods;
import com.mocentre.tehui.sub.service.ISubjectGoodsService;

/**
 * 商品模块privider Created by 王雪莹 on 2016/11/24.
 */
public class GoodsManageServiceImpl implements GoodsManageService {

    @Autowired
    private IGoodsService         goodsService;
    @Autowired
    private IGoodsStorageService  goodsStorageService;
    @Autowired
    private IGoodsParamService    goodsParamService;
    @Autowired
    private IGoodsShareService    goodsShareService;
    @Autowired
    private ICategoryService      categoryService;
    @Autowired
    private IGoodsLaunchService   goodsLaunchService;
    @Autowired
    private IAttributeService     attributeService;
    @Autowired
    private ISubjectGoodsService  subjectGoodsService;
    @Autowired
    private IActivityGoodsService actGoodsService;
    @Autowired
    private IMallHomeService      mallHomeService;

    @Override
    public ListJsonResult<GoodsInstance> queryPage(GoodsQueryParam queryParam) {
        ListJsonResult<Goods> result = new ListJsonResult<>();
        ListJsonResult<GoodsInstance> resultData = new ListJsonResult<>();
        resultData.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderColumn", queryParam.getOrderColumn());
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("title", queryParam.getTitle());
            paramMap.put("shopId", queryParam.getShopId());
            if ("unCheck".equals(queryParam.getIsCheck())) {
                paramMap.put("isChecked", GoodsCheckedType.UNCHECK.getCodeValue());
            } else if ("checkPass".equals(queryParam.getIsCheck())) {
                paramMap.put("isChecked", GoodsCheckedType.CHECKPASS.getCodeValue());
            } else if ("checkFail".equals(queryParam.getIsCheck())) {
                paramMap.put("isChecked", GoodsCheckedType.CHECKFAIL.getCodeValue());
            }
            if ("notShelf".equals(queryParam.getIsShow())) {
                paramMap.put("isShow", GoodsShowType.NOTSHELF.getCodeValue());
            } else if ("onShelf".equals(queryParam.getIsShow())) {
                paramMap.put("isShow", GoodsShowType.ONSHELF.getCodeValue());
            } else if ("offShelf".equals(queryParam.getIsShow())) {
                paramMap.put("isShow", GoodsShowType.OFFSHELF.getCodeValue());
            }
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            result = goodsService.queryPage(require);
            List<Goods> lists = result.getData();
            List<GoodsInstance> listData = GoodsMapper.toInstanceList(lists);
            resultData.setData(listData);
            resultData.setDraw(result.getDraw());
            resultData.setCode(result.getCode());
            resultData.setMessage(result.getMessage());
            resultData.setRecordsTotal(result.getRecordsTotal());
            resultData.setRecordsFiltered(result.getRecordsFiltered());
            resultData.setSuccess(result.isSuccess());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            resultData.setErrorMessage("999", "系统错误");
        }
        return resultData;
    }

    @Override
    public BaseResult deleteById(Long id, Long shopId, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            List<ActivityGoods> actGoodsList = actGoodsService.queryListByGoods(id);
            if (actGoodsList != null && actGoodsList.size() > 0) {
                br.setErrorMessage("1001", "活动中已关联此商品");
                return br;
            }
            List<SubjectGoods> subGoodsList = subjectGoodsService.queryListByGoods(id);
            if (subGoodsList != null && subGoodsList.size() > 0) {
                br.setErrorMessage("1001", "专题中已关联此商品");
                return br;
            }
            List<MallHome> mallHomeList = mallHomeService.getMallHomeByGoods(id);
            if (mallHomeList != null && mallHomeList.size() > 0) {
                br.setErrorMessage("1001", "农行首页中已关联此商品");
                return br;
            }
            goodsService.delGoodsById(id, shopId);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteById", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult updateGoodsStatus(Long id, Long shopId, String status, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            Long count = goodsService.updateGoodsStatus(id, shopId, status);
            if (count == 0) {
                br.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateGoodsStatus", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public PlainResult<Long> addOrUpdateGoodsBaseInfo(GoodsBaseInfoParam goodsBaseInfoParam) {
        PlainResult<Long> pr = new PlainResult<Long>();
        pr.setRequestId(goodsBaseInfoParam.getRequestId());
        try {
            String putAreas = goodsBaseInfoParam.getAreaList();
            Goods goods = GoodsMapper.toGoods(goodsBaseInfoParam);
            Long id = goods.getId();
            if (id != null) {
                goodsService.updateGoodsBaseInfoAndArea(goods, putAreas);
            } else {
                goods = goodsService.addGoodsBaseInfo(goods);
                id = goods.getId();
            }
            pr.setData(id);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addOrUpdateGoodsBaseInfo", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public PlainResult<GoodsAdvancedInstance> getGoodsAndStorageById(Long id, String requestId) {
        PlainResult<GoodsAdvancedInstance> pr = new PlainResult<GoodsAdvancedInstance>();
        pr.setRequestId(requestId);
        try {
            Goods goods = goodsService.getGoodsById(id);
            List<GoodsStorage> storageList = goodsStorageService.queryGoodsStorageByGoosid(id, 0L);
            GoodsAdvancedInstance advancedIns = GoodsMapper.toAdvanceInstance(goods, storageList, null, null, null);
            pr.setData(advancedIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGoodsAndStorageById", e);
        }
        return pr;
    }

    @Override
    public BaseResult addGoodsShareInfo(Long goodsId, Map<String, Object> paramMap, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            GoodsShare goodsShare = JSON.parseObject(JSON.toJSONString(paramMap), GoodsShare.class);
            Long res = goodsShareService.updateGoodsShare(goodsId, goodsShare);
            if (res == 0) {
                CommonResultCode.ERROR_DB.mixIn(br);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addGoodsShareInfo", e);
            br.setErrorMessage("999", "更新失败");
        }
        return br;
    }

    @Override
    public BaseResult addGoodsParamInfo(Long shopId, Long goodsId, String paramList, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            List<GoodsParam> goodsParamList = JSON.parseArray(paramList, GoodsParam.class);
            goodsService.updateGoodsParam(shopId, goodsId, goodsParamList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addGoodsShareInfo", e);
            br.setErrorMessage("999", "更新失败");
        }
        return br;
    }

    @Override
    public BaseResult addOrUpdateStorage(Long goodsId, String standard, Long shopId,
                                         List<GoodsStorageParam> goodsStorageList, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            List<GoodsStorage> goodsStorages = GoodsStorageMapper.toGoodsStorageList(goodsStorageList);
            goodsService.saveOrupdateGoodsSkuAndCache(goodsId, standard, shopId, goodsStorages);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addOrUpdateStorage", e);
            br.setErrorMessage("999", "更新失败");
        }
        return br;
    }

    @Override
    public BaseResult addGoodsDetailInfo(Long goodsId, Long shopId, String imgBanner, String details, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            Long count = goodsService.updateGoodsDetail(goodsId, shopId, imgBanner, details);
            if (count == 0) {
                br.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addGoodsDetailInfo", e);
            br.setErrorMessage("999", "更新失败");
        }
        return br;
    }

    @Override
    public PlainResult<GoodsStorageAttributeInstance> getStorageInfoById(Long id, String requestId) {
        PlainResult<GoodsStorageAttributeInstance> resultData = new PlainResult<GoodsStorageAttributeInstance>();
        resultData.setRequestId(requestId);
        try {
            GoodsStorageAttributeInstance storageCategory = new GoodsStorageAttributeInstance();
            List<GoodsStorage> gsList = goodsStorageService.queryGoodsStorageByGoosid(id, 0l);
            Goods goods = goodsService.getGoodsById(id);
            GoodsInstance goodsIns = GoodsMapper.toInstance(goods);
            List<GoodsStorageInstance> storageInsList = GoodsStorageMapper.toGoodsStorageInstancesList(gsList);

            List<AttributeInstance> attrInsList = new ArrayList<AttributeInstance>();
            List<Attribute> attrList = attributeService.getAttributes();
            if (attrList != null) {
                for (Attribute attr : attrList) {
                    AttributeInstance attrIns = AttributeMapper.toInstance(attr);
                    attrInsList.add(attrIns);
                }
            }
            storageCategory.setGoods(goodsIns);
            storageCategory.setAttributeList(attrInsList);
            storageCategory.setGoodsStorageList(storageInsList);
            resultData.setData(storageCategory);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getStorageInfoById", e);
            resultData.setErrorMessage("999", "系统异常");
        }
        return resultData;
    }

    @Override
    public ListResult<GoodsParamInstance> getGoodsParamInfo(Long shopId, long goodsId, String requestId) {
        ListResult<GoodsParamInstance> resultData = new ListResult<>();
        resultData.setRequestId(requestId);
        try {
            List<GoodsParam> paramList = goodsParamService.getByGoodsId(goodsId);
            List<GoodsParamInstance> instanceList = GoodsMapper.toGoodsParamInstanceList(paramList);
            resultData.setData(instanceList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGoodsParamInfo", e);
            resultData.setErrorMessage("999", "系统异常");
        }
        return resultData;
    }

    @Override
    public PlainResult<GoodsShareInstance> getGoodsShareInfo(long goodsId, String requestId) {
        PlainResult<GoodsShareInstance> resultData = new PlainResult<>();
        resultData.setRequestId(requestId);
        try {
            GoodsShare goodsShare = goodsShareService.getGoodsShareByGoodsid(goodsId);
            GoodsShareInstance instance = GoodsShareMapper.toGoodsShareInstance(goodsShare);
            resultData.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGoodsShareInfo", e);
            resultData.setErrorMessage("999", "系统异常");
        }
        return resultData;
    }

    @Override
    public ListResult<GoodsInstance> queryOnShelfGoodsByCategory(Long categoryId, String requestId) {
        ListResult<GoodsInstance> result = new ListResult<GoodsInstance>();
        result.setRequestId(requestId);
        try {
            List<Goods> list = goodsService.getOnShelfGoodsByCategory(categoryId);
            List<GoodsInstance> goodsInsList = GoodsMapper.toInstanceList(list);
            result.setData(goodsInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryOnShelfGoodsByCategory", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<GoodsBaseInstance> getGoodsBaseInfoById(Long goodsId, Long shopId, String requestId) {
        PlainResult<GoodsBaseInstance> result = new PlainResult<GoodsBaseInstance>();
        result.setRequestId(requestId);
        GoodsBaseInstance goodsBaseIns = new GoodsBaseInstance();
        try {
            Goods goods = goodsService.getGoodsByIdShop(goodsId, shopId);
            if (goods == null) {
                result.setErrorMessage("1001", "商品不存在");
                return result;
            }
            List<Category> categoryList = categoryService.getAllCategoryList();
            List<GoodsLaunch> goodsLaunchList = goodsLaunchService.queryByGoodsId(goodsId);
            GoodsInstance goodsIns = GoodsMapper.toInstance(goods);
            List<CategoryInstance> categoryInsList = CategoryMapper.toInstanceList(categoryList);
            List<GoodsLaunchInstance> goodsLaunchInsList = GoodsLaunchMapper.toInstanceList(goodsLaunchList);
            goodsBaseIns.setGoods(goodsIns);
            goodsBaseIns.setCategoryList(categoryInsList);
            goodsBaseIns.setGoodsLaunchList(goodsLaunchInsList);
            result.setData(goodsBaseIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGoodsBaseInfoById", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<GoodsInstance> getGoodsById(Long goodsId, Long shopId, String requestId) {
        PlainResult<GoodsInstance> result = new PlainResult<GoodsInstance>();
        result.setRequestId(requestId);
        try {
            Goods goods = goodsService.getGoodsByIdShop(goodsId, shopId);
            if (goods == null) {
                result.setErrorMessage("1001", "商品不存在");
                return result;
            }
            GoodsInstance goodsIns = GoodsMapper.toInstance(goods);
            result.setData(goodsIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGoodsById", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }
}
