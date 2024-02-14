package DAO;

import java.util.List;

import Entities.Department;
import Entities.Seller;

public interface SellerDAO {
    void Insert(Seller sell);
    void Update(Seller sell);
    void DeleteByID(Integer ID);
    Seller FindByID(Integer ID);
    List<Seller> FindAll();
    List<Seller> FindByDepartment(Department dep);
}
