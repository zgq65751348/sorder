package com.tlongx.sorder.utils;

import com.tencentcloud.lai.IaiClient;
import com.tencentcloud.lai.models.*;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tlongx.common.Constants;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.user.pojo.User;
import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云人脸识别相关工具类
 */
@Slf4j
public class FaceUtil {

    /**
     * 初始客户端
     * @return
     */
    static IaiClient init() {
        Credential cred = new Credential(Constants.TencentSecretId, Constants.TencentSecretKey);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("iai.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);
        return client;
    }

    /**
     * 创建人脸库
     * @param groupId
     * @param groupName
     */
    public static void createGroup(Integer groupId, String groupName){
        try{
            IaiClient client=init();
            String params = "{\"GroupName\":\""+groupName+"\",\"GroupId\":\""+groupId+"\"}";
            CreateGroupRequest req = CreateGroupRequest.fromJsonString(params, CreateGroupRequest.class);
            CreateGroupResponse resp = client.CreateGroup(req);
            log.info(CreateGroupRequest.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            throw new CodeException(Constants.OPERATE_FAIL_CODE,e.toString());
        }
    }


    /**
     * 添加人脸
     * @param user
     * @param url
     */
    public static void addFace(User user,String url){
        String params = "{\"GroupId\":\""+user.getSid()+"\",\"PersonName\":\""+user.getUsername()+"\",\"PersonId\":\""+user.getUid()+"\",\"Url\":\""+url+"\"}";
        CreatePersonRequest req = CreatePersonRequest.fromJsonString(params, CreatePersonRequest.class);
        try {
            IaiClient client=init();
            CreatePersonResponse resp = client.CreatePerson(req);
            log.info(CreateFaceResponse.toJsonString(req));
        } catch (TencentCloudSDKException e) {
            throw new CodeException(Constants.OPERATE_FAIL_CODE,e.toString());
        }
    }

    /**
     * 人脸验证
     * @param uid
     * @param url
     */
    public static boolean verifyFace(String uid,String url){
        boolean result=false;
        try{
            IaiClient client=init();
            String params = "{\"Url\":\""+url+"\",\"PersonId\":\""+uid+"\"}";
            VerifyFaceRequest req = VerifyFaceRequest.fromJsonString(params, VerifyFaceRequest.class);
            VerifyFaceResponse resp = client.VerifyFace(req);
            log.info(VerifyFaceRequest.toJsonString(resp));
            if (resp.getIsMatch()){
                result=true;
            }
        } catch (TencentCloudSDKException e) {
            throw new CodeException(Constants.OPERATE_FAIL_CODE,e.toString());
        }
        return result;
    }

    /**
     * 人脸检测与分析
     * @param url
     */
    public static void detectFace(String url){
        try{
            IaiClient client=init();
            String params = "{\"Url\":\""+url+"\",\"NeedFaceAttributes\":1,\"NeedQualityDetection\":1}";
            DetectFaceRequest req = DetectFaceRequest.fromJsonString(params, DetectFaceRequest.class);
            DetectFaceResponse resp = client.DetectFace(req);
            log.info(DetectFaceRequest.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            throw new CodeException(Constants.OPERATE_FAIL_CODE,e.toString());
        }
    }

    /**
     * 人脸搜索
     * @return personId
     */
    public static String searchFaces(String sid,String url){
        String params = "{\"GroupIds\":[\""+sid+"\"],\"Url\":\""+url+"\",\"MaxFaceNum\":1}";
        SearchFacesRequest req = SearchFacesRequest.fromJsonString(params, SearchFacesRequest.class);
        String personId;
        try {
            IaiClient client=init();
            SearchFacesResponse resp = client.SearchFaces(req);
            Result result=resp.getResults()[0];
            log.info("result的score：{}",result.getCandidates()[0].getScore()<80.00);
            if (result.getCandidates()[0].getScore()<80.00){
                throw new CodeException(ErrorEnum.Not_Found_Face);
            }
            personId=result.getCandidates()[0].getPersonId();
        } catch (TencentCloudSDKException e) {
            throw new CodeException(Constants.OPERATE_FAIL_CODE,e.toString());
        }
        return personId;
    }

    /**
     * 删除人员
     * @param personId
     * @return
     */
    public static Boolean deletePerson(String personId){
        String params = "{\"PersonId\":\""+personId+"\"}";
        DeletePersonRequest req = DeletePersonRequest.fromJsonString(params, DeletePersonRequest.class);

        IaiClient client=init();
        try {
            DeletePersonResponse resp = client.DeletePerson(req);
            log.info("删除人员的响应结果：{}",resp);
            return true;
        } catch (TencentCloudSDKException e) {
            //throw new CodeException(Constants.OPERATE_FAIL_CODE,e.toString());
            log.info("删除人员失败：{}",e.toString());
            return false;
        }
    }
}
