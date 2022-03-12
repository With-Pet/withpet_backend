package com.example.withpet_01.api.domain.service;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Experience")
@Getter
@Setter
public class ExperienceService extends Service {
}
