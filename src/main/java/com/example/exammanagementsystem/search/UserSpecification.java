package com.example.exammanagementsystem.search;

import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                username == null || username.isEmpty()
                        ? null
                        : criteriaBuilder.equal(root.get("username"), username);
    }

    public static Specification<User> hasRole(RoleName roleName) {
        return (root, query, criteriaBuilder) -> {
            if (roleName == null) return null;

            // Join user.roles
            var rolesJoin = root.join("roles");
            return criteriaBuilder.equal(rolesJoin.get("name"), roleName);
        };
    }

    public static Specification<User> hasFirstname(String firstname) {
        return (root, query, criteriaBuilder) ->
                firstname == null || firstname.isEmpty()
                        ? null
                        : criteriaBuilder.like(root.get("firstname"), "%" + firstname + "%");
    }

    public static Specification<User> hasLastname(String lastname) {
        return (root, query, criteriaBuilder) ->
                lastname == null || lastname.isEmpty()
                        ? null
                        : criteriaBuilder.like(root.get("lastname"), "%" + lastname + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                email == null || email.isEmpty()
                        ? null
                        : criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }
}
