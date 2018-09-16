



import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



import java.awt.event.*;


public class Ecran extends JPanel implements MouseListener,MouseMotionListener{
    private Photo photo;
    private boolean terminer;
    boolean touche;
    
	private static int ordre=3;
	private Fenetre fenetre;
	
	private ConnecteurBD conn=new ConnecteurBD();
    
    Ecran (Fenetre f) {
    	super();
    	fenetre=f;
    	
    	this.addMouseListener(this);
    	
    	
    }
	

    public Photo getPhoto() {
    	return photo;
    }

    public void setPhoto(Photo photo) { 
    	this.photo = photo;
    	//photo.setEcran(this);
    	conn.savePhoto(photo.getNom(), photo.getIdMax());
		
    	repaint();
    }

    void setOrdre(int o){
    	ordre=o;
    }
    
    boolean getTerminer(){
    	return terminer;
    }
    
    void setTerminer(Boolean t){
    	terminer=t;
    }
    
    public void paint(Graphics g){
    	super.paint(g);
    	g.setColor(Color.WHITE);
		g.fillRect(0, 0, 600, 400);
    	BufferedImage img;
    	if(photo!=null){
    		try {
    			
    			img = ImageIO.read(getClass().getResource("/"+photo.toString()));
    			int width=(int)(photo.getBufferedImage().getWidth());
    			int height=(int)(photo.getBufferedImage().getHeight());
    			g.drawImage(img, 0, 0,width , height, this);
    			photo.setBufferedImage(img);
    			//drawImage	
    			
    			ArrayList<Point> points=photo.points;
    			int dernierIndice=points.size()-1;
    			
    			for(int i=0;i<points.size();i++){
    				
    				g.setColor(Color.blue);
    				if(terminer){
    					g.setColor(Color.GRAY);
    					
    					if(photo.points.get(i).getId()==ordre ){
    						g.setColor(Color.GREEN);
    					}
    					
    				}
    				else if(i==dernierIndice){
    					
    					g.setColor(Color.GREEN);
    				}
    				drawPoint(g, points.get(i));
    				
    				
    				
    			}
    			//g.setColor(Color.WHITE);
    			if(this==fenetre.getEcranG()){
    				if(photo.numPoints()>2){
			    
    					List<Point[]> triangle=getPhoto().delaunay.computeTriangles();
    					for(Point[] t:triangle){
    						drawTriangle(g, t[0], t[1], t[2]);
    						
    					}
    				}
    			}else{
    				if (photo.numPoints()>2){
			    	
    					List<Point[]> triangle=getPhoto().delaunay.copieTriangle(fenetre.getEcranG().getPhoto().triangles, points);
    					
    					System.out.println();
    					for(Point[] t:triangle){
    						drawTriangle(g, t[0], t[1], t[2]);
    						
    					}
    				}

			    
    			}
    			
    		} catch (IOException e) {
		    e.printStackTrace();
    		}  
    		
    	}
	
    }
	
    public void removePhoto(){
    	this.photo=null;
    }
    
    public void drawPoint(Graphics g,Point p){
    	int x=(int)p.getCoords(0);
    	int y=(int)p.getCoords(1);
    	
        
		g.fillOval(x-5,y-5,10,10);
		
        
    }

    public void drawLine(Graphics g,Point p1,Point p2){
    	Color prevCol = g.getColor();
    	g.setColor(Color.pink);
        g.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        g.setColor(prevCol);
        
    }

    public void drawTriangle(Graphics g,Point p1,Point p2,Point p3){
    	Color prevCol = g.getColor();
    	g.setColor(Color.pink);
        drawLine(g, p1, p2);
        drawLine(g, p1, p3);
        drawLine(g, p2, p3);
        g.setColor(prevCol);
    }

    public void mouseClicked(MouseEvent e) {
    	
    	int x=e.getX();
		int y=e.getY();
		Point p=new Point(x,y);
    	if (e.getButton() == MouseEvent.BUTTON1 && terminer==false && photo!=null){
    		
    		p.setNomPhoto(photo.getNom());
			
    		Graphics g=this.getGraphics();
    		g.setColor(Color.GREEN);
		
    		drawPoint(g,p);
    		ordre++;
    		photo.addPoint(p);
    		if(this==fenetre.getEcranG()){
					
    			
    			getPhoto().delaunay.insertPoint(p);
    			getPhoto().triangles=(LinkedList<Point[]>) getPhoto().delaunay.computeTriangles();
			    //System.out.println(x+" "+y);
			    //System.out.println("id:"+p.getId());
    		}

			    	   
    		if(this==fenetre.getEcranD() ){
    			
				LinkedList<Point[]> trianglesG=fenetre.getEcranG().getPhoto().triangles;
    			getPhoto().triangles=new LinkedList<Point[]>();
    			
			   	getPhoto().triangles=getPhoto().delaunay.copieTriangle(fenetre.getEcranG().getPhoto().triangles, getPhoto().points);
				afficherTriangles(getPhoto().triangles);
			    
    			
    			//photo.points=new ArrayList<Point>();
    			if(photo.points.size()<=fenetre.getEcranG().photo.points.size()){
    				fenetre.getEcranG().repaint();
				    
    				/*ArrayList<Point> points=fenetre.getEcranG().photo.points;
				    			//System.out.println("points.size:"+points.size());
				    			//System.out.println(p.getId());
				    			Point p2=points.get(p.getId()-1);
				    			int[][] coordsCorrespondants={{p2.x,p2.y},{p.x,p.y}};
				    	
				    			fenetre.ecranFinal.points_interm.add(coordsCorrespondants);
				    			fenetre.ecranFinal.affichePoint_interm();*/
    			}
				    	
				    
    			
    		}
    	}else if (e.getButton() == MouseEvent.BUTTON3) {  
    		terminer=true;
    		touche=true;
    		ordre=5;
    			
    			
    		
    		
    		//sauvegarder les points dans notre bd
    		//System.out.println("size"+photo.getPoints().size());
    		for(int i=0;i<photo.getPoints().size();i++){
    			int id=photo.getPoints().get(i).getId();
    			int xx=photo.getPoints().get(i).getX();
    			int yy=photo.getPoints().get(i).getY();
    			String nomPhoto=photo.getPoints().get(i).getNomPhoto();
    		
    			conn.savePoint(id, xx, yy, nomPhoto);
    		}
    		conn.updateIdMaxPhoto(photo.getPoints().size(), photo.getNom());
    	}
    	this.repaint();
    }
    
    void afficherTriangle(Point[] tri) {
		for (int i = 0; i < tri.length; i++) {
			System.out.print(tri[i] + ",");
		}
		System.out.println();
	}

    void afficherTriangles(LinkedList<Point[]> trs){
    	for(int i=0;i<trs.size();i++){
    		afficherTriangle(trs.get(i));
    	}
    }
    
    public void mousePressed(MouseEvent e) {
    	
    }

    public void mouseEntered(MouseEvent e) {}
  
    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    public void mouseReleased(MouseEvent e){}
}
