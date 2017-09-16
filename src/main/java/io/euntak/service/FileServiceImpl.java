package io.euntak.service;

import io.euntak.dao.FileDao;
import io.euntak.domain.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class FileServiceImpl implements FileService {

    @Value("${spring.uploadfile.root-directory}")
    private String localDirectory;
    private FileDao fileDao;

    @Autowired
    public FileServiceImpl(FileDao fileDao) {
        this.fileDao=fileDao;
    }

    @Override
    public Map<String, String> getAllFiles() {
        return null;
    }

    public Map<String, Object> uploadMultiFiles(MultipartFile[] files) {
        Map message=new HashMap<String, Object>();
        List<Long> uploadedfiles=new ArrayList<>();

        String createTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        String curtime=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String savePath=localDirectory + File.separator + curtime;

        for (MultipartFile file : files) {
            String contentType=file.getContentType();
            String originalFilename=file.getOriginalFilename();
            long size=file.getSize();

            String uuid=UUID.randomUUID().toString();
            String saveFileName=savePath + File.separator + uuid;

            // 실제 저장될 폴더가 없으면 폴더 생성
            if (!new File(savePath).isDirectory()) {
                new File(savePath).mkdirs();
            }

            // try-with-resource 구문. close()를 할 필요가 없다. java 7 이상에서 가능
            try (InputStream in=file.getInputStream();
                 FileOutputStream fos=new FileOutputStream(saveFileName)) {
                int readCount;
                byte[] buffer=new byte[512];

                while ((readCount=in.read(buffer)) != -1) {
                    fos.write(buffer, 0, readCount);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            FileInfo fileInfo=new FileInfo(); // File Info

            fileInfo.setCreateDate(createTime);
            fileInfo.setModifyDate(createTime);
            fileInfo.setContentType(contentType);
            fileInfo.setFileLength(size);
            fileInfo.setFileName(originalFilename);
            fileInfo.setSaveFileName(saveFileName);
            fileInfo.setDeleteFlag(0);

            uploadedfiles.add(fileDao.insert(fileInfo));
            message.put("success", true);
            message.put("uploadfiles", uploadedfiles);
            message.put("location", "/api/files");
            message.put("summary", "Upload Files successfully");

        }

        return message;
    }
}
