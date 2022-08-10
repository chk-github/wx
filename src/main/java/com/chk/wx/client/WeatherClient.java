package com.chk.wx.client;

import com.dtflys.forest.annotation.Get;
import org.springframework.stereotype.Component;

@Component
public interface WeatherClient {

    /**
     * 实况天气
     * @author chk
     * @date 2022/8/10 9:52
     * @return java.lang.String
     **/
    @Get("https://restapi.amap.com/v3/weather/weatherInfo?city=330100&key=67f119bf9beffe42e31ade64b307917c")
    String getLiveWeather();

    /**
     * 预报天气
     * @author chk
     * @date 2022/8/10 9:52
     * @return java.lang.String
     **/
    @Get("https://restapi.amap.com/v3/weather/weatherInfo?city=330100&key=67f119bf9beffe42e31ade64b307917c&extensions=all")
    String getForecastWeather();
}
