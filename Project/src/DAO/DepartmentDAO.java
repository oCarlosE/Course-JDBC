package DAO;

import java.util.List;

import Entities.Department;

public interface DepartmentDAO {
    
    void Insert(Department dep);
    void Update(Department dep);
    void DeleteByID(Integer ID);
    Department FindByID(Integer ID);
    List<Department> FindAll();
}
