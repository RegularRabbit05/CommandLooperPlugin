package github.regularrabbit05.commandloop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        this.getCommand("loop").setExecutor(this);
        this.getCommand("loopTimed").setExecutor(this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        boolean type = false;
        if (s.equalsIgnoreCase("loopTimed")) {
            type = true;
            if (strings.length < 3) return false;
        } else {
            if (strings.length < 2) return false;
        }

        int timeout = 0;
        int times = 0;
        if (type) {
            try {
                timeout = Integer.parseInt(strings[1]);
            } catch (Exception ignored) {
                return false;
            }
        }

        try {
            times = Integer.parseInt(strings[0]);
        } catch (Exception ignored) {
            return false;
        }

        StringBuilder cmd = new StringBuilder();
        for (int i = (type) ? 2 : 1; i < strings.length; i++) cmd.append(strings[i]).append(" ");

        commandSender.sendMessage("§aSuccess!");
        if (!type) {
            for (int i = 0; i < times; i++) {
                this.getServer().dispatchCommand(commandSender, cmd.toString());
            }
        } else {
            int finalTimes = times;
            int finalTimeout = timeout;
            this.getServer().getScheduler().runTaskAsynchronously(this, () -> {
                for (int i = 0; i < finalTimes; i++) {
                    try {
                        this.getServer().dispatchCommand(commandSender, cmd.toString());
                        Thread.sleep(50L * finalTimeout);
                    } catch (Exception ignored) {
                        return;
                    }
                }
            });
        }
        return true;
    }
}
