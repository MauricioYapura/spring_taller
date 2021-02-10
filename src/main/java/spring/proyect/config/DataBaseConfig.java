package spring.proyect.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataBaseConfig {
  @Autowired
  private Environment env;
  @Autowired
  private DataSource dataSource;

  @Bean
  public DataSource dataSource() {
	  
	  DriverManagerDataSource dataSource = new DriverManagerDataSource();
	  dataSource.setDriverClassName(this.env.getProperty("db.driver"));
	  dataSource.setUrl(this.env.getProperty("db.url"));
	  dataSource.setUsername(this.env.getProperty("db.username"));
	  dataSource.setPassword(this.env.getProperty("db.password"));
	  	  
    return dataSource;
  }  
}