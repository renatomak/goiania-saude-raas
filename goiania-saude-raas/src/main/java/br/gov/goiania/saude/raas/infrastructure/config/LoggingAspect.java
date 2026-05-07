package br.gov.goiania.saude.raas.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* br.gov.goiania.saude.raas.application.usecase..*(..)) || "
            + "execution(* br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence..*(..))")
    public Object logExecution(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        if (log.isInfoEnabled()) {
            log.info("[{}#{}] iniciado", className, methodName);
        }

        long inicio = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duracao = System.currentTimeMillis() - inicio;

        if (result instanceof Collection<?> collection) {
            if (log.isInfoEnabled()) {
                log.info("[{}#{}] concluido em {}ms | resultado: {} registros", className, methodName, duracao, collection.size());
            }
        } else if (log.isInfoEnabled()) {
            log.info("[{}#{}] concluido em {}ms", className, methodName, duracao);
        }

        return result;
    }
}
