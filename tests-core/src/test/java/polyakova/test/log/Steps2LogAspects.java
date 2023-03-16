package polyakova.test.log;

import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.util.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Logging of methods marked with an annotation "Step"
 *
 * @author Iuliia Poliakova
 */
@Aspect
public class Steps2LogAspects {

    private static final Logger log = LoggerFactory.getLogger(Steps2LogAspects.class);

    @Around("@annotation(io.qameta.allure.Step) && execution(* *(..))")
    public Object stepWrapper(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Step step = methodSignature.getMethod().getAnnotation(Step.class);
        String name = AspectUtils.getName(step.value(), joinPoint);
        List<Parameter> parameters = AspectUtils.getParameters(methodSignature, joinPoint.getArgs());
        StringBuilder parametersStr = new StringBuilder();
        for (Parameter parameter : parameters) {
            parametersStr.append(parameter.getName()).append('=').append(parameter.getValue()).append(", ");
        }
        if (parametersStr.length() > 2) {
            parametersStr.setLength(parametersStr.length() - 2);
        }
        try {
            long time = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            log.info("{}({}) {}ms", name, parametersStr, time);
            return result;
        } catch (Throwable e) {
            log.error("error ", e);
            throw e;
        }
    }
}
