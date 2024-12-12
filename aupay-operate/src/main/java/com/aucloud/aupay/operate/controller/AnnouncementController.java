package com.aucloud.aupay.operate.controller;

import com.aucloud.aupay.operate.orm.po.AupayAnnouncement;
import com.aucloud.aupay.operate.orm.service.AupayAnnouncementService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.PageQuery;
import com.aucloud.commons.pojo.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    @Autowired
    private AupayAnnouncementService aupayAnnouncementService;

    @RequestMapping(value = "viewAnnouncement", method = RequestMethod.POST)
    public Result<Page<AupayAnnouncement>> viewAnnouncement(@RequestBody PageQuery<?> pageQuery) {
//        Object conditions = pageQuery.getConditions();
        Page<AupayAnnouncement> pager = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
        Page<AupayAnnouncement> page = aupayAnnouncementService.lambdaQuery()
                .eq(AupayAnnouncement::getIsDel, 0)
                .eq(AupayAnnouncement::getIsShow, 1)
                .orderByDesc(AupayAnnouncement::getCreateTime)
                .page(pager);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), page);
    }

    /**
     * 公告管理接口 ↓
     *
     */

    @RequestMapping(value = "findAnnouncementList", method = RequestMethod.POST)
//    @Operation(value = OperationEnum.FIND_ANNOUNCEMENT_LIST, handler = AdminOperationHandler.class)
    public Result<Page<AupayAnnouncement>> findAnnouncementList(@RequestBody PageQuery<?> pageQuery) {
        Page<AupayAnnouncement> pager = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
        Page<AupayAnnouncement> page = aupayAnnouncementService.lambdaQuery()
                .eq(AupayAnnouncement::getIsDel, 0)
                .page(pager);
//        OperationParam.operate(true, SecurityTokenHandler.getTokenInfoObject().getId());
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), page);
    }

    @RequestMapping(value = "addAnnouncement", method = RequestMethod.POST)
//    @Operation(value = OperationEnum.ADD_ANNOUNCEMENT, handler = AdminOperationHandler.class)
    public Result<Boolean> addAnnouncement(@RequestBody AupayAnnouncement aupayAnnouncement) {
        boolean b = aupayAnnouncementService.save(aupayAnnouncement);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }

    @RequestMapping(value = "updateAnnouncement", method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.UPDATE_ANNOUNCEMENT, handler = AdminOperationHandler.class)
    public Result<Boolean> updateAnnouncement(@RequestBody AupayAnnouncement aupayAnnouncement) {
        boolean b = aupayAnnouncementService.updateById(aupayAnnouncement);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }

    @RequestMapping(value = "deleteAnnouncement", method = RequestMethod.DELETE)
//    @Operation(value = OperationEnum.DELETE_ANNOUNCEMENT, handler = AdminOperationHandler.class)
    public Result<Boolean> deleteAnnouncement(@RequestParam String announcementId) {
        boolean b = aupayAnnouncementService.removeById(announcementId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }
}
