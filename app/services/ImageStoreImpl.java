package services;

import controllers.ImagesController;
import models.Image;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageStoreImpl implements ImageStore {

    private final static Logger.ALogger LOGGER = Logger.of(ImageStoreImpl.class);
    private static final Path STORAGE_ROOT = Paths.get("/Users/nidhi_p/Documents/images");


    public ImageStoreImpl() {

        File rootDir = STORAGE_ROOT.toFile();

        if (rootDir.exists()) {
            return;
        }

        if (!rootDir.mkdirs()) {
            LOGGER.error("Failed to create image stored root directory");
        }

    }

    @Override
    public String save(Path source,String extension) {

        final String imageId = generateImageId();
        final Path destination = STORAGE_ROOT.resolve(imageId + extension);
        LOGGER.debug("Source: {} Destination: {}", source, destination);

        try {
            Files.move(source, destination);
            return imageId;
        } catch (IOException e) {
            LOGGER.debug("Failed to move uploaded image into folder {}", destination);
            e.printStackTrace();
            return null;
        }

    }




    private String generateImageId() {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString();

    }

    @Override
    public File getImageById(String id) {

        final File file = STORAGE_ROOT.resolve(id + ".png").toFile();
        if (!file.isFile()) {
            return null;
        }

        return file;

    }

    @Override
    public boolean deleteImageById(String id) {

        final File file = STORAGE_ROOT.resolve(id + ".png").toFile();
        if (!file.isFile()) {
            return false;
        }

        try {
            boolean deleted = Files.deleteIfExists(file.toPath());
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



}