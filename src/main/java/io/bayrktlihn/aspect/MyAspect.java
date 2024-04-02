package io.bayrktlihn.aspect;

import io.bayrktlihn.annotation.MyAnno;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class MyAspect {

    @Pointcut("execution(public * io.bayrktlihn.component.MyComponent.run())")
    public void ee() {
        System.out.println("xxxxxxxxxx");
    }

    @Before("ee()")
    public void dd(){
        log.info("before run method");
    }

    @Around("@annotation(myAnno)")
    public void ff( ProceedingJoinPoint joinPoint, MyAnno myAnno){
        log.info(myAnno.myValue());
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
