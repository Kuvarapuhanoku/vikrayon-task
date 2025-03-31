package com.vikrayon.loginauth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikrayon.loginauth.model.VikrayONUsers;

public interface VikrayONRepo extends JpaRepository<VikrayONUsers, Integer> {
	Optional<VikrayONUsers> findByEmail(String email);
}
