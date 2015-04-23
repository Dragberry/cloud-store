package net.dragberry.cloudstore.dataimport;

import java.io.InputStream;

import javax.ejb.Local;

@Local
public interface ProductImporterLocal {
	
	void doImport(InputStream is) throws Exception;

}
