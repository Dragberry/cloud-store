package net.dragberry.cloudstore.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dragberry.cloudstore.business.ImageServiceLocal;
import net.dragberry.cloudstore.domain.Image;

@Named
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = -1016854414183822604L;
	
	@Inject
	private ImageServiceLocal imageService;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        // Get ID from request.
		Long imageId = null;
		try {
			imageId = Long.valueOf(request.getParameter("id"));
		} catch (NumberFormatException ne) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

        // Lookup Image by ImageId in database.
        Image image = imageService.fetchImage(imageId);

        // Check if image is actually retrieved from database.
        if (image == null) {
            // Do your thing if the image does not exist in database.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setContentType(image.getContentType());
        response.setContentLength(image.getContent().length);
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFileName() + "\"");

        // Write image content to response.
        response.getOutputStream().write(image.getContent());
	}

}
