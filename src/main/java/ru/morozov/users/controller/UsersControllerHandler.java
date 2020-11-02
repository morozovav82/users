package ru.morozov.users.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * Обработчик, вызываемый перед выполнением основного контроллера
 */
@Component
public class UsersControllerHandler extends HandlerInterceptorAdapter {

    private ThreadLocal<Long> startTimer = new ThreadLocal<>();

    private Counter requestCounter;
    private Timer requestTimer;

    @Autowired
    private MeterRegistry meterRegistry;

    @PostConstruct void init() {
        requestCounter = Counter.builder("app_requestCounter")
                .tag("controller", "users")
                .register(meterRegistry);

        requestTimer = Timer.builder("app_requestTimer")
                .tag("controller", "users")
                .publishPercentiles(0.5, 0.95, 0.99, 1)
                .register(meterRegistry);
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTimer.set(System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        requestCounter.increment();
        requestTimer.record(System.currentTimeMillis() - startTimer.get(), TimeUnit.MILLISECONDS);
    }
}
