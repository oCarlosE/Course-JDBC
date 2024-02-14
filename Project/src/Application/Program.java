package Application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import DAO.DAOFactory;
import DAO.SellerDAO;
import Entities.Department;
import Entities.Seller;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        SellerDAO sellDAO = DAOFactory.CreateSellerDAO();

        System.out.println("=== TEST 01: SELLER BY ID ===");
        Seller seller = sellDAO.FindByID(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 02: SELLER BY DEPARTMENT ===");
        Department dep = new Department(2, null);
        List<Seller> list = sellDAO.FindByDepartment(dep);
        for (Seller seller2 : list) {
            System.out.println(seller2);
        }

        System.out.println("\n=== TEST 03: SELLER FIND ALL ===");
        List<Seller> list2 = sellDAO.FindAll();
        for (Seller seller2 : list2) {
            System.out.println(seller2);
        }

        System.out.println("\n=== TEST 04: SELLER INSERT ===");
        Seller newSeller = new Seller(null, "Greg Taylor", "Greg@gmail.com", new Date(), 2500.00, dep);
        sellDAO.Insert(newSeller);
        System.out.println("Inserte! \n Seller ID: "+newSeller.getID());

        System.out.println("\n=== TEST 05: SELLER UPDATE ===");
        seller = sellDAO.FindByID(1);
        seller.setName("Martha Wayne");
        sellDAO.Update(seller);
        System.out.println("Update Completed: \n"+seller);

        System.out.println("\n=== TEST 05: SELLER DELETE ===");
        System.out.println("Enter an ID to Delete the Seller: ");
        int usr = sc.nextInt();
        sellDAO.DeleteByID(usr);
        System.out.println("Seller Deleted");
        sc.close();
    }
}
