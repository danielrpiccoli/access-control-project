package access_control.security;

import access_control.entity.AppUser;
import access_control.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email);

        if (appUser == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }

        return new User(appUser.getEmail(), appUser.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole())));
    }
}
