package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
