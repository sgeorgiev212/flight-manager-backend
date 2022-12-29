package com.example.demo.util;

public class ServiceUtil {

    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    public static final String CREATED_STATUS = "CREATED";

    public static final String ACTIVE_FLIGHT_STATUS = "ACTIVE";

    public static final String CANCELED_FLIGHT_STATUS = "CANCELED";

    public static final String REGISTERED_TRAVEL_AGENCY_STATUS = "REGISTERED";

    public static final String NOT_DEFINED = "Not defined";

    public static void validateReviewText(String text) {
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Review text must not be empty.");
        }
    }

}
