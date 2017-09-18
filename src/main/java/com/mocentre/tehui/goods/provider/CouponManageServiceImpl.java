package com.mocentre.tehui.goods.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.MapResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.CouponManageService;
import com.mocentre.tehui.backend.model.CategoryInstance;
import com.mocentre.tehui.backend.model.CouponDetailInstance;
import com.mocentre.tehui.backend.model.CouponInstance;
import com.mocentre.tehui.backend.model.CustomerInfoInstance;
import com.mocentre.tehui.backend.model.GoodsInstance;
import com.mocentre.tehui.backend.param.CouponDetailQueryParam;
import com.mocentre.tehui.backend.param.CouponParam;
import com.mocentre.tehui.backend.param.CouponQueryParam;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.enums.CouponRelateType;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.mapper.CouponMapper;
import com.mocentre.tehui.goods.mapper.GoodsMapper;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.model.Coupon;
import com.mocentre.tehui.goods.model.CouponDetail;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.service.ICategoryService;
import com.mocentre.tehui.goods.service.ICouponDetailService;
import com.mocentre.tehui.goods.service.ICouponService;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.ps.model.CustomerInfo;
import com.mocentre.tehui.ps.service.ICustomerInfoService;
import com.mocentre.tehui.shop.service.IShopService;

/**
 * 优惠券接口实现 Created by yukaiji on 2016/11/24.
 */
public class CouponManageServiceImpl implements CouponManageService {

    @Autowired
    private ICouponService       couponService;
    @Autowired
    private ICouponDetailService couponDetailService;
    @Autowired
    private ICategoryService     categoryService;
    @Autowired
    private IShopService         shopService;
    @Autowired
    private IGoodsService        goodsService;
    @Autowired
    private ICustomerInfoService customerInfoService;

    @Override
    public ListJsonResult<CouponInstance> queryCouponPage(CouponQueryParam couponQueryParam) {
        ListJsonResult<Coupon> result = new ListJsonResult<Coupon>();
        ListJsonResult<CouponInstance> resultData = new ListJsonResult<CouponInstance>();
        resultData.setRequestId(couponQueryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderColumn", couponQueryParam.getOrderColumn());
            paramMap.put("orderBy", couponQueryParam.getOrderBy());
            paramMap.put("shopId", couponQueryParam.getShopId());
            paramMap.put("beginTime", couponQueryParam.getBeginTime());
            paramMap.put("endTime", couponQueryParam.getEndTime());
            paramMap.put("couponName", couponQueryParam.getCouponName());
            Requirement require = new Requirement(couponQueryParam.getDraw(), couponQueryParam.getStart(),
                    couponQueryParam.getLength(), paramMap);
            result = couponService.queryCouponPage(require);
            List<Coupon> listData = result.getData();
            List<CouponInstance> listInstance = CouponMapper.toInstanceList(listData);
            resultData.setData(listInstance == null ? new ArrayList<CouponInstance>() : listInstance);
            resultData.setDraw(result.getDraw());
            resultData.setCode(result.getCode());
            resultData.setMessage(result.getMessage());
            resultData.setRecordsTotal(result.getRecordsTotal());
            resultData.setRecordsFiltered(result.getRecordsFiltered());
            resultData.setSuccess(result.isSuccess());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryCouponPage", e);
            resultData.setErrorMessage("999", "系统错误");
        }
        return resultData;
    }

