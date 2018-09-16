import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.SynchronousQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.omg.Messaging.SyncScopeHelper;
import org.omg.PortableServer.POA;

import com.mysql.fabric.xmlrpc.base.Array;

public class EcranF extends JPanel implements MouseListener {
	private static final Point[] trInterm = null;
	Photo photo1;
	Photo photo2;
	BufferedImage imgInterm;
	int a = 0;

	// ArrayList<Point> points_interm;
	int frames = 10;
	Image morphs[] = new Image[frames];
	LinkedList<Point[]> triangles;
	ArrayList<Fonction> fonctions; // transformation lineairement des points en
									// correspondants

	public EcranF() {
		super();
		// points_interm=new ArrayList<Point>();
		// miseAjourFonction();

		/*
		 * photoInterm=photo1; photoInterm.triangles=photo1.triangles;
		 */

		this.addMouseListener(this);
	}

	public void paint(Graphics g) {

		super.paint(g);

		if (imgInterm != null) {
			g.drawImage(imgInterm, 0, 0, imgInterm.getWidth(), imgInterm.getHeight(), this);
		}
		// System.out.println("draw imginterm");
		// g.drawImage(morphs[52], 10, 10,(int)(this.getWidth()*0.95) ,
		// (int)(this.getHeight()*0.95), this);

	}

	void setImgInterm(BufferedImage im) {
		imgInterm = im;
	}

	/*
	 * void morph(int alpha){ miseAjourFonction();
	 * points_interm.add(photo1.points.get(0));
	 * 
	 * 
	 * 
	 * for(int i=0;i<alpha;i++){ try { for(int j=1;j<points_interm.size();j++){
	 * 
	 * Thread.sleep(20);
	 * 
	 * //a chaque action on avance 1/alpha uniteX et uniteY de distance a
	 * deplacer int uniteX=(int)
	 * ((photo1.points.get(j).distance(photo2.points.get(j)))/alpha);//!!!!!!!!!
	 * !!!!!!!!
	 * 
	 * int x=points_interm.get(j-1).x+uniteX;
	 * if(!fonctions.get(j).getClass().equals(FonctionVertical.class)){ Point
	 * p=fonctions.get(j).ptAbscisse(x); }else{
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } }
	 */
	void miseAjourFonction() {
		fonctions = new ArrayList<Fonction>();
		Fonction f;

		for (int i = 0; i < photo1.points.size(); i++) {
			Point p1 = photo1.points.get(i);
			Point p2 = photo2.points.get(i);

			if (p1.x == p2.x) {
				f = new FonctionVertical(p1);
			} else if (p1.y == p2.y) {
				f = new FonctionConstant(p1);
			} else {
				f = p1.fAPartir2Pts(p2);
			}

			fonctions.add(f);
		}
	}

	/*
	 * BufferedImage miseAJourRGB(){
	 * 
	 * System.out.println("photo1:"+photo1); BufferedImage img=null; try {
	 * photoInterm=photo1; img =
	 * ImageIO.read(getClass().getResource("/"+photoInterm.toString())); int
	 * width=img.getWidth(); int height=img.getHeight();
	 * 
	 * 
	 * 
	 * for (int i=0;i<height;i++){ for (int j=0;j<height;j++){
	 * 
	 * img.setRGB(i, j, img.getRGB(j, i)-a);
	 * 
	 * }
	 * 
	 * } a+=5;
	 * 
	 * 
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return img;
	 * 
	 * }
	 */

	/*
	 * int pixel_from_Point(Point p){
	 * 
	 * }
	 */

	Point pointAuTempsT(Point pointDepart, Point pointArrivee, int nbUnite) {

		if(pointDepart.x==pointArrivee.x &&pointDepart.y==pointArrivee.y){
			return pointArrivee;
		}
		
		double avancement = 1 / (frames + 0.0) * nbUnite;
		int x = (int) (pointDepart.x + (pointArrivee.x - pointDepart.x) * (avancement));
		int y = fonctions.get(pointArrivee.getId() - 1).ordonneeDAbscisse(x);
		/*
		 * Fonction f=new FonctionAffine(); f=f.fApartir2pts(pointDepart,
		 * pointArrivee); int y=f.ordonneeDAbscisse(x);
		 */

		return new Point(x, y);
	}

	Point[] triangleAuTempsT(Point[] triangleDep, Point[] triangleArr, int temps) {
		
		
		
		Point[] pts = new Point[3];
		for (int i = 0; i < 3; i++) {
			pts[i] = pointAuTempsT(triangleDep[i], triangleArr[i], temps);

		}
		return pts;
	}

	/*
	 * int rgbFromPoint(Point p){ BufferedImage img=null; int rgb=0; try {
	 * img=ImageIO.read(getClass().getResource("/"+photoInterm.toString()));
	 * rgb=img.getRGB(p.x, p.y); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return rgb; }
	 */

