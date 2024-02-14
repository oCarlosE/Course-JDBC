package DAO;

import DB.DB;
import JDBC.DepartmentDAOJDBC;
import JDBC.SellerDAOJDBC;

public class DAOFactory {
    
    public static SellerDAO CreateSellerDAO(){
        return new SellerDAOJDBC(DB.getConnection());
    }

    public static DepartmentDAO createDepartmentDao() {
		return new DepartmentDAOJDBC(DB.getConnection());
	}
}
