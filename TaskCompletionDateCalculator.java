import java.util.Calendar;
import java.util.List;

public class TaskCompletionDateCalculator {
    public static Calendar getEndTime(Calendar startTime, int timeRequired, int workingHourStart, int workingHourEnd,  List<Calendar> leaves) {
        int timeLeft = timeRequired;
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(startTime.getTime());
        while (timeLeft > 0) {
            if(isEmployeeLeaveToday(currentTime, leaves)) {
                currentTime.add(Calendar.DATE, 1);
            } else {
                currentTime.add(Calendar.HOUR, 1);
                if (currentTime.get(Calendar.HOUR_OF_DAY) < workingHourStart || currentTime.get(Calendar.HOUR_OF_DAY) >= workingHourEnd) {
                    currentTime.set(Calendar.HOUR_OF_DAY, workingHourStart);
                } else {
                    timeLeft--;
                }
            }
        }
        return currentTime;
    }

    private static boolean isEmployeeLeaveToday(Calendar currentTime, List<Calendar> leaves) {
        for (Calendar leave : leaves) {
            if (leave.get(Calendar.YEAR) == currentTime.get(Calendar.YEAR) &&
                    leave.get(Calendar.MONTH) == currentTime.get(Calendar.MONTH) &&
                    leave.get(Calendar.DAY_OF_MONTH) == currentTime.get(Calendar.DAY_OF_MONTH)) {
                return true;
            }
        }
        return false;
    }
}