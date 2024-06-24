package org.acme.service;

import static java.lang.String.format;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.User;
import software.amazon.awssdk.services.sns.SnsClient;

@ApplicationScoped
public class UserService {

    @Inject
    AwsHelper awsHelper;

    @Transactional
    public User updateUser(long existingUserId, User changeToUser) {
        User existingUser = User.findById(existingUserId);
        existingUser.setFirstName(changeToUser.getFirstName());
        existingUser.setLastName(changeToUser.getLastName());
        existingUser.setEmail(changeToUser.getEmail());
        User.persist(existingUser);

        sendNotificationMessage(format("User with Id = %s was successfully updated!", existingUserId));
        return existingUser;
    }

    @Transactional
    public Long deleteUser(long userId) {
        User existingUser = User.findById(userId);
        String notificationMessage;
        long deletedUserId = 0;
        if (existingUser != null) {
            User.delete("id", existingUser.id);
            notificationMessage = format("User with Id = %s was deleted!", userId);
            deletedUserId = userId;
        } else {
            notificationMessage = format("User with Id = %s was not found!", userId);
        }

        sendNotificationMessage(notificationMessage);
        return deletedUserId;
    }

    private void sendNotificationMessage(String message) {
        try (SnsClient snsClient = awsHelper.buildSnsClient()) {
            snsClient.subscribe(awsHelper.buildSnsSubscribeRequest());
            snsClient.publish(awsHelper.buildSnsPublishRequest(message));
        }
    }

}
