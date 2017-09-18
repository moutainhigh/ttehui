package com.mocentre.tehui.sub.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.SubjectManageService;
import com.mocentre.tehui.backend.model.CategoryInstance;
import com.mocentre.tehui.backend.model.SubjectAllMsgInstance;
import com.mocentre.tehui.backend.model.SubjectAndTmpInstance;
import com.mocentre.tehui.backend.model.SubjectGoodsInstance;
import com.mocentre.tehui.backend.model.SubjectInstance;
import com.mocentre.tehui.backend.model.SubjectTempletInstance;
import com.mocentre.tehui.backend.param.SubjectGoodsParam;
import com.mocentre.tehui.backend.param.SubjectGoodsQueryParam;
import com.mocentre.tehui.backend.param.SubjectParam;
import com.mocentre.tehui.backend.param.SubjectQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.model.Navigate;
import com.mocentre.tehui.goods.service.ICategoryService;
import com.mocentre.tehui.goods.service.INavigateService;
import com.mocentre.tehui.sub.mapper.SubjectGoodsMapper;
import com.mocentre.tehui.sub.mapper.SubjectMapper;
import com.mocentre.tehui.sub.mapper.SubjectTempletMapper;
import com.mocentre.tehui.sub.model.Subject;
import com.mocentre.tehui.sub.model.SubjectGoods;
import com.mocentre.tehui.sub.model.SubjectTemplet;
import com.mocentre.tehui.sub.service.ISubjectGoodsService;
import com.mocentre.tehui.sub.service.ISubjectService;
import com.mocentre.tehui.sub.service.ISubjectTempletService;

/**
 * 专题接口实现 Created by yukaiji on 2016/11/24.
 */
public class SubjectManageServiceImpl implements SubjectManageService {

    @Autowired
    private ISubjectService        subjectService;
    @Autowired
    private ISubjectGoodsService   subjectGoodsService;
    @Autowired
    private ISubjectTempletService subjectTempletService;
    @Autowired
    private ICategoryService       categoryService;
    @Autowired
    private INavigateService       navigateService;

