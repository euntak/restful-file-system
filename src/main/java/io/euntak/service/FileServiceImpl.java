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
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class FileServiceImpl implements FileService {

    @Value ("${spring.uploadfile.root-directory}")
    private String localDirectory;
    private FileDao fileDao;

    @Autowired
    public FileServiceImpl(FileDao fileDao) {

        this.fileDao = fileDao;
    }

    @Override
    public Map<String, Object> getAllFiles() {

        Map<String, Object> resultMessage = new HashMap<>();
        List<FileInfo> selectedFiles = fileDao.selectAllFiles();
        if (selectedFiles.size() > 0) {
            resultMessage.put("message", "get All files Successful");
            resultMessage.put("isSuccess", true);
            resultMessage.put("files", selectedFiles);
            resultMessage.put("location", "/api/files");
        } else {
            resultMessage.put("message", "get All files Failure!");
            resultMessage.put("isSuccess", false);
            resultMessage.put("files", selectedFiles);
            resultMessage.put("location", "/api/files");
        }


        return resultMessage;
    }

    @Override
    public Map<String, Object> getFiles(String type) {

        List<FileInfo> selectedFiles = null;

        if (! type.isEmpty()) {
            selectedFiles = fileDao.selectFilesByFilter(type);
        } else {
            selectedFiles = fileDao.selectAllFiles();
        }

        Map<String, Object> resultMessage = new HashMap<>();

        if (selectedFiles != null && selectedFiles.size() > 0) {
            resultMessage.put("message", "get files Successful By types : " + type);
            resultMessage.put("isSuccess", true);
            resultMessage.put("files", selectedFiles);
            resultMessage.put("location", "/api/files");
        } else {
            resultMessage.put("message", "get filtered files Failure!");
            resultMessage.put("isSuccess", false);
            resultMessage.put("files", selectedFiles);
            resultMessage.put("location", "/api/files");
        }

        return resultMessage;
    }

    @Override
    public FileInfo getFile(Long fileId) {

        FileInfo selectedFile = fileDao.selectFileById(fileId);

        if (selectedFile != null) return selectedFile;

        return null;
    }

    @Override
    public FileInfo writeFile(MultipartFile file) {

        String createTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        String curtime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String savePath = localDirectory + File.separator + curtime;
        String contentType = file.getContentType();
        String originalFilename = null;

        try {
            originalFilename = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        long size = file.getSize();

        String uuid = UUID.randomUUID().toString();
        String saveFileName = savePath + File.separator + uuid;

        // 실제 저장될 폴더가 없으면 폴더 생성
        if (! new File(savePath).isDirectory()) {
            new File(savePath).mkdirs();
        }

        // try-with-resource 구문. close()를 할 필요가 없다. java 7 이상에서 가능
        try (InputStream in = file.getInputStream();
             FileOutputStream fos = new FileOutputStream(saveFileName)) {
            int readCount;
            byte[] buffer = new byte[512];

            while ((readCount = in.read(buffer)) != - 1) {
                fos.write(buffer, 0, readCount);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FileInfo fileInfo = new FileInfo(); // File Info

        fileInfo.setCreateDate(createTime);
        fileInfo.setModifyDate(createTime);
        fileInfo.setContentType(contentType);
        fileInfo.setFileLength(size);
        fileInfo.setFileName(originalFilename);
        fileInfo.setSaveFileName(saveFileName);
        fileInfo.setDeleteFlag(0);

        return fileInfo;
    }

    @Override
    public boolean removeFile(FileInfo fileInfo) {

        String savedFile = fileInfo.getSaveFileName();
        File file = new File(savedFile);

        return file.delete();

    }

    @Override
    public Map<String, Object> deleteFile(Long fileId) {

        int isDelete = 0;
        Map<String, Object> message = new HashMap<>();
        FileInfo fileInfo = getFile(fileId);

        if (fileInfo != null && removeFile(fileInfo)) {
            isDelete = fileDao.delete(fileId);
        }

        message.put("location", "/api/files");

        if (isDelete > 0) {
            message.put("success", true);
            message.put("deletedFileId", fileId);
            message.put("summary", "Delete Files successfully");
        } else {
            message.put("success", false);
            message.put("summary", "Delete Files failure");
        }

        return message;
    }

    @Override
    public Map<String, Object> updateFile(Long fileId, MultipartFile file) {

        if (file == null) {
            return null;
        }

        Map<String, Object> message = new HashMap<>();
        FileInfo fileInfo = writeFile(file);
        int isSuccess = fileDao.updateFileById(fileId, fileInfo);

        if (isSuccess > 0) {
            message.put("success", true);
            message.put("summary", "Update Files successfully");
        } else {
            message.put("success", false);
            message.put("summary", "Update Files failure");
        }

        message.put("updatefileId", fileId);
        message.put("location", "/api/files");


        return message;
    }

    public Map<String, Object> uploadMultiFiles(MultipartFile[] files) {

        Map<String, Object> message = new HashMap<>();
        List<Long> uploadedfiles = new ArrayList<>();

        for (MultipartFile file : files) {
            FileInfo fileInfo = writeFile(file);
            uploadedfiles.add(fileDao.insert(fileInfo));
            message.put("success", true);
            message.put("uploadfiles", uploadedfiles);
            message.put("location", "/api/files");
            message.put("summary", "Upload Files successfully");
        }

        return message;
    }
}
