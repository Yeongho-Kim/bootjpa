package com.web.task;

import com.web.domain.Files;
import com.web.repository.FilesRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Component
public class FileCheckTask {
    @Autowired
    private FilesRepository filesRepository;

    private String getFolderYesterDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String str = sdf.format(cal.getTime());
        return str.replace("-", File.separator);
    }

    @Scheduled(cron = "0 0 2 * * *")  //AM 02시마다 실행
    public void checkFiles() throws Exception {
        log.info("File Check Task run.................");
        String uploadPath="C:\\upload\\board";

        //어제 날짜의 파일들을 가져옴
        List<Files> files = filesRepository.getOldFiles(getFolderYesterDay());

        //쓰레기파일
        List<Path> fileListPaths = files.stream()
                .map(file -> Paths.get(uploadPath, file.getUploadUrl(), file.getUuid() + "_" + file.getFileName()))
                .collect(Collectors.toList());

        //이미지파일인 경우 썸네일까지
        files.stream().filter(file -> file.isImage() == true)
                .map(file -> Paths.get(uploadPath, file.getUploadUrl(), "s_" + file.getUuid() + "_" +file.getFileName()))
                .forEach(p -> fileListPaths.add(p));

        //어제 폴더에있는 파일목록을 가져온 뒤 DB목록에 없는 파일들을 removeFiles에 추가함
        File targetDir = Paths.get(uploadPath, getFolderYesterDay()).toFile();
        File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);

        //쓰레기 파일 삭제
        for (File file : removeFiles) {
            log.info(file.getAbsolutePath());
            file.delete();

        }
    }
}
