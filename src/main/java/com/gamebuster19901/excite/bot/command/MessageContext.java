package com.gamebuster19901.excite.bot.command;

import com.gamebuster19901.excite.Main;
import com.gamebuster19901.excite.bot.user.DiscordUser;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class MessageContext<E>{
	
	private E event;
	
	public MessageContext(E e) {
		if(e == null || e instanceof GuildMessageReceivedEvent || e instanceof PrivateMessageReceivedEvent) {
			this.event = e;
		}
		else {
			throw new IllegalArgumentException(e.toString());
		}
	}
	
	public MessageContext() {
		this.event = null;
	}
	
	public boolean isGuildMessage() {
		return event instanceof GuildMessageReceivedEvent;
	}
	
	public boolean isPrivateMessage() {
		return event instanceof PrivateMessageReceivedEvent;
	}
	
	public boolean isConsoleMessage() {
		return event == null;
	}
	
	public E getEvent() {
		return event;
	}
	
	public DiscordUser getAuthor() {
		if(isConsoleMessage()) {
			return null;
		}
		if(event instanceof GuildMessageReceivedEvent) {
			return DiscordUser.getDiscordUser(((GuildMessageReceivedEvent)event).getMessage().getAuthor().getIdLong());
		}
		else if (event instanceof PrivateMessageReceivedEvent) {
			return DiscordUser.getDiscordUser(((PrivateMessageReceivedEvent)event).getMessage().getAuthor().getIdLong());
		}
		return null;
	}
	
	public boolean isAdmin() {
		return isConsoleMessage() || 
				getAuthor()
				.getJDAUser()
				.getAsTag()
				.equalsIgnoreCase(Main.botOwner);
	}
	
	public void sendMessage(String message) {
		if(!isConsoleMessage()) {
			if(event instanceof GuildMessageReceivedEvent) {
				((GuildMessageReceivedEvent)event).getChannel().sendMessage(message).complete();
			}
			else if (event instanceof PrivateMessageReceivedEvent) {
				((PrivateMessageReceivedEvent)event).getChannel().sendMessage(message).complete();
			}
		}
		else {
			System.out.println(message);
		}
	}
	
	public String getMention() {
		if(isConsoleMessage()) {
			return "@ CONSOLE";
		}
		return getAuthor().getJDAUser().getAsMention();
	}
	
	public String getTag() {
		if(isConsoleMessage()) {
			return "CONSOLE";
		}
		return getAuthor().getJDAUser().getAsTag();
	}
}