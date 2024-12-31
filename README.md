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

> - here suppose we have min pool size=3, max pool size=4 and Queue size=3
> - task1->Thread1
> - task2->Thread2
> - Now minimum Thread pool is fulled, now further tasks will goes into queue untill it full(here task3,task4,task5 goes into queue)
> - once it full, now new thread is create because we have max pool size = 4, and assign it to new task
> - task6->Thread3
> - task7->Thread4
> - now Threads reached to max pool size and all threads are busy, now if new task is comes, it will be rejected untill a Thread is free.


<img width="563" alt="image" src="https://github.com/user-attachments/assets/5b51ef28-f766-4a69-8dad-368261b94595" />

Now Understand **Async Annotation**

- 1. Used to mark method that should run asynchronously.
  2. Runs in a new Thread, without blocking main Thread.

<img width="662" alt="image" src="https://github.com/user-attachments/assets/a190a5f8-b77a-4c3b-ac7c-b9b2a55419ce" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/878824eb-3424-4432-8fe6-db76aa82e69f" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/2c7c64a3-3b3c-4d6f-b200-ed1962eb0517" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/67fe8259-d46a-4c36-8d29-474c033d4f0b" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/2922101f-370d-401a-83b6-316fe9681d1e" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/709eaa3a-0072-4dc1-84f8-962e2fe2cf64" />

<img width="662" alt="image" src="https://github.com/user-attachments/assets/c0b759d2-c2a7-4194-a270-17aa00d7a8ba" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/7ce2f060-3abd-4cd4-88b3-08357d20d6a0" />

<img width="662" alt="image" src="https://github.com/user-attachments/assets/2e83cddc-9ee8-48d9-8af8-7e6e91f07758" />

So That's why never used default configuration of Thread pool.
<img width="662" alt="image" src="https://github.com/user-attachments/assets/4b8035df-e265-46fd-af60-53db7696e346" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/924177f4-efab-4c1d-85b1-1a51e1f3587d" />

<img width="662" alt="image" src="https://github.com/user-attachments/assets/fd1959d5-83a2-4025-b909-9699c0b3aa7f" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/cf9eaf24-e348-4de6-920a-f7dce3136ad4" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/0d61206e-38ed-436b-ad41-be8f739cae3c" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/f79faee6-0b86-4895-a7ae-0c3aba783910" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/dcfb56a9-caa6-4014-bd2d-df1ada17883e" />

<img width="662" alt="image" src="https://github.com/user-attachments/assets/9959a431-e232-442b-b6c9-0b98f827b24d" />

<img width="662" alt="image" src="https://github.com/user-attachments/assets/14c69338-11d8-4dc5-bc8f-3e5f57bf834f" />

<img width="662" alt="image" src="https://github.com/user-attachments/assets/35dd0ab6-554b-4710-85fe-589f0fc69f7d" />
So whenever use ThreadPoolExecutor(java) need to use pool name otherwise it will pick SimpleAsyncTaskExecuter or if we dont want to specify name we need to use ThreadPoolTaskExecutor.

SimpleAsyncTaskExecuter is not recommeded.

<img width="662" alt="image" src="https://github.com/user-attachments/assets/0c752541-d8b0-4944-898b-68016b0b3f44" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/298b1ad5-5750-4242-81e3-f212d1d8e078" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/ac6f7093-ab03-425a-bdbd-3bc3ce9535fe" />


<img width="662" alt="image" src="https://github.com/user-attachments/assets/d08e67af-ce9f-43a2-8647-729495109dcf" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/32b6fde8-e984-467f-8f4b-5bb1541044e7" /> This is wrong way of doing it, correct way is, @Async method should be in different class and should be public. becuase here AOP is involved, and AOP wants both conditions.

<img width="662" alt="image" src="https://github.com/user-attachments/assets/0451fa43-db99-4d06-9225-a09ed89c3990" />
means suppose, if something wrong happens in updateUser method, it will be rollback but updateUserBalanace would not roll back becuase it is in a new Thread. and Transaction context is not transfer to a new thread.
<img width="662" alt="image" src="https://github.com/user-attachments/assets/f856b04b-0ba5-4c58-b1bc-c15df33b2dc9" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/5a0d1e80-88a8-4c37-8df7-a3e2021c5f31" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/de4c0a38-f91b-4780-bfac-c210677977d1" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/586d0bae-14c4-492d-a0eb-b90596e0908e" />
<img width="662" alt="image" src="https://github.com/user-attachments/assets/628286b3-79aa-466d-aff0-514dddd3ca1e" />

