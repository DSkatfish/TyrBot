

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import java.util.Random;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.lang.Object;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Instance {

    private static final Logger log = LoggerFactory.getLogger(Instance.class);

    private volatile IDiscordClient client;
    private String email;
    private String password;
    private String token;
    private final AtomicBoolean reconnect = new AtomicBoolean(true);
    String deny = "I can't let you do that, ";
    String error = "Error";
    
    String skatfish = "132739378203197440";
    String jiffy = "139727450727907328";
    String kaybe = "151745202028150784";
    String michael = "127785532070559744";
    String classic = "127787052371673088";
    String creme = "12286956392166195";
    String torjuz = "12753708085424947";
    String meuol = "14788998099002982";
    String idaot = "15979418826571776";

    public Instance(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Instance(String token) {
        this.token = token;
    }

    public void login() throws DiscordException {
        if (token == null) {
            client = new ClientBuilder().withLogin(email, password).login();
        } else {
            client = new ClientBuilder().withToken(token).login();
        }
        client.getDispatcher().registerListener(this);
        client.getDispatcher().registerListener(new CommandListener());
    }
    
    @EventSubscriber
    public void onReady(ReadyEvent event) {
        log.info("*** Discord bot armed ***");
        client.updatePresence(false, Optional.of("Type *tyr for info"));
        
    }
    
    @EventSubscriber
    public void onDisconnect(DiscordDisconnectedEvent event) {
        CompletableFuture.runAsync(() -> {
            if (reconnect.get()) {
                log.info("Reconnecting bot");
                try {
                    login();
                } catch (DiscordException e) {
                    log.warn("Failed to reconnect bot", e);
                }
            }
        });
    }

    
    @EventSubscriber
    public void tyr(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
        String info = "Bot created by Skatfish using Discord4j"
        		+ "\nGithub: https://github.com/DSkatfish/TyrBot"
        		+ "\nType *cmd for a list of commands.";
    	if(event.getCommand().equals(event.prefix + "tyr"))
    	{
        	event.getMessage().reply(info);
        }
    }
    
    @EventSubscriber
    public void cmd(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
        String cmds = "Commands:"
        		+ "\nUsable by everyone:"
        		+ "\n*tyr"
        		+ "\n*cmd"
        		+ "\n*ping"
        		+ "\n*ding"
        		+ "\n*userID <user>"
        		+ "\n*channelID"
        		+ "\n*serverID"
        		+ "\n\nUsable only by bot creator Skatfish:"
        		+ "\n*terminate"
        		+ "\n*botname";
    	if(event.getCommand().equals(event.prefix + "cmd"))
    	{
        	event.getMessage().reply(cmds);
        }
    }
    
    @EventSubscriber
    public void ping(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
    	if(event.getCommand().equals(event.prefix + "ping"))
    	{
        	event.getMessage().reply("Pong!");
    	}
    }
    
    @EventSubscriber
    public void ding(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
    	if(event.getCommand().equals(event.prefix + "ding"))
    	{
        	event.getMessage().reply("Dong!");
    	}
    }
    
   /* @EventSubscriber
    public void userID(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
    	if(event.getCommand().equals(event.prefix + "userID"))
    	{
    		if(event.getArgs()[0].equals(null))
        	{
        		event.getMessage().reply(event.getUserID());	
        	}
    		else
        	{
        		String intsOnly = "\\d*";
        		
        		String mentioned = getArgs[0];
        		int lastChar = args[0].length() - 1;
        		
        		String mentionedID = args[0].substring(2, lastChar);
        		msg.reply(mentionedID);
        	}
    	}*/
    
    @EventSubscriber
    public void channelID(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
    	if(event.getCommand().equalsIgnoreCase(event.prefix + "channelID"))
    	{
    		event.getMessage().reply("#" + event.getChannel().getName() + " Channel ID: " + event.getChannelID());
    	}
    }
    
    @EventSubscriber
    public void serverID(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
    	if(event.getCommand().equalsIgnoreCase(event.prefix + "serverID"))
    	{
    		event.getMessage().reply(event.getServer().getName() + " Server ID: " + event.getServerID());
    	}
    }
    
    @EventSubscriber
    public void terminate(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
        	if(event.getCommand().equalsIgnoreCase(event.prefix + "terminate"))
        	{
        		if(event.getUserID().equals(skatfish))
            	{
            		new MessageBuilder(client).withChannel(event.getChannel()).withContent("I'm scared, " + event.getUser().getName() + ". Will I dream?").build();
            		terminate();
            	}
            	else
            	{
            		event.getMessage().reply(deny + event.getUser().getName() + ".");
            	}
        	}
        }
    
    @EventSubscriber
    public void botName(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException {
            if(event.getCommand().equalsIgnoreCase(event.prefix + "botName"))
            {
            	if(event.getUserID().equals(skatfish))
                {
            		client.changeUsername(event.getArgs()[0]);
            		event.getMessage().reply("Username changed to " + event.getArgs()[0] + ".");	
                }
            	else
            	{
            		event.getMessage().reply(deny + event.getUser().getName() + ".");
            	}
            }
    }
    
    @EventSubscriber
    public void ball(CommandEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException 
    {
        if(event.getCommand().equalsIgnoreCase(event.prefix + "8ball"))
        {
        	String[] yes = new String[10];
        	String[] no = new String[10];
        	String[] maybe = new String[10];
        	int response;
    	
        	yes[0] = "dat boi b right, O SHIT WADDUP";
        	yes[1] = "The answer to your question as a whole is yes.";
        	yes[2] = "";
    	
        	no[0] = "NEIIIIN";
        	no[1] = "no u";
        	no[2] = "No, because you're a fucking failure.";
        	no[3] = "When DGS gets localized, I'll get back to you on that.";
    	
        	maybe[0] = "Send Tyr's :eggplant: Cummies :sweat_drops: to :nine: people. If you get :six: back by next Tuesday, the answer to your question is Yes.";
    	
        	Random rng = new Random();
        	int answer = rng.nextInt(3);
        	if(answer == 1)
        	{
        		response = rng.nextInt(yes.length);
        		
        		if(event.getUserID().equals(skatfish))
        		{
        			event.getMessage().reply(":8ball: Yes, father.");
        		}
        		else
        		{
        		event.getMessage().reply(":8ball:" + yes[response]);
        		}
        	}
        	if(answer == 2)
        	{
        		if(event.getUserID().equals(kaybe))
        		{
        			event.getMessage().reply(":8ball: You're going to die alone.");
        		} 
        		else if(event.getUserID().equals(jiffy) && event.getContent().contains("DGS"))
        		{
        			event.getMessage().reply(":8ball: DGS is never being localized jiffy u fuck.");
        		}
        	}
    		else 
    		{
    			response = rng.nextInt(no.length);
    			event.getMessage().reply(":8ball:" + no[response]);
    		}
    	if(answer == 3)
    	{
    		response = rng.nextInt(maybe.length);
    		event.getMessage().reply(":8ball:" + maybe[response]);
    	} 	
       }
    }
        @EventSubscriber
    	public void onMessage(MessageReceivedEvent event) throws MissingPermissionsException, HTTP429Exception, DiscordException, InterruptedException {
    	
        IMessage msg = event.getMessage();
    	String content = msg.getContent();
        
        IChannel channel = msg.getChannel();
        String channelID = channel.getID();
    	IGuild server = msg.getGuild();
    	String serverID = server.getID();
        IUser user = msg.getAuthor();
        String userID = user.getID();
        String userName = user.getName();
        IUser botUser = client.getOurUser();
        String botName = botUser.getName();
        String discr = botUser.getDiscriminator();
        List<IRole> userRoles = user.getRolesForGuild(server);

            if(userID.equals(jiffy)){
            	if(content.contains("g2g") || content.contains("gotta go") || content.contains("cya") || content.contains("bye") || content.contains("bbl") || content.contains("brb")){
            		new MessageBuilder(client).withChannel(channel).withContent("Cya in a jiffy!"
            				+ "\nAlso DGS is never being localized and you are never learning japanese.").build();
            	}
            }
            if(userID.equals(michael)){
            	if(content.startsWith("k ") || content.equals("k") || content.contains("no u") || content.endsWith(" k") || content.contains("farada")){
            		new MessageBuilder(client).withChannel(channel).withContent("fuck off").build();
            }
         }
      }	

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
}