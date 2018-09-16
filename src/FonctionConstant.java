
public class FonctionConstant extends Fonction{
	double a;
	
	public FonctionConstant() {
		
	}
	
	public FonctionConstant(double a) {
		this.a=a;
	}
	
	public FonctionConstant(Point p){
		this(p.getY());
	}
	
	FonctionVertical perpendiculaireEnUnPoint(Point p){
		return new FonctionVertical(p.getX());
	}

	

	@Override
	Point pointIntersection(Fonction j) {
		FonctionAffine fAff=new FonctionAffine();
		FonctionVertical fVerti=new FonctionVertical();
		
		double x=0;
		
		//la fonction constatnt intersecte avec la foction affine
		if(j.getClass().equals(fAff.getClass())){
			
			double b=((FonctionAffine) j).b;
			double a=((FonctionAffine) j).a;
			
			x=(this.a-b)/a;
		}
		
		//la fonction constant intersecte avec la fonction vertical
		if(j.getClass().equals(fVerti.getClass())){
			x=((FonctionVertical) j).a;
		}
		
		double y=this.a;
		return new Point((int)x,(int)y);
	}

	
	public String toString() {
	
		return "Fonction constant: y="+a+"\n";
	}

	@Override
	Point ptAbscisse(int x) {
		return new Point(x,(int)a);
	}

	@Override
	int ordonneeDAbscisse(int x) {
		// TODO Auto-generated method stub
		return (int) a;
	}

	
}
