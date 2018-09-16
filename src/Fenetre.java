




import java.awt.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;



public class Fenetre extends JFrame{

	private ArrayList<Photo> gallery;
	private Ecran ecranG;
	private Ecran ecranD;
	EcranF ecranFinal;
	JButton uploadG;
	JButton uploadD;
	JButton clearG;
	JButton clearD;
	JButton inverserBoutton;
	JButton morphingBoutton ;
	
    ConnecteurBD conn;
	
	//private Point[][] tabDesCouplesDePointsCles;
	private JMenuBar menuBar;
	
	public Fenetre(){
		System.out.println("hhhhhhhhhhh");
	    conn=new ConnecteurBD();
	    
		setJMenuBar();
		this.initiaFenetre();
		
		this.setJMenuBar(menuBar);
		
		gallery=new ArrayList<>();
		
		Photo p1=new Photo("chien.jpg");
		Photo p2=new Photo("ours.jpg");
		Photo p3=new Photo("singe.jpg");
		Photo p4=new Photo("blanc.png");
		gallery.add(p1);
		gallery.add(p2);
		gallery.add(p3);
		gallery.add(p4);
		
		conn.savePhoto(p1.getNom(),p1.getIdMax() );
		conn.savePhoto(p2.getNom(),p2.getIdMax() );
		conn.savePhoto(p3.getNom(),p3.getIdMax() );
		conn.savePhoto(p3.getNom(),p4.getIdMax() );
	}
	
	ArrayList<Photo> getGallery(){
		return gallery;
	}
	
	//inverser les 2 images dans 2 ecrans
	void inverserPhoto(){  
		Photo stockage=ecranG.getPhoto();
		ecranG.setPhoto(ecranD.getPhoto());
		ecranD.setPhoto(stockage);
		ecranG.repaint();
		ecranD.repaint();
	}
	
