package com.mocentre.tehui.ps.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.ps.dao.ICustomerAddressDao;
import com.mocentre.tehui.ps.model.CustomerAddress;

/**
 * 商城用户个人中心地址数据库操作接口实现。
 * 
 * @author update by yukaiji on 2017年1月19日
 */
@Repository
public class CustomerAddressDao extends BaseDao<CustomerAddress> implements ICustomerAddressDao {

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    @Override
    public int delById(Long id) {
        return super.delete("CustomerAddress_del_id", id);
    }

    /**
     * 根据id批量删除
     * 
     * @param ids
     * @return
     */
    @Override
    public int delById(List<Long> idList) {
        return super.delete("CustomerAddress_del_ids", idList);
    }

    /**
     * 根据用户id删除
     * 
     * @param customerId
     * @return
     */
    @Override
    public int delByCustomerId(Long customerId) {
        return super.delete("CustomerAddress_del_customerId", customerId);
    }

    /**
     * 根据用户idList批量删除
     * 
     * @param customerIds
     * @return
     */
    @Override
    public int delByCustomerId(List<Long> customerIds) {
        return super.delete("CustomerAddress_del_customerIds", customerIds);
    }

    /**
     * 将不需要改为默认的全部置为0
     * 
     * @param customerIds
     * @return
     */
    @Override
    public int updateDefult(CustomerAddress customerAddress) {
        return super.update("CustomerAddress_updateDefult", customerAddress);
    }

}
