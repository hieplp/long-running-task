package com.hieplp.lrt.common.util;

import com.hieplp.lrt.common.annotation.*;
import com.hieplp.lrt.common.exception.CommonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;

public class ValidationTool {

    private static final Logger LOGGER = LogManager.getLogger(ValidationTool.class);

    /**
     * Check if email is valid.
     *
     * @param email email needs to check
     * @return true if email is valid. Otherwise, return false.
     */
    public static boolean isEmailValid(String email) {
        LOGGER.debug("Check if email: {} is valid", email);
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Check if email is valid. If not, throw validation exception.
     *
     * @param email email needs to check
     * @throws CommonException.ValidationException if email is not valid
     */
    public static void checkEmailValid(String email) {
        LOGGER.debug("Check if email: {} is valid", email);
        if (!isEmailValid(email)) {
            throw new CommonException.ValidationException("Email: " + email + " is not valid");
        }
    }

    /**
     * Check if phone is valid
     *
     * @param phone phone number needs to check
     * @return true if phone is valid. Otherwise, return false
     */
    public static boolean isPhoneValid(String phone) {
        LOGGER.debug("Check if phone: {} is valid", phone);
        return phone.matches("^[0-9]{10}$");
    }

    /**
     * Check if phone is valid. If not, throw validation exception
     *
     * @param phone phone number needs to check
     * @throws CommonException.ValidationException if phone is not valid
     */
    public static void checkPhoneValid(String phone) {
        LOGGER.debug("Check if phone: {} is valid", phone);
        if (!isPhoneValid(phone)) {
            throw new CommonException.ValidationException("Phone: " + phone + " is not valid");
        }
    }

    /**
     * Check if length of string is equal to given length
     *
     * @param str    string needs to check
     * @param length length
     * @param name   name of string
     * @throws CommonException.ValidationException if length of string is not equal to given length
     */
    public static void checkLength(String str, int length, String name) {
        LOGGER.debug("Check if length of {} is equal to {}", name, length);
        if (str == null) {
            throw new CommonException.ValidationException(name + " is null");
        }
        if (str.length() != length) {
            throw new CommonException.ValidationException(name + " length is not " + length);
        }
    }

    /**
     * Check if length of string is equal or greater than minLength
     *
     * @param str       string needs to check
     * @param minLength minimum length
     * @param name      name of string
     * @throws CommonException.ValidationException if length of string is less than minLength
     */
    public static void checkMinLength(String str, int minLength, String name) {
        LOGGER.debug("Check if length of {} is equal or greater than {}", name, minLength);
        if (str == null) {
            throw new CommonException.ValidationException(name + " is null");
        }
        if (str.length() < minLength) {
            throw new CommonException.ValidationException(name + " length is less than " + minLength);
        }
    }

    /**
     * Check if length of str is equal or less than maxLength
     *
     * @param str       string needs to check
     * @param maxLength maximum length
     * @param name      name of string
     * @throws CommonException.ValidationException if length of str is greater than maxLength
     */
    public static void checkMaxLength(String str, int maxLength, String name) {
        LOGGER.debug("Check if length of {} is equal or less than {}", name, maxLength);
        if (str == null) {
            throw new CommonException.ValidationException(name + " is null");
        }
        if (str.length() > maxLength) {
            throw new CommonException.ValidationException(name + " length is greater than " + maxLength);
        }
    }

    /**
     * Check if list of object is valid
     *
     * @param list list needs to check
     * @throws CommonException.ValidationException if list is not valid
     */
    public static void checkObjectListWithAnnotation(List<?> list) {
        LOGGER.debug("Check if list of object is valid");
        for (Object object : list) {
            checkObjectWithAnnotation(object);
        }
    }

    /**
     * Check if object is valid
     *
     * @param object object needs to check
     * @throws CommonException.ValidationException if object is not valid
     */
    public static void checkObjectWithAnnotation(Object object) {
        LOGGER.debug("Check if object is valid");
        try {
            if (object == null) {
                throw new CommonException.ValidationException("object is null");
            }

            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (field.isAnnotationPresent(Length.class)) {
                    Length length = field.getAnnotation(Length.class);
                    String str = (String) field.get(object);
                    checkLength(str, length.length(), field.getName());
                }

                if (field.isAnnotationPresent(MinLength.class)) {
                    MinLength minLength = field.getAnnotation(MinLength.class);
                    String str = (String) field.get(object);
                    checkMinLength(str, minLength.length(), field.getName());
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength maxLength = field.getAnnotation(MaxLength.class);
                    String str = (String) field.get(object);
                    checkMaxLength(str, maxLength.length(), field.getName());
                }

                if (field.isAnnotationPresent(Email.class)) {
                    String email = (String) field.get(object);
                    checkEmailValid(email);
                }

                if (field.isAnnotationPresent(Phone.class)) {
                    String phone = (String) field.get(object);
                    checkPhoneValid(phone);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CommonException.ValidationException(e.getMessage());
        }
    }

    /**
     * Check if all objects in list are not null
     *
     * @param objects list of object needs to check
     * @throws CommonException.ValidationException if one object is not null
     */
    public static void checkNotNull(Object... objects) {
        LOGGER.debug("Check if all objects in list are not null");
        for (Object object : objects) {
            if (object == null) {
                throw new CommonException.ValidationException("Object is null");
            }
        }
    }
}
