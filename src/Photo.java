




import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Photo extends ImageIcon{
    ArrayList<Point> points=new ArrayList<>();
    LinkedList<Point[]> triangles;
    ConnecteurBD connecteur=new ConnecteurBD();
    String [] dejaMorphing=new String[10000];
    BufferedImage img;
    private Ecran ecran;
    
    public Ecran getEcran() {
		return ecran;
	}

    void setEcran(Ecran e){
    	ecran=e;
    }
    
	Delaunay delaunay;
	private int idMax;
	static int width,height;
	private String nom;
	
	
    Photo(){
    	super();
    	
    }
    Photo(String str){
    	super(str);
    	
    	
    	nom=str;
    	
    	Point p1=new Point(0, 0,nom);
		Point p2=new Point(getBufferedImage().getWidth()-1,0,nom);
		Point p3=new Point(0,getBufferedImage().getHeight()-1,nom);
		Point p4=new Point(getBufferedImage().getWidth()-1,getBufferedImage().getHeight()-1,nom);
		
		
		this.addPoint(p1);
		this.addPoint(p2);
		this.addPoint(p3);
		this.addPoint(p4);
		
		connecteur.savePoint(1, p1.getX(), p1.getY(), p1.getNomPhoto());
		connecteur.savePoint(2, p2.getX(), p2.getY(), p2.getNomPhoto());
		connecteur.savePoint(3, p3.getX(), p3.getY(), p3.getNomPhoto());
		connecteur.savePoint(4, p4.getX(), p4.getY(), p4.getNomPhoto());
		
		
    	
    	
		
		if(connecteur.photoExiste(nom)){
			points=connecteur.loadAllPoints(nom);
		}
		
		
		
		delaunay=new Delaunay();
		delaunay.setBoundingBox(0,0,400,400);
		for(int i=0;i<points.size();i++){
			Point p=points.get(i);
			delaunay.insertPoint(p);
				
		}
		
		triangles=(LinkedList<Point[]>) delaunay.computeTriangles();
		
		
    }

    
    
    /*int minAbscisseParmi3Pts(Point[] tri){
    	int min=tri[0].x;
    	for(int i=0;i<tri.length;i++){
    		if(min>tri[i].x){
    			min=tri[i].x;
    		}
    	}
    	return min;
    }
    
    int minOrdonneeParmi3Pts(Point[] tri){
    	int min=tri[0].y;
    	for(int i=0;i<tri.length;i++){
    		if(min>tri[i].y){
    			min=tri[i].y;
    		}
    	}
    	return min;
    }
    
    int maxAbscisseParmi3Pts(Point[] tri){
    	int max=tri[0].x;
    	for(int i=0;i<tri.length;i++){
    		if(max<tri[i].x){
    			max=tri[i].x;
    		}
    	}
    	return max;
    }
    
    int maxOrdonneeParmi3Pts(Point[] tri){
    	int max=tri[0].y;
    	for(int i=0;i<tri.length;i++){
    		if(max<tri[i].y){
    			max=tri[i].y;
    		}
    	}
    	return max;
    }
    
    void setXYPoints(){
    	
    	for(int i=0;i<triangles.size();i++){
    		Point[] tri=triangles.get(i);
    		
    		int xMin=minAbscisseParmi3Pts(tri);
    		int xMax=maxAbscisseParmi3Pts(tri);
    		
    		int yMin=minOrdonneeParmi3Pts(tri);
    		int yMax=maxOrdonneeParmi3Pts(tri);
    		
    		int a=xMax-xMin+1;
    		int b=yMax-yMin+1;
    		
    		int[] xPts=new int[a];
    		int[] yPts=new int[b];
    		
    		for(int j=0;j<a;j++){
    			xPts[j]=xMin;
    			xMin++;
    		}
    		
    		for(int k=0;k<b;k++){
    			yPts[k]=yMin;
    			yMin++;
    		}
    				
    	}
    }*/
    
    public void addPoint(Point p){
    	
    	idMax=points.size()+1;
    	points.add(p);
    	p.setId(idMax);
    	
    	connecteur.savePoint(p.getId(), p.getX(), p.getY(), this.nom);
    	
    	
    	
    }

    
    
    
    
    public int numPoints(){
    	return points.size();
    }

    void idMaxEgaleZero(){
    	idMax=0;
    }
	
    public BufferedImage getBufferedImage(){
    	img=null;
    	try {
			img = ImageIO.read(getClass().getResource("/"+this.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return img;
    }
    
    public void setBufferedImage(BufferedImage img){
    	this.img=img;
    }
    
    void supprimerPoint(int id){
    	
    	points.remove(id);
	connecteur.removePoint(points.get(id).getId(), this.nom);
    }
	
	

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getIdMax(){
		return idMax;
	}
	
	void setIdMax(int i){
		idMax=i;
	}
	
	void clear(){
		ArrayList<Point> a=new ArrayList<Point>();
		for(int i=4;i<points.size();i++){
			a.add(points.get(i));
		}
		points.removeAll(a);
		delaunay=new Delaunay();
		for(int i=0;i<points.size();i++){
			Point p=points.get(i);
			delaunay.insertPoint(p);
			
		}
		idMax=points.size();
		connecteur.removeAllPoints(this.nom);
	}
	
	LinkedList<Triangle> triangleIncompatible(LinkedList<Triangle> triangles){
		
		LinkedList<Triangle> trs=new LinkedList<Triangle>();
		for(int i=0;i<triangles.size();i++){
			if(triangles.get(i).cercleCir.existe4emePt(points)){
				trs.add(triangles.get(i));
			}
		}
		return trs;
	}
	
	/*Triangle triangleExiste(Point p1,Point p2,Point p3){
		for(int i=0;i<triangles.size();i++){
			Point tp1=triangles.get(i).p1;
			Point tp2=triangles.get(i).p2;
			Point tp3=triangles.get(i).p3;
			if(tp1==p1 && tp2==p2 && tp3==p3){
				return triangles.get(i);
			}
		}
		return null;
	}*/
	
	
	
	public ArrayList<Point> getPoints(){
		return points;
	}
	public void setPoints(ArrayList<Point> pts){
		points=pts;
	}
	
	void afficheArrayListString(ArrayList<String> s){
		for(int i=0;i<s.size();i++){
			System.out.print(s.get(i)+", ");
		}
	}
	
	
	boolean dejaMorphingAvec(String str){
    	ArrayList<String> noms=connecteur.loadDejasMorph(this);
    	
    	for(int i=0;i<noms.size();i++){
    		//System.out.println(noms.get(i)+","+str);
    		if(noms.get(i).equals(str)){
    			return true;
    		}
    	}
    	return false;
    }
	
	public static void main(String[] args) {
		/*Photo p=new Photo("singe.jpg");
		Photo p2=new Photo("ours.jpg");
		ConnecteurBD c=new ConnecteurBD();
		
		ArrayList<String> str=c.loadDejasMorph(p);
		boolean ok=p.dejaMorphingAvec(p2.getNom());
		p.afficheArrayListString(str);
		System.out.println(ok);*/
		/*System.out.println("gg");
		Photo photo=new Photo("ours.jpg");
		Image img=photo.getImage();
		System.out.println(img.getWidth(null));*/
		Photo photo=new Photo("ours.jpg");
		BufferedImage img=photo.getBufferedImage();
		System.out.println(img.getRGB(0, 0));
		
	}
}
