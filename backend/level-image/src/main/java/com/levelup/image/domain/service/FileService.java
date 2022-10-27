package com.levelup.image.domain.service;

import com.levelup.common.domain.FileType;
import com.levelup.common.exception.ErrorCode;
import com.levelup.common.util.file.FileStore;
import com.levelup.common.util.file.UploadFile;
import com.levelup.image.domain.entity.File;
import com.levelup.image.domain.repository.FileRepository;
import com.levelup.image.domain.service.dto.FileDto;
import com.levelup.image.exception.FileException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileStore fileStore;
    private final FileRepository fileRepository;

    public FileDto create(Long ownerId, FileType fileType, MultipartFile requestFile) throws IOException {
        validateDuplication(ownerId, fileType);

        UploadFile uploadFile = fileStore.storeFile(fileType, requestFile);
        File file = File.of(null, uploadFile, fileType, ownerId);

        fileRepository.save(file);

        return FileDto.from(file);
    }

    private void validateDuplication(Long ownerId, FileType fileType) {
        Optional<File> file = fileRepository.findByOwnerIdAndFileType(ownerId, fileType);

        if (file.isPresent()) {
            throw new FileException(ErrorCode.FILE_DUPLICATION);
        }
    }


    @Transactional(readOnly = true)
    public FileDto get(Long ownerId, FileType fileType) {
        File file = fileRepository.findByOwnerIdAndFileType(ownerId, fileType)
                .orElseThrow(() -> new FileException(ErrorCode.FILE_NOT_FOUND));

        return FileDto.from(file);
    }

    @Transactional(readOnly = true)
    public List<FileDto> get(List<Long> ownerIds, FileType fileType) {
        List<File> files = fileRepository.findByOwnerIdAndFileType(ownerIds, fileType);

        return files.stream()
                .map(FileDto::from)
                .collect(Collectors.toUnmodifiableList());
    }


    /**
     * 1. 기존 파일을 S3에서 삭제
     * 2. 새 파일 S3에 저장
     * 3. 새 파일 URL을 데이터베이스에 업데이트
     * */
    public void update(Long ownerId, FileType fileType, MultipartFile requestFile) throws IOException {
        File file = fileRepository.findByOwnerIdAndFileType(ownerId, fileType)
                .orElseThrow(() -> new FileException(ErrorCode.FILE_NOT_FOUND));

        fileStore.deleteFile(file.getUploadFile().getStoreFileName());
        UploadFile newUploadFile = fileStore.storeFile(FileType.CHANNEL, requestFile);

        file.update(newUploadFile);
    }
}
