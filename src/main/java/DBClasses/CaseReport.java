package DBClasses;

public class CaseReport {


    public int patientid;
    public int doctorid;
    public String casenotes;
    public String datetime;
    public boolean chronic_condition;

    public CaseReport(int patientid, int doctorid, String casenotes, String datetime, boolean chronic_condition){
        this.patientid=patientid;
        this.doctorid=doctorid;
        this.casenotes=casenotes;
        this.datetime=datetime;
        this.chronic_condition=chronic_condition;
    }


}
