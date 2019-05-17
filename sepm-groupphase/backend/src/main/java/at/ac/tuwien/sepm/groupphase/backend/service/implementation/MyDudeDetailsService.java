package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyDudeDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDudeDetailsService.class);
    private IDudeRepository dudeRepository;

    @Autowired
    public MyDudeDetailsService(IDudeRepository dudeRepository){
        this.dudeRepository = dudeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Dude dude = null;

        try {
            dude = this.dudeRepository.findByName(name);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Wrong username");
            throw new UsernameNotFoundException("Wrong username");
        } catch (DataAccessException e) {
            e.printStackTrace();
            LOGGER.error("Database Error");
            throw new UsernameNotFoundException("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Unknown Error");
            throw new UsernameNotFoundException("Unknown Error");
        }

        if (dude == null) {
            LOGGER.error("Bad credentials");
            throw new UsernameNotFoundException("Bad credentials");
        }

        LOGGER.info("Username: " + dude.getName());
        return buildUserFromUserEntity(dude);
    }


    private User buildUserFromUserEntity(Dude dude) {

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User springUser = new User(dude.getName(),
            dude.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            getAuthorities(dude.getRoles()));

        return springUser;
    }

    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String role : roles) {
            authorityList.add(new SimpleGrantedAuthority(role));
        }
        return authorityList;
    }
}
