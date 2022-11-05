package com.noolart.noolartpaperplugin.csv;

import com.noolart.noolartpaperplugin.NoolartPaperPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PasteCsvHidden implements CommandExecutor {

    public NoolartPaperPlugin noolartPaperPlugin;

    public PasteCsvHidden(NoolartPaperPlugin noolartPaperPlugin) {
        this.noolartPaperPlugin = noolartPaperPlugin;

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        CsvInserter.pasteHidden(strings[0]);
        return true;
    }
}
