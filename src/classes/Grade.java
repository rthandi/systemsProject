package classes;

public class Grade {
    private int grade;
    private int credits;

    public Grade(int gradeInput, int creditsInput){
        grade = gradeInput;
        credits = creditsInput;
    }

    public int getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }
}
