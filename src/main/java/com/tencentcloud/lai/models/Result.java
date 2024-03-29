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

public class Result  extends AbstractModel {

    /**
    * 识别出的最相似候选人
    */
    @SerializedName("Candidates")
    @Expose
    private Candidate [] Candidates;

    /**
    * 检测出的人脸框位置
    */
    @SerializedName("FaceRect")
    @Expose
    private FaceRect FaceRect;

    /**
     * 获取识别出的最相似候选人
     * @return Candidates 识别出的最相似候选人
     */
    public Candidate [] getCandidates() {
        return this.Candidates;
    }

    /**
     * 设置识别出的最相似候选人
     * @param Candidates 识别出的最相似候选人
     */
    public void setCandidates(Candidate [] Candidates) {
        this.Candidates = Candidates;
    }

    /**
     * 获取检测出的人脸框位置
     * @return FaceRect 检测出的人脸框位置
     */
    public FaceRect getFaceRect() {
        return this.FaceRect;
    }

    /**
     * 设置检测出的人脸框位置
     * @param FaceRect 检测出的人脸框位置
     */
    public void setFaceRect(FaceRect FaceRect) {
        this.FaceRect = FaceRect;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamArrayObj(map, prefix + "Candidates.", this.Candidates);
        this.setParamObj(map, prefix + "FaceRect.", this.FaceRect);

    }
}

