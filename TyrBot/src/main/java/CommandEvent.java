import java.util.Arrays;

import sx.blah.discord.api.Event;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

	public class CommandEvent extends Event {
		public static String prefix = "*";
	
		private final IMessage message;
		private final String content;
		private final String command;
		private final String[] args;
		private final IChannel channel;
		private final String channelID;
		private final IGuild server;
		private final String serverID;
		private final IUser user;
		private final String userName;
		private final String userID;
		private final IUser bot;
		private final String botName;
		private final String botID;
		
		public CommandEvent(IMessage message, String content, String command, String[] args, IChannel channel, String channelID, IGuild server, String serverID, IUser user, String userName, String userID, IUser bot, String botName, String botID) 
		{
			this.message = message;
			this.content = content;
			this.command = command;
			this.args = args;
			this.channel = channel;
			this.channelID = channelID;
			this.server = server;
			this.serverID = serverID;
			this.user = user;
			this.userName = userName;
			this.userID = userID;
			this.bot = bot;
			this.botName = botName;
			this.botID = botID;
		}
		
		public IMessage getMessage() {
			return message;
		}
		
		public String getContent() {
			return content;
		}
		
		public String getCommand() {
			return command;
		}
		
		public String[] getArgs() {
			return args;
		}
		
		public IChannel getChannel() {
			return channel;
		}
		
		public String getChannelID() {
			return channelID;
		}
		
		public IGuild getServer() {
			return server;
		}
		
		public String getServerID() {
			return serverID;
		}
		
		public IUser getUser() {
			return user;
		}
		
		public String getUserID() {
			return userID;
		}
}