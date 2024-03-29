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

public class PersonInfo  extends AbstractModel {

    /**
    * 人员名称
    */
    @SerializedName("PersonName")
    @Expose
    private String PersonName;

    /**
    * 人员Id
    */
    @SerializedName("PersonId")
    @Expose
    private String PersonId;

    /**
    * 人员性别
    */
    @SerializedName("Gender")
    @Expose
    private Integer Gender;

    /**
    * 人员描述字段内容
    */
    @SerializedName("PersonExDescriptions")
    @Expose
    private String [] PersonExDescriptions;

    /**
    * 包含的人脸照片列表
    */
    @SerializedName("FaceIds")
    @Expose
    private String [] FaceIds;

    /**
     * 获取人员名称
     * @return PersonName 人员名称
     */
    public String getPersonName() {
        return this.PersonName;
    }

    /**
     * 设置人员名称
     * @param PersonName 人员名称
     */
    public void setPersonName(String PersonName) {
        this.PersonName = PersonName;
    }

    /**
     * 获取人员Id
     * @return PersonId 人员Id
     */
    public String getPersonId() {
        return this.PersonId;
    }

    /**
     * 设置人员Id
     * @param PersonId 人员Id
     */
    public void setPersonId(String PersonId) {
        this.PersonId = PersonId;
    }

    /**
     * 获取人员性别
     * @return Gender 人员性别
     */
    public Integer getGender() {
        return this.Gender;
    }

    /**
     * 设置人员性别
     * @param Gender 人员性别
     */
    public void setGender(Integer Gender) {
        this.Gender = Gender;
    }

    /**
     * 获取人员描述字段内容
     * @return PersonExDescriptions 人员描述字段内容
     */
    public String [] getPersonExDescriptions() {
        return this.PersonExDescriptions;
    }

    /**
     * 设置人员描述字段内容
     * @param PersonExDescriptions 人员描述字段内容
     */
    public void setPersonExDescriptions(String [] PersonExDescriptions) {
        this.PersonExDescriptions = PersonExDescriptions;
    }

    /**
     * 获取包含的人脸照片列表
     * @return FaceIds 包含的人脸照片列表
     */
    public String [] getFaceIds() {
        return this.FaceIds;
    }

    /**
     * 设置包含的人脸照片列表
     * @param FaceIds 包含的人脸照片列表
     */
    public void setFaceIds(String [] FaceIds) {
        this.FaceIds = FaceIds;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "PersonName", this.PersonName);
        this.setParamSimple(map, prefix + "PersonId", this.PersonId);
        this.setParamSimple(map, prefix + "Gender", this.Gender);
        this.setParamArraySimple(map, prefix + "PersonExDescriptions.", this.PersonExDescriptions);
        this.setParamArraySimple(map, prefix + "FaceIds.", this.FaceIds);

    }
}

