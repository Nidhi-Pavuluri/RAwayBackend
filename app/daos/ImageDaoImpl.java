package daos;

import controllers.HomeController;
import models.Image;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ImageDaoImpl implements daos.ImageDao {

    private final static Logger.ALogger LOGGER = Logger.of(HomeController.class);

    final JPAApi jpaApi;

    @Inject
    public ImageDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

//    @Override
//    public Collection<Image> getImageById(Integer id){
//
//        if(null == id){
//            throw new IllegalArgumentException("id must be provided");
//        }
//
//        LOGGER.debug(String.valueOf(id));
//         Query query= jpaApi.em().createQuery("SELECT imageUrl FROM Image where id = '" + id + "'", Image.class);
//        List images = query.getResultList();
//
//        return images;
//    }

    @Override
    public Image create(Image entity) {
        jpaApi.em().persist(entity);
        return entity;
    }

    @Override
    public Optional<Image> read(String id) {
        return Optional.empty();
    }

    @Override
    public Image update(Image entity) {
        jpaApi.em().persist(entity);
        return entity;
    }

    @Override
    public Image delete(String id) {

        if(null == id){
            throw new IllegalArgumentException("Image Id must be provided");
        }

        final Image existingimage = jpaApi.em().find(Image.class, id);

        if(null == existingimage){
            throw new IllegalArgumentException("Invalid home");
        }

        jpaApi.em().remove(existingimage);

        return existingimage;

    }

    @Override
    public Collection<Image> all() {
        return null;
    }

    @Override
    public String[] searchByHomeId(Integer Id) {
        LOGGER.debug("id is " + Id);


        if(null == Id) {
            throw new IllegalArgumentException("id must be provided");
        }


        Collection<Image> images = null;
        String[] array;

        List<String> img_list;

        try {
            String queryString = "SELECT imageUrl FROM Image  WHERE home.homeId = '" + Id +"' ";
            LOGGER.debug("queryString{} " + queryString);
            TypedQuery<String> query = jpaApi.em().createQuery(queryString,String.class);
            img_list=  query.getResultList();
            array = img_list.toArray(new String[0]);

//            for(String url : img_list){
//
//            }
        //img = Arrays.copyOf(img,img.length,String [].class);


        }
        catch(NoResultException nre){
            throw new IllegalArgumentException("No image found corresponding to given home id");
        }
        return array;




    }

}