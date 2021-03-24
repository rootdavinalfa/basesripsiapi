/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.specification

/**
 * ## QueryOperation
 * This class specified for help the [QueryHelper] for determining query action
 */
enum class QueryOperation {
    EQUAL,
    NOT_EQUAL,
    MATCH_ANY,
    MATCH_START,
    MATCH_END,
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL,
    EQUAL_IN,
    NOT_IN
}