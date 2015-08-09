package org.hawkular.ircbot

/**
 * @author Jirka Kremser
 *
 */
trait BugResolver {

  def resolve(bugIdentifier: String): String;

}
