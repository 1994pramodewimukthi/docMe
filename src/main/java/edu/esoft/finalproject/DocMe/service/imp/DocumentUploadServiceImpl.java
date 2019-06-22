package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.config.DocCategoryMasterWebixComparator;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.entity.*;
import edu.esoft.finalproject.DocMe.repository.DocCategoryMasterRepository;
import edu.esoft.finalproject.DocMe.repository.DocumentUploadMasterRepository;
import edu.esoft.finalproject.DocMe.repository.DocumentUploadRepository;
import edu.esoft.finalproject.DocMe.service.DocumentUploadSFTPService;
import edu.esoft.finalproject.DocMe.service.DocumentUploadService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class DocumentUploadServiceImpl implements DocumentUploadService {

    private static final String DELETE_REJECT = "DELETE_REJECT";
    private static final int SUCSESS = 1;
    private static final int RECODE_EXSIST_TEMP = 2;
    private static final int ERROR = 0;
    private static final int VIRUS_FILE_MESSAGE = 3;
    private static String ID = "id";
    private static final String AUTH_BUTTON = "<input class='btn btn-success btn-table authbtn' type='button' value='Authorize' id='authBtn' onclick='authRejectViwer(" + ID + ")'>";
    private static final String REJECT_BUTTON = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Reject' id='rejectBtn' onclick='rejectRec(" + ID + ")'>";
    private static final String VEIW_BUTTON = "<input class='btn btn-success btn-table viewbtn' type='button' value='View' id='viewBtn' onclick='viewRec(" + ID + ")'>";
    private static final String RESUBMIT_BTN = "<input class='btn btn-success btn-table viewbtn' type='button' value='Resubmit' id='resubmit' onclick='resubmitRec(" + ID + ")'>";
    private static final String AUTH_BUTTON_DISABLE = "<input class='btn btn-success btn-table authbtn' type='button' value='Authorize' id='authBtn' disabled onclick='authRejectViwer(" + ID + ")'>";
    private static final String REJECT_BUTTON_DISABLE = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Reject' id='rejectBtn' disabled onclick='rejectRec(" + ID + ")'>";
    private static final String VEIW_BUTTON_DISABLE = "<input class='btn btn-success btn-table viewbtn' type='button' value='View' id='viewBtn' disabled onclick='viewRec(" + ID + ")'>";
    private static final String RESUBMIT_BTN_DISABLE = "<input class='btn btn-success btn-table viewbtn' type='button' value='Resubmit' id='resubmit' disabled onclick='resubmitRec(" + ID + ")'>";
    public static final String DELETE_BUTTON = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Delete Authorize'  onclick='authRejectViwer(" + ID + ")'>";
    public static final String DELETE_BUTTON_DISSABLE = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Delete Authorize'  onclick='authRejectViwer(" + ID + ")' disabled>";
    private static String TEMP = "TEMP";
    private static String MST = "MST";
    private static String BOTH = "BOTH";
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DocumentUploadServiceImpl.class);

    @Autowired
    private DocCategoryMasterRepository docCategoryMasterRepository;
    @Autowired
    DocumentUploadMasterRepository documentUploadMasterRepository;
    @Autowired
    private DocumentUploadSFTPService documentUploadSFTPService;
    @Autowired
    DocumentUploadRepository documentUploadRepository;

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

    @Override
    public int uploadDocumentToCategory(DocumentUploadDto documentUploadDto, User user, int catagoryId) throws Exception {
        documentUploadDto.setDocCategoryMasterId(catagoryId);
        try {
            if (null != documentUploadDto.getAttachment()) {
                if (documentUploadDto.getDocumentName() != "") {
                    DocCategoryMaster catagory = docCategoryMasterRepository.findByDocCategoryMstId(catagoryId);

                    String sftpPath = documentUploadSFTPService.uploadFile(documentUploadDto);
                    if (sftpPath != null) {
//                    file save
                        AccessUserType search = new AccessUserType();
                        search.setAccessUserTypeId((long) 1);
                        DocumentUploadTemp documentUploadTemp = new DocumentUploadTemp(catagory);

                        documentUploadTemp.setDocumentUploadTempId(getCategoryNextId());
                        documentUploadTemp.setDocumentName(documentUploadDto.getDocumentName());
                        documentUploadTemp.setDocumentDescription(documentUploadDto.getDocumentDescription());
                        documentUploadTemp.setHeadline(documentUploadDto.getHeadLine());
                        documentUploadTemp.setRecordStatus(documentUploadDto.getRecordStatus());
                        documentUploadTemp.setInpDateTime(new Date());
                        documentUploadTemp.setInputUserId(user.getUserName());
                        documentUploadTemp.setAccessUserType(search);
                        documentUploadTemp.setReason("");
                        documentUploadTemp.setPublishDate(documentUploadDto.getPublishDate());
                        documentUploadTemp.setExpireDate(documentUploadDto.getExpireDate());
                        documentUploadTemp.setPath(sftpPath);
                        documentUploadTemp.setRecordStatus(new RecordStatus(6));
                        for (String id : documentUploadDto.getAcessTypes()) {
                            SystemRole systemRoleById = systemRoleDockUpService.getSystemRoleById(Integer.parseInt(id));
                            DocumentUploadTempSystemRole documentUploadTempSystemRole = new DocumentUploadTempSystemRole();
                            documentUploadTempSystemRole.setDocumentUploadTemp(documentUploadTemp);
                            documentUploadTempSystemRole.setSystemRole(systemRoleById);
                            documentUploadTempSystemRoles.add(documentUploadTempSystemRole);
                        }
                        if (null != user.getSelectedAgent().getAgentCode()) {
                            documentUploadTemp.setChannel(user.getSelectedAgent().getChannel().getChannelType().toString());
                        } else {
                            documentUploadTemp.setChannel(BOTH);
                        }
                        documentUploadTemp.setDocumentUploadTempSystemRoles(documentUploadTempSystemRoles);
                        documentUploadRepository.save(documentUploadTemp);
                        return SUCSESS;
                    } else {
                        return VIRUS_FILE_MESSAGE;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

        return ERROR;
    }

    public int getCategoryNextId() throws Exception {
        int maxTemp = 0;
        int maxMst = 0;

        try {
            try {
                maxTemp = documentUploadRepository.findMaxId();
            } catch (NullPointerException e) {
                LOGGER.error(e.getMessage());
            }

            try {
                maxMst = documentUploadMasterRepository.findMaxId();
            } catch (NullPointerException e) {
                LOGGER.error(e.getMessage());
            }

            if (maxMst > maxTemp) {
                return maxMst + 1;
            } else if (maxTemp > maxMst) {
                return maxTemp + 1;
            } else {
                return maxMst + 1;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

    }
}