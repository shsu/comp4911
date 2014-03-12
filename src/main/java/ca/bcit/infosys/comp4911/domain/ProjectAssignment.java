package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Min;

import java.io.Serializable;
import java.lang.Override;

@Entity
public class ProjectAssignment implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Integer id = null;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Min(0)
   private int userId;

   @Column
   private boolean isProjectManager;

   @Column
   private int projectNumber;

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

   public boolean isProjectManager()
   {
      return this.isProjectManager;
   }

   public void setProjectManager(boolean isProjectManager)
   {
      this.isProjectManager = isProjectManager;
   }

   public int getUserId()
   {
      return userId;
   }

   public void setUserId(int userId)
   {
      this.userId = userId;
   }

   public ProjectAssignment(int projectNumber, int userId, boolean isProjectManager)
   {
      this.projectNumber = projectNumber;
      this.userId = userId;
      this.isProjectManager = isProjectManager;
   }

   public ProjectAssignment()
   {
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
         return id.equals(((ProjectAssignment) that).id);
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

   public int getProjectNumber()
   {
      return this.projectNumber;
   }

   public void setProjectNumber(final int projectNumber)
   {
      this.projectNumber = projectNumber;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "userId: " + userId;
      result += ", isProjectManager: " + isProjectManager;
      result += ", projectNumber: " + projectNumber;
      return result;
   }
}