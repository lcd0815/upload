package hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/uploads")
public class FileUploadController {
    private static final Logger log=LoggerFactory.getLogger(FileUploadController.class);

    @GetMapping
    public String index(){
        return "index";
    }

    @PostMapping("/upload1")
    @ResponseBody
    public Map<String,String> upload1(@RequestParam("file")MultipartFile file)throws IOException{
        log.info("[文件类型]-[{}]",file.getContentType());
        log.info("[文件名称]-[{}]",file.getOriginalFilename());
        log.info("[文件大小]-[{}]",file.getSize());
        //文件写入地址:服务器地址,云存储,指定目录
        File file1=new File("c:\\codemanager\\"+file.getOriginalFilename());
        file.transferTo(file1);
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return result;
    }
    @PostMapping("/upload2")
    @ResponseBody
    public List<Map<String,String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException{
        if (files == null || files.length == 0) {
            return null;
        }
        List<Map<String,String>> list=new ArrayList<>();
        for (MultipartFile file : files) {
            File file1=new File("c:\\codemanager\\"+file.getOriginalFilename());
            file.transferTo(file1);
            Map<String,String> map=new HashMap<>();
            map.put("contentType", file.getContentType());
            map.put("fileName", file.getOriginalFilename());
            map.put("fileSize", file.getSize() + "");
            list.add(map);
        }
        return list;
    }

    @PostMapping("/upload3")
    @ResponseBody
    public void upload3(String base64) throws IOException {
        File file = new File("c:\\codemanager\\99.jpg");
        String[] d=base64.split("base64,");
        final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
        FileCopyUtils.copy(bytes,file);
    }
}
