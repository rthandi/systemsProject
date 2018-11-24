public class User {
    // This is a class that will be used to represent the data of a user as an object once it has been fetched from the database

    // USER DATA

    private String registrationNumber;
    private String title;
    private String surname;
    private String otherNames;
    private String degreeId;
    private String email;
    private String tutorName;

    //CONSTRUCTOR
    public User (String registrationNumberInput, String titleInput, String surnameInput, String otherNamesInput, String degreeIdInput, String emailInput, String tutorNameInput){
        registrationNumber = registrationNumberInput;
        title = titleInput;
        surname = surnameInput;
        otherNames = otherNamesInput;
        degreeId = degreeIdInput;
        email = emailInput;
        tutorName = tutorNameInput;
    }

    //GETTER METHODS
    public String getDegreeId() {
        return degreeId;
    }

    public String getEmail() {
        return email;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getSurname() {
        return surname;
    }

    public String getTitle() {
        return title;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getFullName(){
        return (title + otherNames + surname);

    }

    //TODO: delete this at the end if not needed
    //SETTER METHODS (MAY NOT NEED SO NOT ADDING YET)
}