    @Override
    public ListJsonResult<CouponDetailInstance> queryCouponDetailPage(CouponDetailQueryParam couponDetailQueryParam) {
        ListJsonResult<CouponDetail> result = new ListJsonResult<CouponDetail>();
        ListJsonResult<CouponDetailInstance> resultData = new ListJsonResult<CouponDetailInstance>();
        resultData.setRequestId(couponDetailQueryParam.getRequestId());
        try {
            String telephone = couponDetailQueryParam.getTelephone();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderColumn", couponDetailQueryParam.getOrderColumn());
            paramMap.put("orderBy", couponDetailQueryParam.getOrderBy());
            paramMap.put("couponId", couponDetailQueryParam.getId());
            paramMap.put("couponSn", couponDetailQueryParam.getCouponSn());
            if (StringUtils.isNotBlank(telephone)) {
                CustomerInfo cumInfo = customerInfoService.getCustomerByTelephone(telephone);
                if (cumInfo != null) {
                    paramMap.put("customerId", cumInfo.getId());
                }
            }
            Requirement require = new Requirement(couponDetailQueryParam.getDraw(), couponDetailQueryParam.getStart(),
                    couponDetailQueryParam.getLength(), paramMap);
            result = couponDetailService.queryCouponDetailPage(require);
            List<CouponDetail> listData = result.getData();
            List<CouponDetailInstance> listInstance = CouponMapper.toDetailInstanceList(listData);
            if (listInstance != null) {
                for (CouponDetailInstance cumdIns : listInstance) {
                    Long cumId = cumdIns.getCustomerId();
                    if (cumId != null && cumId > 0) {
                        CustomerInfo cumInfo = customerInfoService.getCustomerById(cumId);
                        CustomerInfoInstance customerInfoIns = new CustomerInfoInstance();
                        customerInfoIns.setTelephone(cumInfo.getTelephone());
                        cumdIns.setCustomerInfo(customerInfoIns);
                    }
                }
            }
            resultData.setData(listInstance);
            resultData.setDraw(result.getDraw());
            resultData.setCode(result.getCode());
            resultData.setMessage(result.getMessage());
            resultData.setRecordsTotal(result.getRecordsTotal());
            resultData.setRecordsFiltered(result.getRecordsFiltered());
            resultData.setSuccess(result.isSuccess());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryCouponDetailPage", e);
            resultData.setErrorMessage("999", "系统错误");
        }
        return resultData;
    }

    @Override
    public MapResult preEdit(Long id, Long shopId, String requestId) {
        MapResult mr = new MapResult();
        mr.setRequestId(requestId);
        try {
            Map<String, Object> resMap = new HashMap<String, Object>();
            Coupon coupon = couponService.getCoupon(id, shopId);
            String relateType = coupon.getRelateType();
            List<GoodsInstance> goodsInsList = new ArrayList<GoodsInstance>();
            if (CouponRelateType.GOODS.getCodeValue().equals(relateType)) {
                String typeIds = coupon.getTypeIds();
                String[] ids = typeIds.split(",");
                for (String tid : ids) {
                    Goods goods = goodsService.getGoodsById(Long.parseLong(tid));
                    GoodsInstance goodsIns = GoodsMapper.toInstance(goods);
                    goodsInsList.add(goodsIns);
                }
            }
            List<Category> categoryList = categoryService.getAllCategoryList();
            CouponInstance couponInstance = CouponMapper.toInstance(coupon);
            List<CategoryInstance> categoryInstanceList = CategoryMapper.toInstanceList(categoryList);
            resMap.put("coupon", couponInstance);
            resMap.put("categroyList", categoryInstanceList);
            resMap.put("goodsInsList", goodsInsList);
            mr.setData(resMap);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getCoupon", e);
            mr.setErrorMessage("999", "系统错误");
        }
        return mr;
    }

    @Override
    public PlainResult<CouponInstance> getCoupon(Long id, Long shopId, String requestId) {
        PlainResult<CouponInstance> pr = new PlainResult<CouponInstance>();
        pr.setRequestId(requestId);
        try {
            Coupon coupon = couponService.getCoupon(id, shopId);
            CouponInstance couponInstance = CouponMapper.toInstance(coupon);
            pr.setData(couponInstance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("preEdit", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public BaseResult addCoupon(CouponParam couponParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(couponParam.getRequestId());
        try {
            Coupon c = CouponMapper.toCoupon(couponParam);
            if (c.getIsLimit() == 0) {
                c.setCouponSn(CommUtil.generateShortUUID());
            } else if (c.getIsLimit() == 1) {
                c.setCouponSn("");
            }
            Coupon coupon = couponService.addCoupon(c);
            if (coupon == null || coupon.getId() == null) {
                result.setErrorMessage("1001", "新增失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addCoupon", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateCoupon(CouponParam couponParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(couponParam.getRequestId());
        try {
            Coupon c = CouponMapper.toCoupon(couponParam);
            Long count = couponService.updateCouponAndDetail(c);
            if (count <= 0) {
                result.setErrorMessage("1001", "编辑失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateCoupon", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult deleteCoupon(List<Long> idList, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            couponService.deleteCoupon(idList);
            couponDetailService.deleteAllCouponDetail(idList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteCoupon", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
