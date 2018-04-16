package com.restaurantNice.sevice;

import com.restaurantNice.entity.Group;
import com.restaurantNice.entity.User;
import com.restaurantNice.model.GroupViewModel;

import java.util.List;

/**
 * Created by Ник on 13.04.2018.
 */
public interface GroupService {

    /**
     * Get all groups from database
     * @return List of groups if exist in database or empty list if not.
     */
    List<Group> getAllGroups();

    /**
     * Get all group view models from database by authorized user
     * @param authorizedUser
     * @return List of group view models if exist in database or empty list if not.
     */
    List<GroupViewModel> getAllGroupsModel(User authorizedUser);

    /**
     * Save or update group
     * @param group
     * @return True if group has been saved or updated or false if not.
     */
    boolean saveOrUpdateGroup(Group group);

    /**
     * Get  group by id
     * @param groupId
     * @return group if it has been found or null if not.
     */
    Group getOneById(Long groupId);

    /**
     * Delete group by id from database
     * @param groupId
     * @return True if group has been deleted or false if not.
     */
    boolean deleteGroupById(Long groupId);

    /**
     * Quit group by current user
     * @param groupId
     * @param userId
     * @return True if group has been left by user or false if not.
     */
    boolean quitGroup(Long groupId,Long userId);


}
