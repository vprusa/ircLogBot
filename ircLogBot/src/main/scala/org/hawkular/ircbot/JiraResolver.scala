package org.hawkular.ircbot

import java.net.{URI, URISyntaxException}

import com.atlassian.jira.rest.client.api.{JiraRestClient, JiraRestClientFactory}
import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import com.atlassian.util.concurrent.Promise

/**
 * @author Jirka Kremser
 *
 */
class JiraResolver extends BugResolver {

  val JIRA_URL = "https://issues.jboss.org"
  var restClient: JiraRestClient = null

  override def resolve(bugIdentifier: String): String = {
    val issuePromise: Promise[Issue] = getRestClient().getIssueClient().getIssue(bugIdentifier)
    issuePromise.claim().getSummary()
  }

  def resolveAsync(bugIdentifier: String): Promise[Issue] =
    getRestClient().getIssueClient().getIssue(bugIdentifier)


  private def setupJiraClient(url: String): JiraRestClient = {
    val factory: JiraRestClientFactory = new AsynchronousJiraRestClientFactory()
    val restClient: JiraRestClient = factory.createWithBasicHttpAuthentication(new URI(url), "foo", "123456")
    restClient
  }

  private def getRestClient(): JiraRestClient = {
    if (restClient == null) {
      try {
        restClient = setupJiraClient(JIRA_URL);
      } catch {
        case e: URISyntaxException => e.printStackTrace()
      }
    }
    restClient
  }

}
