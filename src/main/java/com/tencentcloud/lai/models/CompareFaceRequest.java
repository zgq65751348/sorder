/*
 * Copyright (c) 2017-2018 THL A29 Limited, a Tencent company. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tencentcloud.lai.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tencentcloudapi.common.AbstractModel;

import java.util.HashMap;

public class CompareFaceRequest  extends AbstractModel {

    /**
    * A 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
    */
    @SerializedName("ImageA")
    @Expose
    private String ImageA;

    /**
    * B 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
    */
    @SerializedName("ImageB")
    @Expose
    private String ImageB;

    /**
    * A 图片的 Url 。A 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
    */
    @SerializedName("UrlA")
    @Expose
    private String UrlA;

    /**
    * B 图片的 Url 。B 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
    */
    @SerializedName("UrlB")
    @Expose
    private String UrlB;

    /**
     * 获取A 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @return ImageA A 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public String getImageA() {
        return this.ImageA;
    }

    /**
     * 设置A 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @param ImageA A 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public void setImageA(String ImageA) {
        this.ImageA = ImageA;
    }

    /**
     * 获取B 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @return ImageB B 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public String getImageB() {
        return this.ImageB;
    }

    /**
     * 设置B 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @param ImageB B 图片 base64 数据。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public void setImageB(String ImageB) {
        this.ImageB = ImageB;
    }

    /**
     * 获取A 图片的 Url 。A 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @return UrlA A 图片的 Url 。A 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public String getUrlA() {
        return this.UrlA;
    }

    /**
     * 设置A 图片的 Url 。A 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @param UrlA A 图片的 Url 。A 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public void setUrlA(String UrlA) {
        this.UrlA = UrlA;
    }

    /**
     * 获取B 图片的 Url 。B 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @return UrlB B 图片的 Url 。B 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public String getUrlB() {
        return this.UrlB;
    }

    /**
     * 设置B 图片的 Url 。B 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @param UrlB B 图片的 Url 。B 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。 
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
若图片中包含多张人脸，只选取其中人脸面积最大的人脸。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public void setUrlB(String UrlB) {
        this.UrlB = UrlB;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "ImageA", this.ImageA);
        this.setParamSimple(map, prefix + "ImageB", this.ImageB);
        this.setParamSimple(map, prefix + "UrlA", this.UrlA);
        this.setParamSimple(map, prefix + "UrlB", this.UrlB);

    }
}

