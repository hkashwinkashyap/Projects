package model;

import java.io.Serializable;

/**
 * email class to handle the email address of a person
 */
public class Email implements Serializable {
    private String emailAddress;

    /**
     * constructor of the class
     *
     * @param emailAddress string value of the email address of a person
     */
    public Email(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * getter of the email
     *
     * @return string value of the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * setter of the email address
     *
     * @param emailAddress string of the email id
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * validates a given email id
     *
     * @param emailAddress string of the email address
     * @return int 1 if it is a valid email and 0 if it's not
     */
    private int validEmail(String emailAddress) {
        if (emailAddress.contains("@")) {
            String[] userName = emailAddress.split("@");
            if (userName.length == 2 && userName[0].length() > 0 && userName[1].length() > 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    /**
     * checks for a valid email and sets the email
     *
     * @param givenEmailAddress string value of the email address
     * @return string value of valid email's result which is int
     */
    public String validateEmail(String givenEmailAddress) {
        int result = validEmail(givenEmailAddress);
        if (result == 0) {
            setEmailAddress(givenEmailAddress);
            return givenEmailAddress;
        } else {
            return String.valueOf(result);
        }
    }
}
