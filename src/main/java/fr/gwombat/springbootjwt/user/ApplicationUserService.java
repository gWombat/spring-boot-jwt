package fr.gwombat.springbootjwt.user;

import fr.gwombat.springbootjwt.user.ApplicationUser;

public interface ApplicationUserService {

    ApplicationUser getByUsername(String username);

    ApplicationUser save(ApplicationUser user);
}
