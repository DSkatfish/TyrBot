import java.util.Arrays;

import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

public class CommandListener {
	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) {
		try
		{
		if (event.getMessage().getContent().toLowerCase().startsWith(CommandEvent.prefix))
		        event.getClient().getDispatcher().dispatch(new CommandEvent(event.getMessage(), event.getMessage().getContent(), event.getMessage().getContent().split(" ")[0], Arrays.copyOfRange(event.getMessage().getContent().split(" "), 1, event.getMessage().getContent().split(" ").length), event.getMessage().getChannel(), event.getMessage().getChannel().getID(), event.getMessage().getGuild(), event.getMessage().getGuild().getID(), event.getMessage().getAuthor(), event.getMessage().getAuthor().getName(), event.getMessage().getAuthor().getID(), event.getClient().getOurUser(), event.getClient().getOurUser().getName(), event.getClient().getOurUser().getID()));
		} 
		catch (Exception ex)
		{
			
		}
	}
}
