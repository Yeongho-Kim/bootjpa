package com.web.controller;

import com.web.domain.Files;
import com.web.domain.WebBoard;
import com.web.repository.FilesRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FilesRepository filesRepository;

    @GetMapping("/getList")
    @ResponseBody
    public List<Files> getList(Long bno){
        WebBoard board=new WebBoard();
        board.setBno(bno);
        return getListByBoard(board);
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public List<Files> uploadAjaxPost(MultipartFile[] uploadFile) {

        List<Files> list = new ArrayList<>();
        String uploadFolder = "C:\\upload\\board\\";

        String uploadFolderPath = getFolder();

        File uploadPath = new File(uploadFolder, uploadFolderPath);

        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile) {
            Files files = new Files();
            String uploadFileName = multipartFile.getOriginalFilename();
            files.setFileName(uploadFileName);

            UUID uuid = UUID.randomUUID();
            uploadFileName = uuid.toString() + "_" + uploadFileName;
            try {
                File saveFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile);

                files.setUuid(uuid.toString());
                files.setUploadUrl(uploadFolderPath);
                files.setImage(false);
                if (checkImageType(saveFile)) {
                    //썸네일 생성
                    files.setImage(true);
                    int idx_fileName=uploadFileName.indexOf(".")+1;
                    Thumbnails.of(saveFile).size(100,150).outputFormat(uploadFileName.substring(idx_fileName,uploadFileName.length())).toFile(new File(uploadPath,"s_"+uploadFileName));
                }
                //filesRepository.save(files);
                list.add(files);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } // end for
        return list;
    }
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String fileName){
        String uploadFolder = "C:\\upload\\board\\";
        Resource resource=new FileSystemResource(uploadFolder+fileName);
        if(resource.exists()==false){
            return  new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
        String resourceName=resource.getFilename();
        String resourceOriginalName=resourceName.substring(resourceName.indexOf("_")+1);
        HttpHeaders headers=new HttpHeaders();
        try{
            String downloadName=null;
            downloadName=new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
            headers.add("Content-Disposition", "attachment; filename=" + downloadName);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) {
        File file = new File("c:\\upload\\board\\" + fileName);
        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", java.nio.file.Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @PostMapping("/delete")
    @ResponseBody
    public String deleteFile(String fileName, String type) {
        String uploadFolder="C:\\upload\\board\\";

        try {
            String uploadFileName= (URLDecoder.decode(fileName, "UTF-8")).toString().replace("/","\\");
            File originFile= new File(uploadFolder + uploadFileName);
            originFile.delete();
            if (type.equals("image")) {
                String largeFileName = originFile.getAbsolutePath().replace("s_", "");
                File imageFile = new File(largeFileName);
                imageFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "deleted";
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteFileDB(String fileName, String type,Long fno) {
        String uploadFolder="C:\\upload\\board\\";

        try {
            String uploadFileName= (URLDecoder.decode(fileName, "UTF-8")).toString().replace("/","\\");
            System.out.println("☆★☆★☆★☆★☆★"+uploadFileName);
            File originFile= new File(uploadFolder + uploadFileName);
            originFile.delete();
            if (type.equals("image")) {
                String largeFileName = originFile.getAbsolutePath().replace("s_", "");
                System.out.println("☆★☆★☆★☆★☆★"+largeFileName);
                File imageFile = new File(largeFileName);
                imageFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        filesRepository.deleteById(fno);
        return "deleted";
    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file) {
        try {
            String contentType = java.nio.file.Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Files> getListByBoard(WebBoard board) throws RuntimeException {
        return filesRepository.getRepliesofBoard(board);
    }
}
