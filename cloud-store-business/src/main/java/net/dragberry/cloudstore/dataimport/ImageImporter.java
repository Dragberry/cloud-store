package net.dragberry.cloudstore.dataimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.business.ImageServiceLocal;
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
        if (folder.listFiles().length <= 0) {
            LOGGER.info("The image folder is empty!");
            return;
        }
        for (File file : folder.listFiles()) {
            InputStream is = null;
            ImageQuery imageQuery = null;
            try {
                imageQuery = new ImageQuery();
                is = new FileInputStream(file);
                byte[] content = IOUtils.toByteArray(is);
                String fileName = file.getName();
                String contentType = CONTENT_TYPE_MAP.get(fileName.substring(fileName.lastIndexOf(".") + 1));
                if (contentType == null) {
                    LOGGER.info("Can not determine content-type of the file with name '" + fileName + "'!");
                    return;
                }
                imageQuery.setContent(content);
                imageQuery.setFileName(fileName);
                imageQuery.setContentType(contentType); 
                imageService.saveImage(imageQuery);
                
            } catch (FileNotFoundException e) {
                LOGGER.info(e.getMessage(), e);
            } catch (IOException e) {
                LOGGER.info(e.getMessage(), e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        LOGGER.info(e.getMessage(), e);
                    }
                }
            }
        }
    }

}
