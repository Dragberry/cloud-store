package net.dragberry.cloudstore.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import net.dragberry.cloudstore.domain.Image;
import net.dragberry.cloudstore.query.ImageListQuery;
import net.dragberry.cloudstore.query.ImageQuery;

@Stateless
public class DefaultImageDao extends AbstractDao<Image> implements ImageDao {

	@Override
	public Image findImage(Long id) {
		return getEntityManager().find(Image.class, id);
	}

	@Override
	public List<Image> fetchImages(ImageListQuery query) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Image> cq = cb.createQuery(Image.class);
		cq.from(Image.class);
		return getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	public Image saveImage(ImageQuery query) {
		Image image = new Image();
		image.setContent(query.getContent());
		image.setFileName(query.getFileName());
		image.setContentType(query.getContentType());
		getEntityManager().persist(image);
		return image;
	}

}
