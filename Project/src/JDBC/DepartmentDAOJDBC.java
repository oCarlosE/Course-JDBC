package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.DepartmentDAO;
import DB.DBException;
import DB.DBIntegrityException;
import Entities.Department;

public class DepartmentDAOJDBC implements DepartmentDAO{
    private Connection Conn;

    public DepartmentDAOJDBC(Connection Conn){
        this.Conn = Conn;
    }

    @Override
    public void Insert(Department dep) {
        PreparedStatement PS = null;
		try {
			PS = Conn.prepareStatement(
				"INSERT INTO department " +
				"(Name) " +
				"VALUES " +
				"(?)", 
				Statement.RETURN_GENERATED_KEYS);

			PS.setString(1, dep.getName());

			int rowsAffected = PS.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = PS.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dep.setID(id);
				}
			}
			else {
				throw new DBException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			try {
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
		}
    }

    @Override
    public void Update(Department dep) {
        PreparedStatement PS = null;
		try {
			PS = Conn.prepareStatement(
				"UPDATE department " +
				"SET Name = ? " +
				"WHERE Id = ?");

			PS.setString(1, dep.getName());
			PS.setInt(2, dep.getID());

			PS.executeUpdate();
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
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
			PS = Conn.prepareStatement(
				"DELETE FROM department WHERE Id = ?");

			PS.setInt(1, ID);

			PS.executeUpdate();
		}
		catch (SQLException e) {
			throw new DBIntegrityException(e.getMessage());
		} 
		finally {
			try {
                PS.close();
            } catch (SQLException e) {
                throw new DBIntegrityException(e.getMessage());
            }
		}
    }

    @Override
    public Department FindByID(Integer ID) {
        PreparedStatement PS = null;
		ResultSet RS = null;
		try {
			PS = Conn.prepareStatement(
				"SELECT * FROM department WHERE Id = ?");
			PS.setInt(1, ID);
			RS = PS.executeQuery();

			if (RS.next()) {
				Department dep = new Department();
				dep.setID(RS.getInt("Id"));
				dep.setName(RS.getString("Name"));
				return dep;
			}   
			return null;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			try {
                RS.close();
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
		}
    }

    @Override
    public List<Department> FindAll() {
        PreparedStatement PS = null;
		ResultSet RS = null;
        List<Department> list = new ArrayList<>();

		try {
			PS = Conn.prepareStatement(
				"SELECT * FROM department ORDER BY Name");
            
			RS = PS.executeQuery();

			while(RS.next()) {
				Department dep = new Department();
				dep.setID(RS.getInt("Id"));
				dep.setName(RS.getString("Name"));

                list.add(dep);
				
			}
            return list;   
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			try {
                RS.close();
                PS.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
		}
    }
     
}
