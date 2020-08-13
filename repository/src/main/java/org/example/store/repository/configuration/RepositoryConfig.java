package org.example.store.repository.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "org.example.store.model")
@EnableJpaRepositories(basePackages = "org.example")
@EnableTransactionManagement
public class RepositoryConfig {

}