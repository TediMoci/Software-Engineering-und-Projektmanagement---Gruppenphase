package at.ac.tuwien.sepm.groupphase.backend.configuration;

import at.ac.tuwien.sepm.groupphase.backend.configuration.properties.H2ConsoleConfigurationProperties;
import at.ac.tuwien.sepm.groupphase.backend.security.HeaderTokenAuthenticationFilter;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.MyDudeDetailsService;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.MyFitnessProviderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final MyFitnessProviderDetailsService fitnessProviderDetailsService;
    private final MyDudeDetailsService dudeDetailsService;

    @Autowired
    public SecurityConfiguration(MyFitnessProviderDetailsService fitnessProviderDetailsService, MyDudeDetailsService dudeDetailsService) {
        this.fitnessProviderDetailsService = fitnessProviderDetailsService;
        this.dudeDetailsService = dudeDetailsService;
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes((WebRequest) requestAttributes, includeStackTrace);
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(fitnessProviderDetailsService).passwordEncoder(passwordEncoder());
        auth.userDetailsService(dudeDetailsService).passwordEncoder(passwordEncoder());
    }

    @Configuration
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    private static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final String h2ConsolePath;
        private final String h2AccessMatcher;

        @Autowired
        private AuthenticationManager authenticationManager;

        public WebSecurityConfiguration(
            H2ConsoleConfigurationProperties h2ConsoleConfigurationProperties
        ) {
            h2ConsolePath = h2ConsoleConfigurationProperties.getPath();
            h2AccessMatcher = h2ConsoleConfigurationProperties.getAccessMatcher();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .headers().frameOptions().sameOrigin().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint((req, res, aE) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST).permitAll()
                .antMatchers(HttpMethod.GET,
                    "/authentication",
                    "/downloadImage/{fileName:.+}",
                    "/user",
                    "/user/name",
                    "/dudes",
                    "/dudes/all",
                    "/dudes/{id}",
                    "/dudes/{id}/exercises",
                    "/dudes/{id}/workouts",
                    "/dudes/{id}/trainingSchedules",
                    "/dudes/{id}/activeTrainingSchedule",
                    "/dudes/{id}/activeTrainingSchedule/done",
                    "/dudes/{id}/uploadImage",
                    "/dudes/{dudeId}/follow",
                    "/dudes/filtered",
                    "/{id}/{version}/exercises",
                    "/user",
                    "/fitnessProvider",
                    "/fitnessProvider/{id}",
                    "/fitnessProvider/{id}/courses",
                    "/fitnessProvider/{id}/allFollowers",
                    "/fitnessProvider/all",
                    "/fitnessProvider/{name}/followers",
                    "/fitnessProvider/filtered",
                    "/course",
                    "/course/all",
                    "/course/filtered",
                    "/course/{id}",
                    "/exercise",
                    "/exercise/all",
                    "/exercise/filtered",
                    "/exercise/{id}/{version}",
                    "/exercise/{id}/uploadImage",
                    "/workout",
                    "/workout/all",
                    "/workout/filtered",
                    "/workout/{id}/{version}",
                    "/workout/{id}/{version}/exercises",
                    "/trainingSchedule",
                    "/trainingSchedule/{id}/{version}",
                    "/trainingSchedule/{id}/{version}/workouts",
                    "/v2/api-docs",
                    "/swagger-resources/**",
                    "/webjars/springfox-swagger-ui/**",
                    "/swagger-ui.html")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,
                    "/dudes/{dudeId}/follow/{fitnessProviderId}",
                    "/exercise/{id}",
                    "/course/{id}",
                    "/trainingSchedule/{id}",
                    "/trainingSchedule/active/{dudeId}",
                    "/workout/{id}").permitAll()
                .antMatchers(HttpMethod.PUT,
            "/dudes/{name}",
                    "/dudes/{dudeId}/follow/{fitnessProviderId}",
                    "/exercise/{id}",
                    "/trainingSchedule/{id}",
                    "/trainingSchedule/active/done",
                    "/course/{id}",
                    "/workout/{id}",
                    "/fitnessProvider/{name}").permitAll()
            ;
            if (h2ConsolePath != null && h2AccessMatcher != null) {
                http
                    .authorizeRequests()
                    .antMatchers(h2ConsolePath + "/**").access(h2AccessMatcher);
            }
            http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .addFilterBefore(new HeaderTokenAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
        }

           @Bean
          @Override
            public AuthenticationManager authenticationManagerBean() throws Exception {
              return super.authenticationManagerBean();
          }

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                    .addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("PUT","POST","OPTION","GET", "DELETE");
            }
        };
    }

}
