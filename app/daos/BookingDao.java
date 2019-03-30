package daos;

import akka.stream.impl.fusing.Collect;
import models.Booking;

import java.awt.print.Book;
import java.util.Collection;

public interface BookingDao {

    Booking create(Booking booking);
    Collection<Booking> getBookingsByHomeId(Integer id);
}
