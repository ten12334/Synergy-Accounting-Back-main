package edu.kennesaw.appdomain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kennesaw.appdomain.types.UserType;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("userid")
    private Long userid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private Date birthday;

    @Column(nullable = false)
    private Date joinDate;

    @Column
    private String address;

    @Column(nullable = false)
    private String verificationCode;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isVerified;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isActive;

    @Column(nullable = false)
    private int failedLoginAttempts;

    @Column
    private Date tempLeaveStart;

    @Column
    private Date tempLeaveEnd;

    @Column
    private Date lastPasswordReset;

    @Column(nullable = false)
    private String emailPassword;

    @ElementCollection
    @CollectionTable(name = "user_old_passwords", joinColumns = @JoinColumn(name = "userid"))
    @Column(name = "old_passwords")
    private Set<String> oldPasswords = new HashSet<>();

    public User() {
        Random ran = new Random();
        verificationCode = ran.nextInt(999999) + "";
        userType = UserType.DEFAULT;
        setIsVerified(false);
        lastPasswordReset = new Date();
        emailPassword = generateRandomPassword();  // Generate email password on user creation
    }

    public void setUserid(Long id) {
        this.userid = id;
    }

    @JsonProperty("userid")
    public Long getUserid() { return userid; }

    public void setUserType(UserType userType) { this.userType = userType; }

    public UserType getUserType() {
        return userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setTempLeaveStart(Date tempLeaveStart) {
        this.tempLeaveStart = tempLeaveStart;
    }

    public Date getTempLeaveStart() {
        return tempLeaveStart;
    }

    public void setTempLeaveEnd(Date tempLeaveEnd) {
        this.tempLeaveEnd = tempLeaveEnd;
    }

    public Date getTempLeaveEnd() {
        return tempLeaveEnd;
    }

    /*public void setBirthMonth(Date birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthYear(Date birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthYear() {
        return birthYear;
    }*/

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() { return verificationCode; }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    @JsonProperty("isVerified")
    public boolean isVerified() {
        return isVerified;
    }

    @JsonProperty("failedLoginAttempts")
    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @JsonProperty("failedLoginAttempts")
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Column(name = "old_passwords")
    public Set<String> getOldPasswords() {
        return oldPasswords;
    }

    @Column(name = "old_passwords")
    public void setOldPasswords(Set<String> oldPasswords) {
        this.oldPasswords = oldPasswords;
    }

    @Column(name = "old_passwords")
    public void addOldPassword(String oldPassword) {
        oldPasswords.add(oldPassword);
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Date lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    private String generateRandomPassword() {
        // Generate a random 12-character password
        int length = 12;
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(charSet.charAt(random.nextInt(charSet.length())));
        }
        return password.toString();
    }

}

