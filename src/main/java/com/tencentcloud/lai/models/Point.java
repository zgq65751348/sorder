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

public class Point  extends AbstractModel {

    /**
    * x坐标
    */
    @SerializedName("X")
    @Expose
    private Integer X;

    /**
    * Y坐标
    */
    @SerializedName("Y")
    @Expose
    private Integer Y;

    /**
     * 获取x坐标
     * @return X x坐标
     */
    public Integer getX() {
        return this.X;
    }

    /**
     * 设置x坐标
     * @param X x坐标
     */
    public void setX(Integer X) {
        this.X = X;
    }

    /**
     * 获取Y坐标
     * @return Y Y坐标
     */
    public Integer getY() {
        return this.Y;
    }

    /**
     * 设置Y坐标
     * @param Y Y坐标
     */
    public void setY(Integer Y) {
        this.Y = Y;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "X", this.X);
        this.setParamSimple(map, prefix + "Y", this.Y);

    }
}

