package de.hpi.bpbridge.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties("idealobridge")
@Getter @Setter
@Primary
public class IdealoBridgeProperties {

    private String oAuth2ClientId, oAuth2ClientSecret, accessTokenURI, apiUrl, randomOfferRoute, rootUrlRoute;
}
