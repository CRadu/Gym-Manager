package gym.model;

/**
 * User class for user login information
 */
public class User {
    /**
     * Holds user's id
     */
    private Long id;
    /**
     * Holds user's name
     */
    private String name;
    /**
     * Holds user's log name
     */
    private String logname;
    /**
     * Holds user's password
     */
    private String password;

    public User(Long id, String name, String logname) {
        this.id = id;
        this.name = name;
        this.logname = logname;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogname() {
        return logname;
    }
}
