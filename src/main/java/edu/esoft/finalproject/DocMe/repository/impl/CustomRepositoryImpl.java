package edu.esoft.finalproject.DocMe.repository.impl;

import edu.esoft.finalproject.DocMe.dto.McgReportDto;
import edu.esoft.finalproject.DocMe.dto.SystemMenuItemWithPrivilegesDto;
import edu.esoft.finalproject.DocMe.repository.CustomRepository;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Scope(value = "singleton")
public class CustomRepositoryImpl implements CustomRepository {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<SystemMenuItemWithPrivilegesDto> getPrivilegedMenuForUserRole(Integer id) {
        String sql = "SELECT\n" +
                "\tsmi.system_menu_name,\n" +
                "\tsmi.url,\n" +
                "\tsmip.view_privilege,\n" +
                "\tsmip.authorization_privilege,\n" +
                "\tsmip.delete_privilege,\n" +
                "\tsmip.save_privilege,\n" +
                "\tsmip.update_privilege\n" +
                "FROM\n" +
                "\tsystem_menu_item smi,\n" +
                "\tsystem_menu_item_privilege smip\n" +
                "WHERE\n" +
                "\tsmi.id = smip.system_menu_item_id\n" +
                "AND smip.system_role_id = ?1";


        Query query = entityManager.createNativeQuery(sql, SystemMenuItemWithPrivilegesDto.class);
        query.setParameter(1, id);

        return (List<SystemMenuItemWithPrivilegesDto>) query.getResultList();
    }

    @Override
    public List<McgReportDto> msgReportJson() {
        String sql = "SELECT\n" +
                "	CONCAT(us.first_name,\" \" ,us.last_name) AS agentName,\n" +
                "	CONCAT(mc.category_name,\" - (\",doc.doc_version,\")\") AS docName,\n" +
                "	info.doc_issue_date AS docIssueDateString,\n" +
                "	info.sign_date AS docSignDateString,\n" +
                "	info.`status` AS signStatus\n" +
                "FROM\n" +
                "	`mcg_agent_document_info` info, user_mst us,mcg_document doc ,mcg_category mc\n" +
                "WHERE info.doc_id = doc.doc_id AND info.agent_code=us.id AND doc.cat_id=mc.category_id";
        Query query = entityManager.createNativeQuery(sql);
        return  query.getResultList();
    }
}
