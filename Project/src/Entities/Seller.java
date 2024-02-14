package Entities;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable{
    private static final long serialVersionUID= 1L;
    private Integer ID;
    private String Name;
    private String Email;
    private Date Birthday;
    private Double BaseSalary;
    private Department Departement;

    public Seller(Integer iD, String name, String email, Date birthday, Double baseSalary, Entities.Department departement) {
        this.ID = iD;
        this.Name = name;
        this.Email = email;
        this.Birthday = birthday;
        this.BaseSalary = baseSalary;
        this.Departement = departement;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer iD) {
        ID = iD;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public Double getBaseSalary() {
        return BaseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        BaseSalary = baseSalary;
    }

    public Department getDepartement() {
        return Departement;
    }

    public void setDepartement(Department departement) {
        Departement = departement;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ID == null) ? 0 : ID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Seller other = (Seller) obj;
        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Seller | ID: "+ID+" | Name: "+Name+" | Email: "+Email+" | Birthday: "+Birthday+" | BaseSalary: "+BaseSalary+" | Departement:"+Departement.getName();
    }
}
