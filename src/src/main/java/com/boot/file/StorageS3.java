package com.boot.file;

import com.boot.security.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

public class StorageS3 implements StorageService{

    private final FileRepository fileRepository;

    private final FileDownloadRepository fileDownloadRepository;

    private final S3Client s3Client;

    private static final Logger logger = LoggerFactory.getLogger("StorageS3");

    @Value("${s3.bucket.name}")
    private String bucket;

    public StorageS3(FileRepository fileRepository, FileDownloadRepository fileDownloadRepository, S3Client s3Client) {
        this.fileRepository = fileRepository;
        this.fileDownloadRepository = fileDownloadRepository;
        this.s3Client = s3Client;
    }


    @Override
    public File store(String originalName, String bucket, User user, InputStream content) {
        if (originalName == null) {
            throw new StorageError("Nome original indefinido");
        }
        Assert.hasText(originalName, "Nome original não pode ser vazio");

        Assert.notNull(user, "Usuário não pode ser nulo");

        Assert.notNull(content, "Conteúdo não pode ser nulo");

        File file = new File();
        file.setBucket(bucket);
        file.setUuid(UUID.randomUUID().toString());

        file.setOriginalName(originalName);

        file.setUser(user);
        file.setDeleted(false);
        file.setCreatedAt(LocalDateTime.now());

        java.io.File arquivoTemp = null;
        try {
            arquivoTemp = this.createTempFile(content);

            this.s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucket)
                            .contentLength(arquivoTemp.length())
                            .key(file.getUuid())
                            .build(),
                    RequestBody.fromFile(arquivoTemp));
        } catch (IOException ex) {
            throw new StorageError("Erro no sistema de arquivos", ex);
        } finally {
            if (arquivoTemp != null && arquivoTemp.exists()) {
                if (! arquivoTemp.delete()) {
                    logger.error("Não foi possível apagar o file " + arquivoTemp.getAbsolutePath());
                }
            }
        }

        return this.fileRepository.save(file);
    }

    @Override
    public InputStream read(File file, User user) {
        Assert.notNull(file, "File cannot be null");
        Assert.notNull(user, "User cannot be null");

        FileDownload download = new FileDownload(file, user);
        fileDownloadRepository.save(download);

        return this.s3Client.getObject(GetObjectRequest.builder()
                .bucket(file.getBucket())
                .key(file.getUuid())
                .build());
    }

    @Override
    public void remove(File file) {
        DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(file.getBucket()).key(file.getUuid()).build();
        this.s3Client.deleteObject(request);
        file.setDeleted(true);
        fileRepository.save(file);
    }

    private java.io.File createTempFile(InputStream input) throws IOException {
        java.io.File arquivo = new java.io.File(System.getProperty("java.io.tmpdir") + "/tempUpload_" + UUID.randomUUID().toString());
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            int c = -1;
            byte[] buffer = new byte[65536];
            while ((c = input.read(buffer)) != -1) {
                fos.write(buffer, 0, c);
            }
        }
        return arquivo;
    }

    @Override
    public String getType() {
        return "AWS S3";
    }
}
