package common.component.matchsearch

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h2

class MatchSearch : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        h2 {
            +"Rise project"
        }
    }
}

fun RBuilder.matchSearch() = child(MatchSearch::class) {}
