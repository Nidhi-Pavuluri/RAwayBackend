package daos;

import models.Booking;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;

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

    @Override
    public Collection<Booking> getBookingsByHomeId(Integer id) {
        Collection<Booking> bookings = null;

        try {
            TypedQuery<Booking> query = jpaApi.em().createQuery("select b from Booking b where home.homeId = '" + id + "'", Booking.class);

        bookings = query.getResultList();
        }
        catch (NoResultException nre){

        }
        if(null == bookings)
            return null;
        return bookings;
    }

    @Override
    public Collection<Booking> getBookingsByUserId(Integer id) {
        Collection<Booking> bookings = null;

        try {
            TypedQuery<Booking> query = jpaApi.em().createQuery("select b from Booking b where user.userId = '" + id + "'", Booking.class);

            bookings = query.getResultList();
        }
        catch (NoResultException nre){

        }
        if(null == bookings)
            return null;
        return bookings;
    }

    @Override
    public Integer findHomeidByBookingId(Integer id) {
       Integer homeId = 0;

        try {
            TypedQuery<Integer> query = jpaApi.em().createQuery("select home.homeId from Booking  where bookingId = '" + id + "'", Integer.class);

            homeId = query.getSingleResult();
        }
        catch (NoResultException nre){

        }
        if(null == homeId)
            return 0;
        return homeId;
    }
}
