package com.weekend.mango.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;

    @Column(length=20)
    private String name;


}
