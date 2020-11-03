package ru.morozov.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.morozov.users.controller.UsersControllerHandler;

/**
 * Конфигурация MVC
 */
@Deprecated
//@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * Создание обработчика, вызываемый перед выполнением основного контроллера
     * @return обработчик
     */
    @Bean
    public UsersControllerHandler getUsersControllerHandler() {
        return new UsersControllerHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUsersControllerHandler()).addPathPatterns("/user/**");
    }
}
