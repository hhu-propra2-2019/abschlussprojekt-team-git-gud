package de.hhu.propra2.material2.mops.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/** Security config class.
 */
@SuppressWarnings("PMD")
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** configures the Spring Web Security.
     * @param http injects http object.
     * @throws Exception no Execption handeling right now.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN");

        http.formLogin()
                .permitAll();
        http.logout()
                .permitAll();
    }
}