now, Future has been decrepted. CompletableFuture is enhanced version of this(Java 8).
<img width="662" alt="image" src="https://github.com/user-attachments/assets/19cef066-6b24-4654-a8ce-74d0ecd1ecfe" />

```
			                               Exception Handling
								|
				|------------------------------------------------------------------|
			Method which has Return Type					Method which do not have return type

```
<img width="550" alt="image" src="https://github.com/user-attachments/assets/9da3f791-744f-4ecc-8992-e3a7a869c706" />
<img width="550" alt="image" src="https://github.com/user-attachments/assets/e2b73244-d619-4287-a959-4466e78c1f48" />
so, How to handle it??

we have 2 options to handle this.

<img width="550" alt="image" src="https://github.com/user-attachments/assets/516c6d04-b48e-41ab-97d6-acfdb31ce685" />
<img width="550" alt="image" src="https://github.com/user-attachments/assets/01287496-0129-4e69-8766-396fa870011c" />

but how we did this?

Because spring framework have code, we have just override it.
<img width="550" alt="image" src="https://github.com/user-attachments/assets/dfa0c29e-e078-4019-8084-f94a39ca22ab" />
<img width="550" alt="image" src="https://github.com/user-attachments/assets/718a77d8-51b0-48f9-8528-c0a4571efbaa" />

## Spring AOP
Spring AOP (Aspect-Oriented Programming) allows you to separate cross-cutting concerns (aspects) from the core business logic of an application.

In AOP, an "aspect" is a concern that cuts across multiple methods or classes, such as logging or transaction management. AOP allows you to define such concerns in separate classes, without modifying the business logic code.

**Key concepts in Spring AOP:**

- **Aspect:** A module that encapsulates a cross-cutting concern. It contains advice and pointcuts.
- **Joinpoint:** A point in the execution of the program where an aspect can be applied, typically a method execution.
- **Advice:** The action taken by an aspect at a specific joinpoint. Types of advice include before, after, around, throwing, and finally, please take a look below.
- **Pointcut:** A predicate that matches joinpoints. It defines when and where an advice should be applied.
- **Weaving:** The process of applying aspects to a target object. Weaving can be done at compile-time, load-time, or runtime.


**Advice Types Below**

- **Before Advice (@Before):** These advices runs before the execution of join point methods. 
- **After (finally) Advice (@After):** An advice that gets executed after the join point method finishes executing, whether normally or by throwing an exception. 
- **After Returning Advice (@AfterReturning)**: Sometimes we want advice methods to execute only if the join point method executes normally. 
- **After Throwing Advice (@AfterThrowing):** This advice gets executed only when join point method throws exception, we can use it to rollback the transaction 
       declaratively.
- **Around Advice (@Around):** This is the most important and powerful advice. This advice surrounds the join point method and we can also choose whether to execute the join point method or not.

so, How AOP Intercept, see below Chart and look hoe proxy is involved
<img width="467" alt="image" src="https://github.com/user-attachments/assets/7a257885-a853-4af0-a881-ba24f113b173" />

There are 2 types of proxies: 

- **1. JDK proxie:** Spring AOP defaults to using standard JDK dynamic proxies for AOP proxies. This enables any interface (or set of interfaces) to be proxied.(In simple work for Interfaces)
- **2. CGLIB proxie:** Spring AOP can also use CGLIB proxies. This is necessary to proxy classes rather than interfaces. By default, CGLIB is used if a business object does not implement an interface.
- 
**Example:**

Suppose we have an class Alien which contains some moethods, in which a method to get All Aliens, below
```
@GetMapping("/aliens")
public List<Aliens>getAliens(){
	System.out.println("Start fetching to all aliens"); //loggin statement, which is not related to bussiness logic. so insted of here we can handle this into log using AOP
	List<Aliens> aliens = repo.findAll(); // buissness logic
	return aliens;
}
```
LoggingAspect.java
```
@Component
@Aspect
public class LoggingAspect{
	@Before("execution(* com.example.controller.HomeController.getAliens(..))") //(..) -> means, any type argument and it's call before the method call
	public void log(){
		System.out.println("Start fetching to all aliens");
	}

	@After("execution(* com.example.controller.HomeController.getAliens(..))") // call After the method call, wheter it gives result or exception
	public void log(){
		System.out.println("completed...");
	}
	// we can intercept accordingly, exaple, @Before("execution(* com.example.controller.HomeController.*(..))")--> now it will be call for all methods inside HomeController

	@Around("execution(* com.example.controller.HomeController.getAliens(..))") // we call before and after of a method
	public Object log(ProceedingJoinPoint joinPoint) thorws Throwable{
		System.out.println("started....");

		//method->jointpoint call
		Object result = joinPoint.proceed(); // here jointpoint method will be call after that below log work
		System.out.println("Completed....");
		return result;
	}
	
	@AfterReturning("execution(* com.example.controller.HomeController.getAliens(..))") // call After method return correclty
	public void log(){
		System.out.println("call...");
	}
	@AfterThrowing("execution(* com.example.controller.HomeController.getAliens(..))") // call After method return Exception
	public void log(){
		System.out.println("call...");
	}
}
```
**PointCut Designator:**

