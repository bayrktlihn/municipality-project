package io.bayrktlihn.component;

import io.bayrktlihn.annotation.MyAnno;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationStartupAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.io.File;
import java.util.List;

//@Component
@Slf4j
public class MyComponent implements DisposableBean, InitializingBean, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, ApplicationContextAware, EnvironmentAware, EmbeddedValueResolverAware, ResourceLoaderAware, ApplicationEventPublisherAware, MessageSourceAware, ApplicationStartupAware {


    @Autowired
    private List<Interface> interfaceList;
    private String firstName;

//    @Autowired
    @Qualifier("lastName")
    private String lastName;

    @Value("${person.gender}")
    private String gender;

    private Environment environment;


    public void run(){
        log.info("myComponent =====> run()");
//        throw new RuntimeException("run method throws exception");
    }

    @MyAnno(myValue = "denemeee")
    public void walk(){
        log.info("myComponent =====> run()");
    }

//    @Autowired
    public MyComponent(List<Interface> interfaceList) {
        this.interfaceList = interfaceList;
        log.info("myComponent ====> MyComponent(List<Interface> interfaceList) " + " "+this);
    }

    public MyComponent() {
        log.info("myComponent ====> MyComponent()");
    }

    @Override
    public void destroy() throws Exception {
        log.info("myComponent ====> destroy()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("myComponent ====> afterPropertiesSet()");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("myComponent ====> setBeanFactory(BeanFactory beanFactory)");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("myComponent ====> setBeanClassLoader(ClassLoader classLoader)");
    }

    @Override
    public void setBeanName(String name) {
        log.info("myComponent ====> setBeanName(String name)");
    }

    @PreDestroy
    void preDestroy(){
        log.info("myComponent ====> preDestroy()");
    }

    @PostConstruct
    void postConstruct(){
        log.info("myComponent ====> postConstruct()");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("myComponent ====> setApplicationContext(ApplicationContext applicationContext)");
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        log.info("myComponent ====> setEmbeddedValueResolver(StringValueResolver resolver)");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        log.info("myComponent ====> setEnvironment(Environment environment)" +" "+environment.getProperty("firstName") + " "+environment.getProperty("lastName"));
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        log.info("myComponent ====> setResourceLoader(ResourceLoader resourceLoader)");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.info("myComponent ====> setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        log.info("myComponent ====> setMessageSource(MessageSource messageSource)");
    }

    @Override
    public void setApplicationStartup(ApplicationStartup applicationStartup) {
        log.info("myComponent ====> setEmbeddedValueResolver(ApplicationStartup applicationStartup)");
    }

    public List<Interface> getInterfaceList() {
        return interfaceList;
    }

//    @Autowired
    public void setFirstName(@Qualifier("firstName") String firstName) {
        this.firstName = firstName;
        log.info("myComponent ====> setFirstName(String firstName)" + " "+this);

    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "MyComponent{" +
                "interfaceList=" + interfaceList +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
