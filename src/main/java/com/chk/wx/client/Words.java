package com.chk.wx.client;

import com.dtflys.forest.annotation.Get;
import org.springframework.stereotype.Component;

@Component
public interface Words {
    @Get("https://chp.shadiao.app/")
    String get();
}
