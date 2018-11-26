package classes;

public class Degree {
    private String degreeId;
    private String degreeName;
    private String deptCode;

    //CONSTRUCTOR
    public Degree (String degreeIdInput, String degreeNameInput, String deptCodeInput){
        degreeId = degreeIdInput;
        degreeName = degreeNameInput;
        deptCode = deptCodeInput;
    }

    //GETTERS
    public String getDegreeId() {
        return degreeId;
    }
    public String getDegreeName() {
        return degreeName;
    }
    public String getDeptCode() {
        return deptCode;
    }

    @Override
    public String toString(){
        return degreeName;
    }
}
