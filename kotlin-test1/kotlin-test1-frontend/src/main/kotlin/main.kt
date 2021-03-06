import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Json

fun main(args: Array<String>) {
    window.onload = {
        fetch("1")
        //bind elements
        val input = document.getElementById("count_id") as HTMLInputElement
        val button = document.getElementById("button_id")
        //bind click listener on button
        button?.addEventListener("click", fun(event: Event) {
            fetch(input.value)
        })
    }
}

fun fetch(count: String): Unit {
    val url = "http://localhost:8080/api/ping/$count"
    val req = XMLHttpRequest()
    req.onloadend = fun(event: Event){
        val text = req.responseText
        println(text)
        val objArray  = JSON.parse<Array<Json>>(text)
        val textarea = document.getElementById("textarea_id") as HTMLTextAreaElement
        textarea.value = ""
        objArray.forEach {
            val message = it["message"]
            textarea.value += "$message\n"
        }
    }
    req.open("GET", url, true)
    req.send()
}
