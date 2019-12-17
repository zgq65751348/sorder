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

public class SearchFacesRequest  extends AbstractModel {

    /**
    * 希望搜索的人员库列表，上限100个。
    */
    @SerializedName("GroupIds")
    @Expose
    private String [] GroupIds;

    /**
    * 图片 base64 数据。支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
    */
    @SerializedName("Image")
    @Expose
    private String Image;

    /**
    * 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
    */
    @SerializedName("Url")
    @Expose
    private String Url;

    /**
    * 最多处理的人脸数目。默认值为1（仅检测图片中面积最大的那张人脸），最大值为10。 
MaxFaceNum用于，当待识别图片包含多张人脸时，要搜索的人脸数量。 
当 MaxFaceNum 不为1时，设MaxFaceNum=M，则实际上是 M:N 的人脸搜索。
    */
    @SerializedName("MaxFaceNum")
    @Expose
    private Integer MaxFaceNum;

    /**
    * 人脸长和宽的最小尺寸，单位为像素。默认为80。低于40将影响搜索精度。建议设置为80。
    */
    @SerializedName("MinFaceSize")
    @Expose
    private Integer MinFaceSize;

    /**
    * 被检测到的人脸，对应最多返回的最相似人员数目。默认值为5，最大值为10。  
例，设MaxFaceNum为3，MaxPersonNum为5，则最多可能返回3*5=15个人员。
    */
    @SerializedName("MaxPersonNum")
    @Expose
    private Integer MaxPersonNum;

    /**
     * 获取希望搜索的人员库列表，上限100个。
     * @return GroupIds 希望搜索的人员库列表，上限100个。
     */
    public String [] getGroupIds() {
        return this.GroupIds;
    }

    /**
     * 设置希望搜索的人员库列表，上限100个。
     * @param GroupIds 希望搜索的人员库列表，上限100个。
     */
    public void setGroupIds(String [] GroupIds) {
        this.GroupIds = GroupIds;
    }

    /**
     * 获取图片 base64 数据。支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @return Image 图片 base64 数据。支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public String getImage() {
        return this.Image;
    }

    /**
     * 设置图片 base64 数据。支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @param Image 图片 base64 数据。支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public void setImage(String Image) {
        this.Image = Image;
    }

    /**
     * 获取图片的 Url、Image必须提供一个，如果都提供，只使用 Url。
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @return Url 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public String getUrl() {
        return this.Url;
    }

    /**
     * 设置图片的 Url、Image必须提供一个，如果都提供，只使用 Url。
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     * @param Url 图片的 Url、Image必须提供一个，如果都提供，只使用 Url。
图片存储于腾讯云的Url可保障更高下载速度和稳定性，建议图片存储于腾讯云。 
非腾讯云存储的Url速度和稳定性可能受一定影响。
支持PNG、JPG、JPEG、BMP，不支持 GIF 图片。
     */
    public void setUrl(String Url) {
        this.Url = Url;
    }

    /**
     * 获取最多处理的人脸数目。默认值为1（仅检测图片中面积最大的那张人脸），最大值为10。 
MaxFaceNum用于，当待识别图片包含多张人脸时，要搜索的人脸数量。 
当 MaxFaceNum 不为1时，设MaxFaceNum=M，则实际上是 M:N 的人脸搜索。
     * @return MaxFaceNum 最多处理的人脸数目。默认值为1（仅检测图片中面积最大的那张人脸），最大值为10。 
MaxFaceNum用于，当待识别图片包含多张人脸时，要搜索的人脸数量。 
当 MaxFaceNum 不为1时，设MaxFaceNum=M，则实际上是 M:N 的人脸搜索。
     */
    public Integer getMaxFaceNum() {
        return this.MaxFaceNum;
    }

    /**
     * 设置最多处理的人脸数目。默认值为1（仅检测图片中面积最大的那张人脸），最大值为10。 
MaxFaceNum用于，当待识别图片包含多张人脸时，要搜索的人脸数量。 
当 MaxFaceNum 不为1时，设MaxFaceNum=M，则实际上是 M:N 的人脸搜索。
     * @param MaxFaceNum 最多处理的人脸数目。默认值为1（仅检测图片中面积最大的那张人脸），最大值为10。 
MaxFaceNum用于，当待识别图片包含多张人脸时，要搜索的人脸数量。 
当 MaxFaceNum 不为1时，设MaxFaceNum=M，则实际上是 M:N 的人脸搜索。
     */
    public void setMaxFaceNum(Integer MaxFaceNum) {
        this.MaxFaceNum = MaxFaceNum;
    }

    /**
     * 获取人脸长和宽的最小尺寸，单位为像素。默认为80。低于40将影响搜索精度。建议设置为80。
     * @return MinFaceSize 人脸长和宽的最小尺寸，单位为像素。默认为80。低于40将影响搜索精度。建议设置为80。
     */
    public Integer getMinFaceSize() {
        return this.MinFaceSize;
    }

    /**
     * 设置人脸长和宽的最小尺寸，单位为像素。默认为80。低于40将影响搜索精度。建议设置为80。
     * @param MinFaceSize 人脸长和宽的最小尺寸，单位为像素。默认为80。低于40将影响搜索精度。建议设置为80。
     */
    public void setMinFaceSize(Integer MinFaceSize) {
        this.MinFaceSize = MinFaceSize;
    }

    /**
     * 获取被检测到的人脸，对应最多返回的最相似人员数目。默认值为5，最大值为10。  
例，设MaxFaceNum为3，MaxPersonNum为5，则最多可能返回3*5=15个人员。
     * @return MaxPersonNum 被检测到的人脸，对应最多返回的最相似人员数目。默认值为5，最大值为10。  
例，设MaxFaceNum为3，MaxPersonNum为5，则最多可能返回3*5=15个人员。
     */
    public Integer getMaxPersonNum() {
        return this.MaxPersonNum;
    }

    /**
     * 设置被检测到的人脸，对应最多返回的最相似人员数目。默认值为5，最大值为10。  
例，设MaxFaceNum为3，MaxPersonNum为5，则最多可能返回3*5=15个人员。
     * @param MaxPersonNum 被检测到的人脸，对应最多返回的最相似人员数目。默认值为5，最大值为10。  
例，设MaxFaceNum为3，MaxPersonNum为5，则最多可能返回3*5=15个人员。
     */
    public void setMaxPersonNum(Integer MaxPersonNum) {
        this.MaxPersonNum = MaxPersonNum;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamArraySimple(map, prefix + "GroupIds.", this.GroupIds);
        this.setParamSimple(map, prefix + "Image", this.Image);
        this.setParamSimple(map, prefix + "Url", this.Url);
        this.setParamSimple(map, prefix + "MaxFaceNum", this.MaxFaceNum);
        this.setParamSimple(map, prefix + "MinFaceSize", this.MinFaceSize);
        this.setParamSimple(map, prefix + "MaxPersonNum", this.MaxPersonNum);

    }
}

