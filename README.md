# Spring-Boot

# Spring Beans and ApplicationContext

## Spring Beans
In Spring, the objects that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container.

**How to create Beans**

Generally we have two methods to create Beans:
1. @Component
2. @Configuration and @Bean

### **1.@Componenet:** 
The @Component annotation indicates that a class should be managed by the Spring IoC container.

**How it works**

- When a class is annotated with @Component, Spring automatically: 
- Registers the class as a bean 
- Scans the application for other classes annotated with @Component 
- Instantiates the classes 
- Injects any specified dependencies into the classes 
- Injects the classes wherever needed
  
**Specializations of @Component**
The spring framework provides three special annotations that are special types of @Component annotations: @Controller, @Service, and @Repository.

```
@Component
public class productService{
}
```

### **2.@Configuration and @Bean:** 
- The @Configuration annotation in Spring is a class-level annotation that indicates a class is a source of bean definitions:
- The @Bean annotation in Spring is a method-level annotation that indicates that a method returns a bean to be managed by the Spring context. It's similar to the XML <bean/> element

**What it does**
- The @Configuration annotation tells the Spring container that a class has @Bean definition methods. The Spring container then processes the class to generate Spring Beans for use in the application.

**Features**
- The @Configuration annotation allows for: 
- Declaring inter-bean dependencies by calling @Bean methods on @Configuration classes 
- Using annotations for dependency injection

```
@Configuration
public class PaymentMethods{
  @Bean
  public PaymentService gPayPaymentServce(){
    return new gPayPaymentServce();
  }
}
```

**How to check if Beans are present in application?**
- 1.using Actuators
- 2.using object of application context

- 1.Actuators: Spring Boot Actuator is a sub-project of Spring Boot that provides production-grade services for managing and monitoring a Spring Boot application.

**Dependency For Actuator**

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```
In Application.properties
```
management.endpoints.web.base-path=/manage-ecom
management.endpoints.web.exposure.include=*
management.endpoint.info.enabled=false
```
For check beans go to url: http://localhost:8080/actuator/beans
