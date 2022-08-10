package com.genomic.Fibersmart.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "group_licences")
public class GroupLicence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "activated_date")
    private Date activatedDate;

    @Column(name = "expired_date")
    private Date expiredDate;

    //a voir
    private Integer numberOfDaysBeforeExpiration;

    public GroupLicence() {
    }

    public GroupLicence(final Long id, final Date activatedDate, final Date expiredDate) {
        this.id = id;
        this.activatedDate = activatedDate;
        this.expiredDate = expiredDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(final Date activatedDate) {
        this.activatedDate = activatedDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(final Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
