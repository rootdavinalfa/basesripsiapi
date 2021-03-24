/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.helper

fun Any.isString(): Boolean {
    return this is String
}

fun Any?.isNull(): Boolean {
    return this == null
}

fun Any.isList(): Boolean{
    return this is Collection<*>
}