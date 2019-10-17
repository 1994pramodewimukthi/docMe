package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.DocCategoryMasterWebixComparator;
import edu.esoft.finalproject.DocMe.dto.*;
import edu.esoft.finalproject.DocMe.entity.*;
import edu.esoft.finalproject.DocMe.repository.*;
import edu.esoft.finalproject.DocMe.service.DocumentUploadSFTPService;
import edu.esoft.finalproject.DocMe.service.DocumentUploadService;
import edu.esoft.finalproject.DocMe.service.SystemRoleDockUpService;
import edu.esoft.finalproject.DocMe.utility.ActiveMQEmail;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
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
    DocumentUploadMasterRepository documentUploadMasterRepository;
    @Autowired
    private DocCategoryMasterRepository docCategoryMasterRepository;
    @Autowired
    private DocumentUploadSFTPService documentUploadSFTPService;
    @Autowired
    private DocumentUploadRepository documentUploadRepository;
    @Autowired
    private SystemRoleDockUpService systemRoleDockUpService;
    @Autowired
    private DocumentUploadTempSystemRoleRepository documentUploadTempSystemRoleRepository;
    @Autowired
    private DocumentUploadMasterSystemRoleRepository documentUploadMasterSystemRoleRepository;
    @Autowired
    private DocumentUploadHistoryRepository documentUploadHistoryRepository;
    @Autowired
    private ActiveMQEmail activeMQEmail;

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
//                        AccessUserType search = new AccessUserType();
//                        search.setAccessUserTypeId((long) 1);
                        DocumentUploadTemp documentUploadTemp = new DocumentUploadTemp(catagory);

                        List<DocumentUploadTempSystemRole> documentUploadTempSystemRoles = new ArrayList<>();

                        documentUploadTemp.setDocumentUploadTempId(documentUploadDto.getDocumentUploadTempId() == null ? getCategoryNextId() : documentUploadDto.getDocumentUploadTempId());
                        documentUploadTemp.setDocumentName(documentUploadDto.getDocumentName());
                        documentUploadTemp.setDocumentDescription(documentUploadDto.getDocumentDescription());
                        documentUploadTemp.setHeadline(documentUploadDto.getHeadLine());
                        documentUploadTemp.setRecordStatus(documentUploadDto.getRecordStatus());
                        documentUploadTemp.setInpDateTime(new Date());
                        documentUploadTemp.setInputUserId(user.getUserName());
//                        documentUploadTemp.setAccessUserType(search);
                        documentUploadTemp.setReason("");
                        documentUploadTemp.setPublishDate(documentUploadDto.getPublishDate());
                        documentUploadTemp.setExpireDate(documentUploadDto.getExpireDate());
                        documentUploadTemp.setPath(sftpPath);
                        documentUploadTemp.setRecordStatus(new RecordStatus(6));
                        documentUploadTemp.setSystemRoleId(documentUploadDto.getSystemRoleId());
