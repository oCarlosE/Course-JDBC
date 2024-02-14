package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.SellerDAO;
import DB.DBException;
import Entities.Department;
import Entities.Seller;

public class SellerDAOJDBC implements SellerDAO{
    private Connection Conn;

    public SellerDAOJDBC(Connection Conn){
        this.Conn = Conn;
    }

    @Override
    public void Insert(Seller sell) {
        PreparedStatement PS = null;

        try 
        {
            PS = Conn.prepareStatement(
                "INSERT INTO seller "+
                "(Name, Email, BirthDate, BaseSalary, DepartmentId) "+
                "VALUES "+
                "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            PS.setString(1, sell.getName());
            PS.setString(2, sell.getEmail());
            PS.setDate(3, new java.sql.Date(sell.getBirthday().getTime()));
            PS.setDouble(4, sell.getBaseSalary());
            PS.setInt(5, sell.getDepartement().getID());

            int rowsAffected = PS.executeUpdate();

            if (rowsAffected>0) {
                ResultSet RS = PS.getGeneratedKeys();
                if (RS.next()) {
                    int id = RS.getInt(1);
                    sell.setID(id);
                }
                RS.close();
            }
            else{
                throw new DBException("Unexpected Error, No Rows Affected");
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally{
            try {
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    @Override
    public void Update(Seller sell) {
        PreparedStatement PS = null;

        try 
        {
            PS = Conn.prepareStatement(
                "UPDATE seller "+
                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary  = ?, DepartmentId = ? "+
                "WHERE Id = ? ");
            
            PS.setString(1, sell.getName());
            PS.setString(2, sell.getEmail());
            PS.setDate(3, new java.sql.Date(sell.getBirthday().getTime()));
            PS.setDouble(4, sell.getBaseSalary());
            PS.setInt(5, sell.getDepartement().getID());
            PS.setInt(6, sell.getID());

            PS.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally{
            try {
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    @Override
    public void DeleteByID(Integer ID) {
        PreparedStatement PS = null;

        try {
            PS = Conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            PS.setInt(1, ID);

            PS.executeUpdate();
        } 
        catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally{
            try {
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    @Override
    public Seller FindByID(Integer ID) {
        PreparedStatement PS = null;
        ResultSet RS = null;

        try {
            PS = Conn.prepareStatement(
            "SELECT seller.*, department.Name as DepName "+
            "FROM seller INNER JOIN department "+
            "ON seller.DepartmentId = department.Id "+
            "WHERE seller.Id = ?");
            
            PS.setInt(1, ID);
            RS = PS.executeQuery();

            if (RS.next()) {
                Department dep = InstanciateDepartment(RS);
                Seller sell = InstanciateSeller(RS, dep);
                return sell;
            }
            return null;
        } 
        catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            try {
                RS.close();
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }            
        }
    }

    private Seller InstanciateSeller(ResultSet RS, Department dep) throws SQLException{
        Seller sell = new Seller(RS.getInt("Id"), RS.getString("Name"), RS.getString("Email"), 
        RS.getDate("BirthDate"), RS.getDouble("BaseSalary"), dep);

        return sell;
    }

    private Department InstanciateDepartment(ResultSet RS) throws SQLException{
        Department dep = new Department(RS.getInt("DepartmentId"), RS.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> FindAll() {
        PreparedStatement PS = null;
        ResultSet RS = null;

        try {
            PS = Conn.prepareStatement(
            "SELECT seller.*,department.Name as DepName "+
            "FROM seller INNER JOIN department "+
            "ON seller.DepartmentId = department.Id "+
            "ORDER BY Name "
            );
            
            RS = PS.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(RS.next()) {
                Department department = map.get(RS.getInt("DepartmentId"));

                if (department == null) {
                    department = InstanciateDepartment(RS);
                    map.put(RS.getInt("DepartmentId"), department);
                }
                
                Seller sell = InstanciateSeller(RS, department);    
                sellers.add(sell);
            }
            return sellers;
        } 
        catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            try {
                RS.close();
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }            
        }
    }

    @Override
    public List<Seller> FindByDepartment(Department dep) {
        PreparedStatement PS = null;
        ResultSet RS = null;

        try {
            PS = Conn.prepareStatement(
            "SELECT seller.*,department.Name as DepName "+
            "FROM seller INNER JOIN department "+
            "ON seller.DepartmentId = department.Id "+
            "WHERE DepartmentId = ? "+
            "ORDER BY Name "
            );
            
            PS.setInt(1, dep.getID());
            RS = PS.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(RS.next()) {
                Department department = map.get(RS.getInt("DepartmentId"));

                if (department == null) {
                    department = InstanciateDepartment(RS);
                    map.put(RS.getInt("DepartmentId"), department);
                }
                
                Seller sell = InstanciateSeller(RS, department);    
                sellers.add(sell);
            }
            return sellers;
        } 
        catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            try {
                RS.close();
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }            
        }
    }   
}
