package org.opengrid.security;

import javax.annotation.Resource;
import javax.ws.rs.HttpMethod;

import org.opengrid.security.impl.MongoTokenAuthenticationService;
import org.opengrid.util.PropertiesManager;
import org.opengrid.util.ServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Resource(name="userDetailsService")
	private UserDetailsService userService;
	
	//@Resource(name="tokenAuthService")
    private TokenAuthenticationService tokenAuthenticationService;
    
    @Resource(name="authManager")
    private AuthenticationManager authManager;
    
    protected PropertiesManager properties = ServiceProperties.getProperties();
 
    public SpringSecurityConfig() {
        super(true);
        //tokenAuthenticationService = new TokenAuthenticationService(properties.getStringProperty("auth.key"));
        
        //TODO: update to use dep injection that works with WebSecurityConfigurerAdapter
        tokenAuthenticationService = new MongoTokenAuthenticationService();
        tokenAuthenticationService.setKey(properties.getStringProperty("auth.key"));
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl().and()
                .authorizeRequests()
 
                // Allow anonymous resource requests
                .antMatchers("/").permitAll()
                .antMatchers("/rest").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("**/*.html").permitAll()
                .antMatchers("**/*.css").permitAll()
                .antMatchers("**/*.js").permitAll() 
                
                //Added to prevent CORS issue
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()                                 
 
                // Allow anonymous login to auth and current resources
                .antMatchers("/rest/users/token").permitAll()
                 
                // All other request need to be authenticated
                .anyRequest().authenticated().and()
 
                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
				//.addFilterBefore(new StatelessLoginFilter("/rest/users/token", tokenAuthenticationService, userService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                
                .addFilterBefore(
                		new StatelessLoginFilter(
                				"/rest/users/token", 
                				tokenAuthenticationService, 
                				userService, 
                				(AuthenticationManager) authManager
                		), UsernamePasswordAuthenticationFilter.class
                )
                
                
                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }
 
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //return super.authenticationManagerBean();
    	return (AuthenticationManager) authManager;
    }
 
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userService;
    }
 
    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return tokenAuthenticationService;
    }
}