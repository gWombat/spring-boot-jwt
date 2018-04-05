package fr.gwombat.springbootjwt.user;

import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public ApplicationUser getByUsername(String username) {
        return applicationUserRepository.findByUsername(username);
    }

    @Override
    public ApplicationUser save(ApplicationUser user) {
        return applicationUserRepository.save(user);
    }
}
