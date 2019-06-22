package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.MCGCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCGCategoryRepository extends CrudRepository<MCGCategory, Integer> {

    @Query(value = "SELECT * FROM mcg_category WHERE category_name=?1", nativeQuery = true)
    List<MCGCategory> findByCategoryName(String catName) throws Exception;

}
