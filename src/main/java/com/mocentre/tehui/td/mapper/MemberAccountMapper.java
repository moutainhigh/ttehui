package com.mocentre.tehui.td.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mocentre.tehui.backend.model.MemberAccountInstance;
import com.mocentre.tehui.backend.param.MemberAccountParam;
import com.mocentre.tehui.frontend.model.MemberAccountFTInstance;
import com.mocentre.tehui.td.model.MemberAccount;

/**
 * Created by 王雪莹 on 2017/6/21.
 */
public class MemberAccountMapper {

    public static MemberAccount toMemberAccount(MemberAccountParam param) {
        MemberAccount memberAccount = new MemberAccount();
        BeanCopier copier = BeanCopier.create(MemberAccountParam.class, MemberAccount.class, false);
        copier.copy(param, memberAccount, null);
        return memberAccount;
    }

    public static List<MemberAccountInstance> toMemberAccountInstnceList(List<MemberAccount> list) {
        if (list == null) {
            return null;
        }
        List<MemberAccountInstance> instanceList = new ArrayList<MemberAccountInstance>();
        for (MemberAccount ma : list) {
            instanceList.add(toMemberAccountInstnce(ma));
        }
        return instanceList;
    }

    public static MemberAccountInstance toMemberAccountInstnce(MemberAccount memberAccount) {
        MemberAccountInstance instance = new MemberAccountInstance();
        // 绑定地址
        BeanCopier copier = BeanCopier.create(MemberAccount.class, MemberAccountInstance.class, false);
        copier.copy(memberAccount, instance, null);
        return instance;
    }

    public static MemberAccountFTInstance toMemberAccountFTInstnce(MemberAccount memberAccount) {
        MemberAccountFTInstance instance = new MemberAccountFTInstance();
        // 绑定地址
        BeanCopier copier = BeanCopier.create(MemberAccount.class, MemberAccountFTInstance.class, false);
        copier.copy(memberAccount, instance, null);
        return instance;
    }
}
