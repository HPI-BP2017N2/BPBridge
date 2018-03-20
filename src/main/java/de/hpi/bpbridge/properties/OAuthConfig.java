package de.hpi.bpbridge.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

/**
 * @author stanislav.nowogrudski
 */
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@Configuration
@EnableOAuth2Client
@EnableConfigurationProperties(IdealoBridgeProperties.class)
public class OAuthConfig {

    @Autowired
    private IdealoBridgeProperties clientProperties;

    @Bean
    public RestTemplate oAuthRestTemplate() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setId("1");
        resourceDetails.setClientId(getClientProperties().getOAuth2ClientId());
        resourceDetails.setClientSecret(getClientProperties().getOAuth2ClientSecret());
        resourceDetails.setAccessTokenUri(getClientProperties().getAccessTokenURI());

    /*
    When using @EnableOAuth2Client spring creates a OAuth2ClientContext for us:
    "The OAuth2ClientContext is placed (for you) in session scope to keep the state for different users separate.
    Without that you would have to manage the equivalent data structure yourself on the server,
    mapping incoming requests to users, and associating each user with a separate instance of the OAuth2ClientContext."
    (http://projects.spring.io/spring-security-oauth/docs/oauth2.html#client-configuration)
    Internally the SessionScope works with a threadlocal to store variables, hence a new thread cannot access those.
    Therefore we can not use @Async
    Solution: create a new OAuth2ClientContext that has no scope.
    *Note: this is only safe when using client_credentials as OAuth grant type!
     */

//       return OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
        return new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext());

    }
}
