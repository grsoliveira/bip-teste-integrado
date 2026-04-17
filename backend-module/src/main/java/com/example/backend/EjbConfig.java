package com.example.backend;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.util.Properties;

import com.example.ejb.BeneficioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EjbConfig {

  @Bean
  public BeneficioService beneficioService() throws Exception {
    URL configUrl = getClass().getClassLoader().getResource("wildfly-config.xml");
    System.setProperty("wildfly.config.url", configUrl.toString());

    Properties props = new Properties();
    props.put(Context.INITIAL_CONTEXT_FACTORY,
        "org.wildfly.naming.client.WildFlyInitialContextFactory");

//    props.put(Context.PROVIDER_URL, "http-remoting://localhost:8083");
    props.put(Context.PROVIDER_URL, "remote+http://localhost:8083");
    props.put(Context.SECURITY_PRINCIPAL, "appuser");
    props.put(Context.SECURITY_CREDENTIALS, "app123");

    Context context = new InitialContext(props);

//    return (BeneficioService) context.lookup(
//        "java:global/ejb-module-1.0.0/BeneficioEjbService!com.example.ejb.BeneficioService"
//    );
    return (BeneficioService) context.lookup(
        "ejb:/ejb-module-1.0.0/BeneficioEjbService!com.example.ejb.BeneficioService"
    );
  }
}