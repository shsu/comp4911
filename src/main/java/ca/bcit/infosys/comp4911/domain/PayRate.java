package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Min;

import java.io.Serializable;
import java.math.BigDecimal;

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
    @Min(0)
    private int year;

    @Column
    private BigDecimal rate;

    @Enumerated(EnumType.STRING)
    private PLevel pLevel;

    public PayRate(int year, BigDecimal rate, PLevel pLevel) {
		super();
		this.year = year;
		this.rate = rate;
		this.pLevel = pLevel;
	}
    
    public PayRate(){
    	
    }

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public PLevel getpLevel() {
        return pLevel;
    }

    public void setpLevel(PLevel pLevel) {
        this.pLevel = pLevel;
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