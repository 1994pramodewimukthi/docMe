package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.config.DocCategoryMasterWebixComparator;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import edu.esoft.finalproject.DocMe.repository.DocCategoryMasterRepository;
import edu.esoft.finalproject.DocMe.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocCategoryMasterRepository docCategoryMasterRepository;

    @Override
    public List<DocCategoryMasterWebix> createCategoryWebixTable() throws Exception {
        Iterable<DocCategoryMaster> list = docCategoryMasterRepository.findAllByParentDocCategoryMstIsNullAndOrderBySortingOrder();
        List<DocCategoryMasterWebix> webixList = new ArrayList<>();
        for (DocCategoryMaster master : list) {
            DocCategoryMasterWebix docCategoryMasterWebix = getDocCategoryMasterWebix(master);
            webixList.add(docCategoryMasterWebix);
        }
        return webixList;
    }

    private DocCategoryMasterWebix getDocCategoryMasterWebix(DocCategoryMaster docCategoryMaster) throws Exception {
        Comparator<DocCategoryMasterWebix> comparator = new DocCategoryMasterWebixComparator();

        DocCategoryMasterWebix docCategoryMasterWebix = new DocCategoryMasterWebix();
        docCategoryMasterWebix.setDocCategoryMstId(docCategoryMaster.getDocCategoryMstId());
        docCategoryMasterWebix.setDocCategoryName(docCategoryMaster.getDocCategoryName());
        docCategoryMasterWebix.setSortingOrder(docCategoryMaster.getSortingOrder());
        docCategoryMasterWebix.setInpUserId(docCategoryMaster.getInpUserId());
        docCategoryMasterWebix.setInpDateTime(docCategoryMaster.getInpDateTime());
        docCategoryMasterWebix.setAuthUserId(docCategoryMaster.getAuthUserId());
        docCategoryMasterWebix.setAuthDateTime(docCategoryMaster.getAuthDateTime());
        docCategoryMasterWebix.setReason(docCategoryMaster.getReason());
        if (!docCategoryMaster.getSubCategoryDocList().isEmpty()) {
            for (DocCategoryMaster docCategoryMaster1 : docCategoryMaster.getSubCategoryDocList()) {
                docCategoryMasterWebix.getData().add(getDocCategoryMasterWebix(docCategoryMaster1));
            }
            Collections.sort(docCategoryMasterWebix.getData(), comparator);
        }
        return docCategoryMasterWebix;
    }
}
