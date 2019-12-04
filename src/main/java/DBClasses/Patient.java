package DBClasses;

public class Patient {

    public String firstName;
    public String lastName;
    public String phoneNum;
    public String address;
    public String dob;
    public String email;


    public Patient(String firstName, String lastName, String phoneNum, String address, String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNum=phoneNum;
        this.address=address;
        this.email=email;
    }
}
