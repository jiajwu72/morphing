import com.mysql.jdbc.StandardSocketFactory;

public class FonctionAffine extends Fonction{
	double a,b;
	
	public FonctionAffine() {
		
	}
	
	public FonctionAffine(double a,double b) {
		this.a=a;
		this.b=b;
	}
	
	Point pointIntersection(Fonction f){
		
		FonctionAffine fAff=new FonctionAffine();
		FonctionVertical fVerti=new FonctionVertical();
		FonctionConstant fConst=new FonctionConstant();
		
		double x=0;
		double y=0;
		
		//la fonction affine intersecte avec la fonction affine
		if(f.getClass().equals(fAff.getClass())){
			
			f=(FonctionAffine)f;
			if(a!=((FonctionAffine) f).a){
				x= ((b-((FonctionAffine) f).b)/a-((FonctionAffine) f).a);
			}
			y=a*x+b;
			return new Point((int)x,(int)y);
		}
		
		//la fonction affine intersecte avec la fonction constant
		if(f.getClass().equals(fConst.getClass())){
			return ((FonctionConstant)(f)).pointIntersection(this);
		}
		
		
		//la fonction affine intersecte avec la fonction verticale
		if(f.getClass().equals(fVerti.getClass())){
			
			return ((FonctionVertical)(f)).pointIntersection(this);
		}
		
		return null;
	}
	
	Point ptAbscisse(int x){
		
		int y=(int) (a*x+b);
		return new Point(x,y);
	}
	
	
	
	public FonctionAffine perpendiculaireEnUnPoint(Point p) {
		double aa=-1/this.a;
		double bb=p.getY()-aa*p.getX();
		FonctionAffine f=new FonctionAffine(aa, bb);
		return f;
		
	}
	
	
	
	public String toString() {
		return "FonctionAffine: y="+a+"x"+"+"+b+"\n";
	}
	
	
	public static void main(String[] args) {
		Point p1=new Point(10,0);
		Point p2=new Point(2,2);
		
		//FonctionAffine f=fApartir2pts(p1, p2);
		//System.out.println("a:"+f.a+"  b:"+f.b+"\n------------------------");
		
		//FonctionAffine g=f.perpendiculaireEnUnPoint(new Po)int(1,1));
		//System.out.println("a:"+g.a+"  b:"+g.b);
	}

	@Override
	int ordonneeDAbscisse(int x) {
		
		int h=(int) (Math.round(a*x+b+0.0));
		//System.out.println("a:"+a+"  b:"+b+"  ord:"+h);
		return h;
	}

}
