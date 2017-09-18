package com.mocentre.tehui.common.constant;

/**
 * 类RedisKeyConstant.java的实现描述：缓存key值
 * 
 * @author sz.gong 2016年3月22日 下午4:16:21
 */
public class RedisKeyConstant {

    /** 区域缓存key前缀 **/
    public static String AREAS_CODE_LIST          = "areas:list:code";

    /** 库存基础信息缓存key前缀 **/
    public static String GOODS_STORAGE_LIST       = "goods:storage:list";

    /** 商品缓存key前缀 **/
    public static String GOODS_LIST               = "goods:list";

    /** 库存数量缓存key前缀 **/
    public static String GOODS_STOCKNUM           = "goods:stocknum";

    /** 短信验证码key前缀 **/
    public static String VERIFICATION_CODE        = "string_verificationcode";

    /** 用户可使用优惠券key前缀 **/
    public static String COUPON_USE_LIST          = "coupon:use:list";

    /** 用户不可使用优惠券key前缀 **/
    public static String COUPON_NOUSE_LIST        = "coupon:nouse:list";

    /** 默认分享缓存key **/
    public static String SHARE_CONFIG             = "share_config";

    /** 发现页缓存key **/
    public static String DISCOVER_LIST            = "discover:list";

    /** 活动商品缓存key前缀 **/
    public static String ACTGOODS_LIST            = "actgoods:list";

    /** 农行掌银banner列表key前缀 **/
    public static String MALLHOME_BANNER_LIST     = "mallhome:banner:list";

    /** 农行掌银活动商品列表key前缀 **/
    public static String MALLHOME_ACTGOODS_LIST   = "mallhome:actgoods:list";

    /** 农行掌银普通商品列表key前缀 **/
    public static String MALLHOME_GOODS_LIST      = "mallhome:goods:list";

    /** 每日优鲜活动商品列表key前缀 **/
    public static String THIRDGOODS_ACTGOODS_LIST = "thirdgoods:actgoods:list";

    /** 每日优鲜普通商品列表key前缀 **/
    public static String THIRDGOODS_GOODS_LIST    = "thirdgoods:goods:list";

    /** 第三方用户key前缀 **/
    public static String MEMBER_ACCOUNT           = "member:account";
}
