package com.zerocm.jdcheckin.service;

import com.zerocm.jdcheckin.component.TaskApplication;
import com.zerocm.jdcheckin.mapper.JdCookieMapper;
import com.zerocm.jdcheckin.pojo.ReturnParamPojo;
import com.zerocm.jdcheckin.pojo.domain.JdCookie;
import com.zerocm.jdcheckin.tools.MailTool;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能:
 * @项目名:jdcheckin
 * @作者:0cm
 * @日期:2020/4/72:51 下午
 */
@Service
public class StartCheckIn {
    private final static Logger logger = LoggerFactory.getLogger(TaskApplication.class);
    @Autowired
    private CheckIn checkIn;
    @Autowired
    private MailTool mailTool;
    @Autowired
    private JdCookieMapper jdCookieMapper;

    private String Cookies;

    private String mailTos;

    private List<JdCookie> jdCookiesList = new ArrayList<>();

    @Async
    public void startCheckIn(List<JdCookie> jdCookieList) {
        if (jdCookieList != null) {
            this.jdCookiesList = jdCookieList;
            try {
                startCheckIn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startCheckIn() {
        Map<String, String> jdMap = new HashMap<>();
        try {

            if (jdCookiesList.size() == 0 || jdCookiesList == null) {
                // 读取数据库的邮箱和cookie，存入map
                jdCookiesList = jdCookieMapper.selectAllCookie();
            }
            for (JdCookie jdCookie : jdCookiesList) {
                jdMap.put(jdCookie.getRemark(), jdCookie.getJdcookie());
            }
            // 遍历yml中的收件人邮箱和Cookie
            for (String key : jdMap.keySet()) {
                // 收件人邮箱和Cookie初始化
                mailTool.MailTool(key);
                logger.info("===== 等待发送至 " + key + " 邮箱 =====");
                checkIn.CheckIn(jdMap.get(key));

                List<ReturnParamPojo> list = new ArrayList<>();
                list.add(checkIn.JingDongBean());//京东京豆
                list.add(checkIn.JingRongBean());//金融京豆
                list.add(checkIn.JingRongSteel());//京东钢镚
                list.add(checkIn.JingDongTurn(new ReturnParamPojo()));//京东转盘
                list.add(checkIn.JingRSeeAds());//京东金融-广告
                list.add(checkIn.JDGroceryStore());//京东超市
                list.add(checkIn.JingDongClocks());//京东钟表馆
                list.add(checkIn.JingDongPet());//京豆宠物
                list.add(checkIn.JDFlashSale());//京东闪购
                list.add(checkIn.JDSecondhand());//京东拍拍二手
                list.add(checkIn.JingDongBook());//京东图书
                list.add(checkIn.JingDMakeup());//京东美妆管
                list.add(checkIn.JingDongWomen());//京东女装馆
                list.add(checkIn.JingDongCash());//京东现金红包
                list.add(checkIn.JingDongShoes());//京东鞋靴
                list.add(checkIn.JingDongFood());//京东美食
                list.add(checkIn.JingDongFish());//京东京鱼
                list.add(checkIn.JingRongGame());//金融游戏大厅
                list.add(checkIn.JingDongLive());//京东智能生活馆
                list.add(checkIn.JingDongClean());//京东清洁管
                list.add(checkIn.JDPersonalCare());//京东个人护理

                list.add(checkIn.JingDongPrize());//京东大奖签到
                list.add(checkIn.JingDongShake(new ReturnParamPojo()));//京东摇一摇


                Integer success = 0;
                Integer fail = 0;
                StringBuffer notify = new StringBuffer();
                for (ReturnParamPojo pojo : list) {
                    if (null != pojo.getSuccess() && pojo.getSuccess() > 0) {
                        success += pojo.getSuccess();
                    }
                    if (null != pojo.getFail() && pojo.getFail() > 0) {
                        fail += pojo.getFail();
                    }
                    notify.append("<br>\n" + pojo.getNotify());
                }

                StringBuffer finalNotify = new StringBuffer();
                finalNotify.append("<br>\n【签到概括】：成功：" + success + "失败：" + fail)
                        .append("<br>\n【账号总计】：" + checkIn.TotalBean() + "京豆," + checkIn.TotalSteel() + "钢镚," + checkIn.TotalCash() + "红包")
                        .append(notify);
                logger.info(finalNotify.toString());
                //发送邮件
                mailTool.sendSimpleMail(finalNotify.toString());
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("！！!!！！!!未支持的编码异常！！!!！！!!");
        } catch (InterruptedException e){
            logger.info("！！!!！！!!中断异常！！!!！！!!");
        } catch (JSONException e) {
            logger.info("！！!!！！!!JSON解析异常！！!!！！!!");
        } finally {
            this.jdCookiesList.clear();
        }
    }
}
