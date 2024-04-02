package io.bayrktlihn.component;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class InterfaceBImpl implements Interface, Ordered, BeanNameAware {
    private String beanName;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


    @Override
    public String toString() {
        return beanName + " " + getOrder();
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }
}
