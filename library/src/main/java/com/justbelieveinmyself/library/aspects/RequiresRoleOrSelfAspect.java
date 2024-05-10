package com.justbelieveinmyself.library.aspects;

import com.justbelieveinmyself.library.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class RequiresRoleOrSelfAspect {

    @Before("@annotation(com.justbelieveinmyself.library.aspects.RequiresSelfOrRoles)")
    public void checkAccess(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequiresSelfOrRoles annotation = signature.getMethod().getAnnotation(RequiresSelfOrRoles.class);
        String[] allowedRoles = annotation.roles();

        Long currentUserId = Long.parseLong(joinPoint.getArgs()[0].toString());
        String[] currentUserRoles = (String[]) joinPoint.getArgs()[1];
        Long userId = Long.parseLong(joinPoint.getArgs()[2].toString());

        if (!Objects.equals(currentUserId, userId) && !hasAllowedRole(currentUserRoles, allowedRoles)) {
            throw new ForbiddenException("Only current user or user with roles: "
                    + Arrays.toString(allowedRoles) + " can have access!");
        }
    }

    private boolean hasAllowedRole(String[] currentUserRoles, String[] allowedRoles) {
        List<String> roles = new ArrayList<>(Arrays.asList(allowedRoles));
        roles.retainAll(Arrays.asList(currentUserRoles));
        return !roles.isEmpty();
    }

}

