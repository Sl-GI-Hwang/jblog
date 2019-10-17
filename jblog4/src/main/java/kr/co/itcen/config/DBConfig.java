package kr.co.itcen.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfig {
	@Bean
	public DataSource datasource() {
		BasicDataSource basicDatasource = new BasicDataSource();
		basicDatasource.setDriverClassName("org.mariadb.jdbc.Driver");
		basicDatasource.setUrl("jdbc:mariadb://192.168.1.88:3306/jblog3?characterEncoding=utf8");
		basicDatasource.setUsername("jblog3");
		basicDatasource.setPassword("jblog3");
		basicDatasource.setMaxActive(100);
		basicDatasource.setInitialSize(10);
		
		return basicDatasource;
	}
	
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
