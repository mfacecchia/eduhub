package com.feis.eduhub.backend.common.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

/**
 * Main Javalin app builder. Represents the foundational part of the
 * application with endpoints registration and api endpoints versioning.
 * Uses {@link com.feis.eduhub.backend.common.interfaces.EndpointsRegister
 * EndpointsRegister} implementations to configure app available endpoints.
 * 
 * Default server port is {@code 8000} if not set during Object creation.
 * 
 * 
 * @see Javalin
 * @see EndpointsRegister
 */
public class AppBuilder {
    private static final Logger logger = LoggerFactory.getLogger("System");
    private final List<EndpointsRegister> appControllers;
    private final int port;
    private final Javalin app;

    public AppBuilder(List<EndpointsRegister> appControllers) {
        this.appControllers = appControllers;
        port = 8000;
        app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.allowHost("http://localhost:5173");
                    it.allowCredentials = true;
                });
            });
        });
    }

    public AppBuilder(List<EndpointsRegister> appControllers, int port) {
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException(
                    String.format("Invalid port number. Should be between 1 and 65535. Current value: %s", port));
        }
        this.appControllers = appControllers;
        this.port = port;
        this.app = Javalin.create();
    }

    public void start() {
        app.start(port);
    }

    /**
     * Configures the application endpoints by registering all available
     * controllers as well as a 404 endpoint not found handler and an exceptions
     * handler.
     * 
     * @param addTestEndpoint set to {@code true} if you want to add a sample
     *                        endpoint to check if everything's working fine,
     *                        othwewise {@code false}
     */
    public void configureEndpoints(boolean addTestEndpoint) {
        logger.info("Registering app endpoints");
        if (appControllers != null) {
            setAppEndpoints();
        }
        if (addTestEndpoint) {
            addTestEndpoint();
        }
        setEndpointNotFoundHandler();
        setCustomAppExceptionsHandler();
        setAppGenericExceptionsHandler();
        logger.info("Done");
    }

    private void setAppEndpoints() {
        for (EndpointsRegister controller : appControllers) {
            controller.registerEndpoints(app);
        }
    }

    /**
     * Adds all app controllers registered endpoints to the Javalin app.
     * 
     * @see EndpointsRegister
     */
    private void addTestEndpoint() {
        app.get("/hello-world", ((ctx) -> {
            logger.info("Hello, world!");
            ctx.status(200).result("Hello, world!");
        }));
    }

    /**
     * Default handler for 404 endpoint not found
     * 
     * @param app the Javalin app the endpoint will be appended to
     */
    private void setEndpointNotFoundHandler() {
        app.error(404, (ctx) -> {
            final int STATUS_CODE = 404;
            logger.info("Endpoint {} with {} method not found!",
                    ctx.path(), ctx.method());
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(STATUS_CODE)
                    .withMessage(String.format("Endpoint %s with %s method not found", ctx.path(), ctx.method()))
                    .build();
            ctx.status(STATUS_CODE).json(response);
        });
    }

    /**
     * Adds specific {@code AppException}s handling with a custom HTTP response and
     * message.
     * Sets a JSON-serialized response using
     * {@link com.feis.eduhub.backend.common.dto.ResponseDto Response}
     * DTO class
     * 
     * @see AppException
     * @see ResponseDto
     * @see ExceptionHandler
     */
    private void setCustomAppExceptionsHandler() {
        app.exception(AppException.class, (e, ctx) -> {
            final int STATUS_CODE = e.getStatusCode();
            // TODO: Move logging to a more specific class
            logger.info("------");
            logger.info("[INFO]");
            logger.info("------");
            logger.info("Custom exception thrown during execution: ", e);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(STATUS_CODE)
                    .withMessage(e.getMessage())
                    .build();
            ctx.status(STATUS_CODE).json(response);
        });
    }

    /**
     * Adds generic {@code Exception}s handling with a 400 BAD REQUEST HTTP response
     * using {@link com.feis.eduhub.backend.common.dto.ResponseDto Response} format.
     * 
     * @see ResponseDto
     * @see ExceptionHandler
     */
    private void setAppGenericExceptionsHandler() {
        app.exception(Exception.class, (e, ctx) -> {
            final int STATUS_CODE = 400;
            logger.info("------");
            logger.info("[INFO]");
            logger.info("------");
            logger.info("Unexpected Exception thrown during execution: ", e);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(STATUS_CODE)
                    .withMessage("An unexpected error occurred. Please try again later.")
                    .build();
            ctx.status(STATUS_CODE).json(response);
        });
    }
}
