package mridulbagri.com.medicalapp.model;


public class User {
 
    private String username, gender;

    private String firstname, lastname , allergies , metals , dateofbirth;


    private int id;



    public User(int anInt, String username, String firstname, String lastname, String allergies, String metals, String dateofbirth, String gender) {
        this.id = anInt;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.allergies = allergies;
        this.metals = metals;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getMetals() {
        return metals;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }
 

    public String getGender() {
        return gender;
    }
}