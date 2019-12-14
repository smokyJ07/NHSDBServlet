package DBClasses;

import java.sql.Timestamp;
import java.util.Date;

public class CaseReport {


    public int patientid;
    public int doctorid;
    public String casenotes;
    public String datetime;
    public boolean chronic_condition;

    public CaseReport(int patientid, int doctor_id, String casenotes, boolean chronic_condition){
        this.patientid=patientid;
        this.doctorid=doctor_id;
        this.casenotes=casenotes;
        this.chronic_condition=chronic_condition;

    }

    public void updateDatetime(){
        //get current time when creating CaseReport
        Date date = new Date();
        long time = date.getTime();
        this.datetime=(new Timestamp(time)).toString();
    }


}
