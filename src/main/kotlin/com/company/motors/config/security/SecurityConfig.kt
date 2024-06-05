package com.company.motors.config.security

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import java.util.Collections.emptyMap

@Configuration
class SecurityConfig  {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{csrf -> csrf.disable()}
            .sessionManagement{sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .httpBasic { httpBasic -> httpBasic.disable() }
            .formLogin { formLogin -> formLogin.disable() }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/v1/private/**").authenticated()
                    .requestMatchers("/v1/public/**").permitAll()
                    // permit all access to /public

            }.oauth2ResourceServer { oauth2 ->
                oauth2.jwt {
                    it.jwtAuthenticationConverter(jwtAuthenticationConverter())
                }
            }



        return http.build()
    }

    fun jwtAuthenticationConverter() : JwtAuthenticationConverter {
        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(KeycloakRealmRoleConverter())
        return jwtAuthenticationConverter
    }

    @Bean
    fun jwtDecoderByIssuerUri(properties: OAuth2ResourceServerProperties): JwtDecoder? {
        val jwkSetUri = properties.jwt.jwkSetUri
        val jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build()
        jwtDecoder.setClaimSetConverter(MappedJwtClaimSetConverter.withDefaults(emptyMap()))
        return jwtDecoder
    }


}
