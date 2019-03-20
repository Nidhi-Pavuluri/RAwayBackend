package daos;

import models.Home;
import java.util.Collection;

public interface HomeDao {
    Home create(Home home);
    Home findHomeById(Integer id);
   Home Bookupdate(Integer id);
    Home deleteadmin (Integer  id );
    Home deleteuser (Integer id);
    Home homeReportupdate(Integer id);
    Home deleteReportadmin(Integer id);
    Home homeStatusupdate(Integer id);
    Home deleteRequestUpdate(Integer id);
    Collection<Home> pendinglist();
    Collection<Home> searchByUsername(String username);
    Collection<Home> approvedlist();
    Collection<Home> reportlist();
    Collection<Home> filters(Home home);
    Collection<Home> search(Home home);

}
