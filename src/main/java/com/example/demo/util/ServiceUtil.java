package com.example.demo.util;

public class ServiceUtil {

    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    public static final String CREATED_STATUS = "CREATED";

    public static final String ACTIVE_FLIGHT_STATUS = "ACTIVE";

    public static final String CANCELED_FLIGHT_STATUS = "CANCELED";

    public static final String REGISTERED_TRAVEL_AGENCY_STATUS = "REGISTERED";

    public static final String NOT_DEFINED = "Not defined";

    public static final String DEFAULT_IMAGE_URL =
            "https://media.istockphoto.com/id/1130884625/vector/user-member-vector-icon-for-ui-user-interface-or-profile-face-avatar-app-in-circle-design.jpg?s=612x612&w=0&k=20&c=1ky-gNHiS2iyLsUPQkxAtPBWH1BZt0PKBB1WBtxQJRE=";

    public static final String DEFAULT_AIRLINE_IMAGE_URL =
            "https://images.squarespace-cdn.com/content/v1/5b989c06e2ccd197e85cc797/1574639810264-E3IBGH4B9SDTC9K9VC7L/airline-digital-transformat.jpg";

    public static final String RESET_PASSWORD_LINK = "http://localhost:8080/resetPassword";

    public static void validateReviewText(String text) {
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Review text must not be empty.");
        }
    }

}
