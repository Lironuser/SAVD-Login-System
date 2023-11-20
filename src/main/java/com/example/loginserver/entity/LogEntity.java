package com.example.loginserver.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "log",schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LogEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "company_id")
    private long company_id;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @Column(name = "success")
    private boolean success;

    @Basic(optional = false)
    @Column(name = "log")
    private String log;
    @Basic(optional = false)
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public boolean getSuccess() {
        return success;
    }
}
