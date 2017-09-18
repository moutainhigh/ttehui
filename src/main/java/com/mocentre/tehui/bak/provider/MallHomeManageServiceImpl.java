package com.mocentre.tehui.bak.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.MapResult;
import com.mocentre.tehui.MallHomeManageService;
import com.mocentre.tehui.act.enums.ActivityShowLocal;
import com.mocentre.tehui.act.mapper.ActivityGoodsMapper;
import com.mocentre.tehui.act.mapper.ActivityMapper;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.act.service.IActivityService;
import com.mocentre.tehui.backend.model.ImageInstance;
import com.mocentre.tehui.backend.model.MallHomeInstance;
import com.mocentre.tehui.backend.param.ImageParam;
import com.mocentre.tehui.backend.param.ImageQueryParam;
import com.mocentre.tehui.backend.param.MallHomeParam;
import com.mocentre.tehui.backend.param.MallHomeQueryParam;
import com.mocentre.tehui.bak.enums.MallHomeGoodsType;
import com.mocentre.tehui.bak.mapper.MallHomeMapper;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.bak.service.IMallHomeService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.mapper.GoodsMapper;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.service.ICategoryService;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.system.enums.ImageSeat;
import com.mocentre.tehui.system.enums.ImageType;
import com.mocentre.tehui.system.mapper.ImageMapper;
import com.mocentre.tehui.system.model.Image;
import com.mocentre.tehui.system.service.impl.ImageService;

/**
 * 农行掌上银行首页dubbo接口实现类
 * <p>
 * Created by yukaiji on 2017/5/17.
 */

public class MallHomeManageServiceImpl implements MallHomeManageService {

    @Autowired
    private IMallHomeService      mallHomeService;
    @Autowired
    private ICategoryService      categoryService;
    @Autowired
    private IGoodsService         goodsService;
    @Autowired
    private IActivityService      activityService;
    @Autowired
    private IActivityGoodsService activityGoodsService;
    @Autowired
    private ImageService          imageService;

