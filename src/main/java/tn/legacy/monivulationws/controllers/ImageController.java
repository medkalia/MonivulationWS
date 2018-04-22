package tn.legacy.monivulationws.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tn.legacy.monivulationws.Util.DebugUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
public class ImageController {

    private static String UPLOADED_FOLDER = "D://";

    @RequestMapping(method = RequestMethod.POST, value = "/picture/upload")
    public String uploadFile(@RequestParam("file") MultipartFile uploadfile) {

        DebugUtil.log("Single file Upload");

        if (uploadfile.isEmpty()) {
            return "ERROR | file empty !";
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfile));

        } catch (IOException e) {
            return "ERROR | problem while converting file";
        }

        return "Successfully uploaded - " + uploadfile.getOriginalFilename();

    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }

    }





}
