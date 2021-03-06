package jm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl  implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    private long count;

    @Value("${upload.directory}")
    String uploadPath;

    @Override
    public String store(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get(uploadPath);

        //Костыльное удаление папки при первом сохранении с рестарта
        if (count++ < 1) {
            File dir = new File(uploadPath);
            String[] entries = dir.list();
            assert entries != null;
            for(String s: entries){
                File currentFile = new File(dir.getPath(),s);
                currentFile.delete();
            }
            Files.deleteIfExists(uploadDir);
        }

        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        String fileName = file.getOriginalFilename();
        file.transferTo(Paths.get(uploadPath + "/" + fileName));
        logger.info("File upload was successful");
        return fileName;
    }

    public Path load(String filename) {
        return Paths.get(uploadPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
