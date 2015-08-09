package org.hawkular.ircbot


import java.text.SimpleDateFormat
import java.util.regex.Pattern

import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.{NickChangeEvent, PrivateMessageEvent}
import org.pircbotx.hooks.types.GenericMessageEvent


/**
 *
 * @author Jiri Kremser
 */
class IrcBotListener(var server: String, var channel: String) extends ListenerAdapter[IrcLogBot] {

  val JIRA_PROJECT = "foo-";
  val JIRA_PATTERN = Pattern.compile("(?i)(" + JIRA_PROJECT + "\\d{1,5})");
  val ECHO_PATTERN = Pattern.compile("(?i)echo[ ]+(.+)");

  val jiraResolver: JiraResolver = new JiraResolver()

  def IrcBotListener(server: String, channel: String) {
    this.server = server
    this.channel = channel
  }


  override def onGenericMessage(event: GenericMessageEvent[IrcLogBot]) {
    if (event.getUser().getNick().toLowerCase().contains("bot")) {
      return
    }

    val bot = event.getBot()
    event.respond("bot" + event.getMessage)
    //        if (!bot.getNick().equals(bot.getName())) {
    //            bot.changeNick(bot.getName())
    //        }
    val message: String = event.getMessage()

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


  override def onPrivateMessage(privateMessageEvent: PrivateMessageEvent[IrcLogBot]) {
    privateMessageEvent.respond("pico " + privateMessageEvent.getUser + " " + privateMessageEvent.getMessage)
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

}
