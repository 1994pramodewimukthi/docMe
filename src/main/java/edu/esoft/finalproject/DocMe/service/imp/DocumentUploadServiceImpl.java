package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.config.DocCategoryMasterWebixComparator;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import edu.esoft.finalproject.DocMe.entity.DocumentUploadMaster;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.repository.DocCategoryMasterRepository;
import edu.esoft.finalproject.DocMe.repository.DocumentUploadMasterRepository;
import edu.esoft.finalproject.DocMe.service.DocumentUploadService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class DocumentUploadServiceImpl implements DocumentUploadService {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DocumentUploadServiceImpl.class);
    @Autowired
    private DocCategoryMasterRepository docCategoryMasterRepository;
    @Autowired
    DocumentUploadMasterRepository documentUploadMasterRepository;
    @Override
    public List<DocCategoryMasterWebix> createCategoryWebixTableWithUploadDocumentAll(User user) throws Exception {
        try {
            List<DocCategoryMaster> list = docCategoryMasterRepository.findAllByParentDocCategoryMstIsNullAndOrderBySortingOrder();
            List<DocCategoryMasterWebix> webixList = new ArrayList<>();
            for (DocCategoryMaster master : list) {
                DocCategoryMasterWebix docCategoryMasterWebix = getDocCategoryMasterWebixAll(master, user);
                webixList.add(docCategoryMasterWebix);
            }
            return webixList;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    private DocCategoryMasterWebix getDocCategoryMasterWebixAll(DocCategoryMaster docCategoryMaster, User user) throws Exception {
        Comparator comparator = new DocCategoryMasterWebixComparator();
        try {
            DocCategoryMasterWebix docCategoryMasterWebix = new DocCategoryMasterWebix();
            docCategoryMasterWebix.setDocCategoryMstId(docCategoryMaster.getDocCategoryMstId());
            docCategoryMasterWebix.setDocCategoryName(docCategoryMaster.getDocCategoryName());
            docCategoryMasterWebix.setInpUserId(docCategoryMaster.getInpUserId());
            docCategoryMasterWebix.setInpDateTime(docCategoryMaster.getInpDateTime());
            docCategoryMasterWebix.setSortingOrder(docCategoryMaster.getSortingOrder());
            docCategoryMasterWebix.setAuthUserId(docCategoryMaster.getAuthUserId());
            docCategoryMasterWebix.setAuthDateTime(docCategoryMaster.getAuthDateTime());
            docCategoryMasterWebix.setReason(docCategoryMaster.getReason());
            if (!docCategoryMaster.getSubCategoryDocList().isEmpty()) {
                Boolean isLoop = true;
                for (DocCategoryMaster docCategoryMaster1 : docCategoryMaster.getSubCategoryDocList()) {
                    docCategoryMasterWebix.getData().add(getDocCategoryMasterWebixAll(docCategoryMaster1, user));
                    if (isLoop) {
                        List<DocumentUploadMaster> uploadList2 = documentUploadMasterRepository.findAllByDocCategoryMasterInActive(docCategoryMaster.getDocCategoryMstId());
                        for (DocumentUploadMaster master : uploadList2) {
//                            DocumentUploadMasterSystemRole systemRoleMst = documentUploadMasterSystemRoleRepository.findByDocumentUploadMasterAndSystemRole(master.getDocumentUploadMstId(), user.getSystemRole().getSystemRoleId());
//                            if (null != systemRoleMst) {
                            DocCategoryMasterWebix uplodDoc = new DocCategoryMasterWebix();
                            uplodDoc.setDocCategoryMstId(master.getDocumentUploadMstId());
                            uplodDoc.setDocCategoryName(master.getDocumentName());
                            uplodDoc.setTableConfig(1);
//                                uplodDoc.setUser(user);
                            uplodDoc.setDocDescription(master.getDocumentDescription());
                            uplodDoc.setPublishDate(new SimpleDateFormat("dd MMM yyyy").format(master.getPublishDate()));
                            docCategoryMasterWebix.getData().add(uplodDoc);
//                            }
                        }
                        isLoop = false;
                    }
                }
                Collections.sort(docCategoryMasterWebix.getData(), comparator);
            } else {
                List<DocumentUploadMaster> uploadList = documentUploadMasterRepository.findAllByDocCategoryMasterInActive(docCategoryMaster.getDocCategoryMstId());
                for (DocumentUploadMaster master : uploadList) {
//                    DocumentUploadMasterSystemRole systemRoleMst = documentUploadMasterSystemRoleRepository.findByDocumentUploadMasterAndSystemRole(master.getDocumentUploadMstId(), user.getSystemRole().getSystemRoleId());
//                    if (null != systemRoleMst) {
                    DocCategoryMasterWebix uplodDoc = new DocCategoryMasterWebix();
                    uplodDoc.setDocCategoryMstId(master.getDocumentUploadMstId());
                    uplodDoc.setDocCategoryName(master.getDocumentName());
                    uplodDoc.setTableConfig(1);
                    uplodDoc.setDocDescription(master.getDocumentDescription());
                    uplodDoc.setPublishDate(new SimpleDateFormat("dd MMM yyyy").format(master.getPublishDate()));
                    docCategoryMasterWebix.getData().add(uplodDoc);
//                    }
                }
            }

            return docCategoryMasterWebix;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}