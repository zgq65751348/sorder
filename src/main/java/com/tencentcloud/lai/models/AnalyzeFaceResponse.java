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

public class AnalyzeFaceResponse  extends AbstractModel {

    /**
    * 请求的图片宽度。
    */
    @SerializedName("ImageWidth")
    @Expose
    private Integer ImageWidth;

    /**
    * 请求的图片高度。
    */
    @SerializedName("ImageHeight")
    @Expose
    private Integer ImageHeight;

    /**
    * 五官定位（人脸关键点）具体信息。
    */
    @SerializedName("FaceShapeSet")
    @Expose
    private FaceShape [] FaceShapeSet;

    /**
    * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
    */
    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    /**
     * 获取请求的图片宽度。
     * @return ImageWidth 请求的图片宽度。
     */
    public Integer getImageWidth() {
        return this.ImageWidth;
    }

    /**
     * 设置请求的图片宽度。
     * @param ImageWidth 请求的图片宽度。
     */
    public void setImageWidth(Integer ImageWidth) {
        this.ImageWidth = ImageWidth;
    }

    /**
     * 获取请求的图片高度。
     * @return ImageHeight 请求的图片高度。
     */
    public Integer getImageHeight() {
        return this.ImageHeight;
    }

    /**
     * 设置请求的图片高度。
     * @param ImageHeight 请求的图片高度。
     */
    public void setImageHeight(Integer ImageHeight) {
        this.ImageHeight = ImageHeight;
    }

    /**
     * 获取五官定位（人脸关键点）具体信息。
     * @return FaceShapeSet 五官定位（人脸关键点）具体信息。
     */
    public FaceShape [] getFaceShapeSet() {
        return this.FaceShapeSet;
    }

    /**
     * 设置五官定位（人脸关键点）具体信息。
     * @param FaceShapeSet 五官定位（人脸关键点）具体信息。
     */
    public void setFaceShapeSet(FaceShape [] FaceShapeSet) {
        this.FaceShapeSet = FaceShapeSet;
    }

    /**
     * 获取唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @return RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public String getRequestId() {
        return this.RequestId;
    }

    /**
     * 设置唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @param RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "ImageWidth", this.ImageWidth);
        this.setParamSimple(map, prefix + "ImageHeight", this.ImageHeight);
        this.setParamArrayObj(map, prefix + "FaceShapeSet.", this.FaceShapeSet);
        this.setParamSimple(map, prefix + "RequestId", this.RequestId);

    }
}

