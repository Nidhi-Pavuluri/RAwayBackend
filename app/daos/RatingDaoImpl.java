package daos;

import models.Booking;
import models.Rating;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;

public class RatingDaoImpl implements RatingDao{

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
    public Integer getRatingsByHomeId(Integer homeId) {
        Integer rating = null;

        try {
            String queryString = "select avg(rating) from rating  where ratingId in ( select ratingId from rating where home.homeId = '" + homeId + "')";
            TypedQuery<Integer> query = jpaApi.em().createQuery(queryString, Integer.class);

            rating = query.getSingleResult();
        }
        catch (NoResultException nre){

        }
        if(null == rating)
            return null;
        return rating;

    }


}
