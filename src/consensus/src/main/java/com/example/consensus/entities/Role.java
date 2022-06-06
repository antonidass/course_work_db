package com.example.consensus.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(generator = "user_id")
    @GenericGenerator(
            name = "user_id",
            strategy = "com.example.consensus.generators.RoleIdGenerator"
    )
    private Long id;

    @Column(name = "name")
    private String name;
}
