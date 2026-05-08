package br.gov.goiania.saude.raas.mock;

import org.mockito.ArgumentMatchers;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class InterceptorRegistryMock {

    private InterceptorRegistryMock() { }

    public static InterceptorRegistry registryComRegistration(HandlerInterceptor interceptor) {
        InterceptorRegistry registry = mock(InterceptorRegistry.class);
        InterceptorRegistration registration = mock(InterceptorRegistration.class);
        when(registry.addInterceptor(ArgumentMatchers.eq(interceptor))).thenReturn(registration);
        when(registration.addPathPatterns(ArgumentMatchers.any(String[].class))).thenReturn(registration);
        return registry;
    }
}
