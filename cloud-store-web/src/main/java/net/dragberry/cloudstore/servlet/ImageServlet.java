package net.dragberry.cloudstore.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import net.dragberry.cloudstore.business.ImageServiceLocal;
import net.dragberry.cloudstore.domain.Image;

@Named
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = -1016854414183822604L;
	
	@Inject
	private ImageServiceLocal imageService;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String imageName = uri.substring(uri.lastIndexOf("/") + 1);
		
        // Lookup Image by ImageId in database.
        Image image = imageService.fetchImage(imageName);

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
        File file = new File(image.getRealPath());
        InputStream is = new FileInputStream(file);
        IOUtils.copy(is, response.getOutputStream());
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFileName() + "\"");

        // Write image content to response.
        response.getOutputStream().close();
        response.getOutputStream().flush();
	}

}