**1. execution**
Matches method execution joinpoints based on method signature.
```
execution(* com.example.service.*.*(..))
```
Matches any method in the com.example.service package.

**2. within**
```
within(com.example.service..*)
```
Matches all methods within classes in the com.example.service package and its sub-packages.

**3. this**
Matches joinpoints where the target object is of a specific type.
```
this(com.example.MyService)
```
Matches joinpoints in the proxy implementing MyService.

**4. target**
Matches joinpoints where the target object is of a specific type (actual implementation class).
```
target(com.example.MyService)
```
Matches joinpoints in the target object of type MyService.

**5. args**
Matches joinpoints where method arguments match specified types.
```
args(java.lang.String, int)
```
Matches methods with arguments of type String and int.

**6.@annotation**
Matches joinpoints where the target method is annotated with a specific annotation.
```
@annotation(com.example.MyAnnotation)
```
Matches methods annotated with @MyAnnotation.

**7.@within**
Matches joinpoints where the class is annotated with a specific annotation.
```
@within(com.example.MyAnnotation)
```
Matches methods in classes annotated with @MyAnnotation.

**8. @target**
Matches joinpoints where the target class is annotated with a specific annotation.
```
@target(com.example.MyAnnotation)
```

By combining these designators, you can create powerful and flexible rules to specify where your advice should be applied.

## Transaction

