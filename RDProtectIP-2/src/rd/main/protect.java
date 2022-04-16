package rd.main;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class protect extends JavaPlugin implements Listener {
	
	public void onEnable() {
		getLogger().info("Starting protect...");
		File config = new File(getDataFolder() + File.separator + "config.yml");
		if(!config.exists()) {
			getLogger().info("Creating new config file...");
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
			}
		Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
		getLogger().info("Events loaded!");
		getLogger().info("Server is protected!");
	}
	
	
	public void onDisable() {
		getLogger().info("Stopping protect...");
		getLogger().info("Disabled!");
	}
	  
	
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.sendMessage("Начало проверки. Пожалуйста подождите...");
		getLogger().info("Player " + p.getName() + " has connected");
		List<String> nick = getConfig().getStringList("protect.admins");
		if(nick.contains(p.getName())) {
			List<String> iplist = getConfig().getStringList("protect.ip");
			String ip = p.getAddress().getAddress() + "";
			getLogger().info("IP: " + ip);
			getLogger().info("IPs: " + iplist);
			if(iplist.contains(ip)) {
				p.sendMessage("Вы успешно прошли проверку ip адреса!");
				getLogger().info("Администратор " + p.getName() + " успешно прошел проверку");
			}
			else {
				String reason = getConfig().getString("protect.kick-message");
				getLogger().warning("Под никнеймом " + p.getName() + " пытались зайти с ip адреса: " + ip);
				p.kickPlayer(reason);
			}
		}
		else {
			p.sendMessage("Вы не являетесь администратором. Можете играть дальше!");
		}
	}

}
