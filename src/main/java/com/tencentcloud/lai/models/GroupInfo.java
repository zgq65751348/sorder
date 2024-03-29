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

public class GroupInfo  extends AbstractModel {

    /**
    * 人员库名称
    */
    @SerializedName("GroupName")
    @Expose
    private String GroupName;

    /**
    * 人员库ID
    */
    @SerializedName("GroupId")
    @Expose
    private String GroupId;

    /**
    * 人员库自定义描述字段
注意：此字段可能返回 null，表示取不到有效值。
    */
    @SerializedName("GroupExDescriptions")
    @Expose
    private String [] GroupExDescriptions;

    /**
    * 人员库信息备注
注意：此字段可能返回 null，表示取不到有效值。
    */
    @SerializedName("Tag")
    @Expose
    private String Tag;

    /**
     * 获取人员库名称
     * @return GroupName 人员库名称
     */
    public String getGroupName() {
        return this.GroupName;
    }

    /**
     * 设置人员库名称
     * @param GroupName 人员库名称
     */
    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    /**
     * 获取人员库ID
     * @return GroupId 人员库ID
     */
    public String getGroupId() {
        return this.GroupId;
    }

    /**
     * 设置人员库ID
     * @param GroupId 人员库ID
     */
    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }

    /**
     * 获取人员库自定义描述字段
注意：此字段可能返回 null，表示取不到有效值。
     * @return GroupExDescriptions 人员库自定义描述字段
注意：此字段可能返回 null，表示取不到有效值。
     */
    public String [] getGroupExDescriptions() {
        return this.GroupExDescriptions;
    }

    /**
     * 设置人员库自定义描述字段
注意：此字段可能返回 null，表示取不到有效值。
     * @param GroupExDescriptions 人员库自定义描述字段
注意：此字段可能返回 null，表示取不到有效值。
     */
    public void setGroupExDescriptions(String [] GroupExDescriptions) {
        this.GroupExDescriptions = GroupExDescriptions;
    }

    /**
     * 获取人员库信息备注
注意：此字段可能返回 null，表示取不到有效值。
     * @return Tag 人员库信息备注
注意：此字段可能返回 null，表示取不到有效值。
     */
    public String getTag() {
        return this.Tag;
    }

    /**
     * 设置人员库信息备注
注意：此字段可能返回 null，表示取不到有效值。
     * @param Tag 人员库信息备注
注意：此字段可能返回 null，表示取不到有效值。
     */
    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "GroupName", this.GroupName);
        this.setParamSimple(map, prefix + "GroupId", this.GroupId);
        this.setParamArraySimple(map, prefix + "GroupExDescriptions.", this.GroupExDescriptions);
        this.setParamSimple(map, prefix + "Tag", this.Tag);

    }
}

