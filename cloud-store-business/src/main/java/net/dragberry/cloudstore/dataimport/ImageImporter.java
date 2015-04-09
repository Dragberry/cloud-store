package net.dragberry.cloudstore.dataimport;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.business.ImageServiceLocal;
import net.dragberry.cloudstore.domain.Image;
import net.dragberry.cloudstore.query.ImageQuery;

@Stateless
public class ImageImporter implements ImageImporterLocal, Serializable {

    private static final long serialVersionUID = 6494715717157433768L;

    private static Log LOGGER = LogFactory.getLog(ImageImporter.class);
    
    private static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>();
    static {
        CONTENT_TYPE_MAP.put("jpg", "image/jpeg");
        CONTENT_TYPE_MAP.put("png", "image/png");
        CONTENT_TYPE_MAP.put("jpeg", "image/jpeg");
        CONTENT_TYPE_MAP.put("gif", "image/gif");
    }
    
    @Inject
    private ImageServiceLocal imageService;

    @Override
    public void doImageImport(String imageFolderPath) {
        File folder = new File(imageFolderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            LOGGER.info("Incorrect image folder path!");
            return;
        }
        if (folder.list().length <= 0) {
            LOGGER.info("The image folder is empty!");
            return;
        }
        scanFolder(folder.listFiles());
    }
    
    private void scanFolder(File[] folder) {
    	for (File file : folder) {
        	if (file.isDirectory()) {
        		scanFolder(file.listFiles());
        	}
        	String fileName = file.getName();
        	String absolutePath = file.getAbsolutePath();
            String contentType = CONTENT_TYPE_MAP.get(fileName.substring(fileName.lastIndexOf(".") + 1));
            if (contentType == null) {
            	LOGGER.info("File '" + absolutePath + "' is not an image!");
                break;
            }
        	String path = absolutePath.substring(Image.IMAGE_DIRECTORY.length(), absolutePath.lastIndexOf(File.separator) + 1);
        	ImageQuery imageQuery = new ImageQuery();
        	imageQuery.setPath(path);
            imageQuery.setFileName(fileName);
            imageQuery.setContentType(contentType);
            imageService.saveImage(imageQuery);
            imageService.saveImage(imageQuery);
        }
    }

}
