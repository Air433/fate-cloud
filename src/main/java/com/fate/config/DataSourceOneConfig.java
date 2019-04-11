package com.fate.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by ouyanggang on 2018/6/25.
 */
//@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceOneConfig {

  @Value("${spring.datasource.vip.driver-class-name}") String driver;
  @Value("${spring.datasource.vip.url}") String url;
  @Value("${spring.datasource.vip.username}") String username;
  @Value("${spring.datasource.vip.password}") String password;
  @Value("${spring.datasource.vip.minIdle}") int minIdle;
  @Value("${spring.datasource.vip.maxActive}") int maxActive;
  @Value("${spring.datasource.vip.initialSize}") int initialSize;
  @Value("${spring.datasource.vip.timeBetweenEvictionRunsMillis}") long timeBetweenEvictionRunsMillis;
  @Value("${spring.datasource.vip.minEvictableIdleTimeMillis}") long minEvictableIdleTimeMillis;
  @Value("${spring.datasource.vip.validationQuery}") String validationQuery;
  @Value("${spring.datasource.vip.testWhileIdle}") boolean testWhileIdle;
  @Value("${spring.datasource.vip.testOnBorrow}") boolean testOnBorrow;
  @Value("${spring.datasource.vip.testOnReturn}") boolean testOnReturn;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getMinIdle() {
    return minIdle;
  }

  public void setMinIdle(int minIdle) {
    this.minIdle = minIdle;
  }

  public int getMaxActive() {
    return maxActive;
  }

  public void setMaxActive(int maxActive) {
    this.maxActive = maxActive;
  }

  public int getInitialSize() {
    return initialSize;
  }

  public void setInitialSize(int initialSize) {
    this.initialSize = initialSize;
  }

  public long getTimeBetweenEvictionRunsMillis() {
    return timeBetweenEvictionRunsMillis;
  }

  public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
  }

  public long getMinEvictableIdleTimeMillis() {
    return minEvictableIdleTimeMillis;
  }

  public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }

  public String getValidationQuery() {
    return validationQuery;
  }

  public void setValidationQuery(String validationQuery) {
    this.validationQuery = validationQuery;
  }

  public boolean isTestWhileIdle() {
    return testWhileIdle;
  }

  public void setTestWhileIdle(boolean testWhileIdle) {
    this.testWhileIdle = testWhileIdle;
  }

  public boolean isTestOnBorrow() {
    return testOnBorrow;
  }

  public void setTestOnBorrow(boolean testOnBorrow) {
    this.testOnBorrow = testOnBorrow;
  }

  public boolean isTestOnReturn() {
    return testOnReturn;
  }

  public void setTestOnReturn(boolean testOnReturn) {
    this.testOnReturn = testOnReturn;
  }

  @Bean(name = "vip", destroyMethod = "close")
  public DruidDataSource com(){
    DruidDataSource druidDataSource = new DruidDataSource();
    druidDataSource.setDriverClassName(driver);
    druidDataSource.setUrl(url);
    druidDataSource.setUsername(username);
    druidDataSource.setPassword(password);
    druidDataSource.setMinIdle(minIdle);
    druidDataSource.setMaxActive(maxActive);
    druidDataSource.setInitialSize(initialSize);
    druidDataSource
        .setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    druidDataSource
        .setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    druidDataSource.setValidationQuery(validationQuery);
    druidDataSource.setTestWhileIdle(testWhileIdle);
    druidDataSource.setTestOnBorrow(testOnBorrow);
    druidDataSource.setTestOnReturn(testOnReturn);
    return druidDataSource;
  }
}
