/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dto

enum class TargetKind {
    /**
     * Target ROLE is used when want to send to a bunch of user in one ROLE,
     *
     * target must be filled with role name
     */
    ROLE,

    /**
     * Target individual is used when want to send to one user,
     *
     * target must be filled with username
     */
    INDIVIDUAL
}