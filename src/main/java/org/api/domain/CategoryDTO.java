package org.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotNull // Do I need to put this validation here ?
    private String name;

    private Timestamp createdOn;

    private Timestamp modifiedOn;

    @Id // Do I need to put this validation here ?
    private UUID id;

    private Boolean systemDefined;

    private Boolean purchaseParkingPass;
}
