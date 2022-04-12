package com.together.community.controller;

import com.together.community.domain.channel.Channel;
import com.together.community.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    ChannelService channelService;

    @GetMapping("/")
    public String home(Model model) {
        List<Channel> findChannels = channelService.findAll(0, 10);
        model.addAttribute("channels", findChannels);
        return "home";
    }

}