	static double toDegres(Point p1, Point p2, Point p3) {
		double a1 = p2.distance(p1);
		double c1 = p2.distance(p3);
		double b1 = p1.distance(p3);

		double a1Carre = Math.pow(a1, 2);
		double b1Carre = Math.pow(b1, 2);
		double c1Carre = Math.pow(c1, 2);

		double alpha = Math.toDegrees(Math.acos((c1Carre + a1Carre - b1Carre) / (2 * a1 * c1)));
		return alpha;
	}

	static boolean ptDansTriangle(Point p, Point[] triangle) {
		
		
		double alpha = toDegres(triangle[0], p, triangle[1]);
		double beta = toDegres(triangle[0], p, triangle[2]);
		double gamma = toDegres(triangle[1], p, triangle[2]);

		double somme = alpha + beta + gamma;
		// System.out.println("somme"+somme);
		somme = Math.round(somme);
		return somme == 360;
	}

	int rgbAuTemps(int rgb1, int rgb2, int temps) {
		Color a = new Color(rgb1);
		Color b = new Color(rgb2);

		int ecartRed = (int) ((b.getRed() - a.getRed()) * temps / (frames + 0.0));
		int ecartGreen = (int) ((b.getGreen() - a.getGreen()) * temps / (frames + 0.0));
		int ecartBlue = (int) ((b.getBlue() - a.getBlue()) * temps / (frames + 0.0));
		int red = a.getRed() + ecartRed;
		int green = a.getGreen() + ecartGreen;
		int blue = a.getBlue() + ecartBlue;

		Color c = new Color(red, green, blue);
		return c.getRGB();
	}

	void imgInterm(int temps) {

		imgInterm = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		LinkedList<Point[]> trianglesDeparts = photo1.triangles;
		LinkedList<Point[]> trianglesArrivees = photo2.triangles;
		//LinkedList<Point[]> trianglesInterms = new LinkedList<>();

		for (int i = 0; i < trianglesDeparts.size(); i++) { // on parcour la
															// liste des
															// triangles

			Point[] trDepart = trianglesDeparts.get(i);
			Point[] trArrivee = trianglesArrivees.get(i);
			Point[] trInterm = triangleAuTempsT(trDepart,trArrivee,temps);// triangle
			System.out.print("trDepart:");  afficherTriangle(trDepart);
			System.out.print("trInterm:");  afficherTriangle(trInterm);
			System.out.print("trArrivee:");  afficherTriangle(trArrivee); System.out.println();
			for (int l = 0; l < 3; l++) { // dessiner des 3 sommets de triangle
											// intermediaire


				int rgb1 = photo1.getBufferedImage().getRGB(trDepart[l].getX(), trDepart[l].getY());
				
				int rgb2 = photo2.getBufferedImage().getRGB(trArrivee[l].getX(), trArrivee[l].getY());
				
				int rgb3 = rgbAuTemps(rgb1, rgb2, temps);
		

				// System.out.println("imgInterm.getWidth()"+imgInterm.getWidth());
				
				imgInterm.setRGB(trInterm[l].x, trInterm[l].y, rgb3);

			}
			

			
		
			System.out.println("------------------------------------------------------------------------");
			for(int j=plusPetitOrdDeTriangle(trInterm);j<=plusGrandOrdDeTriangle(trInterm);j++){     
				for(int k=plusPetitAbsDeTriangle(trInterm);k<=plusGrandAbsDeTriangle(trInterm);k++){
	
					//System.out.println("k:"+k+"   j:"+j);
					//si le point jest dans le triangle intermediare
					Point p=new Point(k,j);
					if(ptDansTriangle(p,trInterm)){ 
						
						
						//definir les parametre alpha beta gamma de point p
						p.setAlphaBetaGamma(trInterm);
						
						//on trouve le point dans triangle de depart dont alpha beta gamma egale....
						Point ptDep=ptDontAlphaBetaGammaEgale(p.alpha, p.beta, p.gamma, trDepart);
						System.out.println("ptDep:"+ptDep);
						if(ptDep.gamma==0){
							//imgInterm.setRGB(p.x, p.y, Color.red.getRGB());
							continue;
						}
						//System.out.println("ptDep:"+ptDep);
						ptDep.setAlphaBetaGamma(trDepart);
						
						//on trouve le point dans triangle de arrivee dont alpha beta gamma egale....
						Point ptArrivee=ptDontAlphaBetaGammaEgale(p.alpha, p.beta, p.gamma, trArrivee);
						ptDep.setAlphaBetaGamma(trArrivee);
						afficherTriangle(trDepart);
						afficherTriangle(trInterm);
						afficherTriangle(trArrivee);
						
						//on calcule le rgb de ce point
						int rgb1 = photo1.img.getRGB(ptDep.x, ptDep.y);
						
						int rgb2 = photo2.img.getRGB(ptArrivee.x, ptArrivee.y);
						
						int rgb3 = rgbAuTemps(rgb1, rgb2, temps);
						System.out.println("ptArr:"+ptArrivee);
						//System.out.println(rgb3);
						imgInterm.setRGB(p.x, p.y, rgb3);
						
					}
				}
			}
		}
		/*int nT=0;
		for(int h=0;h<=photo1.img.getHeight();h++){
			for(int w=0;w<=photo1.img.getWidth();w++){
				Point p=new Point(w,h);
				for(int i=0;i<trianglesInterms.size();i++){
					if(ptDansTriangle(p, trianglesInterms.get(i))){
						nT=i;
					}
					
				}
				Point[] trDepart = trianglesDeparts.get(nT);
				Point[] trArrivee = trianglesArrivees.get(nT);
				Point[] trInterm = trianglesInterms.get(nT);
				//definir les parametre alpha beta gamma de point p
				p.setAlphaBetaGamma(trInterm);
				
				//on trouve le point dans triangle de depart dont alpha beta gamma egale....
				Point ptDep=ptDontAlphaBetaGammaEgale(p.alpha, p.beta, p.gamma, trDepart);
				System.out.println("ptDep:"+ptDep);
				if(ptDep.gamma==0){
					//imgInterm.setRGB(p.x, p.y, Color.red.getRGB());
					continue;
				}
				//System.out.println("ptDep:"+ptDep);
				ptDep.setAlphaBetaGamma(trDepart);
				
				//on trouve le point dans triangle de arrivee dont alpha beta gamma egale....
				Point ptArrivee=ptDontAlphaBetaGammaEgale(p.alpha, p.beta, p.gamma, trArrivee);
				ptDep.setAlphaBetaGamma(trArrivee);
				afficherTriangle(trDepart);
				afficherTriangle(trInterm);
				afficherTriangle(trArrivee);
				
				//on calcule le rgb de ce point
				System.out.println("ptDep:"+ptDep);
				int rgb1=Color.black.getRGB();
				if(ptDep.x<=275&&ptDep.y>=0){
					rgb1 = photo1.img.getRGB(ptDep.x, ptDep.y);
				}
				//int rgb2 = photo2.img.getRGB(ptArrivee.x, ptArrivee.y);
				
				//int rgb3 = rgbAuTemps(rgb1, rgb2, temps);
				
				//System.out.println("ptArr:"+ptArrivee+"\n "+rgb3);
				//System.out.println(rgb3);
				imgInterm.setRGB(w,h, rgb1);
			
				
			}
			
		}*/
			
		}
	

