package com.haulmont.globaleventsdemo.composite

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.Label

class Screen1 extends Composite<Screen1> {

    @Wire
    Label receivedLab

    @Wire
    Button sendBtn
}
