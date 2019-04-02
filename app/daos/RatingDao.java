package daos;

import models.Rating;

import java.util.Collection;

public interface RatingDao {
    Rating create(Rating booking);
    Double getRatingsByHomeId(Integer homeId);
}