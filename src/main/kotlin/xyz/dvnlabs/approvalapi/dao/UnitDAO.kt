/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.Unit

@Repository
interface UnitDAO : GenericDAO<Unit, String> {


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