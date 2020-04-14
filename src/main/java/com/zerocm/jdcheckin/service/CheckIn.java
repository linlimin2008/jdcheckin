package com.zerocm.jdcheckin.service;

import com.alibaba.fastjson.JSON;
import com.zerocm.jdcheckin.pojo.JDResponsePojo;
import com.zerocm.jdcheckin.pojo.ReturnParamPojo;
import com.zerocm.jdcheckin.tools.HttpClientTool;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * @功能:京豆
 * @项目名:jdcheckin
 * @作者:0cm
 * @日期:2020/3/2711:38 上午
 */
@Service
public class CheckIn {
    private final static Logger logger = LoggerFactory.getLogger(CheckIn.class);
    private String Cookie;
    public void CheckIn(String cookie){
        this.Cookie = cookie;
    }
    private static String  JingDongBeanUrl = "https://api.m.jd.com/client.action?functionId=signBeanIndex&appid=ld";//京东京豆
    private static String  SteelUrl = "https://coin.jd.com/m/gb/getBaseInfo.html";//京东钢镚
    private static String  CashUrl = "https://api.m.jd.com/client.action?functionId=myhongbao_balance";//京东京豆
    private static String  TotalBeanUrl = "https://wq.jd.com/user/info/QueryJDUserInfo?sceneval=2";//总京豆查询
    private static String  loginUrl= "https://ms.jr.jd.com/gw/generic/zc/h5/m/signRecords";//金融京豆登录
    private static String  JRBUrl = "https://ms.jr.jd.com/gw/generic/zc/h5/m/signRewardGift";//金融京豆
    private static String JRSUrl = "https://ms.jr.jd.com/gw/generic/gry/h5/m/signIn";//金融钢镚
    private static String JDTUrl = "https://api.m.jd.com/client.action?functionId=lotteryDraw&body=%7B%22actId%22%3A%22jgpqtzjhvaoym%22%2C%22appSource%22%3A%22jdhome%22%2C%22lotteryCode%22%3A%224wwzdq7wkqx2usx4g5i2nu5ho4auto4qxylblkxacm7jqdsltsepmgpn3b2hgyd7hiawzpccizuck%22%7D&appid=ld";//京东转盘
    private static String JRDSUrl = "https://nu.jr.jd.com/gw/generic/jrm/h5/m/process?";//京东双签
    private static String JDGSUrl = "https://api.m.jd.com/client.action?functionId=userSign";//京东超市 &京东钟表馆 &京东宠物 &京东图书 &京豆二手拍 &京豆美妆馆 &京豆女装馆 &京东鞋靴馆 &京东食物 &京东生活 &京东京鱼
    private static String JDPETUrl = "https://api.m.jd.com/client.action?functionId=partitionJdSgin";//京东闪购
    private static String JDCAUrl = "https://api.m.jd.com/client.action?functionId=ccSignInNew";//京东现金
    private static String JRAdsUrl = "https://ms.jr.jd.com/gw/generic/jrm/h5/m/sendAdGb";//京东金融-广告
    private static String JRGamelogin = "https://ylc.m.jd.com/sign/signGiftDays";//京东金融-游戏登录
    private static String JRGameUrl = "https://ylc.m.jd.com/sign/signDone";//京东金融-游戏
    private static String JDkey = "https://api.m.jd.com/client.action?functionId=vvipscdp_raffleAct_index&client=apple&clientVersion=8.1.0&appid=member_benefit_m";//京东抽大奖
    private static String JDPUrl = "https://api.m.jd.com/client.action?functionId=vvipscdp_raffleAct_lotteryDraw&body=%7B%22raffleActKey%22%3A%22";//京东抽大奖签到
    private static String JDSh = "https://api.m.jd.com/client.action?appid=vip_h5&functionId=vvipclub_shaking";//京东摇一摇

