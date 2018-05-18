package com.haulmont.globaleventsdemo

import com.haulmont.globaleventsdemo.composite.Screen1
import com.haulmont.masquerade.components.AppMenu
import groovy.sql.Sql

import java.sql.DriverManager

import static com.haulmont.masquerade.Components._$

class SystemTestSupport {

    static getSql() {
        new Sql(DriverManager.getConnection('jdbc:postgresql://localhost/glevtdemo', 'cuba', 'cuba'))
    }

    static Screen1 openScreen() {
        AppMenu.Menu<Screen1> screen1Menu = new AppMenu.Menu(Screen1, 'application-glevtdemo', 'screen1')
        _$(AppMenu).openItem(screen1Menu)
    }

}
