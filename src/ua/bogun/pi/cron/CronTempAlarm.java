package ua.bogun.pi.cron;

import ua.bogun.pi.PiModule;
import ua.bogun.pi.TgSender;

import java.util.TimerTask;

public class CronTempAlarm extends TimerTask {
    @Override
    public void run() {
        String allInfo = PiModule.checkTemp();
        double gpuTemp = PiModule.parseGPU(allInfo);
        double cpuTemp = PiModule.parseCPU(allInfo);
        if (gpuTemp > PiModule.MAX_HEAT_SIZE || cpuTemp > PiModule.MAX_HEAT_SIZE) {
            TgSender.sendMessage(PiModule.USER_ID, "\uD83D\uDCDBAttention:\uD83D\uDCDB\n<b>Temperature > " + PiModule.MAX_HEAT_SIZE + "!!!</b>\n\n" + allInfo);
        }
    }

}