    /**
     * 京东商城-京鱼
     */
    public ReturnParamPojo JingDongFish(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("body", "{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/3BwL7BKvYV3i9imDeVtBbyU1v893\\/index.html?lng=0.000000&lat=0.000000&sid=ae5cf92b2f53e23f9339e4dae4789caw&un_area=19_1617_3643_8208\"},\"url\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/3BwL7BKvYV3i9imDeVtBbyU1v893\\/index.html?lng=0.000000&lat=0.000000&sid=ae5cf92b2f53e23f9339e4dae4789caw&un_area=19_1617_3643_8208\",\"params\":\"{\\\"enActK\\\":\\\"kHUojtemGSfyWMS74j\\/yrQjdkHCZ7LS0OV5zi7BgjzgaZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00620312_30612459_t1\\\",\\\"signId\\\":\\\"RWgRKe30+okaZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("client", "apple");
            param.put("clientVersion","8.5.6");
            param.put("d_brand","apple");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("rfs","0000");
            param.put("scope","11");
            param.put("sign","9be1cb3dbe9d2986bed77e36f20d043a");
            param.put("st","1586362297346");
            param.put("sv","120");
            String content = HttpClientTool.doPost(JDGSUrl, Cookie, param, null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content, JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东商城-京鱼签到成功response: \n" + content);
                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-京鱼: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-京鱼: 成功, 明细: 无京豆");
                }
            } else {
                logger.info("京东商城-京鱼签到失败response: \n" + content);
                if (content.contains("已签到") || content.contains("已领取")) {
                    returnParamPojo.setNotify("京东商城-京鱼: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                } else if (content.contains("不存在") || content.contains("已结束")) {
                    returnParamPojo.setNotify("京东商城-京鱼: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                } else if (jdResponsePojo.getCode() == 3) {
                    returnParamPojo.setNotify("京东商城-京鱼: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                } else if (jdResponsePojo.getCode() == 600) {
                    returnParamPojo.setNotify("京东商城-京鱼: 失败, 原因: 认证失败 ");
                    returnParamPojo.setFail(1);
                } else {
                    returnParamPojo.setNotify("京东商城-京鱼: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }

        } catch (Exception e){
            logger.info("京东商城-京鱼异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-京鱼: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东摇一摇
     */
    public ReturnParamPojo JingDongShake(ReturnParamPojo returnParamPojo){
        if (returnParamPojo.getFail()==null){
            returnParamPojo.setFail(0);
        }
        if (returnParamPojo.getSuccess()==null){
            returnParamPojo.setSuccess(0);
        }
        if (returnParamPojo.getBean()==null){
            returnParamPojo.setBean(0);
        }
        try {
            String content = HttpClientTool.doGet(JDSh,Cookie);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("prize")){
                logger.info("京东商城-摇一摇签到成功 : " + content);
                if (jdResponsePojo.getData().getPrizeBean()!=null){
                    if (returnParamPojo.getNotify()==null){
                        returnParamPojo.setNotify("京东商城-摇摇: 成功, 明细: "+jdResponsePojo.getData().getPrizeBean().getCount() +"京豆");
                    }else{
                        returnParamPojo.setNotify("京东商城-摇摇: 成功, 明细: "+jdResponsePojo.getData().getPrizeBean().getCount() +"京豆(多次)");
                    }
                    returnParamPojo.setBean(returnParamPojo.getBean()+1);
                    returnParamPojo.setSuccess(returnParamPojo.getSuccess()+1);
                }else {
                    if (jdResponsePojo.getData().getPrizeCoupon()!=null){
                        if (returnParamPojo.getNotify()==null){
                            returnParamPojo.setNotify("京东商城-摇摇: 获得满 "+jdResponsePojo.getData().getPrizeCoupon().getQuota() +"减"
                                    +jdResponsePojo.getData().getPrizeCoupon().getDiscount()+"优惠券→ "
                                    +jdResponsePojo.getData().getPrizeCoupon().getLimitStr());
                        }else{
                            returnParamPojo.setNotify("京东商城-摇摇（多次): 获得满 "+jdResponsePojo.getData().getPrizeCoupon().getQuota() +"减"
                                    +jdResponsePojo.getData().getPrizeCoupon().getDiscount()+"优惠券→ "
                                    +jdResponsePojo.getData().getPrizeCoupon().getLimitStr());
                        }
                        returnParamPojo.setSuccess(returnParamPojo.getSuccess()+1);
                    }else {
                        if (returnParamPojo.getNotify()==null){
                            returnParamPojo.setNotify("京东商城-摇摇: 失败, 原因: 未知 ");
                        }else{
                            returnParamPojo.setNotify("京东商城-摇摇: 失败, 原因: 未知(多次)");
                        }
                        returnParamPojo.setFail(returnParamPojo.getFail()+1);
                    }
                }

                if (jdResponsePojo.getData().getLuckyBox().getFreeTimes()!=0){
                    sleep(2000);
                    return JingDongShake(returnParamPojo);
                }

            }else {
                logger.info("京东商城-摇一摇签到失败 : " + content);
                if (content.contains("true")){
                    if (returnParamPojo.getNotify()==null){
                        returnParamPojo.setNotify("京东商城-摇摇: 失败, 原因: 未知 ");
                    }else{
                        returnParamPojo.setNotify("京东商城-摇摇: 失败, 原因: 未知(多次)");
                    }
                    returnParamPojo.setSuccess(returnParamPojo.getSuccess()+1);
                    if (jdResponsePojo.getData().getLuckyBox().getFreeTimes()!=0){
                        sleep(2000);
                        return JingDongShake(returnParamPojo);
                    }
                }else {
                    if (content.contains("无免费")||content.contains("8000005")){
                        returnParamPojo.setFail(1);
                        returnParamPojo.setNotify( "京东商城-摇摇: 失败, 原因: 已摇过" );
                    }else if (content.contains("未登录")||content.contains("101")){
                        returnParamPojo.setFail(1);
                        returnParamPojo.setNotify( "京东商城-摇摇: 失败, 原因: Cookie失效!!" );
                    }else {
                        returnParamPojo.setFail(1);
                        if (returnParamPojo.getNotify()==null){
                            returnParamPojo.setNotify("京东商城-摇摇: 失败, 原因: 未知 ");
                        }else{
                            returnParamPojo.setNotify("京东商城-摇摇: 失败, 原因: 未知(多次)");
                        }
                    }
                }
            }

        }catch (Exception e){
            logger.info("京东商城-摇摇: 签到接口请求失败 ！！");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-摇摇: 签到接口请求失败 !!!!");
            returnParamPojo.setFail(returnParamPojo.getFail()+1);
        }
        return returnParamPojo;
    }
    /**
     * 京东商城-大奖 签到
     */
    public ReturnParamPojo JDPrizeCheckin(String key){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try {
            StringBuffer NEWJDPUrl = new StringBuffer(JDPUrl);
            NEWJDPUrl.append(key)
                    .append("%22%2C%22drawType%22%3A0%2C%22riskInformation%22%3A%7B%7D%7D&client=apple&clientVersion=8.1.0&appid=member_benefit_m");
            Map<String,Object> header = new HashMap<>();
            header.put("Referer","https://jdmall.m.jd.com/beansForPrizes");
            String content = HttpClientTool.doGet(NEWJDPUrl.toString(),Cookie,null,header);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (jdResponsePojo.getSuccess()){
                logger.info("京东商城-大奖签到成功  : " + content);
                if (jdResponsePojo.getData().getBeanNumber()!=null){
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(jdResponsePojo.getData().getBeanNumber());
                    returnParamPojo.setNotify( "京东商城-大奖: 成功, 明细:  " + jdResponsePojo.getData().getBeanNumber() + "京豆 🐶");
                }else if (jdResponsePojo.getData().getCouponInfoVo()!=null){
                    if (jdResponsePojo.getData().getCouponInfoVo().getLimitStr()!=null){
                        returnParamPojo.setSuccess(1);
                        returnParamPojo.setNotify( "京东商城-大奖: 获得满 " + jdResponsePojo.getData().getCouponInfoVo().getQuota()
                                + "减"+jdResponsePojo.getData().getCouponInfoVo().getDiscount()
                                +"优惠券→"+jdResponsePojo.getData().getCouponInfoVo().getLimitStr());
                    }else {
                        returnParamPojo.setSuccess(1);
                        returnParamPojo.setNotify( "京东商城-大奖: 成功, 明细: 优惠券 " );
                    }

                }else if (jdResponsePojo.getData().getPitType()==0){
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-大奖: 成功, 明细: 未中奖 " );
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-大奖: 成功, 明细: 未知  " );
                }
            }else {
                logger.info("京东商城-大奖签到失败  : " + content);
                if (content.contains("已用光")||content.contains("7000003")){
                    returnParamPojo.setFail(1);
                    returnParamPojo.setNotify( "京东商城-大奖: 失败, 原因: 已签过" );
                }else if (content.contains("未登录")||content.contains("101")){
                    returnParamPojo.setFail(1);
                    returnParamPojo.setNotify( "京东商城-大奖: 失败, 原因: Cookie失效" );
                }else {
                    returnParamPojo.setFail(1);
                    returnParamPojo.setNotify( "京东商城-大奖: 失败, 原因: 未知 " );
                }
            }
        }catch (Exception e){
            logger.info("京东商城-大奖签到异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-大奖签到: 查询接口请求失败 !!!!");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东商城-大奖
     */
    public ReturnParamPojo JingDongPrize(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> header = new HashMap<>();
            header.put("Referer","https://jdmall.m.jd.com/beansForPrizes");
            String content = HttpClientTool.doGet(JDkey,Cookie,null,header);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (!StringUtils.isEmpty(jdResponsePojo.getData().getFloorInfoList()[0].getDetail().getRaffleActKey())){
                returnParamPojo = JDPrizeCheckin(jdResponsePojo.getData().getFloorInfoList()[0].getDetail().getRaffleActKey());
                logger.info("京东商城-大奖查询KEY成功 : " + content);
            }else {
                returnParamPojo.setNotify("京东商城-大奖: 失败, 原因: 无奖池");
                returnParamPojo.setFail(1);
            }
        }catch (Exception e){
            logger.info("京东商城-大奖异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-大奖: 查询接口请求失败 !!!!");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东商城-个护
     */
    public ReturnParamPojo JDPersonalCare(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/NJ1kd1PJWhwvhtim73VPsD1HwY3\\/index.html?collectionId=294\"},\"url\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/NJ1kd1PJWhwvhtim73VPsD1HwY3\\/index.html?collectionId=294\",\"params\":\"{\\\"enActK\\\":\\\"T9fTAER+0EaJX5kEXrIO5hRPQXWgYDTaDljnh13\\/Bv8aZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00167278_31530230_t1\\\",\\\"signId\\\":\\\"Q+TbBJ3LWR4aZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("client","apple");
            param.put("clientVersion","8.5.6");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("scope","11");
            param.put("sign","6ae0c689b3463149d59e4e09a0a7acd3");
            param.put("st","1585642030591");
            param.put("sv","100");

            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东商城-个护签到成功response: \n" + content);

                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-个护: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-个护: 成功, 明细: 无京豆");
                }
            }else{
                logger.info("京东商城-个护签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")){
                    returnParamPojo.setNotify("京东商城-个护: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")||content.contains("未开始")){
                    returnParamPojo.setNotify("京东商城-个护: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3){
                    returnParamPojo.setNotify("京东商城-个护: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==600){
                    returnParamPojo.setNotify("京东商城-个护: 失败, 原因: 认证失败‼!");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东商城-个护: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东商城-个护异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-个护: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }


    /**
     * 京东商城-清洁
     */
    public ReturnParamPojo JingDongClean(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"-1\"},\"url\":\"\",\"params\":\"{\\\"enActK\\\":\\\"eODg3hfm3fuuqL4C+0PEuyilmPGJ\\/3a2OgGu9YgS4AQaZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00561054_31521509_t1\\\",\\\"signId\\\":\\\"kfuLCTw\\/9mAaZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("client","apple");
            param.put("clientVersion","8.5.5");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("partner","apple");
            param.put("scope","11");
            param.put("screen","1242*2208");
            param.put("sign","9b2eea9c5c41842277ea6d2f2d99b1f3");
            param.put("st","1585155369440");
            param.put("sv","102");

            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东商城-清洁签到成功response: \n" + content);

                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-清洁: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-清洁: 成功, 明细: 无京豆");
                }
            }else{
                logger.info("京东商城-清洁签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")){
                    returnParamPojo.setNotify("京东商城-清洁: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")||content.contains("未开始")){
                    returnParamPojo.setNotify("京东商城-清洁: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3){
                    returnParamPojo.setNotify("京东商城-清洁: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==600){
                    returnParamPojo.setNotify("京东商城-清洁: 失败, 原因: 认证失败‼!");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东商城-清洁: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东商城-清洁异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-清洁: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东生活馆
     */
    public ReturnParamPojo JingDongLive(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/KcfFqWvhb5hHtaQkS4SD1UU6RcQ\\/index.html?cu=true&utm_source=www.luck4ever.net&utm_medium=tuiguang&utm_campaign=t_1000042554_&utm_term=8d1fbab27551485f8f9b1939aee1ffd0\"},\"url\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/KcfFqWvhb5hHtaQkS4SD1UU6RcQ\\/index.html?cu=true&utm_source=www.luck4ever.net&utm_medium=tuiguang&utm_campaign=t_1000042554_&utm_term=8d1fbab27551485f8f9b1939aee1ffd0\",\"params\":\"{\\\"enActK\\\":\\\"isDhQnCJUnjlNPoFf5Do0JM9l54aZ0\\/eHe0aBgdJgcQaZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":true,\\\"ruleSrv\\\":\\\"00007152_29653514_t0\\\",\\\"signId\\\":\\\"ZYsm01V6Gr4aZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("client","apple");
            param.put("clientVersion","8.5.0");
            param.put("d_brand","apple");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("sign","c7ecee5b465f5edd7ed2e2189fad2335");
            param.put("st","1581317924210");
            param.put("sv","120");
            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东智能-生活签到成功response: \n" + content);

                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东智能-生活: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东智能-生活: 成功, 明细: 无京豆");
                }
            }else{
                logger.info("京东智能-生活签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")){
                    returnParamPojo.setNotify("京东智能-生活: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")){
                    returnParamPojo.setNotify("京东智能-生活: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3){
                    returnParamPojo.setNotify("京东智能-生活: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==600){
                    returnParamPojo.setNotify("京东智能-生活: 失败, 原因: 认证失败‼!");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东智能-生活: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东智能-生活异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东智能-生活: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东金融-游戏
     */
    public ReturnParamPojo JRGameCheckin(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("channelId","1");
            String content = HttpClientTool.doPost(JRGameUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (jdResponsePojo.getCode()==200){
                logger.info("京东金融-游戏签到成功response: \n" + content);
                if (jdResponsePojo.getData().getRewardAmount()>0){
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(jdResponsePojo.getData().getRewardAmount());
                    returnParamPojo.setNotify( "京东金融-游戏: 成功, 明细: " + jdResponsePojo.getData().getRewardAmount() + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东金融-游戏:  成功, 明细: 无京豆 🐶");
                }
            }else {
                logger.info("京东金融-游戏签到失败response: \n" + content);
                if (content.contains("用户重复") || content.contains("重复点击")||jdResponsePojo.getCode()==301||jdResponsePojo.getCode()==303) {
                    returnParamPojo.setNotify("京东金融-游戏: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                } else if (content.contains("不存在") || content.contains("已结束")|| content.contains("未找到")) {
                    returnParamPojo.setNotify("京东金融-游戏: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                } else if (jdResponsePojo.getCode() == 202|| content.contains("未登录")) {
                    returnParamPojo.setNotify("京东金融-游戏: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                } else {
                    returnParamPojo.setNotify("京东金融-游戏: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东金融-游戏签到接口异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东金融-游戏: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东金融-游戏
     */
    public ReturnParamPojo JingRongGame(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("channelId","1");
            String content = HttpClientTool.doPost(JRGamelogin,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("未登录")){
                logger.info("京东金融-游戏登录失败response: \n" + content);
                returnParamPojo.setNotify("京东游戏-登录: 失败, 原因: Cookie失效!!️");
                returnParamPojo.setFail(1);
            }else if (content.contains("成功")){
                logger.info("京东金融-游戏登录成功response: \n" + content);
                return this.JRGameCheckin();
            }else {
                returnParamPojo.setNotify("京东游戏-登录: 失败, 原因: 未知!!");
                returnParamPojo.setFail(1);
            }
        }catch (Exception e){
            logger.info("京东金融-游戏登录异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东金融-游戏登录: 登录接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东金融-广告
     */
    public ReturnParamPojo JingRSeeAds(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("reqData","{\"clientType\":\"ios\",\"actKey\":\"176696\",\"userDeviceInfo\":{\"adId\":9999999},\"deviceInfoParam\":{\"macAddress\":\"02:00:00:00:00:00\",\"channelInfo\":\"appstore\",\"IPAddress1\":\"\",\"OpenUDID\":\"\",\"clientVersion\":\"5.3.30\",\"terminalType\":\"02\",\"osVersion\":\"\",\"appId\":\"com.jd.jinrong\",\"deviceType\":\"iPhone8,2\",\"networkType\":\"\",\"startNo\":212,\"UUID\":\"\",\"IPAddress\":\"\",\"deviceId\":\"\",\"IDFA\":\"\",\"resolution\":\"\",\"osPlatform\":\"iOS\"},\"bussource\":\"\"}");
            String content = HttpClientTool.doPost(JRAdsUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (jdResponsePojo.getResultData().getCanGetGb()){
                logger.info("京东金融-广告签到成功response: \n" + content);
                if (jdResponsePojo.getResultData().getData().getVolumn()>0){
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(jdResponsePojo.getResultData().getVolumn());
                    returnParamPojo.setNotify( "京东金融-广告: 成功, 明细: " + jdResponsePojo.getResultData().getData().getVolumn() + "京豆 🐶");
                }else{
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东金融-广告:  成功, 明细: 无京豆 🐶");
                }
            }else {
                logger.info("京东金融-广告签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")||
                        content.contains("已经发完")||jdResponsePojo.getResultData().getCode()==2000){
                    returnParamPojo.setNotify("京东金融-广告: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")||content.contains("未找到")){
                    returnParamPojo.setNotify("京东金融-广告: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3||content.contains("未登录")){
                    returnParamPojo.setNotify("京东金融-广告: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东金融-广告: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东金融-广告异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东金融-广告: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东美食
     */
    public ReturnParamPojo JingDongFood(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("body", "{\"params\":\"{\\\"enActK\\\":\\\"FXy4qPoGOckBeTSpyYzozEW3M9mj+XDDcciQAT4BCBQaZs/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00149803_31265281_t1\\\",\\\"signId\\\":\\\"Z3x1jBClFqsaZs/n4coLNw==\\\"}\",\"riskParam\":{\"platform\":\"3\",\"orgType\":\"2\",\"openId\":\"-1\",\"pageClickKey\":\"Babel_Sign\",\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"fp\":\"-1\",\"shshshfp\":\"b8ff826674dda95c4258d632e7c5845e\",\"shshshfpa\":\"f6ca1cb3-300a-fef7-ce56-11b2dc685988-1582473660\",\"shshshfpb\":\"ao0pyKirmGbxBzmszs2h/sw==\",\"childActivityUrl\":\"https://pro.m.jd.com/mall/active/43tTmWFv8cBQM6YNtJpq1gCFmCfv/index.html?collectionId=249&un_area=20_1806_1810_12325&lng=0&lat=0\"},\"siteClient\":\"apple\",\"mitemAddrId\":\"\",\"geo\":{\"lng\":\"0\",\"lat\":\"0\"},\"addressId\":\"\",\"posLng\":\"\",\"posLat\":\"\",\"focus\":\"\",\"innerAnchor\":\"\",\"cv\":\"2.0\"}");
            param.put("client", "wh5");
            String content = HttpClientTool.doPost(JDGSUrl, Cookie, param, null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content, JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东商城-美食签到成功response: \n" + content);
                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-美食: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-美食: 成功, 明细: 无京豆");
                }
            } else {
                logger.info("京东商城-美食签到失败response: \n" + content);
                if (content.contains("已签到") || content.contains("已领取")) {
                    returnParamPojo.setNotify("京东商城-美食: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                } else if (content.contains("不存在") || content.contains("已结束")) {
                    returnParamPojo.setNotify("京东商城-美食: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                } else if (jdResponsePojo.getCode() == 3) {
                    returnParamPojo.setNotify("京东商城-美食: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                } else if (jdResponsePojo.getCode() == 600) {
                    returnParamPojo.setNotify("京东商城-美食: 失败, 原因: 认证失败 ");
                    returnParamPojo.setFail(1);
                } else {
                    returnParamPojo.setNotify("京东商城-美食: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }

        } catch (Exception e){
            logger.info("京豆商城-美食异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京豆商城-美食: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }


    /**
     * 京东鞋靴馆
     */
    public ReturnParamPojo JingDongShoes(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"params\":\"{\\\"enActK\\\":\\\"7Ive90vKJQaMEzWlhMgIwIih1KqMPXNQdPbewzqrg2MaZs/n4coLNw==\\\",\\\"isFloatLayer\\\":true,\\\"ruleSrv\\\":\\\"00116882_29523722_t0\\\",\\\"signId\\\":\\\"SeWbLe9ma04aZs/n4coLNw==\\\"}\",\"riskParam\":{\"platform\":\"3\",\"orgType\":\"2\",\"openId\":\"-1\",\"pageClickKey\":\"Babel_Sign\",\"eid\":\"\",\"fp\":\"-1\",\"shshshfp\":\"b3fccfafc270b38e0bddfdc0e455b48f\",\"shshshfpa\":\"\",\"shshshfpb\":\"\",\"childActivityUrl\":\"\"},\"siteClient\":\"apple\",\"mitemAddrId\":\"\",\"geo\":{\"lng\":\"0\",\"lat\":\"0\"},\"addressId\":\"\",\"posLng\":\"\",\"posLat\":\"\",\"focus\":\"\",\"innerAnchor\":\"\",\"cv\":\"2.0\"}");
            param.put("client","wh5");
            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东商城-鞋靴签到成功response: \n" + content);
                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-鞋靴: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-鞋靴: 成功, 明细: 无京豆");
                }
            }else {
                logger.info("京东商城-鞋靴签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")){
                    returnParamPojo.setNotify("京东商城-鞋靴: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")||content.contains("未开始")){
                    returnParamPojo.setNotify("京东商城-鞋靴: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3){
                    returnParamPojo.setNotify("京东商城-鞋靴: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==600){
                    returnParamPojo.setNotify("京东商城-鞋靴: 失败, 原因: 认证失败");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东商城-鞋靴: 失败, 原因: 未知!");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京豆商城-鞋靴异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京豆商城-鞋靴: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东现金
     */
    public ReturnParamPojo JingDongCash(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"pageClickKey\":\"CouponCenter\",\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"childActivityUrl\":\"openapp.jdmobile%3a%2f%2fvirtual%3fparams%3d%7b%5c%22category%5c%22%3a%5c%22jump%5c%22%2c%5c%22des%5c%22%3a%5c%22couponCenter%5c%22%7d\",\"monitorSource\":\"cc_sign_ios_index_config\"}");
            param.put("client","apple");
            param.put("clientVersion","8.5.0");
            param.put("d_brand","apple");
            param.put("d_model","iPhone8,2");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("scope","11");
            param.put("screen","1242*2208");
            param.put("sign","1cce8f76d53fc6093b45a466e93044da");
            param.put("st","1581084035269");
            param.put("sv","102");
            String content = HttpClientTool.doPost(JDCAUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (jdResponsePojo.getBusiCode()==0){
                logger.info("京东现金-红包签到成功response: \n" + content);
                if (jdResponsePojo.getResult().getSignResult().getSignData().getAmount()>0){
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setCash(jdResponsePojo.getResult().getSignResult().getSignData().getAmount());
                    returnParamPojo.setNotify( "京东现金-红包: 成功, 明细: " + jdResponsePojo.getResult().getSignResult().getSignData().getAmount() + "红包");
                }else{
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东现金-红包: 成功, 明细: 无红包 🐶");
                }
            }else {
                logger.info("京东现金-红包签到失败response: \n" + content);
                if (content.contains("完成签到")||jdResponsePojo.getBusiCode()==1002){
                    returnParamPojo.setNotify("京东现金-红包: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")){
                    returnParamPojo.setNotify("京东现金-红包: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getBusiCode()==3||content.contains("未登录")){
                    returnParamPojo.setNotify("京东现金-红包: 失败, 原因: Cookie失效！！");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东现金-红包: 失败, 原因: 未知!");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){

        }
        return returnParamPojo;
    }

    /**
     * 京东女装馆
     */
    public ReturnParamPojo JingDongWomen(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try {
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"-1\"},\"url\":\"\",\"params\":\"{\\\"enActK\\\":\\\"OQmfgxmylrMM6EurCHg9lEjL1ShNb2dVjEja9MceBPgaZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00002492_28085975_t1\\\",\\\"signId\\\":\\\"YE5T0wVaiL8aZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("build","167057");
            param.put("client","apple");
            param.put("clientVersion","8.5.0");
            param.put("d_brand","apple");
            param.put("d_model","iPhone8,2");
            param.put("networklibtype","JDNetworkBaseAF");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("osVersion","13.3.1");
            param.put("scope","11");
            param.put("screen","1242*2208");
            param.put("sign","7329899a26d8a8c3046b882d6df2b329");
            param.put("st","1581083524405");
            param.put("sv","101");
            param.put("uuid","coW0lj7vbXVin6h7ON+tMNFQqYBqMahr");
            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东商城-女装签到成功response: \n" + content);
                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-女装: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-女装: 成功, 明细: 无京豆");
                }
            }else {
            logger.info("京东商城-女装签到失败response: \n" + content);
            if (content.contains("已签到")||content.contains("已领取")){
                returnParamPojo.setNotify("京东商城-女装: 失败, 原因: 已签过");
                returnParamPojo.setFail(1);
            }else if (content.contains("不存在")||content.contains("已结束")){
                returnParamPojo.setNotify("京东商城-女装: 失败, 原因: 活动已结束!");
                returnParamPojo.setFail(1);
            }else if (jdResponsePojo.getCode()==3){
                returnParamPojo.setNotify("京东商城-女装: 失败, 原因: Cookie失效‼!");
                returnParamPojo.setFail(1);
            }else if (jdResponsePojo.getCode()==600){
                returnParamPojo.setNotify("京东商城-女装: 失败, 原因: 认证失败 ");
                returnParamPojo.setFail(1);
            }else {
                returnParamPojo.setNotify("京东商城-女装: 失败, 原因: 未知 !");
                returnParamPojo.setFail(1);
            }
        }
        }catch (Exception e){
            logger.info("京东商城-女装异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-女装: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东美妆馆
     */
    public ReturnParamPojo JingDMakeup(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"-1\"},\"url\":\"\",\"params\":\"{\\\"enActK\\\":\\\"Ivkdqs6fb5SN1HsgsPsE7vJN9NGIydei6Ik+1rAyngwaZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00138455_30206794_t1\\\",\\\"signId\\\":\\\"YU1cvfWmabwaZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("build","167092");
            param.put("client","apple");
            param.put("clientVersion","8.5.2");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("scope","11");
            param.put("sign","cc38bf6e24fd65e4f43868ccbe679f85");
            param.put("st","1582992598833");
            param.put("sv","112");
            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);

            if (content.contains("签到成功")){
                logger.info("京东商城-美妆签到成功response: \n" + content);
                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-美妆: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-美妆: 成功, 明细: 无京豆");
                }
            }else{
                logger.info("京东商城-美妆签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")){
                    returnParamPojo.setNotify("京东商城-美妆: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")||content.contains("未开始")){
                    returnParamPojo.setNotify("京东商城-美妆: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3){
                    returnParamPojo.setNotify("京东商城-美妆: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==600){
                    returnParamPojo.setNotify("京东商城-美妆: 失败, 原因: 认证失败 ");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东商城-美妆: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东商城-美妆异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-美妆: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }

        return returnParamPojo;
    }

    /**
     * 京东拍拍二手
     */
    public ReturnParamPojo JDSecondhand(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/3S28janPLYmtFxypu37AYAGgivfp\\/index.html\"},\"url\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/3S28janPLYmtFxypu37AYAGgivfp\\/index.html\",\"params\":\"{\\\"enActK\\\":\\\"HjRtRBMJdzRlhJzUCg9461ejcOQJht\\/IVs0vaXG9bu8aZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00124860_28262902_t1\\\",\\\"signId\\\":\\\"dNjggqEioBYaZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("client","apple");
            param.put("clientVersion","8.5.5");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("partner","apple");
            param.put("rfs","0000");
            param.put("scope","11");
            param.put("sign","e3a35ec455319c47b94f3ad95663849c");
            param.put("st","1585154729277");
            param.put("sv","101");
            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东拍拍-二手签到成功response: \n" + content);

                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东拍拍-二手: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东拍拍-二手: 成功, 明细: 无京豆");
                }
            }else{
                logger.info("京东拍拍-二手签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")){
                    returnParamPojo.setNotify("京东拍拍-二手: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")){
                    returnParamPojo.setNotify("京东拍拍-二手: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3){
                    returnParamPojo.setNotify("京东拍拍-二手: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==600){
                    returnParamPojo.setNotify("京东拍拍-二手: 失败, 原因: 认证失败 ");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东拍拍-二手: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东拍拍-二手异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东拍拍-二手: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东闪购
     */
    public ReturnParamPojo JDFlashSale(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        Map<String,Object> param = new HashMap<>();
        param.put("client","apple");
        param.put("clientVersion","8.4.6");
        param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
        param.put("sign","141ab5f9af92126bb46d50f3e8af758a");
        param.put("st","1579305780511");
        param.put("sv","102");
        param.put("body","{}");
        String content = HttpClientTool.doPost(JDPETUrl,Cookie,param,null);
        JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
        logger.info(content);
        if (jdResponsePojo.getResult().getCode()==0){
            logger.info("京东商城-闪购签到成功response: \n" + content);
            if (jdResponsePojo.getResult().getCount()>0){
                returnParamPojo.setSuccess(1);
                returnParamPojo.setBean(jdResponsePojo.getResult().getCount());
                returnParamPojo.setNotify( "京东商城-闪购: 成功, 明细: " + jdResponsePojo.getResult().getCount() + "京豆 🐶");
            }else{
                returnParamPojo.setSuccess(1);
                returnParamPojo.setNotify( "京东商城-闪购:  成功, 明细: 无京豆 🐶");
            }
        }else {
            logger.info("京东商城-闪购签到失败response: \n" + content);
            if (content.contains("已签到")||content.contains("已领取")||content.contains("2005")){
                returnParamPojo.setNotify("京东商城-闪购: 失败, 原因: 已签过");
                returnParamPojo.setFail(1);
            }else if (content.contains("不存在")||content.contains("已结束")||content.contains("2008")){
                returnParamPojo.setNotify("京东商城-闪购: 失败, 原因: 活动已结束!");
                returnParamPojo.setFail(1);
            }else if (jdResponsePojo.getCode()==3||content.contains("1003")){
                returnParamPojo.setNotify("京东商城-闪购: 失败, 原因: Cookie失效‼!");
                returnParamPojo.setFail(1);
            }else {
                returnParamPojo.setNotify("京东商城-闪购: 失败, 原因: 未知 !");
                returnParamPojo.setFail(1);
            }
        }
        return returnParamPojo;
    }

    /**
     * 京东图书
     */
    public ReturnParamPojo JingDongBook(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"riskParam\":{\"eid\":\"O5X6JYMZTXIEX4VBCBWEM5PTIZV6HXH7M3AI75EABM5GBZYVQKRGQJ5A2PPO5PSELSRMI72SYF4KTCB4NIU6AZQ3O6C3J7ZVEP3RVDFEBKVN2RER2GTQ\",\"shshshfpb\":\"v1\\/zMYRjEWKgYe+UiNwEvaVlrHBQGVwqLx4CsS9PH1s0s0Vs9AWk+7vr9KSHh3BQd5NTukznDTZnd75xHzonHnw==\",\"pageClickKey\":\"Babel_Sign\",\"childActivityUrl\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/3SC6rw5iBg66qrXPGmZMqFDwcyXi\\/index.html?cu=true&utm_source=www.linkstars.com&utm_medium=tuiguang&utm_campaign=t_1000089893_157_0_184__cc59020469361878&utm_term=e04e88b40a3c4e24898da7fcee54a609\"},\"url\":\"https:\\/\\/pro.m.jd.com\\/mall\\/active\\/3SC6rw5iBg66qrXPGmZMqFDwcyXi\\/index.html?cu=true&utm_source=www.linkstars.com&utm_medium=tuiguang&utm_campaign=t_1000089893_157_0_184__cc59020469361878&utm_term=e04e88b40a3c4e24898da7fcee54a609\",\"params\":\"{\\\"enActK\\\":\\\"ziJpxomssJzA0Lnt9V+VYoW5AbqAOQ6XiMQuejSm7msaZs\\/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"ruleSrv\\\":\\\"00416621_28128239_t1\\\",\\\"signId\\\":\\\"jw9BKb\\/b+fEaZs\\/n4coLNw==\\\"}\",\"geo\":{\"lng\":\"0.000000\",\"lat\":\"0.000000\"}}");
            param.put("client","apple");
            param.put("clientVersion","8.4.6");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("sign","c1d6bdbb17d0d3f8199557265c6db92c");
            param.put("st","1579305128990");
            param.put("sv","121");
            String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            logger.info(content);
            if (content.contains("签到成功")){
                logger.info("京东商城-图书签到成功response: \n" + content);

                if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                    Integer beanQuantity = matchInteger(matchForJDBean(content));
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setBean(beanQuantity);
                    returnParamPojo.setNotify( "京东商城-图书: 成功, 明细: " + beanQuantity + "京豆 🐶");
                }else {
                    returnParamPojo.setSuccess(1);
                    returnParamPojo.setNotify( "京东商城-图书: 成功, 明细: 无京豆");
                }
            }else{
                logger.info("京东商城-图书签到失败response: \n" + content);
                if (content.contains("已签到")||content.contains("已领取")){
                    returnParamPojo.setNotify("京东商城-图书: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("不存在")||content.contains("已结束")||content.contains("未开始")){
                    returnParamPojo.setNotify("京东商城-图书: 失败, 原因: 活动已结束!");
                    returnParamPojo.setFail(1);
                }else if (jdResponsePojo.getCode()==3){
                    returnParamPojo.setNotify("京东商城-图书: 失败, 原因: Cookie失效‼!");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东商城-图书: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            logger.info("京东商城-图书异常");
            e.printStackTrace();
            returnParamPojo.setNotify("京东商城-图书: 签到接口请求失败!!️");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东宠物
     */
    public ReturnParamPojo JingDongPet(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        Map<String,Object> param = new HashMap<>();
        param.put("body","{\"params\":\"{\\\"enActK\\\":\\\"6DiDTHMDvpNyoP9JUaEkki/sREOeEAl8M8REPQ/2eA4aZs/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"signId\\\":\\\"Nk2fZhdgf5UaZs/n4coLNw==\\\"}\"}");
        param.put("client","wh5");
        String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
        JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
        logger.info(content);
        if (content.contains("签到成功")){
            logger.info("京东商城-宠物签到成功response: \n" + content);

            if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                Integer beanQuantity = matchInteger(matchForJDBean(content));
                returnParamPojo.setSuccess(1);
                returnParamPojo.setBean(beanQuantity);
                returnParamPojo.setNotify( "京东商城-宠物: 成功, 明细: " + beanQuantity + "京豆 🐶");
            }else {
                returnParamPojo.setSuccess(1);
                returnParamPojo.setNotify( "京东商城-宠物: 成功, 明细: 无京豆");
            }
        }else{
            logger.info("京东商城-宠物签到失败response: \n" + content);
            if (content.contains("已签到")||content.contains("已领取")){
                returnParamPojo.setNotify("京东商城-宠物: 失败, 原因: 已签过");
                returnParamPojo.setFail(1);
            }else if (content.contains("不存在")||content.contains("已结束")){
                returnParamPojo.setNotify("京东商城-宠物: 失败, 原因: 活动已结束!");
                returnParamPojo.setFail(1);
            }else if (jdResponsePojo.getCode()==3){
                returnParamPojo.setNotify("京东商城-宠物: 失败, 原因: Cookie失效‼!");
                returnParamPojo.setFail(1);
            }else {
                returnParamPojo.setNotify("京东商城-钟表: 失败, 原因: 未知 !");
                returnParamPojo.setFail(1);
            }
        }
        return returnParamPojo;
    }

    /**
     * 京东钟表馆
     */
    public ReturnParamPojo JingDongClocks(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        Map<String,Object> param = new HashMap<>();
        param.put("body","{\"params\":\"{\\\"enActK\\\":\\\"LW67/HBJP72aMSByZLRaRqJGukOFKx9r4F87VrKBmogaZs/n4coLNw==\\\",\\\"isFloatLayer\\\":true,\\\"signId\\\":\\\"g2kYL2MvMgkaZs/n4coLNw==\\\"}\"}");
        param.put("client","wh5");
        String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
        JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
        logger.info(content);
        if (content.contains("签到成功")){
            logger.info("京东商城-钟表签到成功response: \n" + content);

            if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                Integer beanQuantity = matchInteger(matchForJDBean(content));
                returnParamPojo.setSuccess(1);
                returnParamPojo.setBean(beanQuantity);
                returnParamPojo.setNotify( "京东商城-钟表: 成功, 明细: " + beanQuantity + "京豆 🐶");
            }else {
                returnParamPojo.setSuccess(1);
                returnParamPojo.setNotify( "京东商城-钟表: 成功, 明细: 无京豆");
            }
        }else{
            logger.info("京东商城-钟表签到失败response: \n" + content);
            if (content.contains("已签到")||content.contains("已领取")){
                returnParamPojo.setNotify("京东商城-钟表: 失败, 原因: 已签过");
                returnParamPojo.setFail(1);
            }else if (content.contains("不存在")||content.contains("已结束")){
                returnParamPojo.setNotify("京东商城-钟表: 失败, 原因: 活动已结束!");
                returnParamPojo.setFail(1);
            }else if (jdResponsePojo.getCode()==3){
                returnParamPojo.setNotify("京东商城-钟表: 失败, 原因: Cookie失效‼!");
                returnParamPojo.setFail(1);
            }else {
                returnParamPojo.setNotify("京东商城-钟表: 失败, 原因: 未知 !");
                returnParamPojo.setFail(1);
            }
        }
        return returnParamPojo;
    }
    /**
     * 京东超市
     */
    public ReturnParamPojo JDGroceryStore(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        Map<String,Object> param = new HashMap<>();
        param.put("body","{\"params\":\"{\\\"enActK\\\":\\\"caA6+/To6Jfe/AKYm8gLQEchLXtYeB53heY9YzuzsZoaZs/n4coLNw==\\\",\\\"isFloatLayer\\\":false,\\\"signId\\\":\\\"hEr1TO1FjXgaZs/n4coLNw==\\\"}\"}");
        param.put("screen","750*1334");
        param.put("client","wh5");
        param.put("clientVersion","1.0.0");
        param.put("sid","0ac0caddd8a12bf58ea7a912a5c637cw");
        param.put("uuid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
        param.put("area","19_1617_3643_8208");
        String content = HttpClientTool.doPost(JDGSUrl,Cookie,param,null);
        JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
        logger.info(content);
        if (content.contains("签到成功")){
            logger.info("京东商城-超市签到成功response: \n" + content);

            if (matchForJDBean(content)!=null&&matchInteger(matchForJDBean(content))>0){
                Integer beanQuantity = matchInteger(matchForJDBean(content));
                returnParamPojo.setSuccess(1);
                returnParamPojo.setBean(beanQuantity);
                returnParamPojo.setNotify( "京东商城-超市: 成功, 明细: " + beanQuantity + "京豆 🐶");
            }else {
                returnParamPojo.setSuccess(1);
                returnParamPojo.setNotify( "京东商城-超市: 成功, 明细: 无京豆");
            }
        }else{
            logger.info("京东商城-超市签到失败response: \n" + content);
            if (content.contains("已签到")||content.contains("已领取")){
                returnParamPojo.setNotify("京东商城-超市: 失败, 原因: 已签过");
                returnParamPojo.setFail(1);
            }else if (content.contains("不存在")||content.contains("已结束")){
                returnParamPojo.setNotify("京东商城-超市: 失败, 原因: 活动已结束!");
                returnParamPojo.setFail(1);
            }else if (jdResponsePojo.getCode()==3){
                returnParamPojo.setNotify("京东商城-超市: 失败, 原因: Cookie失效‼!");
                returnParamPojo.setFail(1);
            }else {
                returnParamPojo.setNotify("京东商城-超市: 失败, 原因: 未知 !");
                returnParamPojo.setFail(1);
            }
        }
        return returnParamPojo;
    }

    /**
     *京东双签
     */
    public ReturnParamPojo JRDoubleSign(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        Map<String,Object> param = new HashMap<>();
        param.put("reqData","{\"actCode\"%3A\"FBBFEC496C\"%2C\"type\"%3A3%2C\"riskDeviceParam\"%3A\"\"}");
        String content = HttpClientTool.doPost(JRDSUrl,Cookie,param,null);
        JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
        if (content.contains("京豆X")){
            logger.info("京东金融-双签签到成功response: \\n" + content);
            returnParamPojo.setSuccess(1);
            returnParamPojo.setBean(jdResponsePojo.getResultData().getData().getBusinessData().getBusinessData().getAwardListVo()[1].getCount());
            returnParamPojo.setNotify("京东金融-双签: 成功, 明细:"+ jdResponsePojo.getResultData().getData().getBusinessData().getBusinessData().getAwardListVo()[1].getCount() + "京豆 🐶");
        }else {
            logger.info("京东金融-双签签到失败response: \\n" + content);
            if (content.contains("已领取")){
                returnParamPojo.setNotify("京东金融-双签: 失败, 原因: 已签过");
                returnParamPojo.setFail(1);
            }else if (content.contains("不存在")||content.contains("已结束")){
                returnParamPojo.setNotify("京东金融-双签: 失败, 原因: 活动已结束 !");
                returnParamPojo.setFail(1);
            }else if (content.contains("未在")){
                returnParamPojo.setNotify("京东金融-双签: 失败, 原因: 未在京东签到!");
                returnParamPojo.setFail(1);
            }else if (content.contains("\"resultCode\":3")||content.contains("请先登录")){
                returnParamPojo.setNotify("京东金融-双签: 失败, 原因: Cookie失效！！");
                returnParamPojo.setFail(1);
            }else if (jdResponsePojo.getData().getBusinessData().getBusinessCode().equals("000sq")&&
                    jdResponsePojo.getData().getBusinessData().getBusinessMsg().equals("成功")){
                returnParamPojo.setNotify("京东金融-双签: 成功, 明细: 无奖励");
                returnParamPojo.setSuccess(1);
            }else {
                returnParamPojo.setNotify("京东金融-双签: 失败, 原因: 未知 !");
                returnParamPojo.setFail(1);
            }
        }
        return returnParamPojo;
    }

    /**
     * 京东京豆
     * @return
     */
    public ReturnParamPojo JingDongBean() throws JSONException {
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        //直接请求
        String content  = HttpClientTool.doGet(JingDongBeanUrl,Cookie);
        JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
        logger.info(content);
        if (jdResponsePojo.getCode()==3){
            logger.info("京东商城-京豆Cookie失效response: \n" + content);
            returnParamPojo.setFail(1);
            returnParamPojo.setNotify("京东商城-京豆: 失败, 原因: Cookie失效!!");
        }else {
            if (content.contains("跳转至拼图")){
                returnParamPojo.setNotify("京东商城-京豆: 失败, 原因: 需要拼图验证");
                returnParamPojo.setFail(1);
            }else {
                if (jdResponsePojo.getData().getStatus() == 1){
                    logger.info("京东商城-京豆签到成功response: \n" + jdResponsePojo.getData());
                    if (content.contains("dailyAward")){
                        returnParamPojo.setBean(jdResponsePojo.getData().getDailyAward().getBeanAward().getBeanCount());
                        returnParamPojo.setSuccess(1);
                        returnParamPojo.setNotify("京东商城-京豆: 成功, 明细: " + jdResponsePojo.getData().getDailyAward().getBeanAward().getBeanCount() + "京豆 ");
                    }else {
                        if (content.contains("continuityAward")) {
                            returnParamPojo.setNotify("京东商城-京豆: 成功, 明细: " + jdResponsePojo.getData().getContinuityAward().getBeanAward().getBeanCount() + "京豆 🐶");
                            returnParamPojo.setBean(jdResponsePojo.getData().getContinuityAward().getBeanAward().getBeanCount());
                            returnParamPojo.setSuccess(1);
                        } else {
                            if (content.contains("新人签到")) {
                                //先不考虑了
                                logger.info("新人签到");
//                                returnParamPojo.setNotify("京东商城-京豆: 成功, 明细: " + jdResponsePojo.getData().getContinuityAward().getBeanAward().getBeanCount() + "京豆 🐶");
//                                returnParamPojo.setBean(jdResponsePojo.getData().getContinuityAward().getBeanAward().getBeanCount());
//                                returnParamPojo.setSuccess(1);
                            } else {
                                returnParamPojo.setNotify("京东商城-京豆: 失败, 原因: 未知 ");
                                returnParamPojo.setFail(1);
                            }
                        }
                    }
                }else {
                    logger.info("京东商城-京豆签到失败response: \n" + jdResponsePojo.getData());
                    if(content.contains("已签到")||content.contains("新人签到")){
                        returnParamPojo.setNotify("京东商城-京豆: 失败, 原因: 已签过");
                        returnParamPojo.setFail(1);
                    }else {
                        returnParamPojo.setNotify("京东商城-京豆: 失败, 原因: 未知");
                        returnParamPojo.setFail(1);
                    }
                }
            }
        }
        return returnParamPojo ;
    }

    /**
     * 金融京豆
     * @return
     */
    public ReturnParamPojo JingRongBean() throws UnsupportedEncodingException {
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        Map<String,Object> header = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        header.put("Referer","https://jddx.jd.com/m/money/index.html?from=sign");
        param.put("reqData","{\"bizLine\":2}");
        //logger.info(URLDecoder.decode("reqData=%7B%22bizLine%22%3A2%2C%22signDate%22%3A%221%22%2C%22deviceInfo%22%3A%7B%22os%22%3A%22iOS%22%7D%2C%22clientType%22%3A%22sms%22%2C%22clientVersion%22%3A%2211.0%22%7D","UTF-8"));
        String contentLogin = HttpClientTool.doPost(loginUrl,Cookie,param,header);
        logger.info(contentLogin);
        if (contentLogin.contains("\"login\":true")){
            logger.info("京东金融-京豆登录成功response: \n" + contentLogin);
            Map<String,Object> headerJR = new HashMap<>();
            Map<String,Object> paramJR = new HashMap<>();
            headerJR.put("Referer","https://jddx.jd.com/m/jddnew/money/index.html");
            paramJR.put("reqData","{\"bizLine\":2,\"signDate\":\"1\",\"deviceInfo\":{\"os\":\"iOS\"},\"clientType\":\"sms\",\"clientVersion\":\"11.0\"}");
            String content = HttpClientTool.doPost(JRBUrl,Cookie,paramJR,headerJR);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            if (content.contains("\"resultCode\":\"00000\"")){
                logger.info("京东金融-京豆签到成功response:\n" + content);

                if (jdResponsePojo.getResultData().getData().getRewardAmount()!=0){
                    returnParamPojo.setNotify("京东金融-京豆: 成功, 明细:" + jdResponsePojo.getResultData().getData().getRewardAmount()+ "京豆 ");
                    returnParamPojo.setBean(jdResponsePojo.getResultData().getData().getRewardAmount());
                    returnParamPojo.setSuccess(1);
                }else {
                    returnParamPojo.setNotify("京东金融-京豆: 成功, 明细: 无奖励 ");
                    returnParamPojo.setSuccess(1);
                }
            }else{
                logger.info("京东金融-京豆签到失败response:\n" + content);
                if(content.contains("发放失败")||content.contains("70111")){
                    returnParamPojo.setNotify("京东金融-京豆: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("\"resultCode\":\"3\"")||content.contains("请先登录")){
                    returnParamPojo.setNotify("京东金融-京豆: 失败, 原因: Cookie失效!!");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东金融-京豆: 失败, 原因: 未知!");
                    returnParamPojo.setFail(1);
                }
            }
        }else {
            logger.info("京东金融-京豆登录失败response: \n" + contentLogin);
            if (contentLogin.contains("\"login\":true")){
                returnParamPojo.setNotify("京东金融-京豆: 失败, 原因: Cookie失效!!");
                returnParamPojo.setFail(1);
            }else{
                returnParamPojo.setNotify("京东金融-京豆: 登录接口需修正 !!️");
                returnParamPojo.setFail(1);
            }
        }
        return returnParamPojo;
    }

    /**
     *金融钢镚
     */
    public ReturnParamPojo JingRongSteel(){
        ReturnParamPojo returnParamPojo = new ReturnParamPojo();
        try {
            Map<String,Object> param = new HashMap<>();
            param.put("reqData","{\"channelSource\":\"JRAPP\",\"riskDeviceParam\":\"{}\"}");
            String content= HttpClientTool.doPost(JRSUrl,Cookie,param,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            if (content.contains("\"resBusiCode\":0")){
                logger.info("京东金融-钢镚签到成功response: " + content);
                double leng = jdResponsePojo.getResultData().getResBusiData().getActualTotalRewardsValue();
                returnParamPojo.setNotify("京东金融-钢镚: 成功, 明细:"+leng+"钢镚 ");
                returnParamPojo.setSuccess(1);
                returnParamPojo.setSteel(leng);
            }else {
                logger.info("京东金融-钢镚签到失败response: " + content);
                if (content.contains("已经领取")||content.contains("\"resBusiCode\":15")){
                    returnParamPojo.setNotify("京东金融-钢镚: 失败, 原因: 已签过");
                    returnParamPojo.setFail(1);
                }else if (content.contains("未实名")){
                    returnParamPojo.setNotify("京东金融-钢镚: 失败, 原因: 账号未实名!");
                    returnParamPojo.setFail(1);
                }else if (content.contains("\"resBusiCode\":0")||content.contains("请先登录")){
                    returnParamPojo.setNotify("京东金融-钢镚: 失败, 原因: Cookie失效!!");
                    returnParamPojo.setFail(1);
                }else {
                    returnParamPojo.setNotify("京东金融-钢镚: 失败, 原因: 未知 !");
                    returnParamPojo.setFail(1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("京东金融-钢镚签到异常：" );
            returnParamPojo.setNotify("京东金融-钢镚: 失败, 原因: 未知 !");
            returnParamPojo.setFail(1);
        }
        return returnParamPojo;
    }

    /**
     * 京东转盘
     */
    public ReturnParamPojo JingDongTurn(ReturnParamPojo returnParamPojo) throws InterruptedException {
        if (returnParamPojo.getSuccess()==null){
            returnParamPojo.setSuccess(0);
        }
        if (returnParamPojo.getBean()==null){
            returnParamPojo.setBean(0);
        }
        Map<String,Object> header = new HashMap<>();
        String content = HttpClientTool.doGet(JDTUrl,Cookie);
        JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
        if (jdResponsePojo.getCode() == 3){
            logger.info("京东转盘Cookie失效response: \n"+content);
            returnParamPojo.setNotify("京东商城-转盘: 失败, 原因: Cookie失效 !");
            returnParamPojo.setFail(1);
        }else if (content.contains("\"T216\"")||content.contains("活动结束")){
            returnParamPojo.setNotify("京东商城-转盘: 失败, 原因: 活动结束 !");
            returnParamPojo.setFail(1);
        }else if (content.contains("京豆")||content.contains("910582")){
            logger.info("京东商城-转盘签到成功response: \n"+content);
            if (returnParamPojo.getNotify()==null){
                returnParamPojo.setNotify("京东商城-转盘: 成功, 明细: "+ jdResponsePojo.getData().getPrizeSendNumber()+"京豆(多次)");
            }else {
                returnParamPojo.setNotify("京东商城-转盘: 成功, 明细:"+ jdResponsePojo.getData().getPrizeSendNumber()+"京豆");
            }
            returnParamPojo.setSuccess(returnParamPojo.getSuccess()+1);
            returnParamPojo.setBean(jdResponsePojo.getData().getPrizeSendNumber());
            if (jdResponsePojo.getData().getChances()!=0){
                sleep(2000);
                return JingDongTurn(returnParamPojo);
            }
        }else {
            logger.info("京东商城-转盘签到失败response: \n"+content);
            if (content.contains("未中奖")){
                if (returnParamPojo.getNotify()==null){
                    returnParamPojo.setNotify("京东商城-转盘: 成功, 状态：未中奖: 京豆(多次)");
                }else {
                    returnParamPojo.setNotify("京东商城-转盘: 成功, 状态：未中奖:京豆");
                }
                returnParamPojo.setSuccess(returnParamPojo.getSuccess()+1);
                if (jdResponsePojo.getData().getChances()!=0){
                    sleep(2000);
                    return JingDongTurn(returnParamPojo);
                }
            }else if (content.contains("T215")||content.contains("次数为0")){
                returnParamPojo.setNotify("京东商城-转盘: 失败, 原因: 已转过");
                returnParamPojo.setFail(1);
            }else if (content.contains("T210")||content.contains("密码")){
                returnParamPojo.setNotify("京东商城-转盘: 失败, 原因: 无支付密码 !");
                returnParamPojo.setFail(1);
            }else {
                returnParamPojo.setNotify("京东商城-转盘: 失败, 原因: 未知 !(多次)");
                returnParamPojo.setFail(1);
            }
        }
        return  returnParamPojo;
    }


    /**
     * 总京豆查询
     * @return
     */
    public Integer TotalBean(){
        try {
            Map<String,Object> header = new HashMap<>();
            header.put("Referer","https://wqs.jd.com/my/jingdou/my.shtml?sceneval=2");
            String content  = HttpClientTool.doGet(TotalBeanUrl,Cookie,null,header);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            if (jdResponsePojo.getBase().getJdNum()>0){
                logger.info("京东-总京豆查询成功："+jdResponsePojo.getBase().getJdNum().toString()+"京豆");
                return jdResponsePojo.getBase().getJdNum();
            }else {
                logger.info("京东-总京豆查询失败："+ content);
                return -1;
            }
        }catch (Exception e){
            logger.info("京豆接口查询异常");
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 总钢镚查询
     */
    public Double TotalSteel(){
        try {
            Map<String,Object> header = new HashMap<>();
            String content  = HttpClientTool.doPost(SteelUrl,Cookie,null,null);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            if (jdResponsePojo.getGbBalance()>=0){
                logger.info("京东-总钢镚查询成功："+jdResponsePojo.getGbBalance().toString()+"钢镚");
                return jdResponsePojo.getGbBalance();
            }else {
                logger.info("京东-总钢镚查询失败："+ content);
                return null;
            }
        }catch (Exception e) {
            logger.info("钢镚接口查询异常");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 总红包查询
     */
    public  Double TotalCash(){
        try {
            Map<String,Object> param = new HashMap<>();
            param.put("body","{\"fp\":\"-1\",\"appToken\":\"apphongbao_token\",\"childActivityUrl\":\"-1\",\"country\":\"cn\",\"openId\":\"-1\",\"childActivityId\":\"-1\",\"applicantErp\":\"-1\",\"platformId\":\"appHongBao\",\"isRvc\":\"-1\",\"orgType\":\"2\",\"activityType\":\"1\",\"shshshfpb\":\"-1\",\"platformToken\":\"apphongbao_token\",\"organization\":\"JD\",\"pageClickKey\":\"-1\",\"platform\":\"1\",\"eid\":\"-1\",\"appId\":\"appHongBao\",\"childActiveName\":\"-1\",\"shshshfp\":\"-1\",\"jda\":\"-1\",\"extend\":\"-1\",\"shshshfpa\":\"-1\",\"activityArea\":\"-1\",\"childActivityTime\":\"-1\"}");
            param.put("client","apple");
            param.put("clientVersion","8.5.0");
            param.put("d_brand","apple");
            param.put("networklibtype","JDNetworkBaseAF");
            param.put("openudid","1fce88cd05c42fe2b054e846f11bdf33f016d676");
            param.put("sign","fdc04c3ab0ee9148f947d24fb087b55d");
            param.put("st","1581245397648");
            param.put("sv","120");
            String content  = HttpClientTool.doPost(CashUrl,Cookie,param,null);
            logger.info(content);
            JDResponsePojo jdResponsePojo = JSON.parseObject(content,JDResponsePojo.class);
            if (jdResponsePojo.getTotalBalance()>=0){
                logger.info("京东-总红包查询成功："+jdResponsePojo.getTotalBalance().toString()+"红包");
                return jdResponsePojo.getTotalBalance();
            }else {
                logger.info("京东-总红包查询失败："+ content);
                return null;
            }
        }catch (Exception e) {
            logger.info("红包接口查询异常");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 匹配返回的京豆
     * @param a
     * @return
     */
    private String matchForJDBean(String a){
        String regEx="\\\"text\\\":\\\"\\d京豆";
        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(a);
        if(m.find()){
            return m.group();
        }
        return null;
    }

    /**
     * 提取数字
     *
     */
    private Integer matchInteger(String str){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.valueOf(m.replaceAll("").trim());
    }
    /**/
}
