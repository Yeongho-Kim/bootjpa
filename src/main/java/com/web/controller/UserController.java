package com.web.controller;

import com.web.domain.User;
import com.web.repository.UsersRepository;
import com.web.security.CustomUser;
import com.web.security.CustomUserDetailService;
import com.web.vo.FileDTO;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private UsersRepository usersRepository;

    List<CustomUser> users=new ArrayList<CustomUser>();

    @GetMapping("/signUp")
    public void signUp(){

    }
    @GetMapping("/list")
    public void list(Model model){
        model.addAttribute("users", usersRepository.findAll());
    }

    @PostMapping("/create")
    public String userCreate(User user){
        customUserDetailService.save(user);
        return "redirect:/";
    }
    @GetMapping("/view")
    public void view(@RequestParam("uNum")Long uNum, Model model){
        User user = usersRepository.findById(uNum).get();
        model.addAttribute("user",user);
    }
    @GetMapping("/update/{uNum}")
    public String userUpdate(@PathVariable Long uNum, Model model){
        model.addAttribute("user", usersRepository.findById(uNum).get());
        return "/users/update";
    }
    @PostMapping("/update")
    public String postUpdate(User newUser){
        User user= usersRepository.findById(newUser.getUNum()).get();
        user.setUserName(newUser.getUserName());
        user.setUserPhone(newUser.getUserPhone());
        user.setUserEmail(newUser.getUserEmail());
        user.setProfilePhoto(newUser.getProfilePhoto());
        user.setThumbnailUrl(newUser.getThumbnailUrl());
        usersRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/login")
    public void login(){

    }
    @GetMapping("/login_failed")
    public void login_failed(){
    }


    @GetMapping("/profile")
    public void profile(Model model){

    }

    @GetMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("userId") String userId){
        int num=usersRepository.findCountByUserId(userId);
        return num;
    }

    @PostMapping("/upload")
    @ResponseBody
    public FileDTO upload(MultipartFile uploadFile){

        String uploadFolder="C:\\upload\\profile";
        String uploadFolderPath=getFolder();
        String uploadFileName=uploadFile.getOriginalFilename();

        //날짜별 폴더 생성
        File uploadPath=new File(uploadFolder,uploadFolderPath);
        if(uploadPath.exists()==false){
            uploadPath.mkdirs();
        }
        FileDTO fileDTO=new FileDTO();
        fileDTO.setFileName(uploadFileName);

        //파일명 중복방지 UUID 생성
        UUID uuid=UUID.randomUUID();
        uploadFileName=uuid.toString()+"_"+uploadFileName;

        fileDTO.setUuid(uuid.toString());
        fileDTO.setUploadPath(uploadFolderPath);
        try{
            File saveFile=new File(uploadPath,uploadFileName);
            uploadFile.transferTo(saveFile);

            //썸네일 생성
            int idx_fileName=uploadFileName.indexOf(".")+1;
            Thumbnails.of(saveFile).size(100,150).outputFormat(uploadFileName.substring(idx_fileName,uploadFileName.length())).toFile(new File(uploadPath,"s_"+uploadFileName));
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileDTO;
    }
    @GetMapping("/thumbnail")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String filename) {
        File file = new File("C:\\upload\\profile\\" + filename);
        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/deleteImg")
    @ResponseBody
    public String deleteImg(String thumbPath,String uNum){
        User user=usersRepository.findById((Long.parseLong(uNum))).get();
        String uploadFolder="C:\\upload\\profile\\";
        System.out.println("☆★☆★☆★☆★"+thumbPath+",,,,,,,,Unum:"+uNum);
        try{
            String uploadFileName= (URLDecoder.decode(thumbPath, "UTF-8")).toString().replace("/","\\");
            System.out.println("☆★☆★☆★☆★"+uploadFileName);
            File thumbFile=new File(uploadFolder + uploadFileName);
            thumbFile.delete();
            user.setProfilePhoto(null);

            String largeFileName = thumbFile.getAbsolutePath().replace("s_", "");
            System.out.println("☆★☆★☆★☆★"+largeFileName);
            File originFile=new File(largeFileName);
            originFile.delete();
            user.setThumbnailUrl(null);

            usersRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return "실패";
        }
        return "삭제성공";
    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }

}
