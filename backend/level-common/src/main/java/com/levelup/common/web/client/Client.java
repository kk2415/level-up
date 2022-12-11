package com.levelup.common.web.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "level-channel", url = "http://localhost:8080/api/v1/channel-members")
public class Client {
}
