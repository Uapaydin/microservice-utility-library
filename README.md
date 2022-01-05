General util is a helper jar for services. In order to use, you need to make the following configurations. To use this
project, pull the code and use maven to locally deploy to your .mvn folder (since this project is not added to
artifactory yet)
After that your project can get the jar from your local maven repository

In **SpringBootMainApplication**;

```
@SpringBootApplication(scanBasePackages = {"com.turkcell"})
```

In **pom.xml** ;

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

<dependency>
    <groupId>com.turkcell.boss</groupId>
    <artifactId>ms-util</artifactId>
    <version>DESIRED VERSION OF ARTIFACT</version>
</dependency>
```

In **application.properties**;

```properties
spring.application.name= [your application name for logging (better if it is same with service name)]
spring.mvc.throw-exception-if-no-com.turkcell.handler-found=true
spring.web.resources.add-mappings=false
```


# To user Kafka integration follow instruction below
**Add following properties to your application.property/yml file

```yml
kafka:
   consumer:
      active: true
      bootstrapServers: localhost:29092
      groupId: consumer_group_1
      keyDeserializer: org.apache.kafka.common.serialization.StringDeserializer
      valueDeserializer: org.springframework.kafka.support.serializer.JsonDeserializer
   producer:
      active: true
         bootstrapServers: localhost:29092
         keySerializer: org.apache.kafka.common.serialization.StringSerializer
         valueSerializer: org.springframework.kafka.support.serializer.JsonSerializer
 ```
### Producer

1. Autowire KafkaTemplate from Util library (use of autowiring through constructor suggested)
    ```java
        private final KafkaTemplate<String, Object> kafkaTemplate;
    ```
1. Send your message using code below
    ```java
        kafkaTemplate.send("utku", KafkaTestMessage.builder()
        .age(1).userName("Utku")
        .Country("Turkey").title(TitleEnum.WORKER).build());
    ```

### Consumer

1. Autowire KafkaConsumerConfig from Util library (use of autowiring through constructor suggested)
    ```java
        private final KafkaConsumerConfig kafkaConsumerConfig;
    ```
1. Create your custom ContainerFactory as shown below ( groupId information is optional providing it will help kafka improve load balancing)
    ```java
        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, KafkaTestMessage> kafkaMessageTestFactory(){
            return kafkaConsumerConfig.createCustomConsumerFactory(KafkaTestMessage.class,"consumer_group_1");
        }
    ```
1. create yout KafkaListener with your topic name as shown below (you **MUST** user your custom containerFactory for deserialization)
    ```java
    @KafkaListener(topics = "utku", containerFactory = "kafkaMessageTestFactory" )
    public void listenGroupFoo(KafkaTestMessage message) {
        log.info(message.toString());
    }
    ```
   
## DatabaseConnection

To create a database connection using this library you need to define following property in your yml or property file.

```yml
   config:
      database:
         active: true
         package-scan: [YOUR_ENTITY_PACKAGE_LOCATION]
```

config.database.package-scan is the package location of your entities.

config.database.active lets the library know if the database connection beans needed to be created.
If you set this true library expect a bean named <strong>dataSource</strong>
Example code is given below:
```java
package net.superonline.account.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
   
   /**
   * @author Utku APAYDIN
   * @created 27/12/2021 - 16:13
   */
   @Configuration
   @EnableJpaRepositories(
      basePackages = "net.superonline.account", //application root path
      entityManagerFactoryRef = "databaseEntityManager",// DON'T CHANGE THIS
      transactionManagerRef = "databaseTransactionManager"// DON'T CHANGE THIS
   )
   
   public class DatabaseConfiguration {
      @Bean(name = "customDatasourceBean") // DON'T CHANGE THIS
      @ConfigurationProperties(prefix = "spring.ds-postgres")// Your database config properties example given below DON'T CHANGE THIS
      public DataSource dataSource() {
      return  DataSourceBuilder.create().build();
   }

}

```

These definitions should not be placed in application.yml or application.properties file

```yml
spring:
  ds-postgres:
    jdbc-url: "jdbc:postgresql://localhost:5432/account"
    username: postgres
    password: mysecretpassword
```

