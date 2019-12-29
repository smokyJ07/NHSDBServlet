package DBClasses;

import java.sql.Timestamp;
import java.util.Date;

public class CaseReport {

    public int id;
    public int patient_id;
    public int doctor_id;
    public String casenotes;
    public String datetime;
    public boolean chronic_condition;

    public CaseReport(int patient_id, int doctor_id, String casenotes, boolean chronic_condition){
        this.patient_id=patient_id;
        this.doctor_id=doctor_id;
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
