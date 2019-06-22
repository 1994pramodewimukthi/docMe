package edu.esoft.finalproject.DocMe.repository;


import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocCategoryMasterRepository extends CrudRepository<DocCategoryMaster, Integer> {

    @Query(value = "SELECT * FROM doc_category_mst WHERE parent_doc_category_mst_id IS  NULL  ORDER BY sorting_order", nativeQuery = true)
    public List<DocCategoryMaster> findAllByParentDocCategoryMstIsNullAndOrderBySortingOrder();

    @Query(value = "SELECT MAX(doc_category_mst_id) AS doc_category_mst_id  FROM doc_category_mst", nativeQuery = true)
    public Integer findMaxId();

    @Query(value = "SELECT * FROM doc_category_mst WHERE parent_doc_category_mst_id IS NULL AND record_status= '7'", nativeQuery = true)
    public List<DocCategoryMaster> getAllMainCategory();

    @Query(value = "SELECT * FROM doc_category_mst WHERE parent_doc_category_mst_id=?1 AND record_status= '7'", nativeQuery = true)
    List<DocCategoryMaster> getAllDocCategoryMstId(Integer parentDocCategoryTemp);

    public DocCategoryMaster findByDocCategoryMstId(int mstId);

    @Query(value = "SELECT * FROM doc_category_mst WHERE parent_doc_category_mst_id IS  NULL  AND sorting_order=?1 ", nativeQuery = true)
    List<DocCategoryMaster> findByParentDocCategoryMstIsNull_AndSortingOrder(int sortingOrder);

    @Query(value = "SELECT * FROM doc_category_mst WHERE parent_doc_category_mst_id IS  NULL  AND sorting_order=?1 AND NOT doc_category_mst_id=?2", nativeQuery = true)
    List<DocCategoryMaster> findByParentDocCategoryMstIsNull_AndSortingOrder(int sortingOrder, int docCatID);

    List<DocCategoryMaster> findAllByParentDocCategoryMstDocCategoryMstId_AndSortingOrder_AndDocCategoryMstIdNot(int pParentDocCategoryMstDocCategoryMstId,int sortingOrder,int docCatId);

    List<DocCategoryMaster> findAllByParentDocCategoryMstDocCategoryMstId_AndSortingOrder(int pParentDocCategoryMstDocCategoryMstId,int sortingOrder);

}
