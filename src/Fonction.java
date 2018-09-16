
public abstract class Fonction {
	
	public Fonction() {
		
	}
	abstract Fonction perpendiculaireEnUnPoint(Point p);
	
	/*abstract double abscPointIntersection(Fonction j) ;
	abstract double ordPointIntersection(Fonction j) ;*/
	
	public abstract String toString();
	FonctionAffine fApartir2pts(Point p1,Point p2) {
		double a,b;
		double x1=p1.getX();
		double x2=p2.getX();
		
		double y1=p1.getY();
		double y2=p2.getY();
		
		a=(y2-y1)/(x2-x1);
		b=y1-a*x1;
		return new FonctionAffine(a, b);
	}
	abstract Point pointIntersection(Fonction j) ;
	
	abstract Point ptAbscisse(int x);
	
	abstract int ordonneeDAbscisse(int x);
}
