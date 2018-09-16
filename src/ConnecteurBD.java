

import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public class ConnecteurBD {

	public Statement stm;
	public ResultSet rs;
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/morphing3";
	private String username = "morphing3";
	private String password = "morphing3";
	public Connection conn;

	public ConnecteurBD() {
		this.connection();
	}

	public void connection() {
		try {
			System.setProperty("jdbc.Drivers", driver);
			conn = DriverManager.getConnection(url, username, password);
			//JOptionPane.showMessageDialog(null, "Successfully connected");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Connection Error\n Error:" + e.getMessage());
		}
	}

	public void disconnect() {
		try {
			conn.close();
			JOptionPane.showMessageDialog(null, "Connection close");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error to close the connection\n Error:" + e.getMessage());
		}
	}

	

	void savePoint(int i, int xx, int yy, String nomP) {
		try {
			
			PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement("insert into point (id,x,y,nomPhoto) values (?,?,?,?)");
			pst.setInt(1, i);
			pst.setInt(2, xx);
			pst.setInt(3, yy);
			pst.setString(4, nomP);
			pst.executeUpdate();
			pst.close();
			
			//System.out.println("save point!!");
		} catch (SQLException e) {
			//System.out.println("err savePoint");;
		}
	}

	void saveMorph(String photoDep,String photoArr) {
		try {
			
			PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement("insert into dejamorph (photoDepart,photoArrivee) values (?,?)");
			
			pst.setString(1, photoDep);
			pst.setString(2, photoArr);
			pst.executeUpdate();
			pst.close();
			
			//System.out.println("save morph!!");
		} catch (SQLException e) {
			//System.out.println("err saveMorph");;
		}
	}
	
	void effacherTousMorph(){
		PreparedStatement pst;
		try {
			this.connection();
			pst = (PreparedStatement) conn.prepareStatement("delete from dejamorph");
					
					pst.execute();
					pst.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			e.printStackTrace();
			//System.out.println("removeMorph erreur");
		}
		
	}
	
	// la liste des photos dejas morphing avec la photo donnee
	ArrayList<String> loadDejasMorph(Photo photoDep) {
		
		ArrayList<String> photos=new ArrayList<String>();
		String nom=photoDep.getNom();
		String sql="select * from dejamorph where photoDepart='"+nom+"'";
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while (rs.next()){
				
				String nomPhoto=rs.getString("photoArrivee");
				
				photos.add(nomPhoto);
			}
			stmt.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return photos;
	}
	
	
	void removePoint(int i,String nomP){
		
		PreparedStatement pst;
		try {
			this.connection();
			pst = (PreparedStatement) conn.prepareStatement("delete from point where id=?"
					+ " and nomPhoto=?");
					pst.setInt(1, i);
					pst.setString(2, nomP);
					pst.execute();
					pst.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.out.println("removePoint erreur");
		}
		
		
	}
	
	void removeAllPoints(String nomP){
		PreparedStatement pst;
		try {
			pst = (PreparedStatement) conn.prepareStatement("delete from point where id>4"
					+ " and nomPhoto=?");
					pst.setString(1, nomP);
					pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println("removeAllPoints erreur");
		}
		
		
	}
	
	ArrayList<Point> loadAllPoints(String nomP) {
		ArrayList<Point> points=new ArrayList<Point>();
		String sql="select * from point where nomPhoto='"+nomP+"'";
		
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while (rs.next()){
				int id=rs.getInt("id");
				int x=rs.getInt("x");
				int y=rs.getInt("y");
				Point p =new Point(id,x,y);
				points.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return points;
	}
	
	void savePhoto(String nomP,int i) {
		
		try {
			/*File photo = new File("C:/morphing3/src/"+nomP);
			FileInputStream inputStream = new FileInputStream(photo);*/
			PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement("insert into photo (nom,idMax,image) values (?,?)");
			pst.setString(1, nomP);
			pst.setInt(2, i);
			//pst.setBinaryStream(3,inputStream, (int) photo.length());
			
			pst.executeUpdate();
			//System.out.println("save photo!!");
			pst.close();
			
		} catch (SQLException e) {
			System.out.println();
		}
	}

	void removePhoto(String nomP){
		
		PreparedStatement pst;
		try {
			pst = (PreparedStatement) conn.prepareStatement("delete frrom photo where nom=?"
					);

					pst.setString(1, nomP);
					pst.execute();
					pst.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//on parcours notre bd pour verifier si cette photo est dejas existe dans notre bd
	boolean photoExiste(String nomP){
		
		String sql="select * from photo where nom='"+nomP+"'";
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			 if(rs.next()){
				 return true;
			 }else{
				 return false;
			 }
			 
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	ArrayList<Photo> loadPhoto() {
		
		ArrayList<Photo> gallery=new ArrayList<Photo>();
		String sql="select * from photo";
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while (rs.next()){
				
				String nomPhoto=rs.getString("nom");
				Photo p=new Photo(nomPhoto);
				gallery.add(p);
			}
			stmt.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return gallery;
	}
	
	void updateIdMaxPhoto(int i,String nomP){
		
		String sql="update photo set idMax='"+i+"' where nom='"+nomP+"'";
		PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        //System.out.println("resutl: " + i);
	        pstmt.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	}
	 
	void afficheArrayListString(ArrayList<String> s){
		for(int i=0;i<s.size();i++){
			System.out.print(s.get(i)+", ");
		}
	}
	
	public static void main(String[] args) {
		ConnecteurBD conn = new ConnecteurBD();
		
		
		conn.effacherTousMorph();
		
		 
	}
}
