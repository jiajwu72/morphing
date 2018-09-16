



public class Triangle {
	Point p1,p2,p3;
	Cercle cercleCir; //cercle circonscrit
	
	
	public Triangle(Point p1,Point p2,Point p3){
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
		
		cercleCir=formerCercleCir(this);
	}
	
	public Cercle formerCercleCir(Triangle t){
		Point p1=t.p1;
		Point p2=t.p2;
		Point p3=t.p3;
		
		int x1=p1.getX();
		int x2=p2.getX();
		int x3=p3.getX();
		
		int y1=p1.getY();
		int y2=p2.getY();
		int y3=p3.getY();
		
		int x1Carre=(int) Math.pow(x1, 2);
		int x2Carre=(int) Math.pow(x2, 2);
		int x3Carre=(int) Math.pow(x3, 2);
		
		int y1Carre=(int) Math.pow(y1, 2);
		int y2Carre=(int) Math.pow(y2, 2);
		int y3Carre=(int) Math.pow(y3, 2);
		
		int a=(x3Carre-x2Carre+y3Carre-y2Carre)/(2*(y3-y2));
		int b=(x2Carre-x1Carre+y2Carre-y1Carre)/(2*(y2-y1));
		int c=(x2-x1)/(y2-y1);
		int d=(x3-x2)/(y3-y2);
		
		int xc=(a-b)/(c-d);  //abscisse de point du centre de cercle
		
		
		int e=-(x2-x1)/(y2-y1);
		int f=x2Carre-x1Carre+y2Carre-y1Carre;
		int g=2*(y2-y1);
		
		int yc=e*xc+f/g;//ordonnee de point du centre de cercle
		
		Point ctr=new Point(xc,yc);//point du centre de cercle
		double ry=ctr.distance(p1);//
		
		return new Cercle(ctr, ry);
	}
	
	
}
