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
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer designationId;

    @Column(name = "designation_name")
    private String designationName;
}
