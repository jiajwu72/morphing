package TESTE;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Teste{

	public void insereImg(JButton jb,String str){
       	Image img = null;
           try {
               img = ImageIO.read(getClass().getResource(str));
               jb.setIcon(new ImageIcon(img));
           } catch (IOException e1) {
               e1.printStackTrace();
           }
    }
	
	
		  
    public static void main(String[] args){
       	
    	   
    	   /*try{
    		   String host ="jdbc:derby://localhost:1527/Employees";
    		   String uName ="Username";
    		   String uPass = "Password";

    		   Connection con = DriverManager.getConnection(host,uName,uPass);
    	   }
    	   catch(SQLException err){
    		   System.out.println(err.getMessage());
    	   }*/
    	JFrame jf=new JFrame();
    	   jf.setVisible(true);
    	   jf.setSize(500, 600);

    	   Panneau p=new Panneau();
    	   jf.add(p);
    	   
    }
    	   
       
       
       
}       

