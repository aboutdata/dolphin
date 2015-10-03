package com.sabsari.dolphin.core.config;

import com.sabsari.dolphin.core.auth.domain.Authorization;
import com.sabsari.dolphin.core.history.domain.UserHistory;
import com.sabsari.dolphin.core.member.domain.User;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.ClassUtils;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@EnableJpaRepositories(basePackages={"com.sabsari.dolphin.core.member.repository",
									 "com.sabsari.dolphin.core.auth.repository",
									 "com.sabsari.dolphin.core.history.repository"})
public class DataConfig {
	
	@Value("${db.driverClassName}") String driverClassName;
    @Value("${db.url}") String url;
    @Value("${db.username}") String username;
    @Value("${db.password}") String password;
    @Value("${db.initialSize}") int initialSize;
    @Value("${db.maxActive}") int maxActive;
    @Value("${db.maxIdle}") int maxIdle;
    @Value("${db.minIdle}") int minIdle;
	
	@Bean
    public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMinIdle(minIdle);
		dataSource.setValidationQuery("select 1");
//		dataSource.setTestOnBorrow(true);
//		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
        return dataSource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(emf);
	    return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(ClassUtils.getPackageName(User.class),
        							  ClassUtils.getPackageName(Authorization.class),
        							  ClassUtils.getPackageName(UserHistory.class));
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	    factoryBean.setJpaProperties(jpaProperties());
	    
	    return factoryBean;
    }
    
	@Value("${jpa.dialect}") String dialect;
	@Value("${jpa.hbm2ddl}") String hbm2ddl;
	@Value("${jpa.showSql}") boolean showSql;
	@Value("${jpa.formatSql}") boolean formatSql;
	@Value("${jpa.useSqlComments}") boolean useSqlComments;

	@Bean
	public Properties jpaProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.put(Environment.SHOW_SQL, showSql);
		jpaProperties.put(Environment.FORMAT_SQL, formatSql);
		jpaProperties.put(Environment.USE_SQL_COMMENTS, useSqlComments);
		jpaProperties.put(Environment.HBM2DDL_AUTO, hbm2ddl);
		jpaProperties.put(Environment.DIALECT, dialect);
		return jpaProperties;
	}
}