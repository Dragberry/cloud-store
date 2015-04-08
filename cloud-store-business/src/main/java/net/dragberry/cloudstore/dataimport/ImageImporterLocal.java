package net.dragberry.cloudstore.dataimport;

import javax.ejb.Local;

@Local
public interface ImageImporterLocal {
    
    void doImageImport(String imageFolderPath);

}
