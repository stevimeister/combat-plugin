package me.stevimeister.combat.configuration.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import me.stevimeister.combat.configuration.Configuration;
import me.stevimeister.combat.configuration.ConfigurationOption;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class MainConfiguration extends Configuration {

    @ConfigurationOption("actionBar.symbol") private String actionBarSymbol_;
    @ConfigurationOption("actionBar.colourOne") private String actionBarColourOne;
    @ConfigurationOption("actionBar.colourTwo") private String actionBarColourTwo;
    @ConfigurationOption("actionBar.message") private String actionBarMessage;

    @ConfigurationOption("combat.time") private int combatTime_;
    @ConfigurationOption("combat.commands") private List<String> combatCommands;
    @ConfigurationOption("combat.bypassPermission") private String combatBypassPermission;

    @ConfigurationOption("messages.started") private String messageStarted;
    @ConfigurationOption("messages.finished") private String messageFinished_;
    @ConfigurationOption("messages.combatLogged") private String messageCombatLogged;
    @ConfigurationOption("messages.commandBlocked") private String messageCommandBlocked;

    private final String messageFinishedTitle, messageFinishedSubTitle;
    private final char actionBarSymbol;
    private final int actionBarMaxSymbols;
    private final long combatTime;

    public MainConfiguration() {
        super("config.yml");
        super.load();

        this.messageFinishedTitle = this.messageFinished_.split("\\{N}")[0];
        this.messageFinishedSubTitle = this.messageFinished_.split("\\{N}")[1];
        this.actionBarSymbol = this.actionBarSymbol_.charAt(0);
        this.combatTime = TimeUnit.SECONDS.toMillis(this.combatTime_);
        this.actionBarMaxSymbols = (int) TimeUnit.MILLISECONDS.toSeconds(this.combatTime);
    }

    public char getActionBarSymbol() {
        return this.actionBarSymbol;
    }

    public int getActionBarMaxSymbols() {
        return this.actionBarMaxSymbols;
    }

    public String getActionBarColourOne() {
        return this.actionBarColourOne;
    }

    public String getActionBarColourTwo() {
        return this.actionBarColourTwo;
    }

    public String getActionBarMessage() {
        return this.actionBarMessage;
    }

    public long getCombatTime() {
        return this.combatTime;
    }

    public List<String> getCombatCommands() {
        return this.combatCommands;
    }

    public String getCombatBypassPermission() {
        return this.combatBypassPermission;
    }

    public String getMessageStarted() {
        return this.messageStarted;
    }

    public String getMessageFinishedTitle() {
        return this.messageFinishedTitle;
    }

    public String getMessageFinishedSubTitle() {
        return this.messageFinishedSubTitle;
    }

    public String getMessageCombatLogged() {
        return this.messageCombatLogged;
    }

    public String getMessageCommandBlocked() {
        return this.messageCommandBlocked;
    }
}
