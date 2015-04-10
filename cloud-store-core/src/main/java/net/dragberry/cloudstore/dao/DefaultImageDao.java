package net.dragberry.cloudstore.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.dragberry.cloudstore.domain.Image;
import net.dragberry.cloudstore.domain.Image_;
import net.dragberry.cloudstore.query.ImageListQuery;
import net.dragberry.cloudstore.query.ImageQuery;

@Stateless
public class DefaultImageDao extends AbstractDao<Image> implements ImageDao {

	@Override
	public Image findImage(Long id) {
		return getEntityManager().find(Image.class, id);
	}
	

	@Override
	public Image findImage(String fileName) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Image> cq = cb.createQuery(Image.class);
		Root<Image> imageRoot = cq.from(Image.class);
		cq.where(cb.equal(imageRoot.get(Image_.fileName), fileName));
		List<Image> resultList = getEntityManager().createQuery(cq).getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
		
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
		image.setPath(query.getPath());
		image.setFileName(query.getFileName());
		image.setContentType(query.getContentType());
		getEntityManager().persist(image);
		return image;
	}

}
