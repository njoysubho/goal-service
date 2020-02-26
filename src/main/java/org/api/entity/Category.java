package org.api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category" ,schema = "goal")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  UUID id;

    @Column(name = "category_name")
    private String category_Name;

    @Column(name = "created_by")
    private UUID created_By;

    @Column(name = "created_on")
    @CreatedDate
    private Timestamp created_On;

    @Column(name = "system_defined")
    private Boolean system_Defined;

    @LastModifiedDate
    @Column(name = "modified_on")
    private  Timestamp modified_On;

    @Column(name = "tags")
    private String tags;
}
