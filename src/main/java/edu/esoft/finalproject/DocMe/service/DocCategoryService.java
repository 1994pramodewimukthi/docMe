package edu.esoft.finalproject.DocMe.service;


import edu.esoft.finalproject.DocMe.dto.AuthRejectCategoryWebix;
import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import edu.esoft.finalproject.DocMe.entity.DocCategoryTemp;
import edu.esoft.finalproject.DocMe.entity.User;

public interface DocCategoryService {

    int createNewCategory(DocCategoryTemp docCategoryTemp) throws Exception;

    DocCategoryMaster loadModifyCategory(int category_id) throws Exception;

    int checkCatagorySortingExistMst(DocCategoryMaster docCategoryMaster) throws Exception;

    int checkCatagorySortingExistTemp(DocCategoryTemp docCategoryTemp) throws Exception;

    int ModifyCategory(DocCategoryMaster master) throws Exception;

    AuthRejectCategoryWebix getPendingCategoryList(User user)throws Exception;

    public DocCategoryTemp getCategoryById(int category_id) throws Exception;

    public int authCategory(int catId, String userId) throws Exception;

    public int rejectCategory(int catId, String rejectReason, String userId) throws Exception;

    public AuthRejectCategoryWebix getRejectedLst() throws Exception;

    public int updateRejectedCategory(DocCategoryTemp temp) throws Exception;
}

