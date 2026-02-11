package com.scheduleapp.baseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeleteEntity extends AuditBaseEntity {

    @Column(nullable = false)
    protected boolean deleted = false;

    public void delete() {
        this.deleted = true;
    }
}