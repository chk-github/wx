package com.chk.wx.entity;

import lombok.Data;

/**
 * Weather
 *
 * @author chk
 * @since 2022/8/9 16:28
 */
@Data
public class Weather {
    private String weather;
    private String temperature;
    private String dayTemp;
    private String nightTemp;
}
