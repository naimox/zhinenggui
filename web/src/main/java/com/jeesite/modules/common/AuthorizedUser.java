package com.jeesite.modules.common;

import java.io.Serializable;

import com.jeesite.modules.zhinenggui.entity.ZngUser;

/**
 * Created by Administrator on 2018/8/25 0025.
 */
public class AuthorizedUser implements Serializable{

    /**
	 *
	 */
	private static final long serialVersionUID = -837768435496493421L;

	public AuthorizedUser(ZngUser user){
        this.user = user;
    }

    private ZngUser user;

    public ZngUser getUser() {
        return user;
    }

    public String getUserType() {
        return this.user.getIsManager();
    }

    public String getId() {
        return this.user.getId();
    }

    public String getName() {
        return this.user.getName();
    }

}
