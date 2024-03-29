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

public class GetPersonGroupInfoResponse  extends AbstractModel {

    /**
    * 包含此人员的人员库及描述字段内容列表
    */
    @SerializedName("PersonGroupInfos")
    @Expose
    private PersonGroupInfo [] PersonGroupInfos;

    /**
    * 人员库总数量
注意：此字段可能返回 null，表示取不到有效值。
    */
    @SerializedName("GroupNum")
    @Expose
    private Integer GroupNum;

    /**
    * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
    */
    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    /**
     * 获取包含此人员的人员库及描述字段内容列表
     * @return PersonGroupInfos 包含此人员的人员库及描述字段内容列表
     */
    public PersonGroupInfo [] getPersonGroupInfos() {
        return this.PersonGroupInfos;
    }

    /**
     * 设置包含此人员的人员库及描述字段内容列表
     * @param PersonGroupInfos 包含此人员的人员库及描述字段内容列表
     */
    public void setPersonGroupInfos(PersonGroupInfo [] PersonGroupInfos) {
        this.PersonGroupInfos = PersonGroupInfos;
    }

    /**
     * 获取人员库总数量
注意：此字段可能返回 null，表示取不到有效值。
     * @return GroupNum 人员库总数量
注意：此字段可能返回 null，表示取不到有效值。
     */
    public Integer getGroupNum() {
        return this.GroupNum;
    }

    /**
     * 设置人员库总数量
注意：此字段可能返回 null，表示取不到有效值。
     * @param GroupNum 人员库总数量
注意：此字段可能返回 null，表示取不到有效值。
     */
    public void setGroupNum(Integer GroupNum) {
        this.GroupNum = GroupNum;
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
        this.setParamArrayObj(map, prefix + "PersonGroupInfos.", this.PersonGroupInfos);
        this.setParamSimple(map, prefix + "GroupNum", this.GroupNum);
        this.setParamSimple(map, prefix + "RequestId", this.RequestId);

    }
}

