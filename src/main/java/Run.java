import Dao.BillsDao;
import Dao.ExtraBillDao;
import Model.Bill;
import Model.Config;
import Model.ExtraBill;
import Model.Semester;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Run {
    public static String[] getNextSemester(){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 4);
        currentDate = calendar.getTime();
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
                    if (i == 2){
                        return new String[]{Calendar.getInstance().get(Calendar.YEAR) +"-"+ semesters[0].getDetail(), Calendar.getInstance().get(Calendar.YEAR) +"-"+  semesters[1].getDetail()};
                    } else {
                        return new String[]{Calendar.getInstance().get(Calendar.YEAR) +"-"+ semesters[i*2].getDetail(), Calendar.getInstance().get(Calendar.YEAR) +"-"+  semesters[i*2+1].getDetail()};
                    }
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static void main(String[] args) {
/*        String start = "2023-05-01";
        String end = "2023-08-31";
        String[] starts = start.split("-");
        start = starts[1] +"-"+ starts[2];
        String[] ends = end.split("-");
        end = ends[1] + "-" +ends[2];
        System.out.println(start);
        System.out.println(end);
        System.out.println(Semester.summer_start.getDetail());
        if (start.equals(Semester.spring_start.getDetail()) && end.equals(Semester.spring_end.getDetail())){
            System.out.println(starts[0] + "-spring");
        } else if (start.equals(Semester.summer_start.getDetail()) && end.equals(Semester.summer_end.getDetail())){
            System.out.println(starts[0] + "-summer");
        } else if (start.equals(Semester.fall_start.getDetail()) && end.equals(Semester.fall_end.getDetail())){
            System.out.println(starts[0] + "-fall");
        } else {
            System.out.println("cac");;
        }*/
        ExtraBillDao extraBillDao = new ExtraBillDao();
        ArrayList<ExtraBill> arrayList = extraBillDao.getExtraWithUsername(Integer.parseInt("4"));
        String[] addresses = new String[arrayList.size()];
        String[] contents = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            addresses[i] = arrayList.get(i).user_email;
            contents[i] = "<h1 style='display: inline-block; white-space: pre;'>Xin chào " + arrayList.get(i).username + ", sau đây là hoá đơn tiền điện, tiền nước của phòng " + arrayList.get(i).room_name + " trong kì " + Semester.getSemester(arrayList.get(i).start, arrayList.get(i).end) + ". <br>" +
                    "Tiền điện : ("+arrayList.get(i).electricity+"-"+ Config.map.get("max_electricity") +") * "+arrayList.get(i).electricity_price+" = "+ (arrayList.get(i).electricity - Integer.parseInt(Config.map.get("max_electricity"))) * arrayList.get(i).electricity_price +" <br>" +
                    "Tiền nước : ("+arrayList.get(i).water+"-"+ Config.map.get("max_water") +") * "+arrayList.get(i).water_price+" = "+ (arrayList.get(i).water - Integer.parseInt(Config.map.get("max_water"))) * arrayList.get(i).water_price +
                    "</h1>";
            System.out.println(Arrays.toString(contents));
        }
    }
}
