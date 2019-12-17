package com.tlongx.sorder.controller;

import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import com.tlongx.common.enums.ResourceBusiness;
import com.tlongx.common.enums.ResourceType;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.utils.FileStorageService;
import com.tlongx.sorder.utils.MyUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("file")
@Slf4j
public class FileUploadController{

    @Autowired
    private FileStorageService fileStorageService;

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "文件", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "文件类型，IMAGE:图片，VOICE：语音，VIDEO：视频，HTML：网页，FILE：文件", required = true),
            @ApiImplicitParam(paramType = "query", name = "business", value = "业务类型，AVATAR:头像，SYS：系统，USER：用户，FEEDBACK：意见反馈，FILE：ATTACH：附件，DOC：文档，PHOTO：个人图集，ESSAY，朋友圈", required = true)})
    public TLResult uploadFile(MultipartFile[] file, ResourceType type, ResourceBusiness business) {
        if (StringUtils.isEmpty(file) || StringUtils.isEmpty(type) || StringUtils.isEmpty(business)) {
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
        List<String> list=new ArrayList<>();
        log.info(file.length+"");
        for (MultipartFile multipartFile:file){
            list.add(fileStorageService.storeFile(multipartFile, type, business));
        }
        return TLResult.ok(list);
    }

    @GetMapping("/oss/{type}/{business}/{fileName:.+}")
    @ApiOperation(value = "获取文件", notes = "获取文件")
    @ApiImplicitParams({@ApiImplicitParam(name = "fileName", paramType = "path", value = "文件名称", required = true)})
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @PathVariable ResourceType type, @PathVariable ResourceBusiness business, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName, type, business);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.info("counld not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(resource);
    }

//    @PostMapping(value="/upload", consumes="multipart/*",headers="content-type=multipart/form-data")
//    public TLResult uploadFile(HttpServletRequest request, @ApiParam(value="上传的文件",required=true)@RequestParam("uploadFile")MultipartFile[] files) throws IOException {
//        List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
//        log.info("文件数量"+files.length);
//            if (files.length>0) {
//
//                for (MultipartFile multipartFile : files) {
//                    Map<String, Object> resultMap = new HashMap<String, Object>();
//                    String fileName = multipartFile.getOriginalFilename();
//                    if("".equals(fileName)) {
//                        break;
//                    }
//                    log.info("fileName = "+fileName);
//                    String type = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/")+1);
//                    log.info("上传文件类型 是 png jpg ....  "+type);
//                    String subString = fileName.substring(fileName.indexOf("."));
//                    log.info("获取文件后缀 = "+subString);
//
//                    ServletContext sc = request.getServletContext();
//                    String path = sc.getRealPath("/upload");
//                    log.info("打印存储地址 = "+path);
//                    String datePath = (new StringBuilder("/")).append(MyUtil.formStrFormat(new Date(), "yyyyMMdd")).toString();
//                    String relPath = (new StringBuilder(String.valueOf(path))).append(datePath).toString();
//                    String newfileName = MyUtil.createUUID()+"_"+fileName;
//                    log.info("打印新的文件名字 newfileName = "+newfileName);
//                    log.info("打印新的文件名字 datePath = "+datePath);
//                    log.info("打印新的文件名字 relPath = "+relPath);
//                    String imgurl = relPath+"/";
//                    //生成新的文件名称和存放地址
//                    File file = new File(imgurl, newfileName);
//                    String url = "/upload"+datePath+"/"+newfileName;
//                    log.info("返回地址 = "+url);
//                    //创建文件夹
//                    File f = new File(relPath);
//                    if (!f.exists()) {
//                        f.mkdirs();
//                    }
//                    BufferedOutputStream bos = null;
//                    try {
//                        log.info(multipartFile.getOriginalFilename());
//                        FileInputStream in = (FileInputStream) multipartFile.getInputStream();
//
//                        if (type.equals("gif")
//                                ||type.equals("jpeg")
//                                ||type.equals("jpg")
//                                ||type.equals("png")) {
//                            if (in.available() > 1024 * 1024 * 5) {
//                                file.delete();
//                                throw new CodeException(ErrorEnum.FILE_IS_TOO_LARGE);
//                            }else {
//                                //图片生成
//                                bos = new BufferedOutputStream(new FileOutputStream(file));
//                                byte[] bs = new byte[1024];
//                                int len;
//                                while ((len = in.read(bs)) != -1) {
//                                    bos.write(bs, 0, len);
//                                }
//                                log.info("生成成功");
//                            }
//                        }else {
//                            if (in.available() > 1024 * 1024 * 1000) {
//                                file.delete();
//                                throw new CodeException(ErrorEnum.FILE_IS_TOO_LARGE);
//                            } else {
//                                //文件生成
//                                bos = new BufferedOutputStream(new FileOutputStream(file));
//                                byte[] bs = new byte[1024];
//                                int len;
//                                while ((len = in.read(bs)) != -1) {
//                                    bos.write(bs, 0, len);
//                                }
//                                log.info("生成成功2");
//                            }
//                        }
//                        resultMap.put("url", url);
//                        resultMap.put("fileName", fileName);
//                        resultList.add(resultMap);
//                    } catch (Exception e) {
//                        throw new CodeException(ErrorEnum.FILE_IS_TOO_LARGE);
//                    }finally {
//                        bos.flush();
//                        bos.close();
//                    }
//                }
//            }
//        return TLResult.ok(resultList);
//    }
}