package ua.bogun.pi.cron;

import ua.bogun.pi.PiModule;
import ua.bogun.pi.TgSender;

import java.util.TimerTask;

public class CronTempChanges extends TimerTask {
    @Override
    public void run() {
        String allInfo = PiModule.checkTemp();
        double gpuTemp = PiModule.parseGPU(allInfo);
        double cpuTemp = PiModule.parseCPU(allInfo);
        if (Math.abs(gpuTemp - PiModule.PREV_TEMP_GPU) >= 5 || Math.abs(cpuTemp - PiModule.PREV_TEMP_CPU) >= 5) {
            TgSender.sendMessage(PiModule.USER_ID, "☢️Info:☢️\n<b>Temperature changed</b>\n\n" + allInfo);
            PiModule.PREV_TEMP_GPU = gpuTemp;
            PiModule.PREV_TEMP_CPU = cpuTemp;
        }
    }


}