	void definirTousAlphaBetaGamma(){
		
	}
	
	Point ptDontAlphaBetaGammaEgale(double alpha,double beta,double gamma,Point[] t){
		Point p=new Point();
		p.alpha=alpha;
		p.beta=beta;
		p.gamma=gamma;
		
		
		double a= (alpha*t[0].x+beta*t[1].x+gamma*t[2].x);
		double b= (alpha*t[0].y+beta*t[1].y+gamma*t[2].y);
		double m=alpha+beta+gamma;
		
		int x=(int) (Math.round(a/(m+0.0)));
		int y=(int) (Math.round(b/(m+0.0)));
		p.x=x;
		p.y=y;
		return p;
	}
	
	int plusPetitAbsDeTriangle(Point[] t){
		int a=t[0].x;
		for(int i=1;i<3;i++){
			if(a>t[i].x){
				a=t[i].x;
			}
		}
		return a;
	}
	
	int plusPetitOrdDeTriangle(Point[] t){
		int a=t[0].y;
		for(int i=1;i<3;i++){
			if(a>t[i].y){
				a=t[i].y;
			}
		}
		return a;
	}
	
	int plusGrandAbsDeTriangle(Point[] t){
		int a=t[0].x;
		for(int i=1;i<3;i++){
			if(a<t[i].x){
				a=t[i].x;
			}
		}
		return a;
	}
	
	int plusGrandOrdDeTriangle(Point[] t){
		int a=t[0].y;
		for(int i=1;i<3;i++){
			if(a<t[i].y){
				a=t[i].y;
			}
		}
		return a;
	}
	
	
	void afficherTriangle(Point[] tri) {
		for (int i = 0; i < tri.length; i++) {
			System.out.print(tri[i] + ",");
		}
		System.out.println();
	}

	void setPhoto1(Photo p) {
		photo1 = p;
	}

	void setPhoto2(Photo p) {
		photo2 = p;
	}

