package com.feis.eduhub.backend.common.lib;

import java.util.Set;

import io.javalin.http.Handler;
import io.javalin.http.HandlerType;

/**
 * Utility class for executing Javalin {@code Handler} middleware methods based
 * on HTTP request method.
 */
public class MiddlewareExecutor {

    /**
     * Executes the given {@code middleware} only if the HTTP method of the current
     * request matches any of the specified methods (or if the {@code methodsSet}
     * set is empty or {@code null} (executes the mw for all methods)).
     *
     * @param methodsSet A {@code HandlerType} Set containing the HTTP methods
     *                   for which the middleware should be executed
     * @param middleware The middleware Handler to be executed
     * @return A Handler that conditionally executes the middleware based on the
     *         HTTP method
     * 
     * @see HandlerType
     * @see Handler
     */
    public static Handler executeOnMethod(Set<HandlerType> methodsSet, Handler middleware) {
        return (ctx) -> {
            if (methodsSet == null || methodsSet.isEmpty()
                    || methodsSet.contains(ctx.method())) {
                middleware.handle(ctx);
            }
        };
    }
}
