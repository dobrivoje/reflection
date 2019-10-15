package com.dobri.hibernate;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder

@Entity
@Table(name = "x_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Relation> relations = new HashSet<>();

}
