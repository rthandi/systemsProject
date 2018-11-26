package classes;

public class Department {

    private String departmentCode;
    private String departmentName;

    //CONSTRUCTOR
    public Department(String departmentCodeInput, String departmentNameInput){
        departmentCode = departmentCodeInput;
        departmentName = departmentNameInput;
    }

    //GETTERS
    public String getDepartmentCode() {
        return departmentCode;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public String toString() {
        return departmentName;
    }
}
