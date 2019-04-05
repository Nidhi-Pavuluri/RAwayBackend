package daos;

import models.Booking;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.awt.print.Book;
import java.util.Collection;
import java.util.List;

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
    public Integer getBookingsByHome(Integer id) {
        Integer bookings = 0;
        Long value;

        try {
            TypedQuery<Long> query = jpaApi.em().createQuery("select count(bookingId) from Booking  where home.homeId = '" + id + "'", Long.class);

            bookings = (new Long(query.getSingleResult())).intValue();
        }
        catch (NoResultException nre){

        }
        if(null == bookings)
            return 0;
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

    @Override
    public Booking findBookingById(Integer id) {

        Booking booking = null;
        try {
            TypedQuery<Booking> query = jpaApi.em().createQuery("select b from Booking b where bookingId = '" + id + "'", Booking.class);

            booking = query.getSingleResult();
        }
        catch (NoResultException nre){
            throw new IllegalArgumentException("No booking found");
        }
        return booking;
    }

    @Override
    public Booking delete(Booking booking) {


        if(null == booking){
            throw new IllegalArgumentException("Invalid booking");
        }

        jpaApi.em().remove(booking);

        return booking;

    }
}
