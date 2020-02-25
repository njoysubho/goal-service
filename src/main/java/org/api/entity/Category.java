package org.api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Table(name = "Category" ,schema = "goal")
public class Category {

    @Id
    private  Integer id;

    @Column(name = "category_name")
    private String category_name;

    @Column(name = "created_by")
    private UUID created_by;

    @Column(name = "created_on")
    private Timestamp created_on;

    @Column(name = "system_defined")
    private Boolean system_defined;

    @Column(name = "modified_on")
    private  Timestamp modified_on;

    @Column(name = "tags")
    private String tags;
}
