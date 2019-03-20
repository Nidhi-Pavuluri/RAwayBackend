package daos;

import models.Image;

import java.util.Collection;

public interface ImageDao extends CrudDao<Image, String> {
    String[] searchByHomeId(Integer id);

}
