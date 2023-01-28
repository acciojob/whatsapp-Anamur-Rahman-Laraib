package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {
    public static int groupcount = 0;
    public static int messageId = 0;

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
   // private int customGroupCount;
  //  private int messageId;

    public WhatsappRepository(){
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        //this.customGroupCount = 0;
       // this.messageId = 0;
    }

    public String creteUser(String name, String mobile) throws Exception {
        if(userMobile.contains(mobile))
        {
            throw new Exception("User already exists");
        }
        else {
            User user = new User(name, mobile);
            user.setName(name);
            user.setMobile(mobile);
            return "SUCCESS";
        }
    }




    public Group createGroup(List<User> users) {
        if(users.size()==2)
        {
            //String admin = users.get(0).getName();
            String groupname = users.get(1).getName();
            System.out.println(groupname);//may or may not be required
            Group group = new Group();
            group.setName(groupname);
            group.setNumberOfParticipants(users.size());
            groupUserMap.put(group, users);
            return group;
        }
        else
        {
            //String admin = users.get(0).getName();
            groupcount += 1;
            String groupname = "Group "+ groupcount;
            System.out.println(groupname);//may or may not be required
            Group group = new Group();
            group.setName(groupname);
            group.setNumberOfParticipants(users.size());
            groupUserMap.put(group, users);
            return group;
        }
    }


    public int createMessage(String content) {
        messageId += 1;
        Message message = new Message();
        message.setId(messageId);
        message.setContent(content);
        message.setTimestamp(message.getTimestamp());
        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(!groupUserMap.containsKey(group))
        {
            throw new Exception("Group does not exist");
        }
        else {
            List<User> currentUsers = groupUserMap.get(group);
            if(!currentUsers.contains(sender))
            {
                throw new Exception("You are not allowed to send message");
            }
            else
            {
                List<Message> groupMessages = groupMessageMap.get(group);
                groupMessages.add(message);
                groupMessageMap.put(group,groupMessages);
                return groupMessages.size();
            }
        }
    }


    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(!groupUserMap.containsKey(group))
        {
            throw new Exception("Group does not exist");
        }
        else {
            List<User> currentUsers = groupUserMap.get(group);
            String adminName = currentUsers.get(0).getName();
            String approverName = approver.getName();
            String userName = user.getName();
            if (!adminName.equals(approverName))
            {
                throw new Exception("Approver does not have rights");
            }
            else if(!currentUsers.contains(user))
            {
                throw new Exception("User is not a participant");
            }
            else
            {
                int userIndex = currentUsers.indexOf(user);
                Collections.swap(currentUsers,0,userIndex);
                groupUserMap.put(group,currentUsers);
                return "SUCCESS";
            }
        }
    }

    public int removeUser(User user) {
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return "null";
    }
}
