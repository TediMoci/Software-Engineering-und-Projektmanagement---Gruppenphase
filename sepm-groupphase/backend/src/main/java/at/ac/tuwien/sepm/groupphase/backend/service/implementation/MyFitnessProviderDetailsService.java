package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IUserService;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MyFitnessProviderDetailsService {

    @Autowired
    private IFitnessProviderRepository fitnessProviderRepository;

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException  {

        FitnessProvider fitnessProvider = fitnessProviderRepository.findByName(name);
        if (fitnessProvider == null){
            throw new UsernameNotFoundException("No fitness provider found with username; " + name);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return  new User(
            fitnessProvider.getName(),
            fitnessProvider.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            getAuthorities(fitnessProvider.getRoles()));

    }
    private static List<GrantedAuthority> getAuthorities (List<String> roles){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(String role : roles){
            authorityList.add(new SimpleGrantedAuthority(role));
        }
        return authorityList;
    }
}
