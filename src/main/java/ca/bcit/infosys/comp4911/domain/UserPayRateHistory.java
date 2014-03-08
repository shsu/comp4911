package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

public class UserPayRateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private PLevel pLevel;

    @Column
    private int userId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserPayRateHistory that = (UserPayRateHistory) o;

        if (!id.equals(that.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PLevel getpLevel() {
        return pLevel;
    }

    public void setpLevel(PLevel pLevel) {
        this.pLevel = pLevel;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        return result;
    }
}
