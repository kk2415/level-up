package com.levelup.image.controller.v1;

import com.levelup.common.domain.constant.FileType;
import com.levelup.common.web.dto.OkResponse;
import com.levelup.image.controller.v1.dto.FileResponse;
import com.levelup.image.domain.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping({"", "/"})
    public ResponseEntity<OkResponse<FileResponse>> create(
            @RequestParam Long ownerId,
            @RequestParam FileType fileType,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException
    {
        FileResponse result = FileResponse.from(fileService.create(ownerId, fileType, file));

        return ResponseEntity.ok().body(new OkResponse<>(HttpStatus.OK, result));
    }


    @GetMapping({"", "/"})
    public ResponseEntity<OkResponse<FileResponse>> get(
            @RequestParam Long ownerId,
            @RequestParam FileType fileType)
    {
        FileResponse result = FileResponse.from(fileService.get(ownerId, fileType));

        return ResponseEntity.ok().body(new OkResponse<>(HttpStatus.OK, result));
    }

    @GetMapping({"/list", "/list/"})
    public ResponseEntity<OkResponse<List<FileResponse>>> getFiles(
            @RequestParam List<Long> ownerIds,
            @RequestParam FileType fileType)
    {
        ownerIds.forEach(System.out::println);
        List<FileResponse> responses = fileService.get(ownerIds, fileType)
                .stream()
                .map(FileResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(new OkResponse<>(HttpStatus.OK, responses));
    }


    @PatchMapping({"", "/"})
    public ResponseEntity<Void> update(
            @RequestParam Long ownerId,
            @RequestParam FileType fileType,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException
    {
        fileService.update(ownerId, fileType, file);

        return ResponseEntity.ok().build();
    }
}