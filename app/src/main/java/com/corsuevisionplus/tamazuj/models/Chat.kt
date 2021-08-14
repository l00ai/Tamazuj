package com.corsuevisionplus.tamazuj.models

class Chat {
    var sender: String? = null
    var receiver: String? = null
    var message: String? = null
    var time: String? = null

    constructor(sender: String?, receiver: String?, message: String?,time: String?) {
        this.sender = sender
        this.receiver = receiver
        this.message = message
        this.time = time
    }

    constructor() {}


}