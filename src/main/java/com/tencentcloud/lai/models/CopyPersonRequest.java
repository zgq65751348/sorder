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

public class CopyPersonRequest  extends AbstractModel {

    /**
    * 人员ID
    */
    @SerializedName("PersonId")
    @Expose
    private String PersonId;

    /**
    * 待加入的人员库列表
    */
    @SerializedName("GroupIds")
    @Expose
    private String [] GroupIds;

    /**
     * 获取人员ID
     * @return PersonId 人员ID
     */
    public String getPersonId() {
        return this.PersonId;
    }

    /**
     * 设置人员ID
     * @param PersonId 人员ID
     */
    public void setPersonId(String PersonId) {
        this.PersonId = PersonId;
    }

    /**
     * 获取待加入的人员库列表
     * @return GroupIds 待加入的人员库列表
     */
    public String [] getGroupIds() {
        return this.GroupIds;
    }

    /**
     * 设置待加入的人员库列表
     * @param GroupIds 待加入的人员库列表
     */
    public void setGroupIds(String [] GroupIds) {
        this.GroupIds = GroupIds;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "PersonId", this.PersonId);
        this.setParamArraySimple(map, prefix + "GroupIds.", this.GroupIds);

    }
}
