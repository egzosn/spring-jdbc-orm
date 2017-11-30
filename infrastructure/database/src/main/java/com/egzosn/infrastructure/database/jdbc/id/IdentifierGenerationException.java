package com.egzosn.infrastructure.database.jdbc.id;

/**
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/29
 */
public class IdentifierGenerationException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public IdentifierGenerationException(String message) {
        super(message);
    }
}
