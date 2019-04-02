package daos;

import models.Booking;
import models.Rating;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;

public class RatingDaoImpl implements RatingDao{
    private final static Logger.ALogger LOGGER = Logger.of(daos.RatingDaoImpl.class);

    final JPAApi jpaApi;

    @Inject
    public RatingDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public Rating create(Rating rating) {
        if(null == rating) {
            throw new IllegalArgumentException("Rating must be provided");
        }

        jpaApi.em().persist(rating);

        return rating;

    }

    @Override
    public Double getRatingsByHomeId(Integer homeId) {
        Double rating = null;

        try {
            String queryString = "select avg(rating) from Rating where ratingId in (select ratingId from Rating where home.homeId = '"+ homeId+"')";
            TypedQuery<Double> query = jpaApi.em().createQuery(queryString, Double.class);

            rating = query.getSingleResult();
            LOGGER.debug("rating is " + rating);
        }
        catch (NoResultException nre){

        }
        if(null == rating)
            return null;
        return rating;

    }


}
