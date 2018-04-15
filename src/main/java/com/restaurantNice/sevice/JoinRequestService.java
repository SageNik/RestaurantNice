package com.restaurantNice.sevice;

import com.restaurantNice.entity.Group;
import com.restaurantNice.entity.JoinRequest;
import com.restaurantNice.entity.User;

/**
 * Created by Ник on 14.04.2018.
 */
public interface JoinRequestService {

    /**
     * Send a request to join the group when the current user is not the owner
     * @param group
     * @param currentUser
     * @return True if request has been sent or false if not.
     */
    boolean sendJoinRequest(Group group, User currentUser);

    /**
     * Accept join request by id for current user
     * @param joinRequestId
     * @return True if join request has been accepted or false if not.
     */
    boolean acceptRequest(Long joinRequestId);

    /**
     * Reject join request by id for current user
     * @param joinRequestId
     * @return True if join request has been rejected or false if not.
     */
    boolean rejectRequest(Long joinRequestId);

    /**
     * Get join request by id
     * @param joinRequestId
     * @return Found join request if exist or null if not
     */
    JoinRequest getOneById(Long joinRequestId);
}
