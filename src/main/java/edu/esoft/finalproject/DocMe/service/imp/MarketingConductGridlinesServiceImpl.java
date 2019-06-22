package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.config.MessageConstant;
import edu.esoft.finalproject.DocMe.dto.DocumentCategoryDto;
import edu.esoft.finalproject.DocMe.dto.DocumentExpireDto;
import edu.esoft.finalproject.DocMe.entity.MCGCategory;
import edu.esoft.finalproject.DocMe.entity.MCGDocumentExpire;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.repository.MCGCategoryRepository;
import edu.esoft.finalproject.DocMe.repository.MCGDocumentExpireRepository;
import edu.esoft.finalproject.DocMe.service.MarketingConductGridlinesService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MarketingConductGridlinesServiceImpl implements MarketingConductGridlinesService {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MarketingConductGridlinesServiceImpl.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final Long SUCSESS = 1L;
    private static final Long ERROR = 0L;
    private static final Long LP_AGENCY = 3L;
    private static final Long LP_BANCA = 6L;

    @Autowired
    private MCGCategoryRepository mcgCategoryRepository;
    @Autowired
    private MCGDocumentExpireRepository mcgDocumentExpireRepository;

    @Override
    public Long saveCategory(DocumentCategoryDto documentCategoryDto, User user) throws Exception {
        try {
            Long valid = validateCategorySave(documentCategoryDto);
            if (valid.equals(SUCSESS)) {
                List<MCGCategory> byCategoryName = mcgCategoryRepository.findByCategoryName(documentCategoryDto.getCategoryName());
                if (byCategoryName.isEmpty()) {

                    MCGCategory cat = new MCGCategory();
                    cat.setCategoryName(documentCategoryDto.getCategoryName().trim());
                    cat.setInputUser(user.getFirstName().trim());
                    cat.setInputDateTime(new Date());
                    MCGCategory save = mcgCategoryRepository.save(cat);
                    if (null != save) {
                        return SUCSESS;
                    } else {
                        return ERROR;
                    }
                } else {
                    return MessageConstant.DUPLICATE_NAME;
                }
            } else {
                return valid;

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

    }

    @Override
    public Long saveExpireYear(DocumentExpireDto documentExpireDto, User user) throws Exception {
        try {
            Long valid = validateExpireYear(documentExpireDto);
            if (valid.equals(SUCSESS)) {
                MCGDocumentExpire mcgDocumentExpire = new MCGDocumentExpire();
                mcgDocumentExpire.setId(Integer.parseInt(documentExpireDto.getId()));
                mcgDocumentExpire.setExpireYear(documentExpireDto.getNewExpireYear());
                mcgDocumentExpire.setInputUser(user.getFirstName().trim());
                mcgDocumentExpire.setInputDate(new Date());

                mcgDocumentExpireRepository.save(mcgDocumentExpire);
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ERROR;
        }
        return SUCSESS;
    }

    Long validateExpireYear(DocumentExpireDto documentExpireDto) {

        if (!documentExpireDto.getNewExpireYear().isEmpty()) {
            char[] chars = documentExpireDto.getNewExpireYear().toCharArray();
            for (char c : chars) {
                if (!Character.isDigit(c)) {
                    return ERROR;
                }
            }
        } else {
            return ERROR;
        }

        return SUCSESS;
    }

    public Long updateCategory(DocumentCategoryDto documentCategoryDto, User user) throws Exception {
        try {
            Long valid = validateCategorySave(documentCategoryDto);
            if (valid.equals(SUCSESS)) {
                List<MCGCategory> byCategoryName = mcgCategoryRepository.findByCategoryName(documentCategoryDto.getCategoryName());
                if (byCategoryName.isEmpty()) {
                    MCGCategory cat = new MCGCategory();
                    cat.setCatId(Integer.parseInt(documentCategoryDto.getCategoryId().trim()));
                    cat.setCategoryName(documentCategoryDto.getCategoryName().trim());
                    cat.setInputUser(user.getFirstName().trim());
                    cat.setInputDateTime(new Date());
                    MCGCategory save = mcgCategoryRepository.save(cat);
                    if (null != save) {
                        return SUCSESS;
                    } else {
                        return ERROR;
                    }
                } else {
                    return MessageConstant.DUPLICATE_NAME;
                }
            } else {
                return valid;

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

    }

    private Long validateCategorySave(DocumentCategoryDto documentCategoryDto) {
        if (!documentCategoryDto.getCategoryName().trim().isEmpty() && documentCategoryDto.getCategoryName().trim().length() <= 100) {
            for (char c : documentCategoryDto.getCategoryName().trim().toCharArray()) {
                if (!Character.isDigit(c) && !Character.isAlphabetic(c) && !Character.isWhitespace(c) && c != ',' && c != '.') {
                    return MessageConstant.INVALIDE_DOC_NAME;
                }
            }
        } else {
            return MessageConstant.INVALIDE_DOC_NAME;
        }
        return SUCSESS;
    }


       @Override
    public DocumentExpireDto getExpireYear() throws Exception {
        DocumentExpireDto documentExpireDto = null;
        try {
            documentExpireDto = new DocumentExpireDto();
            Iterable<MCGDocumentExpire> mcgDocumentExpires = mcgDocumentExpireRepository.findAll();
            if (null != mcgDocumentExpires) {
                for (MCGDocumentExpire mcgDocumentExpire : mcgDocumentExpires) {
                    documentExpireDto.setId(mcgDocumentExpire.getId().toString());
                    documentExpireDto.setExpireYear(mcgDocumentExpire.getExpireYear());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return documentExpireDto;
    }

    @Override
    public List<DocumentCategoryDto> getAllCategorys() throws Exception {
        List<DocumentCategoryDto> documentCategoryDtos = new ArrayList<>();
        try {
            ArrayList<MCGCategory> catList = (ArrayList<MCGCategory>) mcgCategoryRepository.findAll();
            if (null != catList) {
                for (MCGCategory mcgCategory : catList) {
                    DocumentCategoryDto documentCategoryDto = new DocumentCategoryDto();
                    documentCategoryDto.setCategoryId(mcgCategory.getCatId().toString().trim());
                    documentCategoryDto.setCategoryName(mcgCategory.getCategoryName().trim());
                    documentCategoryDto.setCategoryInputUser(mcgCategory.getInputUser().trim());
                    documentCategoryDto.setCategoryInputDateTime(simpleDateFormat.format(mcgCategory.getInputDateTime()));

                    documentCategoryDtos.add(documentCategoryDto);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return documentCategoryDtos;
    }

    @Override
    public DocumentCategoryDto getCategoryById(String catId) throws Exception {
        DocumentCategoryDto documentCategoryDto = new DocumentCategoryDto();
        MCGCategory category = mcgCategoryRepository.findOne(Integer.parseInt(catId.trim()));
        if (null != category) {
            documentCategoryDto.setCategoryId(category.getCatId().toString().trim());
            documentCategoryDto.setCategoryName(category.getCategoryName().trim());
            documentCategoryDto.setCategoryInputUser(category.getInputUser().trim());
            documentCategoryDto.setCategoryInputDateTime(simpleDateFormat.format(category.getInputDateTime()));
        }
        return documentCategoryDto;
    }
}
