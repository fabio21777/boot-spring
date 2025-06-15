package com.boot.file;

import com.boot.domain.BaseDomain;
import com.boot.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file", uniqueConstraints = @UniqueConstraint(columnNames = {"uuid"}))
public class File extends BaseDomain {
    @Column(name="bucket", nullable=false, length=64)
    private String bucket;

    @Column(name="original_name", nullable=false, length=255)
    private String originalName;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "deleted")
    private boolean deleted;

}
