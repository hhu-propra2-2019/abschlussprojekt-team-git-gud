package de.hhu.propra2.material2.mops.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WORKAROUND for https://issues.redhat.com/browse/KEYCLOAK-11282
 * Bean should move into {@link SecurityConfiguration} if Bug has been resolved
 */
@Configuration
public class KeycloakConfig {

    /**KeycloakConfigResolver.
     * @return KeycloakSpringBootConfigResolver
     */
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
