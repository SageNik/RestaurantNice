package com.restaurantNice.controller;

import com.restaurantNice.Constants;
import com.restaurantNice.entity.Group;
import com.restaurantNice.entity.User;
import com.restaurantNice.model.GroupViewModel;
import com.restaurantNice.sevice.GroupService;
import com.restaurantNice.sevice.JoinRequestService;
import com.restaurantNice.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Ник on 13.04.2018.
 */
@Controller
public class GroupController implements Constants{

    @Autowired
    private GroupService groupService;
    @Autowired
    private JoinRequestService joinRequestService;
    private static final Logger LOGGER = Logger.getLogger(GroupController.class.getName());

    @RequestMapping(value = "groups",method = RequestMethod.GET)
    public String groups(HttpSession session,Model model) {
        User authorizedUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (authorizedUser != null) {
            List<GroupViewModel> groups = groupService.getAllGroupsModel(authorizedUser);

            model.addAttribute("groups", groups);
            model.addAttribute("title", "Groups page");
            return "parts/groupsPage";
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }

    @RequestMapping(value = "newGroup",method = RequestMethod.GET)
    public String newGroup ( Model model){

            model.addAttribute("title", "New group page");
       return "parts/groupForm";
    }

    @RequestMapping(value = "editGroup",method = RequestMethod.POST)
    public String editGroup ( @RequestParam("groupId")Long groupId, Model model){
        if(groupId != null){

        Group group = groupService.getLightOneById(groupId);
        if(group != null) {
            model.addAttribute("group", group);
            model.addAttribute("title", "New group page");
            return "parts/groupForm";
        }else{
            LOGGER.info("ERROR: Group not found with id=" + groupId );
            return null;
        }
        }else{
            LOGGER.info("ERROR: not correct params: groupId=" + groupId );
            return null;
        }
    }

    @RequestMapping(value = "saveOrUpdateGroup", method = RequestMethod.POST)
    public String saveOrUpdateGroup(@RequestParam("groupId")Long groupId,@RequestParam("name")String name,
                                    @RequestParam("description")String description, HttpSession session,Model model){
        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(currentUser != null) {
            if (name != null && description != null) {
                Group group = new Group(groupId,currentUser.getId(),name,description);
                if(groupService.saveOrUpdateGroup(group))return "redirect: groups";
                else return null;

            } else {
                LOGGER.info("ERROR: not correct params: groupId=" + groupId + " or name=" + name + " or description=" + description);
                return null;
            }
        }else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }

    @RequestMapping(value = "deleteGroup", method = RequestMethod.POST)
    public String deleteGroup (@RequestParam("groupId")Long groupId){

        if(groupId != null ){
            if(groupService.deleteGroupById(groupId)) return "redirect: groups";
            else {
                LOGGER.info("ERROR: The group hasn't been deleted.");
                return null;
            }
        } else {
            LOGGER.info("ERROR: not correct params: groupId=" + groupId);
            return null;
        }
    }

    @RequestMapping(value = "joinToGroup",method = RequestMethod.POST)
    public String joinToGroup (@RequestParam("groupId")Long groupId, HttpSession session){
        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(groupId != null){
            Group group = groupService.getLightOneById(groupId);
            if(group != null && joinRequestService.sendJoinRequest(group,currentUser)) {
                LOGGER.info("Join request has been successfully sent to owner(id="+currentUser.getId()+") of group (id="+groupId+")");
                return "redirect: groups";
            }else{
                LOGGER.info("ERROR: Group not found with id=" + groupId );
                return null;
            }
        }else{
            LOGGER.info("ERROR: not correct params: groupId=" + groupId );
            return null;
        }
    }

    @RequestMapping(value = "quitGroup",method = RequestMethod.POST)
    public String quitGroup (@RequestParam("groupId")Long groupId, HttpSession session){
        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(groupId != null){
            if(groupService.quitGroup(groupId,currentUser.getId())) {
                LOGGER.info(" The group with id="+groupId+" has been successfully left by user with id="+currentUser.getId());
                return "redirect: groups";
            }else{
                LOGGER.info("ERROR: Group with id=" + groupId+"has been successfully left by user with id="+currentUser.getId() );
                return null;
            }
        }else{
            LOGGER.info("ERROR: not correct params: groupId=" + groupId );
            return null;
        }
    }
}
