





import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;

import java.awt.*;

public class UploadListenner implements ActionListener{

    private Fenetre fenetre;
    Ecran ecran;
    private URL manform;
	
    public UploadListenner(Fenetre f,Ecran jp) {
    	super();
    	fenetre=f;
    	ecran=jp;
    		
    }
	
   
	
	
	
    public void actionPerformed(ActionEvent e) {
    		if(ecran==fenetre.getEcranG() ||(ecran==fenetre.getEcranD() /*&& fenetre.getEcranG().getTerminer()*/)){
    		String[] choix={"Gallery","Repertoire"};
    		String choisi=(String) JOptionPane.showInputDialog(null,"Vous pouvez choisir image de ","Upload",
							   JOptionPane.INFORMATION_MESSAGE,null,choix,choix[0]);
    		//System.out.println(choisi);
		
    		if (choisi==choix[0]){ //on parcour le gallery
    			JFrame jf=new JFrame();
    			jf.setLayout(new GridLayout(1, 3));
    			jf.setVisible(true);
    			//jf.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    			jf.setLocation(new java.awt.Point(550, 150));
    			jf.setPreferredSize(new Dimension(900, 550));
			
    			for(int i=0;i<fenetre.getGallery().size();i++){
	    	
    				Photo photo=fenetre.getGallery().get(i);
	    	
    				JButton jb=new JButton();
				
    				try {
    					BufferedImage img = ImageIO.read(getClass().getResource("/"+photo.toString()));
    					jb.setIcon(new ImageIcon(img));
    				} catch (IOException ex) {}
				
    				jf.add(jb);
    				//System.out.println(jb.getIcon());
    				jb.addMouseListener(new MouseAdapter() {
    					public void mouseClicked(MouseEvent e){
    						
    						ecran.setPhoto(photo);
    						if (ecran==fenetre.getEcranD()){
    							if(photo.dejaMorphingAvec(fenetre.getEcranG().getPhoto().getNom())){
    								fenetre.getEcranD().setTerminer(true);
    					    		fenetre.getEcranD().repaint();
    					    		//return ;
    					    	}else{
    					    		photo.clear();
    					    	}
    						}
    						//System.out.println(photo.toString());
    						jf.dispose();
    					}
    				});
    			}
			
    			jf.pack();
	
    		}
    		
    		if(choisi==choix[1]){
    			JFileChooser chooser = new JFileChooser();
    			ImageFileFilter filter = new ImageFileFilter();
    			chooser.setFileFilter(filter);
    			int returnVal = chooser.showOpenDialog(ecran);
    			if(returnVal == JFileChooser.APPROVE_OPTION) {
    				try{
    					String str=chooser.getSelectedFile().getName();
    					//Photo p=new Photo(str);
    					//ecran.setPhoto(p);
    				}catch(Exception e1){
    					System.out.println("Can't find file.");
    				}
    				
    			}
    		}
    		}else if(fenetre.getEcranG().getPhoto()==null ){
    			JOptionPane.showMessageDialog(null, "l ecran gauche est vide!");
    		}else{
    			JOptionPane.showMessageDialog(null, "Vous devez finaliser le placement des points(clique droit sur l ecran)");
    		}
    }
    
	
  
    
	


}
