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
## Profiles
Spring Boot Profiles provide developers with a powerful way to manage environment-specific configurations. Whether you’re in development, testing, or production. This feature simplifies deployment scenarios and enhances flexibility.

**Without profiling, developers need to:**
- Manually switch configurations for each environment.
- Use conditional logic within the code, which is error-prone and unmanageable.

**You can define profiles in:**
- Property files (e.g., application-dev.properties)
- YAML files (e.g., application.yml)

By default, Spring Boot activates the default profile(application.properties) if no other exist.

**Defining profiles**

Profiles can be defined using property files(application.properties) or YAML files. For example, you might have an **application-dev.properties** file for your development environment and an **application-prod.properties** file for your production environment. To activate a profile, you can set the **spring.profiles.active** property in your **application.properties** file:
```
spring.profile.active=dev
```
This will activate the dev profile, and Spring Boot will load the **application-dev.properties.**

**Note:** When you define the “spring.profiles.active” property in your “application.properties” file, Spring Boot will still load that file as well as the properties file specific to the active profile. This means that any properties defined in the “application.properties” file will be overridden by properties defined in the active profile’s properties file if they have the same key.

Example:
> Create a **application.properties** file in your src/main/resources directory, containing the **default database** configuration:
```
spring.datasource.url=jdbc:mysql://localhost:3306/myapp
spring.datasource.username=myappuser
spring.datasource.password=myapppassword
```

> Add another properties file named **application-dev.properties** in the same directory, containing the configuration for the **development database**:

```
spring.datasource.url=jdbc:mysql://localhost:3306/myapp_dev
spring.datasource.username=myappuser_dev
spring.datasource.password=myapppassword_dev
```

> Create a third properties file named **application-prod.properties** in the same directory, containing the configuration for the **production database**:
```
spring.datasource.url=jdbc:mysql://localhost:3306/myapp_prod
spring.datasource.username=myappuser_prod
spring.datasource.password=myapppassword_prod
```
> To use specific profile, In your application.properties file, add the following line to indicate which profiles should be active:

```
spring.profiles.active=dev
```
> Now, using this, my **application-dev.properties** configuration will be used, and is same key exist in parent file **application.properties**, it will be override.

```
  public class DBConnection{
  @Value("${spring.datasource.url}")
  String username;
  @Value("${spring.datasource.password}")
  String password;

  @PostConstruct
  public void init(){
    System.out.println("Username: "+username+" | "+"Password: "+password);
  }
}
```
> Output: Username: myappuser_dev | Password: myapppassword_dev

**But here we need to change application.property file manually, we can do it dynamically by 2 ways.**

- 1.if application is startup using command
- > run command: mvn spring-boot:run -Dspring-boot.run.profiles=prod
- 2.Add profiles in pom.xml file and run command: mvn spring-boot:run -Pprod
  ```
  <build>
  </build>
  <profiles>
    <profile>
      <id>local</id>
      <spring-boot.run.profiles>dev</spring-boot.run.profiles>
    </profile>
    <profile>
      <id>production</id>
      <spring-boot.run.profiles>prod</spring-boot.run.profiles>
    </profile>
  </profiles>
  ```
Note: IntelleJ give option **Active Profiles** in which we can give dev or prod.

**@Profile Annotation:** Using profile annotation we can tell spring boot, to create a bean only when perticular profile is set.

EmailService.java Interface
```
public interface EmailService {
    void sendEmail(String recipient, String message);
}
```
Developement implementation
```
@Component
@Profile("dev") // Bean is only active in the "dev" profile
public class DevEmailService implements EmailService {
    @Override
    public void sendEmail(String recipient, String message) {
        System.out.println("Logging email: To=" + recipient + ", Message=" + message);
    }
}
```
Producton implementation
```
@Component
@Profile("prod") // Bean is only active in the "prod" profile
public class ProdEmailService implements EmailService {
    @Override
    public void sendEmail(String recipient, String message) {
        // Actual email sending logic
        System.out.println("Sending email: To=" + recipient + ", Message=" + message);
    }
}
```
Dependent Class
```
@Component
public class NotificationService{
  private final EmailService emailService;
  @Autowired
  public NotificationService(EmailService emailService){
    this.emailService = emailService;
  }
  public void notifyUser(String recipient, String message){
    emailService.sendEmail(recipient,message);
  }
}
```
**Setting Active Profile:**

- 1.For development:
  > spring.profiles.active=dev
  Now only **DevEmailService** bean will be create and call.
- 2.For Production
  > spring.profiles.active=prod
  Now only **ProdEmailService** bean will be create and call.

 Note: we can set profiles dynamically, like above using pom.xml or command line or through IDE at runtime.


## Exception Handling in Spring Boot: @ExceptionHandler & @ControllerAdvice
To Handle exception in convenient and effective manner and reduced code repeatation, we have used a centralized approach to handle all type of exception using ExceptionHandler and ControllerAdvice annotation.

- No need to use general try-catch approch

**Example:**
> ProductController.java
  ```
   @GetMapping("/product/{id}")
	 public ResponseEntity<?> getProductById(@PathVariable int id) {
	     Product product = productService.findProductById(id)
	                .orElseThrow(() -> new ProductNotFoundException("Product not found with this id: " + id));
	     return ResponseEntity.status(HttpStatus.FOUND).body(product);
	 }
  ```
because we have create a custom exception **ProductNotFoundException**, so let's create this in Exception>ProductNotFoundException.java
```
package com.security.exception;

public class ProductNotFoundException extends RuntimeException{
		
	public ProductNotFoundException(String message) {
		super(message);
	}
}
```
> create a GlobalExceptionHandler inside controller
```

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class) // whenever ProductNotFoundException thrown by any controller, it will detected here. 
	public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException exception){
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(),exception.getMessage(),"Product Not Found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<?> handleArrayIndexOutOfBoundException(ArrayIndexOutOfBoundsException exception){
		 ErrorResponse productNotFound = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "Product Not Found");
	     return new ResponseEntity<>(productNotFound, HttpStatus.NOT_FOUND);
	}
}
```
**@RestControllerAdvice** is a convenience annotation to creates a global exception handler for RESTful web services: 
- It's a combination of the @ControllerAdvice and @ResponseBody annotations 
- It's used to return a JSON or XML response in case of an exception 
- It allows you to implement logic once, instead of duplicating it across the app

**@ExceptionHandler** annotation to handle exceptions thrown by a specific controller method.

> model>ErrorResponse.java
```
public class ErrorResponse {
	private LocalDateTime timestamp;
	private String message;
    private String details;
//setter and getter...
```
## Asynchronous Programming in Spring Boot
To move forward, first we need to understand **what is ThreadPool?**

<img width="563" alt="image" src="https://github.com/user-attachments/assets/384b1980-15f6-4e9f-898d-35db56b53ee3" />
<img width="563" alt="image" src="https://github.com/user-attachments/assets/7b5e20fe-69b5-47b0-8d94-4b2e4263ca11" />

> here suppose we have min pool size=3, max pool size=4 and Queue size=3
> task1->Thread1
> task2->Thread2
> Now minimum Thread pool is fulled, now further tasks will goes into queue untill it full(here task3,task4,task5 goes into queue)
> once it full, now new thread is create because we have max pool size = 4, and assign it to new task
> task6->Thread3
> task7->Thread4
> now Threads reached to max pool size and all threads are busy, now if new task is comes, it will be rejected untill a Thread is free.
