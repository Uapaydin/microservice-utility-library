package com.turkcell.microserviceutil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author Utku APAYDIN
 * @created 30/12/2021 - 13:46
 */
@Configuration
@ConditionalOnProperty(value = "config.database.active", havingValue = "true")
@Order(Integer.MIN_VALUE)
public class DatabaseBaseConfiguration {

    private final DataSource customDatasourceBean;

    @Value("${config.database.package-scan}")
    private String packageScanPath;

    public DatabaseBaseConfiguration(DataSource customDatasourceBean) {
        this.customDatasourceBean = customDatasourceBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean databaseEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(customDatasourceBean);
        em.setPackagesToScan(packageScanPath);
        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.ddl-auto", false);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.physical_naming_strategy", "com.turkcell.microserviceutil.util.CustomPhysicalNamingStrategy");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager databaseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( databaseEntityManager().getObject());
        return transactionManager;
    }
}
