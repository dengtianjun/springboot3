package com.example.demo;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DemoApplication}.
 */
@Generated
public class DemoApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'demoApplication'.
   */
  public static BeanDefinition getDemoApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DemoApplication.class);
    beanDefinition.setInstanceSupplier(DemoApplication::new);
    return beanDefinition;
  }
}
