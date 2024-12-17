package com.aucloud.aupay.operate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@Slf4j
@RestController
@RequestMapping("approve")
public class ApproveController {

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("exec")
    public void exec(String beanName, String methodName, Object... args) {
        try {
            // 1. 获取 Bean 实例
            Object bean = applicationContext.getBean(beanName);

            // 2. 获取 Bean 的 Class 类型
            Class<?> beanClass = bean.getClass();
            // 3. 获取方法 (方法名及参数类型)
            Method method;
            if (args != null && args.length > 0) {
                Class<?>[] paramTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    paramTypes[i] = args[i].getClass();
                }
                method = beanClass.getMethod(methodName, paramTypes);
            } else {
                method = beanClass.getMethod(methodName);
            }

            // 4. 反射调用方法
            Object result = method.invoke(bean, args);
            // 5. 输出结果
            log.info("invoke:{}", result);
        } catch (Exception e) {
            log.error("Error invoking method: {}", e.getMessage(), e);
        }
    }

}
