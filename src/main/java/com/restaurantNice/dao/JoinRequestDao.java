package com.restaurantNice.dao;

import com.restaurantNice.entity.JoinRequest;

import java.util.List;

/**
 * Created by Ник on 14.04.2018.
 */
public interface JoinRequestDao {

    /**
     * Send (save) join request
     * @param joinRequest
     * @return True if join request has been sent (saved) or false if not.
     */
    boolean send(JoinRequest joinRequest);

    /**
     * Find all join requests by user id
     * @param userId
     * @return list of join requests belong current user if exist or empty list if not.
     */
    List<JoinRequest> findAllByUserId(Long userId);

    /**
     * Find join request in database by id
     * @param joinRequestId
     * @return Found join request if exist or null if not.
     */
    JoinRequest findOneById(Long joinRequestId);

    /**
     * Delete join request by id
     * @param joinRequestId
     * @return True if join request has been deleted or false if not.
     */
    boolean deleteById(Long joinRequestId);

    /**
     * Find join request by parameters
     * @param joinUserId
     * @param groupOwnerId
     * @param groupId
     * @return Found join request if exist or null if not.
     */
    JoinRequest findOneByParams(Long joinUserId,Long groupOwnerId,Long groupId);
}
