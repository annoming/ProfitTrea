package com.atming.utils;

import com.atming.entity.FiveOrder;
import com.atming.mapper.CronMapper;
import com.atming.utils.result.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.jvm.hotspot.runtime.ResultTypeFinder;

import java.util.concurrent.TimeUnit;

/**
 * @author annoming
 * @date 2021/3/27 11:56 下午
 */
//@Component
public class TimerUtil {

    @Autowired
    private CronMapper cronMapper;

    private static Logger tLogger = Logger.getLogger(TimeUnit.class); //日志记录
    private static String shell_path = "/Users/mingrui/PycharmProjects/pythonProject/login.py";

    @Bean
    public String getCronValue() {
        String cron = cronMapper.findById("PRJ-00001");
        System.out.println(cron);
        return cron;
    }

    @Bean
    public String getNewBlockCron() {
        String cron = cronMapper.findById("PRJ-00002");
        System.out.println(cron);
        return cron;
    }

    @Bean
    public String getBondCron() {
        String cron = cronMapper.findById("PRJ-00003");
        System.out.println(cron);
        return cron;
    }

    @Bean
    public String getFOREXCron() {
        String cron = cronMapper.findById("PRJ-00004");
        System.out.println(cron);
        return cron;
    }

    @Bean
    public String getNewsCron() {
        String cron = cronMapper.findById("PRJ-00005");
        System.out.println(cron);
        return cron;
    }

    @Bean
    public String getAnnounceCron() {
        String cron = cronMapper.findById("PRJ-00006");
        System.out.println(cron);
        return cron;
    }

    /**
     * 爬取新三板数据
     */
    @Scheduled(cron = "#{@getNewBlockCron}")
    public void excute() {
        Process pr = null;
        tLogger.info("==========getData job start===========");
        System.out.println("============开始爬取新三板数据=============");
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/login.py");
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        } finally {
            if (pr != null) {
                pr.destroy();
            }
        }
        System.out.println("================爬取新三板数据成功===============");
        tLogger.info("爬取数据成功");
    }

    @Scheduled(cron = "#{@getCronValue}")
    public void runFund() {
        Process pr = null;
        tLogger.info("==========getData job start===========");
        System.out.println("============开始爬取新基金数据=============");
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/fundation.py");
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        } finally {
            if (pr != null) {
                pr.destroy();
            }
        }
        System.out.println("================爬取基金数据成功===============");
        tLogger.info("爬取数据成功");
    }

    @Scheduled(cron = "#{@getBondCron}")
    public void runBond() {
        Process pr = null;
        tLogger.info("==========getData job start===========");
        System.out.println("============开始爬取新债券数据=============");
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/bond.py");
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        } finally {
            if (pr != null) {
                pr.destroy();
            }
        }
        System.out.println("================爬取债券数据成功===============");
        tLogger.info("爬取数据成功");
    }

    @Scheduled(cron = "#{@getFOREXCron}")
    public void runForex() {
        Process pr = null;
        tLogger.info("==========getData job start===========");
        System.out.println("============开始爬取新外汇数据=============");
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/forex.py");
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        } finally {
            if (pr != null) {
                pr.destroy();
            }
        }
        System.out.println("================爬取外汇数据成功===============");
        tLogger.info("爬取数据成功");
    }

    @Scheduled(cron = "#{@getNewsCron}")
    public void runNews() {
        Process pr = null;
        tLogger.info("==========getData job start===========");
        System.out.println("============开始爬取新闻咨询数据=============");
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/news.py");
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        } finally {
            if (pr != null) {
                pr.destroy();
            }
        }
        System.out.println("================爬取新闻咨询数据成功===============");
        tLogger.info("爬取数据成功");
    }

    @Scheduled(cron = "#{@getAnnounceCron}")
    public void runAnnouce() {
        Process pr = null;
        tLogger.info("==========getData job start===========");
        System.out.println("============开始爬取声明数据=============");
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/announce.py");
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        } finally {
            if (pr != null) {
                pr.destroy();
            }
        }
        System.out.println("================爬取声明数据成功===============");
        tLogger.info("爬取数据成功");
    }


    /**
     * 执行爬取五档数据
     * @param stockCode
     * @param stockName
     * @return
     */
    public FiveOrder sendFiveOrder(String stockCode,String stockName) {
        Process pr = null;
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/buyInfo.py" + " " + stockCode + " " + stockName);
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        } finally {
            if (pr != null) {
                pr.destroy();
            }
        }
        return null;
    }


}
