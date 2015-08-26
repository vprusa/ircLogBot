package org.hawkular.ircbot


import java.net._
import java.util.regex.Pattern

import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.{MessageEvent, PrivateMessageEvent}
import org.slf4j.{Logger, LoggerFactory}


/**
 *
 * @author Jiri Kremser
 */
class IrcBotListener(var server: String, var channel: String) extends ListenerAdapter[IrcLogBot] {

  val logger: Logger = LoggerFactory.getLogger(this.getClass.getName)

  val JIRA_PROJECT = "foo-"
  val JIRA_PATTERN = Pattern.compile("(?i)(" + JIRA_PROJECT + "\\d{1,5})")
  val ECHO_PATTERN = Pattern.compile("(?i)echo[ ]+(.+)")

  val jiraResolver: JiraResolver = new JiraResolver()

  def IrcBotListener(server: String, channel: String) {
    this.server = server
    this.channel = channel
  }


//  override def onGenericMessage(event: GenericMessageEvent[IrcLogBot]) {
  override def onMessage(event: MessageEvent[IrcLogBot]) {
    if (event.getUser().getNick().toLowerCase().contains("bot")) {
      return
    }
    // do nothing, just log the message
    logger.info(s"${event.getUser.getNick} ${event.getMessage}")

//    val bot = event.getBot()
//    event.respond("bot" + event.getMessage)
    //        if (!bot.getNick().equals(bot.getName())) {
    //            bot.changeNick(bot.getName())
    //        }
//    val message: String = event.getMessage()

    //
    //        // react to Jira bugs in the messages
    //        val jiraMatcher: Matcher = JIRA_PATTERN.matcher(message);
    //        while (jiraMatcher.find()) {
    //            //            final String response = jiraResolver.resolve(bzMatcher.group(1));
    //            //            bot.sendMessage(event.getChannel(), response);
    //            final String bugId = jiraMatcher.group(1);
    //            final Promise<Issue> issuePromise = jiraResolver.resolveAsync(bugId);
    //            issuePromise.done(new Effect<Issue>() {
    //                @Override
    //                public void apply(Issue a) {
    //                    bot.sendMessage(event.getChannel(), bugId + ": " + Color.RED + a.getSummary() + Color.NORMAL
    //                        + ", priority: " + Color.GREEN + a.getPriority().getName() + Color.NORMAL + ", created: "
    //                        + a.getCreationDate().toString("YYYY-MM-DD") + "  [ " + JiraResolver.JIRA_URL + "/browse/"
    //                        + bugId + " ]");
    //                }
    //            });
    //            issuePromise.fail(new Effect<Throwable>() {
    //                @Override
    //                public void apply(Throwable e) {
    //                    bot.sendMessage(event.getChannel(),
    //                        "Failed to access bug " + bugId + " Cause: " + shorten(e.getMessage()));
    //                }
    //            });
    //        }
    //
  }


  override def onPrivateMessage(msg: PrivateMessageEvent[IrcLogBot]) {
    msg.respond(s"Hey ${msg.getUser.getNick}, you'll find the logs on http://$host/logs")
  }


  //    override def onDisconnect(disconnectEvent: DisconnectEvent[IrcLogBot]) {
  //        boolean connected = false;
  //        while (!connected) {
  //            Thread.sleep(60 * 1000L); // 1 minute
  //            try {
  //                PircBotX newBot = new IrcLogBot(this);
  //                newBot.connect(this.server);
  //                newBot.joinChannel(this.channel);
  //
  //                connected = true;
  //            } catch (Exception e) {
  //                System.err.println("Failed to reconnect to " + disconnectEvent.getBot().getServer() + " IRC server: "
  //                    + e);
  //            }
  //        }
  //
  //        // Try to clean up the old bot, so it can release any memory, sockets, etc. it's using.
  //        PircBotX oldBot = disconnectEvent.getBot();
  //        Set<Listener> oldListeners = new HashSet<Listener>(oldBot.getListenerManager().getListeners());
  //        for (Listener oldListener : oldListeners) {
  //            oldBot.getListenerManager().removeListener(oldListener);
  //        }
  //    }


//  override def onNickChange(event: NickChangeEvent[IrcLogBot]) =
  //Store the result
//    names.put(event.getOldNick(), event.getNewNick())

  def host = InetAddress.getLocalHost.getHostAddress

}
