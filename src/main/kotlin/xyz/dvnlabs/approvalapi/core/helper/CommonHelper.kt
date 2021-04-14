/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.helper


open class CommonHelper {

    companion object {
        fun getStringSeq(
            lastSeq: String, prefix: String, midfix: String, zeroFill: Int
        ): String {
            val max = lastSeq.substring(lastSeq.length - zeroFill).toLong()

            return prefix + midfix + String.format("%0" + zeroFill + "d", max + 1)
        }
    }

}