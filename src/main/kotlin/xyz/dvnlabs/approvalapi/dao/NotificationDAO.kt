/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.Notification

@Repository
interface NotificationDAO : GenericDAO<Notification,Long>{
}