package com.levelup.api.controller;

import com.levelup.api.api.SessionName;
import com.levelup.api.service.ChannelService;
import com.levelup.core.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/project/create")
    public String createProject() {
        return "html/channel/createProjectChannel";
    }

    @GetMapping("/study/create")
    public String createStudy() {
        return "html/channel/createStudyChannel";
    }

    @GetMapping("/edit/{channelId}")
    public String edit() {
        return "html/channel/updateChannel";
    }

    @GetMapping("/detail/{channelId}")
    public String detail(@PathVariable Long channelId,
                         @RequestParam(required = false, defaultValue = "1") Long page,
                         @RequestParam(required = false) String field,
                         @RequestParam(required = false) String query,
                         HttpServletRequest request,
                         Model model) {
        model.addAttribute("isManager", false);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionName.SESSION_NAME) == null) {
            return "html/channel/detailChannel";
        }

        Member manager = channelService.findOne(channelId).getMember();
        Member findMember = (Member)session.getAttribute(SessionName.SESSION_NAME);

        if (findMember.getId().equals(manager.getId())) {
            model.addAttribute("isManager", true);
        }

        return "html/channel/detailChannel";
    }

    @GetMapping("/{channelId}/manager")
    public String detailManager(@PathVariable Long channelId) {
        return "html/channel/manager";
    }


    @GetMapping("/detail-description/{channelId}")
    public String detailDescription() {
        return "html/channel/detailChannelDescription";
    }

    @PostMapping("/create")
    public String createPost() {
        return "redirect:/";
    }

}
