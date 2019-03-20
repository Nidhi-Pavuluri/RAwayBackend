package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import daos.ImageDao;
import models.Image;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ImageStore;

import java.io.File;
import java.nio.file.Path;


public class ImagesController extends Controller {

    private String extension ;
    private final static Logger.ALogger LOGGER = Logger.of(ImagesController.class);

    private ImageStore imageStore;
    private ImageDao imageDao;

    @Inject
    public ImagesController(String extension, ImageStore imageStore, ImageDao imageDao) {
        this.extension = extension;
        this.imageStore = imageStore;
        this.imageDao = imageDao;
    }



    public Result uploadImage() {



        final Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        if (null == body) {
            return badRequest("Not multipart request");
        }

        final Http.MultipartFormData.FilePart<File> image = body.getFile("file");
        if (null == image) {
            return badRequest("No file found");
        }

        if (image.getContentType().equals("image/png")) {

            extension = ".png";
            LOGGER.debug("extension is " + extension);
        }

        else if(image.getContentType().equals("image/jpg")){
            extension = ".jpg";
            LOGGER.debug("extension is " + extension);
        }

        else if(image.getContentType().equals("image/jpeg")){
            extension = ".jpeg";
            LOGGER.debug("extension is " + extension);
        }

        final Path source = image.getFile().toPath();

        String imageId = imageStore.save(source,extension);

        final String downloadUrl = routes.ImagesController.downloadImage(imageId).absoluteURL(request());


        final ObjectNode result = Json.newObject();
        result.put("image_url", downloadUrl);
        //result.put("imageId", imageId);

        return ok(result);
    }

//    public String getExtension(){
//
//
//        final Http.MultipartFormData<File> body = request().body().asMultipartFormData();
//        if (null == body) {
//            LOGGER.debug("body");
//            return null;
//        }
//
//        final Http.MultipartFormData.FilePart<File> image = body.getFile("file");
//        if (null == image) {
//            LOGGER.debug("image");
//            return null;
//        }
//
//        if (image.getContentType().equals("image/png")) {
//
//            extension = ".png";
//            LOGGER.debug("extension is " + extension);
//        }
//
//        else if(image.getContentType().equals("image/jpg")){
//            extension = ".jpg";
//            LOGGER.debug("extension is " + extension);
//        }
//
//        else if(image.getContentType().equals("image/jpeg")){
//            extension = ".jpeg";
//            LOGGER.debug("extension is " + extension);
//        }
//        return extension;
//
//    }

    public Result downloadImage(String id) {
        final File file = imageStore.getImageById(id);
        //LOGGER.debug("extension is "+ extension);
        if (null == file) {
            return notFound("Image not found");
        }

        return ok(file);
    }

    public Result deleteImage(String id) {
        final boolean deleted = imageStore.deleteImageById(id);
        if (!deleted) {
            return notFound("Image not found");
        }

        return ok();
    }

    @Transactional
    public Result getImagesByHomeId(Integer id) {
        String[] images = imageDao.searchByHomeId(id);
        final JsonNode result = Json.toJson(images);
        return ok(result);
    }



}
