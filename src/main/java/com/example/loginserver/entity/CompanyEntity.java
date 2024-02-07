package com.example.loginserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "company",schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    @Basic(optional = false)
    @Column(name = "mail")
    private String mail;
    @Basic(optional = false)
    @Column(name = "comapny_name")
    private String comapny_name;
    @Basic(optional = false)
    @Column(name = "secret_key")
    private String secret_key;


}