	//mise en place de JMenuBar
	void setJMenuBar(){
		menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu=new JMenu("Menu");
		JMenu info=new JMenu("Info");
		
		JMenuItem New=new JMenuItem("New");
		New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ecranG.removePhoto();
				ecranG.setTerminer(false);
				ecranG.repaint();
				
				ecranD.removePhoto();
				ecranD.setTerminer(false);
				ecranD.repaint();
			}
		});
		
		JMenuItem Exit=new JMenuItem("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JMenuItem Help=new JMenuItem("Help");
		Help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jf=new JFrame();
				
				jf.setLocation(700,300);
		        jf.setSize(550, 550);
		        
		        JPanel jp=new JPanel();
		        jp.setBackground(Color.WHITE);
		        JLabel jl1=new JLabel("indication pour notre application");
		        jl1.setFont(new Font("serif", Font.BOLD, 30));
		        jp.add(jl1);
		        /*Desktop desktop=Desktop.getDesktop();
		        String path="C/morphing3/README.txt";
		        try {
					desktop.open(new File(path));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
		        
		        
		        jf.add(jp);
				jf.setVisible(true);
			}
		});
		
		JMenuItem Credits=new JMenuItem("Credits");
		Credits.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jf=new JFrame();
				
				jf.setLocation(700,300);
		        jf.setSize(550, 550);
		        
		        JPanel jp=new JPanel();
		        jp.setBackground(Color.WHITE);
		        JLabel jl1=new JLabel("Morphing3");
		        jl1.setFont(new Font("serif", Font.BOLD, 30));
		        JLabel jl2=new JLabel("                    Create by :jiajunWU  matheus  hangqingCao boWang");
		        jp.add(jl1);
		        jp.add(jl2);
		        
		        jf.add(jp);
				jf.setVisible(true);
			}
		});

		menu.add(New);
		menu.add(Exit);
		info.add(Help);
		info.add(Credits);
		
		
		menuBar.add(menu);
		menuBar.add(info);
	}
	
	void initiaFenetre(){
		setTitle("Morphing 3");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(500,300);
        setSize(900, 550);
        setResizable(false);
        
        
        try {
    		BufferedImage img = ImageIO.read(getClass().getResource("/"+"logo.jpg"));	
    		this.setIconImage(img);
    	} catch (IOException ex) {}
        
        getContentPane().setLayout(null);
 
        
        
        
        //creation des ecrans
        ecranG = new Ecran(this);
        ecranD = new Ecran(this);
        ecranFinal=new EcranF();
        
        ecranG.setBounds(22, 20, 400, 400);
        //ecranG.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ecranG.setBackground(Color.WHITE); 
        
        ecranD.setBounds(470, 20, 400, 400);
        ecranD.setBackground(Color.WHITE);
        
        ecranFinal.setBounds(22, 20, 400, 400);
        ecranFinal.setBackground(Color.WHITE); 
        
        getContentPane().add(ecranG);
        getContentPane().add(ecranD);   
 
        //creation des bouttons
        uploadG=new JButton("upload");
        uploadD=new JButton("upload");
        clearG=new JButton("clear");
        clearD=new JButton("clear");
        morphingBoutton=new JButton("morphing");
        inverserBoutton=new JButton("<<>>");
        
        uploadG.setBounds(20, 430, 91, 21);
        uploadG.addActionListener(new UploadListenner(this,ecranG));
        getContentPane().add(uploadG);
        uploadD.setBounds(780, 430, 91, 21);
        uploadD.addActionListener(new UploadListenner(this,ecranD));
        getContentPane().add(uploadD);
        
        clearG.setBounds(20, 450, 91, 21);
        clearG.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ecranG.getPhoto()!=null && !ecranG.getTerminer()){
					ecranG.getPhoto().clear();
					ecranG.getPhoto().setIdMax(ecranG.getPhoto().getPoints().size());
					ecranG.repaint();
					conn.removeAllPoints(ecranG.getPhoto().getNom());
					conn.updateIdMaxPhoto(4, ecranG.getPhoto().getNom());
					
					
				}
				
			}
		});
        getContentPane().add(clearG);
        clearD.setBounds(780, 450, 91, 21);
        clearD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ecranD.getPhoto()!=null ){
					ecranD.getPhoto().clear();
					ecranD.getPhoto().setIdMax(ecranD.getPhoto().getPoints().size());
					//ecranFinal.points_interm=new ArrayList<int[][]>();
					ecranD.repaint();
					conn.removeAllPoints(ecranD.getPhoto().getNom());
					conn.updateIdMaxPhoto(4, ecranD.getPhoto().getNom());
					
					ecranG.setOrdre(5);
					ecranG.repaint();
				}
				
				
			}
		});
        getContentPane().add(clearD);
        inverserBoutton.setBounds(400, 430, 91, 21);
        inverserBoutton.addActionListener(new ActionListener() {
   		 public void actionPerformed(java.awt.event.ActionEvent evt) {
   			 if(ecranG.getPhoto()!=null && ecranD.getPhoto()!=null 
   					 && ecranG.getTerminer() && ecranD.getTerminer()){
   				 inverserPhoto();
   				 ecranD.repaint();
   				 ecranG.repaint();
   			 }
   		 }
   	    });
        getContentPane().add(inverserBoutton);
        
        morphingBoutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//if(ecranG.getPhoto()!=null && ecranD.getPhoto()!=null){
				JFrame jf=new JFrame();
				jf.setLocation(700,300);
		        jf.setSize(550, 550);
		        jf.setVisible(true);
		        ecranFinal.setPhoto1(ecranG.getPhoto());
		        ecranFinal.setPhoto2(ecranD.getPhoto());
		        if(!ecranG.getPhoto().dejaMorphingAvec(ecranD.getPhoto().getNom())){
		        	conn.saveMorph(ecranG.getPhoto().getNom(), ecranD.getPhoto().getNom());
		        	conn.saveMorph(ecranD.getPhoto().getNom(), ecranG.getPhoto().getNom());
		        }
		        ecranFinal.miseAjourFonction();
		        
		        ecranFinal.imgInterm(5);
		        
		        /*Image im1=null;
		        Image im2=null;
		        try {
					im1 = ImageIO.read(getClass().getResource("/"+ecranG.getPhoto().toString()));
					im2 = ImageIO.read(getClass().getResource("/"+ecranD.getPhoto().toString()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} */
		        //ecranFinal.morph(im1,im2,10);
		       
		        //Image[] imgs=ecranFinal.morphs;
		        //System.out.println("imgs.length"+imgs.length);
		        jf.add(ecranFinal);
		        /*for(int i=0;i<imgs.length;i++){
		        	ecranFinal.removeAll();
		        	ecranFinal.setImgInterm(imgs[i]);
		        	
		        	try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	ecranFinal.repaint();
		        	System.out.println("img"+i);
		        }*/
		        
		        //jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		        
		        
				
				
			}
		});
        morphingBoutton.setBounds(400, 460, 91, 21);
        getContentPane().add(morphingBoutton);
        
        
        
        setVisible(true);
        
	}
	
	JButton getClearD(){
		return clearD;
	}
	
	Ecran getEcranG(){
		return ecranG;
	}
	
	Ecran getEcranD(){
		return ecranD;
	}
	
	JButton getUploadD() {
		// TODO Auto-generated method stub
		return uploadD;
	}
	
    public void dessinerTriangle(ArrayList<Point> pointts){
		
    }

    public static void main(String[] args) {
	javax.swing.SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		    new Fenetre();

	
		}
	    
	    });
	

    }
    
	

}

