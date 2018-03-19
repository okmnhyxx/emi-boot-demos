package com.emi.shiro.t15.repository;

import com.emi.shiro.t15.domain.User;
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
