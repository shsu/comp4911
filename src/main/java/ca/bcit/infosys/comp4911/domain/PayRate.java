package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

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
    private String payLevelName;

    @Column
    private int year;

    @Column
    private double rate;

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

    public String getPayLevelName() {
        return this.payLevelName;
    }

    public void setPayLevelName(final String payLevelName) {
        this.payLevelName = payLevelName;
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

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (payLevelName != null && !payLevelName.trim().isEmpty())
            result += "payLevelName: " + payLevelName;
        result += ", year: " + year;
        result += ", rate: " + rate;
        return result;
    }
}