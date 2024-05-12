package com.tuts.ots.onetimeshop.services.impl;


import com.tuts.ots.onetimeshop.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //File Name
        String name=file.getOriginalFilename();
    
//        //Ramdom Genrate image id
//        String randomId = UUID.randomUUID().toString();
//        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));

        //Full Path
        String filepath=path + File.separator+name;





        //Create Folder If Not Created
        File f= new File(path);
        if (!f.exists()){
            f.mkdir();
        }


        //file Copy
        Files.copy(file.getInputStream(), Paths.get(filepath));

        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
