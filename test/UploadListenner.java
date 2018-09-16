package morphing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.prism.Image;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class UploadListenner implements ActionListener{

	private Fenetre fenetre;
	Ecran ecran;
	
	
	
	public UploadListenner(Fenetre f,Ecran jp) {
		super();
		fenetre=f;
		ecran=jp;
		
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		String[] choix={"Gallery","Répertoire"};
		String choisi=(String) JOptionPane.showInputDialog(null,"Vous pouvez choisir l'image de ","Upload",
				JOptionPane.INFORMATION_MESSAGE,null,choix,choix[0]);
		System.out.println(choisi);
		if (choisi==choix[0]){ //on parcour le gallery
			JFrame jf=new JFrame();
			jf.setLayout(new GridLayout(0, 3));
			jf.setVisible(true);
			jf.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
			jf.setLocation(new java.awt.Point(550, 150));
			jf.setPreferredSize(new Dimension(900, 550));
			
			for(int i=0;i<fenetre.getGallery().size();i++){
				Photo photo=fenetre.getGallery().get(i);

				JButton jb=new JButton();
				
				try {
				    BufferedImage img = ImageIO.read(getClass().getResource("/"+photo.toString()));
				    jb.setIcon(new ImageIcon(img));
				} catch (IOException ex) {
				}
				
				jf.add(jb);
				System.out.println(jb.getIcon());
				jb.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						ecran.setPhoto(photo);
						System.out.println(photo.toString());
						jf.dispose();
					}
				});
			}
			
			jf.pack();
		}
		if(choisi==1){
		    File file = new File();
		    File[] files=file.listFiles(new ImgFileFilter());
		}
	}


    class ImgFileFilter implements FileFilter{

	public boolean accept(File pathname){
	    String filename = pathname.getName().toLowerCase();
	    if(filename.contains(".jpg")||filename.contains(".gif")||filename.contains(".png")){
		return true;
	    }else{
		return false;
	    }
	}

	public String getDescription() {
	    return "*.jpg, *.bmp, *.gif";
	}
    }
	
	


}