![IMG-20250101-WA0003](https://github.com/user-attachments/assets/5f87cd9f-1fb0-4165-bf65-85248aecdfa3)

> Note: Remaining Handwritten Notes below,(end of the Transaction) please look at once:


**How to Perform Transaction Management in Spring Boot?**

Transaction management in spring boot is super easy using the **Transaction** annotation.

Two ways we can perform a transaction in spring boot are:

- 1.Declarative
- 2.Programmatic

Both work, but declarative transactions are simple to work with.

**Declarative Transaction**

Declarative transactions involve separating the transactional logic from the business logic of your application. You do not have to write explicit code to handle transactions. Instead, you can use annotations  or XML configuration to declare which methods should be executed within a transaction.

we can apply **@Transactional** annotation on method level or class level.
> Example
```
@Transactional
public void updateUserData(Long userId, String data) {
    User user = userRepository.findById(userId);
    user.setData(data);
    userRepository.save(user);
}
```
In this example, the updateUserData() method is annotated with @Transactional, which means that the data update operation will be executed within a transaction. If any exceptions are thrown during the execution of this method, the transaction will be rolled back, and the data will not be saved to the database.

Overall, declarative transactions make it easier to write transactional code in your Spring Boot application and help to ensure that your data is consistent and properly managed.

and best example is sending money to another account should be transactional.

**Programmatic Transaction**

The Spring Framework provides two means of programmatic transaction management, by using:

The **TransactionTemplate**(Recommended) or TransactionalOperator.

A **TransactionManager** implementation directly.

**Using the TransactionTemplate**

It uses a callback approach. TransactionTemplate resembles the next example. You, as an application developer, can write a TransactionCallback implementation (typically expressed as an anonymous inner class) that contains the code that you need to run in the context of a transaction. You can then pass an instance of your custom TransactionCallback to the execute(..) method exposed on the TransactionTemplate. The following example shows how to do so:

```
public class SimpleService implements Service {

	// single TransactionTemplate shared amongst all methods in this instance
	private final TransactionTemplate transactionTemplate;

	// use constructor-injection to supply the PlatformTransactionManager
	public SimpleService(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager); //PlatformTransactionManager: PlatformTransactionManager is a core Spring interface that provides programmatic control over transaction management in a Spring application. It abstracts the underlying transaction management mechanism, such as JPA, JDBC, Hibernate, or JTA,
	}
	
	public Object someServiceMethod() {
		return transactionTemplate.execute(new TransactionCallback() {
			// the code in this method runs in a transactional context
			public Object doInTransaction(TransactionStatus status) {
				updateOperation1();
				return resultOfUpdateOperation2();
			}
		});
	}
}
```
If there is no return value, you can use the convenient TransactionCallbackWithoutResult class with an anonymous class, as follows:
```
transactionTemplate.execute(new TransactionCallbackWithoutResult() {
	protected void doInTransactionWithoutResult(TransactionStatus status) {
		updateOperation1();
		updateOperation2();
	}
});
```
**Spring Transaction Abstractions**

Spring Framework's declarative transaction support is enabled via AOP proxies. Whenever the caller invokes a method or class with @Transactional annotation, it first invokes a proxy that uses a TransactionInterceptor in conjunction with an appropriate PlatformTransactionManager implementation to drive transactions around method invocations.

Conceptually, calling a method on a transactional proxy looks like this.
<img width="395" alt="image" src="https://github.com/user-attachments/assets/c05d746c-e89a-417b-8a69-ede6e8819309" />

**What are @Transactional Propagation**

Transaction propagation determines how a new transaction is started when a method marked with the @Transactional annotation is called from within another @Transactional method. 

> Spring Transaction Propagation Types

Spring provides multiple propagation options to control how transactions interact when one transactional method calls another.
| **Propagation Type**     | **Description**                                                                                           |
|---------------------------|-----------------------------------------------------------------------------------------------------------|
| `REQUIRED`               | This is the default propagation level. If a transaction already exists when the method is called, that transaction will be used. Otherwise, a new transaction will be started.                   |
| `REQUIRES_NEW`           | This propagation level always starts a new transaction, regardless of whether a transaction already exists. The existing transaction, if any, will be suspended until the new transaction is completed.                      |
| `SUPPORTS`               | This propagation level will use an existing transaction if one exists, but it will not start a new transaction if none exists.                   |
| `NOT_SUPPORTED`          | This propagation level will never start a new transaction and will run the method without a transaction. If a transaction already exists, it will be suspended until the method has been completed.  |
| `MANDATORY`              | Throws an exception if called without an existing transaction.                                       |
| `NEVER`                  | Does not support transactions; throws an exception if a transaction exists.                              |
| `NESTED`                 | Executes within a nested transaction if a current transaction exists; otherwise behaves like `REQUIRED`. |


> Examples of Propagation

**1. `REQUIRED`**
```
@Transactional(propagation = Propagation.REQUIRED)
public void methodA() {
    // Joins the current transaction or creates a new one
}

// all used similaryly
// @Transactional(propagation = Propagation.REQUIRES_NEW)
//@Transactional(propagation = Propagation.NESTED)
// @Transactional(propagation = Propagation.NOT_SUPPORTED)
```

**What are @Transactional Isolation Levels Used for?**

it determines how much a transaction can see and be affected by changes made by other concurrent transactions. In Spring Boot, transaction isolation is defined using the isolation attribute of the @Transactional annotation.

generally 4 types of Isolation level: 

**1.ISOLATION_READ_UNCOMMITTED:**
This isolation level allows a transaction to read data written by other transactions that have not yet been committed. This can lead to dirty reads, where a transaction reads data later rolled back by another transaction.

**2.ISOLATION_READ_COMMITTED (Default isolation level):**
This isolation level ensures that a transaction can only read data that other transactions have committed. This prevents dirty reads but does not prevent non-repeatable reads or phantom reads.

**3.ISOLATION_REPEATABLE_READ:**
This isolation level ensures that a transaction will always read the same data, even if other transactions are modifying that data concurrently. This prevents dirty reads and non-repeatable reads but does not prevent phantom reads.

**4.ISOLATION_SERIALIZABLE:**
This isolation level provides the highest isolation level, ensuring that transactions are executed in a serializable order. This prevents dirty reads, non-repeatable reads, and phantom reads.

When you declare your transactional method, you can specify the isolation level you want to use by setting the isolation attribute of the @Transactional annotation. For example:
```
@Transactional(isolation = Isolation.ISOLATION_REPEATABLE_READ)
public void doSomething() {
// transactional method code
}
```

**Readonly vs Read-Write Transaction**

The readOnly attribute of the @Transactional annotation in Spring can indicate that a transaction should be read-only, meaning that it will not attempt to modify the database in any way. This can be useful for optimizing the performance of your application because read-only transactions can often be executed more quickly than transactions that modify the database.
```
@Transactional(readOnly = true)
public void doSomething() {
// transactional method code
}
```

![IMG-20250101-WA0002](https://github.com/user-attachments/assets/ae8f763a-587a-44f6-b168-e572a1b0cb0d)
![IMG-20250101-WA0004](https://github.com/user-attachments/assets/61cf8445-4be2-4857-ba72-620656176e4f)
![IMG-20250101-WA0005](https://github.com/user-attachments/assets/55c5e79d-475d-427b-b824-c0719d73d7a4)

![IMG-20250101-WA0006](https://github.com/user-attachments/assets/6c0a5848-f313-408b-b4db-9a8d23de9094)
