package daos;

import models.Home;
import java.util.Collection;
import java.util.Optional;

public interface HomeDao {
    Home create(Home home);
    Optional<Home> read(Integer id);
    Home findHomeById(Integer id);
   Home Bookupdate(Home home);
    Home deleteadmin (Home home );
    Home deleteuser (Home home);
    Home homeReportupdate(Home home);
    Home deleteReportadmin(Home home);
    Home homeStatusupdate(Home home);
    Home deleteRequestUpdate(Home home);
    Collection<Home> pendingListings();
    Collection<Home> pendingUsers();
    Collection<Home> searchByUsername(String username);
    Collection<Home> approvedlist();
    Collection<Home> reportlist();
    Collection<Home> filters(Home home);
    Collection<Home> search(Home home);

}