    @Override
    public ListJsonResult<SubjectInstance> querySubjectPage(SubjectQueryParam subjectQuery) {
        ListJsonResult<SubjectInstance> result = new ListJsonResult<SubjectInstance>();
        String requestId = subjectQuery.getRequestId();
        result.setRequestId(requestId);
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("shopId", subjectQuery.getShopId());
            paramMap.put("orderBy", subjectQuery.getOrderBy());
            paramMap.put("orderColumn", subjectQuery.getOrderColumn());
            Requirement require = new Requirement(subjectQuery.getDraw(), subjectQuery.getStart(),
                    subjectQuery.getLength(), paramMap);
            result = subjectService.querySubjectPage(require);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("querySubjectPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListJsonResult<SubjectGoodsInstance> querySubjectGoodsPage(SubjectGoodsQueryParam sbjGoodsQueryParam) {
        ListJsonResult<SubjectGoodsInstance> result = new ListJsonResult<SubjectGoodsInstance>();
        String requestId = sbjGoodsQueryParam.getRequestId();
        result.setRequestId(requestId);
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("subjectId", sbjGoodsQueryParam.getSubjectId());
            paramMap.put("orderBy", sbjGoodsQueryParam.getOrderBy());
            paramMap.put("orderColumn", sbjGoodsQueryParam.getOrderColumn());
            Requirement require = new Requirement(sbjGoodsQueryParam.getDraw(), sbjGoodsQueryParam.getStart(),
                    sbjGoodsQueryParam.getLength(), paramMap);
            result = subjectGoodsService.querySubjectPage(require);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("querySubjectGoodsPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<SubjectTempletInstance> getSubTmp() {
        ListResult<SubjectTempletInstance> result = new ListResult<SubjectTempletInstance>();
        try {
            List<SubjectTemplet> templetList = subjectTempletService.queryAll();
            result.setData(SubjectTempletMapper.toSubjectTempletInstanceList(templetList));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getSubTmp", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<SubjectAndTmpInstance> subEdit(Long id, Long shopId) {
        PlainResult<SubjectAndTmpInstance> result = new PlainResult<SubjectAndTmpInstance>();
        SubjectAndTmpInstance subAndTmpIns = new SubjectAndTmpInstance();
        try {
            List<SubjectTemplet> templetList = subjectTempletService.queryAll();
            Subject subject = subjectService.getSubjectById(id, shopId);
            subAndTmpIns.setSubject(SubjectMapper.toSubjectInstance(subject));
            subAndTmpIns.setSubTempList(SubjectTempletMapper.toSubjectTempletInstanceList(templetList));
            result.setData(subAndTmpIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("subEdit", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<SubjectAllMsgInstance> subGoodsAdd(Long subjectId, String requestId) {
        PlainResult<SubjectAllMsgInstance> pr = new PlainResult<SubjectAllMsgInstance>();
        pr.setRequestId(requestId);
        SubjectAllMsgInstance subAllMsg = new SubjectAllMsgInstance();
        List<CategoryInstance> categoryInsList = new ArrayList<CategoryInstance>();
        try {
            List<Category> categoryList = categoryService.getAllCategoryList();
            Subject subject = subjectService.getSubjectById(subjectId);
            if (subject == null) {
                pr.setErrorMessage("1001", "数据不存在");
            }
            SubjectTemplet subTemp = subjectTempletService.getSubjectTempletByCode(subject.getSubTmpCode());
            SubjectInstance subIns = SubjectMapper.toSubjectInstance(subject, subTemp);
            if (categoryList != null) {
                categoryInsList = CategoryMapper.toInstanceList(categoryList);
            }
            subAllMsg.setSubject(subIns);
            subAllMsg.setCategoryList(categoryInsList);
            pr.setData(subAllMsg);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("subGoodsEdit", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public PlainResult<SubjectAllMsgInstance> subGoodsEdit(Long subjectId, Long subGoodsId, String requestId) {
        PlainResult<SubjectAllMsgInstance> pr = new PlainResult<SubjectAllMsgInstance>();
        pr.setRequestId(requestId);
        SubjectAllMsgInstance subAllMsg = new SubjectAllMsgInstance();
        try {
            SubjectGoods subjectGoods = subjectGoodsService.querySubjectGoods(subGoodsId);
            Subject subject = subjectService.getSubjectById(subjectId);
            if (subjectGoods == null || subject == null) {
                pr.setErrorMessage("1001", "数据不存在");
            }
            SubjectTemplet subTemp = subjectTempletService.getSubjectTempletByCode(subject.getSubTmpCode());
            SubjectInstance subIns = SubjectMapper.toSubjectInstance(subject, subTemp);
            SubjectGoodsInstance subGoodsIns = SubjectGoodsMapper.toSubjectGoodsInstance(subjectGoods);
            subAllMsg.setSubject(subIns);
            subAllMsg.setSubjectGoods(subGoodsIns);
            pr.setData(subAllMsg);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("subGoodsEdit", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public BaseResult addSubject(SubjectParam subjectParam) {
        BaseResult result = new BaseResult();
        try {
            Subject subject = subjectService.addSubject(subjectParam);
            if (subject.getId() == null) {
                result.setErrorMessage("1001", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addSubject", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateSubject(SubjectParam subjectParam) {
        BaseResult result = new BaseResult();
        try {
            Long updateNum = subjectService.updateSubject(subjectParam);
            if (updateNum <= 0) {
                result.setErrorMessage("1001", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateSubject", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult deleteSubject(Long id) {
        BaseResult result = new BaseResult();
        try {
            List<Navigate> navigates = navigateService.getByTypeId(id);
            if (navigates.size() > 0) {
                result.setErrorMessage("1001", "导航栏已关联此专题");
                return result;
            }
            int num = subjectService.delById(id);
            if (num <= 0) {
                result.setErrorMessage("1001", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteSubject", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult addSubjectGoods(SubjectGoodsParam subGoodsParam) {
        BaseResult result = new BaseResult();
        try {
            SubjectGoods subjectGoods = subjectGoodsService.insertSubjectGoods(subGoodsParam);
            if (subjectGoods.getId() == null) {
                result.setErrorMessage("1001", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addSubjectGoods", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateSubjectGoods(SubjectGoodsParam subGoodsParam) {
        BaseResult result = new BaseResult();
        try {
            Long updateNum = subjectGoodsService.updateSubjectGoods(subGoodsParam);
            if (updateNum <= 0) {
                result.setErrorMessage("1001", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateSubjectGoods", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult deleteSubjectGoods(Long id) {
        BaseResult result = new BaseResult();
        try {
            int num = subjectGoodsService.delByid(id);
            if (num <= 0) {
                result.setErrorMessage("1001", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteSubject", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult show(Long id, String isShow, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            int count = subjectService.show(id, isShow);
            if (count == 0) {
                result.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("show", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<SubjectInstance> getShowSubjectList(String requestId) {
        ListResult<SubjectInstance> lr = new ListResult<SubjectInstance>();
        try {
            List<Subject> subList = subjectService.getShowSubjectList();
            List<SubjectInstance> subInsList = SubjectMapper.toSubjectInstanceList(subList);
            lr.setData(subInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getSubjectList", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public PlainResult<SubjectInstance> getSubjectById(Long subjectId, String requestId) {
        PlainResult<SubjectInstance> pr = new PlainResult<SubjectInstance>();
        pr.setRequestId(requestId);
        try {
            Subject subject = subjectService.getSubjectById(subjectId);
            if (subject == null) {
                pr.setErrorMessage("1001", "数据不存在");
                return pr;
            }
            SubjectTemplet subTemp = subjectTempletService.getSubjectTempletByCode(subject.getSubTmpCode());
            SubjectInstance subIns = SubjectMapper.toSubjectInstance(subject, subTemp);
            pr.setData(subIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getSubjectList", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

}
