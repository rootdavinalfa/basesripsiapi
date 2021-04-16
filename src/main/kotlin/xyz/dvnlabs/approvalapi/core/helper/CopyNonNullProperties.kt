/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.helper

import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import org.springframework.lang.NonNull

/**
 * ## Copy Non Null
 * Class helper for ignoring null properties on specified object.
 *
 * Note:
 * - If object properties is null, then the properties will be ignored
 * - If object properties is null, and you specified ignored process properties,
 * then the properties will be processed even it null
 */
class CopyNonNullProperties {

    companion object {
        fun copyNonNullProperties(src: Any, target: Any) {
            BeanUtils.copyProperties(src, target, *getNullPropertyNames(src))
        }

        private fun getNullPropertyNames(source: Any): Array<String> {
            val src: BeanWrapper = BeanWrapperImpl(source)
            val pds = src.propertyDescriptors
            val emptyNames: MutableSet<String> = HashSet()
            for (pd in pds) {
                val srcValue = src.getPropertyValue(pd.name)
                if (srcValue == null) emptyNames.add(pd.name)
            }
            return emptyNames.toTypedArray()
        }

        /**
         * Copy non null properties.
         *
         *
         * This method used for dont process specified field for further processing
         * ignoreProperties provided by BeanUtils
         *
         * **Example:**
         *
         * class SomethingDTO {

         * &#x09;private String name;

         * &#x09;private String address;
         *
         * &#x09;private String dontProcess;
         *
         * }
         *
         * Thus you want to skip dontProcess field / variable. you can provide like this
         *
         *
         * copyNonNullProperties(source,target,"dontProcess")
         *
         * *Above will ignore process of null value on field dontProcess *
         *
         *
         * Or if you want to more than one
         *
         * copyNonNullProperties(source,target,"dontProcess","address")
         *
         * *Above will ignore process of null value on field 'dontProcess' and 'address' *
         *
         * **Note:**
         *
         *  1. Ignored process is case sensitive
         *  2. Must specified dontProcessProperties
         *
         *
         * [src]                   the src
         *
         * [target]                the target
         *
         * [dontProcessProperties] the dont process properties
         */
        fun copyNonNullProperties(src: Any, target: Any, @NonNull vararg dontProcessProperties: String?) {
            BeanUtils.copyProperties(src, target, *getNullPropertyNames(src, *dontProcessProperties))
        }

        /**
         * Get null property names string [ ].
         *
         * @param source  the source
         * @param ignored the ignored
         * @return the string [ ]
         */
        private fun getNullPropertyNames(source: Any, vararg ignored: String?): Array<String?> {
            val src: BeanWrapper = BeanWrapperImpl(source)
            val pds = src.propertyDescriptors
            val emptyNames: MutableSet<String> = HashSet()
            for (pd in pds) {
                val srcValue = src.getPropertyValue(pd.name)
                if (srcValue == null) emptyNames.add(pd.name)
            }
            emptyNames.removeAll(ignored.toList())
            return emptyNames.toTypedArray()
        }

    }
}