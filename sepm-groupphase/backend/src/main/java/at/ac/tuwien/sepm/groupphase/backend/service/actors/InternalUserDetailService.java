package at.ac.tuwien.sepm.groupphase.backend.service.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.DudePrincipal;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InternalUserDetailService implements UserDetailsService {

    @Autowired
    private IDudeRepository dudeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Dude dude = dudeRepository.findByName(username);
        if (dude == null) {
            throw new UsernameNotFoundException(username);
        }
        return new DudePrincipal(dude);
    }

}