    @Override
    public ListJsonResult<MallHomeInstance> queryMallHomePage(MallHomeQueryParam queryParam) {
        ListJsonResult<MallHomeInstance> result = new ListJsonResult<MallHomeInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("orderColumn", queryParam.getOrderColumn());
            paramMap.put("showName", queryParam.getShowName());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<MallHome> lr = mallHomeService.queryMallHomePage(require);
            List<MallHome> mallHomes = lr.getData();
            result.setData(MallHomeMapper.toMallHomeInsList(mallHomes));
            result.setDraw(lr.getDraw());
            result.setRecordsFiltered(lr.getRecordsFiltered());
            result.setRecordsTotal(lr.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryMallHomePage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public MapResult getMallHomeById(Long id, Long shopId) {
        MapResult result = new MapResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            MallHome mallHome = mallHomeService.getMallHomeById(id);
            map.put("mallHome", MallHomeMapper.toMallHomeIns(mallHome));
            String goodsType = mallHome.getGoodsType();
            if ("0".equals(mallHome.getIsChain())) {
                if (MallHomeGoodsType.COMMON.getCodeValue().equals(goodsType)) {
                    List<Category> categories = categoryService.getAllCategoryList();
                    Goods goods = goodsService.getGoodsById(mallHome.getGoodsId());
                    List<Goods> goodsList = goodsService.getOnShelfGoodsByCategory(goods.getCategoryId());
                    map.put("categories", CategoryMapper.toInstanceList(categories));
                    map.put("categoryId", goods.getCategoryId());
                    map.put("goodsList", GoodsMapper.toInstanceList(goodsList));
                } else {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("showLocal", ActivityShowLocal.ABC.getCodeValue());
                    param.put("isShow", "1");
                    param.put("shopId", shopId);
                    param.put("type", mallHome.getGoodsType());
                    List<Activity> activities = activityService.getActivityByParam(param);
                    List<ActivityGoods> activityGoodsList = activityGoodsService
                            .getActivityGoodsListByActivityId(mallHome.getActivityId());
                    map.put("actList", ActivityMapper.toActivityInstanceList(activities));
                    map.put("actGoodsList", ActivityGoodsMapper.toActivityGoodsInstanceList(activityGoodsList));
                }
            }
            result.setData(map);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getMallHomeById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult addMallHome(MallHomeParam mallHomeParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(mallHomeParam.getRequestId());
        try {
            MallHome mallHome = mallHomeService.addMallHome(mallHomeParam);
            if (mallHome.getId() == null) {
                br.setErrorMessage("999", "添加失败");
                return br;
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addMallHome", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult updateMallHome(MallHomeParam mallHomeParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(mallHomeParam.getRequestId());
        try {
            Long updateNum = mallHomeService.updateMallHome(mallHomeParam);
            if (updateNum < 0) {
                br.setErrorMessage("999", "修改失败");
                return br;
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateMallHome", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult delById(Long id, String showLocal) {
        BaseResult result = new BaseResult();
        try {
            int updateNum = mallHomeService.removeById(id, showLocal);
            if (updateNum <= 0) {
                result.setErrorMessage("999", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("removeById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListJsonResult<ImageInstance> queryBannerPage(ImageQueryParam queryParam) {
        ListJsonResult<ImageInstance> result = new ListJsonResult<ImageInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("type", ImageType.BANNER.getCodeValue());
            paramMap.put("seat", ImageSeat.MALLHOME.getCodeValue());
            paramMap.put("orderColumn", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<Image> lr = imageService.queryBannerPage(require);
            List<Image> imageList = lr.getData();
            result.setData(ImageMapper.toImageInstanceList(imageList));
            result.setDraw(lr.getDraw());
            result.setRecordsFiltered(lr.getRecordsFiltered());
            result.setRecordsTotal(lr.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryBannerPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public MapResult getBannerById(Long id, Long shopId) {
        MapResult result = new MapResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Image image = imageService.getBannerById(id);
            map.put("image", ImageMapper.toImageInstance(image));
            String fromType = image.getSourceType();
            if (MallHomeGoodsType.COMMON.getCodeValue().equals(fromType)) {
                List<Category> categories = categoryService.getAllCategoryList();
                Goods goods = goodsService.getGoodsById(image.getSeatId());
                List<Goods> goodsList = goodsService.getOnShelfGoodsByCategory(goods.getCategoryId());
                map.put("categories", CategoryMapper.toInstanceList(categories));
                map.put("categoryId", goods.getCategoryId());
                map.put("goodsList", GoodsMapper.toInstanceList(goodsList));
            } else {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("showLocal", ActivityShowLocal.ABC.getCodeValue());
                param.put("isShow", "1");
                param.put("shopId", shopId);
                param.put("type", fromType);
                List<Activity> activities = activityService.getActivityByParam(param);
                ActivityGoods activityGoods = activityGoodsService.getActivityGoodsById(image.getSeatId());
                List<ActivityGoods> activityGoodsList = activityGoodsService
                        .getActivityGoodsListByActivityId(activityGoods.getActivityId());
                map.put("actList", ActivityMapper.toActivityInstanceList(activities));
                map.put("activityId", activityGoods);
                map.put("actGoodsList", ActivityGoodsMapper.toActivityGoodsInstanceList(activityGoodsList));
            }
            result.setData(map);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getBannerById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult addBanner(ImageParam imageParam) {
        BaseResult result = new BaseResult();
        try {
            List<Image> images = imageService.queryImageList(null, ImageSeat.MALLHOME.getCodeValue());
            if (images.size() < 6) {
                imageParam.setSeat(ImageSeat.MALLHOME.getCodeValue());
                imageParam.setType(ImageType.BANNER.getCodeValue());
                Image img = imageService.saveImage(imageParam);
                if (img.getId() == null) {
                    result.setErrorMessage("999", "添加失败");
                }
            } else {
                result.setErrorMessage("999", "最多添加6张");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addBanner", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateBanner(ImageParam imageParam) {
        BaseResult result = new BaseResult();
        try {
            imageParam.setSeat(ImageSeat.MALLHOME.getCodeValue());
            Long updateNum = imageService.updateImage(imageParam);
            if (updateNum < 0) {
                result.setErrorMessage("999", "updateBanner");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addBanner", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult delBannerById(Long id, String seat) {
        BaseResult result = new BaseResult();
        try {
            int updateNum = imageService.removeById(id, seat);
            if (updateNum <= 0) {
                result.setErrorMessage("999", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delBannerById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    //    /** 首页所需要参数 **/
    //    private MallAllInstance getMallAll() {
    //        MallAllInstance mallAllIns = new MallAllInstance();
    //        List<MallHome> actMallHomeList = mallHomeService.getMallHomeListByLocal(MallHomeShowLocal.ACT.getCodeValue());
    //        List<MallHome> comMallHomeList = mallHomeService.getMallHomeListByLocal(MallHomeShowLocal.SPECIAL
    //                .getCodeValue());
    //        List<Image> imageList = imageService.getImageBySeatList(ImageSeat.MALLHOME.getCodeValue());
    //        List<ImageInstance> imageInsList = ImageMapper.toImageInstanceList(imageList);
    //        List<MallHomeInstance> actMallHomeInsList = MallHomeMapper.toMallHomeInsList(actMallHomeList);
    //        List<MallHomeInstance> comMallHomeInsList = MallHomeMapper.toMallHomeInsList(comMallHomeList);
    //        mallAllIns.setActMallHomeInstanceList(actMallHomeInsList);
    //        mallAllIns.setComMallHomeInstanceList(comMallHomeInsList);
    //        mallAllIns.setImageInstanceList(imageInsList);
    //        return mallAllIns;
    //    }

}
