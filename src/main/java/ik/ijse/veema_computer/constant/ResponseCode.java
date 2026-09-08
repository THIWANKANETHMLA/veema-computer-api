package ik.ijse.veema_computer.constant;

public final class ResponseCode {

    public static final String SUCCESS = "SUCCESS";
    public static final String CREATED = "RESOURCE_CREATED";
    public static final String UPDATED = "RESOURCE_UPDATED";
    public static final String DELETED = "RESOURCE_DELETED";

    public static final String VALIDATION_FAILED = "VALIDATION_FAILED";
    public static final String NOT_FOUND = "RESOURCE_NOT_FOUND";
    public static final String CONFLICT = "RESOURCE_CONFLICT";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String INTERNAL_ERROR = "INTERNAL_SERVER_ERROR";

    private ResponseCode() {
    }
}
