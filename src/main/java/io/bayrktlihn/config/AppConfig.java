package io.bayrktlihn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bayrktlihn.component.MyComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan(basePackages = {"io.bayrktlihn.service","io.bayrktlihn.component", "io.bayrktlihn.config", "io.bayrktlihn.aspect", "io.bayrktlihn.repository"})
//@Lazy
//@ImportResource
@PropertySource("classpath:application.properties")
@Slf4j
@EnableAspectJAutoProxy
@Getter
@Setter
public class AppConfig {

    @Value("classpath:municipalities.json")
    Resource resourceFile;


    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public String firstName() {
        return "alihan";
    }

    @Bean
    public String lastName() {
        return "bayraktar";
    }


    @Bean

    public MyComponent myComponent(Environment environment) {
        MyComponent myComponent = new MyComponent();
        myComponent.setFirstName(environment.getProperty("firstName"));
        myComponent.setFirstName(environment.getProperty("lastName"));

        return myComponent;
//
//        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setTarget(myComponent);
////        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
////            if(method.getName().equals("run") && args.length == 0){
////                log.info(String.format("%s %s %s", method, args, target));
////            }
////        });
//
//        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
//            try {
//
//                invocation.getMethod().setAccessible(true);
//
//                if (invocation.getMethod().getName().equals("run") && invocation.getArguments().length == 0) {
//                    log.info(String.format("before run method invoke %s %s %s", invocation.getMethod(), Arrays.toString(invocation.getArguments()), invocation.getThis()));
//                }
//
//                Object invoke = invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
//
//
//                if (invocation.getMethod().getName().equals("run") && invocation.getArguments().length == 0) {
//                    log.info("after run method invoke");
//                }
//
//
//                return invoke;
//            } catch (InvocationTargetException e) {
//                log.info(e.getTargetException().getMessage());
//            }
//
//            return null;
//        });
//
//        return (MyComponent) proxyFactory.getProxy();

    }

}
