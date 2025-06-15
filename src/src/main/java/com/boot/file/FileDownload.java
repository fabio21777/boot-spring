package com.boot.file;

import com.boot.domain.BaseDomain;
import com.boot.security.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file_download")
public class FileDownload extends BaseDomain {

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
