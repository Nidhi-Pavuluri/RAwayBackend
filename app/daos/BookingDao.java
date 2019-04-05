package daos;

import akka.stream.impl.fusing.Collect;
import models.Booking;

import java.awt.print.Book;
import java.util.Collection;
import java.util.List;

public interface BookingDao {

    Booking create(Booking booking);
    Collection<Booking> getBookingsByHomeId(Integer id);
    Integer getBookingsByHome(Integer id);
    Collection<Booking> getBookingsByUserId(Integer id);
    Integer findHomeidByBookingId(Integer id);
    Booking findBookingById(Integer id);
    Booking delete(Booking booking);
}
