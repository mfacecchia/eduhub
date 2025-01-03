package com.feis.eduhub.backend.common.lib;

import com.feis.eduhub.backend.common.exceptions.ValidationException;

import io.javalin.http.Context;

/**
 * Utility class providing helper methods for handling Javalin context
 * operations such as IDs obtaining & auto-validation.
 * 
 * The methods in this class are designed to work with Javalin Context objects
 * and provide standardized ways to handle common operations while maintaining
 * consistent error handling.
 */
public class ContextUtil {

    /**
     * Extracts and validates a Long ID from the passed {@code Context}'s path
     * parameter.
     *
     * @param ctx       The Javalin context containing the path parameters
     * @param fieldName The name of the path parameter field to extract the ID from
     * @return The validated ID as a Long value
     * @throws ValidationException If the ID is invalid (not a number or less than
     *                             or equal to 0)
     */
    public static Long getIdFromPath(Context ctx, String fieldName) throws ValidationException {
        Long id;
        try {
            id = Long.valueOf(ctx.pathParam(fieldName));
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID", e);
        }
        if (id <= 0) {
            throw new ValidationException("Invalid ID");
        }
        return id;
    }
}
