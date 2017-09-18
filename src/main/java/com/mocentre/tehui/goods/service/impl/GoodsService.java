package com.mocentre.tehui.goods.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.dao.IActivityGoodsDao;
import com.mocentre.tehui.backend.param.GoodsTopNParam;
import com.mocentre.tehui.common.constant.CONSTANT;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
//import com.mocentre.tehui.goods.dao.IGoodsChannelDao;
import com.mocentre.tehui.goods.dao.IGoodsDao;
import com.mocentre.tehui.goods.dao.IGoodsLaunchDao;
import com.mocentre.tehui.goods.dao.IGoodsParamDao;
import com.mocentre.tehui.goods.dao.IGoodsShareDao;
import com.mocentre.tehui.goods.dao.IGoodsStorageDao;
import com.mocentre.tehui.goods.enums.GoodsCheckedType;
import com.mocentre.tehui.goods.enums.GoodsShowType;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsLaunch;
import com.mocentre.tehui.goods.model.GoodsParam;
import com.mocentre.tehui.goods.model.GoodsShare;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.sub.dao.ISubjectGoodsDao;
import com.mocentre.tehui.system.dao.IAreasDao;
import com.mocentre.tehui.system.model.Areas;

/**
 * 商品service实现 Created by 王雪莹 on 2016/11/10.
 */
@Component
public class GoodsService implements IGoodsService {

    @Autowired
    private IGoodsDao         goodsDao;
    @Autowired
    private IGoodsShareDao    shareDao;
    @Autowired
    private IGoodsLaunchDao   launchDao;
    @Autowired
    private IGoodsParamDao    goodsParamDao;
    @Autowired
    private IGoodsStorageDao  storageDao;
    @Autowired
    private IAreasDao         areasDao;
    @Autowired
    private ISubjectGoodsDao  subjectGoodsDao;
    @Autowired
    private IActivityGoodsDao activityGoodsDao;

    @Override
    @DataSource(value = "write")
    public Goods addGoodsBaseInfo(Goods goods) {
        goods.setIsChecked(GoodsCheckedType.UNCHECK.getCodeValue());
        goods.setIsShow(GoodsShowType.NOTSHELF.getCodeValue());
        goodsDao.saveEntity(goods);
        if (goods.getId() != null) {
            goodsDao.updateToCache(goods);
        }
        return goods;
    }

    @Override
    @DataSource(value = "write")
    public void delGoodsById(Long id, Long shopId) {
        int count = goodsDao.logicRemove(id, shopId);
        if (count > 0) {
            shareDao.delByGoodsId(id);
            launchDao.delByGoodsId(id);
            goodsDao.deleteFromCache(id);
            List<GoodsStorage> storageList = storageDao.queryListByGoodsId(id);
            if (storageList != null && storageList.size() > 0) {
                storageDao.delByGoodsId(id);
                storageDao.deleteFromCache(storageList);
            }
        }
    }

