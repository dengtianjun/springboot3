package com.example.demo.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HelloController}.
 */
@Generated
public class HelloController__BeanDefinitions {
  /**
   * Get the bean definition for 'helloController'.
   */
  public static BeanDefinition getHelloControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HelloController.class);
    beanDefinition.setInstanceSupplier(HelloController::new);
    return beanDefinition;
  }
}
