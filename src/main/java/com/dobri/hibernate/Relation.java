package com.dobri.hibernate;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "user"})
@Builder

@Entity
public class Relation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datum")
    private Date date;
}
