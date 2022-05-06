package com.together.levelup.controller;

import com.together.levelup.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final ChannelService channelService;

//    /**
//     * 생성
//     * */
//    @GetMapping("/create")
//    public String create(@RequestParam Long channel) {
//        return "html/notice/createNotice";
//    }

    /**
     * 조회
     * */
    @GetMapping("")
    public String notice(@RequestParam(required = false, defaultValue = "1") Long page,
                         @RequestParam(required = false) String field,
                         @RequestParam(required = false) String query,
                         HttpServletRequest request,
                         Model model) {
        return "html/notice/boardNotice";
    }

//    @GetMapping("/detail/{id}")
//    public String find(@PathVariable Long id,
//                       @RequestParam Long channel,
//                       HttpServletRequest request,
//                       Model model) {
//        model.addAttribute("isManager", false);
//
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute(SessionName.SESSION_NAME) == null) {
//            return "html/channel_notice/detailChannelNotice";
//        }
//
//        Member manager = channelService.findOne(channel).getMember();
//        Member findMember = (Member)session.getAttribute(SessionName.SESSION_NAME);
//
//        if (findMember.getId().equals(manager.getId())) {
//            model.addAttribute("isManager", true);
//        }
//
//        return "html/channel_notice/detailChannelNotice";
//    }
//
//    /**
//     * 수정
//     * */
//    @GetMapping("/edit/{id}")
//    public String update(@PathVariable Long id,
//                         @RequestParam Long channel) {
//        return "html/channel_notice/updateChannelNotice";
//    }

}
