package com.web.controller;

import com.web.domain.Files;
import com.web.domain.WebBoard;
import com.web.domain.WebReply;
import com.web.repository.FilesRepository;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FilesRepository filesRepository;

    @GetMapping("/{bno}")
    public ResponseEntity<List<Files>> getReplies(@PathVariable("bno")Long bno){
        WebBoard board=new WebBoard();
        board.setBno(bno);
        return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
    }
/*    @PostMapping("/{bno}")
    public void uploadAjaxPost(MultipartFile[] uploadFile,@PathVariable("bno") Long bno) {

        System.out.println("update ajax post.........");

        String uploadFolder = "C:\\upload";

        for (MultipartFile multipartFile : uploadFile) {

            System.out.println("-------------------------------------");
            System.out.println("Upload File Name: " + multipartFile.getOriginalFilename());
            System.out.println("Upload File Size: " + multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();

            // IE has file path
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            System.out.println("only file name: " + uploadFileName);

            File saveFile = new File(uploadFolder, uploadFileName);

            try {

                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } // end catch

        } // end for

    }*/
    @PostMapping("/{bno}")
    @ResponseBody
    public ResponseEntity<List<Files>> uploadAjaxPost(@RequestParam("uploadFile") MultipartFile[] uploadFile, @PathVariable("bno") Long bno) {
        System.out.println("☆★☆★☆★☆★☆★☆★☆★☆★");
        System.out.println("☆★☆★☆★☆★☆★☆★☆★☆★");
        System.out.println("☆★☆★☆★☆★☆★☆★☆★☆★");
        System.out.println("☆★☆★☆★☆★☆★☆★☆★☆★");


        String uploadFolder = "C:\\upload";

        WebBoard board =new WebBoard();
        board.setBno(bno);

        // make folder --------
        File uploadPath = new File(uploadFolder, getFolder());
        System.out.println("upload path: " + uploadPath);

        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }
        for (MultipartFile multipartFile : uploadFile) {
            System.out.println("-------------------------------------");
            System.out.println("Upload File Name: " + multipartFile.getOriginalFilename());
            System.out.println("Upload File Size: " + multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

            UUID uuid=UUID.randomUUID();
            uploadFileName=uuid.toString()+"_"+uploadFileName;

            Files files =new Files();
            files.setFileName(uploadFileName);
            files.setFileUrl(uploadPath.toString());
            files.setBoard(board);
            filesRepository.save(files);

            System.out.println("only file name: " + uploadFileName);

            File saveFile = new File(uploadPath, uploadFileName);
            try {
                multipartFile.transferTo(saveFile);
                 if (checkImageType(saveFile)) {
                 FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
                 Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
                 thumbnail.close();
                 }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } // end catch
        } // end for
        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }

    private List<Files> getListByBoard(WebBoard board) throws RuntimeException {
        return filesRepository.getFilesofBoard(board);
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

}
