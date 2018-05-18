package com.haulmont.globaleventsdemo.composite

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.PasswordField
import com.haulmont.masquerade.components.TextField

class LoginWindow extends Composite<LoginWindow> {

    @Wire
    TextField loginField;

    @Wire
    PasswordField passwordField;

    @Wire
    Button loginButton

    MainWindow login() {
        loginButton.click()
        new MainWindow()
    }
}
