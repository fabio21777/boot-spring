package com.boot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class BaseDomain implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @Column(name="id")
    private long id;

    @Column(name="uuid", length=64, unique=true, nullable=false, updatable=false)
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}