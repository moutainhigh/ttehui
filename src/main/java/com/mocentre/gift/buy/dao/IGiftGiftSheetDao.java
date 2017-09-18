package com.mocentre.gift.buy.dao;

import com.mocentre.gift.buy.model.GiftGiftSheet;

import java.util.List;
import java.util.Map;

/**
 * 礼品单dao接口
 * Created by 王雪莹 on 2017/4/10.
 */
public interface IGiftGiftSheetDao {
    /**
     * 新增礼品
     * @param gift
     */
    void save(GiftGiftSheet gift);
    /**
     * 根据id删除
     * @param customerId
     * @param idList
     */
    void delById(Long customerId, List<Long> idList);

    /**
     * 更新数量
     * @param id
     * @param num
     */
    void updateNum(Long id, Integer num);

    /**
     * 根据用户id查询
     * @param customerId
     * @return
     */
    List<GiftGiftSheet> getByCustomerid(Long customerId)  ;

    /**
     * 根据idList查询
     * @param idList
     * @return
     */
    List<GiftGiftSheet> getByIdList(List<Long> idList,Long customerId);

    /**
     * 根据条件查询
     * @param param
     * @return
     */
    List<GiftGiftSheet> queryList(Map<String, Object> param);
}
