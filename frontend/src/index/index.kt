package index

import app.app
import kotlinext.js.require
import react.dom.render
import kotlin.browser.document

fun main() {
    require("bootstrap/dist/css/bootstrap.css")
    require("bootstrap/dist/js/bootstrap.js")
    render(document.getElementById("root")) {
        app()
    }
}
