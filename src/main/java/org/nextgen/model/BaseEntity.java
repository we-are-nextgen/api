package org.nextgen.model;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@MappedSuperclass
public class BaseEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    // audit records
    @Column(name = "create_date")
    public LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "update_date")
    public LocalDateTime updateDate = LocalDateTime.now();
    @Column(name = "created_by")
    public String createdBy;
    @Column(name = "updated_by")
    public String updatedBy;
        
}
