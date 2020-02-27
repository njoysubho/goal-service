package org.api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private  UUID id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_on")
    @CreatedDate
    private Timestamp createdOn;

    @Column(name = "system_defined")
    private Boolean systemDefined;

    @LastModifiedDate
    @Column(name = "modified_on")
    private  Timestamp modifiedOn;

    @Column(name = "tags")
    private String tags;
}
