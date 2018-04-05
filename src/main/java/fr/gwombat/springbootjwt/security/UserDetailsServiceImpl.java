package fr.gwombat.springbootjwt.security;

import fr.gwombat.springbootjwt.user.ApplicationUser;
import fr.gwombat.springbootjwt.user.ApplicationUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserService applicationUserService;

    public UserDetailsServiceImpl(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ApplicationUser applicationUser = applicationUserService.getByUsername(username);
        if (applicationUser == null)
            throw new UsernameNotFoundException(username);

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), new ArrayList<>(0));
    }
}
