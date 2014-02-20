package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

import ca.bcit.infosys.comp4911.domain.PayLevel;

import javax.persistence.ManyToOne;
import java.lang.Override;

@Entity
public class PayRate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;
    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    private int year;

    @Column
    private double rate;

    @ManyToOne
    private PayLevel payLevel;

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        if (id != null) {
            return id.equals(((PayRate) that).id);
        }
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }

    public PayLevel getPayLevel() {
        return this.payLevel;
    }

    public void setPayLevel(final PayLevel payLevel) {
        this.payLevel = payLevel;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        result += ", year: " + year;
        result += ", rate: " + rate;
        return result;
    }
}