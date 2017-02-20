package com.emi.shiro.repository;

import com.emi.shiro.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emi on 2017/2/13.
 */
public interface UserRepository extends CrudRepository<User, Long> {


    User findByUsername(String username);

    int countByUsername(String username);


//    @Query("select u.* from User u where u.username like :username and (null = :locked or u.locked = :locked")
//    void findPagedByUsernameAndLocked(String username, boolean locked, int pageSize, int currentPage);
}
