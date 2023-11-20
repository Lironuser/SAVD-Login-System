package com.example.loginserver.server;

import com.example.loginserver.Errors.LogsError;
import com.example.loginserver.dto.CompanyVo;
import com.example.loginserver.dto.LogsVo;
import com.example.loginserver.entity.LogEntity;
import com.example.loginserver.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
public class LogServer {
    @Autowired
    private LogRepository logRepository;
    private LogsError e;
    private int timeBetweenSpam= 15;
    public int getTimeBetweenSpam() {
        return timeBetweenSpam;
    }

    public LogsError save(LogsVo log, String ip){
        LogEntity bean = new LogEntity();
        bean.setLog("null");
        bean.setIp(ip);
        bean.setSuccess(true);
        bean.setTime(new Date());
        if (!(check_spam(ip, new Date()) == e.GOOD)){
            bean.setSuccess(false);
            bean.setLog("spam");
            logRepository.save(bean);
            return LogsError.SPAM;
        } else if (!(check_block(log.getCompany_id()) == e.GOOD)) {
            bean.setSuccess(false);
            bean.setLog("blocked");
            logRepository.save(bean);
            return LogsError.BLOCK;
        }
        return e.GOOD;
    }



    public LogsError check_block(long company_id){
        Optional<List<LogEntity>> logEntity;
        logEntity = logRepository.CheckBlock(company_id, 10);
        if(!logEntity.isPresent()){
            return e.NOT_FOUND;
        }
        short cnt = 0;
        for (LogEntity log : logEntity.get()){
            if (!log.getSuccess()){
                cnt+= 1;
            }
        }
        if (cnt == 10){
            return e.BLOCK;
        }
        return e.GOOD;
    }

    public LogsError check_spam(String ip, Date dateUser){
        // יצירת אובייקט Date עם הזמן הנוכחי
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateUser);
        cal.add(Calendar.MINUTE, - getTimeBetweenSpam());
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        Optional<List<LogEntity>> log;
        log = logRepository.CheckSpam(ip, timestamp);
        short cnt = 0;
        for (LogEntity run : log.get()){
            if (!run.getSuccess()){
                cnt++;
            } else if (run.getSuccess()) {
                return e.GOOD;
            }
        }
        if (cnt >= 10){
            return e.SPAM;
        }
        return e.GOOD;
    }


}
