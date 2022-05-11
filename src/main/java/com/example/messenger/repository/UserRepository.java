package com.example.messenger.repository;

import com.example.messenger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(@Param("nickname") String nickname);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE nickname = :nickname")
    Optional<User> findByNickName(@Param("nickname") String nickname);


    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE phone = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "INSERT INTO users(phone, email, password, nickname, first_name, last_name, created_at) " +
            "VALUES (:phone, :email, :password, :nickname, :firstName, :lastName, :creatAt)")
    void registerUser(@Param("phone") String phone, @Param("email") String email, @Param("password") String password,
                      @Param("nickname") String nickname, @Param("firstName")String firstName,
                      @Param("lastName") String lastName, @Param("creatAt")Date creatAt);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE nickname=:username AND password=:password")
    Optional<User> verifyUser(@Param("username") String username, @Param("password") String password);

    @Query(nativeQuery = true, value =
            "SELECT users.id, users.phone, users.email, users.password, users.nickname, users.first_name, users.last_name, users.created_at FROM users " +
            "JOIN (SELECT contact_id FROM user_contacts WHERE user_id =:id) as uc ON users.id = uc.contact_id;")
    List<User> pullContacts(@Param("id") Long id);

    @Query(nativeQuery = true, value =
            "SELECT users.id, phone, email, password, nickname, first_name, last_name, created_at FROM users " +
            "JOIN participants p on users.id = p.users_id " +
            "WHERE conversation_id = :id")
    List<User> findAllParticipants(@Param("id")Long id);
}
