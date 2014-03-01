package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;
import ca.bcit.infosys.comp4911.domain.PayRate.PayLevel;

@Entity
public class Effort implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;
    @Version
    @Column(name = "version")
    private int version = 0;

    @Enumerated(EnumType.STRING)
    private PayLevel payLevel;

    /** Tenths of hours */
    @Column
    private int personDays;

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
            return id.equals(((Effort) that).id);
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

    public PayLevel getPayLevel() {
        return this.payLevel;
    }

    public void setPayLevel(final PayLevel payLevel) {
        this.payLevel = payLevel;
    }

    public int getPersonDays() {
        return this.personDays;
    }

    public void setPersonDays(final int personDays) {
        this.personDays = personDays;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Effort{" +
                "id=" + id +
                ", version=" + version +
                ", payLevel=" + payLevel +
                ", personDays=" + personDays +
                '}';
    }
}