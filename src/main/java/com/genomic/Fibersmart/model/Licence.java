package com.genomic.Fibersmart.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "licences")
public class Licence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "days_before_expiration")
    private Integer numberOfDaysBeforeExpiration;

    public Licence() {
    }

    public Licence(final Long id, final Date expirationDate, final Integer numberOfDaysBeforeExpiration) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.numberOfDaysBeforeExpiration = numberOfDaysBeforeExpiration;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getNumberOfDaysBeforeExpiration() {
        return numberOfDaysBeforeExpiration;
    }

    public void setNumberOfDaysBeforeExpiration(final Integer numberOfDaysBeforeExpiration) {
        this.numberOfDaysBeforeExpiration = numberOfDaysBeforeExpiration;
    }
}
