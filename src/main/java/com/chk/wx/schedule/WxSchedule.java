package com.chk.wx.schedule;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chk.wx.client.WeatherClient;
import com.chk.wx.client.Words;
import com.chk.wx.client.WxClient;
import com.chk.wx.config.WxConfig;
import com.chk.wx.entity.Weather;
import com.chk.wx.entity.WxRequest;
import com.chk.wx.service.WxService;
import com.chk.wx.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 微信定时任务
 *
 * @author chk
 * @since 2022/4/14 11:56
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WxSchedule {

    /**
     * 微信token的key
     */
    public static final String ACCESS_TOKEN = "access_token";
    private final WxConfig wxConfig;
    private final WxClient wxClient;
    private final WeatherClient weatherClient;
    private final WxService wxService;
    private final Words words;

    /**
     * 定时获取微信token
     *
     * @author chk
     * @date 2022/4/14 12:02
     **/
    @Scheduled(fixedDelay = 1000 * 60 * 90)
    public void getToken() {
        wxClient.getToken(wxConfig.getAppId(), wxConfig.getAppSecret(),
                //响应成功时执行
                (data, request, response) -> {
                    wxConfig.setToken((String) data.get(ACCESS_TOKEN));
                    log.info("获取到的微信token为:{}", wxConfig.getToken());
                });
    }

    /**
     * 定时发送模板消息
     **/
    @Scheduled(cron = "0 0 7 * * ?")
    //@Scheduled(cron = "0 * * * * ?")
    public void sendOne() {
        //每日一句
        //String words1 = getWords();
        //获取天气
        Weather weather = getWeather();
        //模板消息内容
        WxRequest wxRequest = new WxRequest();
        wxRequest.setToUsers(wxConfig.getUserList());
        wxRequest.setTemplateId(wxConfig.getOneMsgId());
        //模板消息内容拼接
        LinkedHashMap<String, Object> data = getData(weather);
        wxRequest.setData(JSONUtil.toJsonStr(data));
        //发送模板消息
        wxService.push(wxRequest);
    }

    @NotNull
    private static LinkedHashMap<String, Object> getData(Weather weather) {
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        HashMap<String, String> first = new HashMap<>();
        first.put("value", "宝贝早安！");
        first.put("color","#27F0EC");
        data.put("first", first);


        HashMap<String, String> keyword1 = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        keyword1.put("value", simpleDateFormat.format(new Date())+"   "+ TimeUtil.getWeek());
        data.put("keyword1", keyword1);


        HashMap<String, String> keyword2 = new HashMap<>();
        //天气
        keyword2.put("value", weather.getWeather());
        keyword2.put("color","#A5FF85");
        data.put("keyword2", keyword2);

        HashMap<String, String> keyword3 = new HashMap<>();
        //当前气温
        keyword3.put("value", weather.getTemperature()+"摄氏度");
        data.put("keyword3", keyword3);
        HashMap<String, String> keyword4 = new HashMap<>();
        //最高气温
        keyword4.put("value", weather.getDayTemp()+"摄氏度");
        keyword4.put("color","#FF1509");
        data.put("keyword4", keyword4);
        HashMap<String, String> keyword5 = new HashMap<>();
        //最低气温
        keyword5.put("value", weather.getNightTemp()+"摄氏度");
        keyword5.put("color","#90F9FF");
        data.put("keyword5", keyword5);

        HashMap<String, String> remark = new HashMap<>();
        remark.put("value", "爱你每一天");
        remark.put("color","#F0ABD8");
        data.put("remark", remark);
        return data;
    }

    private Weather getWeather() {
        Weather data = new Weather();
        //获取实况天气
        String liveWeather = weatherClient.getLiveWeather();
        JSONObject liveWeatherJson = JSONUtil.parseObj(liveWeather);
        JSONObject lives = liveWeatherJson.getJSONArray("lives").getJSONObject(0);
        //天气中文描述
        data.setWeather(lives.getStr("weather"));
        //当前气温
        data.setTemperature(lives.getStr("temperature"));
        //获取预报天气
        String forecastWeather = weatherClient.getForecastWeather();
        JSONObject forecastWeatherJson = JSONUtil.parseObj(forecastWeather);
        JSONObject forecasts = forecastWeatherJson.getJSONArray("forecasts").getJSONObject(0);
        JSONObject casts = forecasts.getJSONArray("casts").getJSONObject(0);
        //最高气温
        data.setDayTemp(casts.getStr("daytemp"));
        //最低气温
        data.setNightTemp(casts.getStr("nighttemp"));
        return data;
    }

    private String getWords(){
        String data = words.get();
        System.out.println(data);
        return null;
    }

}
