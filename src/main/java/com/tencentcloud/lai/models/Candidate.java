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

public class Candidate  extends AbstractModel {

    /**
    * 人员ID
    */
    @SerializedName("PersonId")
    @Expose
    private String PersonId;

    /**
    * 人脸ID
    */
    @SerializedName("FaceId")
    @Expose
    private String FaceId;

    /**
    * 候选者的匹配得分。 
10万大小人脸库，若人脸均为类似抓拍照（人脸质量较差）， 
误识率百分之一对应分数为70分，误识率千分之一对应分数为80分，误识率万分之一对应分数为90分； 
若人脸均为类似自拍照（人脸质量较好）， 
误识率百分之一对应分数为60分，误识率千分之一对应分数为70分，误识率万分之一对应分数为80分。 
建议分数不要超过90分。您可以根据实际情况选择合适的分数。
    */
    @SerializedName("Score")
    @Expose
    private Float Score;

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
     * 获取人脸ID
     * @return FaceId 人脸ID
     */
    public String getFaceId() {
        return this.FaceId;
    }

    /**
     * 设置人脸ID
     * @param FaceId 人脸ID
     */
    public void setFaceId(String FaceId) {
        this.FaceId = FaceId;
    }

    /**
     * 获取候选者的匹配得分。 
10万大小人脸库，若人脸均为类似抓拍照（人脸质量较差）， 
误识率百分之一对应分数为70分，误识率千分之一对应分数为80分，误识率万分之一对应分数为90分； 
若人脸均为类似自拍照（人脸质量较好）， 
误识率百分之一对应分数为60分，误识率千分之一对应分数为70分，误识率万分之一对应分数为80分。 
建议分数不要超过90分。您可以根据实际情况选择合适的分数。
     * @return Score 候选者的匹配得分。 
10万大小人脸库，若人脸均为类似抓拍照（人脸质量较差）， 
误识率百分之一对应分数为70分，误识率千分之一对应分数为80分，误识率万分之一对应分数为90分； 
若人脸均为类似自拍照（人脸质量较好）， 
误识率百分之一对应分数为60分，误识率千分之一对应分数为70分，误识率万分之一对应分数为80分。 
建议分数不要超过90分。您可以根据实际情况选择合适的分数。
     */
    public Float getScore() {
        return this.Score;
    }

    /**
     * 设置候选者的匹配得分。 
10万大小人脸库，若人脸均为类似抓拍照（人脸质量较差）， 
误识率百分之一对应分数为70分，误识率千分之一对应分数为80分，误识率万分之一对应分数为90分； 
若人脸均为类似自拍照（人脸质量较好）， 
误识率百分之一对应分数为60分，误识率千分之一对应分数为70分，误识率万分之一对应分数为80分。 
建议分数不要超过90分。您可以根据实际情况选择合适的分数。
     * @param Score 候选者的匹配得分。 
10万大小人脸库，若人脸均为类似抓拍照（人脸质量较差）， 
误识率百分之一对应分数为70分，误识率千分之一对应分数为80分，误识率万分之一对应分数为90分； 
若人脸均为类似自拍照（人脸质量较好）， 
误识率百分之一对应分数为60分，误识率千分之一对应分数为70分，误识率万分之一对应分数为80分。 
建议分数不要超过90分。您可以根据实际情况选择合适的分数。
     */
    public void setScore(Float Score) {
        this.Score = Score;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "PersonId", this.PersonId);
        this.setParamSimple(map, prefix + "FaceId", this.FaceId);
        this.setParamSimple(map, prefix + "Score", this.Score);

    }

}

