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

# Spring Bean Life Cycle

![Screenshot 2024-12-25 224042](https://github.com/user-attachments/assets/5298d53a-da42-4776-a0ed-556ed0117683)

**We can Customize the Nature of Bean In Lifecycle**
- 1.Interface to customize bean: (Old)
-   a) InitializingBean: This is an interface which contains method public void afterPropertiesSet() . we need to implement this and perform action that you want. 
-   b) DisposableBean: This is an interface which contains method public void destroy() . we need to implement this and perform action that you want. 
- 2. @PostConstruct & @PreDestroy (New)
  ```
  @PostContruct
  public void init(){ // call after beans injected 
    System.out.println("PostConstructCall"); 
  }
  @PreDestroy
   public void destory(){ // call before destorye beans
    System.out.println("PostConstructCall"); 
  }
  ```
  after customization, bean life cycle
  ![Screenshot 2024-12-25 224042](https://github.com/user-attachments/assets/4f17cdb5-fce6-4581-9e7f-f368c495eb54)

  ```
## Spring Boot Bean Scope and it's type
Bean scopes define how a bean's lifecycle and visibility are managed within a container.

**Type:** Generally beans are 6 types, but here we will consider only 4.

**1.Singleton (Default):** Only one instance is created of beans and shared with all references of that beans. and ths is a default scope.

or 

When we define a bean with the singleton scope, the container creates a single instance of that bean; all requests for that bean name will return the same object, which is cached. Any modifications to the object will be reflected in all references to the bean. This scope is the default value if no other scope is specified.

**1.Prototype:** A new instance of the bean is created every time it is requested.
```
@Bean
@Scope("prototype")
public MyBean myPrototypeBean() {
    return new MyBean();
}
```
**3.Request (For Web Applications)**
A new instance of the bean is created for each HTTP request.
```
@Bean
@Scope("request")
public MyBean myRequestBean() {
    return new MyBean();
}
```
**4.Session (For Web Applications)**
A single instance of the bean is created for an HTTP session.
```
@Bean
@Scope("session")
public MyBean mySessionBean() {
    return new MyBean();
}
```
## @Qualifier and @Primary Annotation 

**@Qualifier:** @Qualifier annotation is used to resolve ambiguity problem, when multiple beans of same type exist in container. It allows us to specify which bean to inject in situations where the @Autowired annotation alone cannot decide which bean to use.

**How @Qualifier Solves the Problem**
The @Qualifier annotation allows you to specify which exact bean should be injected by its name.

Example: Imagine a scenario where we have multiple implementations of an interface, such as:
```
public interface PaymentService {
    void processPayment(double amount);
}
```

```
@Component("creditCardPayment")
public class CreditCardPaymentService implements PaymentService{
  @Override
  public void processPayment(double amount){
    System.out.println("Payment processed by credit card: "+amount);
 }
}
```

```
@Component("paypalPayment")
public class PayPalPaymentService implements PaymentService{
  @Override
  public void processPayment(double amount){
    System.out.println("Payment processed by Paypal: "+amount);
 }
}
```
**Usage Without @Qualifier:**
```
@Component
public class PaymentProcess{
  @Autowired
  private PaymentService paymentService; // Ambiguity: Which PaymentService to inject?
  
   public void pay(double amount) {
        paymentService.processPayment(amount);
    }
}
```
Output: gives error, which beasn should be called?

**Usage With @Qualifier:**
```
@Component
public class PaymentProcess{
  @Autowired
  @Qualifier("creditCardPayment") // Explicitly choose the CreditCardPaymentService
  private PaymentService paymentService; 
  
   public void pay(double amount) {
        paymentService.processPayment(amount);
    }
}
```
Output: Processing credit card payment of 100.0

**@Primary:** Primary annotation is used to specify the default bean to injected when multiple beans of the same type exist in the Spring container, and no specific qualifier is provided.

It helps resolve the ambiguity of which bean to inject when using the @Autowired annotation.

**How @Primary Solves the Problem**

The @Primary annotation marks one of the beans as the default bean. When no @Qualifier is used, Spring will inject the @Primary bean.

Example: Imagine you have multiple implementations of a service interface:

**Interface**
```
public interface PaymentService{
  void processPayment(double amount);
}
```
CreditCardPaymentService.java
```
@Component
public class CreditCardPaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of " + amount);
    }
}
```
PayPalPaymentService.java
```
@Component
@Primary // Default implementation: so if qualifier is not present then it will be injected bydefault:
public class PayPalPaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of " + amount);
    }
}
```
PaymentProcessor.java
```
@Component
public class PaymentProcessor {

    @Autowired
    private PaymentService paymentService; // Injects the `PayPalPaymentService` by default

    public void pay(double amount) {
        paymentService.processPayment(amount);
    }
}
```
Output: Processing PayPal payment of 100.0

## @ConditionalOnProperty
In Spring Boot, we can use the @ConditionalOnProperty annotation to conditionally register the beans based on the property value in the application.properties or application.yml file.

**@ConditionalOnProperty** generally contains 4 attributes:
- 1. name: for Basic Matching
- 2. havingValue: check **name** value is matched to **havingValue**, if matched then bean are registered.

    application.properties
     ```
     feature.x.enabled=true
     ```
     Your service class with the basic match will look like
     ```
     @Component
    @ConditionalOnProperty(name = "feature.x.enabled", havingValue = "true")
    public class FeatureXService {
        //... Your service methods
    }
    ```
     Here, FeatureXService is instantiated only when feature.x.enabled is set to true.
  
- 3. matchIfMissing: Imagine you want a bean to be instantiated by default(like name value and havingValue is not matched, but we want it creates by default) unless explicitly turned off:
    ```
     feature.y.enabled=false
    ```
    ```
    @Component
  @ConditionalOnProperty(name = "feature.y.enabled", matchIfMissing = true)
  public class FeatureYService {
      //... Your service methods
  }
    ```
In this scenario, if feature.y.enabled is not defined in the properties file, the bean will still be created due to matchIfMissing = true. However, in our example property, since it's explicitly set to false, the bean will not be created.

- 4. prefix: For a configuration with a shared prefix, e.g.,
  
     ```
     app.feature.z.enabled=true
     ```
     ```
     @Component
    @ConditionalOnProperty(prefix = "app.feature", name = "z.enabled", havingValue = "true")
    public class FeatureZService {
        //... Your service methods
    }
     ```
**Example: Real-world Scenarios**

Imagine a system that integrates with various third-party services, like payment gateways, message brokers, or notification services. Depending on the deployment environment or client preferences, certain services might be enabled or disabled. Using the @ConditionalOnProperty annotation, developers can easily turn on or off these services based on the configuration properties.

**Conditional Configuration for Database Beans**

Imagine you have two types of databases, and you wish to switch between them based on a property:
```
database.type=MYSQL
```
You can conditionally load beans as:
```
@Configuration
public class DatabaseConfig {

    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "MYSQL")
    public DatabaseService mySqlDatabaseService() {
        return new MySqlDatabaseService();
    }

    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "MONGODB")
    public DatabaseService mongoDatabaseService() {
        return new MongoDatabaseService();
    }
}
```
