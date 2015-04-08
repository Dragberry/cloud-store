package net.dragberry.cloudstore.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.dao.ImageDao;
import net.dragberry.cloudstore.domain.Image;
import net.dragberry.cloudstore.query.ImageListQuery;
import net.dragberry.cloudstore.query.ImageQuery;

@Stateless
public class ImageService implements ImageServiceLocal {
	
	private static Log LOGGER = LogFactory.getLog(ImageService.class);
	
	@Inject
	private ImageDao defaultImageDao;

	@Override
	public Image saveImage(ImageQuery imageQuery) {
		LOGGER.info("Entering into ImageService.saveImage...");
		try {
			imageQuery = new ImageQuery();
			File file = new File("Y:\\images\\details.png");
			InputStream is = new FileInputStream(file);
			byte[] content = IOUtils.toByteArray(is);
			String fileName = file.getName();
			imageQuery.setContent(content);
			imageQuery.setFileName(fileName);
			imageQuery.setContentType("image/png");		
		} catch (FileNotFoundException e) {
			LOGGER.info(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.info(e.getMessage(), e);
		}
		Image image = defaultImageDao.saveImage(imageQuery);
		LOGGER.info("Image '" + image.getFileName() + "' has been saved...");
		return image;
	}

	@Override
	public Image fetchImage(Long id) {
		LOGGER.info("Entering into ImageService.fetchImage...");
		return defaultImageDao.findImage(id);
	}

	@Override
	public List<Image> fetchImages(ImageListQuery imageListQuery) {
		LOGGER.info("Entering into ImageService.fetchImages...");
		return defaultImageDao.fetchImages(imageListQuery);
	}

}
