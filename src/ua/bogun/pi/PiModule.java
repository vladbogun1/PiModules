package ua.bogun.pi;

import ua.bogun.pi.cron.CronTempAlarm;
import ua.bogun.pi.cron.CronTempChanges;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class PiModule {
    public static final int MAX_HEAT_SIZE = 38;
    public static String USER_ID;
    public static double PREV_TEMP_CPU = 0;
    public static double PREV_TEMP_GPU = 0;


    private static final int CRON_TIMER_TEMP_ALARM = 60000 * 30;
    private static final int CRON_TIMER_TEMP_CHECK = 60000 * 5;

    public static void main(String[] args) {
        USER_ID = args.length > 0 ? args[0] : "593289478";

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Say HI
        String message = "<b>✅Pi was started✅</b>\n" +
                "SSH connection up. Wifi: <i>" + checkSSID() + "</i>\n" +
                "<code>ssh pi@" + checkIp() + "</code>\n\n" +
                checkTemp();
        TgSender.sendMessage(USER_ID, message);


        Timer timer = new Timer();
        TimerTask tempAlarm = new CronTempAlarm();
        TimerTask tempCheck = new CronTempChanges();
        timer.schedule(tempAlarm, 0, CRON_TIMER_TEMP_ALARM);
        timer.schedule(tempCheck, 0, CRON_TIMER_TEMP_CHECK);
    }


    public static String checkTemp() {
        return runCommand(new String[]{"/home/pi/temp.sh"});
    }

    public static String checkIp() {
        return runCommand(new String[]{"/usr/bin/hostname", "-I"}).split(" ")[0];
    }

    public static String checkSSID() {
        return runCommand(new String[]{"/sbin/iwgetid", "-r"});
    }

    public static double parseCPU(String allInfo) {
        String substring = allInfo.substring(allInfo.indexOf("CPU =>") + 6).split("'")[0];

        return Double.parseDouble(substring);
    }

    public static double parseGPU(String allInfo) {
        String substring = allInfo.substring(allInfo.indexOf("GPU => temp=") + 12).split("'")[0];
        return Double.parseDouble(substring);
    }

    private static String runCommand(String[] command) {
        StringBuilder text = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
            int val = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
