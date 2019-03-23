package daos;


import models.Home;
import models.User;

import java.util.Collection;

public interface Userdao{
    User create(User user);
    User update(User user);
    User findUserByName(String username);
    User findUserByAuthToken(String authToken);
    User updateaccessToken(User user);
    User homeRoleUpdate(User user);
    User findUserById(Integer  id);

}