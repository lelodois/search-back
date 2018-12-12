package br.com.lelo.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@Profile(value = "default")
public class ResourceConfig extends ResourceServerConfigurerAdapter {

   @Override
   public void configure(ResourceServerSecurityConfigurer resources) {
       resources.resourceId("myresource");
   }

   @Override
   public void configure(HttpSecurity httpSecurity) throws Exception {
       String oauthRead = "#oauth2.hasScope('read')";
       String oauthWrite = "#oauth2.hasScope('write')";

       httpSecurity
               .cors()
               .and()
               .authorizeRequests()
               .anyRequest()
               .authenticated()
               .and()
               .requestMatchers()
               .antMatchers("/**")
               .and()
               .authorizeRequests()
               .anyRequest()
               .authenticated()
               .antMatchers(HttpMethod.GET, "/**").access(oauthRead)
               .antMatchers(HttpMethod.OPTIONS, "/**").access(oauthRead)
               .antMatchers(HttpMethod.POST, "/**").access(oauthWrite)
               .antMatchers(HttpMethod.PUT, "/**").access(oauthWrite)
               .antMatchers(HttpMethod.PATCH, "/**").access(oauthWrite)
               .antMatchers(HttpMethod.DELETE, "/**").access(oauthWrite);
   }

}
