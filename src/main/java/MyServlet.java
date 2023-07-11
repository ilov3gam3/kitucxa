import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MyServlet extends HttpServlet {
    private Timer timer;

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("prepare servlet");
        timer = new Timer();

        Calendar midnight = Calendar.getInstance();
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);

        // Calculate the delay until midnight
        long delay = midnight.getTimeInMillis() - System.currentTimeMillis();
        if (delay < 0) {
            // If it's already past midnight, schedule for the next day
            midnight.add(Calendar.DAY_OF_MONTH, 1);
            delay = midnight.getTimeInMillis() - System.currentTimeMillis();
        }

        // Schedule the task to run at midnight and repeat every 24 hours
        timer.schedule(new MidnightTask(), delay, 24 * 60 * 60 * 1000);
    }

    @Override
    public void destroy() {
        super.destroy();
        // Cancel the timer when the servlet is destroyed
        timer.cancel();
    }
}
