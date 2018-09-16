



import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ImageFileFilter extends FileFilter{
    private final String[] okFileExtensions = 
	new String[] {"jpg", "gif", "png"};
    @Override
    public boolean accept(File file){
	int i = file.getName().lastIndexOf('.');
	// Dont filter directories
	if(i < 0)
	    return true;
	for (String extension : okFileExtensions)
	    if (file.getName().toLowerCase().endsWith(extension))
		return true;
	return false;
    }
    @Override
    public String getDescription() {
	return "*.jpg, *.bmp, *.gif";
    }
}
