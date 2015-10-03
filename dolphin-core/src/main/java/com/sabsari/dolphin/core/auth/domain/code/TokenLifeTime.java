package com.sabsari.dolphin.core.auth.domain.code;

public enum TokenLifeTime {
	HOUR(3600),						// 1 시간 : accessToken default
	DAY(86400),						// 하루
	WEEK(604800),					// 1 주일
	TWO_WEEKS(1209600),				// 2 주일 : refreshToken default
	MONTH(2592000);					// 30 일
		
	private int term;
	
	TokenLifeTime(int term){
		this.term = term;
	}
	
	public int getTerm() {
		return this.term;
	}
	
	public static TokenLifeTime search(int term) {
		for (TokenLifeTime lifeTime : TokenLifeTime.values()) {
			if(lifeTime.getTerm() == term)
				return lifeTime;
		}
		return null;
	}
}
