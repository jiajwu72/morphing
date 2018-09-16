
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;


public class Point  {
    private int[] coords;
    private int id;
    private Color couleur;
    private String nomPhoto;
    int x;
    int y;
    PivotGauss pivotGauss=new PivotGauss();
    double alpha;
    double beta;
    double gamma=1;
    Point pixelEnCorrespond;
    
    public Point(int x,int y){
    	
    	coords = new int[2];
    	coords[0]=x;
    	coords[1]=y;
    	couleur=Color.black;
    	this.x=x;
    	this.y=y;
    	
    	//this.addMouseListener(this);
		
    }
    
    void setPixelEnCorrespond(Point p){
    	pixelEnCorrespond=p;
    }
    
    public Point(int x,int y,String nomPhoto){
    	
    	this(x,y);
    	this.nomPhoto=nomPhoto;
    	
		this.x=x;
		this.y=y;
    		
    }
    
    public Point(int id,int x,int y){
    	this(x,y);
    	this.id=id;
		this.x=x;
	this.y=y;
    	
    }

    public Point(){
	this(0,0);
		this.x=0;
	this.y=0;
    	
    }

    public int getCoords(int i){
	return coords[i];
    }

    public int getId(){
    	return id;
    }
    
    FonctionAffine fAPartir2Pts(Point p2) {
    	
    	double a,b;
		double x1=this.getX();
		double x2=p2.getX();
		
		double y1=this.getY();
		double y2=p2.getY();
		
		
		a=(y2-y1)/(x2-x1);
		
		
		
		b=y1-a*x1;
		return new FonctionAffine(a, b);
	}
    
    void setId(int i){
    	id=i;
    }
    
    int getRgb(BufferedImage img){
		int a=img.getRGB(x, y);
		return a;
    }
    
    void idPlusUn(){
    	id++;
    }
    
    public int getX(){
	return coords[0];
    }

    public int getY(){
	return coords[1];
    }

    public void setCoords(int i,int c){
    	coords[i]=c;
    }
	
    void changeState(int x,int y){
		
    }
	
    void changeColor(Color c){
		
    }

    void idEgaleZero(){
    	id=0;
    }
    
    ArrayList<Point> nPointsLesPlusProche(int n,ArrayList<Point> points){
    	int[] ind=indiceCroissant(points);
    	ArrayList<Point> pts=new ArrayList<Point>();
    	
    	for(int i=1;i<=n;i++){
    		pts.add(points.get(ind[i]-1));
    	}
    	return pts;
    }
    
    boolean idAppartient(ArrayList<Point> points){
    	for(int i=0;i<points.size();i++){
    		if(id==points.get(i).id){
    			return true;
    		}
    	}
    	return false;
    }
    
    int[] indiceCroissant(ArrayList<Point> points){
    	
    	//copie arraylist points dans arraylist pts 
    	ArrayList<Point> pts=new ArrayList<Point>();
    	for(int i=0;i<points.size();i++){
    		pts.add(points.get(i));
    	}
    	
    	
    	
    	//trier arrayList pts selons la distance entre ce point et les points de pts
    	int j,taille;
		taille=points.size();
		while(taille>1){
			for(int i=0; i<taille-1; i++){
				double distI=distance(pts.get(i));
				double distIPlus=distance(pts.get(i+1));
		
				if(distI>distIPlus){
					Point tmp=pts.get(i);
					pts.set(i, pts.get(i+1));
					pts.set(i+1, tmp);
				}	
			}
			taille=taille-1;
		}
		
		//placer les id des points dans le tab
		int[] tab=new int[pts.size()];
		for(int i=0;i<pts.size();i++){
    		tab[i]=pts.get(i).getId();
    	}
    	
		return tab;
    }
    
    public String toString(){
    	return "("+x+","+y+")";
    }
    
    double distance(Point p){
    	double dc=(Math.pow(x-p.getX(), 2)+Math.pow(y-p.getY(), 2));
    	double d=Math.sqrt(dc);
    	return d;
    }
    
    
    boolean memeAlphaBetaGamma(Point a,Point b){
    	if(a.alpha==b.alpha &
    	   a.beta==b.beta &
    	   a.gamma==b.gamma ){
    		
    		return true;
    	}
    	
    	return false;
    }
	
	
	

	public String getNomPhoto() {
		return nomPhoto;
	}

	public void setNomPhoto(String nomPhoto) {
		this.nomPhoto = nomPhoto;
	}

	Fonction getFonction(Point p){
		Fonction f;
		if(x==p.getX()){
			f=new FonctionVertical(x);
			return f;
		}
		if(y==p.getY()){
			f=new FonctionConstant(y);
			return f;
		}else{
			double a,b;
			double x1=x;
			double x2=p.x;
			
			double y1=y;
			double y2=p.y;
			
			a=(y2-y1)/(x2-x1);
			b=y1-a*x1;
			return new FonctionAffine(a, b);
		}
		
		
	}
	
	void setAlphaBetaGamma(Point[] t){
		double a=x-t[0].x;
    	double b=x-t[1].x;
    	double c=y-t[0].y;
    	double d=y-t[1].y;
		
    	
    	FonctionAffine f=t[0].fAPartir2Pts(t[1]);
    	if((c==0&&d==0)||(a==0&&b==0)||(y==(f.ordonneeDAbscisse(x)))){
    		gamma=0;
    		
    	}else{
    		gamma=1;
    	}
    	
    	if(gamma==0){
    		return;
    	}
    	
    	double [][]system={{a, b},{c,d}};
    	double [] solution={-gamma*(x-t[2].x),-gamma*(y-t[2].y)};
    	
    	double []pp=pivotGauss.resolutionGauss(system, solution);
    	alpha=pp[0];
    	beta=pp[1];
    	//System.out.println("gamma:"+gamma);
    	
    }
	public static void main(String [] args){
		
		/*Point p1=new Point(0, 0);
		Point p2=new Point(275, 0);
		Point p3=new Point(146, 56);
		Point p4=new Point(0, 0);
		//Point p5=new Point(5, 2);
		
		Point[] triangle={p1,p2,p3};
		p4.setAlphaBetaGamma(triangle);
		//p4.definirAlphaBetaGamma(triangle);
		System.out.println("alpha:"+p4.alpha+"\nbeta:"+p4.beta+"\ngamma:"+p4.gamma);*/
		
		
		/*ArrayList<Point> points=new ArrayList<Point>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		
		ArrayList<Point> pts=p1.nPointsLesPlusProche(4,points);
		for(int i=0;i<pts.size();i++){
			System.out.println(pts.get(i).getId());
		}*/
		
		Point p1=new Point(0,0);
		Point p2=new Point(2,0);
		Point p3=new Point(0,2);
		Point p4=new Point(1,1);
		
		Point[] t={p1,p2,p3};
		p4.setAlphaBetaGamma(t);
		
		//System.out.println(((FonctionVertical) f).ptOrd(p3.));
	}
}
