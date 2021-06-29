/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.Notification

@Repository
interface NotificationDAO : GenericDAO<Notification, String>, SpecificationRepository<Notification> {

    @Aggregation(
        "{" +
                "\$group: { _id: {\n" +
                "    \$or: [\n" +
                "      {\n" +
                "        '\$eq': ['\$_id', null]\n" +
                "        \n" +
                "      }, \n" +
                "      {\n" +
                "        '\$gt': ['\$_id', null]\n" +
                "        \n" +
                "      }\n" +
                "      ]\n" +
                "    \n" +
                "  } ,maxid: {\$max: '\$_id'} }" +
                "}"
    )
            /*@Aggregation("{ \$limit: 1 , \$sort: {_id : -1}}")*/
    fun firstIDDesc(): String?

}