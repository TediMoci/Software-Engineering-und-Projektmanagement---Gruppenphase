package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
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
public class MyFitnessProviderDetailsService implements UserDetailsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFitnessProviderDetailsService.class);
    private IFitnessProviderRepository fitnessProviderRepository;

    @Autowired
    public MyFitnessProviderDetailsService(IFitnessProviderRepository fitnessProviderRepository){
        this.fitnessProviderRepository = fitnessProviderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException  {

    FitnessProvider fitnessProvider = null;
        try {
            fitnessProvider = this.fitnessProviderRepository.findByName(name);
        }catch(IndexOutOfBoundsException e){
            LOGGER.error("Wrong username");
            throw new UsernameNotFoundException("Wrong username");
        }catch(DataAccessException e){
            e.printStackTrace();
            LOGGER.error("Database Error");
            throw new UsernameNotFoundException("Database Error");
        }catch(Exception e){
            e.printStackTrace();
            LOGGER.error("Unknown Error");
            throw new UsernameNotFoundException("Unknown Error");
        }

        if(fitnessProvider == null){
            LOGGER.error("Bad credentials");
            throw new UsernameNotFoundException("Bad credentials");
        }
        LOGGER.info("Username: "+fitnessProvider.getName());
        return buildUserFromUserEntity(fitnessProvider);
    }


    private User buildUserFromUserEntity(FitnessProvider fitnessProvider) {

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User springUser = new User( fitnessProvider.getName(),
            fitnessProvider.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            getAuthorities(fitnessProvider.getRoles()));

        return  springUser;
    }
    private static List<GrantedAuthority> getAuthorities (List<String> roles){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(String role : roles){
            authorityList.add(new SimpleGrantedAuthority(role));
        }
        return authorityList;
    }
}
