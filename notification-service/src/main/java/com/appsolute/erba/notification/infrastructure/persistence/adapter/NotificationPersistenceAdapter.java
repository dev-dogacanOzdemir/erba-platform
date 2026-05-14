package com.appsolute.erba.notification.infrastructure.persistence.adapter;

import com.appsolute.erba.notification.domain.model.Notification;
import com.appsolute.erba.notification.domain.port.NotificationRepository;
import com.appsolute.erba.notification.infrastructure.persistence.entity.NotificationJpaEntity;
import com.appsolute.erba.notification.infrastructure.persistence.repository.SpringDataNotificationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationPersistenceAdapter implements NotificationRepository {

    private final SpringDataNotificationJpaRepository jpaRepository;

    public NotificationPersistenceAdapter(
            SpringDataNotificationJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Notification save(Notification notification) {
        NotificationJpaEntity savedEntity =
                jpaRepository.save(toEntity(notification));

        return toDomain(savedEntity);
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Notification> findByRecipientUserId(UUID recipientUserId) {
        return jpaRepository
                .findByRecipientUserIdOrderByCreatedAtDesc(recipientUserId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private NotificationJpaEntity toEntity(Notification notification) {
        return new NotificationJpaEntity(
                notification.getId(),
                notification.getRecipientUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getSourceService(),
                notification.getSourceResourceType(),
                notification.getSourceResourceId(),
                notification.isRead(),
                notification.getCreatedAt(),
                notification.getReadAt()
        );
    }

    private Notification toDomain(NotificationJpaEntity entity) {
        return Notification.restore(
                entity.getId(),
                entity.getRecipientUserId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getType(),
                entity.getSourceService(),
                entity.getSourceResourceType(),
                entity.getSourceResourceId(),
                entity.isRead(),
                entity.getCreatedAt(),
                entity.getReadAt()
        );
    }
}