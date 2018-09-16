
public class FonctionVertical extends Fonction{
	double a;
	
	public FonctionVertical() {
		
	}
	
	public FonctionVertical(double a) {
		this.a=a;
	}
	
	public FonctionVertical(Point p){
		this(p.getX());
	}
	
	FonctionConstant perpendiculaireEnUnPoint(Point p){
		return new FonctionConstant(p.getY());
	}

	@Override
	FonctionAffine fApartir2pts(Point p1, Point p2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Point pointIntersection(Fonction j) {
		FonctionAffine fAff=new FonctionAffine();
		FonctionConstant fConst=new FonctionConstant();
		
		double x=this.a;
		double y=0;
		
		//la fonction verticale intersecte avec la fonction affine
		
		if(j.getClass().equals(fAff.getClass())){
			y=((FonctionAffine) j).a*x+((FonctionAffine) j).b;
			return new Point((int)x,(int)y);
			
		}
		
		//la fonction verticale intersecte avec la fonction constant
		if(j.getClass().equals(fConst.getClass())){
			return ((FonctionConstant) j).pointIntersection(this); 
		}
		
		return null;
		
	}

	@Override
	public String toString() {
		return "FonctionVertical: "+"x="+a+"\n";
	}

	@Override
	Point ptAbscisse(int x) {
		// TODO Auto-generated method stub
		return new Point(x,(int) a);
	}
	
	Point ptOrd(int y){
		return new Point((int)a,y);
	}
	@Override
	int ordonneeDAbscisse(int x) {
		
		return (int) a;
	}
}
