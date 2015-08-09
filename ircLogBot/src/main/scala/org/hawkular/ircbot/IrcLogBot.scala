package org.hawkular.ircbot

import org.pircbotx.{Configuration, PircBotX}

/**
 * @author Jirka Kremser
 *
 */
class IrcLogBot(config: Configuration[ _ <: PircBotX], listener: IrcBotListener) extends PircBotX(config) {

  val TRUSTSTORE_NAME = "cacerts.jks"

  def IrcLogBot(bot: IrcBotListener) {
    setNick(System.getProperty("bot.name", "ircLogBot"))
  }
}

object IrcLogBot {
  def main(args: Array[String]) {
    if (args.length != 2 && args.length != 3) {
      System.err.println("Usage: IrcLogBot IRC_SERVER IRC_CHANNEL")
      System.err.println(" e.g.: IrcLogBot irc.freenode.net '#foo'")
      System.exit(1)
    }
    val server: String = args(0)
    val channel: String = args(1)

    val botListener: IrcBotListener = new IrcBotListener(server, channel)
    val bot: PircBotX = new IrcLogBot(getConfig(server, channel), botListener)
    bot.startBot()
  }

  def getConfig(server: String, channel: String): Configuration[IrcLogBot] =
    new Configuration.Builder()
      .addAutoJoinChannel(channel)
      .setServer(server, 6667)
      .addListener(new IrcBotListener(server, channel))
      .setName(System.getProperty("bot.name", "ircLogBot"))
      .setRealName(System.getProperty("bot.name", "ircLogBot") + " (http://git.io/v3twr)")
      .setAutoReconnect(true)
      .setVersion("0.0.1")
      .setFinger("ircLogBot (source code here http://git.io/v3twr)")
      .setAutoNickChange(true)
      .setSocketTimeout(1 * 60 * 1000)
      .buildConfiguration()
}
