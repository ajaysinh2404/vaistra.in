package com.vaistra.master.entity.mines_master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entityTypeId;

    @Column(name = "entity_type_name")
    private String entityTypeName;

    @Column(name = "short_name")
    private String shortName;
}
