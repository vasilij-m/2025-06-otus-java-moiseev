package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

public class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    static CalculationService createLoggedCalculator() {
        InvocationHandler handler = new CalculationInvocationHandler(new Calculator());
        return (CalculationService)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[] {CalculationService.class}, handler);
    }

    static class CalculationInvocationHandler implements InvocationHandler {
        private final CalculationService calculator;
        private final Set<Method> loggedMethods;

        CalculationInvocationHandler(CalculationService calculator) {
            this.calculator = calculator;
            this.loggedMethods = findLoggedMethods(calculator);
        }

        private Set<Method> findLoggedMethods(CalculationService calculator) {
            return Arrays.stream(calculator.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method realMethod = calculator.getClass().getMethod(method.getName(), method.getParameterTypes());

            if (loggedMethods.contains(realMethod)) {
                printLogMessage(method, args);
            }

            return method.invoke(calculator, args);
        }

        private void printLogMessage(Method method, Object[] args) {
            StringBuilder message = new StringBuilder("executed method: " + method.getName());

            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    message.append(" param").append(i + 1).append(": ").append(args[i]);
                    if (i < args.length - 1) {
                        message.append(",");
                    }
                }
            }

            logger.info(message.toString());
        }
    }
}