    @Override
    @DataSource(value = "read")
    public List<Goods> getOnShelfGoodsByCategory(Long categoryId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("categoryId", categoryId);
        paramMap.put("isShow", GoodsShowType.ONSHELF.getCodeValue());
        paramMap.put("isChecked", GoodsCheckedType.CHECKPASS.getCodeValue());
        return goodsDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public Goods getGoodsById(Long id) {
        return goodsDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<Goods> getAllGoodsByShopId(Long shopId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("shopId", shopId);
        return goodsDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public List<Goods> getAllGoodsIsShow(Long shopId) {
        Map<String, Object> paramMap = new HashMap<>();
        if (shopId != null) {
            paramMap.put("shopId", shopId);
        }
        paramMap.put("isShow", GoodsShowType.ONSHELF.getCodeValue());
        paramMap.put("isChecked", GoodsCheckedType.CHECKPASS.getCodeValue());
        return goodsDao.queryList(paramMap);
    }

    /**
     * 分页查询
     * 
     * @param require
     * @return
     */
    @Override
    @DataSource(value = "read")
    public ListJsonResult<Goods> queryPage(Requirement require) {
        ListJsonResult<Goods> result = goodsDao.queryDatatablesPage(require);
        return result;
    }

    @Override
    @DataSource(value = "write")
    public void updateGoodsParam(Long shopId, Long goodsId, List<GoodsParam> goodsParamList) throws Exception {
        if (goodsId != null && shopId != null) {
            Goods goods = this.getGoodsByIdShop(goodsId, shopId);
            if (goods == null) {
                return;
            }
            if (goodsParamList != null && goodsParamList.size() > 0) {
                for (GoodsParam gp : goodsParamList) {
                    gp.setGoodsId(goodsId);
                    gp.setShopId(shopId);
                }
                goodsParamDao.delByGoodsId(goodsId);
                goodsParamDao.saveEntity(goodsParamList);
            }
        }
    }

    @Override
    @DataSource(value = "write")
    public Long updateGoodsBaseInfoAndArea(Goods goods, String putAreas) {
        Long id = goods.getId();
        Long shopId = goods.getShopId();
        Goods gds = this.getGoodsByIdShop(id, shopId);
        if (gds == null) {
            return 0l;
        }
        Long count = this.updateGoodsAndCache(goods);
        if (count > 0) {
            //商品投放
            List<GoodsLaunch> launchList = new ArrayList<GoodsLaunch>();
            if (StringUtils.isNotBlank(putAreas)) {
                String[] codes = putAreas.split("-");
                for (String code : codes) {
                    GoodsLaunch launch = new GoodsLaunch();
                    Areas areas = areasDao.getAreas(code);
                    launch.setGoodsId(id);
                    launch.setAreasCode(areas != null ? code : null);
                    launch.setAreasName(areas != null ? areas.getName() : null);
                    launchList.add(launch);
                }
                launchDao.delByGoodsId(id);
                launchDao.saveEntity(launchList);
            }
            //专题商品
            subjectGoodsDao.updateByGoods(id, goods.getTitle(), null, null, null, null);
            //商品分享
            GoodsShare share = new GoodsShare();
            share.setGoodsId(id);
            share.setTitle(goods.getTitle());
            share.setDescription(goods.getDescribe());
            share.setSharePic(goods.getImgCart());
            GoodsShare oldShare = shareDao.getByGoodsId(id);
            if (oldShare != null) {
                share.setId(oldShare.getId());
                shareDao.updateEntity(share);
            } else {
                shareDao.saveEntity(share);
            }
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public Long updateGoodsAndCache(Goods goods) {
        Long count = goodsDao.updateEntity(goods);
        if (count > 0) {
            Goods gds = goodsDao.get(goods.getId());
            goodsDao.updateToCache(gds);
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public Long updateGoodsDetail(Long id, Long shopId, String imgBanner, String details) {
        Goods goods = this.getGoodsByIdShop(id, shopId);
        if (goods == null) {
            return 0l;
        }
        goods.setImgBanner(imgBanner);
        goods.setDetails(details);
        Long count = this.updateGoodsAndCache(goods);
        return count;
    }

    @Override
    @DataSource(value = "write")
    public Long updateGoodsStatus(Long id, Long shopId, String status) {
        Goods goods = this.getGoodsByIdShop(id, shopId);
        if (goods == null) {
            return 0l;
        }
        if ("onShelf".equals(status)) {
            goods.setIsShow(GoodsShowType.ONSHELF.getCodeValue());
        } else if ("offShelf".equals(status)) {
            goods.setIsShow(GoodsShowType.OFFSHELF.getCodeValue());
        } else if ("pass".equals(status)) {
            goods.setIsChecked(GoodsCheckedType.CHECKPASS.getCodeValue());
        } else if ("notPassed".equals(status)) {
            goods.setIsChecked(GoodsCheckedType.CHECKFAIL.getCodeValue());
        }
        Long count = this.updateGoodsAndCache(goods);
        return count;
    }

    @Override
    @DataSource(value = "read")
    public Goods getGoodsByIdShop(Long id, Long shopId) {
        return goodsDao.get(id, shopId);
    }

    @Override
    @DataSource(value = "read")
    public String getGoodsStandardByCode(String standardJson, String standardCode) {
        StringBuffer buf = new StringBuffer();
        Map<String, String> resMap = new HashMap<String, String>();
        JSONArray jArr = JSON.parseArray(standardJson);
        for (int i = 0; i < jArr.size(); i++) {
            JSONObject jObj = jArr.getJSONObject(i);
            Map<String, Object> standardMap = JSON.parseObject(JSON.toJSONString(jObj), Map.class);
            String code = standardMap.get("code").toString();
            String name = standardMap.get("name").toString();
            standardMap.remove("code");
            standardMap.remove("name");
            if (standardMap != null) {
                for (Entry<String, Object> entry : standardMap.entrySet()) {
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    resMap.put(code + key, name + ":" + val);
                }
            }
        }
        String[] scodes = standardCode.split("-");
        if (resMap != null) {
            for (int i = 0; i < scodes.length; i++) {
                String scode = scodes[i];
                buf.append(resMap.get(scode));
                if ((i + 1) != scodes.length) {
                    buf.append(",");
                }
            }
        }
        return buf.toString();
    }

    /**
     * 按条件 按顺序 取前n件
     * 
     * @param param
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Goods> getTopN(GoodsTopNParam param) {
        Map<String, Object> paramMap = JSONObject.parseObject(JSON.toJSONString(param));
        List<Goods> goodsList = goodsDao.getTopN(paramMap);
        return goodsList;
    }

    /**
     * 销量最高的前Num件在线商品
     * 
     * @param shopId
     * @param num
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Goods> getSellTopN(Long shopId, int num) {
        GoodsTopNParam goodsTopNParam = new GoodsTopNParam();
        goodsTopNParam.setShopId(shopId);
        goodsTopNParam.setIsShow(GoodsShowType.ONSHELF.getCodeValue());
        goodsTopNParam.setIsChecked(GoodsCheckedType.CHECKPASS.getCodeValue());
        goodsTopNParam.setSaleTotalOrder(CONSTANT.ORDERBY_DESC);
        goodsTopNParam.setLimit(0);
        goodsTopNParam.setOffset(num);
        return getTopN(goodsTopNParam);
    }

    /**
     * 库存量最高的前Num件在线商品
     * 
     * @param shopId
     * @param num
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Goods> getStoreTopN(Long shopId, int num) {
        GoodsTopNParam goodsTopNParam = new GoodsTopNParam();
        goodsTopNParam.setShopId(shopId);
        goodsTopNParam.setIsShow(GoodsShowType.ONSHELF.getCodeValue());
        goodsTopNParam.setIsChecked(GoodsCheckedType.CHECKPASS.getCodeValue());
        goodsTopNParam.setStoreTotalOrder(CONSTANT.ORDERBY_DESC);
        goodsTopNParam.setLimit(0);
        goodsTopNParam.setOffset(num);
        return getTopN(goodsTopNParam);
    }

    /**
     * 对库存原价和售价进行排序，获取展示价格区间
     * 
     * @param goodsStorageList 库存价格信息
     */
    private Map<String, Object> sortPrice(List<GoodsStorage> goodsStorageList) {
        Map<String, Object> map = new HashMap<>();

        Collections.sort(goodsStorageList, new Comparator<GoodsStorage>() {
            @Override
            public int compare(GoodsStorage o1, GoodsStorage o2) {
                return o1.getSalePrice().compareTo(o2.getSalePrice());
            }
        });
        GoodsStorage firstStorage = goodsStorageList.get(0);
        GoodsStorage lastStorage = goodsStorageList.get(goodsStorageList.size() - 1);
        String salePrice;
        BigDecimal sfPrice = new BigDecimal(firstStorage.getSalePrice()).divide(new BigDecimal(100));
        BigDecimal stPrice = new BigDecimal(lastStorage.getSalePrice()).divide(new BigDecimal(100));
        if (sfPrice.compareTo(stPrice) != 0) {
            salePrice = sfPrice + "-" + stPrice;
        } else {
            salePrice = sfPrice.toString();
        }
        Collections.sort(goodsStorageList, new Comparator<GoodsStorage>() {
            @Override
            public int compare(GoodsStorage o1, GoodsStorage o2) {
                return o1.getOldPrice().compareTo(o2.getOldPrice());
            }
        });
        String oldPrice;
        BigDecimal ofPrice = new BigDecimal(firstStorage.getOldPrice()).divide(new BigDecimal(100));
        BigDecimal otPrice = new BigDecimal(lastStorage.getOldPrice()).divide(new BigDecimal(100));
        if (ofPrice.compareTo(otPrice) != 0) {
            oldPrice = ofPrice + "-" + otPrice;
        } else {
            oldPrice = ofPrice.toString();
        }
        map.put("salePrice", salePrice);
        map.put("oldPrice", oldPrice);
        map.put("saleLowPrice", firstStorage.getSalePrice());
        map.put("oldLowPrice", firstStorage.getOldPrice());
        return map;
    }

    @Override
    @DataSource(value = "read")
    public Goods getGoodsBaseInfoFromCache(Long id) {
        return goodsDao.queryFromCache(id);
    }

    @Override
    @DataSource(value = "write")
    public void updateGoodsBaseInfoToCache(Goods goods) {
        goodsDao.updateToCache(goods);
    }

    @Override
    @DataSource(value = "write")
    public Boolean saveOrupdateGoodsSkuAndCache(Long goodsId, String standard, Long shopId,
                                                List<GoodsStorage> goodsStorageList) {
        if (goodsId == null || StringUtils.isBlank(standard) || shopId == null || goodsStorageList == null
                || goodsStorageList.isEmpty()) {
            return false;
        }
        Goods goods = goodsDao.get(goodsId, shopId);
        if (goods == null) {
            return false;
        }
        Long storeTotla = 0l;
        for (GoodsStorage gs : goodsStorageList) {
            gs.setVersion(0l);
            gs.setGoodsId(goodsId);
            gs.setSubGoodsId(0l);
            if (StringUtils.isBlank(gs.getStandardCode())) {
                return false;
            }
            storeTotla = storeTotla + gs.getStockNum();
        }
        goods.setStandard(standard);
        goods.setStoreTotal(storeTotla);
        Map<String, Object> priceMap = this.sortPrice(goodsStorageList);
        String salePrice = (String) priceMap.get("salePrice");
        String oldPrice = (String) priceMap.get("oldPrice");
        Long saleLowPrice = (Long) priceMap.get("saleLowPrice");
        Long oldLowPrice = (Long) priceMap.get("oldLowPrice");
        goods.setSellPrice(salePrice);
        goods.setOldPrice(oldPrice);
        goods.setSellLowPrice(saleLowPrice);
        goods.setOldLowPrice(oldLowPrice);
        JSONArray holdArr = new JSONArray();
        for (GoodsStorage goodsStorage : goodsStorageList) {
            String stcode = goodsStorage.getStandardCode();
            String stname = goodsStorage.getDescribe();
            JSONObject jobj = new JSONObject();
            jobj.put("code", stcode);
            jobj.put("name", stname);
            holdArr.add(jobj);
        }
        String holdStandard = holdArr.toJSONString();
        goods.setHoldStandard(holdStandard);
        subjectGoodsDao.updateByGoods(goodsId, goods.getTitle(), salePrice, oldPrice, saleLowPrice, oldLowPrice);
        List<GoodsStorage> oldGoodsStorageList = new ArrayList<GoodsStorage>();
        if (standard.equals(goods.getStandard())) {//对修改商品属性做出不同判断
            oldGoodsStorageList = storageDao.queryByGoods(goodsId, 0l);
            storageDao.delByGoodsId(goodsId, 0l);
        } else {
            oldGoodsStorageList = storageDao.queryListByGoodsId(goodsId);
            storageDao.delByGoodsId(goodsId);
        }
        this.updateGoodsAndCache(goods);//更新商品和缓存
        storageDao.saveEntity(goodsStorageList);
        storageDao.deleteFromCache(oldGoodsStorageList);//删除原有缓存库存信息和库存数量
        storageDao.updateBatchToCache(goodsStorageList);//新增现有库存到缓存
        return true;
    }

    /**
     * 获取全部上线商品
     * 
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Goods> getAllGoodsIsShow() {
        return this.getAllGoodsIsShow(null);
    }

}
