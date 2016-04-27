package net.shadowfacts.discordchat;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.shadowfacts.discordchat.commands.CommandWho;
import net.shadowfacts.discordchat.discord.DiscordThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author shadowfacts
 */
@Mod(modid = DiscordChat.modId, name = DiscordChat.modId, version = DiscordChat.version, acceptableRemoteVersions = "*")
public class DiscordChat {

	public static final String modId = "DiscordChat";
	public static final String version = "0.1.0";

	public static Logger log = LogManager.getLogger(modId);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		DCConfig.init(event.getModConfigurationDirectory());

		if (DCConfig.enabled) {
			ForgeEventHandler feh = new ForgeEventHandler();
			MinecraftForge.EVENT_BUS.register(feh);
			FMLCommonHandler.instance().bus().register(feh);

		}

		DCCommands.getInstance().registerCommand("who", new CommandWho());
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		log.info("Connecting to the Discord server...");
		DiscordThread.runThread();
	}

	@Mod.EventHandler
	public void serverStoping(FMLServerStoppingEvent event) {
		DiscordThread.instance.jda.shutdown();
	}

}
