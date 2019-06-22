package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import edu.esoft.finalproject.DocMe.entity.DocCategoryTemp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocCategoryTempRepository extends CrudRepository<DocCategoryTemp, Integer> {

    public DocCategoryTemp findByDocCategoryTempId(int catId);

    @Query(value = "SELECT MAX(doc_category_temp_id) AS doc_category_temp_id  FROM doc_category_temp", nativeQuery = true)
    public Integer findMaxId();

    @Query(value = "SELECT * FROM doc_category_temp WHERE parent_doc_category_temp IS NULL AND sorting_order=?1", nativeQuery = true)
    List<DocCategoryTemp> findAllByParentDocCategoryTempIsNull_AndSortingOrder(int sortingOrder);

    @Query(value = "SELECT * FROM doc_category_temp WHERE parent_doc_category_temp IS NULL AND sorting_order=?1  AND NOT doc_category_temp_id=?2", nativeQuery = true)
    List<DocCategoryTemp> findAllByParentDocCategoryTempIsNull_AndSortingOrder(int sortingOrder, int docCatTempId);

    List<DocCategoryTemp> findAllByParentDocCategoryTemp_AndSortingOrder(int parentDocCategoryTemp, int sortingOrder);

    List<DocCategoryTemp> findAllByParentDocCategoryTemp_AndSortingOrder_AndDocCategoryTempIdNot(int parentDocCategoryTemp,int sortingOrder,int docCatTempId);

    @Query(value = "SELECT * FROM doc_category_temp WHERE ( record_status =6 OR record_status=5 )ORDER BY sorting_order", nativeQuery = true)
    public List<DocCategoryTemp> findAllByRecordStatusPendingDelet();

    @Modifying
    @Query(value = "DELETE FROM doc_category_temp WHERE doc_category_temp_id=?1", nativeQuery = true)
    public void deleteByDocCategoryTempId(int docCatId);

    @Query(value = "SELECT * FROM doc_category_temp WHERE record_status =?1 ORDER BY sorting_order", nativeQuery = true)
    public List<DocCategoryTemp> findAllByRecordStatus(int statusId);
}
