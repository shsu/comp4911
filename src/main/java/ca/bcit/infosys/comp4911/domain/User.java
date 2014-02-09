package ca.bcit.infosys.comp4911.domain;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class User implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Integer id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String username;

   @Column
   private String password;

   @Column
   private String firstName;

   @Column
   private String lastName;

   @ManyToOne
   private UserRoleGroup userRoleGroup;

   @ManyToOne
   private PayLevel payLevel;

   @Temporal(TemporalType.DATE)
   private Date startDate;

   public Integer getId()
   {
      return this.id;
   }

   public void setId(final Integer id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((User) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getUsername()
   {
      return this.username;
   }

   public void setUsername(final String username)
   {
      this.username = username;
   }

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public String getFirstName()
   {
      return this.firstName;
   }

   public void setFirstName(final String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return this.lastName;
   }

   public void setLastName(final String lastName)
   {
      this.lastName = lastName;
   }

   public User()
   {
   }

   public User(String username, String password, String firstName, String lastName)
   {
      this.username = username;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public UserRoleGroup getUserRoleGroup()
   {
      return this.userRoleGroup;
   }

   public void setUserRoleGroup(final UserRoleGroup UserRoleGroup)
   {
      this.userRoleGroup = UserRoleGroup;
   }

   public PayLevel getPayLevel()
   {
      return this.payLevel;
   }

   public void setPayLevel(final PayLevel PayLevel)
   {
      this.payLevel = PayLevel;
   }

   public Date getStartDate()
   {
      return this.startDate;
   }

   public void setStartDate(final Date startDate)
   {
      this.startDate = startDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (username != null && !username.trim().isEmpty())
         result += "username: " + username;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (firstName != null && !firstName.trim().isEmpty())
         result += ", firstName: " + firstName;
      if (lastName != null && !lastName.trim().isEmpty())
         result += ", lastName: " + lastName;
      return result;
   }
}