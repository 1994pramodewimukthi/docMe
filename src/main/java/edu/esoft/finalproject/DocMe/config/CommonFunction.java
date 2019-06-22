package edu.esoft.finalproject.DocMe.config;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;


@Component
public class CommonFunction {
    private static final Logger LOGGER = Logger.getLogger(CommonFunction.class);

    //example for magicfile types   String[] newsUploadValidMagicTypes = {"0xffd8", "%PDF-", "GIF8", "PNG"}; jpeg,jpg, pdf, gif,png
    public boolean checkIsValidFileType(MultipartFile multipartFile, String[] magicFileTypes) {
        boolean isValidType = false;
        try {
            File convFile = new File(multipartFile.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(multipartFile.getBytes());
                fos.close();
            convFile = convFile;
            MagicMatch match = Magic.getMagicMatch(convFile, true, true);
            String magicType = new String(match.getTest().array());
            for (String type : magicFileTypes) {
                if (type.equals(magicType)) {
                    isValidType = true;
                    break;
                }
            }
            return isValidType;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return isValidType;
        }
    }

    public boolean isFileSizeSufficient(MultipartFile multipartFile, Long maxSize) { //size should be MB
        try {
            Long fileSize = multipartFile.getSize();
            return fileSize > maxSize*(1024 * 1024) ? false : true;
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }
}
