def recommendFriends(userList, friendsList, targetUser):
    # Step 1: Build user -> friends map
    user_to_friends = {userList[i]: set(friendsList[i]) for i in range(len(userList))}
    
    # Step 2: Target's friends
    target_friends = user_to_friends.get(targetUser, set())
    
    max_mutual = 0
    recommendations = []
    
    # Step 3: Iterate over all users
    for candidate in userList:
        if candidate == targetUser:
            continue
        if candidate in target_friends:
            continue
        
        candidate_friends = user_to_friends.get(candidate, set())
        # Mutual friends = intersection
        mutual_count = len(candidate_friends & target_friends)
        
        if mutual_count > 0:
            if mutual_count > max_mutual:
                max_mutual = mutual_count
                recommendations = [candidate]
            elif mutual_count == max_mutual:
                recommendations.append(candidate)
    
    return recommendations