package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum Semester {
    spring_start ("01-01"),
    spring_end ("04-30"),
    summer_start ("05-01"),
    summer_end ("08-31"),
    fall_start ("09-01"),
    fall_end ("12-31");
    final String date;
    Semester(String date){
        this.date = date;
    }
    public String getDetail(){
        return this.date;
    }
    public static String getDate(int year, Semester semester){
        return year + "-" + semester.getDetail();
    }
    public static String[] getCurrentSemester(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        Semester[] semesters = Semester.values();
        for (int i = 0; i < 3; i++) {
            try {
                Date sem_start = dateFormat.parse(semesters[i*2].getDetail());
                Date sem_end = dateFormat.parse(semesters[i*2+1].getDetail());
                Date current = dateFormat.parse(formattedDate);
                int compare1 = current.compareTo(sem_start);
                int compare2 = current.compareTo(sem_end);
                if (compare1 >= 0 && compare2 <= 0){
                    return new String[]{Calendar.getInstance().get(Calendar.YEAR) +"-"+ semesters[i*2].getDetail(), Calendar.getInstance().get(Calendar.YEAR) +"-"+  semesters[i*2+1].getDetail()};
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public static String getNextSemester(int year, String semester){
        if (semester.startsWith("spring")){
            return year + "-" + "summer";
        } else if (semester.startsWith("summer")){
            return year + "-" + "fall";
        } else if (semester.startsWith("fall")){
            return (year + 1) + "spring";
        }
        return null;
    }
    public static String getCurrentSemesterAsString(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        Semester[] semesters = Semester.values();
        for (int i = 0; i < 3; i++) {
            try {
                Date sem_start = dateFormat.parse(semesters[i*2].getDetail());
                Date sem_end = dateFormat.parse(semesters[i*2+1].getDetail());
                Date current = dateFormat.parse(formattedDate);
                int compare1 = current.compareTo(sem_start);
                int compare2 = current.compareTo(sem_end);
                if (compare1 > 0 && compare2 < 0){
                    return i == 0 ? "spring" : (i == 1 ? "summer" : "fall");
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public static String getSemester(String start, String end){
        String[] starts = start.split("-");
        start = starts[1] +"-"+ starts[2];
        String[] ends = end.split("-");
        end = ends[1] + "-" +ends[2];
        if (start.equals(Semester.spring_start.getDetail()) && end.equals(Semester.spring_end.getDetail())){
            return starts[0] + "-spring";
        } else if (start.equals(Semester.summer_start.getDetail()) && end.equals(Semester.summer_end.getDetail())){
            return starts[0] + "-summer";
        } else if (start.equals(Semester.fall_start.getDetail()) && end.equals(Semester.fall_end.getDetail())){
            return starts[0] + "-fall";
        } else {
            return "";
        }
    }
    public static String getNextSemester(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 4);
        Date futureDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String formattedDate = dateFormat.format(futureDate);
        Semester[] semesters = Semester.values();
        for (int i = 0; i < 3; i++) {
            try {
                Date sem_start = dateFormat.parse(semesters[i*2].getDetail());
                Date sem_end = dateFormat.parse(semesters[i*2+1].getDetail());
                Date current = dateFormat.parse(formattedDate);
                int compare1 = current.compareTo(sem_start);
                int compare2 = current.compareTo(sem_end);
                if (compare1 >= 0 && compare2 <= 0){
                    return calendar.get(Calendar.YEAR) +"-"+ semesters[i*2].toString().replace("_start", "");
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public static String getPreviousSemester(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -4);
        Date futureDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String formattedDate = dateFormat.format(futureDate);
        Semester[] semesters = Semester.values();
        for (int i = 0; i < 3; i++) {
            try {
                Date sem_start = dateFormat.parse(semesters[i*2].getDetail());
                Date sem_end = dateFormat.parse(semesters[i*2+1].getDetail());
                Date current = dateFormat.parse(formattedDate);
                int compare1 = current.compareTo(sem_start);
                int compare2 = current.compareTo(sem_end);
                if (compare1 >= 0 && compare2 <= 0){
                    return calendar.get(Calendar.YEAR) +"-"+ semesters[i*2].toString().replace("_start", "");
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
