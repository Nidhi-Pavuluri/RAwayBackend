package daos;

import models.Booking;
import play.db.jpa.JPAApi;

import javax.inject.Inject;

public class BookingDaoImpl implements BookingDao{

    final JPAApi jpaApi;

    @Inject
    public BookingDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }


    @Override
    public Booking create(Booking booking) {
        if(null == booking) {
            throw new IllegalArgumentException("Home must be provided");
        }

        jpaApi.em().persist(booking);
        return booking;

    }
}