//                        for (String id : documentUploadDto.getAcessTypes()) {
//                            SystemRole systemRoleById = systemRoleDockUpService.getSystemRoleById(Integer.parseInt(id));
//                            DocumentUploadTempSystemRole documentUploadTempSystemRole = new DocumentUploadTempSystemRole();
//                            documentUploadTempSystemRole.setDocumentUploadTemp(documentUploadTemp);
//                            documentUploadTempSystemRole.setSystemRole(systemRoleById);
//                            documentUploadTempSystemRoles.add(documentUploadTempSystemRole);
//                        }
                        /*if (null != user.getSelectedAgent().getAgentCode()) {
                            documentUploadTemp.setChannel(user.getSelectedAgent().getChannel().getChannelType().toString());
                        } else {
                            documentUploadTemp.setChannel(BOTH);
                        }*/
                        documentUploadTemp.setChannel(BOTH);
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

    @Override
    public List<DocAuthDto> getAllTempDock(User user) throws Exception {
        List<DocAuthDto> dockAuthDtos = new ArrayList<>();
        Iterable<DocumentUploadTemp> all;
        all = documentUploadRepository.findAllByRecordStatusPendingDelet();

        for (DocumentUploadTemp documentUploadTemp : all) {
            DocAuthDto dto = new DocAuthDto();
            dto.setDocId(documentUploadTemp.getDocumentUploadTempId());
            dto.setDocName(documentUploadTemp.getDocumentName());
            dto.setInputuser(documentUploadTemp.getInputUserId());
            if (documentUploadTemp.getRecordStatus().getStatusId() == AppConstant.DELETE) {
                dto.setAuthorizeButton(DELETE_BUTTON.replace(ID, documentUploadTemp.getDocumentUploadTempId() + AppConstant.STRING_EMPTY));
            } else {
                dto.setAuthorizeButton(AUTH_BUTTON.replace(ID, documentUploadTemp.getDocumentUploadTempId() + AppConstant.STRING_EMPTY));
            }
            dto.setCatagoryName(documentUploadTemp.getDocCategoryMaster().getDocCategoryName());
            Date inpDateTime = documentUploadTemp.getInpDateTime();
            dto.setInputtime(new SimpleDateFormat(AppConstant.DATE_FORMAT).format(inpDateTime));
            dockAuthDtos.add(dto);
        }

        return dockAuthDtos;
    }

    @Override
    public DocumentUploadTemp searchTempDocumentById(int id) throws Exception {

        DocumentUploadTemp byDocumentUploadTempId = documentUploadRepository.findByDocumentUploadTempId(id);
        byDocumentUploadTempId.setDocumentUploadTempSystemRoles(findTempSystemRolesByDocId(id));

        return byDocumentUploadTempId;
    }

    public List<DocumentUploadTempSystemRole> findTempSystemRolesByDocId(int docId) {

        return documentUploadTempSystemRoleRepository.findByDocumentUploadTempDocumentUploadTempId(docId);
    }

    @Override
    public boolean authDocument(int docId, User user) throws Exception {
        try {
            DocumentUploadMaster documentUploadMaster = new DocumentUploadMaster();
            List<DocumentUploadMasterSystemRole> documentUploadMasterSystemRoles = new ArrayList<>();
            DocumentUploadTemp documentUploadTemp = searchTempDocumentById(docId);
            DocumentUploadHistory documentUploadHistory = new DocumentUploadHistory();
            BeanUtils.copyProperties(documentUploadTemp, documentUploadHistory);
            documentUploadHistory.setActionUserId(user.getUserName());
            documentUploadHistory.setActionDateTime(new Date());
            documentUploadHistory.setTableType(TEMP);
            documentUploadHistory.setDocumentUploadId(documentUploadTemp.getDocumentUploadTempId());
            documentUploadMaster = documentUploadMasterRepository.findByDocumentUploadMstId(docId);
            documentUploadMaster = null != documentUploadMaster ? documentUploadMaster : new DocumentUploadMaster();
            List<DocumentUploadMasterSystemRole> allByDocumentUploadMasterDocumentUploadMstId = documentUploadMasterSystemRoleRepository.findAllByDocumentUploadMasterDocumentUploadMstId(docId);

            if (null != allByDocumentUploadMasterDocumentUploadMstId && !allByDocumentUploadMasterDocumentUploadMstId.isEmpty()) {
                documentUploadMasterSystemRoleRepository.deleteAllByDocumentUploadMasterDocumentUploadMstId(docId);
                documentUploadMaster.setDocumentUploadMstId(docId);
            }
            BeanUtils.copyProperties(documentUploadTemp, documentUploadMaster);
            //TODO Set USer
//            documentUploadMaster.setAuthUserId(user.getUserId() + AppConstant.STRING_EMPTY);
            documentUploadMaster.setAuthDateTime(new Date());
            documentUploadMaster.setRecordStatus(new RecordStatus(7));
            documentUploadMaster.setDocumentUploadMstId(documentUploadTemp.getDocumentUploadTempId());
            documentUploadMaster.setDocumentUploadMasterSystemRoles(documentUploadMasterSystemRoles);
            DocumentUploadMaster save = documentUploadMasterRepository.save(documentUploadMaster);
            List<DocumentUploadTempSystemRole> documentUploadTempSystemRoles = documentUploadTempSystemRoleRepository.findByDocumentUploadTempDocumentUploadTempId(docId);
            for (DocumentUploadTempSystemRole documentUploadTempSystemRole : documentUploadTempSystemRoles) {
                DocumentUploadMasterSystemRole documentUploadMasterSystemRole = new DocumentUploadMasterSystemRole();
                SystemRole systemRoleById = documentUploadTempSystemRole.getSystemRole();
                documentUploadMasterSystemRole.setDocumentUploadMaster(save);
                documentUploadMasterSystemRole.setSystemRole(systemRoleById);
                documentUploadMasterSystemRoles.add(documentUploadMasterSystemRole);
            }
            save.setDocumentUploadMasterSystemRoles(documentUploadMasterSystemRoles);

            documentUploadMasterRepository.save(documentUploadMaster);
            documentUploadHistoryRepository.save(documentUploadHistory);
            documentUploadTempSystemRoleRepository.deleteAllByDocumentUploadTempDocumentUploadTempId(docId);
            documentUploadRepository.deleteByDocumentUploadTempId(docId);

            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void rejectDocument(String reson, int id, User user) throws Exception {
        try {
            DocumentUploadHistory documentUploadHistory = new DocumentUploadHistory();
            DocumentUploadTemp documentUploadTemp = searchTempDocumentById(id);
            BeanUtils.copyProperties(documentUploadTemp, documentUploadHistory);
            documentUploadTemp.setReason(reson);
            documentUploadHistory.setActionDateTime(new Date());
            documentUploadHistory.setTableType(TEMP);
            documentUploadTemp.setRecordStatus(new RecordStatus(8));
            documentUploadTemp.setActionUserId(user.getUserName());
            documentUploadTemp.setActionDateTime(new Date());
            documentUploadRepository.save(documentUploadTemp);
            documentUploadHistoryRepository.save(documentUploadHistory);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int deleteRejectDocument(int document_id, User user) throws Exception {
        try {
            DocumentUploadHistory documentUploadHistory = new DocumentUploadHistory();
            DocumentUploadTemp temp = documentUploadRepository.findByDocumentUploadTempId(document_id);
            if (null != temp) {
                BeanUtils.copyProperties(temp, documentUploadHistory);
                documentUploadHistory.setActionUserId(user.getUserName());
                documentUploadHistory.setActionDateTime(new Date());
                documentUploadHistory.setTableType(DELETE_REJECT);
                documentUploadHistory.setDocumentUploadId(temp.getDocumentUploadTempId());
                documentUploadTempSystemRoleRepository.deleteAllByDocumentUploadTempDocumentUploadTempId(document_id);
                documentUploadRepository.deleteByDocumentUploadTempId(document_id);
                documentUploadHistoryRepository.save(documentUploadHistory);
                return SUCSESS;
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean deleteMsterDocument(int id, User user) throws Exception {
        try {
            DocumentUploadHistory documentUploadHistory = new DocumentUploadHistory();
            DocumentUploadMaster mst = documentUploadMasterRepository.findByDocumentUploadMstId(id);
            if (null != mst) {

                BeanUtils.copyProperties(mst, documentUploadHistory);
                documentUploadHistory.setActionUserId(user.getUserName());
                documentUploadHistory.setActionDateTime(new Date());
                documentUploadHistory.setTableType(MST);
                documentUploadHistory.setDocumentUploadId(mst.getDocumentUploadMstId());

                List<DocumentUploadTempSystemRole> lst = documentUploadTempSystemRoleRepository.findByDocumentUploadTempDocumentUploadTempId(id);
                if (lst != null && lst.size() > 0) {
                    documentUploadTempSystemRoleRepository.deleteAllByDocumentUploadTempDocumentUploadTempId(id);
                }
                documentUploadRepository.deleteByDocumentUploadTempId(id);

                List<DocumentUploadMasterSystemRole> lstMst = documentUploadMasterSystemRoleRepository.findAllByDocumentUploadMasterDocumentUploadMstId(id);
                if (lstMst != null && lstMst.size() > 0) {
                    documentUploadMasterSystemRoleRepository.deleteAllByDocumentUploadMasterDocumentUploadMstId(id);
                }
                documentUploadMasterRepository.deleteByDocumentUploadMstId(id);
                documentUploadHistoryRepository.save(documentUploadHistory);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int deleteAuthorizationDocument(int document_id) throws Exception {
        try {
            DocumentUploadTemp temp = documentUploadRepository.findByDocumentUploadTempId(document_id);
            if (temp == null) {
                DocumentUploadMaster master = documentUploadMasterRepository.findByDocumentUploadMstId(document_id);
                if (master != null) {
                    DocumentUploadTemp newTemp = new DocumentUploadTemp();
                    BeanUtils.copyProperties(master, newTemp);
                    master.setDocumentUploadMasterSystemRoles(documentUploadMasterSystemRoleRepository.findAllByDocumentUploadMasterDocumentUploadMstId(document_id));
                    newTemp.setDocumentUploadTempId(master.getDocumentUploadMstId());
                    newTemp.setRecordStatus(new RecordStatus(AppConstant.DELETE));
                    List<DocumentUploadTempSystemRole> documentUploadTempSystemRoles = new ArrayList<>();
                    for (DocumentUploadMasterSystemRole systemRole : master.getDocumentUploadMasterSystemRoles()) {
                        DocumentUploadTempSystemRole documentUploadTempSystemRole = new DocumentUploadTempSystemRole();
                        documentUploadTempSystemRole.setDocumentUploadTemp(new DocumentUploadTemp(systemRole.getDocumentUploadMaster().getDocumentUploadMstId()));
                        documentUploadTempSystemRole.setSystemRole(new SystemRole(systemRole.getSystemRole().getSystemRoleId()));
                        documentUploadTempSystemRoles.add(documentUploadTempSystemRole);
                    }
                    newTemp.setDocumentUploadTempSystemRoles(documentUploadTempSystemRoles);
                    documentUploadRepository.save(newTemp);
                    return SUCSESS;
                } else {
                    return ERROR;
                }
            } else {
                return RECODE_EXSIST_TEMP;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean deleteTempDocument(int id, User user) throws Exception {
        try {
            DocumentUploadHistory documentUploadHistory = new DocumentUploadHistory();
            DocumentUploadTemp temp = documentUploadRepository.findByDocumentUploadTempId(id);
            if (null != temp) {
                BeanUtils.copyProperties(temp, documentUploadHistory);
                documentUploadHistory.setActionUserId(user.getUserName());
                documentUploadHistory.setActionDateTime(new Date());
                documentUploadHistory.setTableType(TEMP);
                documentUploadHistory.setDocumentUploadId(temp.getDocumentUploadTempId());
                documentUploadTempSystemRoleRepository.deleteAllByDocumentUploadTempDocumentUploadTempId(id);
                documentUploadRepository.deleteByDocumentUploadTempId(id);
                documentUploadHistoryRepository.save(documentUploadHistory);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return false;
    }

    @Override
    public List<RejectedDocumentDto> getAllRejectedDoc(User user) throws Exception {

        List<RejectedDocumentDto> dockAuthDtos = new ArrayList<>();
        Iterable<DocumentUploadTemp> all;
        all = documentUploadRepository.findByRecordStatusStatusId(8);
        for (DocumentUploadTemp documentUploadHistory : all) {
            RejectedDocumentDto dto = new RejectedDocumentDto();
            dto.setDocId(documentUploadHistory.getDocumentUploadTempId());
            dto.setCatName(documentUploadHistory.getDocCategoryMaster().getDocCategoryName());
            dto.setDocName(documentUploadHistory.getDocumentName());
            dto.setRejectedReson(documentUploadHistory.getReason());
            dto.setResubmit(RESUBMIT_BTN.replace(ID, documentUploadHistory.getDocumentUploadTempId() + AppConstant.STRING_EMPTY));


            dto.setRejecteduser(documentUploadHistory.getActionUserId());
            dto.setRejectedtime(new SimpleDateFormat(AppConstant.DATE_FORMAT).format(documentUploadHistory.getActionDateTime()));
            dockAuthDtos.add(dto);
        }
        return dockAuthDtos;

    }

    @Override
    public DocumentUploadMaster searchMstDocumentById(int id) throws Exception {

        DocumentUploadMaster byDocumentUploadMstId = documentUploadMasterRepository.findByDocumentUploadMstId(id);
        byDocumentUploadMstId.setDocumentUploadMasterSystemRoles(findMasterSystemRolesByDocId(id));
        return byDocumentUploadMstId;

    }

    @Override
    public int sendEmail(Email email) throws Exception {
        try {
            DocumentUploadMaster dto = documentUploadMasterRepository.findByDocumentUploadMstId(Integer.parseInt(email.getDocId()));
            email.setDocumentNAme(dto.getDocumentName());
            activeMQEmail.sendFromEmail(email);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<DocumentUploadMasterSystemRole> findMasterSystemRolesByDocId(int docId) {
        return documentUploadMasterSystemRoleRepository.findAllByDocumentUploadMasterDocumentUploadMstId(docId);

    }
}