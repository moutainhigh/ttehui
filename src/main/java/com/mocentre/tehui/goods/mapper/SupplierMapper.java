package com.mocentre.tehui.goods.mapper;

import com.mocentre.tehui.backend.model.SupplierInstance;
import com.mocentre.tehui.backend.param.SupplierParam;
import com.mocentre.tehui.goods.model.Supplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukaiji on 2017/8/30.
 */
public class SupplierMapper {

    public static Supplier toSupplier(SupplierParam param) {
        Supplier supplier = new Supplier();
        supplier.setId(param.getId());
        supplier.setName(param.getName());
        supplier.setTelephone(param.getTelephone());
        supplier.setGoodsCount(param.getGoodsCount());
        supplier.setMail(param.getMail());
        supplier.setNote(param.getNote());
        supplier.setPeriod(param.getPeriod());
        supplier.setTitle(param.getTitle());
        return supplier;
    }

    public static SupplierInstance toSupplierInstance(Supplier supplier) {
        SupplierInstance supplierIns = new SupplierInstance();
        supplierIns.setId(supplier.getId());
        supplierIns.setName(supplier.getName());
        supplierIns.setTelephone(supplier.getTelephone());
        supplierIns.setGoodsCount(supplier.getGoodsCount());
        supplierIns.setMail(supplier.getMail());
        supplierIns.setNote(supplier.getNote());
        supplierIns.setPeriod(supplier.getPeriod());
        supplierIns.setTitle(supplier.getTitle());
        return supplierIns;
    }

    public static List<SupplierInstance> toInstanceList(List<Supplier> supplierList) {
        List<SupplierInstance> listResult = new ArrayList<SupplierInstance>();
        if (supplierList.size() < 1) {
            return listResult;
        }
        for (Supplier supplier : supplierList) {
            SupplierInstance supplierInstance = toSupplierInstance(supplier);
            listResult.add(supplierInstance);
        }
        return listResult;
    }
}
