import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.EventDispatcher;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.Event;
import sx.blah.discord.handle.AudioChannel;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.obj.IApplication;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IInvite;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IRegion;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Presences;
import sx.blah.discord.modules.ModuleLoader;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.MessageBuilder;
import java.io.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class Instance extends Event implements IMessage, IDiscordClient, IUser {

    private static final Logger log = LoggerFactory.getLogger(Instance.class);

    private volatile IDiscordClient client;
    private String email;
    private String password;
    private String token;
    private final AtomicBoolean reconnect = new AtomicBoolean(true);

    public Instance(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Instance(String token) {
        this.token = token;
    }

    public void login() throws DiscordException {
            client = new ClientBuilder().withToken(token).login();
        client.getDispatcher().registerListener(this);
    }
    @EventSubscriber
    public void onReady(ReadyEvent event) throws DiscordException, HTTP429Exception {
        log.info("*** Discord bot armed ***");
        client.changeAvatar(Image.forUrl("png", "http://puu.sh/ostXD/dce8cbe78c.png"));
        client.updatePresence(false, Optional.of("Type ^tyr for info"));
    }

    @EventSubscriber
    public void onDisconnect(DiscordDisconnectedEvent event) {
        if (reconnect.get()) {
            log.info("Reconnecting bot");
            try {
                login();
            } catch (DiscordException e) {
                log.warn("Failed to reconnect bot", e);
            }
        }
    }

    @EventSubscriber
    public void onUserJoinEvent(IGuild guild, IUser user, LocalDateTime when) throws MissingPermissionsException, HTTP429Exception, DiscordException, InterruptedException {
    	String serverID = guild.getID();
    	if(serverID=="173340154184990720"){
    		IChannel chill = getChannelByID("132740188744056833");
    		String welcome = "Welcome to Tuesdank! Here we celebrate the International Day of Dank by praying to Tyr, the Norse God of Tuesdays.";
    		new MessageBuilder(client).withChannel(chill).withContent(welcome).build();	
    	}
    	
    }
    
    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException, InterruptedException {
        log.debug("Got message");
        
        IMessage msg = event.getMessage();
        String command = msg.getContent();
        IChannel channel = msg.getChannel();
    	IGuild server = msg.getGuild();
    	String serverID = msg.getID();
        IUser user = msg.getAuthor();
        String userID = user.getID();
        String userName = user.getName();
        IUser botUser = client.getOurUser();
        String botName = botUser.getName();
        String discr = botUser.getDiscriminator();
        String deny = "I'm afraid I can't do that, " + userName + ".";
        
        
        // COMMANDS //
        String info = "Bot created by @Skatfish#5926 using Discord4j"
        		+ "\nUse ^cmd for a list of commands.";
        String cmd = "Commands:"
        		+ "\n^cmd (duh)"
        		+ "\n^tyr"
        		+ "\n^this"
        		+ "\n^leave"
        		+ "\n^hentai"
        		+ "\n^terminate (usable by bot creator Skatfish only)";
       
        /* ^help */ //while (command.startsWith("^")){
        				if(command.equals("^tyr")){
        					new MessageBuilder(client).withChannel(channel).withContent(info).build();
        				} else if(command.equals("^cmd")){
        					new MessageBuilder(client).withChannel(channel).withContent(cmd).build();
        /* ^this */ } else if(command.equals("^this")){
            					String circumflex = "This\n"
            							+ "Literally this\n"
            							+ "[le]terally this\n"
            							+ "Came here to say this";
            					new MessageBuilder(client).withChannel(channel).withContent(circumflex).build();
        /* ^leave */ } else if(command.equals("^leave")){
            					String leave = "\"Don't talk to me or my roboson ever again\" -Skatfish";
            					new MessageBuilder(client).withChannel(channel).withContent(leave).build();
        /* ^hentai */ } else if(command.equals("^hentai")){
								String hentai = "\"in my defense, I did not know he was 11 before sending him hentai.\" -Skatfish";
								new MessageBuilder(client).withChannel(channel).withContent(hentai).build();
        /* ^serverID */ } else if(command.equals("^serverID")){
								new MessageBuilder(client).withChannel(channel).withContent(serverID).build();
        /* ^discr */ } else if(/* command.substring(0, 9)==*/command.startsWith("^discr")){
        			if(userID.equals("132739378203197440")){
        				String setDiscr = command.substring(7, 11);
        				//new MessageBuilder(client).withChannel(channel).withContent(setDiscr).build();
        				new MessageBuilder(client).withChannel(channel).withContent("Attempting to set discriminator to " + setDiscr).build();
        				while(discr != setDiscr){
        					client.changeUsername("lmao");
            				client.changeUsername("TYR");
            				wait(3600000);
        				}
        				new MessageBuilder(client).withChannel(channel).withContent("Discriminator now set to " + discr).build();
        			}
        			else {
        				new MessageBuilder(client).withChannel(channel).withContent("No").build();
        			}
        /* ^terminate */ } else if(command.equals("^terminate")){
        			if(userID.equals("132739378203197440")){
        	        	new MessageBuilder(client).withChannel(channel).withContent("I'm scared, " + userName + ". Will I dream?").build();
        	        	terminate();
        			}
        			else {
        				new MessageBuilder(client).withChannel(channel).withContent(deny).build();
        			}
        /* ^restart */ } else if(command.equals("^restart")){
			if(userID.equals("132739378203197440")){
	        	new MessageBuilder(client).withChannel(channel).withContent("I'll be back...").build();
	        	client.logout();
	        	login();
			}
			else {
				new MessageBuilder(client).withChannel(channel).withContent(deny).build();
			}
        /* ^botname */ } else if(command.startsWith("^botname")){
			if(userID.equals("132739378203197440")){
	        	client.changeUsername(command.substring(9, command.length()));
				new MessageBuilder(client).withChannel(channel).withContent("Username changed to " + botName).build();
			}
			else {
				new MessageBuilder(client).withChannel(channel).withContent(deny).build();
			}
       	} else {
       		// String invalid = "Invalid command; see ^help for a list of commands.";
       		// new MessageBuilder(client).withChannel(channel).withContent(invalid).build();
       			}}
        	
        //}

	public void terminate() {
        reconnect.set(false);
        try {
            client.logout();
        } catch (HTTP429Exception | DiscordException e) {
            log.warn("Logout failed", e);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public void acknowledge() throws HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() throws MissingPermissionsException, HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMessage edit(String arg0) throws MissingPermissionsException, HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attachment> getAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUser getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IChannel getChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDiscordClient getClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getCreationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<LocalDateTime> getEditedTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGuild getGuild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IUser> getMentions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAcknowledged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mentionsEveryone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reply(String arg0) throws MissingPermissionsException, HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeAvatar(Image arg0) throws DiscordException, HTTP429Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeEmail(String arg0) throws DiscordException, HTTP429Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String arg0) throws DiscordException, HTTP429Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeUsername(String arg0) throws DiscordException, HTTP429Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IApplication createApplication(String arg0) throws DiscordException, HTTP429Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGuild createGuild(String arg0, IRegion arg1, Optional<Image> arg2)
			throws HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IApplication> getApplications() throws HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AudioChannel getAudioChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IChannel getChannelByID(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IChannel> getChannels(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<IVoiceChannel> getConnectedVoiceChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IVoiceChannel> getConnectedVoiceChannels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventDispatcher getDispatcher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGuild getGuildByID(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IGuild> getGuilds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IInvite getInviteForCode(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getLaunchTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModuleLoader getModuleLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPrivateChannel getOrCreatePMChannel(IUser arg0) throws DiscordException, HTTP429Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUser getOurUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRegion getRegionByID(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IRegion> getRegions() throws HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getResponseTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUser getUserByID(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IVoiceChannel getVoiceChannelByID(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IVoiceChannel> getVoiceChannels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() throws HTTP429Exception, DiscordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePresence(boolean arg0, Optional<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAvatar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAvatarURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDiscriminator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Presences getPresence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IRole> getRolesForGuild(IGuild arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<IVoiceChannel> getVoiceChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mention() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveToVoiceChannel(IVoiceChannel arg0)
			throws DiscordException, HTTP429Exception, MissingPermissionsException {
		// TODO Auto-generated method stub
		
	}
}