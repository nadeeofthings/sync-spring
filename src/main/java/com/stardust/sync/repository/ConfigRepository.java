package com.stardust.sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Configuration;

public interface ConfigRepository extends JpaRepository<Configuration, String> {

}
