#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}

import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun main(args: Array<String>) {
  log.info("Hello, World!")
}
