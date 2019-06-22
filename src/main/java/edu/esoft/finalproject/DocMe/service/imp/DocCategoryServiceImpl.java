package edu.esoft.finalproject.DocMe.service.imp;


import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.MessageConstant;
import edu.esoft.finalproject.DocMe.dto.AuthRejectCategoryWebix;
import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import edu.esoft.finalproject.DocMe.entity.DocCategoryTemp;
import edu.esoft.finalproject.DocMe.entity.RecordStatus;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.repository.DocCategoryMasterRepository;
import edu.esoft.finalproject.DocMe.repository.DocCategoryTempRepository;
import edu.esoft.finalproject.DocMe.repository.DocumentUploadMasterRepository;
import edu.esoft.finalproject.DocMe.service.DocCategoryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class DocCategoryServiceImpl implements DocCategoryService {

    private static final String MST = "MST";
    private static final String TEMP = "TEMP";
    private static final String DELETE_REJECT = "DELETE_REJECT";
    private static final int SUCSESS = 1;
    private static final int ERROR = 0;
    private static final int OTHER = 2;
    private static final int ERROR_EXIST_IN_HIRARCHY = 3;
    private static final int RECODE_EXSIST_TEMP = 2;
    private static final String ID_ATTR = "id";
    public static final String ATHORIZED_BUTTON = "<input class='btn btn-success btn-table authbtn' type='button' value='Authorize' onclick='authRejectViwer(" + ID_ATTR + ")' >";
    public static final String REJECT_BUTTON = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Reject'  onclick='reject(" + ID_ATTR + ")'>";
    public static final String DELETE_BUTTON = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Delete Authorize'  onclick='authRejectViwer(" + ID_ATTR + ")'>";
    public static final String DELETE_BUTTON_DISABLE = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Delete Authorize'  onclick='authRejectViwer(" + ID_ATTR + ")' disabled>";
    public static final String RESUBMIT_BUTTON = "<input class='btn btn-success btn-table authbtn' type='button' value='Resubmit'  onclick='resubmit(" + ID_ATTR + ")'>";
    public static final String ATHORIZED_BUTTON_DISABLE = "<input class='btn btn-success btn-table authbtn' type='button' value='Authorize' onclick='authRejectViwer(" + ID_ATTR + ")' disabled >";
    public static final String REJECT_BUTTON_DISABLE = "<input class='btn btn-success btn-table rejectbtn' type='button' value='Reject'  onclick='reject(" + ID_ATTR + ")' disabled>";
    public static final String RESUBMIT_BUTTON_DISABLE = "<input class='btn btn-success btn-table authbtn' type='button' value='Resubmit'  onclick='resubmit(" + ID_ATTR + ")' disabled>";
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DocCategoryServiceImpl.class);
    @Autowired
    private DocCategoryMasterRepository docCategoryMasterRepository;
    @Autowired
    private DocCategoryTempRepository docCategoryTempRepository;
    @Autowired
    private DocumentUploadMasterRepository documentUploadMasterRepository;

    @Override
    public int createNewCategory(DocCategoryTemp dt) throws Exception {
        try {
            boolean isSaved = true;
            if (null == dt.getParentDocCategoryTemp()) {
                List<DocCategoryMaster> categoryMasters = docCategoryMasterRepository.getAllMainCategory();
                for (DocCategoryMaster categoryMaster : categoryMasters) {
                    if (categoryMaster.getDocCategoryName().trim().equals(dt.getDocCategoryName().trim())) {
                        isSaved = false;
                        break;
                    }
                }
            } else {
                List<DocCategoryMaster> categoryMasters = docCategoryMasterRepository.getAllDocCategoryMstId(dt.getParentDocCategoryTemp());
                for (DocCategoryMaster categoryMaster : categoryMasters) {
                    if (categoryMaster.getDocCategoryName().trim().equals(dt.getDocCategoryName().trim())) {
                        isSaved = false;
                        break;
                    }
                }
            }

            if (isSaved) {
                int nextId = getCategoryNextId();
                DocCategoryTemp temp = docCategoryTempRepository.findByDocCategoryTempId(nextId);
                if (null == temp) {
                    dt.setDocCategoryName(dt.getDocCategoryName().trim());
                    dt.setDocCategoryTempId(nextId);
                    dt.setInpDateTime(new Date());
                    dt.setRecordStatus(new RecordStatus(AppConstant.PENDDING));
                    docCategoryTempRepository.save(dt);
                    return SUCSESS;
                } else {
                    return ERROR;
                }
            } else {
                return OTHER;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public int checkCatagorySortingExistTemp(DocCategoryTemp docCategoryTemp) throws Exception {
        List<DocCategoryTemp> byParentDocCategoryTemp_andSortingOrder;
        List<DocCategoryMaster> allByParentDocCategoryMstDocCategoryMstId_andSortingOrder;
        try {
            if (null == docCategoryTemp.getParentDocCategoryTemp() || 0 == docCategoryTemp.getParentDocCategoryTemp()) {
                if (null == docCategoryTemp.getDocCategoryTempId() || 0 == docCategoryTemp.getDocCategoryTempId()) {
                    byParentDocCategoryTemp_andSortingOrder = docCategoryTempRepository.findAllByParentDocCategoryTempIsNull_AndSortingOrder(docCategoryTemp.getSortingOrder());
                    allByParentDocCategoryMstDocCategoryMstId_andSortingOrder = docCategoryMasterRepository.findByParentDocCategoryMstIsNull_AndSortingOrder(docCategoryTemp.getSortingOrder());
                } else {
                    byParentDocCategoryTemp_andSortingOrder = docCategoryTempRepository.findAllByParentDocCategoryTempIsNull_AndSortingOrder(docCategoryTemp.getSortingOrder(), docCategoryTemp.getDocCategoryTempId());
                    allByParentDocCategoryMstDocCategoryMstId_andSortingOrder = docCategoryMasterRepository.findByParentDocCategoryMstIsNull_AndSortingOrder(docCategoryTemp.getSortingOrder(), docCategoryTemp.getDocCategoryTempId());
                }
            } else {
                if (null == docCategoryTemp.getDocCategoryTempId() || 0 == docCategoryTemp.getDocCategoryTempId()) {
                    byParentDocCategoryTemp_andSortingOrder = docCategoryTempRepository.findAllByParentDocCategoryTemp_AndSortingOrder(docCategoryTemp.getParentDocCategoryTemp(), docCategoryTemp.getSortingOrder());
                    allByParentDocCategoryMstDocCategoryMstId_andSortingOrder = docCategoryMasterRepository.findAllByParentDocCategoryMstDocCategoryMstId_AndSortingOrder(docCategoryTemp.getParentDocCategoryTemp(), docCategoryTemp.getSortingOrder());
                } else {
                    byParentDocCategoryTemp_andSortingOrder = docCategoryTempRepository.findAllByParentDocCategoryTemp_AndSortingOrder_AndDocCategoryTempIdNot(docCategoryTemp.getParentDocCategoryTemp(), docCategoryTemp.getSortingOrder(), docCategoryTemp.getDocCategoryTempId());
                    allByParentDocCategoryMstDocCategoryMstId_andSortingOrder = docCategoryMasterRepository.findAllByParentDocCategoryMstDocCategoryMstId_AndSortingOrder_AndDocCategoryMstIdNot(docCategoryTemp.getParentDocCategoryTemp(), docCategoryTemp.getSortingOrder(), docCategoryTemp.getDocCategoryTempId());
                }
            }
            if (null == byParentDocCategoryTemp_andSortingOrder || 0 == byParentDocCategoryTemp_andSortingOrder.size()) {
                if (null == allByParentDocCategoryMstDocCategoryMstId_andSortingOrder || 0 == allByParentDocCategoryMstDocCategoryMstId_andSortingOrder.size()) {
                    return SUCSESS;
                } else {
                    return ERROR_EXIST_IN_HIRARCHY;
                }
            } else {
                return RECODE_EXSIST_TEMP;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }


    @Override
    public DocCategoryMaster loadModifyCategory(int category_id) throws Exception {
        try {
            return docCategoryMasterRepository.findByDocCategoryMstId(category_id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public int checkCatagorySortingExistMst(DocCategoryMaster docCategoryMaster) throws Exception {
        List<DocCategoryTemp> byParentDocCategoryTemp_andSortingOrder;
        List<DocCategoryMaster> allByParentDocCategoryMstDocCategoryMstId_andSortingOrder;
        try {
            if (null == docCategoryMaster.getParentDocCategoryMst() || null == docCategoryMaster.getParentDocCategoryMst().getDocCategoryMstId()) {
                byParentDocCategoryTemp_andSortingOrder = docCategoryTempRepository.findAllByParentDocCategoryTempIsNull_AndSortingOrder(docCategoryMaster.getSortingOrder());
                allByParentDocCategoryMstDocCategoryMstId_andSortingOrder = docCategoryMasterRepository.findByParentDocCategoryMstIsNull_AndSortingOrder(docCategoryMaster.getSortingOrder(), docCategoryMaster.getDocCategoryMstId());
            } else {
                byParentDocCategoryTemp_andSortingOrder = docCategoryTempRepository.findAllByParentDocCategoryTemp_AndSortingOrder(docCategoryMaster.getParentDocCategoryMst().getDocCategoryMstId(), docCategoryMaster.getSortingOrder());
                allByParentDocCategoryMstDocCategoryMstId_andSortingOrder = docCategoryMasterRepository.findAllByParentDocCategoryMstDocCategoryMstId_AndSortingOrder_AndDocCategoryMstIdNot(docCategoryMaster.getParentDocCategoryMst().getDocCategoryMstId(), docCategoryMaster.getSortingOrder(), docCategoryMaster.getDocCategoryMstId());
            }
            if (null == byParentDocCategoryTemp_andSortingOrder || 0 == byParentDocCategoryTemp_andSortingOrder.size()) {
                if (null == allByParentDocCategoryMstDocCategoryMstId_andSortingOrder || 0 == allByParentDocCategoryMstDocCategoryMstId_andSortingOrder.size()) {
                    return SUCSESS;
                } else {
                    return ERROR_EXIST_IN_HIRARCHY;
                }
            } else {
                return RECODE_EXSIST_TEMP;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public int ModifyCategory(DocCategoryMaster master) throws Exception {
        try {
            DocCategoryTemp tempCheck = docCategoryTempRepository.findByDocCategoryTempId(master.getDocCategoryMstId());
            if (null == tempCheck) {
                DocCategoryMaster docCategoryMst = docCategoryMasterRepository.findByDocCategoryMstId(master.getDocCategoryMstId());
                if (null != docCategoryMst) {
                    DocCategoryTemp temp = new DocCategoryTemp();
                    temp.setDocCategoryTempId(master.getDocCategoryMstId());
                    if (null != master.getParentDocCategoryMst()) {
                        temp.setParentDocCategoryTemp(master.getParentDocCategoryMst().getDocCategoryMstId());
                    } else {
                        temp.setParentDocCategoryTemp(0);
                    }
                    temp.setDocCategoryName(master.getDocCategoryName());
                    temp.setDocCategoryDescription(master.getDocCategoryDescription());
                    temp.setRecordStatus(new RecordStatus(AppConstant.PENDDING));
                    temp.setInpUserId(master.getInpUserId());
                    temp.setSortingOrder(master.getSortingOrder());
                    temp.setInpDateTime(new Date());
                    docCategoryTempRepository.save(temp);
                    return SUCSESS;
                } else {
                    return ERROR;
                }
            } else {
                return RECODE_EXSIST_TEMP;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public AuthRejectCategoryWebix getPendingCategoryList(User user) throws Exception {
        try {
            AuthRejectCategoryWebix webixTable1 = new AuthRejectCategoryWebix();
            List<AuthRejectCategoryWebix> webixes = new ArrayList<>();

            List<DocCategoryTemp> allByRecordStatus = new ArrayList<>();
            allByRecordStatus = docCategoryTempRepository.findAllByRecordStatusPendingDelet();

            for (DocCategoryTemp temp : allByRecordStatus) {
                AuthRejectCategoryWebix webixTable2 = new AuthRejectCategoryWebix();
                webixTable2.setCategoryId(temp.getDocCategoryTempId());
                webixTable2.setCategoryName(temp.getDocCategoryName());
                if (null != temp.getParentDocCategoryTemp() && temp.getParentDocCategoryTemp() != 0) {
                    DocCategoryMaster master = docCategoryMasterRepository.findByDocCategoryMstId(temp.getParentDocCategoryTemp());
                    if (master != null) {
                        webixTable2.setParentCategoryName("<b> " + master.getDocCategoryName() + "</b>");
                    } else {
                        webixTable2.setParentCategoryName("<b> Pending </b>'");
                    }
                } else {
                    webixTable2.setParentCategoryName("");
                }
                webixTable2.setInputUser(temp.getInpUserId());
                webixTable2.setAutorizationStatus(temp.getRecordStatus().getStatusId());
                if (temp.getRecordStatus().getStatusId() == AppConstant.DELETE) {
                    if (true) { // todo
                        webixTable2.setAuthButton(DELETE_BUTTON.replace(ID_ATTR, temp.getDocCategoryTempId() + ""));
                    } else {
                        webixTable2.setAuthButton(DELETE_BUTTON_DISABLE.replace(ID_ATTR, temp.getDocCategoryTempId() + ""));
                    }
                } else {
                    if (true) { // todo
                        webixTable2.setAuthButton(ATHORIZED_BUTTON.replace(ID_ATTR, temp.getDocCategoryTempId() + ""));
                    } else {
                        webixTable2.setAuthButton(ATHORIZED_BUTTON_DISABLE.replace(ID_ATTR, temp.getDocCategoryTempId() + ""));
                    }
                }
                Date inpDateTime = temp.getInpDateTime();
                webixTable2.setInputDateTime(new SimpleDateFormat("dd MMM yyyy").format(inpDateTime));
                webixes.add(webixTable2);
            }
            webixTable1.setData(webixes);
            return webixTable1;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public DocCategoryTemp getCategoryById(int category_id) throws Exception {
        return docCategoryTempRepository.findByDocCategoryTempId(category_id);
    }

    @Override
    public int authCategory(int docCatId, String userId) throws Exception {
        try {
            DocCategoryTemp dct = docCategoryTempRepository.findByDocCategoryTempId(docCatId);
            if (null != dct) {
                DocCategoryMaster master = docCategoryMasterRepository.findByDocCategoryMstId(docCatId);
                if (null != master) {
                    master.setDocCategoryName(dct.getDocCategoryName());
                    master.setInpUserId(dct.getInpUserId());
                    master.setInpDateTime(new Date());
                    master.setSortingOrder(dct.getSortingOrder());
                    master.setAuthUserId(userId);
                    master.setAuthDateTime(new Date());
                    master.setDocCategoryDescription(dct.getDocCategoryDescription());
                    docCategoryMasterRepository.save(master);


                    docCategoryTempRepository.deleteByDocCategoryTempId(dct.getDocCategoryTempId());
                    return SUCSESS;
                } else {
                    DocCategoryMaster dcm = new DocCategoryMaster();
                    dcm.setDocCategoryMstId(dct.getDocCategoryTempId());
                    dcm.setDocCategoryName(dct.getDocCategoryName());
                    System.out.println(dct.getParentDocCategoryTemp());
                    if (dct.getParentDocCategoryTemp() != null && dct.getParentDocCategoryTemp() != 0) {
                        dcm.setParentDocCategoryMst(new DocCategoryMaster(dct.getParentDocCategoryTemp()));
                    } else {
                        dcm.setParentDocCategoryMst(null);
                    }
                    dcm.setSortingOrder(dct.getSortingOrder());
                    dcm.setInpUserId(dct.getInpUserId());
                    dcm.setInpDateTime(dct.getInpDateTime());
                    dcm.setAuthUserId(userId);
                    dcm.setAuthDateTime(new Date());
                    dcm.setRecordStatus(new RecordStatus(AppConstant.AUTHORIZED));
                    dcm.setDocCategoryDescription(dct.getDocCategoryDescription());
                    docCategoryMasterRepository.save(dcm);

                    docCategoryTempRepository.delete(dct.getDocCategoryTempId());
                    return SUCSESS;
                }
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }


    @Override
    public int rejectCategory(int catId, String rejectReason, String userId) throws Exception {
        try {
            DocCategoryTemp docCategoryTemp = docCategoryTempRepository.findByDocCategoryTempId(catId);
            docCategoryTemp.setRecordStatus(new RecordStatus(AppConstant.REJECTED));
            docCategoryTemp.setReason(rejectReason);
            docCategoryTempRepository.save(docCategoryTemp);
            return SUCSESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public AuthRejectCategoryWebix getRejectedLst() throws Exception {
        try {
            int penddingStatus = AppConstant.REJECTED;
            AuthRejectCategoryWebix webixTable1 = new AuthRejectCategoryWebix();
            List<AuthRejectCategoryWebix> webixes = new ArrayList<>();
            List<DocCategoryTemp> allByRecordStatus = docCategoryTempRepository.findAllByRecordStatus(penddingStatus);
            for (DocCategoryTemp temp : allByRecordStatus) {
                AuthRejectCategoryWebix webixTable2 = new AuthRejectCategoryWebix();
                webixTable2.setCategoryId(temp.getDocCategoryTempId());
                webixTable2.setCategoryName(temp.getDocCategoryName());
                if (null != temp.getParentDocCategoryTemp() && temp.getParentDocCategoryTemp() != 0) {
                    DocCategoryMaster master = docCategoryMasterRepository.findByDocCategoryMstId(temp.getParentDocCategoryTemp());
                    if (master != null) {
                        webixTable2.setParentCategoryName("<b> " + master.getDocCategoryName() + "</b>");
                    } else {
                        webixTable2.setParentCategoryName("<b> Pending </b>'");
                    }
                } else {
                    webixTable2.setParentCategoryName("");
                }
                webixTable2.setReason(temp.getReason());
                if (true) {// todo
                    webixTable2.setAuthButton(RESUBMIT_BUTTON.replace(ID_ATTR, temp.getDocCategoryTempId() + ""));
                } else {
                    webixTable2.setAuthButton(RESUBMIT_BUTTON_DISABLE.replace(ID_ATTR, temp.getDocCategoryTempId() + ""));
                }
                webixTable2.setInputUser(temp.getInpUserId());
                Date inpDateTime = temp.getInpDateTime();
                webixTable2.setInputDateTime(new SimpleDateFormat("dd MMM yyyy").format(inpDateTime));
                webixes.add(webixTable2);
            }
            webixTable1.setData(webixes);
            return webixTable1;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }


    @Override
    public int updateRejectedCategory(DocCategoryTemp temp) throws Exception {
        try {
            DocCategoryTemp docCategoryTemp = docCategoryTempRepository.findByDocCategoryTempId(temp.getDocCategoryTempId());
            if (null != docCategoryTemp) {
                docCategoryTemp.setSortingOrder(temp.getSortingOrder());
                docCategoryTemp.setDocCategoryName(temp.getDocCategoryName());
                docCategoryTemp.setDocCategoryDescription(temp.getDocCategoryDescription());
                docCategoryTemp.setInpDateTime(new Date());
                docCategoryTemp.setRecordStatus(new RecordStatus(AppConstant.PENDDING));
                docCategoryTemp.setInpUserId(temp.getInpUserId());
                docCategoryTemp.setReason(AppConstant.STRING_EMPTY);
                docCategoryTempRepository.save(docCategoryTemp);
                return SUCSESS;
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public int getCategoryNextId() throws Exception {
        try {
            int maxTemp = 0;
            int maxMst = 0;
            synchronized (this) {
                try {
                    maxTemp = docCategoryTempRepository.findMaxId();
                } catch (NullPointerException e) {
                    LOGGER.error(e.getMessage());
                }

                try {
                    maxMst = docCategoryMasterRepository.findMaxId();
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
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
