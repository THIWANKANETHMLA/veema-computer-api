package ik.ijse.veema_computer.constant;

public final class ResponseMessage {

    public static final String OPERATION_SUCCESS =
            "Operation completed successfully";

    public static final String RESOURCE_CREATED =
            "Resource created successfully";

    public static final String RESOURCE_UPDATED =
            "Resource updated successfully";

    public static final String RESOURCE_DELETED =
            "Resource deleted successfully";

    public static final String APPLICATION_RUNNING =
            "Veema Computer API is running";

    public static final String VALIDATION_FAILED =
            "Request validation failed";

    public static final String INTERNAL_ERROR =
            "An unexpected error occurred";

    private ResponseMessage() {
    }
}
