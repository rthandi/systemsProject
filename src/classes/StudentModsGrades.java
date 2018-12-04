package classes;

public class StudentModsGrades {
    String moduleID;
    String moduleName;
    int studentsMarks;
    int moduleCredits;

    public StudentModsGrades(String modId, String modName, int mark, int credits){
        moduleID = modId;
        moduleName = modName;
        studentsMarks = mark;
        moduleCredits = credits;
    }

    public StudentModsGrades(int mark, int credits){
        moduleID = null;
        moduleName = null;
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
