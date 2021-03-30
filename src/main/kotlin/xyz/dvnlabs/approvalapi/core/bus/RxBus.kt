/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.bus

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.springframework.stereotype.Component

@Component
class RxBus {

    companion object {

        private val publisher = PublishSubject.create<Any>()

        fun publish(event: Any) {
            publisher.onNext(event)
        }

        /**Listen should return an Observable and not the publisher
         * Using ofType we filter only events that match that class type
         *
         * [eventType] Fill this with Event Class
         *
         **/
        fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
    }

}