	ArrayList<Point> pixelsDansTriangle(Point[] triangle) {

		ArrayList<Point> pixels = new ArrayList<Point>();

		for (int i = plusPetitOrdDeTriangle(triangle); i < plusGrandOrdDeTriangle(triangle); i++) {
			for (int j = plusPetitAbsDeTriangle(triangle); j < plusGrandAbsDeTriangle(triangle); j++) {
				Point p = new Point(j, i);
				if (ptDansTriangle(p, triangle)) {
					pixels.add(p);
				}
			}
		}

		return pixels;
	}

	Point trouverPixelEnCorresp(Point p, ArrayList<Point> pixels) {

		for (int i = 0; i < pixels.size(); i++) {
			if (p.memeAlphaBetaGamma(p, pixels.get(i))) {
				return pixels.get(i);
			}
		}
		return null;
	}

	/*
	 * public void mouseClicked(MouseEvent e) {
	 * 
	 * //if(a>0 && a<=morphs.length){ if(e.getButton()==MouseEvent.BUTTON1){
	 * imgInterm=morphs[a]; a++; this.repaint();
	 * 
	 * 
	 * } else if(e.getButton()==MouseEvent.BUTTON3){ a--; if(a<0){ a=0; }
	 * 
	 * imgInterm=morphs[a];
	 * 
	 * this.repaint();
	 * 
	 * } //}
	 * 
	 * }
	 */

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		/*
		 * Point a=new Point(1,1); Point b=new Point(4,1); Point c=new
		 * Point(3,3); Point d=new Point(2,2); Point e=new Point(3,1); Point[]
		 * tr={a,b,c};
		 * 
		 * System.out.println("bdc:"+toDegres(c, d, b));
		 * System.out.println("adb:"+toDegres(a, d, b));
		 * System.out.println("adc:"+toDegres(a, d, c));
		 * System.out.println(ptDansTriangle(e, tr));
		 */

		/*
		 * Point depart=new Point(10,10); Point arrivee=new Point(50,40);
		 * 
		 * EcranF ef=new EcranF(); Point interm=ef.pointAuTempsT(depart,
		 * arrivee, 1); System.out.println(interm);
		 */

		// Fenetre f=new Fenetre();

		/*
		 * EcranF ef=new EcranF();
		 * 
		 * Point a=new Point(1,1,1); Point b=new Point(0,3,2); Point c=new
		 * Point(2,2,3); Point aa=new Point(2,1,1); Point bb=new Point(1,2,2);
		 * Point cc=new Point(3,3,3);
		 * 
		 * Point[] triangleDep={a,b,c}; Point[] triangleArr={aa,bb,cc};
		 * 
		 * ArrayList<Point> points1=new
		 * 
		 * ef.triangleAuTempsT(triangleDep, triangleArr, 3);
		 */

		/*
		 * Color a=new Color(22,76, 2); Color b=new Color(132,79, 88);
		 * 
		 * int rgb1=a.getRGB(); int rgb2=b.getRGB(); EcranF ef=new EcranF(); int
		 * rgb3=ef.rgbAuTemps(rgb1, rgb2, 10);
		 * 
		 * System.out.println("rgb1:"+rgb1+"  rgb2:"+rgb2+"  rgb3:"+rgb3);
		 */
		// Fenetre f=new Fenetre();
		EcranF ef = new EcranF();
		
		Point a = new Point(0, 0);
		Point b = new Point(2, 2);
		Point c = new Point(2, 0);
		Point m=new Point(1,0);
		
		
		Point aa = new Point(7, 1);
		Point bb = new Point(5, 3);
		Point cc = new Point(10, 4);
		
		Point[] triangleDep = { a, b, c };
		Point[] triangleArrivee = { aa, bb, cc };
		m.setAlphaBetaGamma(triangleDep);
		System.out.println("m.alpha:"+m.alpha+"  m.beta:"+m.beta+"  m.gamma:"+m.gamma);
		
		//Point[] trInterm=ef.triangleAuTempsT(triangleDep, triangleArrivee, 3);
		//ef.afficherTriangle(trInterm);
		//System.out.println(ef.ptDansTriangle(m, triangleDep));
		
		
		
		/*m.setAlphaBetaGamma(triangleDep);
		System.out.println("m.alpha:"+m.alpha+"  m.beta:"+m.beta+"  m.gamma:"+m.gamma);
		Point p=ef.ptDontAlphaBetaGammaEgale(m.alpha, m.beta, m.gamma, triangleArrivee);
		System.out.println("p.alpha:"+p.alpha+"  p.beta:"+p.beta+"  p.gamma:"+p.gamma+"\n"+p);*/
		/*Point mm=ef.ptDontAlphaBetaGammaEgale(m.alpha, m.beta, m.gamma, triangleArrivee);
		//ef.ptDontAlphaBetaGammaEgale(alpha, beta, gamma, t);
		
		System.out.println(mm.x+"  "+mm.y);*/
		
		
		

	}

}
