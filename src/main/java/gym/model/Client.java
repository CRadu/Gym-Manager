package gym.model;

import java.util.Date;

/**
 * Client class contains:
 * - client parameters and constructors
 * - subscription type enum
 * - gender enum
 */
public class Client {
    /**
     * Holds client's id - generated automatically in database
     */
    private Long id;
    /**
     * Holds client's name
     */
    private String name;
    /**
     * Holds client's gender - form Gender enum
     */
    private Gender gender;
    /**
     * Holds client's phone number
     */
    private String phoneNumber;
    /**
     * Holds client's validation/pass expiring date
     */
    private Date validationExpireDate;
    /**
     * Holds client's type of pass
     */
    private SubscriptionType pass;
    /**
     * Holds client's registration in the system date
     */
    private Date registeredOn;

    /**
     * Client constructor with all parameters
     */
    public Client(Long id, String name, Gender gender, String phoneNumber, Date validationExpireDate, SubscriptionType pass, Date registered_on) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.validationExpireDate = validationExpireDate;
        this.pass = pass;
        this.registeredOn = registered_on;
    }

    /**
     * Client constructor overload - id parameter only
     */
    public Client(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Id: " + id + ", name: " + name + ", phone: " + phoneNumber + ", gender: " + gender + ", validate expire date: " + validationExpireDate + ", pass: " + pass + "registered on: " + registeredOn;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public SubscriptionType getPass() {
        return pass;
    }

    public Date getValidationExpireDate() {
        return validationExpireDate;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Client subscription type enum
     */
    public enum SubscriptionType {
        FITNESS,
        AEROBIC,
        FULL_ACCESS,
    }

    /**
     * Client gender enum
     */
    public enum Gender {
        MALE,
        FEMALE,
    }
}
