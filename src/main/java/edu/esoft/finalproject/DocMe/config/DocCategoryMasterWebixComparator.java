package edu.esoft.finalproject.DocMe.config;

import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;

import java.util.Comparator;

public class DocCategoryMasterWebixComparator implements Comparator<DocCategoryMasterWebix> {
    @Override
    public int compare(DocCategoryMasterWebix o1, DocCategoryMasterWebix o2) {
        return o1.getSortingOrder() - o2.getSortingOrder();
    }

}
