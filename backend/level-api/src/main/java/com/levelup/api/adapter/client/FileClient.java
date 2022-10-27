package com.levelup.api.adapter.client;

import com.levelup.common.domain.FileType;
import com.levelup.common.util.file.UploadFile;
import com.levelup.common.web.dto.OkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "level-image", url = "http://localhost:8090/api/v1/file")
public interface FileClient {

    @GetMapping("")
    ResponseEntity<OkResponse<UploadFile>> get(
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("fileType") FileType fileType);
}
