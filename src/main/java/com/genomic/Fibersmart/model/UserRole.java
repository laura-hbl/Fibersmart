package com.genomic.Fibersmart.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="role_permission",
            joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="permission_id", referencedColumnName="id")})
    private List<Permission> permissions;


}
