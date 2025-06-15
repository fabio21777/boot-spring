package com.boot.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDownloadRepository extends JpaRepository<FileDownload, Long> {
}
