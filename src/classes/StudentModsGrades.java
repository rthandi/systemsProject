package classes;

public class StudentModsGrades {
    String moduleID;
    String moduleName;
    int studentsMarks;
    int moduleCredits;

    StudentModsGrades(String modId, String modName, int mark, int credits){
        moduleID = modId;
        moduleName = modName;
        studentsMarks = mark;
        moduleCredits = credits;
    }

    public String getModuleID() {
        return moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getModuleCredits() {
        return moduleCredits;
    }

    public int getStudentsMarks() {
        return studentsMarks;
    }
}
