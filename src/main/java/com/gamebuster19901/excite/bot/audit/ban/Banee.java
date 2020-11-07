package com.gamebuster19901.excite.bot.audit.ban;

import com.gamebuster19901.excite.bot.command.MessageContext;
import com.gamebuster19901.excite.util.Identified;

public interface Banee extends Identified {
	
	public String getName();
	
	public default Ban getLongestActiveBan(MessageContext context) {
		Ban longest = null;
		for(Ban ban : Ban.getBansOfUser(context, this.getID())) {
			if(ban.isActive()) {
				if(ban.getBanExpireTime().isAfter(longest.getBanExpireTime())) {
					longest = ban;
				}
			}
		}
		return longest;
	}
	
}