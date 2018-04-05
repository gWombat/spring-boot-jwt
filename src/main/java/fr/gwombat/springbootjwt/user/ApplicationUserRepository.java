package fr.gwombat.springbootjwt.user;

import fr.gwombat.springbootjwt.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);

}
