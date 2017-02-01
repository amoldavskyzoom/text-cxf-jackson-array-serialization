package com.projectx.web.api.data.user;

import com.projectx.logic.api.data.User;
import com.projectx.sdk.user.impl.BasicUser;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * Created by amoldavsky on 9/29/16.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    Integer id;
    String profileImageUrl;
    String username;
    String firstName;
    String lastName;
    String email;
    Date dateCreated;

	public UserDTO() {}

    // copy constructor
    public UserDTO( UserDTO aUser ) {

        setId( aUser.getId() );
        setProfileImageUrl( aUser.getProfileImageUrl() );
        setUsername( aUser.getUsername() );
        setFirstName( aUser.getFirstName() );
        setLastName( aUser.getLastName() );
        setEmail( aUser.getEmail() );
        setDateCreated( aUser.getDateCreated() );

    }

    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    
    public void setProfileImageUrl( String url ) {
        this.profileImageUrl = url;

    }

}
