import java.util.ArrayList;

public class Cercle {
	Point centre;
	double rayon;
	
	Cercle(Point c,double r){
		centre=c;
		rayon=r;
	}
	
	boolean existe4emePt(ArrayList<Point> pts){
		for(int i=0;i<pts.size();i++){
			if(centre.distance(pts.get(i))<rayon){
				return true;
			}
		}
		return false;
	}
	
}
