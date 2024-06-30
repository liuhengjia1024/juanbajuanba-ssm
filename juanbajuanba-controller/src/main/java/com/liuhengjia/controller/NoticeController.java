package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.entity.Administrator;
import com.liuhengjia.entity.Notice;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.model.NoticeBean;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.AdministratorService;
import com.liuhengjia.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RequestMapping({"/notice"})
@RestController
public class NoticeController {
    private NoticeService noticeService;
    private AdministratorService administratorService;

    @PostMapping({"/admin-one"})
    public Result adminInsertOne(HttpServletRequest request, Notice notice) {
        HttpSession session = request.getSession();
        UserBean administrator = (UserBean) session.getAttribute("administrator");
        if (administrator == null) {
            throw new BusinessException(MessageConstant.NOT_SIGN_IN);
        }
        notice.setAdministratorId(administrator.getId());
        notice.setCreateTime(new Date());
        if (noticeService.insertOne(notice)) {
            return Result.builder().message(MessageConstant.INSERT_NOTICE_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.INSERT_NOTICE_FAIL);
    }

    @DeleteMapping({"/admin-byId"})
    public Result adminDeleteById(@RequestParam(name = "noticeId", defaultValue = "-1") Integer noticeId) {
        if (noticeService.deleteById(noticeId)) {
            return Result.builder().message(MessageConstant.DELETE_NOTICE_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.DELETE_NOTICE_FAIL);
    }

    @GetMapping({"/all"})
    public Result selectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<NoticeBean> noticeBeanList = noticeService.selectAll(pageNum);
        noticeBeanList.forEach(this::initNoticeBean);
        PageInfo<NoticeBean> pageInfo = new PageInfo<>(noticeBeanList);
        return Result.builder().data(pageInfo).build();
    }

    private void initNoticeBean(NoticeBean noticeBean) {
        Administrator administrator = administratorService.selectById(noticeBean.getNotice().getAdministratorId());
        if (administrator != null) {
            administrator.setAvatarPath(PathConstant.ADMINISTRATOR_AVATAR_DIR_PATH + administrator.getAvatarPath());
        }
    }
}
